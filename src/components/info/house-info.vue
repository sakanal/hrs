<template>
  <div class=''>
    <el-dialog
      :title="'房源信息'"
      :visible.sync="visible"
      center>
      <div>
        <el-form :label-position="'left'" label-width="80px" v-if="houseInfo.baseInfo">
          <el-form-item label="房源地区">
            <div>
              {{ houseInfo.baseInfo.cityName }}-{{ houseInfo.baseInfo.roadName }}-{{ houseInfo.baseInfo.areaName }}
            </div>
          </el-form-item>
          <el-form-item label="房源标题">
            <div>
              {{ houseInfo.baseInfo.houseTitle }}
            </div>
          </el-form-item>
        </el-form>
        <el-row>
          <el-col :span="12">
            <el-form :label-position="'left'" label-width="80px" v-if="houseInfo.baseInfo">
              <el-form-item label="房源样式">
                <div>
                  <el-tag>{{ houseInfo.baseInfo.roomNumber }}室</el-tag>
                  <el-tag>{{ houseInfo.baseInfo.hallNumber }}厅</el-tag>
                  <el-tag>{{ houseInfo.baseInfo.cloakroomNumber }}卫</el-tag>
                  <el-tag>{{ houseInfo.baseInfo.areaCovered }}㎡</el-tag>
                </div>
                <div>
                  <el-tag>第{{ houseInfo.baseInfo.floor }}层</el-tag>
                  <el-tag>共{{ houseInfo.baseInfo.totalFloor }}层</el-tag>
                  <el-tag>朝{{ houseInfo.baseInfo.orientation }}</el-tag>
                </div>
                <div>
                  <el-tag v-if="houseInfo.baseInfo.hasElevator" type="success">有电梯</el-tag>
                  <el-tag v-else type="danger">无电梯</el-tag>
                  <el-tag v-if="houseInfo.baseInfo.hasParkingPlace" type="success">有车位</el-tag>
                  <el-tag v-else type="danger">无车位</el-tag>
                </div>
              </el-form-item>
            </el-form>
          </el-col>
          <el-col :span="12">
            <el-form :label-position="'left'" label-width="80px" v-if="houseInfo.rentInfo">
              <el-form-item label="基本租金">
                <div>
                  <el-tag>{{ houseInfo.rentInfo.monthlyRent }}元/月</el-tag>
                  <el-tag>付{{ houseInfo.rentInfo.payNumber }}压{{ houseInfo.rentInfo.mortgageNumber }}</el-tag>
                </div>
              </el-form-item>
              <el-form-item label="租金包含">
                <div v-if="houseInfo.rentInfo.rentContentList.length>0">
                  <template v-for="rentContent in houseInfo.rentInfo.rentContentList">
                    <el-tag>{{ rentContent }}</el-tag>&nbsp;
                  </template>
                </div>
                <div v-else>无</div>
              </el-form-item>
            </el-form>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form :label-position="'left'" label-width="80px" v-if="houseInfo.detailedInfo">
              <el-form-item label="装修情况">
                <div>
                  <el-tag>
                    <template v-if="houseInfo.detailedInfo.decorationState===0">
                      毛胚
                    </template>
                    <template v-else-if="houseInfo.detailedInfo.decorationState===1">
                      简单装修
                    </template>
                    <template v-else-if="houseInfo.detailedInfo.decorationState===2">
                      精装修
                    </template>
                    <template v-else-if="houseInfo.detailedInfo.decorationState===3">
                      豪华装修
                    </template>
                  </el-tag>
                </div>
              </el-form-item>
              <el-form-item label="最早入住">
                <div>
                  <el-tag>{{ houseInfo.detailedInfo.earliestInTime }}</el-tag>
                  <el-tag>宜居{{ houseInfo.detailedInfo.livablePeopleNumber }}人</el-tag>
                </div>
              </el-form-item>
            </el-form>
          </el-col>
          <el-col :span="12">
            <el-form :label-position="'left'" label-width="80px" v-if="houseInfo.detailedInfo">
              <el-form-item label="看房时间">
                <div>
                  <el-tag>
                    <template v-if="houseInfo.detailedInfo.lookingHouseTimeState===0">
                      仅周末
                    </template>
                    <template v-else-if="houseInfo.detailedInfo.lookingHouseTimeState===1">
                      仅工作日
                    </template>
                    <template v-else-if="houseInfo.detailedInfo.lookingHouseTimeState===2">
                      工作日晚上和周末
                    </template>
                    <template v-else-if="houseInfo.detailedInfo.lookingHouseTimeState===3">
                      随时看房
                    </template>
                  </el-tag>
                </div>
              </el-form-item>
              <el-form-item label="房源亮点">
                <div v-if="houseInfo.detailedInfo.baseHighlightList.length>0">
                  <template v-for="highlight in houseInfo.detailedInfo.baseHighlightList">
                    <el-tag>{{highlight}}</el-tag>&nbsp;
                  </template>
                </div>
                <div v-else>无</div>
              </el-form-item>
            </el-form>
          </el-col>
        </el-row>
        <el-form :label-position="'left'" label-width="80px" v-if="houseInfo.detailedInfo">
          <el-form-item label="房源设施">
            <div v-if="houseInfo.detailedInfo.baseFacilitiesList.length>0">
              <template v-for="facilities in houseInfo.detailedInfo.baseFacilitiesList">
                <el-tag>{{facilities}}</el-tag>
              </template>
            </div>
            <div v-else>无</div>
          </el-form-item>
          <el-form-item label="房源介绍">
            <template v-if="houseInfo.detailedInfo.houseDetailedDescription">
              <div>{{houseInfo.detailedInfo.houseDetailedDescription}}</div>
            </template>
            <template v-else>无</template>
          </el-form-item>
        </el-form>
        <el-row>
          <el-col :span="12">
            <el-form :label-position="'left'" label-width="80px" v-if="houseInfo.contactInfo">
              <el-form-item label="发布人">
                <div>{{houseInfo.contactInfo.publisherName}}</div>
              </el-form-item>
              <el-form-item label="联系方式">
                <div>{{houseInfo.contactInfo.contactPhone}}</div>
              </el-form-item>
            </el-form>
          </el-col>
          <el-col :span="12">
            <el-form :label-position="'left'" label-width="80px" v-if="houseInfo.contactInfo">
              <el-form-item label="发布身份">
                <div>
                  <template v-if="houseInfo.contactInfo.publisherIdentity===0">
                    <el-tag>个人房东</el-tag>
                  </template>
                  <template v-if="houseInfo.contactInfo.publisherIdentity===1">
                    <el-tag>个人转租</el-tag>
                  </template>
                  <template v-if="houseInfo.contactInfo.publisherIdentity===2">
                    <el-tag>职业房东</el-tag>
                  </template>
                </div>
              </el-form-item>
              <el-form-item label="联系时间">
                <el-tag>{{houseInfo.contactInfo.answerPeriodTimeStart}}</el-tag>
                -
                <el-tag>{{houseInfo.contactInfo.answerPeriodTimeEnd}}</el-tag>
              </el-form-item>
            </el-form>
          </el-col>
        </el-row>
        <el-form :label-position="'left'" label-width="80px" v-if="houseInfo.imageInfoList">
          <el-form-item label="房源图片">
            <div v-if="houseInfo.imageInfoList.length>0">
              <template v-for="image in houseInfo.imageInfoList">
                <div>
                  <img :src="image.url" style="object-fit: cover;margin-top: 10px" >
                </div>
              </template>
            </div>
            <div v-else>无</div>
          </el-form-item>
        </el-form>
      </div>
      <span slot="footer" class="dialog-footer">
        <template v-if="examine">
          <el-button type="primary" @click="successExamine">同意发布</el-button>
          <el-button @click="visible = false">取消</el-button>
          <el-button type="danger" @click="failExamine">拒绝发布</el-button>
        </template>
        <template v-else>
          <el-button @click="visible = false">关闭</el-button>
          <template v-if="houseInfo.state===1">
            <el-button type="success" @click="successExamine">上架</el-button>
          </template>
          <template v-if="houseInfo.state===2">
            <el-button type="danger" @click="houseOff">下架</el-button>
          </template>
          <template v-if="houseInfo.state===3">
            <el-button type="success" @click="successExamine">重新上架</el-button>
          </template>
        </template>
      </span>
    </el-dialog>
  </div>
