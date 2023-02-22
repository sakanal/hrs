<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="商家的用户id" prop="userId">
      <el-input v-model="dataForm.userId" placeholder="商家的用户id"></el-input>
    </el-form-item>
    <el-form-item label="房源基本信息id" prop="baseInfoId">
      <el-input v-model="dataForm.baseInfoId" placeholder="房源基本信息id"></el-input>
    </el-form-item>
    <el-form-item label="房源类别id" prop="categoryId">
      <el-input v-model="dataForm.categoryId" placeholder="房源类别id"></el-input>
    </el-form-item>
    <el-form-item label="房源状态id" prop="stateId">
      <el-input v-model="dataForm.stateId" placeholder="房源状态id"></el-input>
    </el-form-item>
    <el-form-item label="推广次数" prop="promotionNumber">
      <el-input v-model="dataForm.promotionNumber" placeholder="推广次数"></el-input>
    </el-form-item>
    <el-form-item label="推广状态 0-审核中 1-推广中 2-结束推广" prop="promotionState">
      <el-input v-model="dataForm.promotionState" placeholder="推广状态 0-审核中 1-推广中 2-结束推广"></el-input>
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
          categoryId: '',
          stateId: '',
          promotionNumber: '',
          promotionState: '',
          isDeleted: '',
          createdTime: '',
          modifyTime: ''
        },
        dataRule: {
          userId: [
            { required: true, message: '商家的用户id不能为空', trigger: 'blur' }
          ],
          baseInfoId: [
            { required: true, message: '房源基本信息id不能为空', trigger: 'blur' }
          ],
          categoryId: [
            { required: true, message: '房源类别id不能为空', trigger: 'blur' }
          ],
          stateId: [
            { required: true, message: '房源状态id不能为空', trigger: 'blur' }
          ],
          promotionNumber: [
            { required: true, message: '推广次数不能为空', trigger: 'blur' }
          ],
          promotionState: [
            { required: true, message: '推广状态 0-审核中 1-推广中 2-结束推广不能为空', trigger: 'blur' }
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
              url: this.$http.adornUrl(`/user/businesspromotion/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.userId = data.businessPromotion.userId
                this.dataForm.baseInfoId = data.businessPromotion.baseInfoId
                this.dataForm.categoryId = data.businessPromotion.categoryId
                this.dataForm.stateId = data.businessPromotion.stateId
                this.dataForm.promotionNumber = data.businessPromotion.promotionNumber
                this.dataForm.promotionState = data.businessPromotion.promotionState
                this.dataForm.isDeleted = data.businessPromotion.isDeleted
                this.dataForm.createdTime = data.businessPromotion.createdTime
                this.dataForm.modifyTime = data.businessPromotion.modifyTime
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
              url: this.$http.adornUrl(`/user/businesspromotion/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'userId': this.dataForm.userId,
                'baseInfoId': this.dataForm.baseInfoId,
                'categoryId': this.dataForm.categoryId,
                'stateId': this.dataForm.stateId,
                'promotionNumber': this.dataForm.promotionNumber,
                'promotionState': this.dataForm.promotionState,
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
