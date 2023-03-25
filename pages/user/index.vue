<template>
  <div class=''>
    <div style="margin: 5px 0 20px 0">个人资料</div>
    <div>
      <el-tabs @tab-click="tabClick">
        <el-tab-pane label="本地上传"></el-tab-pane>
        <el-tab-pane label="系统头像"></el-tab-pane>
      </el-tabs>
      <div>
        <el-row>
          <el-col :sm="12" :lg="6">
            <el-result>
              <template slot="icon">
                <el-avatar :size="160" :src="userInfo.headPortraitUrl"></el-avatar>
              </template>
              <template slot="extra">
                <single-upload file-dir="avatar/" v-model="avatarImage" @singleUpload="singleUpload"/>
              </template>
            </el-result>
          </el-col>
          <el-col :sm="12" :lg="6">
            <el-result>
              <template slot="icon" v-if="avatarImage===''">
                <el-avatar :size="160" :src="userInfo.headPortraitUrl"></el-avatar>
              </template>
              <template slot="icon" v-else>
                <el-avatar :size="160" :src="avatarImage"></el-avatar>
              </template>
              <template slot="extra">
                <el-button type="primary" size="small" @click="saveAvatar">保存头像</el-button>
                <div slot="tip" class="el-upload__tip">头像预览</div>
              </template>
            </el-result>
          </el-col>
        </el-row>
        {{ userInfo }}
      </div>
    </div>
  </div>
</template>

<script>
// 这里可以导入其他文件（比如：组件，工具js，第三方插件js，json文件，图片文件等等）
// 例如：import 《组件名称》 from ‘《组件路径》‘;

import cookie from 'js-cookie'
import singleUpload from '@/components/upload/singleUpload'

export default {
  layout: 'userLayout',
  // import引入的组件需要注入到对象中才能使用
  components: { singleUpload },
  data () {
    // 这里存放数据
    return {
      userInfo: {},
      avatarImage: '',
      avatarTempUUID: ''
    }
  },
  // 方法集合
  methods: {
    tabClick (event) {
      this.tabNumber = event.name
      console.log(event.name)
    },
    singleUpload (value) {
      this.avatarImage = value
      this.$axios.post(`/user/avatar/temp`,{fileUrl: value}).then(response=>{
        this.avatarTempUUID=response.data
        console.log(response.data)
      })
    },
    saveAvatar() {
      this.$axios.post('/user/avatar/save', {uuid: this.avatarTempUUID,userId: this.userInfo.id.toString()}).then(response=>{
        if (response && response.code===0){
          this.$message.success('修改成功')
        }else {
          this.$message.error('修改失败')
        }
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
        .then(response => {
          this.userInfo = response.userInfo
        })
    } else {
      this.$message.info('请先登录')
      this.$router.push({ path: '/login' })
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
.el-avatar--circle /deep/ img {
  height: 100%;
  width: 100%;
}
</style>
