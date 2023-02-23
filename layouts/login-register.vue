<template>
  <div class=''>
    <div><br><br><br></div>
    <Nuxt/>
  </div>
</template>

<script>
// 这里可以导入其他文件（比如：组件，工具js，第三方插件js，json文件，图片文件等等）
// 例如：import 《组件名称》 from ‘《组件路径》‘;

export default {
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

<style>
.simple-title{
  margin-bottom: 5px;
}

.background {
  height: 100%;
  width: 100%;
  z-index: -1;
  padding: 0;
  margin: 0;
  position: absolute;
  left: 0;
  top: 0;
  bottom: 0;
  right: 0;
  /*background-image: "../static/image/login.jpg";*/
}
.front {
  z-index: 1;
  position: absolute;
  left: 0;
  right: 0;
  top: 0;
  bottom: 0;
  display: flex;
  justify-content: center;
  align-items: center;
  /*color: #ffffff;*/
}
.card {
  width: 400px;
  /*background-color: rgba(200, 200, 200, 1); !**rgba中的a为alpha通道， 为不透明参数，.0即为完全透明*!*/
  /*border-color: rgba(200, 200, 200, 1);*/
  border-radius: 2px;
  z-index: 2;
}
.main .title {
  text-align: center;
  font-size: 24px;
  /*color: #ffffff;*/
}
</style>
