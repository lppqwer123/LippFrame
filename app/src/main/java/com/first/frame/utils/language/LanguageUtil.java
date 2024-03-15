package com.first.frame.utils.language;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.LocaleList;
import android.util.DisplayMetrics;
import android.util.Log;

import com.first.frame.utils.SPUtils;

import java.util.Locale;

/**
 * 多语言切换的帮助类
 */
public class LanguageUtil {

    private static final String TAG = "LanguageUtil";
    private static final int DEFAULT_LANGUAGE = LanguageType.LAUGUAGE_ZH;
    private static String[] languages = {"zh_CN", "en-us"};
    private static String LANGUAGE = languages[0];
    private static LanguageUtil instance;
    private Context mContext;
    private static final String SAVE_LANGUAGE = "save_language";

    private LanguageUtil(Context context) {
        this.mContext = context;
    }

    public static void init(Context mContext) {
        if (instance == null) {
            synchronized (LanguageUtil.class) {
                if (instance == null) {
                    instance = new LanguageUtil(mContext);
                }
            }
        }
    }

    public static LanguageUtil getInstance() {
        if (instance == null) {
            throw new IllegalStateException("You must be init LanguageUtil first");
        }
        return instance;
    }


    public static void initLanguage() {
        int languageType = LanguageUtil.getInstance().getLanguageType();
        if (languageType == LanguageType.LANGUAGE_EN) {
            /**
             * 中文
             */
            LANGUAGE = languages[0];
        } else {
            /**
             * 英文
             */
            LANGUAGE = languages[1];
        }
    }

    /**
     * 设置语言
     */
    public void setConfiguration() {
        Locale targetLocale = getLanguageLocale();
        Configuration configuration = mContext.getResources().getConfiguration();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            configuration.setLocale(targetLocale);
        } else {
            configuration.locale = targetLocale;
        }
        Resources resources = mContext.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        resources.updateConfiguration(configuration, dm);//语言更换生效的代码!

    }

    /**
     * 如果不是英文、简体中文、繁体中文，默认返回简体中文
     *
     * @return
     */
    public Locale getLanguageLocale() {
        int languageType = getLanguageType();
        if (languageType == LanguageType.LANGUAGE_FOLLOW_SYSTEM) {
            Locale sysLocale = getSysLocale();
            return sysLocale;
        } else if (languageType == LanguageType.LANGUAGE_EN) {
            return Locale.ENGLISH;
        } else if (languageType == LanguageType.LAUGUAGE_ZH) {
            return Locale.CHINESE;
        }
        getSystemLanguage(getSysLocale());
        Log.e(TAG, "getLanguageLocale" + languageType);
        return Locale.SIMPLIFIED_CHINESE;
    }

    private String getSystemLanguage(Locale locale) {
        return locale.getLanguage() + "_" + locale.getCountry();

    }

    //以上获取方式需要特殊处理一下
    public Locale getSysLocale() {
        Locale locale;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            locale = LocaleList.getDefault().get(0);
        } else {
            locale = Locale.getDefault();
        }
        return locale;
    }

    /**
     * 更新语言
     *
     * @param languageType
     */
    public void updateLanguage(int languageType) {
        SPUtils.getInstance().put(LanguageUtil.SAVE_LANGUAGE, languageType);
        LanguageUtil.getInstance().setConfiguration();
//        EventBus.getDefault().post(new OnChangeLanguageEvent(languageType));
    }


    /**
     * 获取到用户保存的语言类型
     *
     * @return
     */
    public int getLanguageType() {
        int languageType = SPUtils.getInstance().getInt(LanguageUtil.SAVE_LANGUAGE, DEFAULT_LANGUAGE);
        Log.e(TAG, "getLanguageType" + languageType);
        return languageType;
    }

    public String getLanguageName() {
        int languageType = SPUtils.getInstance().getInt(LanguageUtil.SAVE_LANGUAGE, DEFAULT_LANGUAGE);
        Log.e(TAG, "getLanguageType" + languageType);
        return languageType == LanguageType.LAUGUAGE_ZH ? "zh" : "en";
    }

    public static Context attachBaseContext(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return createConfigurationResources(context);
        } else {
            if (instance ==null){
                init(context);
            }
            getInstance().setConfiguration();
            return context;
        }
    }

    @TargetApi(Build.VERSION_CODES.N)
    private static Context createConfigurationResources(Context context) {
        Resources resources = context.getResources();
        Configuration configuration = resources.getConfiguration();
        Locale locale = getInstance().getLanguageLocale();
        configuration.setLocale(locale);
        return context.createConfigurationContext(configuration);
    }

    public String[] getLanguages() {
        return languages;
    }
}