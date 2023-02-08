<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="所在城市" prop="cityId">
      <city-cascader ref="cityCascader" @cityIdCascaderSelect="getCityIds" @cityNameCascaderSelect="getCityNames" :city-ids="cityIds"></city-cascader>
    </el-form-item>
    <el-form-item label="区域名称" prop="name">
      <el-input v-model="dataForm.name" placeholder="区域名称"></el-input>
    </el-form-item>
    <el-form-item label="区域级别" prop="level">
      <el-radio v-model="dataForm.level" label="0">交通路</el-radio>
      <el-radio v-model="dataForm.level" label="1">住宅区</el-radio>
    </el-form-item>
    <el-form-item v-if="superiorAreaVisible" label="所在路段" prop="superiorId">
      <area-cascader ref="areaCascader" @areaIdCascaderSelect="getAreaIds" @areaNameCascaderSelect="getAreaNames" :area-ids="areaIds"></area-cascader>
    </el-form-item>
    <el-form-item label="区域地址" prop="areaAddress">
      <el-input v-model="dataForm.areaAddress" placeholder="区域地址"></el-input>
    </el-form-item>
    <el-form-item label="排序" prop="sort">
      <el-input v-model="dataForm.sort" placeholder="排序"></el-input>
    </el-form-item>
    <el-form-item label="是否显示" prop="showState">
      <el-radio v-model="dataForm.showState" label="0">隐藏</el-radio>
      <el-radio v-model="dataForm.showState" label="1">显示</el-radio>
    </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmit()">确定</el-button>
    </span>
  </el-dialog>
</template>

