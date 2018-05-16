<template>
  <div class="conditions">
    <mt-header fixed title="联系人">
       <span slot="left">
        <mt-button @click.native= "$router.go(-1)"><i class="iconfont fanhui"></i></mt-button>
      </span>
    </mt-header>
    <div class="container" :class="{h90 : $store.state.tabBar === 'main'}">
      <!--<template v-for="item in $store.state.conditions.levelList">
        <div class="flex_start line" @click="showUser(item)">
            <i class="iconfont jiantou" v-if="$store.state.conditions.tabIndex === item.id"></i>
            <i class="iconfont right" v-else></i>
          <div>{{item.name}}</div>
        </div>
      </template>-->

<template v-for="item in $store.state.conditions.levelList">
  <div>
    <div class="flex_start line" @click="showUser(item)">
      <i class="iconfont jiantou" v-if="$store.state.conditions.tabIndex === item.id"></i>
      <i class="iconfont right" v-else></i>
      {{item.name}}
    </div>
    <mt-checklist
      v-if="$store.state.conditions.tabIndex === item.id && $store.state.tabBar !== 'main'"
      class="page-part"
      v-model="$store.state.org.value1"
      :options="$store.state.conditions.userList">
    </mt-checklist>
    <template v-for="i in $store.state.conditions.userList"
      v-if="$store.state.conditions.tabIndex === item.id && $store.state.tabBar === 'main'">
      <div class="mainConditions flex_start">
        <div class="img"><img :src="url+i.logoUrl" alt=""></div>
        <div class="">
          <div>{{i.label}}</div>
          <div v-if="i.email" class="userEmail">{{i.email}}</div>
        </div>
      </div>
    </template>
  </div>
</template>

    </div>
    <footer v-if="$store.state.tabBar !== 'main'">
      <div>已选择<span>{{$store.state.org.value1.length + 1}}</span>人</div>
      <div @click="sure">确定</div>
    </footer>
  </div>
</template>

<script type="text/ecmascript-6">
  export default {
    name: 'conditions',
    data() {
      return{
        value: [],
        url: 'http://' + localStorage.ip + ':8080'
      }
    },
    methods: {
      showUser(item){
          let that = this;
           that.$store.state.conditions.userList = [];
          if(item.id === that.$store.state.conditions.tabIndex){
            that.$store.state.conditions.tabIndex = '';
            return
          }
            that.$store.state.conditions.tabIndex = item.id;
          console.log(item.id);
          let list = [];
          for(let i in that.$store.state.conditions.list){
              if(that.$store.state.conditions.list[i].pid === item.id){
                  list = that.$store.state.conditions.list[i].data;
              }
          }
          for(let item in list){
            that.$store.state.conditions.userList.push({
                label: list[item].user.name,
                email: list[item].user.email,
                logoUrl: list[item].user.logoUrl,
                value: list[item].user.id + ','+ list[item].user.logoUrl + ',' +
                        list[item].user.name + ',' + list[item].user.phone,
                namePinying: list[item].user.namePinying
            })
          }
          console.log(that.$store.state.conditions.userList);
      },
      sure(){
        console.log(this.$store.state.org.value1);
        this.$router.go(-1)
      }
    },
    mounted() {
        let that = this;
      console.log(that.$store.state.org.value1);
      if(that.$store.state.conditions.levelList.length === 0) {
        that.$indicator.open();
        that.axios.post(that.$store.state.api.userByConditions + '?sessionId=' +
          that.$store.state.loginData.data.sessionId, {}).then(function (res) {
        //  console.log(res.data.data.data);
          let arr = res.data.data.data;
          let map = {}, dest = [];
          for (var i = 0; i < arr.length; i++) {
              if(arr[i].pid === 0){
                that.$store.state.conditions.levelList.push(arr[i])
              }else {
                let ai = arr[i];
                if (!map[ai.pid]) {
                  dest.push({
                    pid: ai.pid,
                    data: [ai]
                  });
                  map[ai.pid] = ai;
                } else {
                  for (let j = 0; j < dest.length; j++) {
                    let dj = dest[j];
                    if (dj.pid == ai.pid) {
                      dj.data.push(ai);
                      break;
                    }
                  }
                }
              }
          }
          that.$store.state.conditions.list = dest;
        //
         console.log(that.$store.state.conditions.list);
          that.$indicator.close();
        }).catch(function (err) {
          console.log(err);
        })
      }

    }

  };
</script>
<style>
  .conditions .mint-checklist-title{
    display: none;
  }
</style>
<style lang="less" rel="stylesheet/less" scoped>
  .conditions{
    .container{
      position: absolute;
      top: 0.88rem;
      height: 70%;
      overflow-y: scroll;
      width: 100%;
      font-size: 0.3rem;
        .line{
          height: 0.8rem;
          border-bottom: 1px solid #eeeeee;
          .iconfont{
            color: #dbdbdb;
            margin: 0 0.3rem;
           }
        }
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
  .mainConditions{
  padding: 0.1rem 0.3rem;
  border-bottom: 1px solid #d9d9d9;
    .img{
      width: 0.94rem;
      height: 0.94rem;
      margin-right: 0.2rem;
      border-radius: 50%;
      img{
          width: 100%;
          height: 100%;
          border-radius: 50%;
      }
    }

  .userEmail{
    font-size: 0.2rem;
    color: rgb(185, 185, 185);
    margin-top: 0.2rem;
  }
  }
  }
  .h90{
    height: 90%!important;
  }
</style>
