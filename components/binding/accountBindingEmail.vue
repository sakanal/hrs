<template>
  <div class=''>
    <el-dialog
      title="换绑邮箱"
      :visible="emailChangeFlag"
      :before-close="handleClose"
      :close-on-click-modal="false">
      <el-form ref="dataFormRef" :model="dataForm" :rules="rules" status-icon label-width="0px">
        <el-form-item prop="email">
          <el-input v-model="dataForm.email" placeholder="邮箱" auto-complete="false">
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
          <el-input v-model="dataForm.code" placeholder="动态码" auto-complete="false"></el-input>
        </el-form-item>
        <el-form-item size="medium">
          <el-button type="primary" style="min-width: 100%" @click="toBinding">换绑</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>

<script>
// 这里可以导入其他文件（比如：组件，工具js，第三方插件js，json文件，图片文件等等）
// 例如：import 《组件名称》 from ‘《组件路径》‘;

export default {
  // import引入的组件需要注入到对象中才能使用
  props: {
    userId: {
      type: Number,
      required: true
    },
    emailChangeFlag: {
      type: Boolean,
      default: false
    }
  },
  components: {},
  data () {
    const validateEmail = (rule, value, callback) => {
      const pattern = /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/
      if (value === '') {
        callback(new Error('请输入邮箱'))
      } else if (!pattern.test(value)) {
        callback(new Error('请输入有效邮箱'))
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
    // 这里存放数据
    return {
      hasCode: false,
      time: 60,
      dataForm: {
        email: '',
        code: '',
      },
      rules: {
        email: [
          {
            required: true,
            message: '请输入邮箱',
            trigger: 'blur'
          },
          {
            validator: validateEmail,
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
  // 方法集合
  methods: {
    getCode () {
      this.$refs.dataFormRef.validateField('email', (valid) => {
        if (!valid && !this.hasCode) {
          this.$axios.post(`/user/userbaseinfo/sendCode/email`,{
            userId: this.userId,
            email: this.dataForm.email
          }).then(response => {
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
    toBinding(){
      this.$refs.dataFormRef.validate((valid)=>{
        if (valid){
          this.$axios.put('/user/userbaseinfo/updateUserInfo',{
            userId: this.userId.toString(),
            email: this.dataForm.email,
            code: this.dataForm.code
          }).then(response=>{
            if (response===undefined){
              this.$refs.dataFormRef.resetFields()
            }else if (response.code===0){
              this.$message.success('换绑邮箱成功')
              this.handleClose()
            }
          })
        }else {
          this.$message.error('请输入正确信息')
          return false
        }
      })
    },
    handleClose () {
      this.dataForm={}
      this.$emit('bindingResult', false)
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
