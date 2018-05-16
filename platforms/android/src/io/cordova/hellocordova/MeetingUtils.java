package io.cordova.hellocordova;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;

import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import us.zoom.sdk.CallOutRoomSystemStatus;
import us.zoom.sdk.InviteRoomSystemHelper;
import us.zoom.sdk.InviteRoomSystemListener;
import us.zoom.sdk.JoinMeetingOptions;
import us.zoom.sdk.MeetingError;
import us.zoom.sdk.MeetingEvent;
import us.zoom.sdk.MeetingService;
import us.zoom.sdk.MeetingServiceListener;
import us.zoom.sdk.MeetingStatus;
import us.zoom.sdk.StartMeetingOptions;
import us.zoom.sdk.ZoomError;
import us.zoom.sdk.ZoomSDK;
import us.zoom.sdk.ZoomSDKInitializeListener;

/**
 * 会议工具类
 *
 * @author jerome
 * @version 2018/3/27
 */
public class MeetingUtils implements ZoomSDKInitializeListener, MeetingServiceListener, InviteRoomSystemListener {

    //    开始会议类型
    private int STYPE = MeetingService.USER_TYPE_API_USER;

    //    是否开启一个新的会议
    private boolean mbPendingStartMeeting = false;

    //    sdk统一回调
    private MeetingCallBack callBack = null;

    private int connectionCount = 5;//硬件会议断后重连接

    private int resetCount = 5; //剩余连接数

    private String currentHardwareId = ""; //硬件地址


    /**
     * 初始化sdk:只初始化一次
     */
    public void initSdk(Context context, MeetingCallBack callBack) {

        ZoomSDK sdk = ZoomSDK.getInstance();

        this.callBack = callBack;

        if (!sdk.isInitialized()) {
//            进行初始化
            sdk.initialize(context, BuildConfig.UMEETING_KEY,
                    BuildConfig.UMEETING_SECRET,
                    BuildConfig.UMEETING_DOMAIN, this);
        } else {
//            添加注册监听
            registerMeetingServiceListener();
        }

    }


    /**
     * 必须同步生命周期关闭
     */
    public void destroy() {
//        关闭会议监听
        ZoomSDK zoomSDK = ZoomSDK.getInstance();

        if (zoomSDK.isInitialized()) {
            MeetingService meetingService = zoomSDK.getMeetingService();
            meetingService.removeListener(this);

            meetingService.getInviteRoomSystemHelper().removeEventListener(this);
        }
    }

    /**
     * 注册会议回调监听
     */
    private void registerMeetingServiceListener() {
        ZoomSDK zoomSDK = ZoomSDK.getInstance();
        MeetingService meetingService = zoomSDK.getMeetingService();
        meetingService.addListener(this);
    }


    /**
     * 加入会议
     */
    public void jobMeeting(final Context context, final String meetingNo, final String meetingPassword, final String myName) {

        getUserPermission((Activity) context, new PermissionCallBack() {
            @Override
            public void isSuccess(Boolean isSuccess) {
                if (isSuccess) {
                    if (TextUtils.isEmpty(meetingNo)) {
                        if (null != callBack)
                            callBack.meetingCallBack(UmeetingStatus.MEETING_PARAM_ERROR, "请传入会议ID");
                        return;
                    }

                    ZoomSDK zoomSDK = ZoomSDK.getInstance();

                    if (!zoomSDK.isInitialized()) {
                        if (null != callBack)
                            callBack.meetingCallBack(UmeetingStatus.MEETING_INIT_ERROR, "会议功能初始化失败");
                        return;
                    }
                    MeetingService meetingService = zoomSDK.getMeetingService();

                    JoinMeetingOptions opts = new JoinMeetingOptions();

                    int ret = meetingService.joinMeeting(context, meetingNo, myName, meetingPassword, opts);
                }
            }
        });
    }


