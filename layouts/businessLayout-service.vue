<template>
  <el-container>
    <!--  NUXT header部分  -->
    <el-header height="90px">
      <el-row style="background-color: #fbfbfb;height: 30px;line-height: 30px" type="flex" justify="center">
        <el-col :span="6">
          <div class="grid-content bg-purple">
            logo
            <el-link :underline="false" href="/">首页</el-link>
          </div>
        </el-col>
        <el-col :span="7"/>
        <el-col :span="6">
          <el-menu class="my-el-menu-header" style="border: 0;background-color: #fbfbfb;height: 30px;line-height: 30px"
                   mode="horizontal" @select="handleSelect" :router="true">
            <el-menu-item style="height: 30px;line-height: 30px">
              <template v-if="userInfo.nickName === undefined">
                <el-link :underline="false" href="login">登录</el-link>
                <template>/</template>
                <el-link :underline="false" href="register">注册</el-link>
              </template>
              <template v-else>
                <el-link :underline="false" v-text="userInfo.nickName"></el-link>
                <template></template>
                <el-link :underline="false" @click="toLogout">退出</el-link>
              </template>
            </el-menu-item>
            <el-submenu index="2">
              <template slot="title" style="line-height: 30px">
                <el-link :underline="false" href="/user">个人中心</el-link>
              </template>
              <el-menu-item index="/user">我的信息</el-menu-item>
              <el-menu-item index="2-2">我的收藏</el-menu-item>
              <el-menu-item index="2-3">我的浏览</el-menu-item>
            </el-submenu>
            <el-submenu index="3">
              <template slot="title">
                <el-link :underline="false" href="/business">商家中心</el-link>
              </template>
              <el-menu-item index="/business">我的发布</el-menu-item>
              <el-menu-item index="3-2">账户资料</el-menu-item>
            </el-submenu>
          </el-menu>
        </el-col>
      </el-row>
      <el-row style="box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1)" type="flex" class="row-bg" justify="center">
        <el-col :span="6">
          <div class="grid-content bg-purple">
            <el-link :underline="false" href="/business">商家中心logo</el-link>
          </div>
        </el-col>
        <el-col :span="5"/>
        <el-col :span="8">
          <el-menu style="border: 0" mode="horizontal" :router="true" default-active="/business">
            <el-menu-item index="/business">首页</el-menu-item>
            <el-menu-item index="/business/info">账户资料</el-menu-item>
            <el-menu-item index="/business/fund">我的资金</el-menu-item>
            <el-menu-item index="/business/service">订购服务</el-menu-item>
            <el-menu-item index="5">一键发布</el-menu-item>
          </el-menu>
        </el-col>
      </el-row>
    </el-header>
    <!--  NUXT主体部分  -->
    <el-main style="background-color: #fbfbfb;margin-top: 10px;height: 100%">
      <el-row style="background-color: #f6f8fb;height: 100%" type="flex" class="row-bg" justify="center">
        <el-col :span="19" style="height: 100%;">
          <el-container>
            <el-aside style="height: 100%;">
              <el-row style="height: 100%;">
                <el-col :span="20" style="height: 100%;">
                  <el-menu :router="true"
                           style="min-height: 1000px"
                           default-active="/business/service"
                           active-text-color="#ffd04b"
                           background-color="#545c64"
                           text-color="#fff">
                    <el-submenu index="/business/service">
                      <template slot="title">
                        <span>订购服务</span>
                      </template>
                      <el-menu-item index="/business/service">我的服务</el-menu-item>
                      <el-menu-item index="/business/service">我的订单</el-menu-item>
                    </el-submenu>
                  </el-menu>
                </el-col>
              </el-row>
            </el-aside>
            <el-main>
              <Nuxt/>
            </el-main>
          </el-container>
        </el-col>
      </el-row>
    </el-main>
    <!--  NUXT Footer部分  -->
    <el-footer style="background-color: #eff2f7">
      footer
    </el-footer>
  </el-container>
</template>

<script>
// 这里可以导入其他文件（比如：组件，工具js，第三方插件js，json文件，图片文件等等）
// 例如：import 《组件名称》 from ‘《组件路径》‘;

import cookie from 'js-cookie'

export default {
  head() {
    return {
      title: '商家中心',
      link: [
        { rel: 'stylesheet', href:'/css/layout.css'},
      ]
    }
  },
  // import引入的组件需要注入到对象中才能使用
  components: {},
  data () {
    // 这里存放数据
    return {
      userInfo: {}
    }
  },
  // 方法集合
  methods: {
    toLogout () {
      // 退出
      cookie.remove('userToken')
      this.$router.go(0)
    },
    handleSelect (key, keyPath) {
      console.log(key, keyPath)
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
        })
    }else {
      this.$message.info('请先登录')
      this.$router.push({path:'/login'})
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
.el-divider {
  margin: 0;
}
.el-header {
  padding: 0;
}

.el-main {
  padding: 0;
}

.my-el-menu-header .el-submenu /deep/ .el-submenu__title {
  height: 30px;
  line-height: 30px;
}

.el-menu--horizontal > .el-menu-item {
  border-bottom: none;
  text-decoration: none;
}

</style>
