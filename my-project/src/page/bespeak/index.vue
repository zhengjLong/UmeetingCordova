<template>
  <div class="bespesk">
    <img class="titleImg" src="../../assets/img/yuyuetu@2x.png" alt="">
    <div class="navMenu flex_auto">
      <div class="navLine" style="border-right: 1px solid #eeeeee;">
        <div @click="reservedConference('local')" class="flex_auto">
          <img class="firstImg" src="../../assets/img/bendi_hy@2x.png" alt="">
        </div>
        <div @click="reservedConference('network')" class="flex_auto">
          <img class="firstImg" src="../../assets/img/Umeeting@2x.png" alt="">
        </div>
      </div>
      <div class="navLine">
        <div @click="reservedConference('video')" class="flex_auto">
          <img src="../../assets/img/shipin_hy@2x.png" alt="">
        </div>
        <div @click="reservedConference('phone')" class="flex_auto">
          <img src="../../assets/img/phone@2x.png" alt="">
        </div>
      </div>
    </div>
  </div>
</template>

<script type="text/ecmascript-6">
  export default {
    name: 'bespesk',
    data() {
      return{

      }
    },
    methods: {
      reservedConference(name){
        //   console.log(name);
        let that = this;
        that.$store.state.addMeetingParams = {};
        that.$store.state.meetingRoom = {
          value1: [],
          tabIndex: '',
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
          banwidthName: '',
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
        that.$store.state.nowMeetingInformation = '';
        that.$store.state.meetingStatus = 'add';
        that.$store.state.org.value1.push(that.$store.state.loginData.data.id+','+ that.$store.state.loginData.data.logoUrl + ',' + that.$store.state.loginData.data.name + ','+ that.$store.state.loginData.data.phone);
        if(name === 'video'){
          that.$store.state.information.banwidthName = '自动'
        }else if(name === 'network'){
          that.$store.state.information.banwidthName = 10;
        }
        that.$router.push({
          path: '/bespeak/information',
          query: {type: name}
        })

      }
    },
    mounted() {
    }

  };
</script>
<style>
</style>
<style lang="less" rel="stylesheet/less" scoped>
  .bespesk{
    height: 100%;
    width: 100%;
  .titleImg{
    height: 35%;
    width: 100%;
  }
  .navMenu{
    height: 65%;
    width: 100%;
  img{
    width: 1.2rem;
  }
  .navLine{
    width: 50%;
    height: 100%;
    div{
      width: 100%;
      height: 50%;
      border-bottom: 1px solid #eeeeee;
    }
  }

/*  .navLine{
    width: 100%;
    height: 50%;
  div{
    flex: 1;
    text-align: center;
  img{
    width: 1.4rem;
    height: 1.68rem;
  }
  }
  }*/

  }
  }

</style>
