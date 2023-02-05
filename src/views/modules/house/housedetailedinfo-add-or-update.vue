<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="房源基础信息id" prop="baseInfoId">
      <el-input v-model="dataForm.baseInfoId" placeholder="房源基础信息id"></el-input>
    </el-form-item>
    <el-form-item label="最早入住时间" prop="earliestInTime">
      <el-input v-model="dataForm.earliestInTime" placeholder="最早入住时间"></el-input>
    </el-form-item>
    <el-form-item label="宜居人数" prop="livablePeopleNumber">
      <el-input v-model="dataForm.livablePeopleNumber" placeholder="宜居人数"></el-input>
    </el-form-item>
    <el-form-item label="看房时间 0-仅周末 1-仅工作日 2-工作日晚上和周末 3-随时看房" prop="lookingHouseTimeState">
      <el-input v-model="dataForm.lookingHouseTimeState" placeholder="看房时间 0-仅周末 1-仅工作日 2-工作日晚上和周末 3-随时看房"></el-input>
    </el-form-item>
    <el-form-item label="装修情况 0-毛胚 1-简单装修 2-精装修 3-豪华装修" prop="decorationState">
      <el-input v-model="dataForm.decorationState" placeholder="装修情况 0-毛胚 1-简单装修 2-精装修 3-豪华装修"></el-input>
    </el-form-item>
    <el-form-item label="房屋设施id 使用"/"分隔" prop="baseFacilitiesIds">
      <el-input v-model="dataForm.baseFacilitiesIds" placeholder="房屋设施id 使用"/"分隔"></el-input>
    </el-form-item>
    <el-form-item label="房屋亮点id 使用"/"分隔" prop="baseHighlightIds">
      <el-input v-model="dataForm.baseHighlightIds" placeholder="房屋亮点id 使用"/"分隔"></el-input>
    </el-form-item>
    <el-form-item label="房屋要求id 使用"/"分隔" prop="baseRentalRequirementIds">
      <el-input v-model="dataForm.baseRentalRequirementIds" placeholder="房屋要求id 使用"/"分隔"></el-input>
    </el-form-item>
    <el-form-item label="房源详细描述" prop="houseDetailedDescription">
      <el-input v-model="dataForm.houseDetailedDescription" placeholder="房源详细描述"></el-input>
    </el-form-item>
    <el-form-item label="房源详细地址" prop="houseDetailedAddress">
      <el-input v-model="dataForm.houseDetailedAddress" placeholder="房源详细地址"></el-input>
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
          earliestInTime: '',
          livablePeopleNumber: '',
          lookingHouseTimeState: '',
          decorationState: '',
          baseFacilitiesIds: '',
          baseHighlightIds: '',
          baseRentalRequirementIds: '',
          houseDetailedDescription: '',
          houseDetailedAddress: '',
          isDeleted: '',
          createdTime: '',
          modifyTime: ''
        },
        dataRule: {
          baseInfoId: [
            { required: true, message: '房源基础信息id不能为空', trigger: 'blur' }
          ],
          earliestInTime: [
            { required: true, message: '最早入住时间不能为空', trigger: 'blur' }
          ],
          livablePeopleNumber: [
            { required: true, message: '宜居人数不能为空', trigger: 'blur' }
          ],
          lookingHouseTimeState: [
            { required: true, message: '看房时间 0-仅周末 1-仅工作日 2-工作日晚上和周末 3-随时看房不能为空', trigger: 'blur' }
          ],
          decorationState: [
            { required: true, message: '装修情况 0-毛胚 1-简单装修 2-精装修 3-豪华装修不能为空', trigger: 'blur' }
          ],
          baseFacilitiesIds: [
            { required: true, message: '房屋设施id 使用"/"分隔不能为空', trigger: 'blur' }
          ],
          baseHighlightIds: [
            { required: true, message: '房屋亮点id 使用"/"分隔不能为空', trigger: 'blur' }
          ],
          baseRentalRequirementIds: [
            { required: true, message: '房屋要求id 使用"/"分隔不能为空', trigger: 'blur' }
          ],
          houseDetailedDescription: [
            { required: true, message: '房源详细描述不能为空', trigger: 'blur' }
          ],
          houseDetailedAddress: [
            { required: true, message: '房源详细地址不能为空', trigger: 'blur' }
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
              url: this.$http.adornUrl(`/house/housedetailedinfo/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.baseInfoId = data.houseDetailedInfo.baseInfoId
                this.dataForm.earliestInTime = data.houseDetailedInfo.earliestInTime
                this.dataForm.livablePeopleNumber = data.houseDetailedInfo.livablePeopleNumber
                this.dataForm.lookingHouseTimeState = data.houseDetailedInfo.lookingHouseTimeState
                this.dataForm.decorationState = data.houseDetailedInfo.decorationState
                this.dataForm.baseFacilitiesIds = data.houseDetailedInfo.baseFacilitiesIds
                this.dataForm.baseHighlightIds = data.houseDetailedInfo.baseHighlightIds
                this.dataForm.baseRentalRequirementIds = data.houseDetailedInfo.baseRentalRequirementIds
                this.dataForm.houseDetailedDescription = data.houseDetailedInfo.houseDetailedDescription
                this.dataForm.houseDetailedAddress = data.houseDetailedInfo.houseDetailedAddress
                this.dataForm.isDeleted = data.houseDetailedInfo.isDeleted
                this.dataForm.createdTime = data.houseDetailedInfo.createdTime
                this.dataForm.modifyTime = data.houseDetailedInfo.modifyTime
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
              url: this.$http.adornUrl(`/house/housedetailedinfo/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'baseInfoId': this.dataForm.baseInfoId,
                'earliestInTime': this.dataForm.earliestInTime,
                'livablePeopleNumber': this.dataForm.livablePeopleNumber,
                'lookingHouseTimeState': this.dataForm.lookingHouseTimeState,
                'decorationState': this.dataForm.decorationState,
                'baseFacilitiesIds': this.dataForm.baseFacilitiesIds,
                'baseHighlightIds': this.dataForm.baseHighlightIds,
                'baseRentalRequirementIds': this.dataForm.baseRentalRequirementIds,
                'houseDetailedDescription': this.dataForm.houseDetailedDescription,
                'houseDetailedAddress': this.dataForm.houseDetailedAddress,
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
