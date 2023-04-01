<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="设施名字" prop="name">
      <el-input v-model="dataForm.name" placeholder="设施名字"></el-input>
    </el-form-item>
    <el-form-item label="设施图标" prop="iconUrl">
      <el-input v-model="dataForm.iconUrl" placeholder="设施图标url"></el-input>
    </el-form-item>
    <el-form-item label="设施类别" prop="category">
<!--      <el-input v-model="dataForm.category" placeholder="设施类别 0-公共设施 1-卧室设施"></el-input>-->
      <template>
        <el-radio v-model="dataForm.category" label="0">公共设施</el-radio>
        <el-radio v-model="dataForm.category" label="1">卧室设施</el-radio>
      </template>
    </el-form-item>
<!--    <el-form-item label="是否显示 0-不显示 1-显示" prop="showState">-->
<!--      <el-input v-model="dataForm.showState" placeholder="是否显示 0-不显示 1-显示"></el-input>-->
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
          name: '',
          iconUrl: '',
          category: '0',
          showState: '1',
          isDeleted: '',
          createdTime: '',
          modifyTime: ''
        },
        dataRule: {
          name: [
            { required: true, message: '设施名字不能为空', trigger: 'blur' }
          ],
          iconUrl: [
            { required: true, message: '设施图标url不能为空', trigger: 'blur' }
          ],
          category: [
            { required: true, message: '设施类别 0-公共设施 1-卧室设施不能为空', trigger: 'blur' }
          ],
          showState: [
            { required: true, message: '是否显示 0-不显示 1-显示不能为空', trigger: 'blur' }
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
              url: this.$http.adornUrl(`/house/basefacilities/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.name = data.baseFacilities.name
                this.dataForm.iconUrl = data.baseFacilities.iconUrl
                this.dataForm.category = data.baseFacilities.category
                this.dataForm.showState = data.baseFacilities.showState
                this.dataForm.isDeleted = data.baseFacilities.isDeleted
                this.dataForm.createdTime = data.baseFacilities.createdTime
                this.dataForm.modifyTime = data.baseFacilities.modifyTime
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
              url: this.$http.adornUrl(`/house/basefacilities/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'name': this.dataForm.name,
                'iconUrl': this.dataForm.iconUrl,
                'category': this.dataForm.category,
                'showState': this.dataForm.showState,
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
