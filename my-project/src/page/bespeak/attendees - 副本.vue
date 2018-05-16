<template>
  <div class="attendees">
    <mt-header title= "参会人员">
      <span slot="left">
        <mt-button @click.native= "$router.go(-1)"><i class="iconfont fanhui"></i></mt-button>
      </span>
      <span slot="right">
        <mt-button @click.native= "allDelete">全删</mt-button>
      </span>
    </mt-header>
    <div class="controllUser">
      <div class="flex_auto addRemove">
        <dl  @click="sheetVisible = true">
        <dd class="flex_auto"><i class="iconfont jia"></i></dd>
        <dt>添加</dt>
      </dl>
        <dl @click="removeVis = !removeVis">
          <dd class="flex_auto"><i class="iconfont jian1"></i></dd>
          <dt>移除</dt>
        </dl>
      </div>
      <div class="main">
        <template>
          <div class="userList flex_start" v-for="group in $store.state.attendees.userList" >
            <div class="line flex_start" v-for="item in group">
              <i v-if="removeVis && item.name !== $store.state.loginData.data.name" @click="removeUser(item)" class="iconfont jian"></i>
              <img :src = item.logoUrl alt="">
              <div>{{item.name}}</div>
            </div>
        </div>
        </template>
      </div>
 <!--     <template v-for="group in $store.state.attendees.userList">
      <div class="userList flex_start">
          <div class="line flex_auto" v-for="item in group">
            <i v-if="removeVis" class="iconfont icon-jian"></i>
            <img :src = item.img alt="">
            <div>{{item.name}}
            </div>
          </div>
      </div>
      </template>-->
    </div>
    <footer>
      <div>已选择<span>{{$store.state.org.value1.length + 1}}</span>人</div>
      <div @click="sure">确定</div>
    </footer>
    <mt-actionsheet :actions="actions" v-model="sheetVisible"></mt-actionsheet>
  </div>
</template>

<script>
  export default {
    name: 'attendees',
    data() {
      return{
        removeVis:false,
        sheetVisible: false,
        img:'',
        actions : [{
          name: '请添加人员类型'
        },{
          name: '组织架构',
          method: this.queryOrgById
        }, {
          name: '我的联系人',
          method: this.userByConditions
        }],

      }
    },
    methods: {
      removeUser(item){
          let that = this;
        for(let i in this.$store.state.org.value1){
              if(this.$store.state.org.value1[i].includes(item.name)){
                this.$store.state.org.value1.splice(i,1)
              }
        }
        that.value1Arr(that.$store.state.org.value1);
         // console.log(this.$store.state.org.value1)

      },
      allDelete(){
          let that = this;
        this.img = 'http://' + localStorage.ip + ':8080'+ that.$store.state.loginData.data.logoUrl;
        that.$store.state.attendees.userList = [
            [{
            logoUrl: that.img,
            name: that.$store.state.loginData.data.name
            }]
        ];
        that.$store.state.org.select = false;
        that.$store.state.org.value1 = [];
      },
      userByConditions(){
        let that = this;
        that.$router.push({
          path: '/information/conditions'
        })
      },
      queryOrgById(){
        let that = this;
        that.$router.push({
            path: '/information/org'
        })
        return
        console.log(that.$store.state.loginData.data.orgId);

      },
      sure(){
          let that = this;
          that.$store.state.tabBar = 'conference';
          console.log(that.$store.state.org.value1);
          let attendeesArr = [];
          for(let i in that.$store.state.org.value1){
            attendeesArr.push(that.$store.state.org.value1[i].split(',')[0])

          }
    //    console.log(attendeesArr);
        that.$store.state.addMeetingParams.attendees = attendeesArr.join(',');
        console.log(that.$store.state.addMeetingParams);
          that.$router.push({
              path: '/home'
          })
      },
      value1Arr(arr){
          let that = this;
        that.$store.state.attendees.userList = [{
          logoUrl: that.img,
          name: that.$store.state.loginData.data.name
        }];
        for(let i in arr){
          that.$store.state.attendees.userList.push({
            id: arr[i].split(',')[0],
            name: arr[i].split(',')[2],
            logoUrl: 'http://' + localStorage.ip + ':8080'+ arr[i].split(',')[1]
          })
          //that.$store.state.attendees.userList[i] = (that.$store.state.org.value1[i].split(','));
        }
        let chunk = (arr, num) =>{
          if (!arr.length) {
            return [];
          }
          return [arr.slice(0, num)].concat(chunk(arr.slice(num), num))
        }
        that.$store.state.attendees.userList = chunk(that.$store.state.attendees.userList,4);
       // console.log(that.$store.state.attendees.userList);
      }
    },
    mounted() {
        let that = this;
        this.img = 'http://' + localStorage.ip + ':8080'+ that.$store.state.loginData.data.logoUrl;
     //  console.log(that.$store.state.org.value1);

       that.value1Arr(that.$store.state.org.value1);
    //    console.log(that.$store.state.attendees.userList);

     // console.log(chunk(that.$store.state.attendees.userList,4));
    }

  };
