<template>
  <div class="org">
    <mt-header fixed title= "组织架构">
      <span slot="left">
        <mt-button @click.native= "$router.go(-1)"><i class="iconfont fanhui"></i></mt-button>
      </span>
    </mt-header>
    <div class="search themeBgColor flex_auto">
      <div class="flex_start">
        <i class="iconfont sousuo"></i>
        <input type="text" maxlength="20" v-model="$store.state.org.searchUser" v-on:input="search" placeholder="请输入姓名">
      </div>
    </div>
    <div class="main">
      <div class="level line">
        <template v-for="(item,index) in $store.state.org.levelName">
          <span @click="levelAddMoreUser(item,index)">{{item.name}}</span>
          <span class="jt" v-if="$store.state.org.levelName.length > index+1">></span>
        </template>
      </div>
      <div class="line controll flex_between">
        <div class="" @click="selectAll">
          <i class="iconfont circle2yuanquan" v-if="!$store.state.org.select"></i>
          <i class="iconfont xuanze" v-if="$store.state.org.select && $store.state.org.userList.length >= 1"></i>
          全选
        </div>
        <div class="reture" @click= "returnLevel">返回上层</div>
      </div>
      <div class="fenzu themeBgColor">分组</div>
      <template v-for="item in $store.state.org.leveList">
        <div class="fenzuName line" @click="addMoreUser(item)">{{item.name}}</div>
      </template>
      <div class="user themeBgColor">人员</div>
      <div class="line container">
        <mt-checklist class="page-part"
                      v-model="$store.state.org.value1"
                      :options="$store.state.org.userList"
                      @change="changeValue">
        </mt-checklist>
      </div>
    </div>
    <footer>
      <div>已选择<span>{{$store.state.org.value1.length}}</span>人</div>
      <div @click="sure">确定</div>
    </footer>
  </div>
</template>

<script type="text/ecmascript-6">
  export default {
    name: 'Org',
    data() {
      return{
        participantArr: []
      }
    },
    /*    watch:{
     value1: function (newVal,oldVal) {
     console.log(newVal)
     console.log(oldVal)

     }
     },*/
    methods: {
      returnLevel(){
        let that = this;
        console.log(that.$store.state.org.levelName);
        if(that.$store.state.org.levelName.length === 0){
            that.$toast('无上级部门');
            return
        }
        if(that.$store.state.org.levelName.length === 1){
          that.$toast('无上级部门');
          return
        }
        let index = that.$store.state.org.levelName.length-2;
        let item = that.$store.state.org.levelName[index];
        that.levelAddMoreUser(item,index);
      },
      changeValue(value){
        let that = this;
        console.log(value);
        let valueStr = value.join(',');
        let count = 0;
        for(let i in that.$store.state.org.userList){
          if(!valueStr.includes(that.$store.state.org.userList[i].value)){
            that.$store.state.org.select = false;
          }else {
            count++;
            //that.$store.state.org.select = true;
          }
        }
        if(that.$store.state.org.userList.length === count){
          that.$store.state.org.select = true;
        }
      },
      selectAll(){
        let that = this;
        if(that.$store.state.org.userList.length <= 0){
          return
        }
        that.$store.state.org.select = !that.$store.state.org.select;
        if(!that.$store.state.org.select){
          for(let i in that.$store.state.org.userList){
            that.$store.state.org.value1.findIndex(function (value,index,arr) {
              if(value === that.$store.state.org.userList[i].value){
                // that.value1.splice(index,1);
                that.$store.state.org.value1.splice(index,1);
              }
            })
          }
        }else {
          let valueStr = that.$store.state.org.value1.join(',');
          for(let i in that.$store.state.org.userList){
            if(!valueStr.includes(that.$store.state.org.userList[i].value)){
              that.$store.state.org.value1.push(that.$store.state.org.userList[i].value);
            }
            // that.$store.state.org.value1.push(that.$store.state.org.userList[i].value);
          }
        }
      },
      sure(){
        let that = this;
        //  console.log(that.$store.state.org.value1[0].split(','));
        // console.log(this.$store.state.org.value1);
        that.$router.go(-1);
      },
      //姓名搜索
      search(){
        let that = this;
        let arr = [];
        if(!that.$store.state.org.searchUser){
          that.$store.state.org.userList = [];
            that.orgInit();
          //that.$store.state.org.userList = that.$store.state.org.list;
          //  console.log(that.$store.state.org.userList)
        }else {
            that.$store.state.org.leveList = [];
            that.$store.state.org.levelName = [];
          that.axios.post(that.$store.state.api.userList + '?sessionId='+
            that.$store.state.loginData.data.sessionId,{
            searchText: that.$store.state.org.searchUser
          }).then(function (res) {
            for(let i in res.data.data.data){
              if(that.$store.state.nowMeetingInformation.type !== 2){
                arr.push({
                  label: res.data.data.data[i].name,
                  value: res.data.data.data[i].id + ','+res.data.data.data[i].logoUrl + ',' + res.data.data.data[i].name+ ',' +
                  res.data.data.data[i].phone,
                  namePinying: res.data.data.data[i].namePinying
                })
              }else {
                if(res.data.data[i].phone != null){
                  arr.push({
                    label: res.data.data.data[i].name,
                    value: res.data.data.data[i].id + ','+res.data.data.data[i].logoUrl + ',' + res.data.data.data[i].name+ ',' +
                    res.data.data.data[i].phone,
                    namePinying: res.data.data.data[i].namePinying
                  })
                }
              }
            }
            that.$store.state.org.userList = arr;
            that.changeValue(that.$store.state.org.value1);
          }).catch(function (err) {

          })
        }

        //   console.log(that.$store.state.org.searchUser);
      },
      queryOrgById(orgId){
        let that = this;
        that.$indicator.open();
        that.$store.state.org.leveList = [];
        that.$store.state.org.list = that.$store.state.org.userList = [];
        that.axios.post(that.$store.state.api.queryOrgById+'?sessionId='+
          that.$store.state.loginData.data.sessionId,{
          orgId:orgId
        }).then(function (res) {
          console.log(res.data.data);
          for(let i in res.data.data){
            if(res.data.data[i].pid){
                console.log('11')
              that.$store.state.org.leveList.push(res.data.data[i]);
            }else {
                let commonFun = () =>{
                that.$store.state.org.userList.push({
                  label: res.data.data[i].name,
                  value: res.data.data[i].id + ','+res.data.data[i].logoUrl + ',' + res.data.data[i].name+ ',' +
                  res.data.data[i].phone,
                  namePinying: res.data.data[i].namePinying
                })
              }
              if(that.$route.query.type === 'phone'){
                  if(res.data.data[i].phone != null && !(that.participantArr.join(',').includes(res.data.data[i].phone))){
                    commonFun();
                  }

              }else {
                commonFun();
              }


            }
          }
          /* if(that.$store.state.nowMeetingInformation.type === 2){
           for(let i in that.$store.state.org.userList){
           console.log(that.$store.state.org.userList[i].phone)
           if(that.$store.state.org.userList[i].phone == null){
           that.$store.state.org.userList[i].disabled = true;
           }
           }
           }*/
          that.$store.state.org.list =  that.$store.state.org.userList;
       //   that.search();
          /*console.log(that.$store.state.org.searchUser);
           if(!that.$store.state.org.searchUser){
           that.$store.state.org.userList = that.$store.state.org.list;
           }else {

           }*/
          that.changeValue(that.$store.state.org.value1);
          that.$indicator.close();
        }).catch(function (err) {
          console.log(err);
        })
      },
      addMoreUser(item){
        //console.log(item);
        let that = this;
        that.$store.state.org.levelName.push(item);
        that.queryOrgById(item.id)
      },
      levelAddMoreUser(item,index){
        let that = this;
        if(index+1 === that.$store.state.org.levelName.length){
          return
        }
        that.$store.state.org.levelName.length = index+1;
        that.queryOrgById(item.id);
      },
      orgInit(){
          let that = this;
         /* that.$indicator.open();*/
          that.axios.post(that.$store.state.api.org+'?sessionId='+
            that.$store.state.loginData.data.sessionId,{
          }).then(function (res) {
            // console.log(res.data.data);
            if(that.$store.state.org.leveList.length === 0 && that.$store.state.org.levelName.length === 0){
              that.$store.state.org.leveList.push(res.data.data[0]);
            }

            that.$indicator.close();
          }).catch(function (err) {
            console.log(err);
          })
      }
    },
    mounted() {
      let that = this;
      that.participantArr = [];
      if(that.$store.state.meetingStatus === 'controll'){
        console.log(that.$store.state.controll.participant.participantList);
        for(let i in that.$store.state.controll.participant.participantList){
          that.participantArr.push(that.$store.state.controll.participant.participantList[i].phoneNum);
        }
      }else {
        that.$store.state.controll.participant.participantList = [];
      }

      window.addEventListener('native.keyboardhide', function (e){
        $('footer').css('display', 'block');
      })
      window.addEventListener('native.keyboardshow', function (e){
        $('footer').css('display', 'none');
      })
      that.orgInit();
      that.changeValue(that.$store.state.org.value1);
    },
  };
