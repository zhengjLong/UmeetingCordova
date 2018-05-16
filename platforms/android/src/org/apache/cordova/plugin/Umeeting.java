package org.apache.cordova.plugin;


import io.cordova.hellocordova.LoadingDialog;
import io.cordova.hellocordova.MeetingUtils;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaArgs;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * android 提供的codrdova插件调用
 *
 * @author jerome
 * @version 2018/5/11
 */
public class Umeeting extends CordovaPlugin {

    private MeetingUtils meetingUtils;

    @Override
    public boolean execute(String action, String rawArgs, CallbackContext callbackContext) throws JSONException {
        return super.execute(action, rawArgs, callbackContext);
    }

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        return super.execute(action, args, callbackContext);
    }

    @Override
    public boolean execute(String action, final CordovaArgs args, final CallbackContext callbackContext) throws JSONException {

        //显示信息提示
        if (action.equals("showToast")) {
            cordova.getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        LoadingDialog.toast(cordova.getActivity(), args.getString(0));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
            return true;
        }

//       结束会议事件监听
        if (action.equals("destroy")) {
          cordova.getActivity().runOnUiThread(new Runnable() {
              @Override
              public void run() {
                  if (null != meetingUtils) {
                      meetingUtils.destroy();
                      meetingUtils = null;
                  }
              }
          });
            return true;
        }


//        加入会议
        if (action.equals("jobMeeting")) {
          cordova.getActivity().runOnUiThread(new Runnable() {
              @Override
              public void run() {
                  if (null != meetingUtils) {
                      try {
                          meetingUtils.jobMeeting(cordova.getActivity(), args.getString(0), args.getString(1), args.getString(2));
                      } catch (JSONException e) {
                          e.printStackTrace();
                      }
                  }
              }
          });
            return true;
        }

//        级联硬件会议
        if (action.equals("hardwareMeeting")) {
          cordova.getActivity().runOnUiThread(new Runnable() {
              @Override
              public void run() {
                  if (null != meetingUtils) {
                      try {
                          meetingUtils.hardwareMeeting(args.getString(0));
                      } catch (JSONException e) {
                          e.printStackTrace();
                      }
                  }
              }
          });
            return true;
        }

//        开启会议
        if (action.equals("startMeeting")) {
            cordova.getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (null != meetingUtils) {
                        try {
                            meetingUtils.startMeeting(cordova.getActivity(), args.getString(0), args.getString(1), args.getString(2), args.getString(3));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            return true;
        }

//        初始化umeeting sdk
        if (action.equals("initSdk")) {
            initSdk(callbackContext);
            return true;
        }
        return true;
    }


    /**
     * 初始化sdk及事件总回调
     * @param callbackContext
     */
    private void initSdk(final CallbackContext callbackContext) {
        cordova.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                meetingUtils = new MeetingUtils();
                meetingUtils.initSdk(cordova.getActivity(), new MeetingUtils.MeetingCallBack() {
                    @Override
                    public void meetingCallBack(int code, String message) {
                        try {
                            callbackContext.success(new JSONObject().put("code",code).put("message",message));
                        } catch (JSONException e) {
                            e.printStackTrace();
                            callbackContext.error(e.getMessage());
                        }
                    }
                });
            }
        });
    }
}
