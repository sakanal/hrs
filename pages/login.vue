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
          <el-link :underline="false" href="/register" style="float: right">没有账号？去注册</el-link>
        </div>
        <el-card>
          <div slot="header" style="text-align: center" class="title">
            <span>用户登录</span>
          </div>
          <div>
            <el-tabs v-model="activeName" @tab-click="handleClick" stretch>
              <!--账号密码登录-->
              <el-tab-pane label="账号密码登录" name="simple">
                <el-form ref="simpleLoginForm" :model="simpleLogin" :rules="simpleRules" status-icon label-width="0px"
                         class="demo-ruleForm">
                  <el-form-item prop="userName">
                    <el-input v-model="simpleLogin.userName" placeholder="用户名" autocomplete="off"/>
                  </el-form-item>
                  <el-form-item prop="password">
                    <el-input type="password" v-model="simpleLogin.password" placeholder="密码" autocomplete="off"/>
                  </el-form-item>
                  <el-form-item>
                    <el-checkbox-group v-model="simpleLogin.autoLogin">
                      <el-checkbox label="autoLogin">7天免登录</el-checkbox>
                      <el-link :underline="false" href="http://localhost:8001" style="float: right">我是管理员</el-link>
                    </el-checkbox-group>
                  </el-form-item>
                  <el-form-item size="medium">
                    <el-button type="primary" style="min-width: 100%" @click="toSimpleLogin">登录</el-button>
                  </el-form-item>
                </el-form>
              </el-tab-pane>
              <!--手机验证码登录-->
              <el-tab-pane label="手机验证码登录" name="phone">
                <el-form ref="phoneLoginForm" :model="phoneLogin" :rules="phoneRules" status-icon label-width="0px" class="demo-ruleForm">
                  <el-form-item prop="phone">
                    <el-input v-model="phoneLogin.phone" placeholder="手机号" auto-complete="false">
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
                  <el-form-item prop="password">
                    <el-input  v-model="phoneLogin.code" placeholder="验证码" autocomplete="off"/>
                  </el-form-item>
                  <el-form-item>
                    <el-checkbox-group v-model="simpleLogin.autoLogin">
                      <el-checkbox label="autoLogin">7天免登录</el-checkbox>
                      <el-link :underline="false" href="http://localhost:8001" style="float: right">我是管理员</el-link>
                    </el-checkbox-group>
                  </el-form-item>
                  <el-form-item size="medium">
                    <el-button type="primary" style="min-width: 100%" @click="toPhoneLogin">登录</el-button>
                  </el-form-item>
                </el-form>
              </el-tab-pane>
            </el-tabs>
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
import cookie from 'js-cookie'

export default {
  layout: 'login-register',
  data () {
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
      activeName: 'simple',
      simpleLogin: {
        userName: '',
        password: '',
        autoLogin: false
      },
      simpleRules: {
        userName: [
          {
            required: true,
            message: '请输入用户名',
            trigger: 'blur'
          },
          // { min: 3, max: 5, message: "长度在 3 到 5 个字符", trigger: "blur" }
        ],
        password: [
          {
            required: true,
            message: '请输入密码',
            trigger: 'blur'
          },
          {
            min: 6,
            max: 18,
            message: '长度在 6 到 18 个字符',
            trigger: 'blur'
          }
        ]
      },
      hasCode: false,
      time: 60,
      phoneLogin: {
        phone: '',
        code: '',
        autoLogin: false
      },
      phoneRules: {
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
        ]
      }
    }
  },
  methods: {
    getCode () {
      this.$refs.phoneLoginForm.validateField('phone', (valid) => {
        if (!valid && !this.hasCode) {
          this.$axios.get(`/user/login/sendCode/${this.phoneLogin.phone}`).then(response => {
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
    toSimpleLogin () {
      this.$refs.simpleLoginForm.validate((valid) => {
        if (valid) {
          let data = {
            'userName': this.simpleLogin.userName,
            'password': this.simpleLogin.password
          }
          this.$axios.post('/user/login/simple', data)
            .then(response => {
              if (response !== undefined) {
                let expires = this.simpleLogin.autoLogin?7:1
                cookie.set(
                  'userToken',
                  response.token,
                  {
                    domain: 'localhost',
                    expires: expires
                  }
                )
                this.$router.push({ path: '/' })
              }
            })
        } else {
          this.$message.error('请正确输入登录信息！')
          return false
        }
      })
    },
    toPhoneLogin(){
      this.$refs.phoneLoginForm.validate((valid)=>{
        if (valid){
          let data={
            'phone': this.phoneLogin.phone,
            'code': this.phoneLogin.code,
          }
          this.$axios.post('/user/login/phone',data).then(response=>{
            if (response!==undefined){
              let expires=this.phoneLogin.autoLogin?7:1
              cookie.set(
                'userToken',
                response.token,
                {
                  domain: 'localhost',
                  expires: expires
                }
              )
              this.$router.push({ path: '/' })
            }
          })
        } else {
          this.$message.error('请正确输入登录信息！')
          return false
        }
      })
    },
    handleClick (tab, event) {
      console.log(tab, event)
    }
  },
  created () {
    if (this.$route.params.userName) {
      this.simpleLogin.userName = this.$route.params.userName
      this.simpleLogin.password = this.$route.params.password
    }
  },
  beforeCreate () {
    if (cookie.get('userToken')) {
      window.location.href = '/'
    }
  }
}
</script>
<style scoped>

</style>
