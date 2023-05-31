<template>
  <div style="text-align: center;">
    <div>
      <h2>欢迎加入旅社租赁平台</h2>
      <br>
      <h2>在这里您可以发布你所拥有的空闲房源</h2>
      <br>
      <h2>在这里您可以为已发布的房源购买推广数据</h2>
      <br>
      <h2>在这里您可以查看推广订单</h2>
      <br>
      <h2>希望在这里您可以感受到我们的诚意</h2>
    </div>
  </div>
</template>

<script>
// 这里可以导入其他文件（比如：组件，工具js，第三方插件js，json文件，图片文件等等）
// 例如：import 《组件名称》 from ‘《组件路径》‘;

export default {
  layout: 'businessLayout',
  // import引入的组件需要注入到对象中才能使用
  components: {},
  data () {
    // 这里存放数据
    return {}
  },
  // 方法集合
  methods: {},
  // 监听属性 类似于data概念
  computed: {},
  // 监控data中的数据变化
  watch: {},
  // 生命周期 - 创建完成（可以访问当前this实例）
  created () {
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
.el-main {
  padding: 0;
}
.el-container {
  flex-direction: row; /* 方向为纵向，保持正常的从上到下文档流 */
}

</style>
