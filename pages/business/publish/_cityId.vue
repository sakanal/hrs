<template>
  <div class=''>
    <el-card style="margin: 20px 0 20px 0">
      <el-steps :active="1" finish-status="success" simple>
        <el-step icon="el-icon-map-location" title="房源定位"></el-step>
        <el-step icon="el-icon-edit" title="填写信息"></el-step>
        <el-step icon="el-icon-upload" title="等待审核"></el-step>
      </el-steps>
      <div style="background-color: #fffbe4; color: #ec4514; padding: 10px;margin-top: 10px">
        为共建真实可信的生活服务平台，所有新发布的信息需要进行认证才能展现，认证方式包括：支付认证或芝麻信用认证
        <br>
        请如实填写信息，如有虚假会有账号封禁及扣除保证金等处罚
      </div>
    </el-card>
    <el-row :gutter="20">
      <el-col :span="20" style="margin-bottom: 50px">
        <el-card>
          <el-tabs value="first">
            <el-tab-pane name="first">
              <span slot="label" style="font-size: 24px">整租发布</span>
              <el-col :span="20">
                <el-form ref="baseForm" :model="baseForm" :rules="baseRules" label-width="80px">
                  <div style="margin: 20px 0 20px 0;">
                    <div style="margin: 20px 0 20px 0;" id="baseForm"><b>基础信息</b></div>
                    <el-form-item label="所在区域">
                      <span slot="label"><span style="color: red">*&nbsp;</span>所在区域</span>
                      <el-col :span="8">
                        <el-form-item prop="cityId">
                          <el-select v-model="baseForm.cityId" @change="changeRoadList" filterable
                                     placeholder="请选择所在区域">
                            <el-option v-for="item in cityList" :key="item.id" :label="item.name" :value="item.id"/>
                          </el-select>
                        </el-form-item>
                      </el-col>
                      <el-col :span="8">
                        <el-form-item prop="roadId">
                          <el-select v-model="baseForm.roadId" @change="changeAreaList" filterable
                                     placeholder="请选择所在交通路">
                            <el-option v-for="item in selectRoadList" :key="item.id" :label="item.name"
                                       :value="item.id"/>
                          </el-select>
                        </el-form-item>
                      </el-col>
                      <el-col :span="8">
                        <el-form-item prop="areaId">
                          <el-select v-model="baseForm.areaId" filterable placeholder="请选择所在小区">
                            <el-option v-for="item in selectAreaList" :key="item.id" :label="item.name"
                                       :value="item.id"/>
                          </el-select>
                        </el-form-item>
                      </el-col>
                    </el-form-item>
                    <el-form-item label="房源标题">
                      <span slot="label"><span style="color: red">*&nbsp;</span>房源标题</span>
                      <el-col>
                        <el-form-item prop="houseTitle">
                          <el-input v-model="baseForm.houseTitle"></el-input>
                        </el-form-item>
                      </el-col>
                    </el-form-item>
                    <el-form-item label="房屋户型">
                      <span slot="label"><span style="color: red">*&nbsp;</span>房屋户型</span>
                      <el-col :span="6">
                        <el-form-item prop="roomNumber">
                          <el-input onkeyup="this.value=this.value.replace(/\D/g,'')" v-model="baseForm.roomNumber" class="my-center-input">
                            <template slot="prefix">&nbsp;共</template>
                            <template slot="suffix">室&nbsp;</template>
                          </el-input>
                        </el-form-item>
                      </el-col>
                      <el-col :span="6">
                        <el-form-item prop="hallNumber">
                          <el-input onkeyup="this.value=this.value.replace(/\D/g,'')" v-model="baseForm.hallNumber" class="my-center-input">
                            <template slot="prefix">&nbsp;共</template>
                            <template slot="suffix">厅&nbsp;</template>
                          </el-input>
                        </el-form-item>
                      </el-col>
                      <el-col :span="6">
                        <el-form-item prop="cloakroomNumber">
                          <el-input onkeyup="this.value=this.value.replace(/\D/g,'')" v-model="baseForm.cloakroomNumber" class="my-center-input">
                            <template slot="prefix">&nbsp;共</template>
                            <template slot="suffix">卫&nbsp;</template>
                          </el-input>
                        </el-form-item>
                      </el-col>
                      <el-col :span="6">
                        <el-form-item prop="areaCovered">
                          <el-input v-model="baseForm.areaCovered" class="my-center-input">
                            <template slot="prefix">&nbsp;共</template>
                            <template slot="suffix">㎡&nbsp;</template>
                          </el-input>
                        </el-form-item>
                      </el-col>
                    </el-form-item>
                    <el-form-item label="楼层信息">
                      <span slot="label"><span style="color: red">*&nbsp;</span>楼层信息</span>
                      <el-col :span="6">
                        <el-form-item prop="floor">
                          <el-input onkeyup="this.value=this.value.replace(/\D/g,'')" v-model="baseForm.floor" class="my-center-input">
                            <template slot="prefix">&nbsp;第</template>
                            <template slot="suffix">层&nbsp;</template>
                          </el-input>
                        </el-form-item>
                      </el-col>
                      <el-col :span="6">
                        <el-form-item prop="totalFloor">
                          <el-input onkeyup="this.value=this.value.replace(/\D/g,'')" v-model="baseForm.totalFloor" class="my-center-input">
                            <template slot="prefix">&nbsp;共</template>
                            <template slot="suffix">层&nbsp;</template>
                          </el-input>
                        </el-form-item>
                      </el-col>
                      <el-col :span="6">
                        <el-form-item prop="hasElevator">
                          <el-select v-model="baseForm.hasElevator" placeholder="有/无电梯">
                            <el-option label="有电梯" :value="true"></el-option>
                            <el-option label="没有电梯" :value="false"></el-option>
                          </el-select>
                        </el-form-item>
                      </el-col>
                      <el-col :span="6">
                        <el-form-item prop="hasParkingPlace">
                          <el-select v-model="baseForm.hasParkingPlace" placeholder="有/无车位">
                            <el-option label="有车位" :value="true"></el-option>
                            <el-option label="没有车位" :value="false"></el-option>
                          </el-select>
                        </el-form-item>
                      </el-col>
                    </el-form-item>
                    <el-form-item label="房屋朝向">
                      <span slot="label"><span style="color: red">*&nbsp;</span>房屋朝向</span>
                      <el-col :span="8">
                        <el-form-item prop="orientationId">
                          <el-select v-model="baseForm.orientationId" placeholder="请选择朝向">
                            <el-option
                              v-for="item in orientationList"
                              :key="item.id"
                              :label="item.orientation"
                              :value="item.id">
                            </el-option>
                          </el-select>
                        </el-form-item>
                      </el-col>
                    </el-form-item>
                  </div>
                </el-form>
                <el-form ref="rentForm" :model="rentForm" :rules="rentRules">
                  <div style="margin: 20px 0 20px 0;">
                    <div style="margin: 20px 0 20px 0;" id="rentForm"><b>租金信息</b></div>
                    <el-form-item label="每月租金">
                      <span slot="label"><span style="color: red">*&nbsp;</span>每月租金</span>
                      <el-col :span="6">
                        <el-form-item prop="monthlyRent">
                          <el-input v-model="rentForm.monthlyRent" class="my-center-input">
                            <template slot="suffix">元/月&nbsp;</template>
                          </el-input>
                        </el-form-item>
                      </el-col>
                    </el-form-item>
                    <el-form-item label="付款方式">
                      <span slot="label"><span style="color: red">*&nbsp;</span>付款方式</span>
                      <el-col :span="4">
                        <el-form-item prop="payNumber">
                          <el-input onkeyup="this.value=this.value.replace(/\D/g,'')" v-model="rentForm.payNumber" class="my-center-input">
                            <template slot="prefix">&nbsp;付</template>
                            <template slot="suffix">月&nbsp;</template>
                          </el-input>
                        </el-form-item>
                      </el-col>
                      <el-col :span="4">
                        <el-form-item prop="mortgageNumber">
                          <el-input onkeyup="this.value=this.value.replace(/\D/g,'')" v-model="rentForm.mortgageNumber" class="my-center-input">
                            <template slot="prefix">&nbsp;压</template>
                            <template slot="suffix">月&nbsp;</template>
                          </el-input>
                        </el-form-item>
                      </el-col>
                    </el-form-item>
                    <el-form-item label="租金包含">
                      <span slot="label">&nbsp;&nbsp;&nbsp;租金包含</span>
                      <el-col :span="18">
                        <el-form-item>
                          <el-checkbox-group v-model="rentForm.rentContentIds">
                            <template v-for="rentContent in rentContentList">
                              <el-checkbox :label="rentContent.id">{{ rentContent.rentContentName }}</el-checkbox>
                            </template>
                          </el-checkbox-group>
                        </el-form-item>
                      </el-col>
                    </el-form-item>
                  </div>
                </el-form>
                <el-form ref="detailedForm" :model="detailedForm">
                  <div style="margin: 20px 0 20px 0;">
                    <div style="margin: 20px 0 20px 0;" id="detailedForm"><b>详细介绍</b></div>
                    <el-form-item label="最早入住">
                      <span slot="label">&nbsp;&nbsp;&nbsp;最早入住</span>
                      <el-col :span="8">
                        <el-form-item prop="earliestInTime">
                          <el-date-picker
                            v-model="detailedForm.earliestInTime"
                            type="date"
                            value-format="yyyy-MM-dd"
                            placeholder="选择日期">
                          </el-date-picker>
                        </el-form-item>
                      </el-col>
                      <el-col :span="4">
                        <el-form-item prop="">
                          <el-input onkeyup="this.value=this.value.replace(/\D/g,'')" v-model="detailedForm.livablePeopleNumber" class="my-center-input">
                            <template slot="prefix">宜住</template>
                            <template slot="suffix">人</template>
                          </el-input>
                        </el-form-item>
                      </el-col>
                    </el-form-item>
                    <el-form-item label="看房时间">
                      <span slot="label">&nbsp;&nbsp;&nbsp;看房时间</span>
                      <el-col :span="18">
                        <el-form-item prop="lookingHouseTimeState">
                          <el-radio-group v-model="detailedForm.lookingHouseTimeState">
                            <el-radio :label="0">仅周末</el-radio>
                            <el-radio :label="1">仅工作日</el-radio>
                            <el-radio :label="2">工作日晚上和周末</el-radio>
                            <el-radio :label="3">随时看房</el-radio>
                          </el-radio-group>
                        </el-form-item>
                      </el-col>
                    </el-form-item>
                    <el-form-item label="装修情况">
                      <span slot="label">&nbsp;&nbsp;&nbsp;装修情况</span>
                      <el-col :span="18">
                        <el-form-item prop="decorationState">
                          <el-radio-group v-model="detailedForm.decorationState">
                            <el-radio :label="0">毛胚</el-radio>
                            <el-radio :label="1">简单装修</el-radio>
                            <el-radio :label="2">精装修</el-radio>
                            <el-radio :label="3">豪华装修</el-radio>
                          </el-radio-group>
                        </el-form-item>
                      </el-col>
                    </el-form-item>
                    <el-form-item label="房屋配置">
                      <span slot="label">&nbsp;&nbsp;&nbsp;房屋配置</span>
                      <el-col :span="18">
                        <el-form-item>
                          <el-checkbox-group v-model="detailedForm.baseFacilitiesIds">
                            <template v-for="facilities in facilitiesList">
                              <el-checkbox :label="facilities.id">{{ facilities.name }}</el-checkbox>
                            </template>
                          </el-checkbox-group>
                        </el-form-item>
                      </el-col>
                    </el-form-item>
                    <el-form-item label="房屋亮点">
                      <span slot="label">&nbsp;&nbsp;&nbsp;房屋亮点</span>
                      <el-col :span="18">
                        <el-form-item>
                          <el-checkbox-group v-model="detailedForm.baseHighlightIds">
                            <template v-for="highlight in highlightList">
                              <el-checkbox :label="highlight.id">{{ highlight.name }}</el-checkbox>
                            </template>
                          </el-checkbox-group>
                        </el-form-item>
                      </el-col>
                    </el-form-item>
                    <el-form-item label="出租要求">
                      <span slot="label">&nbsp;&nbsp;&nbsp;出租要求</span>
                      <el-col :span="18">
                        <el-form-item>
                          <el-checkbox-group v-model="detailedForm.baseRentalRequirementIds">
                            <template v-for="rentalRequirement in rentalRequirementsList">
                              <el-checkbox :label="rentalRequirement.id">{{ rentalRequirement.name }}</el-checkbox>
                            </template>
                          </el-checkbox-group>
                        </el-form-item>
                      </el-col>
                    </el-form-item>
                    <el-form-item label="房源描述">
                      <span slot="label">&nbsp;&nbsp;&nbsp;房源描述</span>
                      <el-col :span="18">
                        <el-form-item prop="houseDetailedDescription">
                          <el-input
                            type="textarea"
                            :autosize="{ minRows: 5}"
                            :placeholder="textAreaPlaceholder"
                            v-model="detailedForm.houseDetailedDescription">
                          </el-input>
                        </el-form-item>
                      </el-col>
                    </el-form-item>
                  </div>
                </el-form>
                <el-form ref="imageForm">
                  <div style="margin: 20px 0 20px 0;">
                    <div style="margin: 20px 0 20px 0;" id="imageForm"><b>房源图片</b></div>
                    <el-form-item label="上传图片">
                      <span slot="label"><span style="color: red">*&nbsp;</span>上传图片</span>
                      <el-col :span="18">
                        <el-row>
                          <div style=" font-size: 14px; color: #999;line-height: 24px;">
                            请上传清晰、实拍的室内图片，请不要在图片上添加文字、数字、网址等内容，请勿上传名片、二维码、自拍照、风景照等与房源无关的图片，最多上传12张，每张最大10M
                          </div>
                        </el-row>
                        <el-row style="margin-top: 25px">
                          <multi-upload fileDir="house/" v-model="imageForm" @multiUpload="multiUpload"></multi-upload>
                        </el-row>
                      </el-col>
                    </el-form-item>
                  </div>
                </el-form>
                <el-form ref="contactForm" :model="contactForm" :rules="contactRules">
                  <div style="margin: 20px 0 20px 0;">
                    <div style="margin: 20px 0 20px 0;" id="contactForm"><b>联系信息</b></div>
                    <el-form-item label="昵称">
                      <span slot="label"><span style="color: red">*&nbsp;</span>昵&nbsp;称&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
                      <el-col :span="8">
                        <el-form-item prop="publisherName">
                          <el-input v-model="contactForm.publisherName" :disabled="true"></el-input>
                        </el-form-item>
                      </el-col>
                      <el-col :span="8">
                        <span style="color: #999">如需修改请到个人中心修改</span>
                      </el-col>
                    </el-form-item>
                    <el-form-item label="身份选择">
                      <span slot="label"><span style="color: red">*&nbsp;</span>身份选择</span>
                      <el-col :span="8">
                        <el-form-item prop="publisherIdentity">
                          <el-select v-model="contactForm.publisherIdentity" placeholder="请选择">
                            <el-option label="个人房东" value="0"></el-option>
                            <el-option label="个人转租" value="1"></el-option>
                            <el-option label="职业房东" value="2"></el-option>
                          </el-select>
                        </el-form-item>
                      </el-col>
                    </el-form-item>
                    <el-form-item label="联系电话">
                      <span slot="label"><span style="color: red">*&nbsp;</span>联系电话</span>
                      <el-col :span="8">
                        <el-form-item prop="contactPhone">
                          <el-input v-model="contactForm.contactPhone"></el-input>
                        </el-form-item>
                      </el-col>
                    </el-form-item>
                    <el-form-item label="接听时段">
                      <span slot="label"><span style="color: red">*&nbsp;</span>接听时段</span>
                      <el-col :span="8">
                        <el-form-item prop="answerPeriodTimeStart">
                          <el-time-select
                            placeholder="起始时间"
                            v-model="contactForm.answerPeriodTimeStart"
                            :picker-options="{start: '00:00',step: '01:00',end: '24:00'}">
                          </el-time-select>
                        </el-form-item>
                      </el-col>
                      <el-col :span="1">&nbsp;-</el-col>
                      <el-col :span="8">
                        <el-form-item prop="answerPeriodTimeEnd">
                          <el-time-select
                            placeholder="结束时间"
                            v-model="contactForm.answerPeriodTimeEnd"
                            :picker-options="{start: '00:00',step: '01:00',end: '24:00',minTime:contactForm.answerPeriodTimeStart}">
                          </el-time-select>
                        </el-form-item>
                      </el-col>
                    </el-form-item>
                  </div>
                </el-form>
              </el-col>
              <el-col style="text-align: center;margin-top: 20px">
                <el-button size="medium" type="danger" style="width: 30%" @click="toPublish">发布</el-button>
              </el-col>
            </el-tab-pane>
          </el-tabs>
        </el-card>
      </el-col>
      <el-col :span="4">
        <el-card style="position: fixed;top: 30.1%;width: 183px">
          <div style="height: 300px;">
            <el-steps direction="vertical" :active="stepActive">
              <el-step title="基础信息">
                <template slot="title">
                  <el-link :underline="false" href="#baseForm"
                           :class="stepActive>0?'my-step-title-success':'my-step-title-error'">基础信息
                  </el-link>
                  <template v-if="this.baseFlag !== undefined">
                    <i :class="this.baseFlag?'el-icon-success':'el-icon-error'"></i>
                  </template>
                </template>
              </el-step>
              <el-step title="租金信息">
                <template slot="title">
                  <el-link :underline="false" href="#rentForm"
                           :class="stepActive>1?'my-step-title-success':'my-step-title-error'">租金信息
                  </el-link>
                  <template v-if="this.rentFlag !== undefined">
                    <i :class="this.rentFlag?'el-icon-success':'el-icon-error'"></i>
                  </template>
                </template>
              </el-step>
              <el-step title="详细介绍">
                <template slot="title">
                  <el-link :underline="false" href="#detailedForm"
                           :class="stepActive>2?'my-step-title-success':'my-step-title-error'">详细介绍
                  </el-link>
                  <template v-if="this.detailedFlag !== undefined">
                    <i :class="this.detailedFlag?'el-icon-success':'el-icon-error'"></i>
                  </template>
                </template>
              </el-step>
              <el-step title="房源图片">
                <template slot="title">
                  <el-link :underline="false" href="#imageForm"
                           :class="stepActive>3?'my-step-title-success':'my-step-title-error'">房源图片
                  </el-link>
                  <template v-if="this.imageFlag !== undefined">
                    <i :class="this.imageFlag?'el-icon-success':'el-icon-error'"></i>
                  </template>
                </template>
              </el-step>
              <el-step title="联系信息">
                <template slot="title">
                  <el-link :underline="false" href="#contactForm"
                           :class="stepActive>4?'my-step-title-success':'my-step-title-error'">联系信息
                  </el-link>
                  <template v-if="this.contactFlag !== undefined">
                    <i :class="this.contactFlag?'el-icon-success':'el-icon-error'"></i>
                  </template>
                </template>
              </el-step>
            </el-steps>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
