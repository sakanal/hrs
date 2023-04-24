<template>
  <div style="background-color: white;height: 100%;width: 100%">
    <el-row :gutter="20" style="width: 100%;padding: 20px">
      <template v-for="data in dataList">
        <el-col :span="8" style="margin: 0 0 20px 0">
          <el-card shadow="hover" :body-style="{ padding: '0px' }" class="my-divider-simple">
            <div slot="header" style="text-align: center">
              <span>{{ data.name }}</span>
            </div>
            <div style="padding: 15px;text-align: center">
              <div>
                <h1 style="font-size: 36px">
                  {{ data.number }}
                </h1>
              </div>
              <div>{{ data.description }}</div>
            </div>
            <el-divider></el-divider>
            <div style="padding: 15px;text-align: center">
              <el-row type="flex" justify="space-between">
                <el-col>
                  <b style="font-size: 20px">
                    ￥{{ data.price }}
                  </b>
                </el-col>
                <el-col>
                  <el-button type="text" @click="toBuy(data.id)">立即购买</el-button>
                </el-col>
              </el-row>
            </div>
          </el-card>
        </el-col>
      </template>
    </el-row>
    <el-pagination
      @current-change="currentChangeHandle"
      :current-page="page"
      :total="totalCount"
      :page-size="pageSize"
      layout="total, prev, pager, next"
      hide-on-single-page>
    </el-pagination>

    <div ref="alipayWap" v-html="alipay"/>
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
      page: 1,
      pageSize: 10,
      totalCount: 0,
      totalPage: 0,
      dataList: [],
      alipay: ''
    }
  },
  // 方法集合
  methods: {
    getDataList () {
      this.$axios.get(`/promotion/housepromotionfunction/list`, {
        params: {
          showState: 1,
          page: this.page,
          limit: 9
        }
      }).then(response => {
        this.totalCount = response.page.totalCount
        this.totalPage = response.page.totalPage
        this.pageSize = response.page.pageSize
        this.dataList = response.page.list
      })
    },
    // 当前页
    currentChangeHandle (val) {
      this.page = val
      this.getDataList()
    },
    toBuy (promotionId) {
      // alert(promotionId)
      // this.$router.push({
      //   name: 'business-service-payment',
      //   params: { promotionId }
      // })
      // 调用支付接口
      this.$axios.post(`/promotion/housepromotionorder/createOrder`, {
        userId: this.userInfo.id,
        promotionId: promotionId,
        number: '1'
      }).then((res) => {
        // 渲染支付页面
        this.alipay = res.pay
        // 防抖避免重复支付
        this.$nextTick(() => {
          // 提交支付表单
          this.$refs.alipayWap.children[0].submit()
          setTimeout(() => {
          }, 500)
        })
      })
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
          if (response && response.code === 0) {
            this.userInfo = response.userInfo
            this.getDataList()
          } else {
            this.$message.error(response.msg)
            this.$router.push({ path: '/login' })
          }
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
.my-divider-simple .el-divider--horizontal {
  margin: 1px 0 1px 0;
}
</style>
