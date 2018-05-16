<template>
  <div class="roomDetailsList themeBgColor">
    <mt-header fixed title= "会议室详情">
      <span slot="left">
        <mt-button @click.native= "$router.go(-1)"><i class="iconfont fanhui"></i></mt-button>
      </span>
    </mt-header>
    <div class="search flex_auto">
      <div class="flex_start">
        <i class="iconfont sousuo"></i>
        <input type="text" maxlength="20" v-model="$store.state.roomDetailsList.searchVal" v-on:input="search" placeholder="请输入会议室名称">
      </div>
    </div>
    <template v-for="item in $store.state.roomDetailsList.list">
        <div class="list flex_auto" @click="roomDetail(item)">
          <div class="img flex_auto"><img src="../../assets/img/meeting_room@3x.png" alt=""></div>
          <div class="information">
            <div>{{item.name}}</div>
            <div style="font-size: 0.2rem;color: #b9b9b9;"><i class="iconfont weizhi"></i>{{item.address}}</div>
          </div>
          <div class="icon"><i class="iconfont arrow-right-copy"></i></div>
        </div>
    </template>
  </div>
</template>

<script type="text/ecmascript-6">
  export default {
    name: 'roomDetailsList',
    data() {
      return{
      }
    },

    methods: {
      search(){
         let that = this;
       //  that.$store.state.roomDetailsList.searchVal
      //  console.log(that.$store.state.roomDetailsList.searchVal);
        let list = [];
        if(!that.$store.state.roomDetailsList.searchVal){
          that.$store.state.roomDetailsList.list = that.$store.state.nowMeetingInformation.meetingRooms;
        }else {
          for(let i in that.$store.state.nowMeetingInformation.meetingRooms){
            if(that.$store.state.nowMeetingInformation.meetingRooms[i].name.includes(that.$store.state.roomDetailsList.searchVal)||
              that.$store.state.nowMeetingInformation.meetingRooms[i].namePinying.includes(that.$store.state.roomDetailsList.searchVal.toLowerCase())
            ){
              list.push(that.$store.state.nowMeetingInformation.meetingRooms[i])
            }
          }
          that.$store.state.roomDetailsList.list = list;
        }

      },
      roomDetail(item){
          this.$store.state.meetingRoomDetails = item;
        switch (item.type){
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
      }
    },
    mounted() {
      let that = this;
      that.search();
    }

  };
</script>

<style lang="less" rel="stylesheet/less" scoped>
  .roomDetailsList{
    overflow-y: scroll;
    width: 100%;
    height: 100%;
    font-size: 0.3rem;
    .search{
      width: 100%;
      height: 0.8rem;
      margin-top:0.88rem;
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
  .list{
     height: 1.3rem;
    line-height: 1.3rem;
    border-bottom: 1px solid #d9d9d9;
    background-color: #ffffff;
    .img{
      width: 20%;
      img{
        width: 0.94rem;
      }
    }
    .information{
      width: 70%;
      div{
        height: 0.5rem;
        line-height: 0.5rem;
      }
    }
    .icon{
      width: 10%;
      text-align: center;
    }
  }
  }
</style>
