<template>
  <div class="information">
    <mt-header :title= "$store.state.information.titleName">
      <span slot="left">
        <mt-button @click.native= "$router.go(-1)"><i class="iconfont fanhui"></i></mt-button>
      </span>
    </mt-header>
    <div class="themeBgColor" style="height: 0.88rem"></div>
    <div class="main">
    <div class="lineName line">
      <div class="flex_auto">
        <div class="meetingTitle">会议名称</div>
        <div class="meetingName">
          <input type="text" placeholder="请输入会议名称" v-model = "$store.state.information.name" maxlength="20">
        </div>
      </div>
      <div class="flex_auto" v-if="$route.query.type !== 'local'">
        <div class="meetingTitle">会议密码</div>
        <div class="meetingName">
          <input type="number" v-model="$store.state.information.meetingPassword" maxlength="10" placeholder="填写会议密码（可选）">
        </div>
      </div>
    </div>
    <div class="lineStartNow line">
      <div class="flex_auto">
        <div class="meetingTitle">立即开会</div>
        <div class="meetingName">
          <mt-switch v-model="$store.state.information.nowStartMeeting" @change="meetingStarChange">
          </mt-switch>
        </div>
      </div>
      <div class="flex_auto" v-if="$route.query.type === 'network' && !$store.state.loginData.data.hostId && $store.state.meetingStatus === 'add'" >
        <div class="meetingTitle">最大方数</div>
        <div class="meetingName" @click="maxSquare">
          {{$store.state.information.banwidthName}}
        </div>
      </div>
      <div class="flex_auto" v-if="$route.query.type === 'phone'">
        <div class="meetingTitle">开会时间</div>
        <div class="meetingName" @click="$refs.picker.open()">
          {{$store.state.information.pickerTime}}
        </div>
        <mt-datetime-picker
          ref="picker"
          type="datetime"
          v-model="$store.state.information.pickerValue"
          year-format="{value}年"
          month-format="{value}月"
          date-format="{value}日"
          hour-format="{value}时"
          minute-format="{value}分"
          second-format="{value}秒"
          @confirm="handleConfirm">
        </mt-datetime-picker>
      </div>
    </div>
    <div class="lineStartNow line network">
      <div class="flex_auto" style="width: 100%;"  v-if="$route.query.type === 'network'" @click="networkmore">
        <div class="meetingTitle">更多设置</div>
        <div class="meetingName text-right networkSet">
          <i class="iconfont arrow-right-copy"></i>
        </div>
      </div>
    </div>
    <div class="lineStartNow line" v-if="$route.query.type === 'video'">
      <div class="flex_auto">
        <div class="meetingTitle">自动录像</div>
        <div class="meetingName">
          <mt-switch v-model="$store.state.information.videotape" @change="videotapeChange">
          </mt-switch>
        </div>
      </div>
      <div class="flex_auto">
        <div class="meetingTitle">开启直播</div>
        <div class="meetingName">
          <mt-switch v-model="$store.state.information.live" @change="liveChange">
          </mt-switch>
        </div>
      </div>
      <div class="flex_auto">
        <div class="meetingTitle">带宽设置</div>
        <div class="meetingName phoneTime" @click="$store.state.information.bandwidthVisible = true">
          {{$store.state.information.banwidthName}}
        </div>
      </div>
    </div>
    <div class="lineName line introduction" v-if="$route.query.type !== 'phone'">
      <div class="flex_auto">
        <div class="meetingTitle">会议描述</div>
        <div class="meetingName infoColor f25">
          下方输入会议内容简要说明
        </div>
      </div>
    </div>
    <div class="bg_fff introductionTextarea" v-if="$route.query.type !== 'phone'">
      <textarea rows="4" @focus="focusScoll" v-model="$store.state.information.description" v-on:input="introductionglen">
      </textarea>
      <div class="text-right" style="padding: 0.1rem;">{{$store.state.information.description.length}}/100</div>
    </div>
      <div class="submitNext" :class="{videoNext: $route.query.type === 'video'}" @click="submitNext">下一步</div>
    </div>

    <mt-popup
      v-model="$store.state.information.bandwidthVisible"
      position="bottom" :class="{h92: bandwidthName === '带宽'}">
      <div class="bandwidthList">
        <div class="title">请选择会议{{bandwidthName}}</div>
        <div class="lineBandWidthList">
          <template v-for="item in $store.state.information.bandwidthArr">
            <div class="line" @click="bandwidth(item)">{{item}}</div>
          </template>
        </div>
       <!-- <div style="height: 0.2rem;background-color: #000;opacity: 0.5;"></div>-->
       <!-- <div class="cancel line" @click="$store.state.information.bandwidthVisible = false">
         取消
        </div>-->
        <mt-button size="large"  @click="$store.state.information.bandwidthVisible = false" class="cancel" type="primary">取消</mt-button>
      </div>
    </mt-popup>
  <!--  <div class="bandwidthList" :class="{bandSq:$store.state.information.bandwidthArr.length > 10}" style="z-index: 999" v-if="">

    </div>-->

  </div>
