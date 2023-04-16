<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="推广标题" prop="name">
      <el-input v-model="dataForm.name" placeholder="推广标题"></el-input>
    </el-form-item>
    <el-form-item label="推广描述" prop="describe">
      <el-input v-model="dataForm.describe" placeholder="推广描述"></el-input>
    </el-form-item>
    <el-form-item label="推广次数" prop="number">
      <el-input v-model="dataForm.number" placeholder="推广次数"></el-input>
    </el-form-item>
    <el-form-item label="推广价格" prop="price">
      <el-input v-model="dataForm.price" placeholder="推广价格"></el-input>
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
          name: '',
          describe: '',
          number: '',
          price: '',
          isDeleted: '',
          createdTime: '',
          modifyTime: ''
        },
        dataRule: {
          name: [
            { required: true, message: '推广标题不能为空', trigger: 'blur' }
          ],
          describe: [
            { required: true, message: '推广描述不能为空', trigger: 'blur' }
          ],
          number: [
            { required: true, message: '推广次数不能为空', trigger: 'blur' }
          ],
          price: [
            { required: true, message: '推广价格不能为空', trigger: 'blur' }
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
              url: this.$http.adornUrl(`/promotion/housepromotionfunction/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.name = data.housePromotionFunction.name
                this.dataForm.describe = data.housePromotionFunction.describe
                this.dataForm.number = data.housePromotionFunction.number
                this.dataForm.price = data.housePromotionFunction.price
                this.dataForm.isDeleted = data.housePromotionFunction.isDeleted
                this.dataForm.createdTime = data.housePromotionFunction.createdTime
                this.dataForm.modifyTime = data.housePromotionFunction.modifyTime
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
                'describe': this.dataForm.describe,
                'number': this.dataForm.number,
                'price': this.dataForm.price,
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
