<template>
  <el-container>
    <!--  NUXT header部分  -->
    <el-header height="120px">
      <el-row style="background-color: #fbfbfb;height: 30px;line-height: 30px" type="flex" class="row-bg" justify="center">
        <el-col :span="6">
          <div class="grid-content bg-purple">
            {{cityName}} [<nuxt-link style="text-decoration: none" to="/house/location">切换城市</nuxt-link>]
          </div>
        </el-col>
        <el-col :span="7"/>
        <el-col :span="6" class=" my-el-menu">
          <el-menu class="my-el-menu-header" style="border: 0;background-color: #fbfbfb;height: 30px;line-height: 30px" mode="horizontal" @select="handleSelect" :router="true">
            <el-menu-item>
              <template v-if="userInfo.nickName === undefined">
                <el-link :underline="false" href="/login">登录</el-link>
                <template>/</template>
                <el-link :underline="false" href="/register">注册</el-link>
              </template>
              <template v-else>
                <el-link :underline="false" v-text="userInfo.nickName"></el-link>
                <template> </template>
                <el-link :underline="false" @click="toLogout">退出</el-link>
              </template>
            </el-menu-item>
            <el-submenu index="2">
              <template slot="title">
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
      <el-divider/>
      <el-row style="background-color: #fbfbfb;height: 90px;line-height: 90px" type="flex" class="row-bg" justify="center">
        <el-col :span="6">
          <div>
            logo
            <el-link :underline="false" href="/">首页</el-link>
          </div>
        </el-col>
        <el-col :span="7"></el-col>
        <el-col :span="6">
<!--          <div style="height: 100%;width: 100%">-->
<!--            <nuxt-link to="/business/publish/toPublish">免费发布</nuxt-link>-->
<!--          </div>-->
        </el-col>
      </el-row>
    </el-header>
    <!--  NUXT主体部分  -->
    <el-divider/>
    <el-main>
      <el-row type="flex" class="row-bg" justify="center">
        <el-col :span="24">
          <Nuxt/>
        </el-col>
      </el-row>
    </el-main>
    <el-divider/>
    <!--  NUXT Footer部分  -->
    <el-footer>
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
      title: '旅社租赁',
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
      userInfo: {},
      cityName: '',
    }
  },
  // 方法集合
  methods: {
    toLogout() {
      // 退出
      cookie.remove('userToken')
      this.$router.go(0)
    },
    handleSelect(key, keyPath) {
      console.log(key, keyPath);
    },
    getLocation () {
      const key = 'SKJBZ-7SRRT-H45XT-V4KYJ-6OKUV-B7BFG'
      const url = 'https://apis.map.qq.com/ws/location/v1/ip'
      this.$jsonp(url, {
        key,
        output: 'jsonp'
      }).then(res => {
        this.cityName = res.result.ad_info.city
        cookie.set('cityName',res.result.ad_info.city,{domain: 'localhost'})
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
        .then(response=>{
          this.userInfo = response.userInfo
        })
    }
  },
  // 生命周期 - 挂载完成（可以访问DOM元素）
  mounted () {
    let cityName = cookie.get('cityName')
    if (!cityName){
      this.getLocation()
    }else {
      this.cityName = cityName
    }
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
  line-height: 30px ;
}
.my-el-menu .el-menu--horizontal>.el-menu-item{
  height: 30px;
  line-height: 30px;
}

</style>
