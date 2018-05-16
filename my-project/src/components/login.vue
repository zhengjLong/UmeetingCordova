<template>
  <div class="login">
    <mt-header fixed title="登录"></mt-header>
   <div class="titleImg flex_auto"><img src="../assets/img/Umeet.png" alt=""></div>
    <div class="form">
      <div class="line flex_auto">
        <label>URL</label>
        <input @focus="focusdelete('url')"
               v-on:keypress="myFunction($event,'url')" type="url"
               v-model="$store.state.login.ip" maxlength="20" placeholder="请输入服务器地址">
         <i class="iconfont shanchu"
            v-if="tableIndex === 'url'"
            @click="$store.state.login.ip = ''"></i>
      </div>
      <div class="line flex_auto">
        <label>账号</label>
        <input class="user" @focus="focusdelete('user')"
               v-on:keypress="myFunction($event,'user')" maxlength="15"
               v-model="$store.state.login.user" placeholder="请输入账号">
        <i class="iconfont shanchu"
           v-if="tableIndex === 'user'"
           @click="$store.state.login.user = ''"></i>
      </div>
      <div class="line flex_auto">
        <label>密码</label>
        <input v-on:keypress="myFunction($event,'pass')"
               type="password" class="pass"
               @focus="focusScoll('pass')" maxlength="15"
               v-model="$store.state.login.pass" placeholder="请输入密码">
        <i class="iconfont shanchu"
           v-if="tableIndex === 'pass'"
           @click="$store.state.login.pass = ''"></i>
        <i @click="passwordShow" class="iconfont htmal5icon09"></i>
      </div>
    </div>
    <mt-button type="primary" @click.native="login">登录</mt-button>
  </div>

</template>

<script type="text/ecmascript-6">
  export default {
    name: 'Login',
    data() {
      return{
        ipRes: false,
        tableIndex: ''
      }
    },
    methods: {
      myFunction(event,key){
        let x = event.which || event.keyCode;
        if(x == 13){
            switch (key){
              case 'pass':
                this.login();
                break;
              case 'url':
                  $('.user').focus();
                  break;
              case 'user':
                  $('.pass').focus();
                  this.focusScoll();
                  break;
            }
        }
      },
      focusdelete(key){
          if(this.$store.state)
          this.tableIndex = key;
      },
      focusScoll(){
        this.tableIndex = 'pass';
        $('.login').css({'overflow':'scroll'});
        $('.login').animate({
          scrollTop: 200
        });
      },
      login(){
        let that = this;
        let reg =/^(([A-Za-z0-9-~]+)\.)+([A-Za-z0-9-~\/])+(\:[0-9]+)?$/;
        if(!that.$store.state.login.ip){
          that.$toast('请输入服务器地址');
            return
        }
        if(!reg.test(that.$store.state.login.ip)){
          that.$toast('IP地址输入有误');
          return
        }
        if(!that.$store.state.login.user){
          that.$toast('请输入账号');
          return
        }
        if(!that.$store.state.login.pass){
          that.$toast('请输入密码');
          return
        }
        that.$indicator.open();
        that.axios.defaults.baseURL ='http://' + that.$store.state.login.ip;
         let user = this.base64(that.$store.state.login.user);
         let pass = this.base64(that.$store.state.login.pass);
        that.axios.post(that.$store.state.api.login,{
         username: user,
         password: pass
         }).then(function(res){
         console.log(res);
            if(!res.data.data){
                that.$toast(res.data.message);
              that.$indicator.close();
                return
            }else{
                console.log(res.data.data.hostId);
                that.$store.state.loginData = res.data;
                that.$store.state.tabBar = 'conference';
                localStorage.loginSucess = true;
              localStorage.ip = that.$store.state.login.ip;
              localStorage.user = that.$store.state.login.user;
              localStorage.pass = that.$store.state.login.pass;
              if(res.data.data.hostId){
                that.axios.defaults.baseURL ='http://admin.systeccloud.com:8080';
                that.axios.get(that.$store.state.api.umeetingUser + res.data.data.hostId).then(function (res) {
                  console.log(res.data.data.user.email);
                  !res.data.data.user ?  that.$toast('获取网络会议账号失败') : that.$store.state.umeetingAccount = res.data.data.user.email;
                }).catch(function (err) {
                  console.log(err)
                  that.$indicator.close();
                })
              }else {
                that.$store.state.umeetingAccount = '';
              }
              that.axios.defaults.baseURL ='http://' + that.$store.state.login.ip;
              that.$router.push({
                path: '/home'
              })
              that.$indicator.close();
            }
         }).catch(function(err){
         console.log(err);
            that.$toast('登录失败');
          that.$indicator.close();
          return
         });
      },
      passwordShow(){
        console.log($('.pass')[0].type)

        if($('.pass')[0].type === 'password'){
          $('.pass')[0].type = 'text';
          $('.htmal5icon09').css('color','#227ee1')
        }else {
          $('.pass')[0].type = 'password';
          $('.htmal5icon09').css('color','#b9b9b9')
        }
      },
      utf8_encode(string) {
        string = string.replace(/\r\n/g,"\n");
        var utftext = "";
        for (var n = 0; n < string.length; n++) {
          var c = string.charCodeAt(n);
          if (c < 128) {
            utftext += String.fromCharCode(c);
          } else if((c > 127) && (c < 2048)) {
            utftext += String.fromCharCode((c >> 6) | 192);
            utftext += String.fromCharCode((c & 63) | 128);
          } else {
            utftext += String.fromCharCode((c >> 12) | 224);
            utftext += String.fromCharCode(((c >> 6) & 63) | 128);
            utftext += String.fromCharCode((c & 63) | 128);
          }
        }
        return utftext;
      },
      base64(input) {
          let keyStr = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=";
        var output = "";
        var chr1, chr2, chr3, enc1, enc2, enc3, enc4;
        var i = 0;
        input = this.utf8_encode(input);
        while (i < input.length) {
          chr1 = input.charCodeAt(i++);
          chr2 = input.charCodeAt(i++);
          chr3 = input.charCodeAt(i++);
          enc1 = chr1 >> 2;
          enc2 = ((chr1 & 3) << 4) | (chr2 >> 4);
          enc3 = ((chr2 & 15) << 2) | (chr3 >> 6);
          enc4 = chr3 & 63;
          if (isNaN(chr2)) {
            enc3 = enc4 = 64;
          } else if (isNaN(chr3)) {
            enc4 = 64;
          }
          output = output +
            keyStr.charAt(enc1) + keyStr.charAt(enc2) +
            keyStr.charAt(enc3) + keyStr.charAt(enc4);
        }
        return output;
      }
    },
    mounted() {
      let that = this;
      that.axios.defaults.timeout = 30000;
      that.axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded;charset=UTF-8';
      if(!localStorage.ip || !localStorage.user || !localStorage.pass){
          return
      }
      if(localStorage.ip){
        that.$store.state.login.ip = localStorage.ip;
      }
      if(localStorage.user){
        that.$store.state.login.user = localStorage.user;
      }
      if(localStorage.pass){
        that.$store.state.login.pass = localStorage.pass;
      }
      if(localStorage.loginSucess === 'true'){
          that.login();
      }
   /*   window.addEventListener('native.keyboardhide', function (e){
        $('.login').css('overflow', 'hidden');
      });
      window.addEventListener('native.keyboardshow', function (e){
        $('.login').css('overflow', 'scroll');
      });*/
    }

  };
