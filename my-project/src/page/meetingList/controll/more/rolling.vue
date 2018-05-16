<template>
  <div class="rolling">
    <mt-header fixed title= "轮询">
      <span slot="left">
        <mt-button @click.native= "$router.go(-1)"><i class="iconfont fanhui"></i></mt-button>
      </span>
      <span slot="right">
        <mt-button @click.native= "allSelect" disabled v-if="$store.state.controlling.rollingStart === '结束'">全选</mt-button>
        <mt-button @click.native= "allSelect" v-else>全选</mt-button>
      </span>
    </mt-header>
    <div style="height: 0.88rem;"></div>
    <div class="search flex_auto">
      <div class="flex_start">
        <i class="iconfont sousuo"></i>
         <input type="text" placeholder="请输入与会者名称" v-model="rollingSearchVal" v-on:input = "rolingSearch">
      </div>
    </div>
    <div class="main">
      <div class="page-loadmore-wrapper" ref="wrapper" :style="{ height: wrapperHeight + 'px' }">
        <mt-loadmore :top-method="loadTop" @translate-change="translateChange" @top-status-change="handleTopChange" ref="loadmore">
         <!-- <ul class="page-loadmore-list">
            <li v-for="item in list" class="page-loadmore-listitem">{{ item }}</li>
          </ul>-->
          <div class="themeBgColor" style="height: 0.3rem;"></div>
          <div class="line" @click="rollingtTypeVis">
            轮询模式：<span>{{$store.state.controlling.rollingType}}</span>
            <i class="iconfont arrow-right-copy"></i>
          </div>
          <div class="themeBgColor" style="height: 0.3rem;"></div>
          <div class="line" @click="intervalPopVis">
            时间间隔：<span>{{$store.state.controlling.interval}}</span>秒
            <i class="iconfont arrow-right-copy"></i>
          </div>
          <div class="themeBgColor" style="height: 0.3rem;"></div>
          <div class="line checkListLine">
            <mt-checklist
            align="right"
            class="page-part"
            v-model="$store.state.controlling.value"
            :options="$store.state.controlling.options">
          </mt-checklist>
          </div>

          <div slot="top" class="mint-loadmore-top">
            <span v-show="topStatus !== 'loading'" :class="{ 'is-rotate': topStatus === 'drop' }">↓</span>
            <span v-show="topStatus === 'loading'">
                <mt-spinner type="snake"></mt-spinner>
              </span>
          </div>
        </mt-loadmore>
      </div>
    </div>
    <footer @click="sure">
      <span>{{$store.state.controlling.rollingStart}}</span>轮询
    </footer>
    <mt-actionsheet :actions="actions" v-model="sheetVisible"></mt-actionsheet>
    <mt-popup
      v-model="intervalPop"
      position="bottom">
      <div class="bandwidthList">
        <div class="title">请选择时间间隔（秒）</div>
        <template v-for="item in intervalPopArr">
          <div class="line" @click="intervalNum(item)">{{item}}</div>
        </template>
        <div class="cancel line" @click="intervalPop = false">取消</div>
      </div>
    </mt-popup>
  </div>
</template>

