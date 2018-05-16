<template>
  <div class="introduce themeBgColor">
  <mt-header fixed title= "产品介绍">
      <span slot="left">
        <mt-button @click.native= "$router.go(-1)"><i class="iconfont fanhui"></i></mt-button>
      </span>
  </mt-header>
    <div class="themeBgColor" style="height: 0.88rem"></div>
    <div class="container bg_fff">
      <div class="line">Umeet智能协作系统是尚阳科技股份有限公司开发的一个全新的智能协作管理平台，针对用户全会议周期，提出了一个全新的管理理念，实现对本地会议、视频会议、电话会议、虚拟会议等资源进行统一预约、管理、统计分析等功能，并集成与会议相关的各种资源，通过各种设备随时随地登陆，实现每位员工都能够轻松简单使用各种会议资源，统一调配提高会议效率，降低企业成本。</div>
      <div class="line">Umeet智能协作系统内嵌虚拟会议功能，融合了视频会议、电话会议、即时消息、远程协作等功能，全面提升企业协作沟通的效率。由于Umeet智能协作系统允许用户在可视的情况下交换信息，因而它能够应用于广泛的领域，无论是用于传达产品介绍、销售活动、员工培训、管理信息还是增强分散地的协作计划，Umeet智能协作系统都日益成为一种能带来竞争优势的实用工具。</div>
    </div>
  </div>
</template>
<script type="text/ecmascript-6">
  export default{
      name: 'introduce',
      data(){
            return{

            }
      },
    mounthed(){
    }
  }
</script>
<style lang="less" rel="stylesheet/less" scoped>
  .introduce{
      width: 100%;
     height:100%;
    .container{
      width: 90%;
      height: 86.5%;
      margin: 5%;
      border-radius: 0.1rem;
      .line{
        width: 94%;
        margin: 0 3%;
        text-indent: 0.3rem;
        font-size: 0.22rem;
        padding-top: 0.2rem;
      }
      .line:last-child{
        margin-top: 0.1rem;
      }
    }
  }
</style>
