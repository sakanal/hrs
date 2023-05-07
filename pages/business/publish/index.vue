<template>
  <div class='' style="height: 100%">
    <el-tabs style="height: 100%" type="border-card" @tab-click="tabClick" :value="tabNumber">
      <el-tab-pane label="显示中" name="2">
        <div>
          <template v-if="publishInfoList.length>0">
            <div v-for="publishInfo in publishInfoList">
              <el-row :gutter="20">
                <el-col :span="6">
                  <div>
                    <el-image
                      style="width: 200px; height: 110px"
                      :src="publishInfo.url"
                      :fit="'cover'"
                      :preview-src-list="publishInfo.imageList"></el-image>
                  </div>
                </el-col>
                <el-col :span="14">
                  <div>
                    <template>
                      <div class="my-title">
                        <el-link :underline="false" :href="`/house/${publishInfo.baseInfoId}`">
                        <span style="font-size: 18px;text-overflow: ellipsis;">
                          {{ publishInfo.houseTitle }}
                        </span>
                        </el-link>
                      </div>
                    </template>
                  </div>
                  <div class="div-house-info">
                    <template v-if="publishInfo.hallNumber>0">{{ publishInfo.hallNumber }}厅</template>
                    <template v-if="publishInfo.roomNumber>0">{{ publishInfo.roomNumber }}室</template>
                    <template v-if="publishInfo.cloakroomNumber>0">{{ publishInfo.cloakroomNumber }}卫</template>
                    <template v-if="publishInfo.areaCovered>0">{{ publishInfo.areaCovered }}㎡</template>
                  </div>
                  <div class="div-house-info">
                    <template v-if="publishInfo.roadName!==null">{{ publishInfo.roadName }}</template>
                    <template v-if="publishInfo.areaName!==null">{{ publishInfo.areaName }}</template>
                  </div>
                  <div class="div-house-info">
                    <template v-if="publishInfo.publisherIdentity===0">来自个人房源</template>
                  </div>
                </el-col>
                <el-col :span="4" style="font-size: 16px;margin-top: 5%;color: red">
                <span v-if="publishInfo.monthlyRent>0">
                  <span style="font-size: 24px">
                    {{ publishInfo.monthlyRent }}
                  </span>
                  元/月
                </span>
                  <template v-else>面议</template>
                  <el-button type="primary" size="small" @click="toPay(publishInfo.baseInfoId)">推广</el-button>
                  <el-button type="danger" size="small" @click="setHouseOff(publishInfo.baseInfoId)">下架</el-button>
                </el-col>
              </el-row>
              <el-divider></el-divider>
            </div>
          </template>
          <template v-else>
            <el-empty description="暂无发布的房源"></el-empty>
          </template>
        </div>
      </el-tab-pane>
      <el-tab-pane label="审核中" name="1">
        <div>
          <template v-if="publishInfoList.length>0">
            <div v-for="publishInfo in publishInfoList">
              <el-row :gutter="20">
                <el-col :span="6">
                  <div>
                    <el-image
                      style="width: 200px; height: 110px"
                      :src="publishInfo.url"
                      :fit="'cover'"
                      :preview-src-list="publishInfo.imageList"></el-image>
                  </div>
                </el-col>
              <el-col :span="14">
                <div>
                  <template>
                    <div class="my-title">
                      {{ publishInfo.houseTitle }}
                    </div>
                  </template>
                </div>
                <div class="div-house-info">
                  <template v-if="publishInfo.hallNumber>0">{{publishInfo.hallNumber}}厅</template>
                  <template v-if="publishInfo.roomNumber>0">{{publishInfo.roomNumber}}室</template>
                  <template v-if="publishInfo.cloakroomNumber>0">{{publishInfo.cloakroomNumber}}卫</template>
                  <template v-if="publishInfo.areaCovered>0">{{publishInfo.areaCovered}}㎡</template>
                </div>
                <div class="div-house-info">
                  <template v-if="publishInfo.roadName!==null">{{publishInfo.roadName}}</template>
                  <template v-if="publishInfo.areaName!==null">{{publishInfo.areaName}}</template>
                </div>
                <div class="div-house-info">
                  <template v-if="publishInfo.publisherIdentity===0">来自个人房源</template>
                </div>
              </el-col>
              <el-col :span="4" style="font-size: 16px;margin-top: 5%;color: red">
                <span v-if="publishInfo.monthlyRent>0">
                  <span style="font-size: 24px">
                    {{ publishInfo.monthlyRent }}
                  </span>
                  元/月
                </span>
                <template v-else>面议</template>
                <el-button type="danger" size="small" @click="toChangeHouseInfo(publishInfo.baseInfoId)">我要修改
                </el-button>
              </el-col>
              </el-row>
              <el-divider></el-divider>
            </div>
          </template>
          <template v-else>
            <el-empty description="暂无审核中的房源"></el-empty>
          </template>
        </div>
      </el-tab-pane>
      <el-tab-pane label="已下架" name="3">
        <div>
          <template v-if="publishInfoList.length>0">
            <div v-for="publishInfo in publishInfoList">
              <el-row :gutter="20">
                <el-col :span="6">
                  <div>
                    <el-image
                      style="width: 200px; height: 110px"
                      :src="publishInfo.url"
                      :fit="'cover'"
                      :preview-src-list="publishInfo.imageList"></el-image>
                  </div>
                </el-col>
                <el-col :span="14">
                  <div>
                    <template>
                      <div class="my-title">
                        {{ publishInfo.houseTitle }}
                      </div>
                    </template>
                  </div>
                  <div class="div-house-info">
                    <template v-if="publishInfo.hallNumber>0">{{publishInfo.hallNumber}}厅</template>
                    <template v-if="publishInfo.roomNumber>0">{{publishInfo.roomNumber}}室</template>
                    <template v-if="publishInfo.cloakroomNumber>0">{{publishInfo.cloakroomNumber}}卫</template>
                    <template v-if="publishInfo.areaCovered>0">{{publishInfo.areaCovered}}㎡</template>
                  </div>
                  <div class="div-house-info">
                    <template v-if="publishInfo.roadName!==null">{{publishInfo.roadName}}</template>
                    <template v-if="publishInfo.areaName!==null">{{publishInfo.areaName}}</template>
                  </div>
                  <div class="div-house-info">
                    <template v-if="publishInfo.publisherIdentity===0">来自个人房源</template>
                  </div>
                </el-col>
                <el-col :span="4" style="font-size: 16px;margin-top: 5%;color: red">
                <span v-if="publishInfo.monthlyRent>0">
                  <span style="font-size: 24px">
                    {{ publishInfo.monthlyRent }}
                  </span>
                  元/月
                </span>
                  <template v-else>面议</template>
                  <el-button type="danger" size="small" @click="toChangeHouseInfo(publishInfo.baseInfoId)">再次发布
                  </el-button>
                </el-col>
              </el-row>
              <el-divider></el-divider>
            </div>
          </template>
          <template v-else>
            <el-empty description="暂无已下架的房源"></el-empty>
          </template>
        </div>
      </el-tab-pane>

      <el-pagination
        :hide-on-single-page="true"
        @current-change="currentChange"
        :page-size="pageSize"
        :current-page.sync="currentPage"
        layout="total, prev, pager, next"
        :total="totalCount">
      </el-pagination>
    </el-tabs>

    <el-drawer
      title="推广列表"
      size="35%"
      :visible.sync="drawerVisit"
      :direction="'rtl'">
      <el-row type="flex" justify="center">
        <el-col :span="22">
          <div>
            <div>
              <template v-for="promotion in promotion.promotionList">
                <el-col :span="8" style="margin-bottom: 10px">
                  <el-button @click="clickButton(promotion)" :type="promotion.id===selectButtonId?'primary':''">
                    {{ promotion.name }}
                  </el-button>
                </el-col>
              </template>
            </div>
            <div>
              <el-col v-if="selectButtonId" class="my-promotion-div">
                <el-divider></el-divider>
                <div>
                  <span>购买次数</span>
                  <el-input-number v-model="promotionHouseInfo.number" :min="1" size="small"></el-input-number>
                </div>
                <div>
                  <el-row>
                    <el-col :span="12">
                      <span>单价：</span>
                      <span>{{ promotionHouseInfo.price }}</span>
                    </el-col>
                    <el-col :span="12">
                      <span>推广次数：</span>
                      <span>{{ multi(promotionHouseInfo.promotionNumber,promotionHouseInfo.number) }}</span>
                    </el-col>
                  </el-row>
                </div>
                <div>
                  <span>总价：</span>
                  <span>{{ multi(promotionHouseInfo.price, promotionHouseInfo.number) }}</span>
                </div>
                <div>
                  <el-button size="small" type="danger" @click="toBuy">立即下单</el-button>
                </div>
              </el-col>
            </div>
          </div>
        </el-col>
      </el-row>
    </el-drawer>
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
      userInfo: {},
      tabNumber: '2',
      // 当前页
      currentPage: 0,
      // 页面尺寸
      pageSize: 0,
      // 总页数
      totalPage: 0,
      // 总记录数
      totalCount: 0,
      publishInfoList: [],
      drawerVisit: false,
      // 最终数据
      promotionHouseInfo: {
        userId: 0,
        promotionId: 0,
        baseInfoId: 0,
        number: 1,
        // 临时数据
        price: 0,
        promotionNumber: 0
      },
      // 需要推广的房源信息
      publishHouseInfo: {},
      selectButtonId: 0,
      // 所有推广活动列表以及相关数据
      promotion: {
        // 页面尺寸
        pageSize: 0,
        // 总页数
        totalPage: 0,
        // 总记录数
        totalCount: 0,
        promotionList: [],
      },
      alipay: ''
    }
  },
  // 方法集合
  methods: {
    tabClick(event){
      this.tabNumber = event.name
      this.getPublishInfoList(1,event.name)
    },
    currentChange(currentPage){
      this.getPublishInfoList(currentPage,this.tabNumber)
    },
    getPublishInfoList (current, state) {
      let publisherId = this.userInfo.id
      this.$axios.post(`/house/houseInfo/getPublishInfoList`,{ publisherId,state,current }).then(response => {
        if (response.page!==null){
          this.currentPage = response.page.currPage
          this.pageSize = response.page.pageSize
          this.totalPage = response.page.totalPage
          this.totalCount = response.page.totalCount
          this.publishInfoList = response.page.list
        } else {
          this.currentPage = 0
          this.pageSize = 0
          this.totalPage = 0
          this.totalCount = 0
          this.publishInfoList = []
        }
      })
    },
    toChangeHouseInfo (houseBaseInfoId) {
      this.$router.push({ path: `/business/publish/update/${houseBaseInfoId}` })
    },
    setHouseOff (houseBaseInfoId) {
      this.$axios.delete(`/house/housestate/setHouseOff/${houseBaseInfoId}`).then(response => {
        if (response && response.data) {
          this.$message.success('下架成功')
          this.getPublishInfoList(1, 2)
        }
      })
    },
    // 乘法-解决number类型使用（*）导致的精度丢失问题
    multi (num1, num2) {
      let str1 = num1.toString(), str2 = num2.toString(), result, str1Length, str2Length
      //解决整数没有小数点方法
      try {
        str1Length = str1.split('.')[1].length
      } catch (error) {
        str1Length = 0
      }
      try {
        str2Length = str2.split('.')[1].length
      } catch (error) {
        str2Length = 0
      }
      let step = Math.pow(10, Math.max(str1Length, str2Length))
      result = ((num1 * step) * (num2 * step)) / step / step
      return result
    },
    toPay (baseInfoId) {
      this.drawerVisit = true
      this.promotionHouseInfo.baseInfoId = baseInfoId
      this.promotionHouseInfo.userId = this.userInfo.id
      // 获取推广活动数据
      this.$axios.get(`/promotion/housepromotionfunction/list`, {
        params: {
          showState: 1,
          page: 1,
          limit: 20
        }
      }).then(response => {
        this.promotion.totalCount = response.page.totalCount
        this.promotion.totalPage = response.page.totalPage
        this.promotion.pageSize = response.page.pageSize
        this.promotion.promotionList = response.page.list
      })
      // 获取选中的房源数据
      this.publishInfoList.forEach(publishHouseInfo => {
        if (publishHouseInfo.baseInfoId === baseInfoId) {
          this.publishHouseInfo = publishHouseInfo
        }
      })
    },
    clickButton (promotion) {
      this.promotionHouseInfo.promotionId = promotion.id
      this.promotionHouseInfo.price = promotion.price
      this.selectButtonId = promotion.id
      this.promotionHouseInfo.promotionNumber=promotion.number
    },
    toBuy () {
      // 调用支付接口
      this.$axios.post(`/promotion/housepromotionorder/createOrder`, {
        userId: this.userInfo.id,
        promotionId: this.promotionHouseInfo.promotionId,
        baseInfoId: this.promotionHouseInfo.baseInfoId,
        number: this.promotionHouseInfo.number
      }).then((res) => {
        // 渲染支付页面
        this.alipay = res.pay
        // 防抖避免重复支付
        this.$nextTick(() => {
          // 提交支付表单
          this.$refs.alipayWap.children[0].submit()
          setTimeout(() => {
            // this.toPayFlag = false;
          }, 500)
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
          if (response && response.code===0){
            this.userInfo = response.userInfo
            this.getPublishInfoList(1,2)
          }else {
            this.$message.error(response.msg)
            this.$router.push({path:'/login'})
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
.div-house-info {
  color: #777;
  font-size: 12px;
  padding: 10px 0 5px 0;
}
.my-title {
  width: 100%;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}
.my-promotion-div div{
  margin: 0 0 15px 0;
}

</style>
