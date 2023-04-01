<template>
  <div class="main">
    <!--    <div class="background">-->
    <!--      <img :src="imgSrc" width="100%" height="100%" alt="" />-->
    <!--    </div>-->

    <el-row type="flex" class="row-bg" justify="center">
      <el-col :span="5">
        <div></div>
      </el-col>
      <el-col :span="8">
        <div class="simple-title">
          <el-link :underline="false" href="/">返回首页</el-link>
          <el-link :underline="false" href="/login" style="float: right">已有账号？去登录</el-link>
        </div>
        <el-card>
          <div slot="header" class="title">
            <span>logo</span>
          </div>
          <div>
            <el-form ref="registerForm" :model="register" :rules="rules" status-icon label-width="0px"
                     class="demo-ruleForm">
              <el-form-item prop="phone">
                <el-input v-model="register.phone" placeholder="手机号" auto-complete="false">
                  <template slot="append">
                    <template v-if="!hasCode">
                      <el-button @click="getCode">获取动态码</el-button>
                    </template>
                    <template v-else>
                      <span>重新获取({{time}})</span>
                    </template>
                  </template>
                </el-input>
              </el-form-item>
              <el-form-item prop="code">
                <el-input v-model="register.code" placeholder="动态码" auto-complete="false"></el-input>
              </el-form-item>
              <el-form-item prop="userName">
                <el-input v-model="register.userName" placeholder="用户名" autocomplete="off"/>
              </el-form-item>
              <el-form-item prop="password">
                <el-input type="password" v-model="register.password" placeholder="设置密码" autocomplete="off"/>
              </el-form-item>
              <el-form-item prop="checkPassword">
                <el-input type="password" v-model="register.checkPassword" placeholder="确认密码" autocomplete="off"/>
              </el-form-item>
              <el-form-item size="medium">
                <el-button type="primary" style="min-width: 100%" @click="toRegister">注册</el-button>
              </el-form-item>
            </el-form>
          </div>
        </el-card>
      </el-col>
      <el-col :span="5">
        <div></div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
// 这里可以导入其他文件（比如：组件，工具js，第三方插件js，json文件，图片文件等等）
// 例如：import 《组件名称》 from ‘《组件路径》‘;

export default {
  layout: 'login-register',
  // import引入的组件需要注入到对象中才能使用
  components: {},
  data: function () {
    const validatePass = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请再次输入密码'))
      } else if (value !== this.register.password) {
        callback(new Error('两次输入密码不一致!'))
      } else {
        callback()
      }
    }
    const validateName = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入姓名'))
      }else{
        this.$axios.get(`/user/register/checkUserName/${value}`)
          .then(response => {
            if (!response.result) {
              callback(new Error('用户名重复'))
            } else {
              callback()
            }
          })
      }
    }
    const validatePhone = (rule, value, callback) => {
      const pattern = /^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\d{8}$/
      if (value === '') {
        callback(new Error('请输入手机号'))
      } else if (!pattern.test(value)) {
        callback(new Error('请输入有效手机号'))
      } else {
        callback()
      }
    }
    const validateCode = (rule, value, callback) => {
      const pattern = /^\d{6}$/
      if (value === '') {
        callback(new Error('请输入动态码'))
      } else if (!pattern.test(value)) {
        callback(new Error('请输入有效动态码'))
      } else {
        callback()
      }
    }
    return {
      imgSrc: require('../static/image/login.jpg'),
      checkUserName: false,
      hasCode: false,
      time: 60,
      register: {
        phone: '',
        code: '',
        userName: '',
        password: '',
        checkPassword: ''
      },
      rules: {
        phone: [
          {
            required: true,
            message: '请输入手机号',
            trigger: 'blur'
          },
          {
            validator: validatePhone,
            trigger: 'blur'
          }
        ],
        code: [
          {
            required: true,
            message: '请输入动态码',
            trigger: 'blur'
          },
          {
            validator: validateCode,
            trigger: 'blur'
          }
        ],
        userName: [
          {
            required: true,
            message: '请输入用户名',
            trigger: 'blur'
          },
          {
            validator: validateName,
            trigger: 'blur'
          }
        ],
        password: [
          {
            required: true,
            message: '请输入密码',
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
  methods: {
    getCode () {
      this.$refs.registerForm.validateField('phone', (valid) => {
        if (!valid && !this.hasCode) {
          this.$axios.get(`/user/register/sendCode/${this.register.phone}`).then(response => {
            if (response && response.code === 0) {
              this.$message.success('动态码发送成功')
              this.time=60
              this.timer()
              this.hasCode = true
            } else {
              this.$message.error(response.msg)
            }
          })
        }
      })
    },
    //发送手机验证码倒计时
    timer() {
      if (this.time > 1) {
        this.time--;
        setTimeout(this.timer, 1000);
      } else{
        this.time=0;
        this.hasCode=false
      }
    },
    toRegister () {
      this.$refs.registerForm.validate((valid) => {
        if (valid) {
          let data = {
            'phone': this.register.phone,
            'code': this.register.code,
            'userName': this.register.userName,
            'password': this.register.password
          }
          this.$axios.post('/user/register/simple', data)
            .then(response => {
              if (response === undefined) {
                this.$refs.registerForm.resetFields()
              } else if (response.code === 0) {
                this.$message.success(response.msg)
                this.$router.push({
                    name:'login',
                    params: {
                      userName: this.register.userName,
                      password: this.register.password
                    }
                  }
                )
              }
            })
        } else {
          this.$message.error('请正确输入注册信息！')
          return false
        }
      })
    },
    handleClick (tab, event) {
      console.log(tab, event)
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
