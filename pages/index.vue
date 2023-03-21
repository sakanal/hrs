<template>
  <div>
    <div style="background-color: rgba(255,85,47,.8);height: 40px;width: 100%">
      <div style="line-height: 40px;color: white;font-size: 20px;text-align: center">
        <b>租房</b>
      </div>
    </div>
    <el-row type="flex" class="row-bg" justify="center" style="margin-top: 20px">
      <el-col :span="19">
        <div class="div-search-info">
          <div>
            <span>地区：</span>
            <el-button type="text" @click="changeChildrenCity(undefined)" :class="childrenCityQuery===undefined?'my-button-active':'my-button-unActive'">不限</el-button>
            <template v-for="childrenCity in childrenCityList">
              <el-button type="text" @click="changeChildrenCity(childrenCity.id)" :class="childrenCityQuery===childrenCity.id?'my-button-active':'my-button-unActive'">{{ childrenCity.name }}</el-button>
            </template>
          </div>
          <div v-if="roadVisible">
            <span>道路：</span>
            <el-button type="text" @click="changeRoad(undefined)" :class="roadId===undefined?'my-button-active':'my-button-unActive'">不限</el-button>
            <template v-for="road in roadList">
              <el-button type="text" @click="changeRoad(road.id)"  :class="roadId===road.id?'my-button-active':'my-button-unActive'">{{ road.name }}</el-button>
            </template>
          </div>
          <div>
            <span>租金：</span>
            <template>
              <el-button type="text" @click="changeRent(undefined)" :class="rentQuery===undefined?'my-button-active':'my-button-unActive'">不限</el-button>
              <el-button type="text" @click="changeRent('_500')"  :class="rentQuery==='_500'?'my-button-active':'my-button-unActive'">500元以下</el-button>
              <el-button type="text" @click="changeRent('500_1000')" :class="rentQuery==='500_1000'?'my-button-active':'my-button-unActive'">500-1000元</el-button>
              <el-button type="text" @click="changeRent('1000_1500')" :class="rentQuery==='1000_1500'?'my-button-active':'my-button-unActive'">1000-1500元</el-button>
              <el-button type="text" @click="changeRent('1500_2000')" :class="rentQuery==='1500_2000'?'my-button-active':'my-button-unActive'">1500-2000元</el-button>
              <el-button type="text" @click="changeRent('2000_2500')" :class="rentQuery==='2000_2500'?'my-button-active':'my-button-unActive'">2000-2500元</el-button>
              <el-button type="text" @click="changeRent('2500_3000')" :class="rentQuery==='2500_3000'?'my-button-active':'my-button-unActive'">2500-3000元</el-button>
              <el-button type="text" @click="changeRent('3000_3500')" :class="rentQuery==='3000_3500'?'my-button-active':'my-button-unActive'">3000-3500元</el-button>
              <el-button type="text" @click="changeRent('4000_')" :class="rentQuery==='4000_'?'my-button-active':'my-button-unActive'">4000元以上</el-button>
            </template>
          </div>
          <div>
            <span>厅室：</span>
            <template>
              <el-select v-model="hallNumber" placeholder="客厅不限" :size="'mini'" style="width: 100px"
                         @change="changeHall">
                <el-option :key="undefined" :label="'客厅不限'" :value="undefined"></el-option>
                <el-option :key="1" :label="'一厅'" :value="'1'"></el-option>
                <el-option :key="2" :label="'两厅'" :value="'2'"></el-option>
                <el-option :key="3" :label="'三厅'" :value="'3'"></el-option>
                <el-option :key="4" :label="'四厅'" :value="'4'"></el-option>
                <el-option :key="5" :label="'四厅以上'" :value="'5'"></el-option>
              </el-select>
            </template>
            <template>
              <el-select v-model="roomNumber" placeholder="房间不限" :size="'mini'" style="width: 100px"
                         @change="changeRoom">
                <el-option :key="undefined" :label="'房间不限'" :value="undefined"></el-option>
                <el-option :key="1" :label="'一室'" :value="'1'"></el-option>
                <el-option :key="2" :label="'两室'" :value="'2'"></el-option>
                <el-option :key="3" :label="'三室'" :value="'3'"></el-option>
                <el-option :key="4" :label="'四室'" :value="'4'"></el-option>
                <el-option :key="5" :label="'四室以上'" :value="'5'"></el-option>
              </el-select>
            </template>
          </div>
          <div>
            <span>其他：</span>
            <template>
              <el-select v-model="orientation" placeholder="朝向不限" :size="'mini'" style="width: 100px"
                         @change="changeOrientation">
                <el-option :key="undefined" :label="'朝向不限'" :value="undefined"></el-option>
                <el-option
                  v-for="item in orientationList"
                  :key="item.id"
                  :label="item.orientation"
                  :value="item.id+ ''">
                </el-option>
              </el-select>
            </template>
            <template>
              <el-select v-model="decoration" placeholder="装修不限" :size="'mini'" style="width: 100px"
                         @change="changeDecoration">
                <el-option :key="undefined" :label="'装修不限'" :value="undefined"></el-option>
                <el-option :key="0" :label="'毛胚'" :value="'0'"></el-option>
                <el-option :key="1" :label="'简单装修'" :value="'1'"></el-option>
                <el-option :key="2" :label="'精装修'" :value="'2'"></el-option>
                <el-option :key="3" :label="'豪华装修'" :value="'3'"></el-option>
              </el-select>
            </template>
          </div>
        </div>
        <el-tabs v-model="activeName">
          <el-tab-pane label="用户管理" name="first">
            <template slot="label">
              {{ cityName }}租房
            </template>
          </el-tab-pane>
        </el-tabs>
        <div>
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
              </el-col>
            </el-row>
            <el-divider></el-divider>
          </div>
        </div>
        <el-pagination
          :hide-on-single-page="true"
          @current-change="currentChange"
          :page-size="pageSize"
          :current-page.sync="currentPage"
          layout="total, prev, pager, next"
          :total="totalCount">
        </el-pagination>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import cookie from 'js-cookie'

