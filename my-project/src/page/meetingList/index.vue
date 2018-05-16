<template>
  <div class="meetingList themeBgColor">
    <mt-header fixed title="我的会议">
       <span slot="right">
        <mt-button @click.native= "saoyisao"><i class="iconfont saoyisao"></i></mt-button>
      </span>
    </mt-header>

    <!--<template v-for="item in meetingList">
      <div>{{item.timeDate}}</div>
    </template>-->

    <div class="page-loadmore-wrapper" ref="wrapper" :style="{ height: wrapperHeight + 'px' }">
      <mt-loadmore :top-method="loadTop"
                   @translate-change="translateChange" @top-status-change="handleTopChange"
                   ref="loadmore">
        <div class="main page-loadmore-list" v-if="$store.state.meetingList.meetingList.length !== 0">
          <template v-for="item in $store.state.meetingList.meetingList">
            <div class="level">{{item.timeDate}}</div>
            <div class="container_line" @click="umeetingInformation(i)" :class="{themeBorder:$store.state.loginData.data.id !== 1 &&
            $store.state.loginData.data.name !== i.compereUser.name }"
                 v-for="(i,index) in item.data">
              <mt-cell-swipe :right="[
                {
                  content: i.status === 'CONF_ONLINE' ? '结束': '删除',
                  handler: () => openConfirm(i)
                }
              ]" v-if="$store.state.loginData.data.id === 1 || $store.state.loginData.data.name === i.compereUser.name">
                <div class="line flex_auto">
                  <div class="iconImg">
                    <img :src=img.localYu  v-if="i.type === 1 && i.status !=='CONF_ONLINE'">
                    <img :src=img.localStart  v-if="i.type === 1 && i.status ==='CONF_ONLINE'">
                    <img :src=img.videoStart  v-if="i.type === 0 && i.status === 'CONF_ONLINE'">
                    <img :src=img.videoYu  v-if="i.type === 0 && i.status !== 'CONF_ONLINE'">
                    <img :src=img.umeetingYu  v-if="i.type === 3 && i.status !== 'CONF_ONLINE'">
                    <img :src=img.umeetingStart  v-if="i.type === 3 && i.status === 'CONF_ONLINE'">
                    <img :src=img.phoneYu  v-if="i.type === 2 && i.status !=='CONF_ONLINE'">
                    <img :src=img.phoneStart  v-if="i.type === 2 && i.status ==='CONF_ONLINE'">
                  </div>
                  <div class="information">
                    <div class="title">{{i.name}}</div>
                    <div class="time">{{ i.startTime | time_tamp }} - {{ (i.startTime + (i.durationSeconds*60*1000)) | time_tamp}} </div>
                    <div class="compereUser">
                      <span v-if="$store.state.loginData.data.name === i.compereUser.name">由我主持</span>
                      <span v-else>{{i.compereUser.name}} 主持</span>
                    </div>

                  </div>
                  <div class="controll" v-if="$store.state.loginData.data.name === i.compereUser.name">
                    <div v-if="i.status ==='CONF_ONLINE' && i.type !== 1 && i.type !== 3" @click="umeetingControll(i,index)">控制</div>
                    <div v-if="i.status === 'CONF_NOAPPROVA' || i.status ==='CONF_SCHEDULE' && i.node === 0" @click="umeetingUpdate(i)">修改</div>
                    <div v-if="i.status ==='CONF_REJECTED' && i.node === 1" @click="umeetingUpdate(i)">修改</div>
                  </div>
                  <div class="controll" v-if="$store.state.loginData.data.id === 1">
                    <div v-if="i.status ==='CONF_ONLINE' && i.type !== 1 && i.type !== 3" @click="umeetingControll(i,index)">控制</div>
                    <div v-if="i.status ==='CONF_SCHEDULE' && i.status !=='CONF_NOAPPROVA' && i.node === 0" @click="umeetingUpdate(i)">修改</div>
                    <div v-if="i.status ==='CONF_REJECTED' && i.node === 1" @click="umeetingUpdate(i)">修改</div>
                  </div>
                  <div class="noapprova">
                    <img src="../../assets/img/meetingList/guo.png" v-if="i.status === 'CONF_MORE_EXPIRED'" alt="">
                    <img src="../../assets/img/meetingList/shen.png" v-if="i.status === 'CONF_NOAPPROVA'" alt="">
                    <img src="../../assets/img/meetingList/bo.png" v-if="i.status === 'CONF_REJECTED'" alt="">
                  </div>
                </div>
              </mt-cell-swipe>
              <div class="line flex_auto" v-else>
                <div class="iconImg">
                  <img :src=img.localYu  v-if="i.type === 1 && i.status !=='CONF_ONLINE'">
                  <img :src=img.localStart  v-if="i.type === 1 && i.status ==='CONF_ONLINE'">
                  <img :src=img.videoStart  v-if="i.type === 0 && i.status === 'CONF_ONLINE'">
                  <img :src=img.videoYu  v-if="i.type === 0 && i.status !== 'CONF_ONLINE'">
                  <img :src=img.umeetingYu  v-if="i.type === 3 && i.status !== 'CONF_ONLINE'">
                  <img :src=img.umeetingStart  v-if="i.type === 3 && i.status === 'CONF_ONLINE'">
                  <img :src=img.phoneYu  v-if="i.type === 2 && i.status !=='CONF_ONLINE'">
                  <img :src=img.phoneStart  v-if="i.type === 2 && i.status ==='CONF_ONLINE'">
                </div>
                <div class="information" @click="umeetingInformation(i)">
                  <div class="title">{{i.name}}</div>
                  <div class="time">{{ i.startTime | time_tamp }} - {{ (i.startTime + (i.durationSeconds*60*1000)) | time_tamp}} </div>
                  <div class="compereUser">
                    <span v-if="$store.state.loginData.data.name === i.compereUser.name">由我主持</span>
                    <span v-else>{{i.compereUser.name}} 主持</span>
                  </div>

                </div>
                <div class="controll" v-if="$store.state.loginData.data.name === i.compereUser.name">
                  <div v-if="i.status === 'CONF_NOAPPROVA' || i.status ==='CONF_SCHEDULE' && i.node === 0"
                       @click="umeetingUpdate(i)">修改</div>
                  <div v-if="i.status ==='CONF_REJECTED' && i.node === 1"
                       @click="umeetingUpdate(i)">修改</div>
                  <div v-if="i.status ==='CONF_ONLINE' && i.type !== 1 && i.type !== 3"
                       @click="umeetingControll(i,index)">控制</div>
                </div>
                <div class="controll" v-if="$store.state.loginData.data.id === 1">
                  <div v-if="i.status ==='CONF_ONLINE' && i.type !== 1 && i.type !== 3"
                       @click="umeetingControll(i,index)">控制</div>
                  <div v-if="i.status ==='CONF_SCHEDULE' && i.status !=='CONF_NOAPPROVA' && i.node === 0"
                       @click="umeetingUpdate(i)">修改</div>
                  <div v-if="i.status ==='CONF_REJECTED' && i.node === 1"
                       @click="umeetingUpdate(i)">修改</div>
                </div>
                <div class="noapprova">
                  <img src="../../assets/img/meetingList/guo.png" v-if="i.status === 'CONF_MORE_EXPIRED'" alt="">
                  <img src="../../assets/img/meetingList/shen.png" v-if="i.status === 'CONF_NOAPPROVA'" alt="">
                  <img src="../../assets/img/meetingList/bo.png" v-if="i.status === 'CONF_REJECTED'" alt="">
                </div>
              </div>
            </div>
          </template>
        </div>
       <!-- <div style="font-size: 0.3rem;width: 100%;margin: 0.3rem auto;text-align: center" v-else>暂无数据</div>-->
        <div slot="top" class="mint-loadmore-top">
          <span v-show="topStatus !== 'loading'" :class="{ 'is-rotate': topStatus === 'drop' }">↓</span>
          <span v-show="topStatus === 'loading'">
            <mt-spinner type="snake"></mt-spinner>
          </span>
        </div>
      </mt-loadmore>
    </div>
  </div>
