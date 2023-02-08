<template>
  <div>
    <el-input
      placeholder="输入关键字进行过滤"
      v-model="filterText"
      clearable>
    </el-input>
    <el-tree
      node-key="id"
      :props="props"
      :data="dataList"
      :filter-node-method="filterNode"
      @node-click="nodeClick"
      accordion
      ref="tree">
    </el-tree>
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
    return {
      dataList: [],
      props: {
        label: 'name',
        children: 'childrenList'
      },
      filterText: ''
    }
  },
  // 方法集合
  methods: {
    // 获取数据列表
    getDataList () {
      this.dataListLoading = true
      this.$http({
        url: this.$http.adornUrl('/house/housecity/childrenList'),
        method: 'post'
      }).then(({data}) => {
        if (data && data.code === 0) {
          this.dataList = data.data
        } else {
          this.dataList = []
        }
      })
    },
    // 根据关键字过滤数据
    filterNode (value, data, node) {
      let names = this.getParents(node, node.data.name, 'name')
      let isName = names.indexOf(value) !== -1
      return !value || isName
    },
    getParents (node, name, key) {
      if (node.parent && node.parent.data[key]) {
        name += node.parent.data[key]
        return this.getParents(node.parent, name, key)
      }
      return name
    },
    nodeClick (data, node) {
      this.$emit('cityTreeEvent', data.id)
    }
  },
  // 监听属性 类似于data概念
  computed: {},
  // 监控data中的数据变化
  watch: {
    filterText (value) {
      this.$refs.tree.filter(value)
    }
  },
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
    this.getDataList()
  }
}
</script>

<style scoped>

</style>
