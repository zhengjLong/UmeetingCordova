<template>
  <div class="contacts">
    <mt-header fixed title= "通讯录">
      <span slot="left">
        <mt-button @click.native= "$router.go(-1)"><i class="iconfont fanhui"></i></mt-button>
      </span>
    </mt-header>
    <div style="height: 0.88rem"></div>
    <div class="main">
      <div class="search themeBgColor flex_auto">
        <div class="flex_start">
          <i class="iconfont sousuo"></i>
          <input type="text" v-model="searchVal" v-on:input="search" placeholder="请输入姓名">
        </div>
      </div>
      <mt-checklist
        v-model="value"
        :options= $store.state.contacts.contactsOptions>
      </mt-checklist>
      <footer>
        <div class="flex_between">
          <!--<div>已选<span>{{value.length}}</span>个会议室</div>-->
          <div @click="next" >确定</div>
        </div>
      </footer>
    </div>
  </div>
</template>
<script type="text/ecmascript-6">
  export default{
      name: 'contacts',
      data() {
          return {
            value: [],
            searchVal: '',
          }
      },
      methods : {
        next(){
          let that = this;
          for(let i in that.value){
           // that.$store.state.org.value1.push(',/fileDefault/fileHead/default.jpg,' + that.name + ','+ String(that.phone));
            that.$store.state.org.value1.push(
              ',/fileDefault/fileHead/default.jpg,' + that.value[i].split(',')[0] + ','+ that.value[i].split(',')[1]
            )
          }
          that.$router.go(-1);
        },
        search(){
            let that = this;
            console.log(that.searchVal);
            let arr = [];
            if(!that.searchVal){
                that.$store.state.contacts.contactsOptions = that.$store.state.contacts.searchList;
            }else {
              for(let i in that.$store.state.contacts.searchList){
                if(that.$store.state.contacts.searchList[i].label.includes(that.searchVal)){
                  arr.push(that.$store.state.contacts.searchList[i]);
                }
              }
              that.$store.state.contacts.contactsOptions = arr;
            }

        }
      },
      mounted(){
        let that = this;
      },
  }
</script>
<style lang="less" rel="stylesheet/less" scoped>
.contacts{
  width: 100%;
  height: 100%;
  .main{
    width: 100%;
    height: 90%;
    overflow-y: scroll;
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
          width: 90%;
          padding: 0.1rem 0.2rem;
          height: 0.55rem;
          line-height: 0.55rem;
          border-radius: 0.1rem;
          background-color: #227ee1;
          color: #fff;
          text-align: center;
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
}
</style>
