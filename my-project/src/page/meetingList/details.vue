<template>
  <div class="meetingDetails themeBgColor">
    <mt-header fixed title= "会议详情">
      <span slot="left">
        <mt-button @click.native= "$router.go(-1)"><i class="iconfont fanhui"></i></mt-button>
      </span>
    </mt-header>
    <div style="margin-top: 0.88rem;width: 100%;height: 0.3rem;"></div>
    <div class="information">
      <div class="title bg_fff" style="word-wrap: break-word;word-break: normal;">
        {{$store.state.nowMeetingInformation.name}}
      </div>
      <div class="main bg_fff">
        <p>
          <span v-if="$store.state.loginData.data.name === $store.state.nowMeetingInformation.compereUser.name">由我主持</span>
          <span v-else>{{$store.state.nowMeetingInformation.compereUser.name}} 主持</span>
        </p>
        <p>
          <span v-html="days_tamp($store.state.nowMeetingInformation.startTime)"></span>
          {{ $store.state.nowMeetingInformation.startTime | time_tamp }} -
          {{ ($store.state.nowMeetingInformation.startTime +
          ($store.state.nowMeetingInformation.durationSeconds*60*1000)) | time_tamp}}

          <!-- {{ $store.state.nowMeetingInformation.startTime | time_tamp }} -
           {{ ($store.state.nowMeetingInformation.startTime +
           ($store.state.nowMeetingInformation.durationSeconds*60*1000)) | time_tamp}}-->
        </p>
      <!--  <p>会议号：{{$store.state.nowMeetingInformation.numericId}}</p>
        <p v-if="$store.state.nowMeetingInformation.password">会议密码：{{$store.state.nowMeetingInformation.password}}</p>-->
      </div>
      <div class="meetingRoom bg_fff" v-if="$store.state.nowMeetingInformation.type !== 1">
        <p>
          <i class="labelTitle">会议号</i>
          <span>
            {{$store.state.nowMeetingInformation.numericId}}
        </span>
        </p>
        <p>
          <i class="labelTitle">会议密码</i>
          <span>
            {{$store.state.nowMeetingInformation.password}}
          </span>
        </p>
      </div>
      <div class="meetingRoom bg_fff color_blue">
        <p v-if="$store.state.nowMeetingInformation.type !== 2 && $store.state.nowMeetingInformation.type !== 1"
           @click="$router.push({'path': '/meetingList/roomDetailsList'})">
          <i class="labelTitle">会议室</i>
          <span>
            已选
          <b>{{$store.state.nowMeetingInformation.meetingRooms.length}}
          </b>
            个会议室
        </span>
          <i class="iconfont arrow-right-copy"></i>
        </p>
        <p v-if="$store.state.nowMeetingInformation.type !== 2 && $store.state.nowMeetingInformation.type === 1"
           @click="localRoomDetails">
          <i class="labelTitle">会议室</i>
           <span>
            {{$store.state.nowMeetingInformation.meetingRooms[0].name}}
            </span>
          <i class="iconfont arrow-right-copy"></i>
        </p>
        <p @click="$router.push({'path': '/meetingList/usersDetailsList'})">
          <i  class="labelTitle">会议人员</i>
          <span>
            已选
          <b>{{$store.state.nowMeetingInformation.users.length}}
          </b>
            个参会人员
        </span>
          <i class="iconfont arrow-right-copy"></i>
        </p>
      </div>
      <div class="meetingRoom bg_fff" v-if="$store.state.nowMeetingInformation.type === 0">
        <p>
          <i class="labelTitle">带宽</i>
          <span>
            {{bandwidth}}
          </span>
        </p>
        <p>
          <i class="labelTitle">自动录像</i>
          <span>
            {{$store.state.nowMeetingInformation.recording === '0' ? '否':'是'}}
          </span>
        </p>
      </div>
      <div class="meetingRoom bg_fff" v-if="$store.state.nowMeetingInformation.type === 3">
        <p>
          <i class="labelTitle">音频选项</i>
          <span>
            {{optionAudio}}
          </span>
        </p>
        <p>
          <i class="labelTitle">提前入会</i>
          <span>
            {{$store.state.nowMeetingInformation.optionJbh === 'false' ? '否' : '是'}}
          </span>
        </p>
      </div>
      <div class="description" v-if="$store.state.nowMeetingInformation.type !== 2">
        <div style="padding: 0.15rem 0.3rem;">会议描述</div>
        <div style="padding:0 0.3rem;word-wrap: break-word;word-break: normal;">{{$store.state.nowMeetingInformation.description}}</div>
      </div>
    </div>
  </div>
</template>

