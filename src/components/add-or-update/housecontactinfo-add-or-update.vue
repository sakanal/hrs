<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="房源基础信息id" prop="baseInfoId">
      <el-input v-model="dataForm.baseInfoId" placeholder="房源基础信息id"></el-input>
    </el-form-item>
    <el-form-item label="发布人昵称" prop="publisherName">
      <el-input v-model="dataForm.publisherName" placeholder="发布人昵称"></el-input>
    </el-form-item>
    <el-form-item label="发布人身份 0-个人房东 1-个人转租 2-职业房东" prop="publisherIdentity">
      <el-input v-model="dataForm.publisherIdentity" placeholder="发布人身份 0-个人房东 1-个人转租 2-职业房东"></el-input>
    </el-form-item>
    <el-form-item label="联系人电话" prop="contactPhone">
      <el-input v-model="dataForm.contactPhone" placeholder="联系人电话"></el-input>
    </el-form-item>
    <el-form-item label="接听时段_start" prop="answerPeriodTimeStart">
      <el-input v-model="dataForm.answerPeriodTimeStart" placeholder="接听时段_start"></el-input>
    </el-form-item>
    <el-form-item label="接听时段_end" prop="answerPeriodTimeEnd">
      <el-input v-model="dataForm.answerPeriodTimeEnd" placeholder="接听时段_end"></el-input>
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
          publisherName: '',
          publisherIdentity: '',
          contactPhone: '',
          answerPeriodTimeStart: '',
          answerPeriodTimeEnd: '',
          isDeleted: '',
          createdTime: '',
          modifyTime: ''
        },
        dataRule: {
          baseInfoId: [
            { required: true, message: '房源基础信息id不能为空', trigger: 'blur' }
          ],
          publisherName: [
            { required: true, message: '发布人昵称不能为空', trigger: 'blur' }
          ],
          publisherIdentity: [
            { required: true, message: '发布人身份 0-个人房东 1-个人转租 2-职业房东不能为空', trigger: 'blur' }
          ],
          contactPhone: [
            { required: true, message: '联系人电话不能为空', trigger: 'blur' }
          ],
          answerPeriodTimeStart: [
            { required: true, message: '接听时段_start不能为空', trigger: 'blur' }
          ],
          answerPeriodTimeEnd: [
            { required: true, message: '接听时段_end不能为空', trigger: 'blur' }
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
              url: this.$http.adornUrl(`/house/housecontactinfo/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.baseInfoId = data.houseContactInfo.baseInfoId
                this.dataForm.publisherName = data.houseContactInfo.publisherName
                this.dataForm.publisherIdentity = data.houseContactInfo.publisherIdentity
                this.dataForm.contactPhone = data.houseContactInfo.contactPhone
                this.dataForm.answerPeriodTimeStart = data.houseContactInfo.answerPeriodTimeStart
                this.dataForm.answerPeriodTimeEnd = data.houseContactInfo.answerPeriodTimeEnd
                this.dataForm.isDeleted = data.houseContactInfo.isDeleted
                this.dataForm.createdTime = data.houseContactInfo.createdTime
                this.dataForm.modifyTime = data.houseContactInfo.modifyTime
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
              url: this.$http.adornUrl(`/house/housecontactinfo/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'baseInfoId': this.dataForm.baseInfoId,
                'publisherName': this.dataForm.publisherName,
                'publisherIdentity': this.dataForm.publisherIdentity,
                'contactPhone': this.dataForm.contactPhone,
                'answerPeriodTimeStart': this.dataForm.answerPeriodTimeStart,
                'answerPeriodTimeEnd': this.dataForm.answerPeriodTimeEnd,
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
