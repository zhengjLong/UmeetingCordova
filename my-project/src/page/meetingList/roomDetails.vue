<template>
  <div class="roomDetails">
    <mt-header fixed title= "会议室详情">
      <span slot="left">
        <mt-button @click.native= "$router.go(-1)"><i class="iconfont fanhui"></i></mt-button>
      </span>
    </mt-header>
    <div class="title">
      {{$store.state.meetingRoomDetails.name}}
    </div>
    <p>{{$store.state.meetingRoomDetails.address}}</p>
    <p>{{$store.state.meetingRoomDetails.typeName}}</p>
    <p v-if="$store.state.meetingRoomDetails.galleryful">{{$store.state.meetingRoomDetails.galleryful}}人</p>
    <p>{{$store.state.meetingRoomDetails.configuration}}</p>
    <img src="../../assets/img/yuyuetu@2x.png" alt="">
  </div>
</template>

<script type="text/ecmascript-6">
  export default {
    name: 'roomDetails',
    data() {
      return{
        type: ''
      }
    },

    methods: {

    },
    mounted() {
        let that = this;
    }

  };
</script>

<style lang="less" rel="stylesheet/less" scoped>
  .roomDetails{
    overflow-y: scroll;
    width: 100%;
    height: 100%;
    font-size: 0.28rem;
    .title{
      font-size: 0.3rem;
      color: #474747;
      width: 90%;
      border-bottom: 1px solid #d9d9d9;
      margin-top: 1.35rem;
      margin-left: 0.3rem;
      padding-bottom: 0.3rem;
      font-weight: bold;
    }
    p{
      padding: 0.1rem 0.3rem;
      font-size: 0.26rem;
    }
    img{
      width: 90%;
      margin-left: 0.3rem;
      margin-top: 0.3rem;
    }
  }
</style>