</script>
<style>
 .attendees .mint-header-button.is-right{
   /*text-align: center;*/
   padding-right: 0.3rem;
   font-size: 0.28rem;
 }
 .attendees .v-modal{
   opacity: 0.5;
   background: #000;
 }
 .attendees .mint-actionsheet{
   background-color: transparent;
   bottom: 0.3rem;
 }
 .attendees .mint-actionsheet ul.mint-actionsheet-list{
   border-radius: 0.15rem;
   width: 90%;
   margin-left: 5%;
   margin-bottom: 0.15rem!important;
   bottom: 0.3rem;
 }
 .attendees .mint-actionsheet ul.mint-actionsheet-list li.mint-actionsheet-listitem{
   height: 0.7rem;
   line-height: 0.7rem;
   font-size: 0.3rem;
   color: #227ee1;
 }
 .attendees .mint-actionsheet ul.mint-actionsheet-list li.mint-actionsheet-listitem:first-child{
   border-top-right-radius: 0.15rem;
   border-top-left-radius: 0.15rem;
   font-size: 0.2rem;
   color: #6d6d72;
   height: 0.5rem;
   line-height: 0.5rem;
 }
 .attendees .mint-actionsheet ul.mint-actionsheet-list li.mint-actionsheet-listitem:last-child{
   border-bottom-right-radius: 0.15rem;
   border-bottom-left-radius: 0.15rem;
 }
 .attendees .mint-actionsheet .mint-actionsheet-button{
   width: 90%;
   border-radius: 0.15rem;
   margin-left: 5%;
   height: 0.7rem;
   line-height: 0.7rem;
   color: #227ee1;
   font-size: 0.3rem;
 }
</style>
<style lang="less" type="text/less" scoped>
  .attendees{
    position: relative;
    .controllUser{
      margin-top: 1.4rem;
      .addRemove{
        width: 90%;
        margin-left: 0.3rem;
        margin-right: 0.3rem;
          dl{
            margin-right: 0.64rem;
          dd{
            width: 0.92rem;
            height: 0.92rem;
            border: 2px dashed #000;
            border-radius: 50%;
          .iconfont{
            font-size: 0.37rem;
          }
          }
          dt{
            text-align: center;
            margin: 0.1rem 0 0.18rem 0;
            font-size: 0.28rem;
          }
          }
          dl:last-child{
            margin-right: 0px;
          }
      }
      .userList{
        margin-left: 0.3rem;
        margin-right: 0.3rem;
          width: 90%;
        margin-top: 0.28rem;
        .line{
          position: relative;
          flex-direction: column;
          width: 25%;
          text-align: center;
          font-size: 0.28rem;
          .jian{
            position: absolute;
            top:0;
            right: 0.2rem;
            font-size: 0.3rem;
            color: #d61f06;
          }
        div{
          width: 90%;
          overflow: hidden;
          text-overflow:ellipsis;
          white-space: nowrap;
          margin-top: 0.08rem;
        }
          img{
            width: 1rem;
            height: 1rem;
          }
        }
      }
      .main{
        height: 6.48rem;
        overflow-y: scroll;
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
      border-radius: 10px;
      color: #FFFFFF;
      font-size: 0.3rem;
      text-align: center;
      margin-top: 0.15rem;
    }
  }
  .userList:first-child{

  }
  }
</style>
