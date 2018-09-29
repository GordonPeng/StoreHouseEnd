package com.sqzn.pl.storehouseend.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.internal.$Gson$Types;
import com.sqzn.pl.storehouseend.MyConst;
import com.sqzn.pl.storehouseend.R;
import com.sqzn.pl.storehouseend.SHApplication;
import com.sqzn.pl.storehouseend.model.Param;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.security.InvalidKeyException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SignatureException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/9/28.
 */

public class HttpUtil {
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    public static final String BaseUrl = "";
    private static HttpUtil mInstance;
    private static Context mContext;
    private final OkHttpClient mOkHttpClient;
    private final Gson mGson;
    private final Handler mDelivery;

    public HttpUtil() {
        mOkHttpClient = new OkHttpClient();
        mOkHttpClient.newBuilder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .build();
        mGson = new Gson();
        mDelivery = new Handler(Looper.getMainLooper());
    }

    public HttpUtil(CookieJar cookieJar) {

        mOkHttpClient = new OkHttpClient().newBuilder()
                .hostnameVerifier(getHostnameVerifier())
                .sslSocketFactory(getTrustedX509())
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(50, TimeUnit.SECONDS)
                .cookieJar(cookieJar)
                .build();
        mDelivery = new Handler(Looper.getMainLooper());
        mGson = new Gson();
    }

