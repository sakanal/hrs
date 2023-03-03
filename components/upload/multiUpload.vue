<template>
  <div>
    <el-upload
      multiple
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
export default {
  name: 'multiUpload',
  props: {
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
    beforeUpload (file) {
      let _self = this
      return new Promise((resolve, reject) => {
        this.$axios.get(`/thirdParty/OSS/upload/${this.fileDir}`)
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
            resolve(true)
          })
          .catch(err => {
            console.log('出错了...', err)
            reject(false)
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


