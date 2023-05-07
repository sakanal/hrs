<template>
  <div class="mod-config">
<!--    <el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">-->
<!--      <el-form-item>-->
<!--        <el-button v-if="isAuth('house:baserentalrequirements:save')" type="primary" @click="addOrUpdateHandle()">新增</el-button>-->
<!--      </el-form-item>-->
<!--    </el-form>-->
    <el-card class="box-card">
      <div slot="header" class="clearfix" style="text-align: center">
        <span>出租要求</span>
      </div>
      <div>
        <template  v-for="rentalRequirement in dataList">
          <el-tag :type="rentalRequirement.showState===1?'':'info'" :key="rentalRequirement.id" closable @close="handleCloseTag(rentalRequirement)">
            {{rentalRequirement.name}}
          </el-tag>
        </template>
        <el-input
          style="width: 90px;"
          v-if="inputVisible"
          v-model="inputValue"
          ref="saveTagInput"
          size="mini"
          @keyup.enter.native="handleInputConfirm"
          @blur="handleInputConfirm"
        >
        </el-input>
        <el-button v-else class="button-new-tag" size="mini" @click="showInput">+ 新增出租要求</el-button>

      </div>
    </el-card>
    <!-- 弹窗, 新增 / 修改 -->
<!--    <add-or-update v-if="addOrUpdateVisible" ref="addOrUpdate" @refreshDataList="getDataList"></add-or-update>-->
  </div>
</template>

<script>
  import AddOrUpdate from '../../../components/add-or-update/baserentalrequirements-add-or-update'
  export default {
    data () {
      return {
        dataForm: {
          key: ''
        },
        dataList: [],
        pageIndex: 1,
        pageSize: 100,
        totalPage: 0,
        dataListLoading: false,
        dataListSelections: [],
        // addOrUpdateVisible: false,
        inputVisible: false,
        inputValue: ''
      }
    },
    components: {
      AddOrUpdate
    },
    activated () {
      this.getDataList()
    },
    methods: {
      // 获取数据列表
      getDataList () {
        this.dataListLoading = true
        this.$http({
          url: this.$http.adornUrl('/house/baserentalrequirements/list'),
          method: 'get',
          params: this.$http.adornParams({
            'page': this.pageIndex,
            'limit': this.pageSize,
            'key': this.dataForm.key
          })
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.dataList = data.page.list
            this.totalPage = data.page.totalCount
          } else {
            this.dataList = []
            this.totalPage = 0
          }
          this.dataListLoading = false
        })
      },
      // 每页数
      sizeChangeHandle (val) {
        this.pageSize = val
        this.pageIndex = 1
        this.getDataList()
      },
      // 当前页
      currentChangeHandle (val) {
        this.pageIndex = val
        this.getDataList()
      },
      // 多选
      selectionChangeHandle (val) {
        this.dataListSelections = val
      },
      // 新增 / 修改
      addOrUpdateHandle (id) {
        this.addOrUpdateVisible = true
        this.$nextTick(() => {
          this.$refs.addOrUpdate.init(id)
        })
      },
      // 删除
      deleteHandle (id) {
        var ids = id ? [id] : this.dataListSelections.map(item => {
          return item.id
        })
        this.$confirm(`确定对[id=${ids.join(',')}]进行[${id ? '删除' : '批量删除'}]操作?`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$http({
            url: this.$http.adornUrl('/house/baserentalrequirements/delete'),
            method: 'post',
            data: this.$http.adornData(ids, false)
          }).then(({data}) => {
            if (data && data.code === 0) {
              this.$message({
                message: '操作成功',
                type: 'success',
                duration: 1000,
                onClose: () => {
                  this.getDataList()
                }
              })
            } else {
              this.$message.error(data.msg)
            }
          })
        })
      },
      handleCloseTag (data) {
        this.$confirm(`是否将${data.name}${data.showState === 1 ? '隐藏' : '重新显示'}？`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          const showState = data.showState === 1 ? 0 : 1
          this.$http({
            url: this.$http.adornUrl(`/house/baserentalrequirements/update`),
            method: 'post',
            data: this.$http.adornData({
              id: data.id,
              showState
            })
          }).then(({data}) => {
            if (data && data.code === 0) {
              this.$message({
                message: '操作成功',
                type: 'success',
                duration: 800
              })
              this.getDataList()
            }
          })
        })
      },
      showInput () {
        this.inputVisible = true
        this.$nextTick(() => {
          this.$refs.saveTagInput.$refs.input.focus()
        })
      },
      handleInputConfirm () {
        let inputValue = this.inputValue
        if (inputValue && inputValue !== '') {
          this.$http({
            url: this.$http.adornUrl(`/house/baserentalrequirements/save`),
            method: 'post',
            data: this.$http.adornData({
              name: inputValue,
              showState: 1
            })
          }).then(({data}) => {
            if (data && data.code === 0) {
              this.$message({
                message: '操作成功',
                type: 'success',
                duration: 800
              })
              this.getDataList()
            }
          })
        }
        this.inputVisible = false
        this.inputValue = ''
      }
    }
  }
</script>

<style scoped>
.el-tag{
  margin-right: 10px;
  margin-bottom: 10px;
}
.el-card{
  margin: 0 0 20px 0;
}
</style>
