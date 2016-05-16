package com.smartbracelet.com.smartbracelet.util;

import java.util.UUID;

import static java.util.UUID.fromString;

/**
 * Created by leo.yang on 2016/4/15.
 */
public interface ConstDefine {

    String ACTION_CONNECTED_CMD = "ACTION_SERVICE_CONNECTED_CMD";

    String ACTION_READ_CMD = "ACTION_SERVICE_READ_CMD";

    String BLE_ADDRESS = "mAddress";

    String BLE_RSSI = "mRssi";

    int TYPE_GET_DEVICE_PARM = 0;
    int TYPE_GET_NUM_PARM = 1;
    int TYPE_UPLOAD_LOCATION = 2;
    int TYPE_UPLOAD_NOTIFY = 3;
    int TYPE_PUSH_MSG = 4;
    int TYPE_PARAMS_POST = 5;
    int TYPE_WARNING_NOTIFY = 6;
    int TYPE_GET_CLCIK_TIMES = 7;
    int TYPE_SET_BIND_STATE = 8;


    final int MSG_SEARCH_OUT = 0;
    final int MSG_SERCH_DONE = 1;
    final int MSG_CHA_READ = 2;
    final int MSG_CHA_SEND_LOCATION = 3;
    final int MSG_PUSH_MSG = 4;
    final int MSG_CHA_WRITE = 5;

    final int DIALOG_TYPE_GPS =0;

    final int STATE_DEVICE_UNBIND = 0;
    final int STATE_DEVICE_BIND = 1;

    String SP_PHONE_NUMBER = "init_phone_number";

    String SP_BIND_STATE = "device_bind_state";

    String SP_POST_INTERNAL = "post_internal_value";

    UUID UUID_READ_SERVICE = fromString("328B1CD1-F643-F7B5-A243-0C51CD3DEEAA");

    UUID UUID_READ_SERVICE_CHARACTER = fromString("EF716109-4F4D-1882-9842-7D95E5B7DF71");

    UUID UUID_WRITE_SERVICE = fromString("000055ff-0000-1000-8000-00805f9b34fb");

    UUID UUID_WRITE_SERVICE_CHARACTER = fromString("000033f2-0000-1000-8000-00805f9b34fb");

    public static class Service {
        final static public UUID HEART_RATE               = UUID.fromString("0000180d-0000-1000-8000-00805f9b34fb");
        final static public UUID BATTERY_SERVICE               = UUID.fromString("0000180f-0000-1000-8000-00805f9b34fb");
        final static public UUID UNKNOWN_SERVICE               = UUID.fromString("328b1cd1-f643-f7b5-a243-0c51cd3deeaa");
        final static public UUID UNKNOWN_SERVICE2               = UUID.fromString("51f6d338-f274-b387-1949-47705f0ab335");
    };

    public static class Characteristic {
        final static public UUID HEART_RATE_MEASUREMENT   = UUID.fromString("00002a37-0000-1000-8000-00805f9b34fb");
        final static public UUID MANUFACTURER_STRING      = UUID.fromString("00002a29-0000-1000-8000-00805f9b34fb");
        final static public UUID MODEL_NUMBER_STRING      = UUID.fromString("00002a24-0000-1000-8000-00805f9b34fb");
        final static public UUID FIRMWARE_REVISION_STRING = UUID.fromString("00002a26-0000-1000-8000-00805f9b34fb");
        final static public UUID APPEARANCE               = UUID.fromString("00002a01-0000-1000-8000-00805f9b34fb");
        final static public UUID BODY_SENSOR_LOCATION     = UUID.fromString("00002a38-0000-1000-8000-00805f9b34fb");
        final static public UUID BATTERY_LEVEL            = UUID.fromString("00002a19-0000-1000-8000-00805f9b34fb");
        final static public UUID CHAR01_LEVEL            = UUID.fromString("ef716109-4f4d-1882-9842-7d95e5b7df71");
        final static public UUID CHAR02_LEVEL            = UUID.fromString("5860dc9a-0ea0-4ebc-9045-f980f732932c");
        final static public UUID CHAR01_LEVEL2            = UUID.fromString("e54d1a53-e9ae-2ea1-6648-5d82466c884e");
        final static public UUID CHAR02_LEVEL2            = UUID.fromString("c8a46bff-fee2-1ca3-0c4f-be76d5afd803");
    }

    public static class Descriptor {
        final static public UUID CHAR_CLIENT_CONFIG       = UUID.fromString("00002902-0000-1000-8000-00805f9b34fb");
    }
}