// 这里可以导入其他文件（比如：组件，工具js，第三方插件js，json文件，图片文件等等）
// 例如：import 《组件名称》 from ‘《组件路径》‘;

import cookie from 'js-cookie'
import singleUpload from '@/components/upload/singleUpload'
import multiUpload from '@/components/upload/multiUpload'

export default {
  // import引入的组件需要注入到对象中才能使用
  components: {
    singleUpload,
    multiUpload
  },
  async asyncData ({
    $axios,
    params
  }) {
    let [
      orientationList,
      facilitiesList,
      highlightList,
      rentalRequirementsList,
      rentContentList,
      publishBaseInfo
    ] = await Promise.all([
      await $axios.get(`/house/baseorientation/getAll`),
      await $axios.get(`/house/basefacilities/getAll`),
      await $axios.get(`/house/basehighlight/getAll`),
      await $axios.get(`/house/baserentalrequirements/getAll`),
      await $axios.get(`/house/baserentcontent/getAll`),
      await $axios.get(`/house/housebaseinfo/getThirdCity/${params.cityId}`),
    ])
    return {
      cityId: params.cityId,
      orientationList: orientationList.data,
      facilitiesList: facilitiesList.data,
      highlightList: highlightList.data,
      rentalRequirementsList: rentalRequirementsList.data,
      rentContentList: rentContentList.data,
      roadList: publishBaseInfo.data.roadList,
      areaList: publishBaseInfo.data.areaList,
      cityList: publishBaseInfo.data.cityList
    }
  },
  data () {
    // 这里存放数据
    return {
      userInfo: {},
      cityId: '',
      cityList: [],
      roadList: [],
      selectRoadList: [],
      areaList: [],
      selectAreaList: [],
      stepActive: 0,
      baseForm: {
        cityId: '',
        cityName: '',
        roadId: '',
        areaId: '',
        areaName: '',
        //房源标题
        houseTitle: '',
        //多少室
        roomNumber: '',
        //多少厅
        hallNumber: '',
        //多少卫生间
        cloakroomNumber: '',
        //占地面积
        areaCovered: '',
        //总层数
        totalFloor: '',
        //所在层数
        floor: '',
        //是否有电梯
        hasElevator: '',
        //是否有车位
        hasParkingPlace: '',
        //房屋朝向id
        orientationId: ''
      },
      baseRules: {
        //区域id
        cityId: [
          {
            required: true,
            message: '请选择所在区域',
            trigger: ['blur', 'change']
          }
        ],
        //区域id
        roadId: [
          {
            required: true,
            message: '请选择所在交通路',
            trigger: ['blur', 'change']
          }
        ],
        //区域id
        areaId: [
          {
            required: true,
            message: '请选择所在小区',
            trigger: ['blur', 'change']
          }
        ],
        //房源标题
        areaName: [
          {
            required: true,
            message: '请输入小区名称',
            trigger: 'blur'
          }
        ],
        //房源标题
        houseTitle: [
          {
            required: true,
            message: '请输入房源标题',
            trigger: 'blur'
          }
        ],
        //多少室
        roomNumber: [
          {
            required: true,
            message: '请输入房源有多少房间',
            trigger: 'blur'
          }
        ],
        //多少厅
        hallNumber: [
          {
            required: true,
            message: '请输入房源有多少客厅',
            trigger: 'blur'
          }
        ],
        //多少卫生间
        cloakroomNumber: [
          {
            required: true,
            message: '请输入房源有多少卫生间',
            trigger: 'blur'
          }
        ],
        //占地面积
        areaCovered: [
          {
            required: true,
            message: '请输入房源的占地面积',
            trigger: 'blur'
          }
        ],
        //总层数
        totalFloor: [
          {
            required: true,
            message: '请输入房源所在的总层数',
            trigger: 'blur'
          }
        ],
        //所在层数
        floor: [
          {
            required: true,
            message: '请输入房源所在楼层',
            trigger: 'blur'
          }
        ],
        //是否有电梯
        hasElevator: [
          {
            required: true,
            message: '请选择房源是否有电梯',
            trigger: ['blur', 'change']
          }
        ],
        //是否有车位
        hasParkingPlace: [
          {
            required: true,
            message: '请选择房源是否有车位',
            trigger: ['blur', 'change']
          }
        ],
        //房屋朝向id
        orientationId: [
          {
            required: true,
            message: '请选择房源朝向',
            trigger: ['blur', 'change']
          }
        ]
      },
      baseFlag: undefined,
      rentForm: {
        //每月租金
        monthlyRent: '',
        //付
        payNumber: '',
        //压
        mortgageNumber: '',
        //租金内容id
        rentContentIds: []
      },
      rentRules: {
        monthlyRent: [
          {
            required: true,
            message: '请输入房源每月租金',
            trigger: 'blur'
          }
        ],
        payNumber: [
          {
            required: true,
            message: '请输入房源每次付多少月租金',
            trigger: 'blur'
          }
        ],
        mortgageNumber: [
          {
            required: true,
            message: '请输入房源需要抵押多少月租金',
            trigger: 'blur'
          }
        ],
        rentContentIds: [
          {
            required: true,
            message: '请输入房源租金包含内容',
            trigger: 'blur'
          }
        ],
      },
      rentFlag: undefined,
      detailedForm: {
        //最早入住时间
        earliestInTime: '',
        //宜居人数
        livablePeopleNumber: '',
        //看房时间
        lookingHouseTimeState: '',
        //装修情况
        decorationState: '',
        //房屋设施ids
        baseFacilitiesIds: [],
        //房屋亮点ids
        baseHighlightIds: [],
        //房屋要求ids
        baseRentalRequirementIds: [],
        //房源详细描述
        houseDetailedDescription: '',
        //房源详细地址
        houseDetailedAddress: '',
      },
      detailedRules: {
        //最早入住时间
        earliestInTime: [
          {
            required: true,
            message: '请输入房源最早入住时间',
            trigger: 'blur'
          }
        ],
        //宜居人数
        livablePeopleNumber: [
          {
            required: true,
            message: '请输入房源宜居人数',
            trigger: 'blur'
          }
        ],
        //看房时间
        lookingHouseTimeState: [
          {
            required: true,
            message: '请输入房源看房时间',
            trigger: 'blur'
          }
        ],
        //装修情况
        decorationState: [
          {
            required: true,
            message: '请输入房源装修情况',
            trigger: 'blur'
          }
        ],
        //房屋设施ids
        baseFacilitiesIds: [
          {
            required: true,
            message: '请输入房源房屋设施ids',
            trigger: 'blur'
          }
        ],
        //房屋亮点ids
        baseHighlightIds: [
          {
            required: true,
            message: '请输入房源房屋亮点ids',
            trigger: 'blur'
          }
        ],
        //房屋要求ids
        baseRentalRequirementIds: [
          {
            required: true,
            message: '请输入房源房屋要求ids',
            trigger: 'blur'
          }
        ],
        //房源详细描述
        houseDetailedDescription: [
          {
            required: true,
            message: '请输入房源房源详细描述',
            trigger: 'blur'
          }
        ],
        //房源详细地址
        houseDetailedAddress: [
          {
            required: true,
            message: '请输入房源房源详细地址',
            trigger: 'blur'
          }
        ]
      },
      detailedFlag: undefined,
      imageForm: [],
      imageFlag: undefined,
      contactForm: {
        //发布人昵称
        publisherName: '',
        //发布人身份
        publisherIdentity: '0',
        //联系人电话
        contactPhone: '',
        //接听时段_start
        answerPeriodTimeStart: '',
        //接听时段_end
        answerPeriodTimeEnd: '',
      },
      contactRules: {
        //发布人昵称
        publisherName: [
          {
            required: true,
            message: '发布人昵称',
            trigger: 'blur'
          }
        ],
        //发布人身份
        publisherIdentity: [
          {
            required: true,
            message: '请输入发布人身份',
            trigger: 'blur'
          }
        ],
        //联系人电话
        contactPhone: [
          {
            required: true,
            message: '请输入联系人电话',
            trigger: 'blur'
          }
        ],
        //接听时段_start
        answerPeriodTimeStart: [
          {
            required: true,
            message: '请输入接听时段',
            trigger: 'blur'
          }
        ],
        //接听时段_end
        answerPeriodTimeEnd: [
          {
            required: true,
            message: '请输入接听时段',
            trigger: 'blur'
          }
        ]
      },
      contactFlag: undefined,
      orientationList: [],
      rentContentList: [],
      facilitiesList: [],
      highlightList: [],
      rentalRequirementsList: [],
      textAreaPlaceholder: '可以介绍一下房源亮点，交通、周边环境，可以入住的时间和对租客的要求等，详细的描述会大大增加快速出租的机会！\n请不要在描述中包含：\n1.任意形式的联系方式及变型词；\n2.与房源或相关配套描述无关的内容；\n3.违反国家法律法规的内容等"'
    }
  },
  // 方法集合
  methods: {
    multiUpload (value) {
      this.imageForm = value
    },
    changeRoadList (value) {
      let roadList = this.roadList
      this.selectRoadList = []
      this.selectAreaList = []
      this.baseForm.roadId = ''
      this.baseForm.areaId = ''
      for (let i = 0; i < roadList.length; i++) {
        if (roadList[i].cityId === value) {
          this.selectRoadList.push(roadList[i])
        }
      }
    },
    changeAreaList (value) {
      let areaList = this.areaList
      this.selectAreaList = []
      this.baseForm.areaId = ''
      for (let i = 0; i < areaList.length; i++) {
        if (areaList[i].superiorId === value) {
          this.selectAreaList.push(areaList[i])
        }
      }
    },
    toPublish () {
      if (this.validateAll()) {
        // TODO 临时设置默认图片
        let imageForm = this.imageForm
        imageForm[0].isDefaultImage=1
        this.$axios.post('/house/housebaseinfo/submitPublishBaseInfo', {
          publishId: this.userInfo.id,
          baseInfo: this.baseForm,
          rentInfo: this.rentForm,
          detailedInfo: this.detailedForm,
          imageInfoList: imageForm,
          contactInfo: this.contactForm,
        }).then(response => {
          if(response!==undefined){
            console.log(response)
            this.$router.push({path:'/business/publish/waitForExamine'})
          }
        })
      } else {
        this.$message.error('submit error')
      }
    },
    validateAll () {
      this.validateBaseForm()
      this.validateRentForm()
      this.validateImageForm()
      this.validateDetailedForm()
      this.validateContactForm()
      return this.baseFlag && this.rentFlag && this.detailedFlag && this.imageFlag && this.contactFlag
    },
    validateBaseForm () {
      this.$refs.baseForm.validate((valid) => {
        this.baseFlag = valid
        if (!valid) {
          return false
        }
      })
    },
    validateRentForm () {
      this.$refs.rentForm.validate((valid) => {
        this.rentFlag = valid
      })
    },
    validateDetailedForm () {
      this.$refs.detailedForm.validate((valid) => {
        this.detailedFlag = valid
      })
    },
    validateImageForm () {
      this.imageFlag = this.imageForm.length > 0
    },
    validateContactForm () {
      this.$refs.contactForm.validate((valid) => {
        this.contactFlag = valid
      })
    },
    handleScroll () {
      let baseFormTop = this.$refs.baseForm.$el.getBoundingClientRect().top
      let rentFormTop = this.$refs.rentForm.$el.getBoundingClientRect().top
      let detailedFormTop = this.$refs.detailedForm.$el.getBoundingClientRect().top
      let imageFormTop = this.$refs.imageForm.$el.getBoundingClientRect().top
      let contactFormTop = this.$refs.contactForm.$el.getBoundingClientRect().top
      if (baseFormTop > -100 && baseFormTop < 0) {
        this.validateBaseForm()
      }
      if (rentFormTop > -100 && rentFormTop < 0) {
        this.validateRentForm()
      }
      if (detailedFormTop > -100 && detailedFormTop < 0) {
        this.validateDetailedForm()
      }
      if (imageFormTop > -100 && imageFormTop < 0) {
        this.validateImageForm()
      }
      if (contactFormTop > -100 && contactFormTop < 0) {
        this.validateContactForm()
      }

      // const clientHeight = document.documentElement.clientHeight
      // const scrollTop = window.pageYOffset || document.documentElement.scrollTop || document.body.scrollTop

      // if(clientHeight+scrollTop > expHeight) {
      //   alert('123')
      // }
    }
  },
  // 监听属性 类似于data概念
  computed: {
    validFlag () {
      const {
        baseFlag,
        rentFlag,
        detailedFlag,
        imageFlag,
        contactFlag
      } = this
      return {
        baseFlag,
        rentFlag,
        detailedFlag,
        imageFlag,
        contactFlag
      }
    }
  },
  // 监控data中的数据变化
  watch: {
    validFlag (flags) {
      this.stepActive = 0
      if (flags.baseFlag === true) {
        this.stepActive = 1
        if (flags.rentFlag === true) {
          this.stepActive = 2
          if (flags.detailedFlag === true) {
            this.stepActive = 3
            if (flags.imageFlag === true) {
              this.stepActive = 4
              if (flags.contactFlag === true) {
                this.stepActive = 5
              }
            }
          }
        }
      }
    }
  },
  // 生命周期 - 创建完成（可以访问当前this实例）
  created () {
    if (cookie.get('userToken')) {
      this.$axios.get('/user/login/userInfoByToken')
        .then(response => {
          let userInfo = response.userInfo
          this.contactForm.publisherName = userInfo.nickName
          this.contactForm.contactPhone = userInfo.phone
          this.userInfo = userInfo
        })
    } else {
      this.$router.push({ path: '/login' })
    }
  },
  // 生命周期 - 挂载完成（可以访问DOM元素）
  mounted () {
    window.addEventListener('scroll', this.handleScroll)
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
    window.removeEventListener('scroll', this.handleScroll)
  },
  // 如果页面有keep-alive缓存功能，这个函数会触发
  activated () {
  }
}
</script>

<style scoped>
.my-center-input /deep/ .el-input__inner {
  text-align: center;
}

.my-step-title-success {
  font-size: 16px;
  line-height: 24px;
  color: #409EFF;
}

.my-step-title-error {
  font-size: 16px;
  line-height: 24px;
  color: #C0C4CC;
}
</style>
