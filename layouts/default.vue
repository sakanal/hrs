<template>
  <el-container>
    <!--  NUXT header部分  -->
    <el-header>
      <el-row type="flex" class="row-bg" justify="center">
        <el-col :span="6">
          <div class="grid-content bg-purple">
            logo
            <el-link :underline="false" href="/">首页</el-link>
          </div>
        </el-col>
        <el-col :span="8"/>
        <el-col :span="6">
          <el-menu class="el-menu-demo" mode="horizontal" @select="handleSelect">
            <el-menu-item>
              <template v-if="userInfo.nickName === undefined">
                <el-link :underline="false" href="login">登录</el-link>
                <template>/</template>
                <el-link :underline="false" href="register">注册</el-link>
              </template>
              <template v-else>
                <el-link :underline="false" v-text="userInfo.nickName"></el-link>
                <template> </template>
                <el-link :underline="false" @click="toLogout">退出</el-link>
              </template>
            </el-menu-item>
            <el-submenu index="2">
              <template slot="title">个人中心</template>
              <el-menu-item index="2-1">我的信息</el-menu-item>
              <el-menu-item index="2-2">我的收藏</el-menu-item>
              <el-menu-item index="2-3">我的浏览</el-menu-item>
            </el-submenu>
            <el-submenu index="3">
              <template slot="title">商家中心</template>
              <el-menu-item index="3-1">我的发布</el-menu-item>
              <el-menu-item index="3-2">账户资料</el-menu-item>
            </el-submenu>
          </el-menu>
        </el-col>
      </el-row>
    </el-header>
    <!--  NUXT主体部分  -->
    <el-divider/>
    <el-main>
      <Nuxt/>
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
    toLogout() {
      // 退出
      cookie.remove('userToken')
      this.$router.go(0)
    },
    handleSelect(key, keyPath) {
      console.log(key, keyPath);
    }
  },
  // 监听属性 类似于data概念
  computed: {},
  // 监控data中的数据变化
  watch: {},
  // 生命周期 - 创建完成（可以访问当前this实例）
  created () {
    if (cookie.get('userToken')) {
      this.$axios.get('/user/userbaseinfo/userInfoByToken')
        .then(response=>{
          this.userInfo = response.userInfo
        })
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

<style>
* {
  box-sizing: border-box;
  margin: 0;
  padding: 0;
}
html, body{
  height: 100%;
}
body #__nuxt,#__layout {
  height: 100%;
}

.el-header {
  /*background-color: #B3C0D1;*/
  color: #333;
  /*text-align: center;*/
  line-height: 60px;
  padding: 0;
}

.el-aside {
  /*background-color: #D3DCE6;*/
  color: #333;
  /*text-align: center;*/
  /*line-height: 200px;*/
}

.el-main {
  /*background-color: #E9EEF3;*/
  color: #333;
  margin: 0;
  padding: 0;
  /*text-align: center;*/
  /*line-height: 160px;*/
}

.el-footer {
  /*background-color: #B3C0D1;*/
  color: #333;
  text-align: center;
  /*line-height: 60px;*/
  margin-top: auto; /* 设置footer的上外边距为auto */
  padding: 0;
}
.el-divider {
  margin: 0;
}


body > .el-container {
  margin-bottom: 40px;
}

.el-container {
  height: 100%;
  flex-direction: column; /* 方向为纵向，保持正常的从上到下文档流 */
}

.el-container:nth-child(5) .el-aside,
.el-container:nth-child(6) .el-aside {
  line-height: 260px;
}

.el-container:nth-child(7) .el-aside {
  line-height: 320px;
}
</style>
