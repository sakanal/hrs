<template>
  <div class=''>
    <el-tabs>
      <el-tab-pane label="修改密码"></el-tab-pane>
    </el-tabs>
    <el-row style="padding: 20px">
      <el-col :span="10">
        <el-form ref="passwordFormRef" :rules="rules" :model="passwordForm" label-width="100px" label-position="left">
          <el-form-item label="旧密码" prop="oldPassword">
            <el-input v-model="passwordForm.oldPassword" placeholder="请输入旧密码" show-password></el-input>
          </el-form-item>
          <el-form-item label="新密码" prop="newPassword">
            <el-input v-model="passwordForm.newPassword" placeholder="请输入新密码" show-password></el-input>
          </el-form-item>
          <el-form-item label="确认密码" prop="checkPassword">
            <el-input v-model="passwordForm.checkPassword" placeholder="请输入确认密码" show-password></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="changePassword">修改密码</el-button>
          </el-form-item>
        </el-form>
      </el-col>
    </el-row>
  </div>
</template>

<script>
// 这里可以导入其他文件（比如：组件，工具js，第三方插件js，json文件，图片文件等等）
// 例如：import 《组件名称》 from ‘《组件路径》‘;

import cookie from 'js-cookie'

export default {
  layout: 'userLayout',
  // import引入的组件需要注入到对象中才能使用
  components: {},
  data () {
    const validatePass = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请再次输入密码'))
      } else if (value !== this.passwordForm.newPassword) {
        callback(new Error('两次输入密码不一致!'))
      } else {
        callback()
      }
    }
    // 这里存放数据
    return {
      userInfo: {},
      passwordForm: {
        oldPassword: '',
        newPassword: '',
        checkPassword: ''
      },
      rules: {
        oldPassword: [
          {
            required: true,
            message: '请输入旧密码',
            trigger: 'blur'
          },
          {
            min: 6,
            max: 16,
            message: '长度在 6 到 16 个字符',
            trigger: 'blur'
          }
        ],
        newPassword: [
          {
            required: true,
            message: '请输入新密码',
            trigger: 'blur'
          },
          {
            min: 6,
            max: 16,
            message: '长度在 6 到 16 个字符',
            trigger: 'blur'
          }
        ],
        checkPassword: [
          {
            required: true,
            message: '请输入确认密码',
            trigger: 'blur'
          },
          {
            min: 6,
            max: 16,
            message: '长度在 6 到 16 个字符',
            trigger: 'blur'
          },
          {
            validator: validatePass,
            trigger: 'blur'
          }
        ]
      }
    }
  },
  // 方法集合
  methods: {
    changePassword () {
      this.$refs.passwordFormRef.validate((valid) => {
        if (valid) {
          let data = {
            userId: this.userInfo.id,
            oldPassword: this.passwordForm.oldPassword,
            newPassword: this.passwordForm.newPassword,
            checkPassword: this.passwordForm.checkPassword
          }
          this.$axios.put('/user/userbaseinfo/changePassword',data).then(response => {
            if (response && response.code === 0) {
              this.$message.success('修改成功')
              cookie.remove('userToken')
              this.$router.push({ path: '/login' })
            }
          })
        } else {
          this.$message.error('请输入正确信息')
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
          if (response && response.code === 0) {
            this.userInfo = response.userInfo
          } else {
            this.$router.push({ path: '/login' })
          }
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

</style>