<script>
import cityCascader from '../cascader/city-cascader'
import areaCascader from '../cascader/area-cascader'
export default {
  components: {cityCascader, areaCascader},
  data () {
    return {
      isInit: true,
      isAddAreaInit: false,
      visible: false,
      dataForm: {
        id: 0,
        cityId: '',
        name: '',
        level: '0',
        superiorId: '0',
        areaAddress: '',
        sort: '0',
        showState: '1',
        isDeleted: '',
        createdTime: '',
        modifyTime: ''
      },
      dataRule: {
        cityId: [
            { required: true, message: '城市id不能为空', trigger: 'blur' }
        ],
        name: [
            { required: true, message: '区域名称不能为空', trigger: 'blur' }
        ],
        level: [
            { required: true, message: '区域级别 0-区 1-路 2-小区不能为空', trigger: 'blur' }
        ],
        superiorId: [
            { required: true, message: '父区域id不能为空', trigger: 'blur' }
        ],
        areaAddress: [
            { required: true, message: '区域地址 省-市-区-路-号 使用“-”分隔不能为空', trigger: 'blur' }
        ],
        sort: [
            { required: true, message: '排序不能为空', trigger: 'blur' }
        ],
        showState: [
            { required: true, message: '是否显示 0-不显示 1-显示不能为空', trigger: 'blur' }
        ],
        isDeleted: [
            { required: true, message: '是否删除 0-未删除 1-已删除不能为空', trigger: 'blur' }
        ],
        createdTime: [
            { required: true, message: '创建时间不能为空', trigger: 'blur' }
        ],
        modifyTime: [
            { required: true, message: '修改时间不能为空', trigger: 'blur' }
        ]
      },
      cityIds: [],
      cityNames: '',
      superiorAreaVisible: false,
      areaIds: [],
      areaNames: ''
    }
  },
  methods: {
    init (id) {
      this.dataForm.id = id || 0
      this.visible = true
      this.$nextTick(() => {
        this.$refs['dataForm'].resetFields()
        this.cityIds = []
        this.cityNames = ''
        this.areaIds = []
        this.areaNames = ''
        if (this.dataForm.id) {
          // 修改，回显数据
          this.$http({
            url: this.$http.adornUrl(`/house/housearea/info/${this.dataForm.id}`),
            method: 'get',
            params: this.$http.adornParams()
          }).then(({data}) => {
            if (data && data.code === 0) {
              this.dataForm.cityId = data.houseArea.cityId
              this.dataForm.name = data.houseArea.name
              this.isInit = true
              this.dataForm.level = data.houseArea.level.toString()
              this.dataForm.superiorId = data.houseArea.superiorId
              this.areaIds.push(this.dataForm.superiorId)
              this.dataForm.areaAddress = data.houseArea.areaAddress
              this.dataForm.sort = data.houseArea.sort
              this.dataForm.showState = data.houseArea.showState.toString()
              this.dataForm.isDeleted = data.houseArea.isDeleted
              this.dataForm.createdTime = data.houseArea.createdTime
              this.dataForm.modifyTime = data.houseArea.modifyTime
            }
            // 根据city的superiorId获取链式数据，回显级联选择器
            this.$nextTick(() => {
              this.$http({
                url: this.$http.adornUrl(`/house/housecity/getSuperiorIds/${this.dataForm.cityId}`),
                method: 'get'
              }).then(({data}) => {
                this.cityIds = data.data
                this.cityIds.push(this.dataForm.cityId)
                // 初始化城市级联选择器
                this.$refs.cityCascader.init()
              })
            })
          })
        } else {
          // 新增
          this.$nextTick(() => {
            // 初始化城市级联选择器
            this.$refs.cityCascader.init()
          })
        }
      })
    },
    addAreaInit (row) {
     // 为所选路段添加小区
      this.isAddAreaInit = true
      this.dataForm.level = '0'
      this.dataForm.id = 0
      this.visible = true
      this.$nextTick(() => {
        this.$refs['dataForm'].resetFields()
        this.cityIds = []
        this.cityNames = ''
        this.areaIds = []
        this.areaNames = ''
        this.superiorAreaVisible = true
        this.dataForm.cityId = row.cityId
        this.dataForm.level = '1'
        this.dataForm.superiorId = row.id
        this.areaIds.push(row.id)

        // 根据city的superiorId获取链式数据，回显级联选择器
        this.$nextTick(() => {
          this.$http({
            url: this.$http.adornUrl(`/house/housecity/getSuperiorIds/${row.cityId}`),
            method: 'get'
          }).then(({data}) => {
            this.cityIds = data.data
            this.cityIds.push(row.cityId)
            // 初始化城市级联选择器
            this.$refs.cityCascader.init()
          })
        })
      })
      this.isAddAreaInit = false
    },
      // 表单提交
    dataFormSubmit () {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          this.$http({
            url: this.$http.adornUrl(`/house/housearea/${!this.dataForm.id ? 'save' : 'update'}`),
            method: 'post',
            data: this.$http.adornData({
              'id': this.dataForm.id || undefined,
              'cityId': this.dataForm.cityId,
              'name': this.dataForm.name,
              'level': this.dataForm.level,
              'superiorId': this.dataForm.superiorId,
              'areaAddress': this.dataForm.areaAddress,
              'sort': this.dataForm.sort,
              'showState': this.dataForm.showState
            })
          }).then(({data}) => {
            if (data && data.code === 0) {
              this.$message({
                message: '操作成功',
                type: 'success',
                duration: 1000,
                onClose: () => {
                  this.visible = false
                  this.$emit('refreshDataList')
                }
              })
            } else {
              this.$message.error(data.msg)
            }
          })
        }
      })
    },
    getCityIds (value) {
      // console.log('getCityIds')
      this.cityIds = value
      if (value.length > 0) {
        this.dataForm.cityId = value[value.length - 1]
      }
      if (this.superiorAreaVisible) {
        // 如果重新选择所在城市且当前区域级别为小区，需要重新获取新的区域数据
        this.$nextTick(() => {
          this.areaIds = []
          this.$refs.areaCascader.init(this.dataForm.cityId, true)
        })
      }
    },
    getAreaIds (value) {
      // console.log('getAreaIds')
      this.areaIds = value
      if (value.length > 0) {
        this.dataForm.superiorId = value[value.length - 1]
      }
    },
    getCityNames (value) {
      // console.log('getCityNames')
      this.cityNames = ''
      for (let i = 0; i < value.length; i++) {
        this.cityNames = this.cityNames + value[i] + '-'
      }
      this.dataForm.areaAddress = this.cityNames + this.areaNames + this.dataForm.name
    },
    getAreaNames (value) {
      // console.log('getAreaNames')
      this.areaNames = ''
      for (let i = 0; i < value.length; i++) {
        this.areaNames = this.areaNames + value[i] + '-'
      }
      this.dataForm.areaAddress = this.cityNames + this.areaNames + this.dataForm.name
    }
  },
  watch: {
    'dataForm.level' (value) {
      switch (value) {
        case '0': {
          this.superiorAreaVisible = false
          this.dataForm.superiorId = 0
          // TODO 修改为交通路时，需要将areaIds和areaNames去除，不去除也可以，要把areaAddress更新；dataFrom里的superiorId为0
          break
        }
        case '1': {
          // 通过superiorAreaVisible是否预习设置为true来判断level的更改是因为自主选择还是因为程序赋值
          if (this.superiorAreaVisible) {
            console.log('superiorAreaVisible = true')
            console.log(this.areaIds)
            this.$nextTick(() => {
              this.$refs.areaCascader.init(this.dataForm.cityId, true)
            })
          } else {
            console.log('superiorAreaVisible = false')
            this.superiorAreaVisible = true
            if (this.dataForm.id === 0 && !this.isAddAreaInit) {
              this.areaIds = []
            }
            this.$nextTick(() => {
              this.$refs.areaCascader.init(this.dataForm.cityId, true)
            })
          }
          break
        }
      }
    },
    'dataForm.superiorId' (value) {
      console.log(value)
    },
    'dataForm.name' (value) {
      if (!this.isInit) {
        this.dataForm.areaAddress = this.cityNames + this.areaNames + value
      } else {
        this.isInit = false
      }
    }
  }
}
</script>
