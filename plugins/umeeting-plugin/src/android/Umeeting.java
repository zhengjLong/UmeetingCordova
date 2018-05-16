package org.apache.cordova.plugin;

import android.util.Log;
import android.widget.Toast;

import com.jerome.test.android.MeetingUtils;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaArgs;
import org.apache.cordova.CordovaPlugin;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;

/**
 * 原生与js交互demo
 * @author jerome
 * @version 2018/5/9
 */
public class Umeeting extends CordovaPlugin {

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
        //showToast 是你在JS中调用的方法名；
        if (action.equals("showToast")){
            //这里可以实现一些你的原生逻辑功能
            Toast.makeText(cordova.getActivity(), args.getString(0), Toast.LENGTH_LONG).show();
            return true;
        }
        if (action.equals("toastWithCallback")){
            cordova.getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Toast.makeText(cordova.getActivity(), args.getString(0), Toast.LENGTH_LONG).show();
                        MeetingUtils meetingUtils = new MeetingUtils();
                        meetingUtils.initSdk(cordova.getActivity(), new MeetingUtils.MeetingCallBack() {
                            @Override
                            public void meetingErrorMessage(int errorCode, @NotNull String message) {
                                Log.e("jerome","meetingErrorMessage=========");
                                callbackContext.error(message);
                            }

                            @Override
                            public void meetingDisconnected() {
                                Log.e("jerome","meetingDisconnected=========");
                                callbackContext.error("断开连接");
                            }

                            @Override
                            public void meetingCancel() {
                                Log.e("jerome","meetingCancel=========");
                                callbackContext.error("取消连接");
                            }

                            @Override
                            public void meetingSuccessCallBack() {
                                Log.e("jerome","meetingSuccessCallBack=========");
                                callbackContext.success("连接成功");
                            }

                            @Override
                            public void hardwareMeetingState(boolean success) {
                                Log.e("jerome","hardwareMeetingState=========");
                                if (success){
                                    callbackContext.success("连接成功");
                                }else {
                                    callbackContext.error("连接出错");
                                }
                            }
                        });
                        meetingUtils.startMeeting(cordova.getActivity(),"111111111","1231","23","fsfs");
                    } catch (JSONException e) {
                        e.printStackTrace();
                        callbackContext.error("格式出错");
                    }
                }
            });


        }
        return true;
    }
}