</template>

<script type="text/ecmascript-6">
  export default {
    name: 'Information',
    data() {
      return{
        bandwidthName : '带宽'
      }
    },
    methods: {
      focusScoll(){
        if(this.$route.query.type === 'local'){
            return
        }
        setTimeout(function () {
          $('.information .main').animate({
            scrollTop: 300
          });
        },500)
      },
      maxSquare(){
          this.bandwidthName = '最大方数';
          this.$store.state.information.bandwidthArr = [10,25,50,100,150,200,300,400,500];
          this.$store.state.information.bandwidthVisible = true;
      },
      introductionglen(){
        //  console.log(this.description.length)
        if(this.$store.state.information.description.length >= 100){
          this.$store.state.information.description = this.$store.state.information.description.substring(0,100);
          this.$toast('会议描述不能超过100个字符');
          return
        }
      },
      submitNext(){
        let that = this;
        if(!that.$store.state.information.name || !(that.$store.state.information.name.replace(/\ +/g, ""))){
            that.$toast('会议名称不能为空');
            return
        }
        that.$store.state.addMeetingParams.name = that.$store.state.information.name;
        //立即开始
        that.$store.state.information.nowStartMeeting ? that.$store.state.addMeetingParams.startNow = 0 :
          that.$store.state.addMeetingParams.startNow = 1;
        //视频会议
        if(that.$route.query.type === 'video'){
          //自动录像
          that.$store.state.information.videotape ? that.$store.state.addMeetingParams.recording = 1 :
            that.$store.state.addMeetingParams.recording = 0;
          //直播
          that.$store.state.information.live ? that.$store.state.addMeetingParams.streaming = 1 :
            that.$store.state.addMeetingParams.streaming = 0;
          //带宽
          if(that.$store.state.information.banwidthName.includes('kb')){
            that.$store.state.addMeetingParams.band_width = Number(that.$store.state.information.banwidthName.replace('kb',''))*1024;
          }else if(that.$store.state.information.banwidthName.includes('M')){
            that.$store.state.addMeetingParams.band_width = Number(that.$store.state.information.banwidthName.replace('M',''))*1024*1024;
          }else if(that.$store.state.information.banwidthName === '自动'){
            that.$store.state.addMeetingParams.band_width = 0;
          }

        }
        if(that.$route.query.type !== 'local'){
          that.$store.state.addMeetingParams.password = that.$store.state.information.meetingPassword;

        }
        if(that.$route.query.type === 'network'){
          // console.log(that.$store.state.networkmore.toolName[that.$store.state.networkmore.tabIndex]);
          switch (that.$store.state.networkmore.tabIndex){
            case 0:
              that.$store.state.addMeetingParams.optionAudio = 'voip';
              break;
            case 1:
              that.$store.state.addMeetingParams.optionAudio = 'telephony';
              break;
            case 2:
              that.$store.state.addMeetingParams.optionAudio = 'both';
              break;
          }
          if(!that.$store.state.loginData.data.hostId){
            that.$store.state.addMeetingParams.capacity = that.$store.state.information.banwidthName;
          }else {
            that.$store.state.addMeetingParams.optionJbh = that.$store.state.networkmore.nowStartMeeting;
          }
        }
        if(that.$route.query.type !== 'phone'){
          that.$store.state.addMeetingParams.description = that.$store.state.information.description;
          that.$router.push({
            path: '/information/meetingRoom',
            query: {type: this.$route.query.type}
          })
        }else {
          //  console.log(Date.parse(that.pickerValue));
          that.$store.state.addMeetingParams.startTime = Date.parse(that.$store.state.information.pickerValue);
console.log(that.$store.state.org.value1);
          that.$router.push({
            path: '/information/attendees',
            query: {type: this.$route.query.type}
          })
        }
        console.log(that.$store.state.addMeetingParams);
      },
      videotapeChange(){

      },
      liveChange(){

      },
      networkmore(){
        let that = this;

        this.$router.push({
          path: '/information/networkmore',
        })
      },
      open(picker) {
        this.$refs[picker].open();
      },
      handleConfirm(value){
        //  console.log(value);
        let that = this;

        /* console.log(Date.parse(new Date()));
         console.log(Date.parse(value));*/
        if(Date.parse(value) < Date.parse(new Date())){
          that.$toast('选择不能小于当前时间');
          return
        }
        this.$store.state.information.pickerTime = this.timetInit(value);
      },
      meetingStarChange(){
        console.log(this.$store.state.information.nowStartMeeting)

      },
      timetInit(date){
        let Y = date.getFullYear() + '/';
        let M = (date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1) + '/';
        let D = (date.getDate() < 10 ? '0' +  date.getDate() : date.getDate()) + ' ';
        let H = (date.getHours() < 10 ? '0' + date.getHours() : date.getHours()) + ':';
        let F = (date.getMinutes() < 10 ? '0' + date.getMinutes() : date.getMinutes());
        //  let S = (date.getSeconds() < 10 ? '0' + date.getSeconds() : date.getSeconds());
        return Y+M+D+H+F;
      },
      bandwidth(val){
          if(this.$route.query.type === 'video'){

          }
        this.$store.state.information.bandwidthVisible = false;
        this.$store.state.information.banwidthName = val;
      }
    },
    mounted() {
      let that = this;
      window.addEventListener('native.keyboardhide', function (e){
        $('.information').css({'overflow':'hidden'});
        $('.submitNext').css('display', 'block');
      });
      window.addEventListener('native.keyboardshow', function (e){
        $('.information').css({'overflow':'scroll'});
        $('.submitNext').css('display', 'none');
      });
      switch (that.$route.query.type){
        case 'local':
          that.$store.state.information.titleName = '本地会议';
          break;
        case 'video':
          that.$store.state.information.titleName = '视频会议';
          break;
        case 'network':
          that.$store.state.information.titleName = '网络会议';
          break;
        case 'phone':
          that.$store.state.information.titleName = '电话会议';
          //  console.log(Date.parse(new Date()));
          let dateParse = Date.parse(new Date()) + 300000 ;//时间戳为10位需*1000，时间戳为13位的话不需乘1000
          let date = new Date(dateParse);
          //  console.log(Y+M+D+H+F+S);

          if(!that.$store.state.information.pickerValue){
            that.$store.state.information.pickerValue = that.timetInit(date);
          }
          if(!that.$store.state.information.pickerTime){
            that.$store.state.information.pickerTime = that.timetInit(date);
          }
          break;
      }
      if(!that.$store.state.information.name){
        that.$store.state.information.name = that.$store.state.loginData.data.name + '的'+ that.$store.state.information.titleName;
      }
      console.log(that.$store.state.information);
    }
  };
