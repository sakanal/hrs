<template>
  <div class=''>
    <el-table
      :data="dataList"
      border
      v-loading="dataListLoading"
      @selection-change="selectionChangeHandle"
      style="width: 100%;">
      <!--      <el-table-column-->
      <!--        type="selection"-->
      <!--        header-align="center"-->
      <!--        align="center"-->
      <!--        width="50">-->
      <!--      </el-table-column>-->
      <el-table-column
        prop="id"
        header-align="center"
        align="center"
        label="推广订单号">
      </el-table-column>
<!--      <el-table-column-->
<!--        prop="userId"-->
<!--        header-align="center"-->
<!--        align="center"-->
<!--        label="用户id">-->
<!--      </el-table-column>-->
      <el-table-column
        prop="promotionName"
        header-align="center"
        align="center"
        label="推广套餐">
      </el-table-column>
      <el-table-column
        prop="baseInfoTitle"
        header-align="center"
        align="center"
        label="推广房源">
        <template slot-scope="prop">
          <template v-if="parseInt(prop.row.baseInfoId)<0">
            <el-tag>所有房源</el-tag>
          </template>
          <template>
            <span class="my-house-title">{{ prop.row.baseInfoTitle }}</span>
          </template>
        </template>
      </el-table-column>
      <el-table-column
        prop="number"
        header-align="center"
        align="center"
        label="购买数量">
      </el-table-column>
      <el-table-column
        prop="totalMoney"
        header-align="center"
        align="center"
        label="总金额">
      </el-table-column>
      <el-table-column
        prop="state"
        header-align="center"
        align="center"
        label="订单状态">
        <template slot-scope="prop">
          <el-tag v-if="prop.row.state===0">未支付</el-tag>
          <el-tag v-if="prop.row.state===1" type="success">已支付</el-tag>
          <el-tag v-if="prop.row.state===2" type="danger">超时未支付</el-tag>
        </template>
      </el-table-column>
      <!--      <el-table-column-->
      <!--        prop="isDeleted"-->
      <!--        header-align="center"-->
      <!--        align="center"-->
      <!--        label="0-未删除 1-已删除">-->
      <!--      </el-table-column>-->
      <!--      <el-table-column-->
      <!--        prop="createdTime"-->
      <!--        header-align="center"-->
      <!--        align="center"-->
      <!--        label="订单创建时间">-->
      <!--      </el-table-column>-->
      <!--      <el-table-column-->
      <!--        prop="modifyTime"-->
      <!--        header-align="center"-->
      <!--        align="center"-->
      <!--        label="收单时间">-->
      <!--      </el-table-column>-->
      <el-table-column
        fixed="right"
        header-align="center"
        align="center"
        width="150"
        label="操作">
        <template slot-scope="scope">
          <el-button v-if="scope.row.state===0" type="text" size="small" @click="toPay(scope.row.id)">
            去支付
          </el-button>
          <el-button v-else type="text" size="small" @click="deleteHandle(scope.row.id)">删除订单</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      @size-change="sizeChangeHandle"
      @current-change="currentChangeHandle"
      :current-page="pageIndex"
      :page-sizes="[10, 20, 50, 100]"
      :page-size="pageSize"
      :total="totalPage"
      :hide-on-single-page="true"
      layout="total, sizes, prev, pager, next, jumper">
    </el-pagination>

    <div ref="alipayWap" v-html="alipay"/>
  </div>
</template>

<script>
// 这里可以导入其他文件（比如：组件，工具js，第三方插件js，json文件，图片文件等等）
// 例如：import 《组件名称》 from ‘《组件路径》‘;

import cookie from 'js-cookie'

export default {
  layout: 'businessLayout',
  // import引入的组件需要注入到对象中才能使用
  components: {},
  data () {
    // 这里存放数据
    return {
      dataForm: {
        key: ''
      },
      dataList: [],
      pageIndex: 1,
      pageSize: 10,
      totalPage: 0,
      dataListLoading: false,
      dataListSelections: [],
      addOrUpdateVisible: false,
      alipay: ''
    }
  },
  // 方法集合
  methods: {
    getDataList () {
      this.dataListLoading = true
      this.$axios.get('/promotion/housepromotionorder/list',{params:{
          'page': this.pageIndex,
          'limit': this.pageSize,
          'userId': this.userInfo.id,
          'key': this.dataForm.key
        }}).then((data) => {
          console.log(data)
        if (data && data.code === 0) {
          this.dataList = data.page.list
          this.totalPage = data.page.totalCount
        } else {
          this.dataList = []
          this.totalPage = 0
        }
        this.dataListLoading = false
      })
    },
    // 每页数
    sizeChangeHandle (val) {
      this.pageSize = val
      this.pageIndex = 1
      this.getDataList()
    },
    // 当前页
    currentChangeHandle (val) {
      this.pageIndex = val
      this.getDataList()
    },
    // 多选
    selectionChangeHandle (val) {
      this.dataListSelections = val
    },
    // 新增 / 修改
    toPay (id) {
      this.$axios.post(`/promotion/housepromotionorder/toPay/${id}`)
        .then((res) => {
          // 渲染支付页面
          this.alipay = res.pay
          // 防抖避免重复支付
          this.$nextTick(() => {
            // 提交支付表单
            this.$refs.alipayWap.children[0].submit()
            setTimeout(() => {
            }, 500)
          })
        })
    },
    // 删除
    deleteHandle (id) {
      this.$confirm(`是否删除[${id}]号订单?`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$axios.delete(`/promotion/housepromotionorder/userDelete/${id}`)
          .then(response => {
            if (response.code === 0) {
              this.$message({
                message: '删除成功',
                type: 'success',
                duration: 800,
                onClose: () => {
                  this.getDataList()
                }
              })
            } else {
              this.$message.error(response.msg)
            }
          })

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
            this.getDataList()
          } else {
            this.$message.error(response.msg)
            this.$router.push({ path: '/login' })
          }
        })
    } else {
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
.my-house-title {
  overflow: hidden;
  text-overflow: ellipsis;
  -webkit-line-clamp: 1;
  -webkit-box-orient: vertical;
  display: -webkit-box;
}
</style>
