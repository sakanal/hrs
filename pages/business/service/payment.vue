<template>
  <div class=''>
    <el-row type="flex" class="row-bg" justify="center">
      <el-col :span="19">
        支付页面
      </el-col>
    </el-row>
  </div>
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
      promotionId: '',
      userInfo: {}
    }
  },
  // 方法集合
  methods: {},
  // 监听属性 类似于data概念
  computed: {},
  // 监控data中的数据变化
  watch: {},
  // 生命周期 - 创建完成（可以访问当前this实例）
  created () {
    if (this.$route.params && this.$route.params.promotionId) {
      this.promotionId = this.$route.params.promotionId
      if (cookie.get('userToken')) {
        this.$axios.get('/user/login/userInfoByToken').then(response => {
          if (response && response.code === 0) {
            this.userInfo = response.userInfo
          } else {
            this.$message.error(response.msg)
            this.$router.push({ path: '/login' })
          }
        })
      } else {
        this.$router.push({ path: '/login' })
      }
    } else {
      this.$router.go(-1)
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

</style>