export default {
  name: 'IndexPage',
  data () {
    return {
      cityId: 0,
      cityName: '',
      childrenCityList: [],
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
      activeName: 'first',
      roadVisible: false,
      childrenCityQuery: undefined,
      rentQuery: undefined,
      hallQuery: undefined,
      hallNumber: '',
      roomQuery: undefined,
      roomNumber: '',
      orientationList: [],
      orientationId: '',
      orientation: '',
      decorationId: '',
      decoration: '',
      roadId:'',
      roadList: ''
    }
  },
  async asyncData ({ $axios }) {
    let [orientationList] = await Promise.all([
      $axios.get(`/house/baseorientation/getAll`),
    ])
    return {
      orientationList: orientationList.data
    }
  },
  methods: {
    getLocation () {
      const key = 'SKJBZ-7SRRT-H45XT-V4KYJ-6OKUV-B7BFG'
      const url = 'https://apis.map.qq.com/ws/location/v1/ip'
      this.$jsonp(url, {
        key,
        output: 'jsonp'
      }).then(res => {
        let cityName = res.result.ad_info.city
        this.cityName = cityName
        cookie.set('cityName', cityName, { domain: 'localhost' })
        this.$axios.get(`/house/housecity/getCityIdByCityName/${cityName}`)
          .then(response => {
            cookie.set('cityId', response.data, { domain: 'localhost' })
            this.cityId = response.data
          })
      })
    },
    initData (cityId) {
      this.getNextChildrenListByCityId(cityId)
    },
    getNextChildrenListByCityId (cityId) {
      this.$axios.get(`/house/housecity/getNextChildrenListByCityId/${cityId}`)
        .then(response => {
          if (response && response.code === 0) {
            this.childrenCityList = response.data
          }
        })
    },
    getPublishInfoList (current, state = 2) {
      let cityId = cookie.get('cityId')
      if (this.childrenCityQuery) {
        cityId = this.childrenCityQuery
      }
      let data = {
        state,
        current,
        cityId,
        roadId: this.roadId,
        rentQuery: this.rentQuery,
        hallQuery: this.hallQuery,
        roomQuery: this.roomQuery,
        orientationId: this.orientationId,
        decoration: this.decorationId
      }
      this.$axios.post(`/house/houseInfo/getPublishInfoList`, data).then(response => {
        if (response.page !== null) {
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
    currentChange(currentPage){
      this.getPublishInfoList(currentPage)
    },
    getRoadList(cityId){
      this.$axios.get(`/house/housearea/getRoadListByCityId/${cityId}`)
      .then(response=>{
        console.log(response.data)
        this.roadList=response.data
      })
    },
    // 从url中获取数据
    setQuery (query) {
      this.childrenCityId = query.childrenCityId
      if (query.childrenCityId) {
        // 显示交通路搜索
        this.getRoadList(this.childrenCityId)
        this.roadVisible = true
        this.roadId=query.roadId
      } else {
        // 隐藏交通路搜索
        this.roadVisible = false
        this.roadId=undefined
      }
      this.rentQuery = query.rentQuery
      this.hallQuery = query.hallQuery
      this.hallNumber = query.hallQuery
      this.roomQuery = query.roomQuery
      this.roomNumber = query.roomQuery
      this.orientationId = query.orientationId
      this.orientation = query.orientationId
      this.decorationId = query.decorationId
      this.decoration = query.decorationId
    },
    // 刷新url
    toQuery () {
      this.$router.push({
        path: '/',
        query: {
          childrenCityId: this.childrenCityQuery,
          roadId: this.roadId,
          rentQuery: this.rentQuery,
          hallQuery: this.hallQuery,
          roomQuery: this.roomQuery,
          orientationId: this.orientationId,
          decorationId: this.decorationId
        }
      })
    },
    changeChildrenCity (cityId) {
      this.childrenCityQuery = cityId
      this.toQuery()
    },
    changeRoad(roadId){
      this.roadId=roadId
      this.toQuery()
    },
    changeRent (rentQuery) {
      this.rentQuery = rentQuery
      this.toQuery()
    },
    changeHall (hallQuery) {
      this.hallQuery = hallQuery
      this.toQuery()
    },
    changeRoom(roomQuery) {
      this.roomQuery=  roomQuery
      this.toQuery()
    },
    changeOrientation (orientationId) {
      this.orientationId = orientationId
      this.toQuery()
    },
    changeDecoration (decorationId) {
      this.decorationId = decorationId
      this.toQuery()
    }
  },
  // 监控data中的数据变化
  watch: {
    '$route.query' (query) {
      this.setQuery(query)
      this.getPublishInfoList(1)
    }
  },
  created () {
    this.setQuery(this.$route.query)
    this.getPublishInfoList(1)
  },
  mounted () {
    let cityName = cookie.get('cityName')
    let cityId = cookie.get('cityId')
    if (!(cityName && cityId)) {
      this.getLocation()
    } else {
      this.cityName = cityName
      this.cityId = cityId
    }
    this.getNextChildrenListByCityId(cityId)
  }
}
</script>

<style scoped>
.div-search-info div {
  margin: 5px 0 5px 5px;
}

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
.my-button-active{
  color: rgba(255,85,47,.8);
}
.my-button-unActive{
  color: black;
}
</style>