    /**
     * 级联会议
     */
    public void hardwareMeeting(String hardwareId) {

        ZoomSDK zoomSDK = ZoomSDK.getInstance();

        if (!zoomSDK.isInitialized()) {
            if (null != callBack)
                callBack.meetingCallBack(UmeetingStatus.MEETING_INIT_ERROR, "会议功能初始化失败");
            return;
        }

        if (TextUtils.isEmpty(hardwareId)) {
            if (null != callBack)
                callBack.meetingCallBack(UmeetingStatus.MEETING_PARAM_ERROR, "请传入会议ID");
            return;
        }

//        记录连接次数
        resetCount--;
        currentHardwareId = hardwareId;

        MeetingService meetingService = zoomSDK.getMeetingService();

        meetingService.getInviteRoomSystemHelper().addEventListener(this);
        boolean isSuccess = meetingService.getInviteRoomSystemHelper().callOutRoomSystem(hardwareId,
                InviteRoomSystemHelper.ROOMDEVICE_H323);
    }


    /**
     * 调用sdk开启会议
     */

    public void startMeeting(final Context context, final String meetingNo, final String userId, final String token, final String meetingName) {


        getUserPermission((Activity) context, new PermissionCallBack() {
            @Override
            public void isSuccess(Boolean isSuccess) {
                if (isSuccess) {
                    ZoomSDK zoomSDK = ZoomSDK.getInstance();

                    if (TextUtils.isEmpty(meetingNo)) {
                        callBack.meetingCallBack(UmeetingStatus.MEETING_PARAM_ERROR, "请传入会议ID");
                        return;
                    }
                    if (TextUtils.isEmpty(userId)) {
                        callBack.meetingCallBack(UmeetingStatus.MEETING_PARAM_ERROR, "请传入userId");
                        return;
                    }
                    if (TextUtils.isEmpty(token)) {
                        callBack.meetingCallBack(UmeetingStatus.MEETING_PARAM_ERROR, "请传入token");
                        return;
                    }

                    if (TextUtils.isEmpty(meetingName)) {
                        callBack.meetingCallBack(UmeetingStatus.MEETING_PARAM_ERROR, "请传入会议名称");
                        return;
                    }

                    if (!zoomSDK.isInitialized()) {
                        callBack.meetingCallBack(UmeetingStatus.MEETING_INIT_ERROR, "会议功能初始化失败");
                        return;
                    }

                    MeetingService meetingService = zoomSDK.getMeetingService();

                    //		判断当前是否已有会议
                    if (alreadyHasMeeting(context, meetingService, meetingNo)) {
                        return;
                    }

                    StartMeetingOptions opts = new StartMeetingOptions();
                    int ret = meetingService.startMeeting(context, userId, token, STYPE, meetingNo, meetingName, opts);
                }
            }
        });
    }


