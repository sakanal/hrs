<template>
  <div>
    <el-upload
      :action="dataObj.host"
      :data="dataObj"
      list-type="picture"
      :multiple="false"
      :show-file-list="false"
      :file-list="fileList"
      :before-upload="beforeUpload"
      :on-remove="handleRemove"
      :before-remove="beforeRemove"
      :on-success="handleUploadSuccess"
      :on-preview="handlePreview">
      <el-button size="small" type="primary">点击上传</el-button>
      <div slot="tip" class="el-upload__tip">只能上传图片文件，且不超过10MB</div>
    </el-upload>
    <el-dialog :visible.sync="dialogVisible">
      <img width="100%" :src="fileList[0].url" alt="">
    </el-dialog>
  </div>
</template>
<script>

import * as imageConversion from 'image-conversion'

export default {
  name: 'singleUpload',
  props: {
    value: String,
    fileDir: {
      type: String,
      default: ''
    }
  },
  computed: {
    imageUrl () {
      return this.value
    },
    imageName () {
      if (this.value != null && this.value !== '') {
        return this.value.substr(this.value.lastIndexOf('/') + 1)
      } else {
        return null
      }
    },
    fileList () {
      return [{
        name: this.imageName,
        url: this.imageUrl
      }]
    },
    showFileList: {
      get: function () {
        return this.value !== null && this.value !== '' && this.value !== undefined
      },
      set: function (newValue) {
      }
    }
  },
  data () {
    return {
      dataObj: {
        policy: '',
        signature: '',
        key: '',
        ossaccessKeyId: '',
        dir: '',
        host: ''
          // callback:'',
      },
      dialogVisible: false
    }
  },
  methods: {
    getUUID () {
      return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, c => {
        return (c === 'x' ? (Math.random() * 16 | 0) : ('r&0x3' | '0x8')).toString(16)
      })
    },
    emitInput (val) {
      this.$emit('singleUpload', val)
    },
    handleRemove (file, fileList) {
      this.emitInput('')
    },
    beforeRemove (file, fileList) {
      // 删除oss中的文件
      let data = { fileUrl: file.url }
      let result = false
      return this.$axios.delete(`/thirdParty/OSS/remove`, { data })
        .then(response => {
          if (response === undefined){
            return Promise.reject();
          }
        })
    },
    handlePreview (file) {
      this.dialogVisible = true
    },
    beforeUpload (file) {
      let _self = this
      // 判断图片基础情况
      const isJpgOrPng = file.type === 'image/jpeg' || file.type === 'image/png'
      if (!isJpgOrPng) {
        this.$message.error('上传的数据只能是图片格式!')
        return false
      }else {
        return this.$axios.get(`/thirdParty/OSS/upload/${this.fileDir}`).then(response => {
          _self.dataObj.policy = response.data.policy
          _self.dataObj.signature = response.data.signature
          _self.dataObj.ossaccessKeyId = response.data.accessKey
          _self.dataObj.key = response.data.dir + this.getUUID() + '_${filename}'
          _self.dataObj.dir = response.data.dir
          _self.dataObj.host = response.data.host
          const isLt2M = file.size / 1024 / 1024 < 2
          return new Promise((resolve) => {
            // 小于2M 不压缩
            if (isLt2M) {
              resolve(file)
            }
            // 压缩到400KB,这里的400就是要压缩的大小,可自定义
            imageConversion.compressAccurately(file, 400).then((res) => {
              resolve(res)
            })
          })
        })
      }
    },
    handleUploadSuccess (res, file) {
      console.log('上传成功...')
      this.showFileList = true
      this.fileList.pop()
      this.fileList.push(
        {
          name: file.name,
          url: this.dataObj.host + '/' + this.dataObj.key.replace('${filename}', file.name)
        }
      )
      this.emitInput(this.fileList[0].url)
    }
  }
}
</script>
<style>

</style>


