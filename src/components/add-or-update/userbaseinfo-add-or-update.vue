<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '新增'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="用户名" prop="userName">
      <el-input v-model="dataForm.userName" placeholder="用户名"></el-input>
    </el-form-item>
    <el-form-item label="昵称" prop="nickName">
      <el-input v-model="dataForm.nickName" placeholder="昵称"></el-input>
    </el-form-item>
<!--    <el-form-item label="头像url" prop="headPortraitUrl">-->
<!--      <el-input v-model="dataForm.headPortraitUrl" placeholder="头像url"></el-input>-->
<!--    </el-form-item>-->
    <el-form-item label="电话" prop="phone">
      <el-input v-model="dataForm.phone" placeholder="电话"></el-input>
    </el-form-item>
    <el-form-item label="邮箱" prop="email">
      <el-input v-model="dataForm.email" placeholder="邮箱"></el-input>
    </el-form-item>
<!--    <el-form-item label="密码" prop="password">-->
<!--      <el-input v-model="dataForm.password" placeholder="密码"></el-input>-->
<!--    </el-form-item>-->
<!--    <el-form-item label="是否删除 0-未删除 1-已删除" prop="isDeleted">-->
<!--      <el-input v-model="dataForm.isDeleted" placeholder="是否删除 0-未删除 1-已删除"></el-input>-->
<!--    </el-form-item>-->
<!--    <el-form-item label="创建时间" prop="createdTime">-->
<!--      <el-input v-model="dataForm.createdTime" placeholder="创建时间"></el-input>-->
<!--    </el-form-item>-->
<!--    <el-form-item label="修改时间" prop="modifyTime">-->
<!--      <el-input v-model="dataForm.modifyTime" placeholder="修改时间"></el-input>-->
<!--    </el-form-item>-->
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
          userName: '',
          nickName: '',
          headPortraitUrl: '',
          phone: '',
          email: '',
          password: '',
          isDeleted: '',
          createdTime: '',
          modifyTime: ''
        },
        dataRule: {
          userName: [
            { required: true, message: '用户名不能为空', trigger: 'blur' }
          ],
          nickName: [
            { required: true, message: '昵称不能为空', trigger: 'blur' }
          ],
          headPortraitUrl: [
            { required: true, message: '头像url不能为空', trigger: 'blur' }
          ],
          phone: [
            { required: true, message: '电话不能为空', trigger: 'blur' }
          ],
          email: [
            { required: true, message: '邮箱不能为空', trigger: 'blur' }
          ],
          password: [
            { required: true, message: '密码不能为空', trigger: 'blur' }
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
              url: this.$http.adornUrl(`/user/userbaseinfo/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.userName = data.userBaseInfo.userName
                this.dataForm.nickName = data.userBaseInfo.nickName
                this.dataForm.headPortraitUrl = data.userBaseInfo.headPortraitUrl
                this.dataForm.phone = data.userBaseInfo.phone
                this.dataForm.email = data.userBaseInfo.email
                this.dataForm.password = data.userBaseInfo.password
                this.dataForm.isDeleted = data.userBaseInfo.isDeleted
                this.dataForm.createdTime = data.userBaseInfo.createdTime
                this.dataForm.modifyTime = data.userBaseInfo.modifyTime
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
              url: this.$http.adornUrl(`/user/userbaseinfo/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'userName': this.dataForm.userName,
                'nickName': this.dataForm.nickName,
                'headPortraitUrl': this.dataForm.headPortraitUrl,
                'phone': this.dataForm.phone,
                'email': this.dataForm.email,
                'password': this.dataForm.password,
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
