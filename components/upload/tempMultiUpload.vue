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
  computed: {},
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
          if (response === undefined) {
            return Promise.reject()
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
      console.log(file.uid)
      const isJpgOrPng = file.type === 'image/jpeg' || file.type === 'image/png'
      if (!isJpgOrPng) {
        this.$message.error('上传图片只能是 JPG 或 PNG 格式!')
      }
      const littleName = this.getUUID() + '_' + file.name
      if (!(file.size / 1024 / 1024 < 2)) {
        // 压缩到400KB,这里的400就是要压缩的大小,可自定义
        imageConversion.compressAccurately(file, 400).then((res) => {
          const copyFile = new File([res], littleName)
          this.handlePddUploadFile(copyFile,file.uid)
        })
      } else {
        const copyFile = new File([file], littleName)
        this.handlePddUploadFile(copyFile,file.uid)
      }
      return false
    },
    // 自定义的上传form
    handlePddUploadFile (copyFile,uid) {
      this.$axios.get(`/thirdParty/OSS/upload/${this.fileDir}`)
        .then(response => {
          const formData = new FormData()
          formData.append('key', response.data.dir + '${filename}')
          formData.append('policy', response.data.policy)
          formData.append('signature', response.data.signature)
          formData.append('ossaccessKeyId', response.data.accessKey)
          formData.append('dir', response.data.dir)
          formData.append('uid', 'uid')
          formData.append('host', response.data.host)
          formData.append('file', copyFile)
          this.handlePddPostForm(response.data.host, formData)
        })

    },
    handlePddPostForm (host, formData) {
      try {
        // TODO 使用自己创建的axios的话可能会有点小问题（oss直传不会有返回数据）
        this.$axios.post(host, formData)
        .then(()=>{
          let fileName = formData.get('file').name
          let url = formData.get('host')+'/'+formData.get('key').replace('${filename}',fileName)
          let uid = formData.get('uid')
          this.handleUploadSuccess(fileName,url,uid)
        })
      } catch (error) {
        console.log(error)
      }
    },

    // TODO 可能是因为uid的问题，图片上传会闪动
    handleUploadSuccess (fileName,url,uid) {
      this.fileList.push({
        name: fileName,
        url: url,
        sort: 0,
        uid: uid,
        isDefaultImage: 0
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