</template>

<script>
// 这里可以导入其他文件（比如：组件，工具js，第三方插件js，json文件，图片文件等等）
// 例如：import 《组件名称》 from ‘《组件路径》‘;

export default {
  // import引入的组件需要注入到对象中才能使用
  components: {},
  data () {
    // 这里存放数据
    return {
      visible: false,
      examine: false,
      houseInfo: {
        state: 0,
        houseBaseInfoId: 6,
        baseInfo: {
          cityName: '城市',
          roadName: '交通路',
          areaName: '住宅区',
          houseTitle: '标题',
          roomNumber: 10,
          hallNumber: 20,
          cloakroomNumber: 30,
          areaCovered: 40,
          totalFloor: 60,
          floor: 50,
          hasElevator: false,
          hasParkingPlace: false,
          orientationId: 1,
          orientation: '北'
        },
        rentInfo: {
          monthlyRent: 2001,
          payNumber: 1,
          mortgageNumber: 2,
          rentContentList: ['水费']
        },
        detailedInfo: {
          earliestInTime: '2023-03-02',
          livablePeopleNumber: 122,
          lookingHouseTimeState: 0,
          decorationState: 3,
          baseFacilitiesList: ['冰箱'],
          baseHighlightList: ['首次出租'],
          baseRentalRequirementList: [],
          houseDetailedDescription: '描述-修改'
        },
        imageInfoList: [
          {
            name: 'mmexport1568105855396.jpg',
            url: 'https://hrs-sakanal.oss-cn-hangzhou.aliyuncs.com/house/2023/03/10/e04b6338-c684-4c28-8427-c87f9840ce73_mmexport1568105855396.jpg',
            sort: 0,
            isDefaultImage: 1
          }
        ],
        contactInfo: {
          publisherName: 'zy0shh5f',
          publisherIdentity: 0,
          contactPhone: 18870493682,
          answerPeriodTimeStart: '03:00',
          answerPeriodTimeEnd: '04:00'
        }
      }
    }
  },
  // 方法集合
  methods: {
    init (baseInfoId, flag = false) {
      this.examine = flag
      this.$nextTick(() => {
        this.houseInfo = []
        this.$http({
          url: this.$http.adornUrl(`/house/houseInfo/getPublishInfo/${baseInfoId}`),
          method: 'get'
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.baseInfoId = baseInfoId
            this.houseInfo = data.data
            this.visible = true
          }
        })
      })
    },
    successExamine () {
      this.visible = false
      this.$emit('refreshDataList', {baseInfoId: this.baseInfoId, state: 2})
    },
    failExamine () {
      this.visible = false
      this.$emit('refreshDataList', {baseInfoId: this.baseInfoId, state: 4})
    },
    houseOff () {
      this.visible = false
      this.$emit('refreshDataList', {baseInfoId: this.baseInfoId, state: 3})
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
.el-form-item {
  margin-bottom: 0;
}
img{
  width:100%;
  height:100%;
}

</style>
