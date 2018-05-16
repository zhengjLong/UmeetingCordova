<template>
  <div class="meetingControll">
    <mt-header fixed title= "会议控制">
      <span slot="left">
        <mt-button @click.native= "$router.go(-1)"><i class="iconfont fanhui"></i></mt-button>
      </span>
      <span slot="right">
        <mt-button v-if="$store.state.controll.meetingNow.type === 0"
                   @click.native= "popupVisible = true">
          <i class="iconfont jiaoyi_gengduo"></i>
        </mt-button>
        <mt-button v-if="$store.state.controll.meetingNow.type === 2"
                   @click.native= "$refs.picker1.open()">
          延时
        </mt-button>
      </span>
    </mt-header>
    <div style="height: 0.88rem;"></div>
    <div class="container">
      <div class="search themeBgColor flex_auto">
        <div class="flex_start">
          <i class="iconfont sousuo"></i>
          <input type="text" maxlength="20" v-model="$store.state.controll.participant.searchVal" v-on:input="search" placeholder="请输入与会者名称">
        </div>
      </div>
      <div class="meetingName">{{$store.state.controll.meetingNow.name}}</div>
      <div class="meetingName listTitle flex_between">
      <div class="text-left">
        已连接:
        {{$store.state.controll.participant.connectNum}}
        /
        {{$store.state.controll.participant.participantList.length}}
      </div>
      <div class="text-right btn flex_auto">
        <div class="controllBtn flex-1 text-center">连接</div>
        <div class="controllBtn flex-1 text-center" v-if="$store.state.controll.meetingNow.type === 0">主会场</div>
        <div class="controllBtn flex-1 text-center">
          <span v-if="$store.state.controll.meetingNow.mode === 0">发言</span>
          <span v-else>麦克风</span>
        </div>
        <div class="controllBtn flex-1 text-center" v-if="$store.state.controll.meetingNow.type === 0">分屏</div>
        <div class="controllBtn flex-1 text-center">删除</div>
      </div>
    </div>
      <template v-for="(item,index) in $store.state.controll.participant.participantList">
        <div class="meetingName listTitle flex_between line">
          <div class="flex_auto column">
            <span class="flex_start ellipsis" style="width:100%;height:0.4rem;line-height: 0.4rem;font-size: 0.25rem;">{{item.displayName}}</span>
            <span class="flex_start ellipsis" style="width:100%;height:0.4rem;line-height: 0.4rem;font-size: 0.25rem;">{{item.phoneNum}}</span>
            </div>
          <div class="text-right btn flex_auto">
            <div class="controllBtn flex-1 text-center" @click="participantControll(item,'call')">
              <i class="iconfont telephone" :class="{blue: item.status === 'PAR_CONNECTED',red: item.status === 'PAR_DISCONNECTED',yellow: item.status === 'PAR_PENDING'}"></i>
            </div>
            <div class="controllBtn flex-1 text-center" v-if="$store.state.controll.meetingNow.type === 0" @click="participantControll(item,'chair')">
              <i  v-if="item.status === 'PAR_CONNECTED'" class="iconfont host" :class="{blue: item.chair === 1,red: item.status !== 1}"></i>
            </div>
            <div class="controllBtn flex-1 text-center" v-if="$store.state.controll.meetingNow.mode === 1" @click="participantControll(item,'microphone')">
              <i class="iconfont maikefeng-tianchong blue" v-if="item.status === 'PAR_CONNECTED' && item.audioRxMuted === 0"></i>
              <i class="iconfont maikefeng-jingyin-tianchongsvg red" v-if="item.status === 'PAR_CONNECTED' && item.audioRxMuted === 1"></i>
            </div>
            <div class="controllBtn flex-1 text-center" v-if="$store.state.controll.meetingNow.mode === 0" @click="participantControll(item,'talker')">
              <i class="iconfont liaotianjilu" :class="{blue: item.speaker === 1,red: item.speaker === 0}" v-if="item.status === 'PAR_CONNECTED'"></i>
            </div>
            <div class="controllBtn flex-1 text-center" v-if="$store.state.controll.meetingNow.type === 0" @click="participantControll(item,'layout',index)">
              <i class="iconfont screen blue" v-if="item.status === 'PAR_CONNECTED'"></i>
            </div>
            <div class="controllBtn flex-1 text-center" @click="participantControll(item,'delete')">
              <i class="iconfont lajitong1"></i>
            </div>
          </div>
        </div>
    </template>
    </div>
    <footer class="footControll flex_auto">
      <div class="flex-1 text-center" @click="meetingCallSheet = true"><i class="iconfont dianhua"></i></div>
      <div class="flex-1 text-center" @click="microphoneSheet = true"><i class="iconfont maikefeng"></i></div>
      <div class="flex-1 text-center" @click="meetingModeSheet = true" v-if="$store.state.controll.meetingNow.type === 0">
        <i class="iconfont duorenhuiyi"></i>
      </div>
      <div class="flex-1 text-center" @click="addAttendessSheet = true" v-if="$store.state.controll.meetingNow.type === 2">
        <i class="iconfont tianjia1"></i>
      </div>
      <div class="flex-1 text-center" @click="deletMeeting" v-if="$store.state.controll.meetingNow.type === 2">
        <i class="iconfont icon-" style="font-size: 0.51rem;"></i>
      </div>
      <div class="flex-1 text-center" @click="openMessage" v-if="$store.state.controll.meetingNow.type === 0">
        <i class="iconfont icon-test"></i>
      </div>
    </footer>
    <mt-actionsheet
      :actions="meetingModeActions"
      v-model="meetingModeSheet">
    </mt-actionsheet>
    <mt-actionsheet
      :actions="meetingCallActions"
      v-model="meetingCallSheet">
    </mt-actionsheet>
    <mt-actionsheet
      :actions="microphoneActions"
      v-model="microphoneSheet">
    </mt-actionsheet>
    <mt-popup
      class="mint-popup-1"
      v-model="popupVisible"
      :style="{ top: 2.8 + 'rem' }">
     <div class="moreLine" @click="move('/controll/addMeetingRoom','addMeetingRoom')">添加会议室</div>
      <div class="moreLine" @click="move('/controll/dial','dial')">拨号</div>
      <div class="moreLine" @click="move('/controll/rolling','rolling')">会议轮询</div>
      <div class="moreLine" @click="move('/controll/layout','layout')">分屏模式</div>
      <div class="moreLine" @click="delay">延长会议</div>
      <div class="moreLine" @click="move('','delete')">结束会议</div>
    </mt-popup>
    <mt-datetime-picker
      ref="picker1"
      type="time"
      v-model="duration"
      hour-format="{value}小时"
      minute-format="{value}分钟"
      @confirm="durationConfirm">
    </mt-datetime-picker>
    <mt-actionsheet class="addAteendess" :actions="addAttendessActions" v-model="addAttendessSheet"></mt-actionsheet>
  </div>