</script>
<style>
</style>
<style lang="less" rel="stylesheet/less" scoped>
.login{
  width: 100%;
  height: 100%;
  background-color: #f2f5f7;
  overflow-y: scroll;
  .mint-button{
    border-radius: 0px;
  }
  .mint-button--normal{
    width: 90%;
    margin: auto 5%;
    height: 0.77rem;
    background-color: #227ee1;
    border-radius: 0.1rem;
    font-size: 0.28rem;
    margin-top: 0.88rem;
    padding: 0px;
  }
  .mint-header{
    height: 0.88rem;
    background-color: #227ee1;
    font-size: 0.35rem;
  }
  .titleImg{
    padding-top: 1.9rem;
    padding-bottom: 0.87rem;
    img{
      width: 1.88rem;
      height: 0.41rem;
    }
  }
.form{
  width: 100%;
  height: 3.38rem;
  background-color: #ffffff;
  box-shadow: 0px 3px 7px 0px
  #e6e6e6,
  inset 0px 2px 5px 0px
  rgba(2, 2, 2, 0.1);
  .line{
    width: 100%;
    border-bottom: 1px solid #d2d2d2;
    height: 3.38rem/3;
    position: relative;
    label{
      display: inline-block;
      width: 20%;
      color: #474747;
      text-align: center;
      font-size: 0.28rem;
    }
    input{
      display: inline-block;
      width: 80%;
      font-size: 0.28rem;
    }
    input::-webkit-input-placeholder{
      color: #b9b9b9;
    }
    .iconfont{
      position: absolute;
      right: 0.4rem;
      font-size: 0.34rem;
      color: #b9b9b9;
      z-index: 5;
    }
  }
  .line:nth-last-of-type(1){
    border-bottom: none;
    .shanchu{
      right: 1rem;
    }

  }
}
}
</style>
