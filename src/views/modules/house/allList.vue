<template>
  <div class=''>
    <el-row :gutter="20">
      <el-col :span="5">
        <div style="margin-bottom: 10px">
          <el-button type="success" @click="getAll()" style="width: 100%">查看所有数据</el-button>
        </div>
        <div>
          <city-tree ref="cityTree" @cityTreeEvent="cityTreeMethod"></city-tree>
        </div>
      </el-col>
      <el-col :span="19">
        <el-tabs v-model="activeName" @tab-click="handleClick">
          <el-tab-pane label="所有数据" name="-1"></el-tab-pane>
          <el-tab-pane label="未发布" name="0"></el-tab-pane>
          <el-tab-pane label="待审核" name="1"></el-tab-pane>
          <el-tab-pane label="已发布" name="2"></el-tab-pane>
          <el-tab-pane label="已下架" name="3"></el-tab-pane>
          <el-tab-pane label="审核失败" name="4"></el-tab-pane>
        </el-tabs>
        <div>
          <template v-if="publishInfoList.length>0">
            <div v-for="publishInfo in publishInfoList">
              <el-row :gutter="20">
                <el-col :span="5">
                  <div style="width: 200px; height: 110px">
                    <img :src="publishInfo.url" style="object-fit: cover">
                  </div>
                </el-col>
                <el-col :span="13">
                  <div>
                    <template>
                      <div class="my-title">
                        {{ publishInfo.houseTitle }}
                      </div>
                    </template>
                  </div>
                  <div class="div-house-info">
                    <template v-if="publishInfo.roomNumber>0">{{ publishInfo.roomNumber }}室</template>
                    <template v-if="publishInfo.hallNumber>0">{{ publishInfo.hallNumber }}厅</template>
                    <template v-if="publishInfo.cloakroomNumber>0">{{ publishInfo.cloakroomNumber }}卫</template>
                    <template v-if="publishInfo.areaCovered>0">{{ publishInfo.areaCovered }}㎡</template>
                  </div>
                  <div class="div-house-info">
                    <template v-if="publishInfo.roadName!==null">{{ publishInfo.roadName }}</template>
                    <template v-if="publishInfo.areaName!==null">{{ publishInfo.areaName }}</template>
                  </div>
                  <div class="div-house-info">
                    <template v-if="publishInfo.publisherIdentity===0">来自个人房源</template>
                  </div>
                </el-col>
                <el-col :span="4" style="font-size: 16px;color: red;height: 100%">
                  <span v-if="publishInfo.monthlyRent>0">
                    <span style="font-size: 24px">
                      {{ publishInfo.monthlyRent }}
                    </span>
                    元/月
                  </span>
                  <span v-else>面议</span>
                  <div>
                    <template v-if="publishInfo.state===0"><el-tag>未发布</el-tag></template>
                    <template v-if="publishInfo.state===1"><el-tag type="primary">待审核</el-tag></template>
                    <template v-if="publishInfo.state===2"><el-tag type="success">已发布</el-tag></template>
                    <template v-if="publishInfo.state===3"><el-tag type="info">已下架</el-tag></template>
                    <template v-if="publishInfo.state===4"><el-tag type="danger">审核失败</el-tag></template>
                  </div>
                </el-col>
                <el-col :span="2" style="padding: 0">
                  <el-button type="primary" @click="toSee(publishInfo.baseInfoId)">查看</el-button>
                </el-col>
              </el-row>
              <hr>
            </div>
          </template>
          <template v-else>
            暂无房源数据
          </template>
        </div>
        <el-pagination
          :hide-on-single-page="true"
          @current-change="currentChange"
          :page-size="pageSize"
          :current-page.sync="currentPage"
          layout="total, prev, pager, next"
          :total="totalCount">
        </el-pagination>
      </el-col>
    </el-row>
    <house-info v-if="houseInfoVisible" ref="houseInfo" @refreshDataList="getRefreshDataList"></house-info>
  </div>
</template>

<script>
// 这里可以导入其他文件（比如：组件，工具js，第三方插件js，json文件，图片文件等等）
// 例如：import 《组件名称》 from ‘《组件路径》‘;

import cityTree from '../../../components/tree/city-tree'
import houseInfo from '../../../components/info/house-info'

export default {
  // import引入的组件需要注入到对象中才能使用
  components: {cityTree, houseInfo},
  data () {
    // 这里存放数据
    return {
      dataForm: {
        key: '',
        cityId: ''
      },
      activeName: '-1',
      state: '',
      currentPage: 0,
      pageSize: 0,
      totalPage: 0,
      totalCount: 0,
      publishInfoList: [],
      houseInfoVisible: false
    }
  },
  // 方法集合
  methods: {
    currentChange (currentPage) {
      this.getPublishInfoList(currentPage)
    },
    // 获取cityTree子组件传递的值
    cityTreeMethod (value) {
      this.dataForm.cityId = value
      this.getPublishInfoList(1)
    },
    getAll () {
      this.dataForm.cityId = ''
      this.getPublishInfoList()
    },
    getPublishInfoList (current) {
      let state = this.state
      if (state < 0) state = null
      this.$http({
        url: this.$http.adornUrl('/house/houseInfo/getPublishInfoList'),
        method: 'post',
        data: this.$http.adornData({
          current,
          state,
          cityId: this.dataForm.cityId
        })
      }).then(({data}) => {
        if (data.page !== null) {
          console.log(data.page)
          this.currentPage = data.page.currPage
          this.pageSize = data.page.pageSize
          this.totalPage = data.page.totalPage
          this.totalCount = data.page.totalCount
          this.publishInfoList = data.page.list
        } else {
          this.currentPage = 0
          this.pageSize = 0
          this.totalPage = 0
          this.totalCount = 0
          this.publishInfoList = []
        }
      })
    },
    toSee (baseInfoId) {
      this.houseInfoVisible = true
      this.$nextTick(() => {
        this.$refs.houseInfo.init(baseInfoId)
      })
    },
    getRefreshDataList (value) {
      this.publishInfo(value.baseInfoId, value.state)
    },
    publishInfo (baseInfoId, state) {
      this.$http({
        url: this.$http.adornUrl(`/house/houseInfo/changePublishState/${baseInfoId}/${state}`),
        method: 'put'
      }).then(({data}) => {
        if (data && data.code === 0) {
          this.$message.success('操作成功')
          this.getPublishInfoList(1)
        }
      })
    },
    handleClick (tab, event) {
      this.state = tab.name
      this.getPublishInfoList(1)
    }
  },
  // 监听属性 类似于data概念
  computed: {},
  // 监控data中的数据变化
  watch: {},
  // 生命周期 - 创建完成（可以访问当前this实例）
  created () {
    this.getPublishInfoList(1)
  },
  // 生命周期 - 挂载完成（可以访问DOM元素）
  mounted () {
  },
  // 生命周期 - 创建之前
  beforeCreate () {
  },
  // 生命周期 - 挂载之前
  beforeMount () {
  },
  // 生命周期 - 更新之前
  beforeUpdate () {
  },
  // 生命周期 - 更新之后
  updated () {
  },
  // 生命周期 - 销毁之前
  beforeDestroy () {
  },
  // 生命周期 - 销毁完成
  destroyed () {
  },
  // 如果页面有keep-alive缓存功能，这个函数会触发
  activated () {
  }
}
</script>

<style scoped>

.div-house-info {
  color: #777;
  font-size: 12px;
  padding: 10px 0 5px 0;
}

.my-title {
  width: 100%;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

img {
  width: 100%;
  height: 100%;
}
</style>
