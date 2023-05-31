<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="推荐标题" prop="name">
      <el-input v-model="dataForm.name" placeholder="推荐标题"></el-input>
    </el-form-item>
    <el-form-item label="推荐备注" prop="description">
      <el-input
        type="textarea"
        placeholder="请输入内容"
        v-model="dataForm.description"
      />
    </el-form-item>
    <el-form-item label="推荐次数" prop="number">
      <el-input-number v-model="dataForm.number" :min="0" :step="100"></el-input-number>
    </el-form-item>
    <el-form-item label="推荐价格" prop="price">
      <el-input-number v-model="dataForm.price" :precision="2" :min="0"></el-input-number>
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
          name: '',
          description: '此页面购买成功后，推荐功能会作用到所有已发布的房源中。',
          number: '',
          price: ''
        },
        dataRule: {
          name: [
            { required: true, message: '推荐标题不能为空', trigger: 'blur' }
          ],
          description: [
            { required: true, message: '推荐描述不能为空', trigger: 'blur' }
          ],
          number: [
            { required: true, message: '推荐次数不能为空', trigger: 'blur' }
          ],
          price: [
            { required: true, message: '推荐价格不能为空', trigger: 'blur' }
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
              url: this.$http.adornUrl(`/promotion/housepromotionfunction/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.name = data.housePromotionFunction.name
                this.dataForm.description = data.housePromotionFunction.description
                this.dataForm.number = data.housePromotionFunction.number
                this.dataForm.price = data.housePromotionFunction.price
                this.dataForm.isDeleted = data.housePromotionFunction.isDeleted
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
              url: this.$http.adornUrl(`/promotion/housepromotionfunction/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'name': this.dataForm.name,
                'description': this.dataForm.description,
                'number': this.dataForm.number,
                'price': this.dataForm.price
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