</template>

<script type="text/ecmascript-6">

  export default {
    name: 'MeetingList',
    data() {
      return{
        countLoadList: 0,
        img:{
          localStart: require('../../assets/img/bd_icon@2x.png'),//本地进行中的会议
          localYu: require('../../assets/img/bdhy_hui@2x.png'),//本地预约中的会议
          videoStart: require('../../assets/img/sp_icon@2x.png'),//视频会议进行中
          videoYu: require('../../assets/img/sphy_hui@2x.png'),//视频会议预约
          umeetingYu: require('../../assets/img/Umeeting_hui@2x.png'),//网络会议预约
          umeetingStart: require('../../assets/img/Umeeting_icon@2x.png'),//网络会议进行中
          phoneStart: require('../../assets/img/dh_icon@2x.png'),//电话会议
          phoneYu: require('../../assets/img/dhhy_hui@2x.png')
        },
        topStatus: '',
        wrapperHeight: 0,
        translate: 0,
        moveTranslate: 0
      }
    },
    filters: {
      time_tamp: function (value) {
        let meet_time_time = new Date(value);
        let hours = meet_time_time.getHours() > 9 ? meet_time_time.getHours(): '0' + meet_time_time.getHours();
        let minutes = meet_time_time.getMinutes() > 9 ? meet_time_time.getMinutes(): '0' + meet_time_time.getMinutes();
        //	console.log(hours+ ':' + minutes);
        return hours+ ':' + minutes;
      }
    },
    created() {
      /* this.rightButtons = [
       {
       content: 'Mark as Unread',
       style: { background: 'lightgray', color: '#fff' }
       },
       {
       content: 'Delete',
       style: { background: 'red', color: '#fff' },
       handler: () => this.$messagebox('delete')
       }
       ];*/
    },
    methods: {
      saoyisao(){
        let that = this;
        com.jieweifu.plugins.barcode.startScan({
          message:{
            barTitle:'扫一扫',
            lightFlag: false,//是否显示照亮按钮
            inputFlag: false//是否显示输入按钮
          },
        },function (data) {
          if(data.type === 'CLOSE'){
              return
          }
          if(!data){
            return
          }
          that.axios.post(that.$store.state.api.scan+'?sessionId='+
            that.$store.state.loginData.data.sessionId,{
            code: data
          }).then(function (res) {
            if(res.data.message !== 'OK'){
              that.$toast(res.data.message);
            }else {
              that.$toast('扫码成功');
            }
          }).catch(function (err) {
            that.$toast('扫码失败');
            // alert(err);
          })
        },function (error) {
          that.$toast('扫码失败')
        })
      /*  return
        cordova.plugins.barcodeScanner.scan(
          function (result) {
            if(!result.text){
              return
            }
            that.axios.post(that.$store.state.api.scan+'?sessionId='+
              that.$store.state.loginData.data.sessionId,{
              code: result.text
            }).then(function (res) {
              if(res.data.message !== 'OK'){
                that.$toast(res.data.message);
              }else {
                that.$toast('扫码成功');
              }
            }).catch(function (err) {
              that.$toast('扫码失败');
              // alert(err);
            })
          },
          function (error) {
            that.$toast('扫码失败');
          });*/
      },
      /*会议控制*/
      umeetingControll(item,index){
        let that = this;
        event.stopPropagation();
        //  event.stopPropagation();
        that.$store.state.meetingStatus = 'controll';
        that.$store.state.controll = {
          meetingNow: '',
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
        };
        that.$store.state.controlling = {
          value: [],
          options: [],
          rollingStart: '开始',
          interval: 5,
          rollingType: '全局轮询'
        };
        that.$store.state.org = {
          levelName: [],
          leveList: [],
          userList: [],
          select:false,
          searchUser: '',
          value1: []
        };
        that.$store.state.conditions = {
          showOrg: false,
          levelList: [],
          userList: [],
          list: [],
          tabIndex: ''
        };
        that.$store.state.controll.meetingNow = item;
        that.$router.push({path: '/meetingList/controll'});
      },
      /*会议详情*/
      umeetingInformation(item){
        let that = this;
     //   event.stopPropagation();
        that.$store.state.meetingRoomDetails = {

        };
        that.$store.state.roomDetailsList = {
          list: [],
          searchVal: ''
        };
        that.$store.state.usersDetailsList = {
          list: [],
          searchVal: ''
        };
        that.$store.state.nowMeetingInformation = item;
        console.log(that.$store.state.nowMeetingInformation);
        if(that.$store.state.nowMeetingInformation.description === null){
          that.$store.state.nowMeetingInformation.description = '';
        }
        if(that.$store.state.nowMeetingInformation.meetingRooms === null){
          that.$store.state.nowMeetingInformation.meetingRooms = [];
        }
        that.$router.push({
          path: '/meetingList/details'
        })
      },
      /*时间格式转换*/
      timetInit(date){
        let Y = date.getFullYear() + '/';
        let M = (date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1) + '/';
        let D = (date.getDate() < 10 ? '0' +  date.getDate() : date.getDate()) + ' ';
        let H = (date.getHours() < 10 ? '0' + date.getHours() : date.getHours()) + ':';
        let F = (date.getMinutes() < 10 ? '0' + date.getMinutes() : date.getMinutes());
        //  let S = (date.getSeconds() < 10 ? '0' + date.getSeconds() : date.getSeconds());
        return Y+M+D+H+F;
      },
      /*会议修改*/
      umeetingUpdate(item){
        console.log(item);
        let that = this;
        event.stopPropagation();
        that.$store.state.nowMeetingInformation = item;
        that.$store.state.addMeetingParams = {};
        that.$store.state.meetingRoom = {
          value1: [],
          meetingRoomUuids: '',
          searchRoom: '',
          start_time: '',
          pickerStartTime: '',
          duration: '01:00',
          RoomList: [],
          list: [],
          type: ''
        };
        that.$store.state.information = {
          name: '',//会议名称
          meetingPassword: '',//会议密码
          description: '',//会议描述
          titleName: '',
          nowStartMeeting: false,
          pickerValue: '',
          pickerTime: '',
          videotape: false,
          live: false,
          banwidthName: '自动',
          bandwidthVisible: false,
          bandwidthArr: ['自动','512kb','768kb','1.00M','1.50M','2.00M','3.00M','4.00M','6.00M','8.00M','10.00M','15.00M','20.00M','30.00M']
        };
        that.$store.state.org = {
          levelName: [],
          leveList: [],
          userList: [],
          select:false,
          searchUser: '',
          value1: []
        };
        that.$store.state.conditions = {
          showOrg: false,
          levelList: [],
          userList: [],
          list: [],
          tabIndex: ''
        };
        that.$store.state.meetingStatus = 'update';
        let type;
        switch (item.type){
          case 0:
            type = 'video';
            item.recording == '0' ? that.$store.state.information.videotape = false : that.$store.state.information.videotape = true;
            item.streaming == '0' ? that.$store.state.information.live = false : that.$store.state.information.live = true;
            if(item.band_width == '0'){
              that.$store.state.information.banwidthName = '自动'
            }else {
              if(item.band_width/1024 > 1000){
                that.$store.state.information.banwidthName = (item.band_width/1024/1024).toFixed(2) + 'M';
              }else {
                that.$store.state.information.banwidthName = item.band_width/1024 + 'kb';
              }
            }
            break;
          case 1:
            type = 'local';
            break;
          case 2:
            type = 'phone';
            that.$store.state.information.pickerValue = that.$store.state.information.pickerTime = that.timetInit(new Date(item.startTime));
            break;
          case 3:
            type = 'network';
            that.$store.state.information.banwidthName = item.capacity;
            break;
        }
        that.$store.state.information.name = item.name;
        that.$store.state.information.meetingPassword = item.password;
        that.$store.state.information.description = item.description;
        item.description === null ? that.$store.state.information.description = '' : that.$store.state.information.description = item.description;
        that.$store.state.information.nowStartMeeting = false;
        that.$store.state.information.titleName = '';
        if(item.type === 1){
          that.$store.state.meetingRoom.meetingRoomUuids = item.meetingRoomUuids;
        }else {
          // that.$store.state.meetingRoom.value1 = item
          if(item.meetingRoomUuids){
            that.$store.state.meetingRoom.value1 = item.meetingRoomUuids.split(',');
            console.log(that.$store.state.meetingRoom.value1);
          }
        }
        //console.log(that.$store.state.meetingRoom.value1)
        //  console.log(that.$store.state.org.value1);
        that.$store.state.meetingRoom.start_time = that.$store.state.meetingRoom.pickerStartTime = that.timetInit(new Date(item.startTime));
        //  console.log(item.durationSeconds);
        let hour = (Math.floor(item.durationSeconds/60) < 10 ? '0' + Math.floor(item.durationSeconds/60) : Math.floor(item.durationSeconds/60)) + ':';
        let mimutes = (Math.floor(item.durationSeconds%60) < 10 ? '0' + Math.floor(item.durationSeconds%60) : Math.floor(item.durationSeconds%60));
        that.$store.state.meetingRoom.duration = hour + mimutes;
        // console.log(hour+mimutes);
        let users = item.users;
        // that.$store.state.org.value1 = item.users;
        let val = [];
        // val.push(item.user.id+','+ item.user.logoUrl + ',' + item.user.name + ','+ item.user.phone);
        if(users && users.length >= 1){
          for(let i in users){
            val.push(users[i].id+','+ users[i].logoUrl + ',' + users[i].name + ','+ users[i].phone);
          }
        }
        that.$store.state.org.value1 = val;
        that.$store.state.addMeetingParams.uuid = item.uuid;
        that.$router.push({
          path: '/bespeak/information',
          query:{type: type}
        })
      },
      openConfirm(i){
        let text;
        let that = this;
        i.status === 'CONF_ONLINE' ? text = '结束会议' : text = '删除会议';
        that.$messagebox.confirm(text, '').then(function (res) {
          /*  console.log(res);
           console.log(that.$store.state.meetingList.meetingList);*/
          that.endMeeting(i);

        }).catch(function (err) {
          console.log(err)
        })
      },
      endMeeting(item){
        let that = this;
        /*   console.log(item.type);
         console.log(item.status);*/
        let api;
        if(item.type !== 3){
          item.status === 'CONF_ONLINE' ? api = that.$store.state.api.meetingEnd : api = that.$store.state.api.meetingDelete
        }else {
          item.status === 'CONF_ONLINE' ? api = that.$store.state.api.umeetingEnd + item.uuid : api = that.$store.state.api.meetingDelete
        }
        // console.log(api);
        that.axios.post(api+'?sessionId='+
          that.$store.state.loginData.data.sessionId,{
          uuid: item.uuid
        }).then(function (res) {
          console.log(res.data);
          if(res.data.message !== 'OK'){
            that.$toast(res.data.message);
          }else {
            that.$indicator.open('加载中');
            that.meetingListLoad();
          }

        }).catch(function (err) {
          console.log(err);
        })
      },
      handleTopChange(status) {
        this.moveTranslate = 1;
        this.topStatus = status;
      },
      translateChange(translate) {
        const translateNum = +translate;
        this.translate = translateNum.toFixed(2);
        this.moveTranslate = (1 + translateNum / 70).toFixed(2);
      },
      loadTop() {
        let that = this;
        that.meetingListLoad();
        setTimeout(() => {
          this.$refs.loadmore.onTopLoaded();
        }, 1500);
      },
      meetingListLoad(){
        let that = this;
        that.axios.post(that.$store.state.api.meetingList+'?sessionId='+
          that.$store.state.loginData.data.sessionId,{
        }).then(function (res) {
          // console.log(res.data);
          if(res.data.message !== 'OK'){
            that.$toast(res.data.message);
            that.countLoadList = 1;
            clearInterval(that.$store.state.meetingListSetInter);
            that.$store.state.meetingListSetInter = null;
            that.$store.state.meetingList.meetingList = [];
            //   that.$store.state.meetingListSetInter = null;

            that.$indicator.close();
          }else {
            that.countLoadList = 0;
            that.$indicator.close();
            that.$store.state.meetingList.list = res.data.data.data;
            let sortRule = (a,b) => {
              return a.startTime - b.startTime;
            }
            for(let i in that.$store.state.meetingList.list){
              that.$store.state.meetingList.list[i].timeDate = that.$store.state.timestampToTime(that.$store.state.meetingList.list[i].startTime);
              /*  if(state.meetingList.list[i].uuid === state.controll.meetingNow.uuid){
               //  console.log(state.meetingList.list[i])
               state.controll.meetingNow = state.meetingList.list[i];
               }else {

               }*/
            }
            that.$store.state.meetingList.list.sort(sortRule);
            let arr = that.$store.state.meetingList.list;
            let map = {}, dest = [];
            for (let i = 0; i < arr.length; i++) {
              let ai = arr[i];
              if (!map[ai.timeDate]) {
                dest.push({
                  timeDate: ai.timeDate,
                  data: [ai]
                });
                map[ai.timeDate] = ai;
              } else {
                for (let j = 0; j < dest.length; j++) {
                  let dj = dest[j];
                  if (dj.timeDate == ai.timeDate) {
                    dj.data.push(ai);
                    break;
                  }
                }
              }
            }
            that.$store.state.meetingList.meetingList = dest;
          }
          //state.controll.meetingNow.mode = res.data.data.data[state.controll.meetingIndex].mode;


          //   console.log(state.meetingList.meetingList);
        }).catch(function (err) {
          that.$store.state.meetingList.meetingList = [];
          that.countLoadList = 1;
          clearInterval(that.$store.state.meetingListSetInter);
          that.$indicator.close();
          that.$toast('获取会议列表失败');
        })
      }
    },
    mounted() {
      let that = this;
      //   console.log(that.$store.state.loginData.data.name);
      that.$indicator.open('加载中');
      that.$store.state.meetingList.meetingList = [];
      that.$store.state.meetingListSetInter =  setInterval(function () {
          if(that.countLoadList !== 0){
              clearInterval(that.$store.state.meetingListSetInter);
              return
          }
        if(that.$route.path === '/home' && that.$store.state.tabBar === 'conference'){
          that.meetingListLoad();
        }else {
          clearInterval(that.$store.state.meetingListSetInter);
        }

      },2000)


      if(that.$store.state.meetingList.meetingList.length > 0){
        this.wrapperHeight = document.documentElement.clientHeight - this.$refs.wrapper.getBoundingClientRect().top;
      }
    }

  };
