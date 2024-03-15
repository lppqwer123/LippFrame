package com.first.frame.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.transform.Result;

public class JsonUtils {
    private static final String TAG = "JsonUtils";
    private static final GsonBuilder gsonb;

    static {
        gsonb = new GsonBuilder();
        gsonb.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {

            @Override
            public Date deserialize(JsonElement json, Type typeOfT,
                                    JsonDeserializationContext context)
                    throws JsonParseException {
                String date = json.getAsJsonPrimitive().getAsString();
                String JSONDateToMilliseconds = "\\/(Date\\((.*?)(\\+.*)?\\))\\/";
                Pattern pattern = Pattern.compile(JSONDateToMilliseconds);
                Matcher matcher = pattern.matcher(date);
                String result = matcher.replaceAll("$2");
                try {
                    return new Date(new Long(result));
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    public static <T> ArrayList<T> getList(String JSONString, Class<T> classOfT) {
        final ArrayList<T> data = new ArrayList<T>();
        JSONArray array = null;
        try {
            array = new JSONArray();
        } catch (Exception e) {
            LogUtils.e("Error in getList - " + e);
        }
        if (array != null) {
            final Gson gson = gsonb.create();
            for (int i = 0; i < array.length(); i++) {
                final JSONObject object;
                try {
                    object = array.getJSONObject(i);
                    final T entity = gson.fromJson(object.toString(), classOfT);
                    data.add(entity);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        return data;
    }

    public static <T> T fromJson(String JSONString, Class<T> classOfT) {
        final Gson gson = gsonb.create();
        T entity = null;
        try {
            LogUtils.d(" output json string = " + JSONString);
            entity = gson.fromJson(JSONString, classOfT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return entity;
    }

    public static <T> T fromJson(byte[] bytes, Class<T> classOfT) {
        final Gson gson = gsonb.create();
        T entity = null;
        try {
            LogUtils.d(" output json string = " + new String(bytes, "UTF-8"));
            entity = gson.fromJson(new String(bytes, "UTF-8"), classOfT);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return entity;
    }

    /**
     * 转换成json数据
     */
    public static String toJson(Object src) {
        final Gson gson = gsonb.create();
        String result = gson.toJson(src);
        return result;
    }


    public static Result fromResuultJson(String json, Class clazz) {
        Gson gson = new Gson();
        Type objectType = type(Result.class, clazz);
        return gson.fromJson(json, objectType);
    }

    public static Result fromResuultJson(String json, Class clazz, Boolean isList) {
        Gson gson = new Gson();
        Type listType = type(List.class, clazz);
        Type type = type(Result.class, listType);
        return gson.fromJson(json, type);
    }

    static ParameterizedType type(final Class raw, final Type... args) {
        return new ParameterizedType() {
            @Override
            public Type getRawType() {
                return raw;
            }

            @Override
            public Type[] getActualTypeArguments() {
                return args;
            }

            @Override
            public Type getOwnerType() {
                return null;
            }
        };
    }
}
