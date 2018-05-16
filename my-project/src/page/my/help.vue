<template>
  <div class="help themeBgColor">
    <mt-header fixed title= "使用帮助">
      <span slot="left">
        <mt-button @click.native= "$router.go(-1)"><i class="iconfont fanhui"></i></mt-button>
      </span>
    </mt-header>
    <div class="themeBgColor" style="height: 0.88rem;width: 100%;"></div>
    <div class="line" @click="toUrl('https://u.systeccloud.com/helpVideo')">
      如何控制视频会议
      <i class="iconfont arrow-right-copy"></i>
    </div>
    <div class="line" @click="toUrl('https://u.systeccloud.com/helpPhone')">
      如何控制电话会议
      <i class="iconfont arrow-right-copy"></i>
    </div>
  </div>
</template>

<script type="text/ecmascript-6">
  export default {
    name: 'help',
    data() {
      return{

      }
    },
    methods: {
      toUrl(val){
        cordova.InAppBrowser.open(val,'_system' ,'location=no,toolbar=yes,toolbarposition=top,closebuttoncaption=关闭')
      }
    },
    mounted() {
    }

  };
</script>

<style lang="less" rel="stylesheet/less" scoped>
  .help{
    position: relative;
    width: 100%;
    height: 100%;
  .line{
    height: 0.77rem;
    line-height: 0.77rem;
    background-color: #ffffff;
    margin: 0.3rem 0;
    padding: 0 0.3rem;
    position: relative;
    font-size: 0.25rem;
  .iconfont{
    position: absolute;
    right: 0.3rem;
    color: #b9b9b9;
    font-size: 0.4rem;
  }
  }
  }
</style>