</template>

<script type="text/ecmascript-6">
  export default {
    name: 'meetingControll',
    data() {
      return{
        addAttendessSheet: false,//添加联系人
        addAttendessActions: [{
          name: '请添加人员类型'
        },{
          name: '组织架构',
          method: this.queryOrgById
        }, {
          name: '我的联系人',
          method: this.userByConditions
        },{
          name: '通讯录',
          method: this.findContacts
        },{
          name: '临时邀请',
          method: this.temporary
        }],
        duration: '00:30',
          /*更多*/
        buttonBottom: 0,
        popupVisible: false,
        /*参会者在线人数*/
        connectNum: 0,
        /*会议模式*/
        meetingModeSheet: false,
        meetingModeActions: [{
          name: '演讲模式',
          method : this.speech// 调用methods中的函数
        }, {
          name: '讨论模式',
          method : this.talk// 调用methods中的函数
        }],
        mode: 1,
        /*全连全断*/
        meetingCallSheet: false,
        meetingCallActions: [{
          name: '全连',
          method : this.connect
        }, {
          name: '全断',
          method : this.disconnect
        }],
        /*麦克风全开全关*/
        microphoneSheet:false,
        microphoneActions:[{
            name: '麦克风全开',
            method: this.microphoneopen
        },{
          name: '麦克风全关',
          method: this.microphoneClose
        }],
        contactsOptions : [],
      }
    },
    methods: {
      temporary(){//临时邀请
        this.$store.state.org.value1 = [];
        this.$router.push({
          path : '/information/temporary'
        })
      },
      findContacts(){//获取手机通讯录
        let that = this;
        that.$store.state.org.value1 = [];
    /*    this.$router.push({
          path: '/controll/contacts'
        })
        return*/
       that.$store.state.contacts.contactsOptions = [];
        function onSuccess(contacts) {
          for (var i = 0; i < contacts.length; i++) {
              if(contacts[i].phoneNumbers[0].value){
                that.$store.state.contacts.contactsOptions.push({
                  value : contacts[i].displayName + ',' + contacts[i].phoneNumbers[0].value.replace(' ','').replace(' ',''),
                  label : contacts[i].displayName + '(' + contacts[i].phoneNumbers[0].value.replace(' ','').replace(' ','') +  ')'
                })
              }
          }
          that.$store.state.contacts.searchList = that.$store.state.contacts.contactsOptions;
          that.$router.push({
            path: '/controll/contacts'
          })
        }
       function onError(contactError) {
            that.$toast('通讯录获取失败');
       //  alert(JSON.stringify(contactError));
       }
        let contactFileds = ["displayName", "name", "phoneNumbers", "emails", "address"];
//filter制定为空或不指定返回所有联系人列表
        let options = { filter: "", multiple: true };
        navigator.contacts.find(contactFileds, onSuccess, onError, options);
     },
      userByConditions(){//联系人
        let that = this;
        that.$store.state.conditions = {
          showOrg: false,
          levelList: [],
          userList: [],
          list: [],
          tabIndex: ''
        };
        that.$store.state.org = {
          levelName: [],
          leveList: [],
          userList: [],
          select:false,
          searchUser: '',
          value1: []
        };
        this.$store.state.org.value1 = [];
        that.$router.push({
          path: '/information/conditions',
          query: {type : 'phone'}
        })
      },
      queryOrgById(){//组织机构
        let that = this;
        that.$store.state.conditions = {
          showOrg: false,
          levelList: [],
          userList: [],
          list: [],
          tabIndex: ''
        };
        that.$store.state.org = {
          levelName: [],
          leveList: [],
          userList: [],
          select:false,
          searchUser: '',
          value1: []
        };
        this.$store.state.org.value1 = [];
        that.$router.push({
          path: '/information/org',
          query: {type : 'phone'}
        })
      },
      durationConfirm(value){
        let that = this;
        console.log(value);
        let hour = value.substring(0,value.indexOf(':'));
        let minutes = value.substring(value.indexOf(':')+1,value.length);
        let durationSeconds = Number(hour)*60 + Number(minutes);
        console.log(durationSeconds)
        if(durationSeconds === 0){
          that.$toast('延长时间不能小于0');
          return
        }
        that.axios.post(that.$store.state.api.meetingDelay + '?sessionId='+
          that.$store.state.loginData.data.sessionId,{
           meetingUuid: that.$store.state.controll.meetingNow.uuid,
           minutes : durationSeconds
        }).then(function (res) {
            console.log(res.data)
          if(res.data.message !== 'OK'){
            that.$toast(res.data.message)
          }else {
            that.$toast('延时成功')
          }
        }).catch(function (err) {
           that.$toast('延时失败')
        })
      },
        move(routerVal,key){
          let that = this;
          this.popupVisible = false;
              switch (key){
                case 'delete':/*结束会议*/
                  that.deletMeeting();
                  break;
                case 'addMeetingRoom':/*添加会议室*/
                  this.$router.push({
                    path: routerVal
                  });
                  break;
                  case 'dial':/*拨号*/
                  this.$router.push({
                    path: routerVal
                  });
                  break;
                  case 'layout':/*分屏*/
                   that.layout(routerVal);

                  break;
                case 'rolling':/*轮询*/
                    that.rolling(routerVal);
                    break;
              }
        },
        /*分屏模式*/
        layout(routerVal,index){
            let that = this;
          if(that.$store.state.controll.meetingNow.serverModel.includes('MCU') ||
            that.$store.state.controll.meetingNow.serverModel.includes('ACANO')){
            this.$router.push({
              path: routerVal,
              query:{
                  type: index
              }
            });
          }else {
            that.$toast('分屏暂不支持')
          }

        },
        /*轮询*/
        rolling(routerVal){
            let that = this;
          console.log(that.$store.state);
          console.log(that.$store.state.controll.meetingNow.serverModel);
          if(that.$store.state.controll.meetingNow.serverModel.includes('MCU')){
            that.axios.post(that.$store.state.api.meetingRollingStatus + '?sessionId='+
              that.$store.state.loginData.data.sessionId,{
              uuid: that.$store.state.controll.meetingNow.uuid,
              serverModel: that.$store.state.controll.meetingNow.serverModel
            }).then(function (res) {
              console.log(res.data);
              if(!res.data.data){
                that.$store.state.controlling.rollingStart = '开始';
              }else {
                that.$store.state.controlling.rollingStart = '结束';
                that.$store.state.controlling.interval = res.data.data.interval;
                res.data.data.mode === 1 ? that.$store.state.controlling.rollingType = '主席轮询': '全局轮询';
              }
            }).catch(function (err) {
              that.$toast('查询轮询状态失败')
            });
            that.$router.push({
              path: routerVal
            });
          }else {
            that.$toast(that.$store.state.controll.meetingNow.serverModel + '轮询暂不支持');
          }
        },
        /*延长会议*/
        delay(){
          this.$refs.picker1.open();
          this.popupVisible = false;
        },
        /*结束会议*/
        deletMeeting(){
          let that = this;
          that.$messagebox.confirm('结束会议', '').then(function () {
            /*  console.log(res);
             console.log(that.$store.state.meetingList.meetingList);*/
            that.$indicator.open();
            that.axios.post(that.$store.state.api.meetingEnd+'?sessionId='+
              that.$store.state.loginData.data.sessionId,{
              uuid: that.$store.state.controll.meetingNow.uuid
            }).then(function (res) {
              console.log(res.data);
              if(res.data.message !== 'OK'){
                that.$toast(res.data.message);
              }else {
                that.$router.push({
                  path: '/home'
                })
              }
              that.$indicator.close();
            }).catch(function (err) {
              console.log(err);
              that.$toast(err);
              that.$indicator.close();
            })
          }).catch(function () {});
        },
        /*字幕*/
        openMessage(){
            let that = this;
          this.$messagebox.prompt(' ', '字幕').then(({ value }) => {
            if (value) {
              let params = { meetingUuid: that.$store.state.controll.meetingNow.uuid, durationSeconds: '5', position: "top", messageParams: value };
              console.log(params);
              that.axios.post(that.$store.state.api.meetingMessage + '?sessionId=' +
                that.$store.state.loginData.data.sessionId,params).then(function (res) {
                console.log(res);
                that.$toast('字幕发送成功');
              }).catch(function (err) {
                console.log(err);
              })
            }
          });
        },
        /*麦克风全开全关*/
        microphoneopen(){
            if(this.$store.state.controll.meetingNow.mode === 0){
               this.$toast('麦克风全开命令发送失败：演讲模式下不允许麦克风全开');
              return
            }
            this.meetingCall('on','麦克风全开命令已发送',this.$store.state.api.meetingMicrophone)
        },
        microphoneClose(){
          if(this.$store.state.controll.meetingNow.mode === 0){
            this.$toast('麦克风全关命令发送失败：演讲模式下不允许麦克风全关');
            return
          }
            this.meetingCall('off','麦克风全关命令已发送',this.$store.state.api.meetingMicrophone)
          },
        /*全连全断*/
        meetingCall(op,toastText,api){
          let that = this;
          that.axios.post(api + '?sessionId=' +
            that.$store.state.loginData.data.sessionId,{
            meetingUuid: that.$store.state.controll.meetingNow.uuid,
            op:op
          }).then(function (res) {
             that.$toast(toastText);
          }).catch(function (res) {
            console.log(res);
            that.$toast(res.data.message);
          })
        },
        disconnect(){
            this.meetingCall('off','全断命令已发送',this.$store.state.api.meetingCall)
        },
        connect(){
          this.meetingCall('on','全连命令已发送',this.$store.state.api.meetingCall)
        },
        /*会议模式*/
      meetingMode(mode){
        let that = this;
        console.log(that.$store.state.controll.meetingNow.uuid);
        that.axios.post(that.$store.state.api.meetingMode + '?sessionId=' +
          that.$store.state.loginData.data.sessionId,{
            meetingUuid: that.$store.state.controll.meetingNow.uuid,
            mode:mode
        }).then(function (res) {
          if(res.data.message !== 'OK'){
            that.$toast(res.data.message);
          }else {
              if(mode === 0){
                that.$toast('演讲模式命令已发送')
              }else if(mode === 1){
                  that.$toast('讨论模式命令已发送')
              }

          }
        }).catch(function (res) {
          console.log(res);
          that.$toast(res.data.message);
        })
      },
        /*演讲模式*/
      speech(){
        this.meetingMode(0);
     //  this.$store.state.controll.meetingMode = 0;
      },
      /*讨论模式*/
      talk(){
        this.meetingMode(1);
    //    this.$store.state.controll.meetingMode = 1;
      },
      //单个与会者的控制
      participantControll(item,key,index){
          let that = this;
          let url;
          switch (key){
            case 'call'://连接与断开
                url = that.$store.state.api.participantpCall;
                that.call(url,item);
                break;
            case 'chair'://主会场切换
                url = that.$store.state.api.participantpChair;
                if(item.chair === 0){
                    that.commonControll(url,item,'on','chair');
                }else if(item.chair === 1){
                  that.commonControll(url,item,'off','chair');
                }
                break;
            case 'microphone'://麦克风控制
                url = that.$store.state.api.participantpMicrophone;
                console.log(item)
              if(item.audioRxMuted === 0){
                    that.commonControll(url,item,'off','microphone')
              }else if(item.audioRxMuted === 1){
                  that.commonControll(url,item,'on','microphone');
              }
                break;
            case 'talker'://发言
              url = that.$store.state.api.participantpFloor;
              if(item.speaker === 0){
                that.commonControll(url,item,'on','talker')
              }else if(item.speaker === 1){
                that.commonControll(url,item,'off','talker');
              }
              break;
            case 'layout'://分屏
                that.layout('/controll/layout',index);
                break;
            case 'delete'://删除
              url = that.$store.state.api.participantpDelete;
              that.delete(url,item);
              break;
          }

      },
      /*删除*/
      delete(url,item){
        let that = this;
        that.axios.post(url + '?sessionId=' +
          that.$store.state.loginData.data.sessionId,{
          meetingUuid: that.$store.state.controll.meetingNow.uuid,
          uuid: item.uuid,
          roomUUid: item.terminalId
        }).then(function (res) {
          if(res.data.message !== 'OK'){
            that.$toast(res.data.message);
          }else {
              that.$toast('移除成功')
          }
        }).catch(function (res) {
          console.log(res);
          that.$toast(res.data.message);
        })
      },
      /*麦克风，主会场控制*/
      commonControll(url,item,op,val){
        let that = this;
        let toastText;
        that.axios.post(url + '?sessionId=' +
          that.$store.state.loginData.data.sessionId,{
          meetingUuid: that.$store.state.controll.meetingNow.uuid,
          participantId: item.uuid,
          op: op
        }).then(function (res) {
          if(res.data.message !== 'OK'){
            toastText = res.data.message;
          }else {
              switch (val){
                case 'chair':
                    op === 'on' ? toastText = '设置主席命令已发送' : toastText = '取消主席命令已发送';
                    break;
                case 'microphone':
                    op === 'on' ? toastText = '麦克风开启命令已发送' : toastText = '麦克风关闭命令已发送';
                    break;
                case 'talker':
                    op === 'on' ? toastText = '设置发言命令已发送' : toastText = '取消发言命令已发送';
                    break;
              }
          }
          that.$toast(toastText)
        }).catch(function (res) {
          console.log(res);
          that.$toast(res.data.message);
        })
      },
      /*连接断开*/
      call(url,item){
          let that = this;
        that.axios.post(url + '?sessionId=' +
          that.$store.state.loginData.data.sessionId,{
          meetingUuid: that.$store.state.controll.meetingNow.uuid,
          participantId: item.uuid
        }).then(function (res) {
          if(res.data.message !== 'OK'){
            that.$toast(res.data.message);
          }else {
              that.$toast('连接/断开命令已发送')
          }
        }).catch(function (res) {
          console.log(res);
          that.$toast(res.data.message);
        })
      },
      /*搜索*/
      search(){

      },
      queryByUuid(){
          let that = this;
        that.axios.post(that.$store.state.api.queryByUuid + '?sessionId=' +
          that.$store.state.loginData.data.sessionId,{
          uuid: that.$store.state.controll.meetingNow.uuid
        }).then(function (res) {
          if(res.data.message !== 'OK'){
            that.$toast(res.data.message);
            clearInterval(that.$store.state.controll.participant.timing);
            that.$router.push({
              'path': '/home'
            })
          }else {
            that.$store.state.controll.meetingNow = res.data.data;
          }
          that.$indicator.close();
        }).catch(function (err) {
          console.log(err);
          that.$indicator.close();
          that.$toast('当前会议信息获取失败');
          clearInterval(that.$store.state.controll.participant.timing);
          that.$router.push({
            'path': '/home'
          })
        })
      }
    },
    mounted() {
      let that = this;
      /*参会人员*/
      that.$indicator.open();
      let phoneList = [];
      let phoneDialData = {};
      if(that.$store.state.org.value1.length > 0){
          for(let i in that.$store.state.org.value1){
            //phoneList.push(that.$store.state.org.value1[i].split(','));
            phoneList.push({
              phoneNum: that.$store.state.org.value1[i].split(',')[3],
              name : that.$store.state.org.value1[i].split(',')[2],
            })
          }
        console.log(phoneList);
        that.axios.post(that.$store.state.api.participantpPhoneDial + '?sessionId=' +
          that.$store.state.loginData.data.sessionId,{
          meetingUuid: that.$store.state.controll.meetingNow.uuid,
          phoneList: phoneList
        }).then(function (res) {
          console.log(res);
          if(res.data.message !== 'OK'){
              that.$toast(res.data.message);
          }else {
              that.$toast('添加临时参会成功')
          }
        }).catch(function (err) {
          console.log(err);
          that.$toast('添加临时参会失败')
        })
      }
      that.$store.state.controll.participant.timing = setInterval(function () {
        if(that.$route.path !== '/meetingList/controll'){
          clearInterval(that.$store.state.controll.participant.timing)
          return
        }
        that.$store.commit('participantAll',{
            uuid:that.$store.state.controll.meetingNow.uuid,
            axios: that.axios,
            $toast: that.$toast,$indicator: that.$indicator
        });
        that.queryByUuid();
       // console.log(that.$store.state.controll.participant.participantList)
      },2000);

      window.addEventListener('native.keyboardhide', function (e){
       /* position: fixed;
        bottom: 0;
        height: 0.88rem;
        width: 100%;*/
        $('.meetingControll .footControll').css({
          'position': 'fixed',
          'width' : '100%',
          'height': '0.88rem',
          'display': 'flex'
        });
        $('.meetingControll .footControll').addClass('flex_auto');

      });
      window.addEventListener('native.keyboardshow', function (e){
        $('.meetingControll .footControll').css('display', 'none');
      });
    }

  };
