var exec = require('cordova/exec');


/**
 * 显示信息提示
 * Umeeting :是plugin.xml中配置的feature的name
 * showToast: 是js中调用的方法名
 */
exports.showToast = function(arg0) {
    exec(null, null, "Umeeting", "showToast", [arg0]);
  };


/**
 * 初始化sdk
 * Umeeting :是plugin.xml中配置的feature的name
 * initSdk: 初始化sdk,界面只初始化一次
 * successCallback:会议操作成功总回调
 * errorCallback：会议操作失败总回调
 */
exports.initSdk =function(successCallback, errorCallback){
              exec(successCallback,errorCallback,"Umeeting","initSdk");
  };

/**
 * 关闭umeeting事件监听，必须调用
 */
exports.destroy =function(){
              exec(null,null,"Umeeting","destroy");
  };

/**
 * 加入会议
 *
 */
exports.jobMeeting =function(meetingNum,meetingPassword,myName){
              exec(null,null,"Umeeting","jobMeeting",[meetingNum,meetingPassword,myName]);
  };

/**
 * 级联会议
 *
 */
exports.hardwareMeeting =function(hardwareId){
              exec(null,null,"Umeeting","hardwareMeeting",[hardwareId]);
  };


/**
 * 开启会议
 */
exports.startMeeting =function(meetingNum,userId,token,meetingName){
              exec(null,null,"Umeeting","startMeeting",[meetingNum,userId,token,meetingName]);
  }
