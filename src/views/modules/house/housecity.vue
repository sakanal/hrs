<template>
  <div class="mod-config">
    <el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
      <el-form-item>
        <el-input v-model="dataForm.key" placeholder="输入关键字进行过滤" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-button v-if="isAuth('house:housecity:save')" type="primary" @click="addOrUpdateHandle()">新增</el-button>
<!--        <el-button v-if="isAuth('house:housecity:delete')" type="danger" @click="deleteHandle()" :disabled="dataListSelections.length <= 0">批量删除</el-button>-->
      </el-form-item>
    </el-form>
    <el-tree
      node-key="id"
      :props="props"
      :data="dataList"
      :filter-node-method="filterNode"
      accordion
      ref="tree">
      <span class="custom-tree-node" slot-scope="{ node, data }">
        <span>{{ node.label }}</span>
        <span>
          <el-button
            type="text"
            size="mini"
            @click.stop="addOrUpdateHandle(data.id)">
            修改
          </el-button>
          <el-button
            type="text"
            size="mini"
            @click.stop="deleteHandle(data.id,data)">
            删除
          </el-button>
        </span>
      </span>
    </el-tree>
    <!-- 弹窗, 新增 / 修改 -->
    <add-or-update v-if="addOrUpdateVisible" ref="addOrUpdate" @refreshDataList="getDataList"></add-or-update>
  </div>
</template>

<script>
// TODO 如果省级数据下已经有市和区数据，不能降低省级数据的城市级别；即最多只有三级数据；二级数据修改时，也要修改下级数据的城市级别
// TODO 降低城市级别时，不能作为原数据的子城市
  import AddOrUpdate from './housecity-add-or-update'
  export default {
    data () {
      return {
        dataForm: {
          key: ''
        },
        dataList: [],
        dataListLoading: false,
        dataListSelections: [],
        addOrUpdateVisible: false,
        props: {
          label: 'name',
          children: 'childrenList'
        }
      }
    },
    components: {
      AddOrUpdate
    },
    activated () {
      this.getDataList()
    },
    watch: {
      'dataForm.key' (value) {
        this.$refs.tree.filter(value)
      }
    },
    methods: {
      // 获取数据列表
      getDataList () {
        this.dataListLoading = true
        this.$http({
          url: this.$http.adornUrl('/house/housecity/childrenList'),
          method: 'post'
        }).then(({data}) => {
          console.log(data)
          if (data && data.code === 0) {
            this.dataList = data.data
          } else {
            this.dataList = []
          }
        })
      },
      // 根据关键字过滤数据
      filterNode (value, data) {
        if (!value) return true
        return data.name.indexOf(value) !== -1
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
      deleteHandle (id, data) {
        let ids = id ? [id] : this.dataListSelections.map(item => {
          return item.id
        })
        this.$confirm(`确定对[${data.name}]进行[${id ? '删除' : '批量删除'}]操作?${data.hasChildren ? '</br>该操作会删除其下所有相关城市' : ''}`, '提示', {
          dangerouslyUseHTMLString: true,
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          if (data.hasChildren) {
            let childrenList = data.childrenList
            for (let i = 0; i < childrenList.length; i++) {
              ids.push(childrenList[i].id)
              if (childrenList[i].hasChildren) {
                for (let j = 0; j < childrenList[i].childrenList.length; j++) {
                  ids.push(childrenList[i].childrenList[j].id)
                }
              }
            }
          }
          this.$http({
            url: this.$http.adornUrl('/house/housecity/delete'),
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
      }
    }
  }
</script>

<style scoped>
.custom-tree-node {
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: space-between;
  font-size: 14px;
  padding-right: 8px;
}
</style>
