package io.cordova.hellocordova;

/**
 * 封装的会议回调
 *
 * @author jerome
 * @version 2018/5/11
 */
interface UmeetingStatus {

    int MEETING_DISCONNECTED = 0;//断开连接
    int MEETING_LEVEL_LOW = -1;//当前应用版本过低，请更新app
    int MEETING_INIT_ERROR = -2;//sdk初始化失败
    int MEETING_PARAM_ERROR = -3;//参数错误
    int MEETING_HARDWARE_ERROR = -4;//连接硬件会议失败
    int MEETING_CONNECTED_SUCCESS = 1;//连接会议成功
    int MEETING_HARDWARE_SUCCESS = 2;//连接硬件会议成功
}
