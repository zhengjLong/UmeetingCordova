import Vue from 'vue'
import Vuex from 'vuex'
Vue.use(Vuex)
const state = {
  umeetingAccount: '',
  /*会议列表的定时器*/
  meetingListSetInter: '',
  /*会议控制*/
  controll:{
    lunxun:{
      value: '',
      options: [],
      rollingStart: '开始轮询'
    },
    meetingNow: {},
    participant:{
      searchVal: '',
      list: [],
      participantList: [],
      timing: '',
      connectNum: 0
    },
    addMeetingRoom:{
      searchVal: '',
      list: [],
      roomList: [],
      value1: []
    }
  },
  /*当前会议信息*/
  nowMeetingInformation: '',
  /*会议是添加还是修改*/
  meetingStatus: '',
  /*登陆页面参数*/
  login: {
    ip: '',
    user: '',
    pass: ''
  },
  tabBar: 'conference',
  /*网络会议更多设置*/
  networkmore: {
    nowStartMeeting: true, /*是否在主持会议前加入*/
    toolName: ['只使用电话', '只使用VOIP', '使用电话和VOIP'],
    tabIndex: 2/*记录选择的toolName是第几个*/
  },
  /*会议预约和修改参数*/
  information: {
    name: '',//会议名称
    meetingPassword: '',//会议密码
    description: '',//会议描述
    titleName: '',//header标题
    nowStartMeeting: false,//是否立即开始会议
    pickerValue: '',//电话会议选择时间
    pickerTime: '',//电话会议时间显示
    videotape: false,//自动录像
    live: false,//直播
    banwidthName: '自动',//带宽
    bandwidthVisible: false,//带宽显示
    bandwidthArr: ['自动', '512kb', '768kb', '1.00M', '1.50M', '2.00M', '3.00M', '4.00M', '6.00M', '8.00M', '10.00M', '15.00M', '20.00M', '30.00M']
  },
  /*会议室参数*/
  meetingRoom: {
    value1: [],//已选会议室（多选）
    meetingRoomUuids: '',//会议室的uuid
    searchRoom: '',//搜索字段
    start_time: '',//开始时间组件
    pickerStartTime: '',//开始时间文本显示
    duration: '01:00',//时长
    RoomList: [],//会议室列表
    list: [],//会议搜索复制列表
    type: ''//判断当前是本地会议还是视频会议
  },
  /*组织机构参数*/
  org: {
    levelName: [], /*组别*/
    leveList: [], /*分组列表*/
    userList: [], /*用户列表*/
    select: false, /*全选*/
    searchUser: '', /*搜索字段*/
    value1: [], /*已选择用户*/
    list: []/*搜索复制列表*/
  },
  /*通讯录*/
  contacts: {
    contactsOptions: [],
    searchList: []
  },
  /*联系人*/
  conditions: {
    levelList: [], /*分组列表*/
    userList: [], /*用户列表*/
    list: [],//复制列表
    tabIndex: ''/*当前显示的组别列表*/
  },
  /*参会人页面*/
  attendees: {
    userList: []
  },
  //会议列表
  meetingList: {
    list: [],
    meetingList: []
  },
  /*登陆成功返回的参数*/
  loginData: {},
  /**/
  meetingAdd: {},
  /*修改会议及预约会议所传的参数*/
  addMeetingParams: {},
  /*单个会议室详情*/
 /* meetingRoomDetails:{
    name:'',//名称
    address: '',
    type:'',//视频会议室0，本地会议室1，移动会议室2
    galleryful: '',//容纳人数
    configuration: ''//配套设备
  },*/
  meetingRoomDetails :{

  },
  /*当前会议的会议室列表*/
  roomDetailsList: {
    list: [],
    searchVal: ''
  },
  /*当前会议的参会人员列表*/
  usersDetailsList: {
    list: [],
    searchVal: ''
  },
  /*所有的接口*/
  api: {
    login: '/api/app/loggin',//登陆
    meetingList: '/api/meeting/app/query',//会议列表
    meetingAddLocal: '/api/meeting/app/addLocal',//添加本地会议
    meetingAddVideo: '/api/meeting/app/addVideo',//添加视频会议
    meetingAddPhone: '/api/meeting/app/addPhone',//添加电话会议
    meetingUpdateLocal: '/api/meeting/app/updateLocal',//修改本地会议
    meetingUpdateVideo: '/api/meeting/app/updateVideo',//修改视频会议
    meetingUpdatePhone: '/api/meeting/app/updatePhone',//修改电话会议
    umeetingCreate: '/api/umeeting/app/create',//添加网络会议
    umeetingUpdate: '/api/umeeting/app/update',//修改网络会议
    meetingDelete: '/api/meeting/app/delete',//删除会议
    meetingEnd: '/api/meeting/app/end',//结束本地/视频/电话会议
    umeetingEnd: '/api/umeeting/app/end/',//结束网络会议
    participantAll: '/api/participant/app/queryAll',//获取与会者列表
    participantAdd: '/api/participant/app/add',//视频会议临时添加会议室
    participantpPhoneDial: '/api/participant/app/phoneDial',//电话会议添加参会人、临时添加参会人
    participantpDelete: '/api/participant/app/delete',//删除与会者
    participantpCall: '/api/participant/app/call',//连接/断开与会者
    participantpChair: '/api/participant/app/chair',//设置/取消主席
    participantpFloor: '/api/participant/app/floor',//设置/取消发言人
    participantpMicrophone: '/api/participant/app/microphone',//打开/关闭麦克风
    participantpLayout: '/api/participant/app/layout',//设置与会者分屏
    meetingCall: '/api/meeting/app/call',//终端全开/全关
    meetingMicrophone: '/api/meeting/app/microphone',//麦克风全开/全关
    meetingDelay: '/api/meeting/app/delay',//会议延时
    meetingMode: '/api/meeting/app/mode',//切换会议模式
    meetingMessage: '/api/meeting/app/message',//发送消息（即字幕）
    meetingRolling: '/api/meeting/app/rolling',//轮询控制
    meetingRollingStatus: '/api/meeting/app/rollingStatus',//查询轮询状态
    meetingLayout: '/api/meeting/app/layout',//设置会议分屏
    meetingCanUseMR: '/api/meeting/app/queryCanUseMR',//查询除此会议外可以使用的会议室
    meetingRoomOccupy: '/api/meeting/app/isRoomOccupy',//添加或更新预约会议时，会议室占用情况
    org: '/api/org/app/query',//查询所有组织机构
    queryOrgById: '/api/org/app/queryOrgById',//查询所有组织机构
    userList: '/api/user/app/query',//根据条件查询用户列表
    importHead: '/api/user/app/importHead',//头像上传
    userByConditions: '/api/contact/app/queryUserByConditions',//获取我的联系人
    findAllGroup: '/api/contact/findAllGrop',//获取我的联系人
    contact: '/api/contact/query',//获取我的联系人
    queryByUuid: '/api/meeting/app/queryByUuid',//当前会议的状态
    participantDial: '/api/participant/app/dial',//视频会议呼叫外部会议室
    scan: '/api/meeting/app/scan',//二维码
    umeetingUser: '/umeet_bg/user/getInfo?host_id='
  },
  timestampToTime : function (timestamp) {
    let date = new Date(timestamp);//时间戳为10位需*1000，时间戳为13位的话不需乘1000
    let Y = date.getFullYear() + '-';
    let M = (date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1) + '-';
    let D = date.getDate() < 10 ? '0' + date.getDate() : date.getDate();
    let days = date.getDay();
    switch (days) {
      case 1:
        days = '星期一';
        break;
      case 2:
        days = '星期二';
        break;
      case 3:
        days = '星期三';
        break;
      case 4:
        days = '星期四';
        break;
      case 5:
        days = '星期五';
        break;
      case 6:
        days = '星期六';
        break;
      case 0:
        days = '星期日';
        break;
    }
    if(date.getMonth() === new Date().getMonth()){
      switch (Number(D) - new Date().getDate()){
        case 0:
          return '今天(' + Y+M+D + ')';
          break;
        case 1:
          return '明天(' + Y+M+D + ')';
          break;
        default:
          return days + '(' + Y+M+D + ')'
      }
    }else {
      return days + '(' + Y+M+D + ')';
    }
  },
  controlling:{
    value: [],
    options: [],
    rollingStart: '开始',
    interval: 5,
    rollingType: '全局轮询'
  }
};
const mutations={
  participantAll(state,item){
    let that = this;
    let list = [];
    let count = 0;
    let mode = 0
    item.axios.post(state.api.participantAll+'?sessionId='+
      state.loginData.data.sessionId,{
      'meetingUuid' : item.uuid
    }).then(function (res) {
      // console.log(res.data.data);
       item.$indicator.close();
      if (res.data.message !== 'OK') {
        item.$toast(res.data.message);
        //alert(res.data.message)
      } else {
        if(!state.controll.participant.searchVal){
          state.controll.participant.participantList = res.data.data;
        }else {
         // console.log(state.controll.participant.searchVal)
          for(let i in res.data.data){
            if(!state.controll.participant.searchVal){
              state.controll.participant.participantList = res.data.data;
            }
            if(res.data.data[i].displayName.includes(state.controll.participant.searchVal) ||
              res.data.data[i].quanpin.includes(state.controll.participant.searchVal.toLowerCase())){
              list.push(res.data.data[i]);
            }

          }
          state.controll.participant.participantList = list;
        }
        for(let i in res.data.data){
          if(res.data.data[i].status === 'PAR_CONNECTED'){
            count += 1;
          }

        }
        state.controll.participant.connectNum = count;

      //  console.log(state.controll.participant.list);
      }
    }).catch(function (err) {
      console.log(err);
      item.$indicator.close();
     // that.$indicator.close();
    })
  },
  backcount(state,item){
    let backCount = 0;
    document.addEventListener("backbutton", function(e){
      let locationHref = window.location.href.substring(window.location.href.indexOf('#')+1,window.location.href.length);
      if(locationHref === '/' || locationHref === '/home'){
        if(backCount == 0){
          backCount ++;
          item.$toast('再点一次退出程序');
          setTimeout(function(){
            backCount = 0;
          },2000);
        }else if(backCount == 1 ){
          navigator.app.exitApp();
        }
      }else {
        window.history.back(-1)
      }
    },false)
  }
}

export default new Vuex.Store({
  state,
  mutations

});
