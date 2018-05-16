<template>
    <div class="mainMy themeBgColor">
      <mt-header fixed title= "我的">
      </mt-header>
      <div class="themeBgColor" style="height: 0.88rem"></div>
      <div class="line title" @click="$router.push({path: '/my/imgUpdate'})">
        <div class="img">
          <img :src="url + $store.state.loginData.data.logoUrl" alt="">
        </div>
        <span>{{$store.state.loginData.data.name}}</span>
        <i class="iconfont arrow-right-copy"></i>
      </div>
      <div class="line" @click="$router.push({'path': 'information/conditions'})">
        <img src="../../assets/img/my_lx@2x.png" alt="">
        我的联系人
        <i class="iconfont arrow-right-copy"></i>
      </div>
      <div class="line" @click="$router.push({path: '/my/about'})">
        <img src="../../assets/img/about_U@2x.png" alt="">
        关于Umeet
        <i class="iconfont arrow-right-copy"></i>
      </div>
      <div class="exitLogin" @click="exit">退出登录</div>
    </div>
</template>

<script type="text/ecmascript-6">
    export default {
        name: 'aboutMy',
        data() {
            return{
                url: 'http://' + localStorage.ip + ':8080'
            }
        },
        methods: {
            exit(){
                localStorage.loginSucess = false;
              this.$router.push({'path': '/'});
            }
        },
        mounted() {
        }

    };
</script>

<style lang="less" rel="stylesheet/less" scoped>
.mainMy{
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
    font-size: 0.28rem;
    img{
      width: 0.5rem;
      margin-right: 0.2rem;
      padding-top: 0.27/2rem;
    }
    .arrow-right-copy{
      position: absolute;
      right: 0.3rem;
      color: #b9b9b9;
      font-size: 0.3rem;
    }
  }
  .title{
    height: 1.74rem;
    line-height: 1.74rem;
    font-size: 0.3rem;
    .img{
      width: 1.3rem;
      height: 1.3rem;
      padding-top: 0.22rem;
      border-radius: 50%;
      display: inline-block;
    }
    img{
      width: 100%;
      height: 100%;
      border-radius: 50%;
      margin: 0;
      padding: 0;
    }
    span{
      position: absolute;
      right: 0.88rem;
    }
  }
  .exitLogin{
      width: 90%;
    height: 0.77rem;
    line-height: 0.77rem;
    margin: 0.7rem 5%;
    text-align: center;
    background-color: #227ee1;
    border-radius: 0.1rem;
    color: #ffffff;
    font-size: 0.3rem;
  }
}
</style>