<script type="text/ecmascript-6">
  export default {
    name: 'rolling',
    data() {
      return{
        searchList: '',
        rollingSearchVal: '',//搜索字段
        intervalPop: false,//时间间隔选择
        intervalPopArr: [5,8,10,15],
        topStatus: '',//下来刷新
        wrapperHeight: 0,
        translate: 0,
        moveTranslate: 0,
        sheetVisible: false,//轮询选择
        actions: [{
          name: '请选择轮询模式'
        },{
          name: '全局轮询',
          method: this.allRolling
        }, {
          name: '主席轮询',
          method: this.masterRolling
        }]
      }
    },
    methods: {
      rolingSearch(){//搜索
          let that = this;
          let arr = [];
          if(!that.rollingSearchVal){
              that.$store.state.controlling.options = that.searchList;
          }else {
            for(let i in that.searchList){
              if(that.searchList[i].quanpin.toLowerCase().includes(that.rollingSearchVal.toLowerCase()) ||
                that.searchList[i].label.includes(that.rollingSearchVal)){
                arr.push(that.searchList[i]);
              }
            }
            that.$store.state.controlling.options = arr;
          }

          console.log(arr);

      },
        //时间间隔
      intervalNum(item){
        this.$store.state.controlling.interval = item;
        this.intervalPop = false;
      },
      intervalPopVis(){
        let that = this;
        if(that.$store.state.controlling.rollingStart === '结束'){
          that.$toast('更换轮询设置，请先结束轮询');
          return
        }
        that.intervalPop = true;
      },
      //轮询
      rollingtTypeVis(){
        let that = this;
        if(that.$store.state.controlling.rollingStart === '结束'){
          that.$toast('更换轮询设置，请先结束轮询');
          return
        }
        that.sheetVisible = true
      },
      allRolling(){
          this.$store.state.controlling.rollingType = '全局轮询';

      },
      masterRolling(){
        this.$store.state.controlling.rollingType = '主席轮询';

      },
      //确定轮询
      sure(){
          let that = this;
          console.log(that.$store.state.controlling.interval);
          console.log(that.$store.state.controlling.rollingType);

          console.log(that.$store.state.controlling.value.join(','));
          console.log(that.$store.state.controll.meetingNow.serverModel);
          console.log(that.$store.state.controll.meetingNow.uuid);
          if(that.$store.state.controlling.value.length < 3 && that.$store.state.controlling.rollingStart === '开始'){
            that.$toast('轮询会议室数量不能小于3');
            return
          }
          if(that.$store.state.controlling.rollingType === '主席轮询' && that.$store.state.controlling.rollingStart === '开始'){
              let count = 0;
              console.log(that.$store.state.controll.participant.participantList);
              for(let i in that.$store.state.controll.participant.participantList){
                  if(that.$store.state.controll.participant.participantList[i].chair === 1){
                     count ++
                  }
              }
             if(count <= 0){
                  that.$toast('请先设置主席');
                  return
             }
          }
          that.$store.state.controlling.rollingStart === '开始' ? that.$store.state.controlling.rollingStart = '结束' :
            that.$store.state.controlling.rollingStart = '开始';
         // that.$indicator.open();

          let params = {
            meetingUuid: that.$store.state.controll.meetingNow.uuid,
            op: that.$store.state.controlling.rollingStart === '开始' ? 'off' : 'on',
            rollingParams:JSON.stringify({
              participants: that.$store.state.controlling.value,
              interval: that.$store.state.controlling.interval,
              conferenceId: that.$store.state.controll.meetingNow.uuid,
              mode: that.$store.state.controlling.rollingType === '全局轮询' ? 2 : 1
            })
          };
          console.log(params);

        that.axios.post(that.$store.state.api.meetingRolling+'?sessionId='+
          that.$store.state.loginData.data.sessionId, params).then(function (res) {
          console.log(res.data);
          if(res.data.message !== 'OK'){
              that.$toast(res.data.message);
          }else {
              let textToast;
              params.op === 'on' ? textToast = '开始' : textToast = '结束';
              that.$toast(textToast + '轮询成功');
             that.$router.go(-1);
          }
          that.$indicator.close();
          //
        }).catch(function (err) {
          console.log(err);
          that.$toast('轮询失败');
          that.$indicator.close();
        })

      },
      participantList(){
          let that = this;
          that.$indicator.open();
          that.$store.state.controlling.options = [];
          that.axios.post(that.$store.state.api.participantAll+'?sessionId='+
              that.$store.state.loginData.data.sessionId,{
              'meetingUuid' : that.$store.state.controll.meetingNow.uuid
          }).then(function (res) {
              if (res.data.message !== 'OK') {
                  that.$toast(res.data.message);
                  //alert(res.data.message)
                  that.$indicator.close();
              } else {
                  that.$indicator.close();
                  for(let item in res.data.data){
                      if(res.data.data[item].status === 'PAR_CONNECTED'){
                        that.$store.state.controlling.options.push({
                          label: res.data.data[item].displayName,
                          value: res.data.data[item].uuid,
                          disabled: that.$store.state.controlling.rollingStart === '结束' ? true : false,
                          quanpin: res.data.data[item].quanpin
                        })
                      }
                  }
                  that.searchList = that.$store.state.controlling.options;
                  console.log(that.$store.state.controlling.options)
              }
          }).catch(function (err) {
              console.log(err);
              that.$indicator.close();
              // that.$indicator.close();
          })
      },
      allSelect(){
        let that = this;
        let valueStr = this.$store.state.controlling.value.join(',');
        let count = 0;
        for(let i in that.$store.state.controlling.options){
          if(!valueStr.includes(that.$store.state.controlling.options[i].value)){
            that.$store.state.controlling.value.push(that.$store.state.controlling.options[i].value);
          }else{
            count++
          }
        }
        if(count === that.$store.state.controlling.options.length){
          that.$store.state.controlling.value = [];
        }
      },
      handleTopChange(status) {
        this.moveTranslate = 1;
        this.topStatus = status;
      },
      translateChange(translate) {
        const translateNum = +translate;
        this.translate = translateNum.toFixed(2);
        this.moveTranslate = (1 + translateNum / 70).toFixed(2);
      },
      loadTop() {
        let that = this;
        that.rollingSearchVal = '';
        that.participantList();
        that.axios.post(that.$store.state.api.meetingRollingStatus + '?sessionId='+
          that.$store.state.loginData.data.sessionId,{
          uuid: that.$store.state.controll.meetingNow.uuid,
          serverModel: that.$store.state.controll.meetingNow.serverModel
        }).then(function (res) {
          console.log(res.data);
          if(!res.data.data){
            that.$store.state.controlling.rollingStart = '开始';
          }else {
            that.$store.state.controlling.rollingStart = '结束';
          }
        }).catch(function (err) {
          that.$toast('查询轮询状态失败')
        });
        setTimeout(() => {
          that.$refs.loadmore.onTopLoaded();
        }, 1500);
      },

    },
    mounted() {
        let that = this;
         this.wrapperHeight = document.documentElement.clientHeight - this.$refs.wrapper.getBoundingClientRect().top;
     // this.$store.commit('participantAll',{uuid:that.$store.state.controll.meetingNow.uuid,axios: that.axios,$toast: that.$toast,$indicator: that.$indicator});
        that.participantList();
    }

  };