    /**
     * 当前已开启会议
     */
    private boolean alreadyHasMeeting(Context context, final MeetingService meetingService, String meetingNo) {
        if (meetingService.getMeetingStatus() != MeetingStatus.MEETING_STATUS_IDLE) {
            long lMeetingNo;
            try {
                lMeetingNo = Long.parseLong(meetingNo);
            } catch (NumberFormatException e) {
                if (null != callBack)
                    callBack.meetingCallBack(UmeetingStatus.MEETING_PARAM_ERROR, "请传入正确的会议ID");
                return true;
            }
            if (meetingService.getCurrentMeetingID() == lMeetingNo) {
                meetingService.returnToMeeting(context);
                return true;
            }

            LoadingDialog.alert(context, "是否离开当前的会议并开启新的会议", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (which == DialogInterface.BUTTON_POSITIVE) {
                        mbPendingStartMeeting = true;
                        meetingService.leaveCurrentMeeting(false);
                    }
                }
            });
            return true;
        }
        return false;
    }


    /**
     * 硬件会议超时重连
     */
    private void reConnect(int p0) {
        MeetingService service = ZoomSDK.getInstance().getMeetingService();

        if (resetCount != 0 && service.getMeetingStatus() != MeetingStatus.MEETING_STATUS_IDLE) {
            switch (p0) {
                case CallOutRoomSystemStatus.CallOutRoomSystem_Timeout:
                    //                超时
                    hardwareMeeting(currentHardwareId);
                    break;
                case CallOutRoomSystemStatus.CallOutRoomSystem_Failed:
                    //               失败
                    hardwareMeeting(currentHardwareId);
                    break;
                case CallOutRoomSystemStatus.CallOutRoomSystem_Unknown:
                    //                  未知
                    hardwareMeeting(currentHardwareId);
                    break;
                case CallOutRoomSystemStatus.CallOutRoomSystem_Success:
                    //                  成功
                    resetCount = connectionCount;
                    if (null != callBack)
                        callBack.meetingCallBack(UmeetingStatus.MEETING_HARDWARE_SUCCESS, "硬件会议连接成功");
                    break;
                default:
                    resetCount = connectionCount;
                    if (null != callBack)
                        callBack.meetingCallBack(UmeetingStatus.MEETING_HARDWARE_ERROR, "硬件会议连接失败");
                    break;

            }
        }
    }

    @Override
    public void onCallOutRoomSystemStatusChanged(int po) {
//        超时重连sdk调用无效
//        reConnect(p0)
        switch (po) {
            case CallOutRoomSystemStatus.CallOutRoomSystem_Timeout:
            case CallOutRoomSystemStatus.CallOutRoomSystem_Failed:
            case CallOutRoomSystemStatus.CallOutRoomSystem_Unknown:
                //                失败
                if (null != callBack)
                    callBack.meetingCallBack(UmeetingStatus.MEETING_HARDWARE_ERROR, "硬件会议连接失败");
                break;
            case CallOutRoomSystemStatus.CallOutRoomSystem_Success:
                //                  成功
                if (null != callBack)
                    callBack.meetingCallBack(UmeetingStatus.MEETING_HARDWARE_SUCCESS, "硬件会议连接成功");
                break;
        }
    }

    @Override
    public void onParingRoomSystemResult(int i) {

    }


    /**
     * 会议事件监听回调
     */
    @Override
    public void onMeetingEvent(int meetingEvent, int errorCode, int internalErrorCode) {
//        版本过低
        if (meetingEvent == MeetingEvent.MEETING_CONNECT_FAILED) {
            if (errorCode == MeetingError.MEETING_ERROR_CLIENT_INCOMPATIBLE && null != callBack)
                callBack.meetingCallBack(UmeetingStatus.MEETING_LEVEL_LOW, "当前应用版本过低，请更新app");

        }

//       会议掉线
        if (meetingEvent == MeetingEvent.MEETING_DISCONNECTED && null != callBack) {
            if (mbPendingStartMeeting) {
                mbPendingStartMeeting = false;
            }
            callBack.meetingCallBack(UmeetingStatus.MEETING_DISCONNECTED, "断开连接");
        }

//        已连接会议
        if (meetingEvent == MeetingEvent.MEETING_CONNECTED && null != callBack) {
            callBack.meetingCallBack(UmeetingStatus.MEETING_CONNECTED_SUCCESS, "连接成功");
        }
    }


    /**
     * sdk初始化回调
     */
    @Override
    public void onZoomSDKInitializeResult(int errorCode, int internalErrorCode) {
        //        初始化失败
        if (errorCode != ZoomError.ZOOM_ERROR_SUCCESS) {
            if (null != callBack)
                callBack.meetingCallBack(UmeetingStatus.MEETING_INIT_ERROR, "会议功能初始化失败");
        } else {
            registerMeetingServiceListener();
        }

    }


    public interface MeetingCallBack {

        /**
         * 总回调
         */
        void meetingCallBack(int code, String message);


    }

    @SuppressLint("CheckResult")
    /**
     * 是否获得用户权限许可
     *
     * @return 权限是否可用
     */
    private void getUserPermission(Activity context, final PermissionCallBack callBack) {

        try {
            RxPermissions rxPermissions = new RxPermissions(context);
            rxPermissions.request(Manifest.permission.CAMERA,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO)
                    .subscribe(new Observer<Boolean>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(Boolean permission) {
                            callBack.isSuccess(permission);
                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onComplete() {

                        }
                    });

        } catch (Exception e) {
            e.printStackTrace();
            callBack.isSuccess(false);
        }
    }

    /**
     * 权限回调
     */
    interface PermissionCallBack {
        void isSuccess(Boolean isSuccess);
    }

}

