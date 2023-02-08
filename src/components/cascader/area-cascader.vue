<template>
  <div class="block">
    <el-cascader
      ref="areaCascader"
      v-model="selectValue"
      :options="options"
      :props="configOption"
      @change="changeValue"
      expand-trigger="hover"
      clearable>
    </el-cascader>
  </div>
</template>

<script>
// 这里可以导入其他文件（比如：组件，工具js，第三方插件js，json文件，图片文件等等）
// 例如：import 《组件名称》 from ‘《组件路径》‘;

export default {
  props: {
    areaIds: {
      type: Array,
      required: false
    }
  },
  // import引入的组件需要注入到对象中才能使用
  components: {},
  data () {
    // 这里存放数据
    return {
      selectValue: [],
      options: [],
      configOption: {
        label: 'name',
        value: 'id',
        children: 'childrenList'
      }
    }
  },
  // 方法集合
  methods: {
    init (cityId, filterFlag = false) {
      this.$nextTick(() => {
        this.selectValue = this.areaIds
        this.$http({
          url: this.$http.adornUrl('/house/housearea/childrenList'),
          method: 'post',
          data: this.$http.adornData({
            'cityId': cityId
          })
        }).then(({data}) => {
          this.options = data.data
          this.$nextTick(() => {
            if (filterFlag) {
              for (let i = 0; i < this.options.length; i++) {
                if (this.options[i].hasChildren) {
                  this.options[i].childrenList = null
                }
              }
            }
          })
        })
      })
    },
    changeValue (value) {
      this.$emit('areaIdCascaderSelect', value)
      // this.$emit('areaNameCascaderSelect', this.$refs.areaCascader.currentLabels)
    }
  },
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
    console.log(this.$refs.areaCascader.currentValue)
    // this.$emit('areaIdCascaderSelect', this.$refs.areaCascader.currentValue)
    this.$emit('areaNameCascaderSelect', this.$refs.areaCascader.currentLabels)
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
