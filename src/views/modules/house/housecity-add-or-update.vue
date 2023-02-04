<template>
  <el-dialog :title="(!dataForm.id ? '新增' : '修改')+type" :close-on-click-modal="false" :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
      <el-form-item label="城市名称" prop="name">
        <el-input v-model="dataForm.name" placeholder="城市名称"></el-input>
      </el-form-item>
      <el-form-item label="城市级别" prop="level">
        <!--        <el-input v-model="dataForm.level" placeholder="城市级别 0-省 1-市"></el-input>-->
        <el-radio v-model="dataForm.level" label="0">省</el-radio>
        <el-radio v-model="dataForm.level" label="1">市</el-radio>
      </el-form-item>
      <!--      <el-form-item label="父城市id" prop="superiorId">-->
      <!--        <el-input v-model="dataForm.superiorId" placeholder="父城市id"></el-input>-->
      <!--      </el-form-item>-->
      <el-form-item label="排序" prop="sort">
        <!--        <el-input v-model="dataForm.sort" placeholder="排序"></el-input>-->
        <el-input-number v-model="dataForm.sort" controls-position="right" :min="0" :max="100"></el-input-number>
      </el-form-item>
      <el-form-item label="是否显示" prop="showState">
        <el-radio v-model="dataForm.showState" label="0">隐藏</el-radio>
        <el-radio v-model="dataForm.showState" label="1">显示</el-radio>
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
      type: '',
      dataForm: {
        id: 0,
        name: '',
        level: '',
        superiorId: '',
        sort: '',
        showState: '',
        isDeleted: '',
        createdTime: '',
        modifyTime: ''
      },
      dataRule: {
        name: [
          {required: true, message: '城市名称不能为空', trigger: 'blur'}
        ],
        level: [
          {required: true, message: '城市级别 0-省 1-市不能为空', trigger: 'blur'}
        ],
        superiorId: [
          {required: true, message: '父城市id不能为空', trigger: 'blur'}
        ],
        sort: [
          {required: true, message: '排序不能为空', trigger: 'blur'}
        ],
        showState: [
          {required: true, message: '是否显示 0-不显示 1-显示不能为空', trigger: 'blur'}
        ],
        isDeleted: [
          {required: true, message: '是否删除 0-未删除 1-已删除不能为空', trigger: 'blur'}
        ],
        createdTime: [
          {required: true, message: '创建时间不能为空', trigger: 'blur'}
        ],
        modifyTime: [
          {required: true, message: '修改时间不能为空', trigger: 'blur'}
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
            url: this.$http.adornUrl(`/house/housecity/info/${this.dataForm.id}`),
            method: 'get',
            params: this.$http.adornParams()
          }).then(({data}) => {
            if (data && data.code === 0) {
              this.dataForm.name = data.houseCity.name
              this.dataForm.level = data.houseCity.level
              this.dataForm.superiorId = data.houseCity.superiorId
              this.dataForm.sort = data.houseCity.sort
              this.dataForm.showState = data.houseCity.showState
              this.dataForm.isDeleted = data.houseCity.isDeleted
              this.dataForm.createdTime = data.houseCity.createdTime
              this.dataForm.modifyTime = data.houseCity.modifyTime
            }
          })
        }
      })
    },
    // 表单提交
    dataFormSubmit () {
      this.$refs['dataForm'].validate((valid) => {
        console.log(this.dataForm)
        if (valid) {
          this.$http({
            url: this.$http.adornUrl(`/house/housecity/${!this.dataForm.id ? 'save' : 'update'}`),
            method: 'post',
            data: this.$http.adornData({
              'id': this.dataForm.id || undefined,
              'name': this.dataForm.name,
              'level': this.dataForm.level,
              'superiorId': this.dataForm.superiorId,
              'sort': this.dataForm.sort,
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
