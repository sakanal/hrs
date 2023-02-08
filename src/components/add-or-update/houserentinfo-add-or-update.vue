<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="房源基础信息id" prop="baseInfoId">
      <el-input v-model="dataForm.baseInfoId" placeholder="房源基础信息id"></el-input>
    </el-form-item>
    <el-form-item label="每月租金" prop="monthlyRent">
      <el-input v-model="dataForm.monthlyRent" placeholder="每月租金"></el-input>
    </el-form-item>
    <el-form-item label="每次付款需要付多少个月的租金" prop="payNumber">
      <el-input v-model="dataForm.payNumber" placeholder="每次付款需要付多少个月的租金"></el-input>
    </el-form-item>
    <el-form-item label="需要抵押多少个月的资金" prop="mortgageNumber">
      <el-input v-model="dataForm.mortgageNumber" placeholder="需要抵押多少个月的资金"></el-input>
    </el-form-item>
    <el-form-item label="租金内容ids，使用“/”分割id" prop="rentContentIds">
      <el-input v-model="dataForm.rentContentIds" placeholder="租金内容ids，使用“/”分割id"></el-input>
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
          monthlyRent: '',
          payNumber: '',
          mortgageNumber: '',
          rentContentIds: '',
          isDeleted: '',
          createdTime: '',
          modifyTime: ''
        },
        dataRule: {
          baseInfoId: [
            { required: true, message: '房源基础信息id不能为空', trigger: 'blur' }
          ],
          monthlyRent: [
            { required: true, message: '每月租金不能为空', trigger: 'blur' }
          ],
          payNumber: [
            { required: true, message: '每次付款需要付多少个月的租金不能为空', trigger: 'blur' }
          ],
          mortgageNumber: [
            { required: true, message: '需要抵押多少个月的资金不能为空', trigger: 'blur' }
          ],
          rentContentIds: [
            { required: true, message: '租金内容ids，使用“/”分割id不能为空', trigger: 'blur' }
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
              url: this.$http.adornUrl(`/house/houserentinfo/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.baseInfoId = data.houseRentInfo.baseInfoId
                this.dataForm.monthlyRent = data.houseRentInfo.monthlyRent
                this.dataForm.payNumber = data.houseRentInfo.payNumber
                this.dataForm.mortgageNumber = data.houseRentInfo.mortgageNumber
                this.dataForm.rentContentIds = data.houseRentInfo.rentContentIds
                this.dataForm.isDeleted = data.houseRentInfo.isDeleted
                this.dataForm.createdTime = data.houseRentInfo.createdTime
                this.dataForm.modifyTime = data.houseRentInfo.modifyTime
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
              url: this.$http.adornUrl(`/house/houserentinfo/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'baseInfoId': this.dataForm.baseInfoId,
                'monthlyRent': this.dataForm.monthlyRent,
                'payNumber': this.dataForm.payNumber,
                'mortgageNumber': this.dataForm.mortgageNumber,
                'rentContentIds': this.dataForm.rentContentIds,
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
