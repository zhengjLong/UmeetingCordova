<template>
  <div class="dial">
    <mt-header fixed title= "临时邀请">
      <span slot="left">
        <mt-button @click.native= "$router.go(-1)"><i class="iconfont fanhui"></i></mt-button>
      </span>
      <span slot="right">
        <mt-button @click.native= "call">呼叫</mt-button>
      </span>
    </mt-header>
    <div style="height: 0.88rem;"></div>
    <div class="line">
      <div class="label"> 输入格式9012@域名或IP地址</div>
      <input type="text" v-model="dialIp" maxlength="40" class="linekey" placeholder="请输入呼叫地址">
    </div>

    <div class="line">
      <div class="label">请选择带宽</div>
      <div class="linekey text-center" @click="popupVisible = true">{{bandWidth}}</div>
    </div>
    <div class="line">
      <div class="label">最近临时邀请</div>
      <template v-for="(item,index) in historyArr">
        <div class="history">
          <span @click="dialIp = item.dialIp">{{item.dialIp}}</span>
        <div class="iconfont" @click="deleteHistory(index)">×</div>
        </div>
      </template>
    </div>
    <mt-popup
      v-model="popupVisible"
      position="bottom" style="height: 92%;">
      <div class="bandwidthList">
      <div class="title">请选择会议带宽</div>
        <div class="lineBandWidthList">
          <template v-for="item in bandwidthArr">
            <div class="line" @click="selecBandwidth(item)">{{item}}</div>
          </template>
        </div>
        <mt-button size="large"  @click="popupVisible = false" class="cancel" type="primary">取消</mt-button>
      </div>
    </mt-popup>

  </div>
</template>

<script type="text/ecmascript-6">
  export default {
    name: 'dial',
    data() {
      return{
        historyArr: [],
        callType: 'sip',
        dialIp: '',
        popupVisible: false,
        bandWidth: '自动',
        bandwidthArr: ['自动', '512kb', '768kb', '1.00M', '1.50M', '2.00M', '3.00M', '4.00M', '6.00M', '8.00M', '10.00M', '15.00M', '20.00M', '30.00M']
      }
    },
    methods: {
      deleteHistory(i){
          let that = this;
          that.historyArr.splice(i,1);
          localStorage.dialParams = JSON.stringify(that.historyArr);
      },
      call(){
        let that = this;
        let params = {};
        //带宽
        if(that.bandWidth.includes('kb')){
           params.bandWidth = Number(that.bandWidth.replace('kb',''))*1024;
        }else if(that.bandWidth.includes('M')){
           params.bandWidth = Number(that.bandWidth.replace('M',''))*1024*1024;
        }else if(that.bandWidth === '自动'){
           params.bandWidth = 0;
        }
        if(!that.dialIp || !(that.dialIp.replace(/\ +/g, ""))){
            that.$toast('地址不能为空');
            return
        }
        params.dialIp = that.dialIp;
        let reg = /^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])$/;
        if(reg.test(that.dialIp)){
            console.log(params)
          params.callType = 'h323'
        }else if(that.dialIp.includes('@')){
            params.callType = 'sip'
        }else {
            params.callType = 'sip'
        }
        params.meetingUuid = that.$store.state.controll.meetingNow.uuid;

        if(!localStorage.dialParams || JSON.parse(localStorage.dialParams).length === 0){
          let arr = [];
          arr.push(params);
          localStorage.dialParams = JSON.stringify(arr);
          that.historyArr = arr;
        }else {
            let arr = JSON.parse(localStorage.dialParams);
            if(!localStorage.dialParams.includes(params.dialIp)){
              arr.push(params);
              localStorage.dialParams = JSON.stringify(arr);
              that.historyArr = arr;
            }
        }
        that.axios.post(that.$store.state.api.participantDial + '?sessionId=' +
          that.$store.state.loginData.data.sessionId,
          params).then(function (res) {
          console.log(res)
          if(res.data.message !== 'OK'){
              that.$toast(res.data.message);
          }else {
              that.$toast('临时邀请成功');
              that.$router.go(-1)
          }

        }).catch(function (err) {
          console.log(err)
        })
      },
      selecBandwidth(item){
          this.bandWidth = item;
          this.popupVisible = false;
      }
    },
    mounted() {
      let that = this;
      if(!localStorage.dialParams){
          that.historyArr = []
      }else {
          that.historyArr = JSON.parse(localStorage.dialParams);
      }
    }

  };
</script>
<style>
  .dial .mint-header-button.is-right{
    font-size: 0.3rem;
  }
</style>
<style lang="less" rel="stylesheet/less" scoped>
  .dial{
     font-size: 0.26rem;
    .line{
        padding: 0.2rem;
    .linekey{
        font-size: 0.25rem;
        width: 100%;
        border: 1px solid #ddd;
        padding: 0.05rem 0;
        border-radius: 0.05rem;
        text-indent: 0.12rem;
      }
    input::-webkit-input-placeholder{
      color: #e6e6e6;
    }
      .label{
        color: #333333;
        font-size: 0.23rem;
        padding-bottom: 0.05rem;
      }

    }
  .popLine{
    height: 0.77rem;
    line-height: 0.77rem;
    color: #00abff;
    text-align: center;
    border-bottom: 1px solid #f2f5f7;
    font-size: 0.25rem;
  }
  .popLine:last-child{
    border-bottom: none;
  }
  .cancel{
    margin-top: 0.2rem;
  }
  .titleLine{
    height: 0.5rem;
    line-height: 0.5rem;
    color: #b9b9b9;
    font-size: 0.2rem;
    text-align: center;
    border-bottom: 1px solid #f2f5f7;
  }
.history{
  height: 0.55rem;
  line-height: 0.55rem;
  border-bottom: 1px solid #f2f5f7;
  position: relative;
span{
  width: 90%;
  display: inline-block;
}
  .iconfont{
    position: absolute;
    right: 0;
    color: #cccccc;
    font-size: 0.35rem;
    display: inline-block;
    width: 0.55rem;
    height: 0.55rem;
    text-align: center;
  }
}
    .bandwidthList{
      width: 100%;
      height: 100%;
      position: relative;
      .title{
        position: fixed;
        width: 100%;
        top: 0;
      }
      .line{
        padding: 0;
      }
      .cancel{
        margin-top: 0.2rem;
        margin-bottom: 0.2rem;
        width: 90%;
        margin-left: 5%;
      }
      .lineBandWidthList{
        height: 85%;
        overflow-y: scroll;
        margin-top: 0.55rem;

      }
    }
  }
</style>
