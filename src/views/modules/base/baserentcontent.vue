<template>
  <div class="mod-config">
<!--    <el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">-->
<!--      <el-form-item>-->
<!--        <el-button v-if="isAuth('house:baserentcontent:save')" type="primary" @click="addOrUpdateHandle()">新增</el-button>     -->
<!--      </el-form-item>-->
<!--    </el-form>-->
    <el-card class="box-card">
      <div slot="header" class="clearfix" style="text-align: center">
        <span>租金包含</span>
      </div>
      <div>
        <template v-for="rentContent in dataList">
          <el-tag :type="rentContent.showState===1?'':'info'" :key="rentContent.id" closable @close="handleCloseTag(rentContent)">
            {{rentContent.rentContentName}}
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
        <el-button v-else class="button-new-tag" size="mini" @click="showInput">+ 新增租金内容</el-button>
      </div>
    </el-card>
    <!-- 弹窗, 新增 / 修改 -->
<!--    <add-or-update v-if="addOrUpdateVisible" ref="addOrUpdate" @refreshDataList="getDataList"></add-or-update>-->
  </div>
</template>

<script>
  import AddOrUpdate from '../../../components/add-or-update/baserentcontent-add-or-update'
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
          url: this.$http.adornUrl('/house/baserentcontent/list'),
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
        let ids = id ? [id] : this.dataListSelections.map(item => {
          return item.id
        })
        this.$http({
          url: this.$http.adornUrl('/house/baserentcontent/delete'),
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
      },
      hideHandle (data) {
        const showState = data.showState === 1 ? 0 : 1
        this.$http({
          url: this.$http.adornUrl(`/house/baserentcontent/update`),
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
      },
      handleCloseTag (data) {
        if (data.showState === 1) {
          this.hideHandle(data)
        } else {
          this.$confirm(`请选择对${data.name}的操作？`, '提示', {
            confirmButtonText: '重新显示',
            cancelButtonText: '完全删除',
            type: 'warning'
          }).then(() => {
            this.hideHandle(data)
          }).catch(() => {
            this.deleteHandle(data.id)
          })
        }
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
            url: this.$http.adornUrl(`/house/baserentcontent/save`),
            method: 'post',
            data: this.$http.adornData({
              rentContentName: inputValue,
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
