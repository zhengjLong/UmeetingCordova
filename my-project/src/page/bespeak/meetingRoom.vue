<template>
  <div class="meetingRoom">
    <mt-header title= "选择会议室">
      <span slot="left">
        <mt-button @click.native= "$router.go(-1)"><i class="iconfont fanhui"></i></mt-button>
      </span>
    </mt-header>
    <div class="main themeBgColor">
      <div class="search themeBgColor flex_auto">
        <div class="flex_start">
          <i class="iconfont sousuo"></i>
          <input type="text"
                 maxlength="20"
                 v-model="$store.state.meetingRoom.searchRoom" v-on:input="search" placeholder="请输入会议室名称">
        </div>
      </div>
      <div class="selectTime flex_between bg_fff">
        <div class="start_time flex_auto" @click="startTimePick">
          开始时间
          <div>{{$store.state.meetingRoom.pickerStartTime}}</div>
        </div>
        <div class="start_time flex_auto" @click="$refs.picker1.open()">时长<div>{{$store.state.meetingRoom.duration}}</div></div>
        <!--<div class="duration">时长</div>-->
      </div>
      <div v-if="$route.query.type === 'local'" style="height: 0.1rem;"></div>
      <div class="container bg_fff">
       <!-- <template v-for="(item,index) in $store.state.meetingRoom.RoomList" v-if="$route.query.type === 'local'">
          <div class="line" @click="selectRoom(index,item)">
            <i class="iconfont circle2yuanquan" v-if="$store.state.meetingRoom.tabIndex !== index"></i>
            <i class="iconfont xuanze" v-if="$store.state.meetingRoom.tabIndex === index"></i>
            {{item.label}}
          </div>
        </template>-->
        <mt-radio v-if="$route.query.type === 'local'"
          class="page-part"
          v-model="$store.state.meetingRoom.meetingRoomUuids"
          :options="$store.state.meetingRoom.RoomList" ></mt-radio>
        <mt-checklist v-if="$route.query.type !== 'local'"
                      class="page-part"
                      v-model="$store.state.meetingRoom.value1"
                      :options="$store.state.meetingRoom.RoomList">
        </mt-checklist>
      </div>
      <footer>
        <div class="flex_between">
          <div v-if="$route.query.type !== 'local'">已选<span>{{$store.state.meetingRoom.value1.length}}</span>个会议室</div>
          <div @click="next" :class="{large: $route.query.type === 'local'}">下一步</div>
        </div>
      </footer>
    </div>
    <mt-datetime-picker
      ref="picker"
      type="datetime"
      v-model="$store.state.meetingRoom.start_time"
      year-format="{value}年"
      month-format="{value}月"
      date-format="{value}日"
      hour-format="{value}时"
      minute-format="{value}分"
      second-format="{value}秒"
      @confirm="startTimeConfirm">
    </mt-datetime-picker>
    <mt-datetime-picker
      ref="picker1"
      type="time"
      v-model="$store.state.meetingRoom.duration"
      hour-format="{value}小时"
      minute-format="{value}分钟"
      @confirm="durationConfirm">
    </mt-datetime-picker>
  </div>
</template>

