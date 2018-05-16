<template>
  <div class="layout">
    <mt-header fixed :title= "screenLayoutTitle + '分屏'">
      <span slot="left">
        <mt-button @click.native= "$router.go(-1)"><i class="iconfont fanhui"></i></mt-button>
      </span>
    </mt-header>
    <div style="height: 0.88rem;"></div>
    <template v-if="$store.state.controll.meetingNow.serverModel.includes('MCU')">
      <div class="MCUtitle">4：3分屏</div>
        <div class="flex_auto line" v-for="item in MCU.ONE" :class="{flex_start: item.length <= 5}">
          <div v-for="i in item" style="padding: 0.1rem;" :class="{border_red: tabIndex === i.layout}">
              <img :src="i.img"  @click="layout(i.layout)"/>
          </div>
        </div>
      <div class="MCUtitle">16：9分屏</div>
      <div class="flex_auto line" v-for="item in MCU.TWO" :class="{flex_start: item.length <= 5}">
        <div v-for="i in item" style="padding: 0.1rem;" :class="{border_red: tabIndex === i.layout}">
          <img :src="i.img"  @click="layout(i.layout)"/>
        </div>
      </div>
     <!--   <img v-for="group in item" :src="group.img" alt="">-->
    </template>
    <template v-if="$store.state.controll.meetingNow.serverModel.includes('ACANO')">
      <div class="flex_auto line" v-for="item in ACANO" :class="{flex_start: item.length <= 5}">
        <div v-for="i in item" style="padding: 0.1rem;" :class="{border_red: tabIndex === i.layout}">
          <img :src="i.img"  @click="layout(i.layout)"/>
        </div>
      </div>
      <!--   <img v-for="group in item" :src="group.img" alt="">-->
    </template>
    </div>
</template>

<script type="text/ecmascript-6">
  export default {
    name: 'layout',
    data() {
      return{
        screenLayoutTitle: this.$store.state.controll.meetingNow.serverModel.substring(
            this.$store.state.controll.meetingNow.serverModel.indexOf('_')+1,this.$store.state.controll.meetingNow.serverModel.lenght),
        MCU:{
            ONE: [],
            TWO: []
        },
        ACANO:[],
        tabIndex: '',
        api: '',
        parmsDate: {

        }

      }
    },
    methods: {
        layout(layout){
            let that = this;
            that.tabIndex = layout;
            that.parmsDate.meetingUuid = that.$store.state.controll.meetingNow.uuid;
            that.parmsDate.layout = layout;
            that.axios.post(that.api + '?sessionId='+
              that.$store.state.loginData.data.sessionId,that.parmsDate).
              then(function (res) {
              if(res.data.message !== 'OK'){
                that.$toast(res.data.message);
              }else {
                  that.$toast('分屏设置成功');
                  that.$router.go(-1)
              }
            }).catch(function (err) {
              console.log(err);
              that.$toast('分屏设置失败');
            })
        }
    },
    mounted() {
      let that = this;
      let chunk = (arr, num) =>{
        if (!arr.length) {
          return [];
        }
        return [arr.slice(0, num)].concat(chunk(arr.slice(num), num))
      }
      if(that.$store.state.controll.meetingNow.serverModel.includes('MCU')){
        for(let i = 1; i <= 49;i++){
            that.MCU.ONE.push({
                img: require('../../../../assets/img/4_3screen/'+ i + '.png'),
                layout: i
            });
        }
       that.MCU.ONE.push({
         img: require('../../../../assets/img/4_3screen/56.png'),
         layout: 56
       },{
         img: require('../../../../assets/img/4_3screen/58.png'),
         layout: 58
       },{
         img: require('../../../../assets/img/4_3screen/59.png'),
         layout: 59
       })
        for(let i = 50; i <= 55;i++){
          that.MCU.TWO.push({
              img: require('../../../../assets/img/16_9screen/'+ i + '.png'),
              layout: i
          })
        }
        that.MCU.TWO.push({
          img: require('../../../../assets/img/16_9screen/57.png'),
          layout: 57
        })
        that.MCU.ONE = chunk(that.MCU.ONE,6);
        that.MCU.TWO = chunk(that.MCU.TWO,6);
      }else if(that.$store.state.controll.meetingNow.serverModel.includes('ACANO')){
          for(let i = 1; i <= 9; i++){
              that.ACANO.push({
                  img: require('../../../../assets/img/acano_screen/'+ i*100+ '.png'),
                  layout: i*100
              })
          }
          that.ACANO.splice(1,0,{
              img: require('../../../../assets/img/acano_screen/110.png'),
              layout: 110
            },{
              img: require('../../../../assets/img/acano_screen/120.png'),
              layout: 120
            })
          that.ACANO = chunk(that.ACANO,6);
      }
      if(that.$route.query.type === undefined){
        that.tabIndex = Number(that.$store.state.controll.meetingNow.cpLayout);
        that.api = that.$store.state.api.meetingLayout;
      }else {
        //  that.tabIndex = that.$store.state.controll.meetingNow.participant.participantList[that.$route.query.type].cpLayout;
        that.tabIndex = that.$store.state.controll.participant.participantList[that.$route.query.type].layout;
        that.api = that.$store.state.api.participantpLayout;
        that.parmsDate.participantId = that.$store.state.controll.participant.participantList[that.$route.query.type].uuid;
      }
    }

  };
</script>

<style lang="less" rel="stylesheet/less" scoped>
  .border_red{
    border:1px solid red;
  }
  .layout{
    width: 100%;
    height: 100%;
    overflow-y: scroll;
    .MCUtitle{
      font-size: 0.3rem;
      text-indent: 0.3rem;
      margin: 0.1rem 0;
    }
    .line{
      width: 90%;
      margin: 0.15rem 5%;
    }
    .line.flex_start{
      margin-left: 0.2rem;
    }
    img{
      width: 0.8rem;
    }
  }
</style>
