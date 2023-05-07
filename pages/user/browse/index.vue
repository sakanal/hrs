<template>
  <div class=''>
    <el-row>
      <el-col :span="24" style="padding-right: 20px">
        <template v-if="publishInfoList.length>0">
          <div v-for="publishInfo in publishInfoList">
            <el-row :gutter="20" class="my-house-info">
              <el-col :span="5">
                <div>
                  <el-image
                    style="width: 200px; height: 120px;margin-top: 5px"
                    :src="publishInfo.url"
                    :fit="'cover'"
                    :preview-src-list="publishInfo.imageList"></el-image>
                </div>
              </el-col>
              <el-col :span="15">
                <div class="my-title">
                  <el-link :underline="false" :href="`/house/${publishInfo.baseInfoId}`">
                    <span style="font-size: 18px;text-overflow: ellipsis;">
                    {{ publishInfo.houseTitle }}
                    </span>
                  </el-link>
                </div>
                <div class="div-house-info">
                  <template v-if="publishInfo.hallNumber>0">{{ publishInfo.hallNumber }}厅</template>
                  <template v-if="publishInfo.roomNumber>0">{{ publishInfo.roomNumber }}室</template>
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
              <el-col :span="4" style="font-size: 16px;color: red">
                <span v-if="publishInfo.monthlyRent>0">
                  <span style="font-size: 24px">
                    {{ publishInfo.monthlyRent }}
                  </span>
                  元/月
                </span>
                <template v-else>面议</template>
              </el-col>
            </el-row>
            <el-divider></el-divider>
          </div>
          <el-pagination
            :hide-on-single-page="true"
            @current-change="currentChange"
            :page-size="pageSize"
            :current-page.sync="currentPage"
            layout="total, prev, pager, next"
            :total="totalCount">
          </el-pagination>
        </template>
        <template v-else>
          <el-empty description="暂无浏览记录"></el-empty>
        </template>
      </el-col>
    </el-row>
  </div>
</template>

<script>
// 这里可以导入其他文件（比如：组件，工具js，第三方插件js，json文件，图片文件等等）
// 例如：import 《组件名称》 from ‘《组件路径》‘;

import cookie from 'js-cookie'

export default {
  layout: 'userLayout',
  // import引入的组件需要注入到对象中才能使用
  components: {},
  data () {
    // 这里存放数据
    return {
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
    getMyBrowseList (current = 1) {
      this.$axios.get(`/user/userbrowse/myBrowse/${current}`).then(response => {
        if (response.page !== null) {
          this.currentPage = response.page.currPage
          this.pageSize = response.page.pageSize
          this.totalPage = response.page.totalPage
          this.totalCount = response.page.totalCount
          this.publishInfoList = response.page.list
        } else {
          this.currentPage = 0
          this.pageSize = 0
          this.totalPage = 0
          this.totalCount = 0
          this.publishInfoList = []
        }
      })
    },
    currentChange (currentPage) {
      this.getMyBrowseList(currentPage)
    },
  },
  // 监听属性 类似于data概念
  computed: {},
  // 监控data中的数据变化
  watch: {},
  // 生命周期 - 创建完成（可以访问当前this实例）
  created () {
    if (cookie.get('userToken')) {
      this.getMyBrowseList()
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
.div-house-info {
  color: #777;
  font-size: 12px;
  padding: 12px 0 6px 0;
}

.my-title {
  width: 100%;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.el-divider--horizontal {
  margin: 12px 0;
}

.my-house-info:hover {
  background-color: rgba(204, 204, 204, 0.1);
}

.my-house-info:hover span {
  color: red;
}
</style>
