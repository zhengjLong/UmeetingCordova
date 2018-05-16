<template>
  <div class="addMeetingRoom">
    <mt-header fixed title= "添加会议室">
      <span slot="left">
        <mt-button @click.native= "$router.go(-1)"><i class="iconfont fanhui"></i></mt-button>
      </span>
    </mt-header>
    <div class="main themeBgColor">
      <div style="height: 0.88rem;"></div>
      <div class="search themeBgColor flex_auto">
        <div class="flex_start">
          <i class="iconfont sousuo"></i>
          <input type="text" maxlength="20" v-model="$store.state.controll.addMeetingRoom.searchVal" v-on:input="search" placeholder="请输入会议室名称">
        </div>
      </div>
      <div class="container bg_fff">
        <mt-index-list>
          <template v-for="(item,index1) in alphabet">
            <mt-index-section :index="item.initial">
              <template v-for="(cell,index2) in item.cells">
                <mt-cell>
                  <div class="line" @click="cellCheck(item,index1,index2)">
                    <i class="iconfont circle2yuanquan" v-if="!$store.state.controll.addMeetingRoom.value1.join(',').includes(cell.uuid)"></i>
                    <i class="iconfont xuanze" v-else></i>
                    {{cell.name}}
                  </div>
                </mt-cell>
              </template>
            </mt-index-section>
          </template>
        </mt-index-list>
      </div>
      <footer>
        <div class="flex_between">
          <div v-if="$route.query.type !== 'local'">已选<span>{{$store.state.controll.addMeetingRoom.value1.length}}</span>个会议室</div>
          <div :class="{large: $route.query.type === 'local'}" @click="sure">确定</div>
        </div>
      </footer>
    </div>
  </div>
</template>
<script type="text/ecmascript-6">

  export default {
    name: 'addMeetingRoom',
    data() {
      return{
        alphabet: [],
      }
    },
    methods: {
      sure(){
          let that = this;
          that.$indicator.open();
          that.axios.post(that.$store.state.api.participantAdd + '?sessionId=' +
            that.$store.state.loginData.data.sessionId,{
            roomUuids: that.$store.state.controll.addMeetingRoom.value1,
            meetingUuid: that.$store.state.controll.meetingNow.uuid
          }).then(function (res) {
            console.log(res.data)
            if(res.data.message !== 'OK'){
                that.$toast(res.data.message)
              that.$indicator.close();
            }else {
              that.$indicator.close();
               that.$toast('添加会议室成功');
                that.$router.go(-1);
                that.$store.state.controll.addMeetingRoom = {
                  searchVal: '',
                  list: [],
                  roomList: [],
                  value1: []
                }
            }

          }).catch(function (err) {
              console.log(err)
            that.$indicator.close();
          })

      },
      cellCheck(item,index1,index2){
          console.log(item.cells[index2]);
         let that = this;
        let hasCheck = $('.addMeetingRoom .main .container .mint-indexlist ul li').eq(index1).find('ul .line .iconfont').eq(index2);
        if(hasCheck.hasClass('circle2yuanquan')){
          hasCheck.removeClass('circle2yuanquan').addClass('xuanze');
          that.$store.state.controll.addMeetingRoom.value1.push(item.cells[index2].uuid);
        }else if(hasCheck.hasClass('xuanze')){
          hasCheck.removeClass('xuanze').addClass('circle2yuanquan');
          for(let i in that.$store.state.controll.addMeetingRoom.value1){
            console.log(that.$store.state.controll.addMeetingRoom.value1[i]);
            if(that.$store.state.controll.addMeetingRoom.value1[i] === item.cells[index2].uuid){
              that.$store.state.controll.addMeetingRoom.value1.splice(i,1);
            }else {

            }
          }
        }

        console.log(that.$store.state.controll.addMeetingRoom.value1)
      },
      search(){
        let that = this;
        let arr = [];
        that.alphabet = [];
        if(!that.$store.state.controll.addMeetingRoom.searchVal){
          console.log(that.$store.state.controll.addMeetingRoom.roomList)
          that.$store.state.controll.addMeetingRoom.roomList = that.$store.state.controll.addMeetingRoom.list;
        }else {
          for(let i in that.$store.state.controll.addMeetingRoom.list){
            if(that.$store.state.controll.addMeetingRoom.list[i].name.includes(that.$store.state.controll.addMeetingRoom.searchVal) ||
              that.$store.state.controll.addMeetingRoom.list[i].namePinying.toLowerCase().includes(that.$store.state.controll.addMeetingRoom.searchVal.toLowerCase())){
              arr.push(that.$store.state.controll.addMeetingRoom.list[i]);
              // console.log(that.RoomList[i]);
            }
          }
          that.$store.state.controll.addMeetingRoom.roomList = arr;
        }
        '0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ'.split('').forEach(initial => {
          let cells = that.$store.state.controll.addMeetingRoom.roomList.filter(name => name.namePinying[0].toLocaleUpperCase() === initial);
          if(cells.length >= 1){
            that.alphabet.push({
              initial,
              cells
            });
          }
          // console.log(cells);
        });
        console.log(that.alphabet);
      },
      meetingCanUseMR(){
        let that = this;
        that.$indicator.open();
        that.axios.post(that.$store.state.api.meetingCanUseMR + '?sessionId='+
          that.$store.state.loginData.data.sessionId,{
          uuid: that.$store.state.controll.meetingNow.uuid
        }).then(function (res) {
          // console.log(res.data.data);
          if(!res.data.data){
            that.$toast(res.data.message);
            that.$indicator.close();
          }else {
            that.$store.state.controll.addMeetingRoom.list = res.data.data;
            if(!that.$store.state.controll.addMeetingRoom.searchVal){
              that.$store.state.controll.addMeetingRoom.roomList =  that.$store.state.controll.addMeetingRoom.list;
            }else {
              that.search();
            }
            console.log(that.$store.state.controll.addMeetingRoom.roomList);
            '0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ'.split('').forEach(initial => {
              let cells = that.$store.state.controll.addMeetingRoom.roomList.filter(name => name.namePinying[0].toLocaleUpperCase() === initial);
              if(cells.length >= 1){
                that.alphabet.push({
                  initial,
                  cells
                });
              }
              // console.log(cells);
            });
            console.log(that.alphabet);
            that.$indicator.close();
          }
        }).catch(function (err) {
          console.log(err);
        })
      }
    },
    mounted() {
        this.$store.state.controll.addMeetingRoom.searchVal = '';
      this.meetingCanUseMR();
    }

  };