</script>
<style>
  .meetingList .mint-cell-value{
    border-bottom: 1px solid #d9d9d9;
  }
</style>
<style lang="less" rel="stylesheet/less" scoped>
  .meetingList{
    position: relative;
    width: 100%;
    height: 100%;
    overflow-y: scroll;
    .main{
      height: 85%;
      overflow-y: scroll;
      width: 100%;
      margin-bottom: 1rem;
      .level{
        height: 0.44rem;
        line-height:0.44rem;
        font-size: 0.25rem;
        padding-left: 0.1rem;
        color: #7d7d7d;
        border-bottom: 1px solid #d9d9d9;
      }
      .container_line{
        width: 100%;
        position: relative;
        background-color: #ffffff;
        .line{
          width: 83%;
          padding: 0.28rem 0;
          margin-left: -0.3rem;

          .noapprova{
            position: absolute;
            left: 0;
            top:0;
            img{
              width: 0.71rem;
            }
          }
          .iconImg{
            width: 20%;
          }
          .information{
            width: 65%;
            .title{
              font-size: 0.28rem;
              color: #020202;
              width: 100%;
              overflow: hidden;
              text-overflow: ellipsis;
              white-space: nowrap;
              padding-top: 0.05rem;
            }
            .time,.compereUser{
              font-size: 0.2rem;
              color: #909090;
            }
            .time{
              margin: 0.08rem 0 0.15rem 0;
            }
          }
          .controll{
            position: absolute;
            right: 0.3rem;
            z-index: 2000;
            width: 15%;
            div{
              height: 0.55rem;
              background-color: #227ee1;
              border-radius: 0.05rem;
              color: #ffffff;
              font-size: 0.28rem;
              line-height: 0.55rem;
              text-align: center;
              width: 1rem;
            }
          }
          img{
            width: 0.93rem;
          }

        }

      }


    }
  }

</style>
