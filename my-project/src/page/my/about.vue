<template>
  <div class="about themeBgColor">
    <mt-header fixed title= "关于Umeet">
      <span slot="left">
        <mt-button @click.native= "$router.go(-1)"><i class="iconfont fanhui"></i></mt-button>
      </span>
    </mt-header>
    <div class="themeBgColor" style="height: 0.88rem"></div>
    <div class="line">
      当前版本
      <i class="iconfont" style="font-size: 0.3rem">V2.0</i>
    </div>
    <div class="line" style="margin-bottom: 0;border-bottom:1px solid #eeeeee;" @click="$router.push({path:'introduce'})">
      产品介绍
      <i class="iconfont arrow-right-copy"></i>
    </div>
    <div class="line" style="margin-top: 0;" @click="$router.push({path: '/my/help'})">
      使用帮助
      <i class="iconfont arrow-right-copy"></i>
    </div>
    <div class="line">
      客服电话
      <i class="iconfont" style="font-size: 0.3rem">
        <a href="tel:400-618-3810">
          400-618-3810
        </a>

      </i>
    </div>
    <footer>
      <div class="moveto flex_auto">
        <div @click="move('http://systec.com.cn/#/')">尚阳官网</div>
        <div @click="$router.push({path: 'agreement'})">服务协议</div>
      </div>
      <div class="info">
        <p>Copyright ©2018尚阳科技股份有限公司</p>
        <p>All Rights Reserved</p>
        <p>Version 2.0</p>
      </div>
    </footer>
  </div>
</template>
<script type="text/ecmascript-6">
  export default{
    name : 'about',
    data(){
      return{
        tel:'400-618-3810'
      }
    },
    methods:{
      move(val){
        cordova.InAppBrowser.open(val,'_system' ,'location=no,toolbar=yes,toolbarposition=top,closebuttoncaption=关闭')
      }
    },
    mounted(){
    }
  }
</script>
<style lang="less" rel="stylesheet/less" scoped>
  .about{
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
        a{
          color: #b9b9b9!important;
        }
      }
    }
    footer{
      position: fixed;
      bottom: 0;
      width: 100%;
      .moveto{
        color: #00abff;
        width: 100%;
        padding-bottom: 0.3rem;
        div{
          width: 2rem;
          text-align: center;
        }
      }
      .info{
        width: 100%;
        text-align: center;
        p{
          padding-bottom: 0.08rem;
          color: #7d7d7d;
        }
        p:last-child{
          margin-top: 0.2rem;
        }

      }
    }
  }
</style>
