<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="用户id" prop="userId">
      <el-input v-model="dataForm.userId" placeholder="用户id"></el-input>
    </el-form-item>
    <el-form-item label="推广id" prop="promotionId">
      <el-input v-model="dataForm.promotionId" placeholder="推广id"></el-input>
    </el-form-item>
    <el-form-item label="订单状态 0-未支付 1-已支付 2-取消订单" prop="state">
      <el-input v-model="dataForm.state" placeholder="订单状态 0-未支付 1-已支付 2-取消订单"></el-input>
    </el-form-item>
    <el-form-item label="0-未删除 1-已删除" prop="isDeleted">
      <el-input v-model="dataForm.isDeleted" placeholder="0-未删除 1-已删除"></el-input>
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
          promotionId: '',
          state: '',
          isDeleted: '',
          createdTime: '',
          modifyTime: ''
        },
        dataRule: {
          userId: [
            { required: true, message: '用户id不能为空', trigger: 'blur' }
          ],
          promotionId: [
            { required: true, message: '推广id不能为空', trigger: 'blur' }
          ],
          state: [
            { required: true, message: '订单状态 0-未支付 1-已支付 2-取消订单不能为空', trigger: 'blur' }
          ],
          isDeleted: [
            { required: true, message: '0-未删除 1-已删除不能为空', trigger: 'blur' }
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
              url: this.$http.adornUrl(`/promotion/housepromotionorder/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.userId = data.housePromotionOrder.userId
                this.dataForm.promotionId = data.housePromotionOrder.promotionId
                this.dataForm.state = data.housePromotionOrder.state
                this.dataForm.isDeleted = data.housePromotionOrder.isDeleted
                this.dataForm.createdTime = data.housePromotionOrder.createdTime
                this.dataForm.modifyTime = data.housePromotionOrder.modifyTime
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
              url: this.$http.adornUrl(`/promotion/housepromotionorder/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'userId': this.dataForm.userId,
                'promotionId': this.dataForm.promotionId,
                'state': this.dataForm.state,
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
