<template>
  <div class="temporary themeBgColor">
    <mt-header title= "临时邀请">
      <span slot="left">
        <mt-button @click.native= "$router.go(-1)"><i class="iconfont fanhui"></i></mt-button>
      </span>
    </mt-header>
    <div style="height: 1.18rem;" class="themeBgColor"></div>
    <div class="container">
      <div class="line">
        <span>姓名</span>
        <input type="text" maxlength="10" v-model="name" placeholder="请输入姓名">
      </div>
      <div style="height: 0.3rem;" class="themeBgColor"></div>
      <div class="line">
        <span>电话</span>
        <input type="number" maxlength="15" v-model="phone" placeholder="请输入电话(固定电话请加区号)">
      </div>
    </div>
    <footer>
      <div @click="sure">确定</div>
    </footer>
  </div>
</template>
<script type="text/ecmascript-6">
  export default{
      name: 'temporary',
      data (){
          return{
            phone: '',
            name: ''
          }
      },
    methods:{
      sure(){
        let that = this;
        let checkTel = (tel) => {
          let mobile = /^1[3|5|7|8]\d{9}$/ ;
          let phone = /^0\d{2,3}-?\d{7,8}$/;
          return mobile.test(tel) || phone.test(tel);
        };
        if(!that.name || !(that.name.replace(/\ +/g, ""))){
            that.$toast('请输入姓名');
            return
        }
        if(!that.phone || !(that.phone.replace(/\ +/g, ""))){
          that.$toast('请输入电话');
          return
        }
        if(!checkTel(that.phone)){
            that.$toast('请输入正确的电话');
            return
        }
        console.log(that.$store.state.org.value1);
        let addPhone = ',/fileDefault/fileHead/default.jpg,' + that.name + ','+ String(that.phone);
        console.log(that.$store.state.controll.participant.participantList);
        console.log(addPhone);
        let participantStr = ''
        for(let i in that.$store.state.controll.participant.participantList){
          participantStr += that.$store.state.controll.participant.participantList[i].phoneNum
        }
        if(participantStr.includes(that.phone)){
           that.$toast('该电话号码已在会议中');
           return
        }
        that.$store.state.org.value1.push(addPhone);
        that.$router.go(-1);
      }
    },
    mounthed(){
    }
  }
</script>
<style lang="less" rel="stylesheet/less" scoped>
  .temporary{
    width: 100%;
    height: 100%;
    position: relative;
    .container{
      width: 100%;
      .line{
        width: 100%;
        height: 0.77rem;
        line-height: 0.77rem;
        background-color: #FFFFFF;
        font-size: 0.28rem;
        span{
          width: 20%;
          display: inline-block;
          text-indent: 0.3rem;
        }
        input{
          width: 70%;
          font-size: 0.28rem;
        }
        input::-webkit-input-placeholder{
          color: #b9b9b9;
        }
      }
    }
    footer{
      position: fixed;
      bottom: 0.45rem;
      width: 100%;
      div{
        width: 90%;
        margin-left: 5%;
        height: 0.75rem;
        line-height: 0.75rem;
        background-color: #227ee1;
        border-radius: 0.1rem;
        color: #FFFFFF;
        font-size: 0.3rem;
        text-align: center;
        margin-top: 0.15rem;
      }
    }
  }
</style>
