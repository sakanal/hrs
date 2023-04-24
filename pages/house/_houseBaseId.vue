<template>
  <div class=''>
    <el-row type="flex" class="row-bg" justify="center" style="margin-top: 20px">
      <el-col :span="19">
        <el-row>
          <el-col :span="20">
            <div style="font-size: 24px">
              <span>{{ houseInfo.baseInfo.houseTitle }}</span>
            </div>
          </el-col>
          <el-col :span="4">
            <template v-if="!isMyCollection">
              <el-button type="text" icon="el-icon-star-off" @click="addCollection">
                <div style="font-size: 12px;margin-top: 8px">加入收藏</div>
              </el-button>
            </template>
            <template v-else>
              <el-button type="text" icon="el-icon-star-on" @click="removeCollection">
                <div style="font-size: 12px;margin-top: 8px">取消收藏</div>
              </el-button>
            </template>
          </el-col>
        </el-row>
        <el-row>
          <!--          图片走马灯-->
          <el-col :span="9" style="margin-right: 20px">
            <el-carousel height="350px" indicator-position="outside">
              <el-carousel-item v-for="item in houseInfo.imageInfoList" :key="item.url">
                <el-image
                  style="height: 350px"
                  :src="item.url"
                  :fit="'cover'"
                  :preview-src-list="previewSrcList"></el-image>
              </el-carousel-item>
            </el-carousel>
          </el-col>
          <el-col :span="14" class="my-divider-simple">
            <el-divider/>
            <el-row>
              <!--              房屋基础信息介绍-->
              <el-col :span="16" class="my-house-info-div">
                <!--                租金介绍-->
                <div>
                  <span style="color: red;font-size: 18px;margin-right: 30px;">
                    <span style="font-size: 30px">{{ houseInfo.rentInfo.monthlyRent }}</span>
                    <span>元/月</span>
                  </span>
                  <span style="margin-right: 30px;">
                    <span>付 {{ houseInfo.rentInfo.payNumber }}</span>
                    <span>压 {{ houseInfo.rentInfo.mortgageNumber }}</span>
                  </span>
                  <span>
                    <el-tooltip content="Bottom center" placement="bottom" effect="light">
                      <template slot="content">
                        <div><b>租金已包含费用</b></div>
                        <div style="margin-bottom: 10px"></div>
                        <div>
                          <template v-for="rentContent in houseInfo.rentInfo.rentContentList">
                            <span style="margin-right: 20px">{{ rentContent }}</span>
                          </template>
                        </div>
                      </template>
                      <el-button type="text">费用详细</el-button>
                    </el-tooltip>
                  </span>
                </div>
                <!--                房屋类型介绍-->
                <div>
                  <el-row>
                    <el-col :span="5">
                      <span>房屋类型：</span>
                    </el-col>
                    <el-col :span="16">
                      <span>{{ houseInfo.baseInfo.roomNumber }}室</span>
                      <span>{{ houseInfo.baseInfo.hallNumber }}厅</span>
                      <span>{{ houseInfo.baseInfo.cloakroomNumber }}卫</span>
                      <span>{{ houseInfo.baseInfo.areaCovered }}平</span>
                      <span>
                        <template v-if="houseInfo.detailedInfo.decorationState===0">毛胚</template>
                        <template v-if="houseInfo.detailedInfo.decorationState===1">简单装修</template>
                        <template v-if="houseInfo.detailedInfo.decorationState===2">精装修</template>
                        <template v-if="houseInfo.detailedInfo.decorationState===3">豪华装修</template>
                      </span>
                    </el-col>
                  </el-row>
                </div>
                <!--                房屋朝向介绍-->
                <div>
                  <el-row>
                    <el-col :span="5">
                      <span>朝向楼层：</span>
                    </el-col>
                    <el-col :span="16">
                      <span style="margin-right: 5px">{{ houseInfo.baseInfo.orientation }}</span>
                      <span>{{ houseInfo.baseInfo.floor }}层</span>
                      /
                      <span>{{ houseInfo.baseInfo.totalFloor }}层</span>
                    </el-col>
                  </el-row>
                </div>
                <!--                所在小区介绍-->
                <div>
                  <el-row>
                    <el-col :span="5">
                      <span>所在小区：</span>
                    </el-col>
                    <el-col :span="16">
                      <span>{{ houseInfo.baseInfo.areaName }}</span>
                    </el-col>
                  </el-row>
                </div>
                <!--                所在区域介绍-->
                <div>
                  <el-row>
                    <el-col :span="5">
                      <span>所在区域：</span>
                    </el-col>
                    <el-col :span="16">
                      <el-link :underline="false" style="margin-right: 15px;font-size: 16px"
                               :href="`/?childrenCityQuery=${houseInfo.baseInfo.cityId}`">
                        {{ houseInfo.baseInfo.cityName }}
                      </el-link>
                      <el-link :underline="false" style="font-size: 16px"
                               :href="`/?childrenCityQuery=${houseInfo.baseInfo.cityId}&roadId=${houseInfo.baseInfo.roadId}`">
                        {{ houseInfo.baseInfo.roadName }}
                      </el-link>
                    </el-col>
                  </el-row>
                </div>
              </el-col>
              <!--              房屋出租人基础信息-->
              <el-col :span="8">
                <el-card>
                  <div class="block" style="text-align: center">
                    <div style="margin-bottom: 10px">
                      <el-avatar :size="100"
                                 :src="houseInfo.contactInfo.headPortraitUrl"></el-avatar>
                    </div>
                    <span>{{ houseInfo.contactInfo.publisherName }}</span>
                    <div>
                      <el-tag>回复时间</el-tag>
                    </div>
                    <div>
                      <span>{{ houseInfo.contactInfo.answerPeriodTimeStart }}</span>
                      -
                      <span>{{ houseInfo.contactInfo.answerPeriodTimeEnd }}</span>
                    </div>
                    <div>
                      <el-button style="height: 100%;width: 100%" type="text" @click="getPhone">
                        <div style="background-color: #ff7759;
                        color: white;
                        font-size: 20px;
                        height: 2em;
                        line-height: 2em">
                          获取联系方式
                        </div>
                      </el-button>
                    </div>
                  </div>
                </el-card>
              </el-col>
            </el-row>
          </el-col>
        </el-row>
        <el-divider/>
        <el-row>
          <el-col :span="18" style="padding-right: 50px">
            <div style="margin-bottom: 20px">
              <el-link :underline="false">
                <span style="font-size: 20px;color: black">房源详情</span>
              </el-link>
            </div>
            <div style="padding:10px;background-color: #fffaf8;font-size: 12px">
              签约前切勿付
              <span style="color: #ff552e">订金、押金、租金</span>
              等一切费用！务必
              <span style="color: #ff552e">实地看房</span>
              ， 查验房东和房屋证件！
            </div>
            <!--            房屋设施-->
            <div style="margin: 5px">
              <el-row style="padding: 20px">
                <template v-for="facilities in houseInfo.facilitiesList">
                  <el-col :span="4">
                    <div>
                      <div style="text-align: center;margin-bottom: 10px" :class="'iconfont '+facilities.icon"></div>
                      <div style="text-align: center">{{ facilities.name }}</div>
                    </div>
                  </el-col>
                </template>
              </el-row>
            </div>
            <!--            房屋详细介绍-->
            <div class="my-house-detailed-info-div">
              <div>
                <el-row>
                  <el-col :span="3">
                    <span>房屋亮点</span>
                  </el-col>
                  <el-col :span="16">
                    <template v-if="houseInfo.detailedInfo.baseHighlightList===null">
                      <span>无</span>
                    </template>
                    <template v-else>
                      <template v-for="highlight in houseInfo.detailedInfo.baseHighlightList">
                        <span>{{ highlight }}</span>
                      </template>
                    </template>
                  </el-col>
                </el-row>
              </div>
              <div>
                <el-row>
                  <el-col :span="3">
                    <span>出租要求</span>
                  </el-col>
                  <el-col :span="16">
                    <template v-if="houseInfo.detailedInfo.baseRentalRequirementList===null">
                      <span>无</span>
                    </template>
                    <template v-else>
                      <template v-for="rentalRequirement in houseInfo.detailedInfo.baseRentalRequirementList">
                        <span>{{ rentalRequirement }}</span>
                      </template>
                    </template>
                  </el-col>
                </el-row>
              </div>
              <div>
                <el-row>
                  <el-col :span="3">
                    <span>房屋描述</span>
                  </el-col>
                  <el-col :span="16">
                    <span>{{ houseInfo.detailedInfo.houseDetailedDescription }}</span>
                  </el-col>
                </el-row>
              </div>
            </div>
            <!--            房屋图片-->
            <div class="my-house-image">
              <template v-for="image in houseInfo.imageInfoList">
                <el-image
                  style="width: 100%;"
                  :src="image.url"
                  :fit="'fit'"></el-image>
              </template>
            </div>
          </el-col>
          <el-col :span="6" v-if="recommendInfoList.length>0">
            <div>
              <span style="font-size: 18px">热推房源</span>
              <el-tag type="danger" effect="plain" size="mini">HOT</el-tag>
            </div>
            <div  style="padding: 5px 0 5px 0">
              <template v-for="recommendInfo in recommendInfoList">
                <el-link :href="`/house/${recommendInfo.id}`" :underline="false">
                  <el-card :body-style="{ padding: '0px' }" style="margin: 0 0 10px 0" shadow="hover">
                    <el-image style="width:180px;height: 180px"
                              :fit="'cover'"
                              :src="recommendInfo.url"
                              class="image"/>
                    <div style="padding: 10px">
                      <div style="font-size: 12px">
                        <span>{{recommendInfo.childrenCityName}}</span>
                        /
                        <span>{{recommendInfo.roadName}}</span>
                        /
                        <span>{{recommendInfo.areaName}}</span>
                      </div>
                      <div style="font-size: 12px; color: #777">
                        <span>{{recommendInfo.roomNumber}}室</span>
                        <span>{{recommendInfo.hallNumber}}厅</span>
                        <span>{{recommendInfo.areaCovered}}平</span>
                      </div>
                      <div>
                        <span>
                          <span style="color: red">
                            {{recommendInfo.rent}}
                          </span>
                          元/月
                        </span>
                      </div>
                    </div>
                  </el-card>
                </el-link>
              </template>
            </div>
          </el-col>
        </el-row>
      </el-col>
    </el-row>
  </div>
