<template>
  <div id="app">
    <router-view/>
  </div>
</template>

<script>
export default {
  name: 'App',
  mounted(){
    let that = this;
    let navOnLine;
    let count = 0;
    let onlineFun = () => {
      navOnLine = setInterval(function () {
        if(navigator.connection.type === 'none'){
            count++;
            if(count === 1){
              that.$toast('当前无网络，请检查网络设置');
            }else if(count === 2){
              count = 2;
            }
        }else {
          count = 0;
        }
      },2000)
    }
    onlineFun();
    that.$store.commit('backcount',{
      $toast: that.$toast
    });
   /* document.addEventListener("backbutton", function(e){
      let locationHref = window.location.href.substring(window.location.href.indexOf('#')+1,window.location.href.length);
      alert(locationHref);
    },false)*/
  }
}
</script>

<style lang="less" rel="stylesheet/less">
  @import "assets/base.css";
  @import "assets/font_605897_i66efaj44k95p14i/iconfont.css";
  html,body,#app{
    width: 100%;
    height: 100%;
    overflow: hidden;
  }
  .page-loadmore-wrapper{
    width: 100%;
    height: 100%!important;
  }
  .mint-loadmore{
    min-height: 80%;
  }
  .mint-popup-bottom{
    width: 90%;
    bottom: 0.3rem;
    max-height: 92%;
    border-radius: 0.1rem;
  }
  .picker-item{
    padding: 0px;
  }
  .mint-datetime-action{
    width: 0.5rem;
    text-align: inherit;
  }
  span.mint-datetime-action:nth-of-type(1){
    padding-left: 0.2rem;
  }
  span.mint-datetime-action:nth-of-type(2){
    padding-right: 0.2rem;
  }
  span.mint-datetime-action{
    font-size: 0.25rem;
  }
  .mint-datetime-action{
    color: #ffffff;
  }
  .mint-datetime .picker-toolbar{
    background-color: #bfbfbf;
  }
  .v-modal{
  /*  opacity: 0;
    background: #fff;*/
  }
  .mint-header{
    height: 0.88rem;
    font-size: 0.32rem;
    background-color: #227ee1;
    margin: 0px;
    padding: 0px;
    position: fixed;
    width: 100%;
    top: 0;
    z-index: 99;
  }
  .mint-header-button.is-left .iconfont{
      font-size: 0.32rem;
    }
  .mint-header-button.is-left{
    padding-left: 0.3rem;
  }
  .mint-header-button.is-right{
    padding-right: 0.3rem;
  }
  .mint-header-button.is-right .iconfont{
    font-size: 0.5rem;
  }
  .mint-button--primary{
    background-color: #227ee1;
  }
  .mint-switch-input:checked + .mint-switch-core {
    border-color: #227ee1;
    background-color: #227ee1;
  }
  .mint-checkbox-core{
    width: 0.3rem;
    height: 0.3rem;
  }
  .mint-cell-wrapper{
    background-image: -webkit-gradient(linear, left top, left bottom, from(#fff), color-stop(50%, #fff), color-stop(50%, transparent));
    background-image: linear-gradient(180deg, #fff, #fff 50%, transparent 50%);
    height: 0.7rem;
    font-size: 0.26rem;
  }
  a.mint-cell{
    border-bottom: 1px solid #D9D9D9;
  }
  a.mint-cell:last-child{
    border-bottom: none;
    background-image:-webkit-linear-gradient(bottom, #fff, #fff 50%, transparent 50%);
    background-image:linear-gradient(0deg, #fff, #fff 50%, transparent 50%);
  }
  .mint-loadmore-top{
    margin-top: 0.2rem;
    display: flex;
    justify-content: center;
    align-items: center;
    display: -webkit-flex;
    -webkit-align-items: center;
    -webkit-justify-content: center;
  }
  .meetingList .mint-cell-wrapper{
    height: inherit;
    padding: 0;
    margin: 0;
  }
 .meetingList .mint-cell-value{
    width: 100%;
  }
 .meetingList .mint-cell-swipe-button{
   font-size: 0.28rem;
   background: #e60012;
   color: #fff;
   padding: 0 0.4rem;
   display: flex;
   justify-content: center;
   align-items: center;
   display: -webkit-flex;
   -webkit-align-items: center;
   -webkit-justify-content: center;

 }
  .meetingList .mint-cell-swipe-buttongroup{
    display: flex;
    justify-content: center;
    align-items: center;

  }
  /*.meetingList .mint-cell-right{
   right: -0.3rem;
 }*/
  .mint-msgbox{
    width: 60%;
    border-radius: 0.15rem;
  }
  .mint-msgbox-message{
    color: #000000;
    font-size: 0.3rem;
  }
  .mint-msgbox-btn{
    font-size: 0.25rem;
  }
  .mint-cell.mint-cell-swipe:hover{
    -webkit-tap-highlight-color:transparent;
    -webkit-user-select:none;
  }
  .mint-cell.mint-cell-swipe:active{
    background-color: transparent;
  }
  .mint-cell-swipe-buttongroup{
    height: 99%;
    border-bottom: 1px solid #d9d9d9;
  }
  .mint-actionsheet{
    background-color: transparent;
    bottom: 0.3rem;
  }
  .mint-actionsheet ul.mint-actionsheet-list{
    border-radius: 0.15rem;
    width: 90%;
    margin-left: 5%;
    margin-bottom: 0.15rem!important;
    bottom: 0.3rem;
  }
  .mint-actionsheet ul.mint-actionsheet-list li.mint-actionsheet-listitem{
    height: 0.7rem;
    line-height: 0.7rem;
    font-size: 0.3rem;
    color: #227ee1;
  }
  .mint-actionsheet ul.mint-actionsheet-list li.mint-actionsheet-listitem:first-child{
    border-top-right-radius: 0.15rem;
    border-top-left-radius: 0.15rem;
  }
  .mint-actionsheet ul.mint-actionsheet-list li.mint-actionsheet-listitem:last-child{
    border-bottom-right-radius: 0.15rem;
    border-bottom-left-radius: 0.15rem;
  }
  .mint-actionsheet .mint-actionsheet-button{
    width: 90%;
    border-radius: 0.15rem;
    margin-left: 5%;
    height: 0.7rem;
    line-height: 0.7rem;
    color: #227ee1;
    font-size: 0.3rem;
  }
  .mint-actionsheet .mint-actionsheet-button{
    font-weight: 600;
  }
  .mint-popup.mint-datetime.mint-popup-bottom{
    width: 100%;
    bottom: 0;
    border-radius: 0;
  }
  .bandwidthList{
    font-size:0.25rem;
  .line{
    width: 100%;
    height: 0.77rem;
    line-height: 0.77rem;
    color: #227ee1;
    text-align: center;
    border-bottom: 1px solid #f2f5f7;
  }
  .line:hover{
    cursor: pointer;
    -webkit-tap-highlight-color:transparent;
    -webkit-user-select:none;
  }
  .line:active{
    background-color: #f2f5f7;
  }
  .line:last-of-type(1){
    border-bottom: none;
  }
  .title{
    color: #b9b9b9;
    height: 0.55rem;
    line-height: 0.55rem;
    text-align: center;
    border-bottom: 1px solid #f2f5f7;
  }
  .cancel{
    margin-top: 0.2rem;
  }
  }
  .meetingRoom .mint-cell-title,.org .mint-cell-title,.contacts .mint-cell-title,.conditions .mint-cell-title{
    width: 100%;
  }
  .mint-header-title{
    height: 0.88rem;
    line-height: 0.88rem   ;
  }
</style>
