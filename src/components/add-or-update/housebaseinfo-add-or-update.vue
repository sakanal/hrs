<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="区域id" prop="areaId">
      <el-input v-model="dataForm.areaId" placeholder="区域id"></el-input>
    </el-form-item>
    <el-form-item label="类别id" prop="categoryId">
      <el-input v-model="dataForm.categoryId" placeholder="类别id"></el-input>
    </el-form-item>
    <el-form-item label="房源名称" prop="name">
      <el-input v-model="dataForm.name" placeholder="房源名称"></el-input>
    </el-form-item>
    <el-form-item label="房源有多少可用卧室" prop="roomNumber">
      <el-input v-model="dataForm.roomNumber" placeholder="房源有多少可用卧室"></el-input>
    </el-form-item>
    <el-form-item label="房源有多少可用厅室" prop="hallNumber">
      <el-input v-model="dataForm.hallNumber" placeholder="房源有多少可用厅室"></el-input>
    </el-form-item>
    <el-form-item label="房源有多少可用卫生间" prop="cloakroomNumber">
      <el-input v-model="dataForm.cloakroomNumber" placeholder="房源有多少可用卫生间"></el-input>
    </el-form-item>
    <el-form-item label="占地面积" prop="areaCovered">
      <el-input v-model="dataForm.areaCovered" placeholder="占地面积"></el-input>
    </el-form-item>
    <el-form-item label="总层数" prop="totalFloor">
      <el-input v-model="dataForm.totalFloor" placeholder="总层数"></el-input>
    </el-form-item>
    <el-form-item label="所在层数" prop="floor">
      <el-input v-model="dataForm.floor" placeholder="所在层数"></el-input>
    </el-form-item>
    <el-form-item label="是否有电梯 0-没有电梯 1-有电梯" prop="hasElevator">
      <el-input v-model="dataForm.hasElevator" placeholder="是否有电梯 0-没有电梯 1-有电梯"></el-input>
    </el-form-item>
    <el-form-item label="是否有车位 0-没有车位 1-有车位" prop="hasParkingPlace">
      <el-input v-model="dataForm.hasParkingPlace" placeholder="是否有车位 0-没有车位 1-有车位"></el-input>
    </el-form-item>
    <el-form-item label="房屋朝向id" prop="orientationId">
      <el-input v-model="dataForm.orientationId" placeholder="房屋朝向id"></el-input>
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
          areaId: '',
          categoryId: '',
          name: '',
          roomNumber: '',
          hallNumber: '',
          cloakroomNumber: '',
          areaCovered: '',
          totalFloor: '',
          floor: '',
          hasElevator: '',
          hasParkingPlace: '',
          orientationId: '',
          isDeleted: '',
          createdTime: '',
          modifyTime: ''
        },
        dataRule: {
          areaId: [
            { required: true, message: '区域id不能为空', trigger: 'blur' }
          ],
          categoryId: [
            { required: true, message: '类别id不能为空', trigger: 'blur' }
          ],
          name: [
            { required: true, message: '房源名称不能为空', trigger: 'blur' }
          ],
          roomNumber: [
            { required: true, message: '房源有多少可用卧室不能为空', trigger: 'blur' }
          ],
          hallNumber: [
            { required: true, message: '房源有多少可用厅室不能为空', trigger: 'blur' }
          ],
          cloakroomNumber: [
            { required: true, message: '房源有多少可用卫生间不能为空', trigger: 'blur' }
          ],
          areaCovered: [
            { required: true, message: '占地面积不能为空', trigger: 'blur' }
          ],
          totalFloor: [
            { required: true, message: '总层数不能为空', trigger: 'blur' }
          ],
          floor: [
            { required: true, message: '所在层数不能为空', trigger: 'blur' }
          ],
          hasElevator: [
            { required: true, message: '是否有电梯 0-没有电梯 1-有电梯不能为空', trigger: 'blur' }
          ],
          hasParkingPlace: [
            { required: true, message: '是否有车位 0-没有车位 1-有车位不能为空', trigger: 'blur' }
          ],
          orientationId: [
            { required: true, message: '房屋朝向id不能为空', trigger: 'blur' }
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
              url: this.$http.adornUrl(`/house/housebaseinfo/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.areaId = data.houseBaseInfo.areaId
                this.dataForm.categoryId = data.houseBaseInfo.categoryId
                this.dataForm.name = data.houseBaseInfo.name
                this.dataForm.roomNumber = data.houseBaseInfo.roomNumber
                this.dataForm.hallNumber = data.houseBaseInfo.hallNumber
                this.dataForm.cloakroomNumber = data.houseBaseInfo.cloakroomNumber
                this.dataForm.areaCovered = data.houseBaseInfo.areaCovered
                this.dataForm.totalFloor = data.houseBaseInfo.totalFloor
                this.dataForm.floor = data.houseBaseInfo.floor
                this.dataForm.hasElevator = data.houseBaseInfo.hasElevator
                this.dataForm.hasParkingPlace = data.houseBaseInfo.hasParkingPlace
                this.dataForm.orientationId = data.houseBaseInfo.orientationId
                this.dataForm.isDeleted = data.houseBaseInfo.isDeleted
                this.dataForm.createdTime = data.houseBaseInfo.createdTime
                this.dataForm.modifyTime = data.houseBaseInfo.modifyTime
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
              url: this.$http.adornUrl(`/house/housebaseinfo/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'areaId': this.dataForm.areaId,
                'categoryId': this.dataForm.categoryId,
                'name': this.dataForm.name,
                'roomNumber': this.dataForm.roomNumber,
                'hallNumber': this.dataForm.hallNumber,
                'cloakroomNumber': this.dataForm.cloakroomNumber,
                'areaCovered': this.dataForm.areaCovered,
                'totalFloor': this.dataForm.totalFloor,
                'floor': this.dataForm.floor,
                'hasElevator': this.dataForm.hasElevator,
                'hasParkingPlace': this.dataForm.hasParkingPlace,
                'orientationId': this.dataForm.orientationId,
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
