<template>
  <div class=''>
    <div style="margin: 5px 0 20px 0">个人资料</div>
    <div>
      <el-tabs @tab-click="tabClick">
        <el-tab-pane label="本地上传"></el-tab-pane>
<!--        <el-tab-pane label="系统头像"></el-tab-pane>-->
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
          <el-col v-if="avatarPreview" :sm="12" :lg="6">
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
      </div>
      <el-tabs>
        <el-tab-pane label="修改个人信息"></el-tab-pane>
      </el-tabs>
      <div>
        <account-binding-phone :user-id="this.userInfo.id" :phone-change-flag="phoneChangeFlag" @bindingResult="bindingResult"/>
        <el-row>
          <el-col :span="12">
            <el-form ref="userInfoFromRef" :model="userInfoFrom" :rules="rules" label-width="100px"
                     label-position="left">
              <el-form-item label="用户名" prop="userName">
                <span class="my-form-item-span">
                  <template v-if="userNameChangeFlag">
                    <el-input v-model="userInfoFrom.userName"></el-input>
                  </template>
                  <template v-else>
                    <span style="padding-left: 15px;">
                      {{ userInfo.userName }}
                    </span>
                  </template>
                </span>
                <template v-if="userNameChangeFlag">
                  <el-button type="text" @click="userNameChange(true)">确认</el-button>
                  <el-button type="text" @click="userNameChange(false)">取消</el-button>
                </template>
                <template v-else>
                  <el-button type="text" @click="userNameChangeFlag=true">修改</el-button>
                </template>
              </el-form-item>
              <el-form-item label="昵称" prop="nickName">
                <span class="my-form-item-span">
                  <template v-if="nickNameChangeFlag">
                    <el-input v-model="userInfoFrom.nickName"></el-input>
                  </template>
                  <template v-else>
                    <span style="padding-left: 15px">
                      {{ userInfo.nickName }}
                    </span>
                  </template>
                </span>
                <template v-if="nickNameChangeFlag">
                  <el-button type="text" @click="nickNameChange(true)">确认</el-button>
                  <el-button type="text" @click="nickNameChange(false)">取消</el-button>
                </template>
                <template v-else>
                  <el-button type="text" @click="nickNameChangeFlag=true">修改</el-button>
                </template>
              </el-form-item>
              <el-form-item label="手机">
                <template v-if="userInfo.phone">
                  <span class="my-form-item-span">
<!--                    <template v-if="phoneChangeFlag">-->
<!--                      <el-input v-model="userInfoFrom.phone"></el-input>-->
<!--                    </template>-->
                    <template>
                      <span style="padding-left: 15px">
                        {{ userInfo.phone }}
                      </span>
                    </template>
                  </span>
<!--                  <template v-if="phoneChangeFlag">-->
<!--                    <el-button type="text" @click="phoneChange(true)">确认</el-button>-->
<!--                    <el-button type="text" @click="phoneChange(false)">取消</el-button>-->
<!--                  </template>-->
                  <template>
                    <el-button type="text" @click="phoneChangeFlag=true">换绑</el-button>
                  </template>
                </template>
                <template v-else>
                  <span style="padding-left: 15px">
                    <el-button type="text">前往绑定手机</el-button>
                  </span>
                </template>
              </el-form-item>
              <el-form-item label="邮箱">
                <template v-if="userInfo.email">
                  <span class="my-form-item-span">
                  <template v-if="emailChangeFlag">
                    <el-input v-model="userInfoFrom.email"></el-input>
                  </template>
                  <template v-else>
                    <span style="padding-left: 15px">
                      {{ userInfo.email }}
                    </span>
                  </template>
                </span>
                  <template v-if="emailChangeFlag">
                    <el-button type="text" @click="emailChange(true)">确认</el-button>
                    <el-button type="text" @click="emailChange(false)">取消</el-button>
                  </template>
                  <template v-else>
                    <el-button type="text" @click="emailChangeFlag=true">换绑</el-button>
                  </template>
                </template>
                <template v-else>
                  <span style="padding-left: 15px">
                    <el-button type="text">前往绑定邮箱</el-button>
                  </span>
                </template>
              </el-form-item>
            </el-form>
          </el-col>
        </el-row>
      </div>
    </div>
  </div>
</template>

<script>
// 这里可以导入其他文件（比如：组件，工具js，第三方插件js，json文件，图片文件等等）
// 例如：import 《组件名称》 from ‘《组件路径》‘;

import cookie from 'js-cookie'
import singleUpload from '@/components/upload/singleUpload'
import accountBindingPhone from '@/components/binding/accountBindingPhone'