</script>
<style>
.mint-cell-title{
   flex: inherit;
  }
.addMeetingRoom .mint-cell-value{
  width: 100%;
}
.addMeetingRoom .mint-indexsection-index{
  font-size: 0.28rem;
}
</style>
<style lang="less" rel="stylesheet/less" scoped>
  .addMeetingRoom{
    width: 100%;
    height: 100%;
    position: relative;
    .main{
      height: 100%;
      .search{
        width: 100%;
        height: 0.8rem;
        top:0.88rem;
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
            width: 100%;
          }
          input::-webkit-input-placeholder{
            color: #b9b9b9;
          }
        }
        .sousuo{
          margin: 0 0.05rem;
          font-size: 0.3rem;
        }
      }
    .container{
        height: 80%;
        overflow-y: scroll;
      .line{
        width: 100%;
        height: 0.7rem;
        line-height: 0.7rem;
        font-size: 0.26rem;
        padding: 0 0.2rem;
        border-bottom: 1px solid #eeeeee;
        color: #333;
      .iconfont{
        font-size: 0.3rem;
        margin-right: 0.1rem;
        color: #CCCCCC;
      }
      .xuanze{
        color: #26a2ff;
      }
      }
      .line:last-child{
        border-bottom: none;
      }
    }
    footer{
      border-top: 1px solid #D9D9D9;
      background-color: #FFFFFF;
      position: fixed;
      bottom: 0;
      width: 100%;
    /* margin-left: 5%;*/
    .flex_between{
      margin-left: 0.3rem;
      margin-right: 0.3rem;
      padding: 0.3rem 0;
      font-size:0.3rem;
    div{
      padding: 0.1rem 0.2rem;
      border-radius: 0.08rem;
    }
    div:nth-of-type(1){
      border: 1px solid #227ee1;
      background-color: transparent;
      color: #26a2ff;

    }
    div:nth-of-type(2){
      background-color: #227ee1;
      color: #fff;
    }
    }
    .large{
      width: 90%;
      background-color: #227ee1!important;
      color: #FFFFFF!important;
      text-align: center!important;
    }
    }
    }
  .searchLine{
    .line{
      padding: 0 0.3rem!important;
    }
  }
  }
</style>
