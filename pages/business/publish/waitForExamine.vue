<template>
  <div class=''>
    <el-row type="flex" class="row-bg" justify="center">
      <el-col :span="19">
        <el-card style="margin: 20px 0 20px 0">
          <el-steps :active="2" finish-status="success" simple>
            <el-step icon="el-icon-map-location" title="房源定位"></el-step>
            <el-step icon="el-icon-edit" title="填写信息"></el-step>
            <el-step icon="el-icon-upload" title="等待审核"></el-step>
          </el-steps>
          <div style="background-color: #fffbe4; color: #ec4514; padding: 10px;margin-top: 10px">
            为共建真实可信的生活服务平台，所有新发布的信息需要进行认证才能展现，认证方式包括：支付认证或芝麻信用认证
            <br>
            请如实填写信息，如有虚假会有账号封禁及扣除保证金等处罚
          </div>
        </el-card>
        <el-card style="margin: 20px 0 60px 0;text-align: center;">
          <el-result icon="success" title="信息发布成功" subTitle="请等待管理员审核">
            <template slot="extra">
              <el-button type="primary" size="medium" @click="$router.push({path:'/business/publish'})">返回</el-button>
            </template>
          </el-result>
        </el-card>
      </el-col>
    </el-row>
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

<style scoped>
</style>
