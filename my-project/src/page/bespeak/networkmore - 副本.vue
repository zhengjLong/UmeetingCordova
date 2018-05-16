<template>
  <div class="networkmore themeBgColor">
    <mt-header title= "更多设置">
      <span slot="left">
        <mt-button @click.native= "$router.go(-1)"><i class="iconfont icon-fanhui"></i></mt-button>
      </span>
    </mt-header>
    <div class="selectTool">
      <mt-radio
        v-model="toolName"
        title=""
        :options="toolOptions">
      </mt-radio>
    </div>
    <div class="checkAdd flex_between bg_fff">
      <div class="title">在主持人入会前加入会议</div>
      <div class="switchStart">
        <mt-switch v-model="nowStartMeeting" @change="meetingStarChange">
        </mt-switch>
      </div>
    </div>
    <mt-button type="primary" class="submit" @click.native="submit">登录</mt-button>
  </div>
</template>

<script>
  export default {
    name: 'Networkmore',
    data() {
      return{
        username: '',
        toolName: '',
        toolOptions: ['只使用电话','只使用VOIP','使用电话和VIOP']
      }
    },
    created() {
      this.options1 = ['选项A', '选项B', '选项C'];
    },
    methods: {
      submit(){

      }
    },
    mounted() {

    }

  };
</script>

<style lang="less" type="text/less" scoped>
  .mint-header{
    height: 0.88rem;
    font-size: 0.35rem;
    background-color: #227ee1;
  }
  .networkmore{
    width: 100%;
    height: 100%;
    color: #474747;
    font-size: 0.3rem;
    .selectTool{
      /*width: 100%;
      background-color: #ffffff;
      box-shadow: 0px 1px 3px 0px
      rgba(2, 2, 2, 0.1);
      margin: 0.3rem 0;*/

    }
    .checkAdd{
      .title{
        padding: 0.15rem 0.3rem;

      }
  .switchStart{
    padding-right: 0.3rem;
  }
    }
    .submit{

    }
  }
</style>
<style>
  .selectTool .mint-cell-wrapper{
    /*  background-image: none;
      font-size: 0.3rem;
      padding: 0;*/
  }
  .selectTool .mint-cell{
   /* height: 0.88rem;
    display: flex;
    align-items: center;
    display: -webkit-flex;
    -webkit-align-items: center;*/
  }
  .selectTool .mint-cell-wrapper .mint-radiolist-label{
   /* padding: 0 0.3rem;*/
  }
  .selectTool .mint-cell-wrapper .mint-radio-core{
   /* width: 0.3rem;
    height: 0.3rem;*/
  }
  .mint-radio-core::after{
    /*width: 0.18rem;
    height: 0.18rem;
    top: 0.045rem;
    left: 0.045rem;*/
    -webkit-transition: -webkit-transformss 0s;
    transition: -webkit-transformss 0s;
    transition: transformss 0s;
    transition: transformss 0s, -webkit-transformss 0s;
  /*  -webkit-transform: scale(0);
    transform: scale(0);*/
  }
  .mint-radio-input:checked + .mint-radio-core::after{
    -webkit-transform: scale(0)!important;
    transform: scale(0)!im;
  }
</style>