<script type="text/ecmascript-6">
  export default {
    name: 'meetingRoom',
    data() {
      return{
        value2: ''
      }
    },
    methods: {
      //开会时间选择
      startTimePick(){
        if(this.$store.state.addMeetingParams.startNow === 0){
          return
        }
        this.$refs.picker.open();
      },
      //会议室搜索
      search(){
        let that = this;
        let arr = [];
        if(!that.$store.state.meetingRoom.searchRoom){
          console.log(that.$store.state.meetingRoom.RoomList)
          that.$store.state.meetingRoom.RoomList = that.$store.state.meetingRoom.list;
        }else {
          for(let i in that.$store.state.meetingRoom.list){
            if(that.$store.state.meetingRoom.list[i].label.includes(that.$store.state.meetingRoom.searchRoom) ||
              that.$store.state.meetingRoom.list[i].namePinying.includes(that.$store.state.meetingRoom.searchRoom.toLowerCase())){
              arr.push(that.$store.state.meetingRoom.list[i]);
              // console.log(that.RoomList[i]);
            }
          }
          that.$store.state.meetingRoom.RoomList = arr;
          if(arr.length === 0){
              that.$toast('暂无数据')
          }
        }
      },
      //本地会议
      selectRoom(index,item){
        let that = this;
        this.$store.state.meetingRoom.tabIndex = index;
        //     console.log(item);
        that.$store.state.meetingRoom.meetingRoomUuids = item.uuid;
      },
      selectRoomCheck(item){

      },
      next(){
        let that = this;
        if(that.$route.query.type === 'phone'){
          return
        }

        if(that.$route.query.type === 'local'){
          //  console.log(that.meetingRoomUuids);
          if(!that.$store.state.meetingRoom.meetingRoomUuids){
            that.$toast('请选择会议室');
            return
          }
          that.$store.state.addMeetingParams.meetingRoomUuids = that.$store.state.meetingRoom.meetingRoomUuids;
        }else {
         /* if(that.$store.state.meetingRoom.value1.length === 0){
            that.$toast('请选择会议室');
            return
          }
              console.log(that.$store.state.meetingRoom.value1);
          return*/
          that.$store.state.addMeetingParams.meetingRoomUuids = that.$store.state.meetingRoom.value1.join(',');
          //
        }
        that.$store.state.addMeetingParams.startTime = Date.parse(that.$store.state.meetingRoom.start_time);
        let hour = that.$store.state.meetingRoom.duration.substring(0,that.$store.state.meetingRoom.duration.indexOf(':'));
        let minutes = that.$store.state.meetingRoom.duration.substring(that.$store.state.meetingRoom.duration.indexOf(':')+1,that.$store.state.meetingRoom.duration.length);
        that.$store.state.addMeetingParams.duration = Number(hour)*60 + Number(minutes);
        //console.log(that.$store.state.addMeetingParams);
        console.log(that.$store.state.org.value1);
        this.$router.push({
          path : '/information/attendees',
          query: {type: this.$route.query.type}
        })
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
      startTimeConfirm(value){
        let that = this;
        /* console.log(Date.parse(new Date()));
         console.log(Date.parse(value));*/
        if(Date.parse(value) < Date.parse(new Date())){
          that.$toast('选择不能小于当前时间');
          return
        }
        this.$store.state.meetingRoom.pickerStartTime = this.timetInit(value);
      //  console.log(Date.parse(this.$store.state.meetingRoom.pickerStartTime))
        let hour = that.$store.state.meetingRoom.duration.substring(0,that.$store.state.meetingRoom.duration.indexOf(':'));
        let minutes = that.$store.state.meetingRoom.duration.substring(that.$store.state.meetingRoom.duration.indexOf(':')+1,that.$store.state.meetingRoom.duration.length);
        let durationSeconds = (Number(hour)*60 + Number(minutes));
        console.log(durationSeconds);
        let data = {
          durationSeconds : durationSeconds,
          startTime : Date.parse(this.$store.state.meetingRoom.pickerStartTime),
          type : that.$store.state.meetingRoom.type
        };
        that.meetingRoomList(data);
      },
      durationConfirm(value){
        let that = this;
        // console.log(value.substring(value.indexOf(':')+1,value.length))

        let hour = value.substring(0,value.indexOf(':'));
        let minutes = value.substring(value.indexOf(':')+1,value.length);
        let durationSeconds = (Number(hour)*60 + Number(minutes));
        console.log(durationSeconds);
        if(durationSeconds === 0){
            that.$toast('会议时长不能为0');
          that.$store.state.meetingRoom.duration = '01:00';
            return
        }
        let data = {
          durationSeconds : durationSeconds,
          startTime : Date.parse(this.$store.state.meetingRoom.pickerStartTime),
          type : that.$store.state.meetingRoom.type
        };
        that.meetingRoomList(data);
      },
      meetingRoomList(params){
        let that = this;
        that.$indicator.open('加载中');
        that.$store.state.meetingRoom.list = that.$store.state.meetingRoom.RoomList = [];
        that.axios.post(that.$store.state.api.meetingRoomOccupy+'?sessionId='+
          that.$store.state.loginData.data.sessionId,params)
          .then(function (res) {
            if(!res.data.data){
              that.$toast(res.data.message);
              that.$indicator.close();
            }else{
              // console.log(res.data.data);
              // that.RoomList = res.data.data;
              //   console.log(that.$store.state.meetingRoom.tabIndex);
              for(let i in res.data.data){
                if(res.data.data[i].roomOccupys.length === 0){
                  that.$store.state.meetingRoom.list.push({
                    label: res.data.data[i].name,
                    value: res.data.data[i].uuid,
                    uuid: res.data.data[i].uuid,
                    namePinying: res.data.data[i].namePinying
                  })

                  /*  if(that.$route.query.type === 'local'&& res.data.data[i].uuid === that.$store.state.meetingRoom.tabIndex){
                   that.$store.state.meetingRoom.tabIndex = Number(i);
                   }*/

                  /* res.data.data[i].name =  that.RoomList[i].label = that.RoomList.name;
                   res.data.data[i].uuid =  that.RoomList[i].uuid;*/
                }
                let val = that.$store.state.meetingRoom.value1.join(',');
                if(that.$route.query.type !== 'local' && res.data.data[i].roomOccupys.length !== 0 && val.includes(res.data.data[i].uuid)){
                  that.$store.state.meetingRoom.list.push({
                    label: res.data.data[i].name,
                    value: res.data.data[i].uuid,
                    uuid: res.data.data[i].uuid,
                    namePinying: res.data.data[i].namePinying
                  })
                }
                if(that.$route.query.type === 'local' && res.data.data[i].roomOccupys.length !== 0 && res.data.data[i].uuid === that.$store.state.meetingRoom.meetingRoomUuids){
                  that.$store.state.meetingRoom.list.push({
                    label: res.data.data[i].name,
                    value: res.data.data[i].uuid,
                    uuid: res.data.data[i].uuid,
                    namePinying: res.data.data[i].namePinying
                  })
                  that.$store.state.meetingRoom.meetingRoomUuids = res.data.data[i].uuid;
                }
              }

              if(!that.$store.state.meetingRoom.searchRoom){
                that.$store.state.meetingRoom.RoomList = that.$store.state.meetingRoom.list;
              }else {
                that.search();
              }

              /* for(let i in that.RoomList){
               if(that.RoomList[i].roomOccupys.length !== 0){
               that.RoomList.splice(i, 1);
               }
               }
               for(let j in that.RoomList){
               that.RoomList[j].label = that.RoomList[j].name
               }*/

              that.$indicator.close();
            }
          }).catch(function (err) {
          console.log(err)
          that.$toast('会议室获取失败');
          that.$indicator.close();
        })
      },
    },
    mounted() {
      let that = this;
      let dateParse = Date.parse(new Date()) + 300000 ;//时间戳为10位需*1000，时间戳为13位的话不需乘1000
      let date = new Date(dateParse);
      if(!that.$store.state.meetingRoom.start_time){
        that.$store.state.meetingRoom.start_time = that.timetInit(date);
      }
      if(!that.$store.state.meetingRoom.pickerStartTime){
        that.$store.state.meetingRoom.pickerStartTime = that.timetInit(date);
      }
      let hour = that.$store.state.meetingRoom.duration.substring(0,that.$store.state.meetingRoom.duration.indexOf(':'));
      let minutes = that.$store.state.meetingRoom.duration.substring(that.$store.state.meetingRoom.duration.indexOf(':')+1,that.$store.state.meetingRoom.duration.length);
      let startTime = Date.parse(new Date(that.$store.state.meetingRoom.start_time));
      let durationSeconds = Number(hour)*60;
      //   console.log(startTime);
      //  console.log(durationSeconds);
      switch (that.$route.query.type){
        case 'video':
          that.$store.state.meetingRoom.type = 0;
          break;
        case 'local':
          that.$store.state.meetingRoom.type = 1;
          break;
        case 'network':
          that.$store.state.meetingRoom.type = 0;
          break;
      };
      let data = {
        durationSeconds : durationSeconds,
        startTime : startTime,
        type : that.$store.state.meetingRoom.type
      };
      that.meetingRoomList(data);
      console.log(that.$store.state.meetingRoom);
      console.log(that.$store.state.meetingRoom.value1);
      // console.log(that.RoomList);
    }

  };
</script>
<style>

</style>
<style lang="less" rel="stylesheet/less" scoped>
  .meetingRoom{
    width: 100%;
    height: 100%;
    position: relative;
    min-height: 6.4rem;
    .mint-header{
      height: 0.88rem;
      font-size: 0.35rem;
      background-color: #227ee1;
      margin: 0px;
      padding: 0px;
      position: fixed;
      width: 100%;
      top: 0;
      z-index: 9999;
    }
    .main{
      height: 100%;
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
      .selectTime{
        width: 100%;
        height: 0.8rem;
        top:1.69rem;
        font-size: 0.22rem;
        .start_time{
          margin-left: 0.3rem;
          margin-right: 0.3rem;
          div{
            padding: 0.1rem 0.25rem;
            margin-left: 0.1rem;
            border-radius: 0.05rem;
            border:1px solid #eeeeee;
            font-size: 0.24 rem;
          }
        }
        /* .start_time:first-child{
           margin-left: 0.3rem;
         }
         .start_time:last-child(1){
           margin-right: 0.3rem;
         }*/
      }
      .search,.selectTime{
        position: fixed;
      }
      .container{
        height: 63.5%;
        margin-top: 2.48rem;
        min-height: 6.4rem;
        /*      margin-bottom: 1rem;*/
        overflow-y: scroll;
        .line{
          height: 0.7rem;
          line-height: 0.7rem;
          margin-left: 0.3rem;
          font-size: 0.26rem;
          border-bottom: 1px solid #eeeeee;
          .iconfont{
            font-size: 0.3rem;
            margin-right: 0.1rem;
            color: #CCCCCC;
          }
          .xuanze{
            color: #26a2ff;
          }
        }
        .line:last-child{
          border-bottom: none;
        }
      }
      footer{
        border-top: 1px solid #D9D9D9;
        background-color: #FFFFFF;
        position: fixed;
        bottom: 0;
        width: 100%;
        /* margin-left: 5%;*/
        .flex_between{
          margin-left: 0.3rem;
          margin-right: 0.3rem;
          padding: 0.3rem 0;
          font-size:0.28rem;
          div{
            padding: 0rem 0.2rem;
            border-radius: 0.08rem;
            height: 0.65rem;
            line-height: 0.65rem;
          }
          div:nth-of-type(1){
            border: 1px solid #227ee1;
            background-color: transparent;
            color: #26a2ff;

          }
          div:nth-of-type(2){
            background-color: #227ee1;
            color: #fff;
          }
        }
        .large{
          width: 90%;
          background-color: #227ee1!important;
          color: #FFFFFF!important;
          text-align: center!important;
        }
      }
    }
  }
</style>
