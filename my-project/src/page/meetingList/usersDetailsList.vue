<template>
  <div class="usersDetailsList themeBgColor">
    <mt-header fixed title= "参会人员">
      <span slot="left">
        <mt-button @click.native= "$router.go(-1)"><i class="iconfont fanhui"></i></mt-button>
      </span>
    </mt-header>
    <div class="search flex_auto">
      <div class="flex_start">
        <i class="iconfont sousuo"></i>
        <input type="text" maxlength="20" v-model="$store.state.usersDetailsList.searchVal" v-on:input="search" placeholder="请输入姓名">
      </div>
    </div>
    <template v-for="item in $store.state.usersDetailsList.list">
      <div class="list flex_auto">
        <div class="img flex_auto"><img :src = "url + item.logoUrl" alt=""></div>
        <div class="information">
          <div>{{item.name}}</div>
          <div style="font-size: 0.2rem;color: #b9b9b9;">{{item.email}}</div>
        </div>
        <div class="icon">
          <span class="sign" v-if="item.signStatus === 1">已签到
          <!--  <i class="iconfont duihao"></i>-->
          </span>
          <span class="nosign" v-if="item.signStatus === 0">未签到
          </span>
        </div>
      </div>
    </template>
  </div>
</template>

<script type="text/ecmascript-6">
  export default {
    name: 'usersDetailsList',
    data() {
      return{
          url: 'http://' + localStorage.ip+ ':8080'
      }
    },

    methods: {
      search(){
        let that = this;
        let list = [];
        console.log(that.$store.state.usersDetailsList.searchVal);
        if(!that.$store.state.usersDetailsList.searchVal){
          that.$store.state.usersDetailsList.list = that.$store.state.nowMeetingInformation.users;
        }else {
          for(let i in that.$store.state.nowMeetingInformation.users){
            if(that.$store.state.nowMeetingInformation.users[i].name.includes(that.$store.state.usersDetailsList.searchVal)||
              that.$store.state.nowMeetingInformation.users[i].namePinying.includes(that.$store.state.usersDetailsList.searchVal.toLowerCase())
            ){
              list.push(that.$store.state.nowMeetingInformation.users[i])
            }
          }
          that.$store.state.usersDetailsList.list = list;
        }

      }
    },
    mounted() {
      let that = this;
      that.search();
    }

  };
</script>

<style lang="less" rel="stylesheet/less" scoped>
  .usersDetailsList{
    overflow-y: scroll;
    width: 100%;
    height: 100%;
    font-size: 0.3rem;
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
  .list{
    height: 1.3rem;
    line-height: 1.3rem;
    border-bottom: 1px solid #d9d9d9;
    background-color: #ffffff;
  .img{
    width: 20%;
    border-radius: 50%;
    height: 0.94rem;
  img{
    width: 0.94rem;
    border-radius: 50%;
    height: 0.94rem;
  }
  }
  .information{
    width: 60%;
  div{
    height: 0.5rem;
    line-height: 0.5rem;
  }
  }
  .icon{
    width: 20%;
    text-align: center;
    font-size: 0.25rem;
    .sign{
      color: #8fc31f;
      border: solid 1px #8fc31f;
    }
    .nosign{
      color: #b9b9b9;
      border: solid 1px #b9b9b9;
    }
    span{
      padding: 0.15rem 0.1rem;
      border-radius: 0.05rem;
    }
  }
  }
  }
</style>
