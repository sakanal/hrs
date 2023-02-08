<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="房源基础信息id" prop="baseInfoId">
      <el-input v-model="dataForm.baseInfoId" placeholder="房源基础信息id"></el-input>
    </el-form-item>
    <el-form-item label="发布人id" prop="publisherId">
      <el-input v-model="dataForm.publisherId" placeholder="发布人id"></el-input>
    </el-form-item>
    <el-form-item label="房源发布状态 0-未发布 1-审核中 2-已发布 3-已下架 4-审核失败" prop="housePublishState">
      <el-input v-model="dataForm.housePublishState" placeholder="房源发布状态 0-未发布 1-审核中 2-已发布 3-已下架 4-审核失败"></el-input>
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
          baseInfoId: '',
          publisherId: '',
          housePublishState: '',
          isDeleted: '',
          createdTime: '',
          modifyTime: ''
        },
        dataRule: {
          baseInfoId: [
            { required: true, message: '房源基础信息id不能为空', trigger: 'blur' }
          ],
          publisherId: [
            { required: true, message: '发布人id不能为空', trigger: 'blur' }
          ],
          housePublishState: [
            { required: true, message: '房源发布状态 0-未发布 1-审核中 2-已发布 3-已下架 4-审核失败不能为空', trigger: 'blur' }
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
              url: this.$http.adornUrl(`/house/housestate/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.baseInfoId = data.houseState.baseInfoId
                this.dataForm.publisherId = data.houseState.publisherId
                this.dataForm.housePublishState = data.houseState.housePublishState
                this.dataForm.isDeleted = data.houseState.isDeleted
                this.dataForm.createdTime = data.houseState.createdTime
                this.dataForm.modifyTime = data.houseState.modifyTime
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
              url: this.$http.adornUrl(`/house/housestate/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'baseInfoId': this.dataForm.baseInfoId,
                'publisherId': this.dataForm.publisherId,
                'housePublishState': this.dataForm.housePublishState,
                'isDeleted': this.dataForm.isDeleted,
                'createdTime': this.dataForm.createdTime,
                'modifyTime': this.dataForm.modifyTime
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
      }
    }
  }
</script>
