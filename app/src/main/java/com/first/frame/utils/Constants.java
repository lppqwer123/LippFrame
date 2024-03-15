package com.first.frame.utils;

public class Constants {


    public interface INTENT_ACTION {
        String INTENT_ACTION_MY_INFO = "INTENT_ACTION_MY_INFO";
    }

    public interface INTENT_KEY {
        String KEY_TYPE = "KEY_TYPE";
        String KEY_MODEL = "KEY_MODEL";
        String KEY_IMAGES = "KEY_IMAGES";
        String KEY_POSITION = "KEY_POSITION";
        String KEY_TITLE = "KEY_TITLE";
        String KEY_FROM = "KEY_FROM";
        String KEY_NOTIFICATION_EXTRAS = "KEY_NOTIFICATION_EXTRAS";
        String KEY_FID = "KEY_FID";
        String KEY_ID = "KEY_ID";
    }

    public interface RECYCLER_ITEM_VIEW_TYPE {
        int BANNER_VIEW_TYPE = 1;
        int MENU_VIEW_TYPE = 2;
        int MENU2_VIEW_TYPE = 3;
        int TITLE_VIEW_TYPE = 4;
        int END_VIEW_TYPE = 5;
        int CONSULTATION_VIEW_TYPE = 6;
        int ARTICLE_LIST_VIEW_TYPE = 7;
        int CONSULTAT_LIST_VIEW_TYPE = 8;
    }

    public interface EVENT_CODE {
        int APPRAISE_SUCCESS = 1;
        int PAY_SUCCESS = 2;
        int LOGIN_SUCCESS = 3;
        int OUTLOGIN = 4;
        int ATTENT_UPDATE = 5;
        int RECHARGE = 6;
        int USER_INFO_UPDATE = 7;
        int SKIP_LOGIN = 8;
        int ARTICLE_LIKE = 9;
        int BIND_PHONE = 10;
    }

    public interface SP_KEY {
        String SEARCH_HISTORY = "history";
        String IS_FIRST_ENTER_LOGIN = "isFirstEnterLogin";
        String KEY_DEVICE_ID = "KEY_DEVICE_ID";
        String KEY_DEVICE_SERIAL = "KEY_DEVICE_SERIAL";
        String KEY_TIME_ZONE = "KEY_TIME_ZONE";
        String KEY_IMAGE_BASE = "KEY_IMAGE_BASE";
        String KEY_AVATAR_URLS = "KEY_AVATAR_URLS";
        String KEY_USER_INFO = "KEY_USER_INFO";
        String KEY_REFRESH_TOKEN = "refresh_token";
        String KEY_LATEST_VERSION = "latest_version";
        String MAIN_LEFT = "MAIN_LEFT";
        String MAIN_TOP = "MAIN_TOP";
        String MAIN_RIGHT = "MAIN_RIGHT";
        String MAIN_BOTTOM = "MAIN_BOTTOM";
        String KEY_DEVICE_TOKEN = "device_token";
        String KEY_UNREAD_FEED_REPLY = "KEY_UNREAD_FEED_REPLY";

        /**
         * 语音聊天
         */
        String CHANGE_VOICE_POSITION = "change_voice_position";
    }

    public interface Channel {
        int CHANNEL_SNAPCHAT = 0;
        int CHANNEL_FACEBOOK = 1;
        int CHANNEL_GOOGLE = 2;
        int CHANNEL_INSTAGRAM = 3;
    }

    public interface CHAT_INFO {
        String CHAT_INFO = "chatInfo";
    }

}