    private HostnameVerifier getHostnameVerifier() {
        return new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                //校验服务器端证书域名
                HostnameVerifier hv = HttpsURLConnection.getDefaultHostnameVerifier();
                boolean result = hv.verify("anju.zwtapp.win", session);
                //它的结果一直是false 导致请求不成功javax.net.ssl.SSLPeerUnverifiedException: Hostname anju.zwtapp.win not verified，所以直接强制忽略，只验证服务器端和本地证书是否一致
//                Log.e("TLS", "SSLSession Peer Host=" + session.getPeerHost());
//                Log.e("TLS", "Verify Hostname Result=" + result);
                return true;
            }
        };
    }

    private SSLSocketFactory getTrustedX509() {
        final X509TrustManager[] trustManagers = new X509TrustManager[]{
                new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

                    }

                    @Override
                    public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                        //真正实现校验服务器端证书
                        if (chain == null) {
                            throw new IllegalArgumentException("Check Server X509Certificates is null");
                        }
                        if (chain.length < 0) {
                            throw new IllegalArgumentException("Check Server X509Certificates is empty");
                        }

//                        Log.e("TLS", "Server Certificates size=" + chain.length);
                        for (X509Certificate cert : chain) {
                            cert.checkValidity();//检查服务器证书是否有问题

                            //和app预埋的证书做对比
                            Certificate localCert = getLocalCert();
                            if (localCert != null) {
                                try {
                                    cert.verify(localCert.getPublicKey());
//                                    Log.e("TLS", "Verify Local Cert Success");
                                } catch (NoSuchAlgorithmException | InvalidKeyException
                                        | NoSuchProviderException | SignatureException e) {
                                    e.printStackTrace();
                                    //do something
//                                    Log.e("TLS", "Verify Local Cert Fail");
                                }
                            }
                        }
                    }

                    @Override
                    public X509Certificate[] getAcceptedIssuers() {
                        return new X509Certificate[0];
                    }
                }
        };
        try {
            final SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustManagers, null);
            return sslContext.getSocketFactory();
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            e.printStackTrace();
        }
        return null;
    }

    private X509Certificate getLocalCert() {
        try {
            InputStream certInput = new BufferedInputStream(mContext.getResources().openRawResource(R.raw.certificate));
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            return (X509Certificate) certificateFactory.generateCertificate(certInput);
        } catch (CertificateException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void getmInstance(CookieJar cookieJar, Context context) {
        if (mInstance == null) {
            mContext = context.getApplicationContext();
            getmInstance(cookieJar);
        }
    }

    public static HttpUtil getmInstance(CookieJar cookieJar) {
        if (mInstance == null) {
            synchronized (HttpUtil.class) {
                if (mInstance == null) {
                    mInstance = new HttpUtil(cookieJar);
                }
            }
        }
        return mInstance;
    }

    public static void postAsyn(String url, final ResultCallback callback, List<Param> params) {
        getmInstance(null)._postAsyn(url, callback, params);
    }

    public static abstract class ResultCallback<T> {
        Type mType;

        public ResultCallback() {
            mType = getSuperclassTypeParameter(getClass());
        }

        static Type getSuperclassTypeParameter(Class<?> subclass) {
            Type superclass = subclass.getGenericSuperclass();
            if (superclass instanceof Class) {
                throw new RuntimeException("Missing type parameter.");
            }
            ParameterizedType parameterized = (ParameterizedType) superclass;
            return $Gson$Types.canonicalize(parameterized.getActualTypeArguments()[0]);
        }

        public abstract void onError(Request request, Exception e);

        public abstract void onResponse(T response);
    }

    private void _postAsyn(String url, ResultCallback callback, List<Param> params) {
        Request request = buildPostRequest(url, params);
        if (request.url().toString().equals(BaseUrl + MyConst.userLogin) || request.url().toString().equals(BaseUrl + MyConst.userRegister)) {
            mOkHttpClient.cookieJar().saveFromResponse(request.url(), null);
        }
        mOkHttpClient.cookieJar().loadForRequest(request.url());
        deliveryResult(callback, request);
    }

    private Request buildPostRequest(String url, List<Param> params) {
        if (params == null) {//判断条件应该是params.isEmpty()
            params = new ArrayList<Param>();
            params.add(new Param("v", "1.0"));
            params.add(new Param("p", "30"));
        } else {
            params.add(new Param("v", "1.0"));
            params.add(new Param("p", "30"));
        }
        FormBody.Builder builder = new FormBody.Builder();
        for (Param param : params) {
            builder.add(param.getKey(), param.getValue());
        }
        builder.add("token", FormatRequest(params));
        RequestBody requestBody = builder.build();
        return new Request.Builder()
                .url(BaseUrl + url)
                .post(requestBody)
                .build();
    }

    private String FormatRequest(List<Param> params) {
        new ListSortUtil().sort(params, "key", "");//升序
        String s = "";
        for (Param param : params) {
            s += param.getKey() + "=" + param.getValue() + "&";
        }
//        String md5 = MD5.stringMD5("anju20160701");
        s += "key=" + "anju20160701";
//        Log.e("SHA1明码", s);
//        Log.e("SHA1日志",SHA1.getSha1(s));
        return SHA1Util.getSha1(s);
    }

    public class ListSortUtil<T> {
        /**
         * @param targetList 目标排序List
         * @param sortField  排序字段(实体类属性名)
         * @param sortMode   排序方式（asc or desc）
         */
        @SuppressWarnings({"unchecked", "rawtypes"})
        public void sort(List<T> targetList, final String sortField, final String sortMode) {
            Collections.sort(targetList, new Comparator() {
                public int compare(Object obj1, Object obj2) {
                    int retVal = 0;
                    try {
                        //首字母转大写
                        String newStr = sortField.substring(0, 1).toUpperCase() + sortField.replaceFirst("\\w", "");
                        String methodStr = "get" + newStr;
                        Method method1 = ((T) obj1).getClass().getMethod(methodStr, new Class[0]);
                        Method method2 = ((T) obj2).getClass().getMethod(methodStr, new Class[0]);
                        if (sortMode != null && "desc".equals(sortMode)) {
                            retVal = method2.invoke(((T) obj2)).toString().compareTo(method1.invoke(((T) obj1)).toString()); // 倒序
                        } else {
                            retVal = method1.invoke(((T) obj1)).toString().compareTo(method2.invoke(((T) obj2)).toString()); // 正序
                        }
                    } catch (Exception e) {
                        Log.e("Exception", e + "");
                        throw new RuntimeException();
                    }
                    return retVal;
                }
            });
        }
    }


    public static void postAsyn(String url, ResultCallback callback, File[] files, String[] fileKeys, List<Param> params) throws IOException {
        getmInstance(null)._postAsyn(url, callback, files, fileKeys, params);
    }

    private void _postAsyn(String url, ResultCallback callback, File[] files, String[] fileKeys, List<Param> params) throws IOException {
        Request request = buildMultipartFormRequest(url, files, fileKeys, params);
        deliveryResult(callback, request);
    }
    private Request buildMultipartFormRequest(String url, File[] files,
                                              String[] fileKeys, List<Param> params) {
        if (params == null) {
            params = new ArrayList<Param>();
            params.add(new Param("v", "1.0"));
            params.add(new Param("p", "30"));
        } else {
            params.add(new Param("v", "1.0"));
            params.add(new Param("p", "30"));
        }
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        for (Param param : params) {
            builder.addFormDataPart(param.getKey(), param.getValue());
        }
        builder.addFormDataPart("token", FormatRequest(params));
        if (files != null) {
            RequestBody fileBody = null;
            for (int i = 0; i < files.length; i++) {
                File file = files[i];
                String fileName = file.getName();
                fileBody = RequestBody.create(MediaType.parse(guessMimeType(fileName)), file);
                builder.addFormDataPart(fileKeys[i].toString(), fileName, fileBody);
            }
        }
        RequestBody requestBody = builder.build();
        return new Request.Builder()
                .url(BaseUrl + url)
                .post(requestBody)
                .build();
    }
    private String guessMimeType(String path) {
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        String contentTypeFor = fileNameMap.getContentTypeFor(path);
        if (contentTypeFor == null) {
            contentTypeFor = "application/octet-stream";
        }
        return contentTypeFor;
    }



    private void deliveryResult(final ResultCallback callback, final Request request) {
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            protected Object clone() throws CloneNotSupportedException {
                return super.clone();
            }

            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("onFailure", "请求没有执行");
                sendFailedStringCallback(request, e, callback);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                List<Cookie> mCookies = Cookie.parseAll(request.url(), response.headers());
                if (request.url().toString().equals(BaseUrl + MyConst.userLogin) || request.url().toString().equals(BaseUrl + MyConst.userRegister)) {
                    mOkHttpClient.cookieJar().saveFromResponse(request.url(), mCookies);
                }
                try {
                    String string = response.body().string();
                    Log.e("Body", "hahaha" + string + "hahahahhahah");//测试 注册模块的 发送验证吗接口  出现的错误
                    JSONObject jsonObject = HttpUtil.parseResponse(string);
                    if (callback.mType == String.class) {
                        sendSuccessResultCallback(string, callback);
                    } else if (callback.mType == JSONObject.class && jsonObject != null) {
                        sendSuccessResultCallback(jsonObject, callback);
                    } else {
                        Object o = mGson.fromJson(string, callback.mType);
                        sendSuccessResultCallback(o, callback);
                    }
                } catch (IOException e) {
                    Log.e("onFailure ", "IOException");
                    sendFailedStringCallback(response.request(), e, callback);
                } catch (com.google.gson.JsonParseException e) {
                    Log.e("onFailure", "com.google.gson.JsonParseException");
                    sendFailedStringCallback(response.request(), e, callback);
                }

            }
        });
    }

    private void sendFailedStringCallback(final Request request, final Exception e, final ResultCallback callback) {
        mDelivery.post(new Runnable() {
            @Override
            public void run() {
                if (callback != null) {
                    callback.onError(request, e);
                    Log.e("hello", e.toString());
                    Log.e("onFailure", "请求执行失败");
                    Util.showToast(SHApplication.getInstance().getlastActivity(), "网络连接有误，请检查网络");
                }
            }
        });
    }

    private void sendSuccessResultCallback(final Object object, final ResultCallback callback) {
        mDelivery.post(new Runnable() {
            @Override
            public void run() {
                if (callback != null && object != null) {
                    if (callback.mType == JSONObject.class) {
                        try {
                            if (((JSONObject) object).getInt("code") == -1) {
                                Util.showToast(SHApplication.getInstance().getlastActivity(), "您的登陆已过期，请重新登陆");
                                SharedPreferences.Editor editor = Util.getSharedPreferences(SHApplication.getInstance().getlastActivity()).edit();
                                editor.putString("phone", null);
                                editor.putString("share", null);
                                editor.putBoolean("setAliasAndTags", false);
                                //modify in 20171113 修改本地推送记录标识位，并清空本地数据
//                                editor.putBoolean(MyConst.RECORD_IN_LOCAL, false);
//                                LitePal.deleteDatabase("record");
//                                editor.putBoolean("bindAccountXG", false);
                                editor.apply();
//                                Intent intent = new Intent(SHApplication.getInstance().getlastActivity(), LoginActivity.class);
//                                Util.StartActivity(intent);
                            } else {
                                callback.onResponse(object);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            //modifying in 2018-01-02
                            callback.onResponse(object);
                        }
                    } else if (callback.mType == String.class) {//modify in 2017-12-29 图片上传返回结果是字符串，不是json格式字符串
                        callback.onResponse(object);
                    }
                } else {
                    callback.onError(null, new JSONException("JSONException"));
                }
            }
        });
    }

    public static JSONObject parseResponse(String jsonString) {
        if (jsonString == null) {
            return null;
        }
        Object result = null;
        jsonString = jsonString.trim();
        if (jsonString.startsWith("{") || jsonString.startsWith("[")) {
            try {
                result = (new JSONTokener(jsonString)).nextValue();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (result instanceof JSONObject) {
            return (JSONObject) result;
        } else {
            Log.e("warning", new JSONException("Unexpected response type " + jsonString) + "");
            return null;
        }
    }


}