export default {
  layout: 'userLayout',
  // import引入的组件需要注入到对象中才能使用
  components: { singleUpload, accountBindingPhone },
  data () {
    const validateName = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入用户名'))
      } else {
        const pattern = /^[a-zA-Z][a-zA-Z0-9_]{4,15}$/
        if (pattern.test(value)){
          this.$axios.get(`/user/register/checkUserName/${value}`)
            .then(response => {
              if (!response.result) {
                callback(new Error('用户名重复'))
              } else {
                callback()
              }
            })
        }else {
          callback(new Error('请输入以字母开头，由字母数字下划线组成5-16位的用户名'))
        }
      }
    }
    const validateNickName = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入昵称'))
      } else {
        const pattern = /^[\u4E00-\u9FA5A-Za-z0-9_]+$/
        if (pattern.test(value)) {
          callback()
        } else {
          callback(new Error('请输入中文、英文、数字或下划线组成的昵称'))
        }
      }
    }
    // 这里存放数据
    return {
      userInfo: {},
      avatarPreview: false,
      avatarImage: '',
      avatarTempUUID: '',
      userInfoFrom: {
        userName: '',
        nickName: '',
        phone: '',
        email: ''
      },
      rules: {
        userName: [{
          validator: validateName
        }],
        nickName: [{
          validator: validateNickName
        }]
      },
      userNameChangeFlag: false,
      nickNameChangeFlag: false,
      phoneChangeFlag: false,
      emailChangeFlag: false
    }
  },
  // 方法集合
  methods: {
    getUserInfo(){
      this.$axios.get('/user/login/userInfoByToken')
        .then(response => {
          if (response && response.code===0){
            this.userInfo = response.userInfo
          }else {
            this.$message.error(response.msg)
            this.$router.push({path:'/login'})
          }
        })
    },
    tabClick (event) {
      this.tabNumber = event.name
      console.log(event.name)
    },
    singleUpload (value) {
      this.avatarPreview = true
      this.avatarImage = value
      this.$axios.post(`/user/avatar/temp`,{fileUrl: value}).then(response=>{
        this.avatarTempUUID=response.data
        console.log(response.data)
      })
    },
    saveAvatar() {
      this.$axios.post('/user/avatar/save', {uuid: this.avatarTempUUID,userId: this.userInfo.id.toString()}).then(response=>{
        if (response && response.code === 0) {
          this.$message.success('修改成功')
          this.avatarPreview = false
          this.getUserInfo()
        } else {
          this.$message.error('修改失败')
        }
      })
    },
    updateUserInfo(data){
      return this.$axios.put('/user/userbaseinfo/updateUserInfo',data)
    },
    userNameChange (flag) {
      if (flag) {
        this.$refs.userInfoFromRef.validateField('userName',(valid) => {
          if (!valid) {
            let data={
              userId: this.userInfo.id.toString(),
              userName: this.userInfoFrom.userName
            }
            this.updateUserInfo(data).then(response=>{
              if (response && response.code === 0){
                this.userNameChangeFlag=false
                this.getUserInfo()
                this.$message.success('修改用户名成功')
              }
            })
          }
        })
      } else {
        this.userNameChangeFlag = false
        this.$refs.userInfoFromRef.clearValidate()
      }
    },
    nickNameChange (flag) {
      if (flag) {
        this.$refs.userInfoFromRef.validateField('nickName',(valid) => {
          if (!valid){
            let data={
              userId: this.userInfo.id.toString(),
              nickName: this.userInfoFrom.nickName
            }
            this.updateUserInfo(data).then(response=>{
              if (response && response.code === 0){
                this.nickNameChangeFlag = false
                this.getUserInfo()
                this.$message.success('修改昵称成功')
              }
            })
          }
        })
      } else {
        this.nickNameChangeFlag = false
        this.$refs.userInfoFromRef.clearValidate()
      }
    },
    phoneChange (flag) {
      if (flag) {
        this.phoneChangeFlag = false
        alert('phone change')
      } else {
        this.phoneChangeFlag = false
        this.$refs.userInfoFromRef.clearValidate()
      }
    },
    bindingResult(flag){
      this.phoneChangeFlag=flag
      this.getUserInfo()
    },
    emailChange (flag) {
      if (flag) {
        this.emailChangeFlag = false
        alert('email change')
      } else {
        this.emailChangeFlag = false
        this.$refs.userInfoFromRef.clearValidate()
      }
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
          if (response && response.code===0){
            this.userInfo = response.userInfo
            this.userInfoFrom.userName = response.userInfo.userName
            this.userInfoFrom.nickName = response.userInfo.nickName
            this.userInfoFrom.phone = response.userInfo.phone
            this.userInfoFrom.email = response.userInfo.email
          }else {
            this.$message.error(response.msg)
            this.$router.push({path:'/login'})
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
.el-avatar--circle /deep/ img {
  height: 100%;
  width: 100%;
}

.my-form-item-span {
  display: inline-block;
  width: 65%;
  margin-right: 5%;
}
</style>
