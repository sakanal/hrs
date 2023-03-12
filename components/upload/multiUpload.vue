<template>
  <div>
    <el-upload
      :action="dataObj.host"
      :data="dataObj"
      list-type="picture-card"
      :file-list="fileList"
      :before-upload="beforeUpload"
      :before-remove="beforeRemove"
      :on-remove="handleRemoveSuccess"
      :on-success="handleUploadSuccess"
      :on-preview="handlePreview"
      :limit="maxCount"
      :on-exceed="handleExceed"
    >
      <i class="el-icon-plus"></i>
    </el-upload>
    <el-dialog :visible.sync="dialogVisible">
      <img width="100%" :src="dialogImageUrl" alt/>
    </el-dialog>
  </div>
</template>
<script>
import * as imageConversion from 'image-conversion'

export default {
  name: 'multiUpload',
  props: {
    //图片属性数组
    value: Array,
    // 最大上传图片数量
    maxCount: {
      type: Number,
      default: 30
    },
    fileDir: {
      type: String,
      default: ''
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
        host: '',
        uuid: ''
      },
      dialogVisible: false,
      dialogImageUrl: null,
      fileList: [],
      fileKeys: []
    }
  },
  computed: {
  },
  mounted () {
  },
  methods: {
    initFileList() {
      this.fileList = this.value
    },
    getUUID () {
      return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, c => {
        return (c === 'x' ? (Math.random() * 16 | 0) : ('r&0x3' | '0x8')).toString(16)
      })
    },
    emitInput (fileList) {
      let value = []
      for (let i = 0; i < fileList.length; i++) {
        value.push({
          name: fileList[i].name,
          url: fileList[i].url,
          sort: 0,
          isDefaultImage: 0
        })
      }
      this.$emit('multiUpload', value)
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
    handleRemoveSuccess (file, fileList) {
      this.fileList = fileList
      this.emitInput(fileList)
    },
    handlePreview (file) {
      this.dialogVisible = true
      this.dialogImageUrl = file.url
    },
    /*
    TODO
      el-upload在一次性上传多个文件时会调用多次beforeUpload，即会为每一个文件获取不同的policy和通过getUUID()获取不同的key
      在当前情况下只会保存其他一个文件的policy和key以及相关数据，这样在上传的时候会存在相同的uuid
     */
    beforeUpload (file) {
      let _self = this
      return this.$axios.get(`/thirdParty/OSS/upload/${this.fileDir}`)
        .then(response => {
          _self.dataObj.policy = response.data.policy
          _self.dataObj.signature = response.data.signature
          _self.dataObj.ossaccessKeyId = response.data.accessKey
          _self.dataObj.key = response.data.dir + this.getUUID() + '_${filename}'
          // 预存所有图片的路径
          _self.fileKeys.push({
            key: this.dataObj.key.replace('${filename}', file.name)
          })
          _self.dataObj.dir = response.data.dir
          _self.dataObj.host = response.data.host

          // 判断图片基础情况
          const isJpgOrPng = file.type === 'image/jpeg' || file.type === 'image/png'
          if (!isJpgOrPng) {
            this.$message.error('上传图片只能是 JPG 或 PNG 格式!')
          }
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
    },
    handleUploadSuccess (res, file, fileList) {
      this.fileKeys.forEach(fileKey => {
        this.fileList.push({
          name: file.name,
          url: this.dataObj.host + '/' + fileKey.key,
          sort: 0,
          uid: file.uid,
          isDefaultImage: 0
        })
      })
      this.emitInput(this.fileList)
      this.fileKeys = []
    },
    handleExceed (files, fileList) {
      this.$message({
        message: '最多只能上传' + this.maxCount + '张图片',
        type: 'warning',
        duration: 1000
      })
    }
  }
}
</script>
<style>
</style>


