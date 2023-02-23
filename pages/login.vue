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
          <div slot="header" class="title">
            <span>logo</span>
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
                    <el-checkbox-group v-model="simpleLogin.checkList">
                      <el-checkbox label="rememberMe">记住密码</el-checkbox>
                      <el-checkbox label="autoLogin">自动登录</el-checkbox>
                    </el-checkbox-group>
                    <div style="position: absolute;top: 0;right: 5px">
                      <el-link :underline="false">忘记密码</el-link>
                    </div>
                  </el-form-item>
                  <el-form-item size="medium">
                    <el-button type="primary" style="min-width: 100%" @click="toSimpleLogin">登录</el-button>
                  </el-form-item>
                </el-form>
              </el-tab-pane>
              <!--手机验证码登录-->
              <el-tab-pane label="手机验证码登录" name="phone">
                <el-form ref="phoneLoginForm" :model="phoneLogin" :rules="phoneRules" status-icon label-width="0px"
                         class="demo-ruleForm">
                  <el-form-item prop="userName">
                    <el-input v-model="phoneLogin.userName" placeholder="手机号" autocomplete="off"/>
                  </el-form-item>
                  <el-form-item prop="password">
                    <el-input type="password" v-model="phoneLogin.password" placeholder="验证码" autocomplete="off"/>
                  </el-form-item>
                  <el-form-item size="medium">
                    <el-button type="primary" style="min-width: 100%">登录</el-button>
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
    return {
      imgSrc: require('../static/image/login.jpg'),
      activeName: 'simple',
      simpleLogin: {
        userName: '',
        password: '',
        checkList: []
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
      phoneLogin: {
        userName: '',
        password: ''
      },
      phoneRules: {
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
            max: 16,
            message: '长度在 6 到 16 个字符',
            trigger: 'blur'
          }
        ]
      }
    }
  },
  methods: {
    toSimpleLogin () {
      this.$refs.simpleLoginForm.validate((valid) => {
        if (valid) {
          let data = {
            'userName': this.simpleLogin.userName,
            'password': this.simpleLogin.password
          }
          this.$axios.post('/user/userbaseinfo/loginSimple', data)
            .then(response => {
              if (response !== undefined) {
                cookie.set('userToken', response.token, { domain: 'localhost' })
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
