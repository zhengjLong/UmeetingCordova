<template>
  <div class="networkmore themeBgColor">
    <mt-header title= "更多设置">
      <span slot="left">
        <mt-button @click.native= "$router.go(-1)"><i class="iconfont fanhui"></i></mt-button>
      </span>
    </mt-header>
    <div class="selectTool">
      <template v-for="(item,index) in $store.state.networkmore.toolName">
        <div class="line" @click="selectTool(index)">
          <i class="iconfont circle2yuanquan" v-if="$store.state.networkmore.tabIndex !== index"></i>
          <i class="iconfont xuanze" v-if="$store.state.networkmore.tabIndex === index"></i>
          {{item}}
        </div>
      </template>

    <!--  <div class="line">
        <i class="iconfont icon-circle2yuanquan"></i>
        只使用电话
      </div>
      <div class="line">
        <i class="iconfont icon-circle2yuanquan"></i>
        只使用电话
      </div>-->
    </div>
    <div class="checkAdd flex_between bg_fff" v-if="$store.state.loginData.data.hostId && !$store.state.loginData.data.publicUmeetingRoomEnable">
      <div class="title">在主持人入会前加入会议</div>
      <div class="switchStart">
        <mt-switch v-model="$store.state.networkmore.nowStartMeeting" @change="meetingStarChange">
        </mt-switch>
      </div>
    </div>
    <mt-button type="primary" class="sure" @click.native="sure">确定</mt-button>
  </div>
</template>

<script type="text/ecmascript-6">
  export default {
    name: 'Networkmore',
    data() {
      return{

      }
    },
    created() {

    },
    methods: {
      selectTool(index){
        console.log(index);
        console.log(this.$store.state.networkmore.tabIndex);
        this.$store.state.networkmore.tabIndex = index;
      },
      sure(){
       //   console.log(this.$store.state.networkmore);
        this.$router.go(-1);
      },
      meetingStarChange(event){
        console.log(event)
      }
    },
    mounted() {
        let that = this;
      // console.log(this.toolOptions)
    }

  };
</script>
<style>

</style>
<style lang="less" rel="stylesheet/less" scoped>

  .networkmore{
    width: 100%;
    height: 100%;
    position: relative;
    color: #474747;
    font-size: 0.3rem;
  .selectTool{
    width: 100%;
    background-color: #ffffff;
    box-shadow: 0px 1px 3px 0px
    rgba(2, 2, 2, 0.1);
    margin: 0.88rem 0 0.3rem 0;
    .line{
      height: 0.7rem;
      line-height: 0.7rem;
      margin-left: 0.3rem;
      border-bottom: 1px solid #eeeeee;
      .iconfont{
        font-size: 0.3rem;
        margin-right: 0.1rem;
      }
      .xuanze{
        color: #26a2ff;
      }
    }
    .line:last-child{
      border-bottom: none;
    }
  }
  .checkAdd{
    .title{
      padding: 0.15rem 0.3rem;

    }
    .switchStart{
      padding-right: 0.3rem;
    }
  }
  .sure{
    margin-top: 0.7rem;
    width: 90%;
    margin-left: 5%;
  }
  }
</style>

