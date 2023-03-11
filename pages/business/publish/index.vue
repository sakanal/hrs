<template>
  <div class='' style="height: 100%">
    <el-tabs style="height: 100%" type="border-card" @tab-click="tabClick" :value="tabNumber">
      <el-tab-pane label="显示中" name="2">
        <div>
          <div v-for="publishInfo in publishInfoList">
            <el-row :gutter="20">
              <el-col :span="6">
                <div>
                  <el-image
                    style="width: 200px; height: 110px"
                    :src="publishInfo.url"
                    :fit="'cover'"
                    :preview-src-list="publishInfo.imageList"></el-image>
                </div>
              </el-col>
              <el-col :span="14">
                <div>
                  <template v-text="publishInfo.houseTitle">房源标题</template>
                </div>
                <div class="div-house-info">
                  <template v-if="publishInfo.roomNumber>0">{{publishInfo.roomNumber}}室</template>
                  <template v-if="publishInfo.hallNumber>0">{{publishInfo.hallNumber}}厅</template>
                  <template v-if="publishInfo.cloakroomNumber>0">{{publishInfo.cloakroomNumber}}卫</template>
                  <template v-if="publishInfo.areaCovered>0">{{publishInfo.areaCovered}}㎡</template>
                </div>
                <div class="div-house-info">
                  <template v-if="publishInfo.roadName!==null">{{publishInfo.roadName}}</template>
                  <template v-if="publishInfo.areaName!==null">{{publishInfo.areaName}}</template>
                </div>
                <div class="div-house-info">
                  <template v-if="publishInfo.publisherIdentity===0">来自个人房源</template>
                </div>
              </el-col>
              <el-col :span="4" style="font-size: 16px;margin-top: 5%;color: red">
                <span v-if="publishInfo.monthlyRent>0">
                  <span style="font-size: 24px">
                    {{publishInfo.monthlyRent}}
                  </span>
                  元/月
                </span>
                <template v-else>面议</template>
              </el-col>
            </el-row>
            <el-divider></el-divider>
          </div>
        </div>
      </el-tab-pane>
      <el-tab-pane label="审核中" name="1">
        <div>
          <div v-for="publishInfo in publishInfoList">
            <el-row :gutter="20">
              <el-col :span="6">
                <div>
                  <el-image
                    style="width: 200px; height: 110px"
                    :src="publishInfo.url"
                    :fit="'cover'"
                    :preview-src-list="publishInfo.imageList"></el-image>
                </div>
              </el-col>
              <el-col :span="14">
                <div>
                  <template v-text="publishInfo.houseTitle">房源标题</template>
                </div>
                <div class="div-house-info">
                  <template v-if="publishInfo.roomNumber>0">{{publishInfo.roomNumber}}室</template>
                  <template v-if="publishInfo.hallNumber>0">{{publishInfo.hallNumber}}厅</template>
                  <template v-if="publishInfo.cloakroomNumber>0">{{publishInfo.cloakroomNumber}}卫</template>
                  <template v-if="publishInfo.areaCovered>0">{{publishInfo.areaCovered}}㎡</template>
                </div>
                <div class="div-house-info">
                  <template v-if="publishInfo.roadName!==null">{{publishInfo.roadName}}</template>
                  <template v-if="publishInfo.areaName!==null">{{publishInfo.areaName}}</template>
                </div>
                <div class="div-house-info">
                  <template v-if="publishInfo.publisherIdentity===0">来自个人房源</template>
                </div>
              </el-col>
              <el-col :span="4" style="font-size: 16px;margin-top: 5%;color: red">
                <span v-if="publishInfo.monthlyRent>0">
                  <span style="font-size: 24px">
                    {{publishInfo.monthlyRent}}
                  </span>
                  元/月
                </span>
                <template v-else>面议</template>
                <el-button type="danger" @click="toChangeHouseInfo(publishInfo.baseInfoId)">我要修改</el-button>
              </el-col>
            </el-row>
            <el-divider></el-divider>
          </div>
        </div>
      </el-tab-pane>
      <el-tab-pane label="已下架" name="3">
        <div>
          <div v-for="publishInfo in publishInfoList">
            <el-row :gutter="20">
              <el-col :span="6">
                <div>
                  <el-image
                    style="width: 200px; height: 110px"
                    :src="publishInfo.url"
                    :fit="'cover'"
                    :preview-src-list="publishInfo.imageList"></el-image>
                </div>
              </el-col>
              <el-col :span="14">
                <div>
                  <template v-text="publishInfo.houseTitle">房源标题</template>
                </div>
                <div class="div-house-info">
                  <template v-if="publishInfo.roomNumber>0">{{publishInfo.roomNumber}}室</template>
                  <template v-if="publishInfo.hallNumber>0">{{publishInfo.hallNumber}}厅</template>
                  <template v-if="publishInfo.cloakroomNumber>0">{{publishInfo.cloakroomNumber}}卫</template>
                  <template v-if="publishInfo.areaCovered>0">{{publishInfo.areaCovered}}㎡</template>
                </div>
                <div class="div-house-info">
                  <template v-if="publishInfo.roadName!==null">{{publishInfo.roadName}}</template>
                  <template v-if="publishInfo.areaName!==null">{{publishInfo.areaName}}</template>
                </div>
                <div class="div-house-info">
                  <template v-if="publishInfo.publisherIdentity===0">来自个人房源</template>
                </div>
              </el-col>
              <el-col :span="4" style="font-size: 16px;margin-top: 5%;color: red">
                <span v-if="publishInfo.monthlyRent>0">
                  <span style="font-size: 24px">
                    {{publishInfo.monthlyRent}}
                  </span>
                  元/月
                </span>
                <template v-else>面议</template>
              </el-col>
            </el-row>
            <el-divider></el-divider>
          </div>
        </div>
      </el-tab-pane>

      <el-pagination
        :hide-on-single-page="true"
        @current-change="currentChange"
        :page-size="pageSize"
        :current-page.sync="currentPage"
        layout="total, prev, pager, next"
        :total="totalCount">
      </el-pagination>
    </el-tabs>
  </div>