</script>
<style>
  .information .mint-popup-bottom{

  }
  .videoNext{
    position: inherit!important;
    margin-top: 0.55rem!important;
  }
  .lineStartNow .mint-actionsheet .mint-actionsheet-list{
    border-radius: 0.3rem;
  }
  .lineStartNow .mint-popup-bottom{
    /* height: 95%;
     margin: 5% auto;
     width: 93%;
     border-radius: 0.3rem;*/
  }
  .lineStartNow .mint-popup-bottom .picker, .lineStartNow .mint-popup-bottom .picker .picker-items{
    /* height: 100%;*/
  }


  .meetingName .mint-switch-core{
    border: 0.02rem solid #d9d9d9;
  }


</style>
<style lang="less" rel="stylesheet/less" scoped>
  .h92{
    height: 92%;
  }
  .information{
    position: relative;
    height: 100%;
    min-height: 6.4rem;
    background-color: #f2f5f7;
    color:#474747;
    .main{
      width: 100%;
      height: 91%;
      overflow-y: scroll;
    }
  .infoColor{
    color: #b9b9b9;
  }
  .network{
    margin: 0.3rem 0;
  }
  .lineName{
    box-shadow: 0px 1px 3px 0px
    rgba(2, 2, 2, 0.1);
    margin: 0.3rem 0;
  }
  .introduction{
    box-shadow: none;
    margin-bottom: 0px;
  }
  .line{
    width: 100%;
    background-color: #ffffff;
  .flex_auto{
    height: 0.75rem;
    font-size: 0.3rem;
    border-bottom: 1px solid #eee;
    width: 92%;
    margin-left: 4%;
  .meetingTitle{
    width: 25%;
    text-align: left;
    margin-right: 0.15rem;
  }
  .meetingName{
    width: 70%;
  input{
    font-size: 0.3rem;
  }
  input::-webkit-input-placeholder{
    color: #b9b9b9;
  }
  }
  .networkSet{
    width: 65%;
    margin-right: 0.4rem;
  i.arrow-right-copy{
    font-size: 0.35rem;
    color: #b9b9b9;
  }
  }
  }
  .flex_auto:last-child{
    border-bottom: none;
  }
  }
  .submitNext{
    position: fixed;
    bottom: 0.45rem;
    width: 90%;
    margin-left: 5%;
    padding: 0;
    height: 0.75rem;
    line-height: 0.75rem;
    background-color: #227ee1;
    border-radius: 0.1rem;
    color: #FFFFFF;
    font-size: 0.3rem;
    text-align: center;
  }
  .introductionTextarea{
    width: 100%;
  textarea{
    width: 100%;
    text-indent: 0.3rem;
    font-size: 0.3rem;
    height: 2.5rem;
  }
  }
    .bandwidthList{
      width: 100%;
      height: 100%;
      position: relative;
      .title{
        position: fixed;
        width: 100%;
        top: 0;
      }
      .cancel{
        margin-top: 0.2rem;
        margin-bottom: 0.2rem;
        width: 90%;
        margin-left: 5%;
      }
      .lineBandWidthList{
        height: 85%;
        overflow-y: scroll;
        margin-top: 0.55rem;

      }
    }
  }
</style>