</script>
<style lang="less" type="text/less">
  .addAteendess.mint-actionsheet ul.mint-actionsheet-list li.mint-actionsheet-listitem:first-child{
    font-size: 0.2rem;
    color: #6d6d72;
    height: 0.5rem;
    line-height: 0.5rem;
  }
  .meetingControll .mint-popup.mint-popup-1{
    left: 83%;
    background-color: #f8f5f5;
    border-radius: 0.08rem;
  }
  .meetingControll .mint-popup-1::before {
    display: inline-block;
    width: 0;
    height: 0;
    border: solid transparent;
    border-width: 0.1rem;
    border-bottom-color: #fff;
    content: '';
    position: absolute;
    top: -0.2rem;
    left: 75%;
    /* right: 80%;*/
  }
  .mint-indexlist-nav{
      max-height: 90%;
  }
  .mint-indexlist-navitem{
    font-size: 0.28rem;
    color: #227ee1;
    padding: 0.05rem 0.05rem;
  }
.meetingControll .mint-header .mint-button{
  font-size: 0.3rem;
}
</style>
<style lang="less" rel="stylesheet/less" scoped>
  .meetingControll{
    width: 100%;
    height: 100%;
    position: relative;
    footer{
      position: fixed;
      bottom: 0;
      height: 0.88rem;
      width: 100%;
      background-color: #227ee1;
      color: #ffffff;
      .iconfont{
        font-size: 0.4rem;
      }
    }
    .container{
      height: 84%;
      width: 100%;
      overflow-y: scroll;
      .search{
        width: 100%;
        height: 0.8rem;
        top:0.88rem;
        .flex_start{
            margin-left: 0.3rem;
            margin-right: 0.3rem;
            height: 0.5rem;
            width: 96%;
            border-radius: 0.1rem;
            background-color: #ffffff;
          .sousuo{
            color: #b9b9b9;
            font-size: 0.27rem;
          }
          input{
            font-size: 0.25rem;
            width: 100%;
          }
          input::-webkit-input-placeholder{
            color: #b9b9b9;
          }
        }
        .sousuo{
          margin: 0px 0.05rem;
          font-size: 0.3rem;
        }
      }
      .meetingName{
        height: 0.6rem;
        line-height: 0.6rem;
        font-size: 0.26rem;
        background-color: #d9d9d9;
        color: #ffffff;
        width: 100%;
        text-align: center;
        div:first-child{
          width: 38%;
          overflow: hidden;
          text-overflow: ellipsis;
          white-space: nowrap;
          height: 100%;
        }
        div:last-child{
          width: 62%;
        }
      }
      .listTitle{
        width: 96%;
        padding: 0 2%;
        background-color: #f2f5f7;
        font-size: 0.2rem;
        color: #333;
      }
      .btn{
        .controllBtn{

        }
      }
      .line{
        background-color: #ffffff;
        height: 0.88rem;
        line-height: 0.88rem;
        border-bottom: 1px solid #eeeeee;
        font-size: 0.28rem;
        .maikefeng-tianchong,.maikefeng-jingyin-tianchongsvg{
          font-size: 0.32rem;
        }
        .telephone,.lajitong1,.host{
          font-size: 0.35rem;
         /* color: #259a27;*/
        }
        .screen{
          font-size: 0.4rem;
        }
        .lajitong1{
          color: #cc070e;
        }
      }
    }
  .red{
    color: #cc070e;
  }
  .blue{
    color: #259a27;
  }
  .yellow{
    color: #f8b551;
  }
  .moreLine{
    width: 1.8rem;
    text-align: center;
    border-bottom: 1px solid #FFFFFF;
    padding: 0.15rem 0;
    font-size: 0.25rem;
  }
  }
</style>
