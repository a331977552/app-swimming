package swimmingpool.co.uk.jesmondswimmingpool.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.content.SharedPreferencesCompat;

/**
 * Created by cody on 2017/11/13.
 */

public class SpUtils {

    private static final String SP_NAME="swimming_shared";
    static SharedPreferences sp;
    static {
        sp= App.context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
    }
    public static void put(String name,String value){
        sp.edit().putString(name,value).apply();

    }
    public static void put(String name,float value){
        sp.edit().putFloat(name,value).apply();

    }

    public static void put(String name,int value){
        sp.edit().putInt(name,value).apply();

    }
    public static void put(String name,boolean value){
        sp.edit().putBoolean(name,value).apply();

    }
    public static void put(String name,long value){
        sp.edit().putLong(name,value).apply();

    }
    public static String getString(String name){
        return sp.getString(name,"");

    }
    public static String getString(String name,String defaultValue){
        return sp.getString(name,defaultValue);

    }
    public static Float getFloat(String name){
        return sp.getFloat(name,0);

    }

    public static Integer getInt(String name){
        return sp.getInt(name,0);

    }
    public static Boolean getBoolean(String name){
        return sp.getBoolean(name,false);

    }
    public static Long getLong(String name){
        return sp.getLong(name,0);

    }
}