</template>

<script>
// 这里可以导入其他文件（比如：组件，工具js，第三方插件js，json文件，图片文件等等）
// 例如：import 《组件名称》 from ‘《组件路径》‘;
import '../../assets/icon/iconfont.css'
import cookie from 'js-cookie'

export default {
  // import引入的组件需要注入到对象中才能使用
  components: {},
  async asyncData ({
    $axios,
    params
  }) {
    let [
      houseInfo,
      recommendList
    ] = await Promise.all([
      await $axios.get(`/house/houseInfo/getPublishInfo/${params.houseBaseId}`),
      await $axios.post(`/house/houseInfo/getRecommendListInHouseInfo/${params.houseBaseId}`)
    ])
    return {
      houseBaseId: params.houseBaseId,
      houseInfo: houseInfo.data,
      previewSrcList: houseInfo.data.previewSrcList,
      recommendInfoList: recommendList.data
    }
  },
  data () {
    // 这里存放数据
    return {
      houseBaseId: '',
      houseInfo: {},
      previewSrcList: [],
      recommendInfoList: [],
      isMyCollection: false
    }
  },
  // 方法集合
  methods: {
    getRecommendInfoList () {
      let data = {
        cityId: this.cityId,
        childrenCityId: this.childrenCityQuery,
        roadId: this.roadId
      }
      this.$axios.post(`/house/houseInfo/getRecommendList`,data).then(response=>{
        if (response && response.code===0){
          this.recommendInfoList = response.data
        }
      })
    },
    addCollection(){
      this.$axios.post(`/user/usercollection/addCollection/${this.houseBaseId}`).then(response=>{
        if (response && response.code===0){
          this.$message.success('加入收藏成功')
          this.isMyCollection=true
        }
      })
    },
    removeCollection(){
      this.$axios.delete(`/user/usercollection/removeCollection/${this.houseBaseId}`).then(response=>{
        if (response && response.code===0){
          this.$message.success('取消收藏成功')
          this.isMyCollection=false
        }
      })
    },
    getPhone(){
      alert(this.houseInfo.contactInfo.contactPhone)
    }
  },
  // 监听属性 类似于data概念
  computed: {},
  // 监控data中的数据变化
  watch: {},
  // 生命周期 - 创建完成（可以访问当前this实例）
  created () {
    if (cookie.get('userToken')) {
      this.$axios.post(`/user/userbrowse/browse/${this.houseBaseId}`)
      this.$axios.get(`/user/usercollection/isMyCollection/${this.houseBaseId}`).then(response=>{
        if (response && response.code===0){
          this.isMyCollection=response.data
        }
      })
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
.my-divider-simple .el-divider--horizontal {
  margin: 0 0 15px 0;
}

.my-house-info-div div {
  margin: 10px 0 20px 5px;
}

.my-house-detailed-info-div div {
  margin: 0 0 20px 0;
}

.my-house-detailed-info-div div span {
  margin-right: 10px;
}
.my-house-image{
  margin: 20px 0 50px 0;
}
.el-avatar--circle /deep/ img {
  height: 100%;
  width: 100%;
}
</style>
