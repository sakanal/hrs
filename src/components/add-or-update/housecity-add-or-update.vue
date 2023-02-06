<template>
  <el-dialog :title="(!dataForm.id ? '新增' : '修改')" :close-on-click-modal="false" :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()"
             label-width="80px">
      <el-form-item label="城市名称" prop="name">
        <el-input v-model="dataForm.name" placeholder="城市名称"></el-input>
      </el-form-item>
      <el-form-item v-model="dataForm.level" label="城市级别" prop="level">
        <el-radio v-model="dataForm.level" label="0">省级</el-radio>
        <el-radio v-model="dataForm.level" label="1">市级</el-radio>
        <el-radio v-model="dataForm.level" label="2">县级</el-radio>
      </el-form-item>
      <el-form-item :label=superiorName prop="superiorId" v-if="superiorCityVisible">
<!--        <el-input v-model="dataForm.superiorId" placeholder="父城市id"></el-input>-->
        <city-check-strictly ref="cityCheckStrictly" @childValue="getSuperiorIds" :superiorIds="superiorIds"></city-check-strictly>
      </el-form-item>
      <el-form-item label="排序" prop="sort">
        <el-input-number v-model="dataForm.sort" controls-position="right" :min="0" :max="100"></el-input-number>
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
import cityCheckStrictly from '../cascader/city-checkStrictly'
export default {
  components: {cityCheckStrictly},
  data () {
    return {
      visible: false,
      dataForm: {
        id: 0,
        name: '',
        level: '0',
        superiorId: '0',
        sort: '0',
        showState: '1',
        isDeleted: '',
        createdTime: '',
        modifyTime: ''
      },
      dataRule: {
        name: [
          {required: true, message: '城市名称不能为空', trigger: 'blur'}
        ],
        level: [
          {required: true, message: '城市级别空', trigger: 'blur'}
        ],
        superiorId: [
          {required: true, message: '上级城市不能为空', trigger: 'blur'}
        ],
        sort: [
          {required: true, message: '排序不能为空', trigger: 'blur'}
        ],
        showState: [
          {required: true, message: '是否显示 0-不显示 1-显示不能为空', trigger: 'blur'}
        ],
        isDeleted: [
          {required: true, message: '是否删除 0-未删除 1-已删除不能为空', trigger: 'blur'}
        ],
        createdTime: [
          {required: true, message: '创建时间不能为空', trigger: 'blur'}
        ],
        modifyTime: [
          {required: true, message: '修改时间不能为空', trigger: 'blur'}
        ]
      },
      superiorCityVisible: false,
      superiorName: '',
      superiorIds: []
    }
  },
  methods: {
    init (id) {
      this.dataForm.id = id || 0
      this.visible = true
      this.$nextTick(() => {
        this.$refs['dataForm'].resetFields()
        if (this.dataForm.id) {
          this.$http({
            url: this.$http.adornUrl(`/house/housecity/info/${this.dataForm.id}`),
            method: 'get',
            params: this.$http.adornParams()
          }).then(({data}) => {
            if (data && data.code === 0) {
              this.dataForm.name = data.houseCity.name
              this.dataForm.level = data.houseCity.level.toString()
              this.dataForm.superiorId = data.houseCity.superiorId
              this.dataForm.sort = data.houseCity.sort
              this.dataForm.showState = data.houseCity.showState.toString()
              this.dataForm.isDeleted = data.houseCity.isDeleted
              this.dataForm.createdTime = data.houseCity.createdTime
              this.dataForm.modifyTime = data.houseCity.modifyTime
            }
          })
          this.$nextTick(() => {
            this.$http({
              url: this.$http.adornUrl(`/house/housecity/getSuperiorIds/${this.dataForm.id}`),
              method: 'get'
            }).then(({data}) => {
              this.superiorIds = data.data
            })
          })
        }
      })
    },
    // 表单提交
    dataFormSubmit () {
      if (this.dataForm.level !== '0') {
        this.dataForm.superiorId = ''
        if (this.superiorIds.length > 0) {
          this.dataForm.superiorId = this.superiorIds[this.superiorIds.length - 1]
        }
      } else {
        this.dataForm.superiorId = '0'
      }
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          this.$http({
            url: this.$http.adornUrl(`/house/housecity/${!this.dataForm.id ? 'save' : 'update'}`),
            method: 'post',
            data: this.$http.adornData({
              'id': this.dataForm.id || undefined,
              'name': this.dataForm.name,
              'level': this.dataForm.level,
              'superiorId': this.dataForm.superiorId,
              'sort': this.dataForm.sort,
              'showState': this.dataForm.showState,
              'isDeleted': this.dataForm.isDeleted
            })
          }).then(({data}) => {
            if (data && data.code === 0) {
              this.$message({
                message: '操作成功',
                type: 'success',
                duration: 1500,
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
    getSuperiorIds (value) {
      this.superiorIds = value
    }
  },
  watch: {
    'dataForm.level' (value) {
      this.superiorName = ''
      switch (value) {
        case '0': {
          this.superiorCityVisible = false
          break
        }
        case '1': {
          this.superiorCityVisible = true
          this.superiorName = '所属省级'
          if (this.dataForm.id === 0) {
            this.superiorIds = []
          }
          this.$nextTick(() => {
            this.$refs.cityCheckStrictly.init('1')
          })
          break
        }
        case '2': {
          this.superiorCityVisible = true
          this.superiorName = '所属市级'
          if (this.dataForm.id === 0) {
            this.superiorIds = []
          }
          this.$nextTick(() => {
            this.$refs.cityCheckStrictly.init('2')
          })
          break
        }
        default: {
          this.superiorCityVisible = false
          break
        }
      }
    }
  }
}
</script>
