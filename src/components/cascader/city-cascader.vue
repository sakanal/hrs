<template>
  <div class="block">
    <el-cascader
      ref="cityCascader"
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
    cityIds: {
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
    // TODO 有时候会有bug，有时候会不显示数据，如果先点击修改县级数据，然后再点击修改修改市级数据，最后点击修改其他的县级数据，会回显失败
    init (cityLevel) {
      this.cityLevel = cityLevel || 0
      this.$nextTick(() => {
        // this.selectValue = []
        this.selectValue = this.cityIds
        this.$http({
          url: this.$http.adornUrl('/house/housecity/childrenList'),
          method: 'post'
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.options = data.data
            this.childrenHandler(cityLevel)
          } else {
            this.options = []
          }
        })
      })
    },
    childrenHandler (cityLevel) {
      if (cityLevel === '1') {
        // 当前设置的是市级城市，需要只显示省级城市
        for (let i = 0; i < this.options.length; i++) {
          this.options[i].childrenList = null
        }
      } else if (cityLevel === '2') {
        // 当前设置的是县级城市，需要只显示市级城市
        for (let i = 0; i < this.options.length; i++) {
          if (this.options[i].hasChildren) {
            for (let j = 0; j < this.options[i].childrenList.length; j++) {
              this.options[i].childrenList[j].childrenList = null
            }
          }
        }
      }
    },
    changeValue (value) {
      this.$emit('cityIdCascaderSelect', value)
      // this.$emit('cityNameCascaderSelect', this.$refs.cityCascader.currentLabels)
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
    // this.$emit('cityIdCascaderSelect', this.$refs.cityCascader.currentValue)
    this.$emit('cityNameCascaderSelect', this.$refs.cityCascader.currentLabels)
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
