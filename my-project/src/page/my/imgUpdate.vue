<template>
  <div class="mainMy themeBgColor">
    <mt-header fixed title= "个人信息">
       <span slot="left">
        <mt-button @click.native= "$router.go(-1)"><i class="iconfont fanhui"></i></mt-button>
      </span>
    </mt-header>
    <div class="themeBgColor" style="height: 0.88rem"></div>
    <div class="line title" @click="sheetVisible = true">

      <div class="img"><img :src="img" alt=""></div>
      <span>头像</span>
      <i class="iconfont arrow-right-copy"></i>
    </div>
    <div style="height: 0.3rem;"></div>
    <div class="line">
      姓名
      <i class="iconfont">{{$store.state.loginData.data.name}}</i>
    </div>
    <div class="line">
      手机号
      <i class="iconfont">{{$store.state.loginData.data.phone}}</i>
    </div>
    <div class="line">
      邮箱
      <i class="iconfont">{{$store.state.loginData.data.email}}</i>
    </div> <div class="line">
    网络会议
    <span class="umeetingAccount" v-text="$store.state.umeetingAccount"></span>
  </div>
    <mt-actionsheet :actions="actions" v-model="sheetVisible"></mt-actionsheet>
    <!--    <input type="file" id="fileUpdata" v-on:change="upload" multiple>-->
  </div>
</template>

<script type="text/ecmascript-6">
  export default {
    name: 'aboutMy',
    data() {
      return{
        img: 'http://' + localStorage.ip + ':8080'+ this.$store.state.loginData.data.logoUrl,
        sheetVisible: false,
        url: 'http://' + localStorage.ip + ':8080',
        actions : [{
          name: '拍照',
          method: this.takePhoto
        }, {
          name: '从相册中选择',
          method: this.openAlbum
        }]
      }
    },
    methods: {
      upload(){

        let that = this;
        //  var files = event.target.files || event.dataTransfer.files;

        console.log($('#fileUpdata').get(0).files[0]);
        let url =$('#fileUpdata').get(0).files[0];
        let data = {
          fileUrl: url,
          userId: that.$store.state.loginData.data.id
        };
        console.log(data);
        that.axios.post(that.$store.state.api.importHead +'?sessionId='+
          that.$store.state.loginData.data.sessionId,data).then(function (res) {
          console.log(res);
        }).catch(function (err) {
          console.log(err);
        })

      },
      updateCommon(){

      },
      takePhoto(){
        let that = this;
        setTimeout(function () {
          let destinationType=navigator.camera.DestinationType;
          navigator.camera.getPicture(onPhotoDataSuccess, onFail, { quality: 50,
            destinationType: destinationType.FILE_URI }
          );
          //拍照成功
          function onPhotoDataSuccess(imageURL) {
            /* var smallImage = document.getElementById('smallImage');
             smallImage.style.display = 'block';
             smallImage.src = imageURL;*/
            // alert(imageURL);
            that.img = imageURL;
            that.axios.post(that.$store.state.api.importHead +'?sessionId='+
              that.$store.state.loginData.data.sessionId,{
              fileUrl: imageURL,
              userId: that.$store.state.loginData.data.id
            }).then(function (res) {
            //  alert(JSON.stringify(res.data))
            }).catch(function (err) {
            //  alert('error:' + JSON.stringify(err))
            });
            //开始上传

            // upload(imageURL);
          }

          //拍照失败
          function onFail(message) {
            alert('拍照失败: ' + message);
          }

          //上传文件
          function upload(fileURL) {

            //上传成功
            var success = function (r) {
              alert("上传成功! Code = " + r.responseCode);
            }

            //上传失败
            var fail = function (error) {
              alert("上传失败! Code = " + error.code);
            }

            /*  var options = new FileUploadOptions();
             options.fileKey = "file1";
             options.fileName = fileURL.substr(fileURL.lastIndexOf('/') + 1);
             options.mimeType = "image/jpeg";

             //上传参数
             var params = {};
             params.fileUrl = fileURL;
             params.userId = that.$store.state.loginData.data.id;
             options.params = params;*/
            var ft = new FileTransfer();
            //上传地址
            var SERVER = 'http://' + localStorage.ip + that.$store.state.api.importHead +'?sessionId='+
              that.$store.state.loginData.data.sessionId;
            ft.upload(fileURL, encodeURI(SERVER), success, fail, {
              fileUrl: imageURI,
              userId: that.$store.state.loginData.data.id
            });
          };
        },1000)

      },
      openAlbum(){
        let that = this;
        setTimeout(function () {
          let pictureSource = navigator.camera.PictureSourceType;
          let destinationType = navigator.camera.DestinationType;
          let source = pictureSource.PHOTOLIBRARY;
          let onPhotoURISuccess = (imageURI) => {
            //打印出照片路径
         //   alert(imageURI);
            //显示照片

            that.img = imageURI;
            that.axios.post(that.$store.state.api.importHead +'?sessionId='+
              that.$store.state.loginData.data.sessionId,{
              fileUrl: imageURI,
              userId: that.$store.state.loginData.data.id
            }).then(function (res) {
            //  alert(JSON.stringify(res.data))
            }).catch(function (err) {
             // alert('error:' +JSON.stringify(err))
            })
            //upload(imageURI);
          }
          navigator.camera.getPicture(onPhotoURISuccess,
            function(error){console.log("照片获取失败！")},
            { quality: 50, destinationType: destinationType.FILE_URI, sourceType: source });

          let upload = (fileURL) => {

            //上传成功
            let success = function (r) {
              alert("上传成功! Code = " + r.responseCode);
            }

            //上传失败
            let fail = function (error) {
              alert("上传失败! Code = " + error.code);
            }

            /*   let options = new FileUploadOptions();
             options.name = "file";
             options.fileName = fileURL.substr(fileURL.lastIndexOf('/') + 1);
             options.mimeType = "image/jpeg";

             //上传参数
             let params = {};
             params.fileUrl = fileURL;
             params.userId = that.$store.state.loginData.data.id;
             options.params = params;*/
            let ft = new FileTransfer();
            //上传地址
            let SERVER = 'http://' + localStorage.ip + that.$store.state.api.importHead +'?sessionId='+
              that.$store.state.loginData.data.sessionId;
            ft.upload(fileURL, encodeURI(SERVER), success, fail, {
              fileUrl: imageURI,
              userId: that.$store.state.loginData.data.id
            });
          };
        },1000)
      }
    },
    mounted() {
    }

  };
</script>

<style lang="less" rel="stylesheet/less" scoped>
  .mainMy{
    position: relative;
    width: 100%;
    height: 100%;
    .line{
      height: 0.77rem;
      line-height: 0.77rem;
      background-color: #ffffff;
      padding: 0 0.3rem;
      position: relative;
      font-size: 0.28rem;
      border-bottom: 1px solid #eeeeee;
      .iconfont,.umeetingAccount{
        position: absolute;
        right: 0.3rem;
        color: #b9b9b9;
        font-size: 0.3rem;
      }
    }
    .title{
      height: 1.74rem;
      line-height: 1.74rem;
      font-size: 0.3rem;
      .img{
        width: 1.3rem;
        height: 1.3rem;
        border-radius: 50%;
        position: absolute;
        right: 0.88rem;
        padding-top: 0.22rem;
      }
      img{
        width: 100%;
        height: 100%;
        border-radius:50%;
      }
      span{

      }
    }
  }
</style>
