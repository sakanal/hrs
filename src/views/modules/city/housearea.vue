<template>
  <div class="mod-config">
    <el-row :gutter="20">
      <el-col :span="5">
        <div class="grid-content bg-purple">
          <city-tree ref="cityTree" @cityTreeEvent="cityTreeMethod"></city-tree>
        </div>
      </el-col>
      <el-col :span="19">
        <div class="grid-content bg-purple">
          <el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
            <el-form-item>
              <el-button type="success" @click="getAllDataList()">查看所有数据</el-button>
            </el-form-item>
            <el-form-item>
              <el-input v-model="dataForm.key" placeholder="区域名称" clearable></el-input>
            </el-form-item>
            <el-form-item>
              <el-button @click="getDataList()">查询</el-button>
              <el-button v-if="isAuth('house:housearea:save')" type="primary" @click="addOrUpdateHandle()">新增
              </el-button>
              <!--              <el-button v-if="isAuth('house:housearea:delete')" type="danger" @click="deleteHandle()" :disabled="dataListSelections.length <= 0">批量删除</el-button>-->
            </el-form-item>
          </el-form>
          <el-table
            row-key="id"
            :data="dataList"
            v-loading="dataListLoading"
            @selection-change="selectionChangeHandle"
            :tree-props="{children: 'childrenList',hasChildren: 'hasChildren'}"
            style="width: 100%;"
            border>
            <el-table-column
              prop="name"
              header-align="center"
              align="center"
              width="120"
              label="区域名称">
            </el-table-column>
            <el-table-column
              prop="level"
              header-align="center"
              align="center"
              width="120"
              label="区域级别">
              <template slot-scope="scope">
                <span v-if="scope.row.level === 0">
                  <el-tag size="medium">交通路</el-tag>
                </span>
                <span v-if="scope.row.level === 1">
                  <el-tag size="medium" type="success">住宅区</el-tag>
                </span>
              </template>
            </el-table-column>
            <el-table-column
              prop="areaAddress"
              header-align="center"
              align="center"
              label="区域地址">
            </el-table-column>
            <el-table-column
              prop="showState"
              header-align="center"
              align="center"
              width="120"
              label="是否显示">
              <template slot-scope="scope">
                <el-switch
                  v-model="scope.row.showState"
                  @change="switchChange(scope.row)"
                  :active-value="1"
                  :inactive-value="0"
                  active-color="#13ce66"
                  inactive-color="#ff4949">
                </el-switch>
              </template>
            </el-table-column>
            <el-table-column
              fixed="right"
              header-align="center"
              align="center"
              width="150"
              label="操作">
              <template slot-scope="scope">
                <el-button v-if="scope.row.superiorId === 0" type="text" size="small" @click="addArea(scope.row)">新增
                </el-button>
                <el-button type="text" size="small" @click="addOrUpdateHandle(scope.row.id)">修改</el-button>
                <el-button type="text" size="small" @click="deleteHandle(scope.row.id, scope.row)">删除</el-button>
              </template>
            </el-table-column>
          </el-table>
          <el-pagination
            @size-change="sizeChangeHandle"
            @current-change="currentChangeHandle"
            :current-page="pageIndex"
            :page-sizes="[10, 20, 50, 100]"
            :page-size="pageSize"
            :total="totalPage"
            layout="total, sizes, prev, pager, next, jumper">
          </el-pagination>
          <!-- 弹窗, 新增 / 修改 -->
          <add-or-update v-if="addOrUpdateVisible" ref="addOrUpdate" @refreshDataList="getDataList"></add-or-update>
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import AddOrUpdate from '../../../components/add-or-update/housearea-add-or-update'
import cityTree from '../../../components/tree/city-tree'

export default {
  data () {
    return {
      dataForm: {
        key: '',
        cityId: ''
      },
      dataList: [],
      pageIndex: 1,
      pageSize: 10,
      totalPage: 0,
      dataListLoading: false,
      dataListSelections: [],
      addOrUpdateVisible: false
    }
  },
  components: {
    AddOrUpdate, cityTree
  },
  activated () {
    this.getDataList()
  },
  methods: {
    // 获取数据列表 key/cityId/limit/page
    getDataList () {
      this.dataListLoading = true
      this.$http({
        url: this.$http.adornUrl('/house/housearea/list'),
        method: 'get',
        params: this.$http.adornParams({
          'page': this.pageIndex,
          'limit': this.pageSize,
          'key': this.dataForm.key,
          'cityId': this.dataForm.cityId
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
    // 重置数据
    getAllDataList () {
      this.dataForm.key = ''
      this.dataForm.cityId = ''
      this.getDataList()
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
    // 为所选道路添加住宅区
    addArea (row) {
      this.addOrUpdateVisible = true
      this.$nextTick(() => {
        this.$refs.addOrUpdate.addAreaInit(row)
      })
    },
    // 删除
    deleteHandle (id, data) {
      var ids = id ? [id] : this.dataListSelections.map(item => {
        return item.id
      })
      this.$confirm(`确定对[${data.level === 0 ? '交通路段' : '住宅区'}:${data.name}]进行[${id ? '删除' : '批量删除'}]操作?${data.hasChildren ? '</br>该操作会删除其下所有相关住宅区' : ''}`, '提示', {
        dangerouslyUseHTMLString: true,
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        if (data.hasChildren) {
          let childrenList = data.childrenList
          for (let i = 0; i < childrenList.length; i++) {
            ids.push(childrenList[i].id)
          }
        }
        this.$http({
          url: this.$http.adornUrl('/house/housearea/delete'),
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
    // 获取cityTree子组件传递的值
    cityTreeMethod (value) {
      this.dataForm.cityId = value
      this.getDataList()
    },
    // 更改显示状态
    switchChange (row) {
      this.$http({
        url: this.$http.adornUrl(`/house/housearea/update/showState`),
        method: 'put',
        data: this.$http.adornData({
          'id': row.id,
          'cityId': row.cityId,
          'name': row.name,
          'level': row.level,
          'superiorId': row.superiorId,
          'areaAddress': row.areaAddress,
          'sort': row.sort,
          'showState': row.showState
        })
      }).then(({data}) => {
        if (data && data.code === 0) {
          this.$message({
            message: '修改成功',
            type: 'success',
            duration: 500,
            onClose: () => {
              this.getDataList()
            }
          })
        } else {
          this.$message.error(data.msg)
        }
      })
    }
  }
}
</script>