<script type="text/ecmascript-6">
  export default {
    name: 'meetingDetails',
    data() {
      return{
          bandwidth: '',
          optionAudio: ''
      }
    },
    filters: {
      time_tamp: function (value) {
        let meet_time_time = new Date(value);
        let hours = meet_time_time.getHours() > 9 ? meet_time_time.getHours(): '0' + meet_time_time.getHours();
        let minutes = meet_time_time.getMinutes() > 9 ? meet_time_time.getMinutes(): '0' + meet_time_time.getMinutes();
        //	console.log(hours+ ':' + minutes);
        return hours+ ':' + minutes;
      },
      day_tamp(val){
          switch (val){
            case 1:
              return '星期一';
              break;
            case 2:
              return '星期二';
              break;
            case 3:
              return '星期三';
              break;
            case 4:
              return '星期四';
              break;
            case 5:
              return '星期五';
              break;
            case 6:
              return '星期六';
              break;
            case 0:
              return '星期日';
              break;
          }
      }
    },
    methods: {
      usersList(){

      },
      localRoomDetails(){
        this.$store.state.meetingRoomDetails = this.$store.state.nowMeetingInformation.meetingRooms[0];
        switch (this.$store.state.nowMeetingInformation.meetingRooms[0].type){
          case 0:
            this.$store.state.meetingRoomDetails.typeName = '视频会议室';
            break;
          case 1:
            this.$store.state.meetingRoomDetails.typeName = '本地会议室';
            break;
          case 2:
            this.$store.state.meetingRoomDetails.typeName = '移动会议室';
            break;
        }
        this.$router.push({
          path: '/meetingList/roomDetails'
        })
      },
      days_tamp: function (value) {
        let meet_time_time = new Date(value);
        let Y = meet_time_time.getFullYear() + '-';
        let M = (meet_time_time.getMonth()+1 < 10 ? '0'+(meet_time_time.getMonth()+1) : meet_time_time.getMonth()+1) + '-';
        let D = meet_time_time.getDate() < 10 ? '0' + meet_time_time.getDate() : meet_time_time.getDate();
        let days = meet_time_time.getDay();
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
        return Y + M + D + '<span style="padding: 0 0.1rem">'+ days + '</span>';
      },
    },
    mounted() {
        let that = this;
        if(that.$store.state.nowMeetingInformation.type === 0){
           if(that.$store.state.nowMeetingInformation.band_width === 0){
               that.bandwidth = '自动';
           }else {
             that.$store.state.nowMeetingInformation.band_width/1024 > 1000 ? that.bandwidth = that.$store.state.nowMeetingInformation.band_width/1024/1024 + '.00M' :
               that.bandwidth = that.$store.state.nowMeetingInformation.band_width/1024 + 'kb'
           }
        }
        if(that.$store.state.nowMeetingInformation.type === 3){
            if(that.$store.state.nowMeetingInformation.optionAudio === 'both'){
              that.optionAudio = '电话和VOIP'
            }else if(that.$store.state.nowMeetingInformation.optionAudio === 'telephony'){
              that.optionAudio = '电话'
            }else if(that.$store.state.nowMeetingInformation.optionAudio === 'voip'){
              that.optionAudio = 'VOIP'
            }
            //that.optionAudio
        }
    }

  };
</script>

<style lang="less" rel="stylesheet/less" scoped>
  .meetingDetails{
    overflow-y: scroll;
    width: 100%;
    height: 100%;
    font-size: 0.26rem;
    .information{
       width: 100%;
      .title{
        font-size: 0.3rem;
        color: #474747;
        width: 90%;
        padding: 0.47rem 5% 0.3rem 5%;
        border-bottom: 1px solid #d9d9d9;
        font-weight: bold;
      }
      .main{
        width: 90%;
        padding: 0.3rem 5%;
        color: #474747;
        p{
          padding: 0.1rem 0;
        }
      }
  .meetingRoom{
    margin: 0.3rem 0;
    p{
      width: 90%;
      height: 0.75rem;
      line-height: 0.75rem;
      padding: 0 5%;
      position: relative;
      border-bottom: 1px solid #d9d9d9;
      background-color: #ffffff;
      box-shadow: 0px 1px 3px 0px
      rgba(2, 2, 2, 0.1);
      span{
        padding: 0 0.3rem;
      }
      .arrow-right-copy{
        color: #b9b9b9;
        font-size: 0.35rem;
        position: absolute;
        right: 0.3rem;
      }
      .labelTitle{
        display: inline-block;
        width: 23%;
      }
    }
    p:nth-last-child(1){
      border-bottom: none;
    }
  }
  .color_blue span{
    color: #227ee1;
  }
  .description{
    width: 100%;
    margin-top: 0.3rem;
    min-height: 4rem;
    background-color: #ffffff;
    box-shadow: 0px 1px 3px 0px
    rgba(2, 2, 2, 0.1);
    margin-bottom: 0.3rem;
    position: relative;
    .xia{
      position: absolute;
      bottom: 0.2rem;
      right: 0.2rem;
      margin-top: 0.3rem;
      color: #b9b9b9;
    }
  }
    }
  }
</style>