</script>
<style>
  .org .mint-checklist-label,.org .mint-cell-wrapper{
    padding: 0;
  }
</style>
<style lang="less" rel="stylesheet/less" scoped>
  .org{
    position: relative;
    height: 100%;
    .search{
      width: 100%;
      height: 0.8rem;
      margin-top:0.88rem;
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
        margin: 0px 0.05rem;
        font-size: 0.3rem;
      }
    }
    .line{
      height: 0.7rem;
      font-size:0.28rem;
      line-height:0.7rem;
      .iconfont{
        font-size: 0.3rem;
        margin-right: 0.1rem;
        color: #CCCCCC;
      }
      .xuanze{
        color: #26a2ff;
      }
    }
    .level{
      border-bottom: 1px solid #eeeeee;
      color: #227ee1;
      font-size: 0.25rem;
      padding-left: 0.3rem;
      padding-right: 0.3rem;
      width: 90%;
      overflow-x: scroll;
      /* z-index: 9999;
       position: fixed;
       background-color: #fff;*/
    }
    .controll{
      padding-left: 0.3rem;
      padding-right: 0.3rem;
      font-size: 0.25rem;
      color: #227ee1;
    }
    .main{
      height: 67%;
      overflow-y: scroll;
      /* position: absolute;
       top:1.38rem;
       bottom: 1.87rem;
       width: 100%;
       */
    }
    .container{
      margin-left: 0.3rem;
      margin-right: 0.3rem;
    }
    .fenzu,.user{
      padding: 0.1rem;
      font-size: 0.16rem;
    }
    .fenzuName{
      margin-left: 0.3rem;
      border-bottom: 1px solid #eeeeee;
    }
    .fenzuName:last-child{
      border-bottom: none;
    }
    footer{
      position: fixed;
      bottom: 0.45rem;
      width: 100%;
      div:first-child{
        width: 100%;
        border-bottom: 1px solid #d2d2d2;
        padding: 0.15rem 0;
        text-align: center;
        color: #7d7d7d;
        font-size: 0.22rem;
      }
      div:last-child{
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
