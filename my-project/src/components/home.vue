<template>
    <div class="home">
      <div style="width: 100%;height: 92%;">
        <v-meet v-if="$store.state.tabBar === 'conference'"></v-meet>
        <v-order v-if="$store.state.tabBar === 'order'"></v-order>
        <v-my v-if="$store.state.tabBar === 'main'"></v-my>
      </div>
      <footer class="flex_auto indexFoot">
        <div @click="changeTab('conference')" :class="{font_color_blue:$store.state.tabBar === 'conference'}">
          <div class="iconfont xinxi"></div>
          <div>会议</div>
        </div>
        <div @click="changeTab('order')" :class="{font_color_blue:$store.state.tabBar === 'order'}">
          <div class="iconfont yuyuetixing"></div>
          <div>预约</div>
        </div>
        <div @click="changeTab('main')" :class="{font_color_blue:$store.state.tabBar === 'main'}">
          <div class="iconfont my"></div>
          <div>我的</div>
        </div>
      </footer>
    </div>
</template>

<script type="text/ecmascript-6">
  import vMeet from '../page/meetingList/index.vue'
  import vOrder from '../page/bespeak/index.vue'
  import vMy from '../page/my/index.vue'
    export default {
        components:{
          vMeet,
          vOrder,
          vMy
        },
        name: 'Home',
        data() {
            return{
              //selected: 'conference'
            }
        },
        methods: {
          changeTab(tab){
            this.$indicator.close();
            this.$store.state.tabBar = tab;
          }

        },
        mounted() {
            let that = this;
          window.addEventListener('native.keyboardhide', function (e){
            $('footer').css('display', 'block');
            $('.home .indexFoot').css({
              'position': 'fixed',
              'width' : '100%',
              'display': 'flex'
            });
            $('.home .indexFoot').addClass('flex_auto');
          })
          window.addEventListener('native.keyboardshow', function (e){
            $('footer').css('display', 'none');
          });
        }

    };
</script>
<style>
  fanhui{
    font-size: 0.32rem;
    margin-left: 0.3rem;
  }
</style>
<style lang="less" rel="stylesheet/less" scoped>

.home{
  position: relative;
  width: 100%;
  height: 100%;
  footer{
    position: fixed;
    bottom: 0;
    width: 100%;
    height: 0.98rem;
    background-color: #fcfcfc;
    box-shadow: inset 0px 1px 0px 0px #d2d2d2;
    color: #9ea7b3;
    font-size: 0.22rem;
    z-index: 4000 ;
    div{
      flex: 1;
      text-align: center;
      .iconfont{
        font-size: 0.35rem;
        margin: 0.05rem 0;
      }
      .xinxi{
        font-size: 0.4rem;
      }
    }
  }
  /*.page-tabbar {
    overflow: hidden;
    height: 100vh;
    .page-wrap {
      overflow: auto;
      height: 100%;
      padding-bottom: 100px;

    }
    .iconfont{
      font-size: 0.4rem;
    }
    .mint-tab-item{
      color: #9ea7b3!important;
    }
    .mint-tabbar > .mint-tab-item.is-selected{
      color: #227ee1!important;
    }
  .tabbarTitle{
    font-size: 0.22rem;
    margin-top: 0.1rem;
  }
  }

.mint-tabbar.is-fixed{
  height: 0.98rem;
}*/

}

</style>
