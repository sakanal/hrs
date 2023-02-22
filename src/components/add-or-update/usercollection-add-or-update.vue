<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="用户id" prop="userId">
      <el-input v-model="dataForm.userId" placeholder="用户id"></el-input>
    </el-form-item>
    <el-form-item label="房源基础信息id" prop="baseInfoId">
      <el-input v-model="dataForm.baseInfoId" placeholder="房源基础信息id"></el-input>
    </el-form-item>
    <el-form-item label="房源类别id" prop="houseCategoryId">
      <el-input v-model="dataForm.houseCategoryId" placeholder="房源类别id"></el-input>
    </el-form-item>
    <el-form-item label="房源状态id" prop="houseStateId">
      <el-input v-model="dataForm.houseStateId" placeholder="房源状态id"></el-input>
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
          userId: '',
          baseInfoId: '',
          houseCategoryId: '',
          houseStateId: '',
          isDeleted: '',
          createdTime: '',
          modifyTime: ''
        },
        dataRule: {
          userId: [
            { required: true, message: '用户id不能为空', trigger: 'blur' }
          ],
          baseInfoId: [
            { required: true, message: '房源基础信息id不能为空', trigger: 'blur' }
          ],
          houseCategoryId: [
            { required: true, message: '房源类别id不能为空', trigger: 'blur' }
          ],
          houseStateId: [
            { required: true, message: '房源状态id不能为空', trigger: 'blur' }
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
              url: this.$http.adornUrl(`/user/usercollection/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.userId = data.userCollection.userId
                this.dataForm.baseInfoId = data.userCollection.baseInfoId
                this.dataForm.houseCategoryId = data.userCollection.houseCategoryId
                this.dataForm.houseStateId = data.userCollection.houseStateId
                this.dataForm.isDeleted = data.userCollection.isDeleted
                this.dataForm.createdTime = data.userCollection.createdTime
                this.dataForm.modifyTime = data.userCollection.modifyTime
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
              url: this.$http.adornUrl(`/user/usercollection/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'userId': this.dataForm.userId,
                'baseInfoId': this.dataForm.baseInfoId,
                'houseCategoryId': this.dataForm.houseCategoryId,
                'houseStateId': this.dataForm.houseStateId,
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
