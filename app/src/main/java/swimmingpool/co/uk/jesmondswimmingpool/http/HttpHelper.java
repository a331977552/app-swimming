package swimmingpool.co.uk.jesmondswimmingpool.http;

import android.os.Handler;

import com.google.gson.Gson;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;

import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by cody on 2017/11/12.
 */

public final class HttpHelper {
    private static final int TIME_OUT = -1;
    private  OkHttpClient okHttpClient;

    private static HttpHelper helper=new HttpHelper();
    private final Gson gson;
    private Handler handler;
    private HttpHelper(){
        okHttpClient = new OkHttpClient();
        handler=new Handler();
        gson = new Gson();
    }

    public static HttpHelper getInstance(){
        return helper;
    }
    public static final int BAD_REQUEST = 400;
    public static final int SERVER_ERROR = 500;
    public static final int NO_FOUND = 404;
    public static final int REDIRECT = 302;





    public void get(String url,final HttpCallBack callBack) {
        Request request = new Request.Builder().url(url).get().build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                callBack.onFailure(getMessage(-1),-1);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                int code = response.code();
                if(code<200|| code>299)
                    callBack.onFailure(getMessage(code),code);
                else {
                    ResponseBody body = response.body();
                    processing(callBack, body);
                }
            }
        });

    }
    public void update(String url,Object obj, final HttpCallBack callBack) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), gson.toJson(obj));
        Request request = new Request.Builder().url(url).patch(requestBody).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                callBack.onFailure(getMessage(-1),-1);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                int code = response.code();
                if(code<200|| code>299)
                    callBack.onFailure(getMessage(code),code);
                else {
                    ResponseBody body = response.body();
                    processing(callBack, body);
                }
            }
        });

    }

    public void post(String url, Object bean, final HttpCallBack callBack) {
        String content = gson.toJson(bean);

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), content);

        Request request = new Request.Builder().url(url).post(requestBody).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                callBack.onFailure(getMessage(-1),-1);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                int code = response.code();
                if(code<200|| code>299)
                    callBack.onFailure(getMessage(code),code);
                else {
                    ResponseBody body = response.body();
                    processing(callBack, body);
                }
            }
        });

    }

    public void remove(String url,final HttpCallBack callBack) {
        Request request = new Request.Builder().url(url).delete().build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                callBack.onFailure(getMessage(-1),-1);
                callBack.after();
            }

            @Override
            public void onResponse(Response response) throws IOException {
                int code = response.code();
                if(code<200|| code>299)
                    callBack.onFailure(getMessage(code),code);
                else {
                    ResponseBody body = response.body();
                    processing(callBack, body);

                }
            }
        });

    }


    private void processing(final HttpCallBack callBack, ResponseBody body) {
        ParameterizedType parameterizedType=(ParameterizedType) callBack.getClass().getGenericInterfaces()[0];
        Type type = parameterizedType.getActualTypeArguments()[0];
        String string = null;
        final Object o;
        try {
            string = body.string();
            if(type ==String.class){
                final String finalString = string;
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callBack.onSuccess(finalString);
                    }
                });

            }else {
            o= gson.fromJson(string, type);
            Method methodStatus = o.getClass().getMethod("getStatus");
            final int status = (int) methodStatus.invoke(o);
            if(status!=0)
            {
                Method methodMsg =    o.getClass().getMethod("getMsg");
                final String msg = (String) methodMsg.invoke(o);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callBack.onFailure(msg,status);
                        callBack.after();
                    }
                });

            }else{
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callBack.onSuccess(o);
                        callBack.after();
                    }
                });

            }
            }
        } catch (Exception e) {
            e.printStackTrace();
            handler.post(new Runnable() {
                @Override
                public void run() {
                    callBack.onFailure("failed to get info",-2);
                    callBack.after();
                    return ;
                }
            });

        }

    }


    public String getMessage(int code) {
        String msg = "sever error";
        
        switch (code) {
            case BAD_REQUEST:
                msg = "server reject";
                break;
            case TIME_OUT:
                msg = "time out,please check network";
                break;

            case SERVER_ERROR:
                msg = "server error";
                break;

            case NO_FOUND:
                msg = "server not found";
                break;


            case REDIRECT:
                msg = "redirect ";
                break;

        }
        return msg;
    }

    }
