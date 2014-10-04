package com.facehu.imface.global;

import android.util.Log;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

/**
 * Created by hxb on 2014/10/4.
 */
public class GsonHelper {

    private static final String LOG_TAG = GsonHelper.class.getName();

    public static Gson getGson() {
        return getGsonBuilder().create();
    }

    public static GsonBuilder getGsonBuilder() {
        return new GsonBuilder()
                .serializeNulls()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .registerTypeAdapter(long.class, LongTypeAdapter)
                .registerTypeAdapter(Long.class, LongTypeAdapter)
                .registerTypeAdapter(int.class, IntegerTypeAdapter)
                .registerTypeAdapter(Integer.class, IntegerTypeAdapter)
                .registerTypeAdapter(String.class, StringTypeAdapter);
    }

    private static TypeAdapter<Number> LongTypeAdapter = new TypeAdapter<Number>() {

        @Override
        public void write(JsonWriter out, Number value)
                throws IOException {
            out.value(value);
        }

        @Override
        public Number read(JsonReader in) throws IOException {
            if (in.peek() == JsonToken.NULL) {
                in.nextNull();
                return 0;
            }
            try {
                String result = in.nextString();
                if ("".equals(result)) {
                    return 0;
                }
                return Long.parseLong(result);
            } catch (NumberFormatException e) {
                Log.e(LOG_TAG, String.valueOf(e));
                return 0;
            }
        }
    };

    private static TypeAdapter<Number> IntegerTypeAdapter = new TypeAdapter<Number>() {

        @Override
        public void write(JsonWriter out, Number value)
                throws IOException {
            out.value(value);
        }

        @Override
        public Number read(JsonReader in) throws IOException {
            if (in.peek() == JsonToken.NULL) {
                in.nextNull();
                return 0;
            }
            try {
                String result = in.nextString();
                if ("".equals(result)) {
                    return 0;
                }
                return Integer.parseInt(result);
            } catch (NumberFormatException e) {
                Log.e(LOG_TAG, String.valueOf(e));
                return 0;
            }
        }
    };


    private static TypeAdapter<String> StringTypeAdapter = new TypeAdapter<String>() {

        @Override
        public void write(JsonWriter out, String value)
                throws IOException {
            out.value(value);
        }

        @Override
        public String read(JsonReader in) throws IOException {
            if (in.peek() == JsonToken.NULL) {
                in.nextNull();
                return "";
            }
            String result = in.nextString();
            if ("null".equals(result)) {
                return "";
            }
            return result;
        }
    };
}