</template>

<script>
// 这里可以导入其他文件（比如：组件，工具js，第三方插件js，json文件，图片文件等等）
// 例如：import 《组件名称》 from ‘《组件路径》‘;

import cookie from 'js-cookie'

export default {
  layout: 'businessLayout',
  // import引入的组件需要注入到对象中才能使用
  components: {},
  data () {
    // 这里存放数据
    return {
      userInfo: {},
      tabNumber: '2',
      // 当前页
      currentPage: 0,
      // 页面尺寸
      pageSize: 0,
      // 总页数
      totalPage: 0,
      // 总记录数
      totalCount: 0,
      publishInfoList: [],
    }
  },
  // 方法集合
  methods: {
    tabClick(event){
      this.tabNumber = event.name
      this.getPublishInfoList(1,event.name)
    },
    currentChange(currentPage){
      this.getPublishInfoList(currentPage,this.tabNumber)
    },
    getPublishInfoList (current, state) {
      let publishId = this.userInfo.id
      this.$axios.get(`/house/housebaseinfo/getPublishInfoList/${publishId}`, {
        params: {
          state,
          current
        }
      }).then(response => {
        if (response.page!==null){
          this.currentPage = response.page.currPage
          this.pageSize = response.page.pageSize
          this.totalPage = response.page.totalPage
          this.totalCount = response.page.totalCount
          this.publishInfoList = response.page.list
        }else {
          this.currentPage = 0
          this.pageSize = 0
          this.totalPage = 0
          this.totalCount = 0
          this.publishInfoList = []
        }
      })
    },
    toChangeHouseInfo(houseBaseInfoId){
      this.$router.push({path:`/business/publish/update/${houseBaseInfoId}`})
    }
  },
  // 监听属性 类似于data概念
  computed: {},
  // 监控data中的数据变化
  watch: {},
  // 生命周期 - 创建完成（可以访问当前this实例）
  created () {
    if (cookie.get('userToken')) {
      this.$axios.get('/user/login/userInfoByToken')
        .then(response => {
          this.userInfo = response.userInfo
          this.getPublishInfoList(1,2)
        })
    } else {
      this.$router.push({ path: '/login' })
    }
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
.div-house-info{
  color: #777;
  font-size: 12px;
  padding: 10px 0 5px 0;
}
</style>
