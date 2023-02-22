<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="商家的用户id" prop="userId">
      <el-input v-model="dataForm.userId" placeholder="商家的用户id"></el-input>
    </el-form-item>
    <el-form-item label="房源基础信息id" prop="baseInfoId">
      <el-input v-model="dataForm.baseInfoId" placeholder="房源基础信息id"></el-input>
    </el-form-item>
    <el-form-item label="房源类别id" prop="categoryId">
      <el-input v-model="dataForm.categoryId" placeholder="房源类别id"></el-input>
    </el-form-item>
    <el-form-item label="房源状态id" prop="stateId">
      <el-input v-model="dataForm.stateId" placeholder="房源状态id"></el-input>
    </el-form-item>
    <el-form-item label="浏览人数" prop="visitorNumber">
      <el-input v-model="dataForm.visitorNumber" placeholder="浏览人数"></el-input>
    </el-form-item>
    <el-form-item label="收藏人数" prop="collectionNumber">
      <el-input v-model="dataForm.collectionNumber" placeholder="收藏人数"></el-input>
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
          visitorNumber: '',
          collectionNumber: '',
          isDeleted: '',
          createdTime: '',
          modifyTime: ''
        },
        dataRule: {
          userId: [
            { required: true, message: '商家的用户id不能为空', trigger: 'blur' }
          ],
          baseInfoId: [
            { required: true, message: '房源基础信息id不能为空', trigger: 'blur' }
          ],
          categoryId: [
            { required: true, message: '房源类别id不能为空', trigger: 'blur' }
          ],
          stateId: [
            { required: true, message: '房源状态id不能为空', trigger: 'blur' }
          ],
          visitorNumber: [
            { required: true, message: '浏览人数不能为空', trigger: 'blur' }
          ],
          collectionNumber: [
            { required: true, message: '收藏人数不能为空', trigger: 'blur' }
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
              url: this.$http.adornUrl(`/user/businesshousepublish/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.userId = data.businessHousePublish.userId
                this.dataForm.baseInfoId = data.businessHousePublish.baseInfoId
                this.dataForm.categoryId = data.businessHousePublish.categoryId
                this.dataForm.stateId = data.businessHousePublish.stateId
                this.dataForm.visitorNumber = data.businessHousePublish.visitorNumber
                this.dataForm.collectionNumber = data.businessHousePublish.collectionNumber
                this.dataForm.isDeleted = data.businessHousePublish.isDeleted
                this.dataForm.createdTime = data.businessHousePublish.createdTime
                this.dataForm.modifyTime = data.businessHousePublish.modifyTime
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
              url: this.$http.adornUrl(`/user/businesshousepublish/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'userId': this.dataForm.userId,
                'baseInfoId': this.dataForm.baseInfoId,
                'categoryId': this.dataForm.categoryId,
                'stateId': this.dataForm.stateId,
                'visitorNumber': this.dataForm.visitorNumber,
                'collectionNumber': this.dataForm.collectionNumber,
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
