<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="城市id" prop="cityId">
      <el-input v-model="dataForm.cityId" placeholder="城市id"></el-input>
    </el-form-item>
    <el-form-item label="区域名称" prop="name">
      <el-input v-model="dataForm.name" placeholder="区域名称"></el-input>
    </el-form-item>
    <el-form-item label="区域级别 0-区 1-路 2-小区" prop="level">
      <el-input v-model="dataForm.level" placeholder="区域级别 0-区 1-路 2-小区"></el-input>
    </el-form-item>
    <el-form-item label="父区域id" prop="superiorId">
      <el-input v-model="dataForm.superiorId" placeholder="父区域id"></el-input>
    </el-form-item>
    <el-form-item label="区域地址 省-市-区-路-号 使用“-”分隔" prop="areaAddress">
      <el-input v-model="dataForm.areaAddress" placeholder="区域地址 省-市-区-路-号 使用“-”分隔"></el-input>
    </el-form-item>
    <el-form-item label="排序" prop="sort">
      <el-input v-model="dataForm.sort" placeholder="排序"></el-input>
    </el-form-item>
    <el-form-item label="是否显示 0-不显示 1-显示" prop="showState">
      <el-input v-model="dataForm.showState" placeholder="是否显示 0-不显示 1-显示"></el-input>
    </el-form-item>
    <el-form-item label="是否删除 0-未删除 1-已删除" prop="isDeleted">
      <el-input v-model="dataForm.isDeleted" placeholder="是否删除 0-未删除 1-已删除"></el-input>
    </el-form-item>
    <el-form-item label="创建时间" prop="createdTime">
      <el-input v-model="dataForm.createdTime" placeholder="创建时间"></el-input>
    </el-form-item>
    <el-form-item label="修改时间" prop="modifyTime">
      <el-input v-model="dataForm.modifyTime" placeholder="修改时间"></el-input>
    </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmit()">确定</el-button>
    </span>
  </el-dialog>
</template>

<script>
  export default {
    data () {
      return {
        visible: false,
        dataForm: {
          id: 0,
          cityId: '',
          name: '',
          level: '',
          superiorId: '',
          areaAddress: '',
          sort: '',
          showState: '',
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
        }
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
              url: this.$http.adornUrl(`/house/housearea/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.cityId = data.houseArea.cityId
                this.dataForm.name = data.houseArea.name
                this.dataForm.level = data.houseArea.level
                this.dataForm.superiorId = data.houseArea.superiorId
                this.dataForm.areaAddress = data.houseArea.areaAddress
                this.dataForm.sort = data.houseArea.sort
                this.dataForm.showState = data.houseArea.showState
                this.dataForm.isDeleted = data.houseArea.isDeleted
                this.dataForm.createdTime = data.houseArea.createdTime
                this.dataForm.modifyTime = data.houseArea.modifyTime
              }
            })
          }
        })
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
                'showState': this.dataForm.showState,
                'isDeleted': this.dataForm.isDeleted,
                'createdTime': this.dataForm.createdTime,
                'modifyTime': this.dataForm.modifyTime
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
      }
    }
  }
</script>