</script>
<style>
  .rolling .mint-cell-title{
    width: 100%;
  }
  .rolling .mint-header-button.is-right{
    font-size: 0.3rem;
  }
 .rolling .mint-loadmore-top{
   margin-top: -0.8rem!important;
 }
  .rolling .mint-checkbox-label{
    margin-left: 0;
  }
 .rolling .mint-loadmore{
   overflow-y: scroll;
 }
.rolling .mint-actionsheet ul.mint-actionsheet-list li.mint-actionsheet-listitem:first-child {
  font-size: 0.2rem;
  color: #6d6d72;
  height: 0.5rem;
  line-height: 0.5rem;
}
</style>
<style lang="less" rel="stylesheet/less" scoped>
  .rolling{
    width: 100%;
    height: 100%;
    .search{
      width: 100%;
      height: 0.8rem;
      top:0.88rem;
      background-color: #cccccc57;
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
    .main{
      height: 95%;
      overflow-y: scroll;
      .line{
        height: 0.77rem;
        line-height: 0.77rem;
        text-indent: 0.3rem;
        font-size: 0.26rem;
        position: relative;
        .arrow-right-copy{
          position: absolute;
          right: 0.3rem;
          color: #b9b9b9;
          font-size: 0.3rem;
        }
      }
      .checkListLine{
        text-indent: 0rem;
      }
    }
    footer{
      position: fixed;
      bottom: 0rem;
      width: 100%;
      text-align: center;
      height: 0.75rem;
      line-height: 0.75rem;
      background-color: #227ee1;
      color: #FFFFFF;
      font-size: 0.3rem;
    }
  }
</style>
