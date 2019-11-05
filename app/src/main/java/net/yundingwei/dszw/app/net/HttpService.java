package net.yundingwei.dszw.app.net;

import android.content.Context;
import android.util.Log;

import com.franmontiel.persistentcookiejar.ClearableCookieJar;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;
import com.zhy.http.okhttp.callback.FileCallBack;
import com.zhy.http.okhttp.cookie.CookieJarImpl;
import com.zhy.http.okhttp.https.HttpsUtils;
import com.zhy.http.okhttp.log.LoggerInterceptor;

import java.io.File;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.CookieJar;
import okhttp3.OkHttpClient;

public class HttpService {
	
	public static void init(Context context)
	{
		ClearableCookieJar cookieJar = new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(context));
		HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory(null, null, null);
		OkHttpClient okHttpClient = new OkHttpClient.Builder()
				.readTimeout(30, TimeUnit.SECONDS)
				.connectTimeout(5, TimeUnit.SECONDS)
				.writeTimeout(60, TimeUnit.SECONDS)
				.addInterceptor(new LoggerInterceptor("TAG"))
				.cookieJar(cookieJar)
				.hostnameVerifier(new HostnameVerifier() {
					@Override
					public boolean verify(String hostname, SSLSession session) {
						return true;
					}
				})
				.sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
				.build();
		OkHttpUtils.initClient(okHttpClient);
	}

	public static void cancelRequest(int cancelId){
		if(cancelId != 0) {
			OkHttpUtils.getInstance().cancelTag(cancelId);
		}
	}
	
	public static void clearSession()
	{
		CookieJar cookieJar = OkHttpUtils.getInstance().getOkHttpClient().cookieJar();
        if (cookieJar instanceof CookieJarImpl)
        {
            ((CookieJarImpl) cookieJar).getCookieStore().removeAll();
        }
	}
	
	public static void get(String url, Callback<?> callback)
	{

		Log.i("TAG", "url->" + url);
		OkHttpUtils.get()
			.url(url)
			.id(url.hashCode())
			.tag(callback)
			.build()
			.execute(callback);
	}
	

	public static void post(String url, Map<String, String> params, Callback<?> callback)
	{
		OkHttpUtils.post()
			.url(url)
			.id(params.hashCode())
			.tag(callback)
			.params(params)
			.build()
			.execute(callback);
	}
	
	public static void upload(String url, File file, Callback<?> callback)
	{
		OkHttpUtils.postFile()
		.url(url)
		.addHeader("Content-Type","application/json")
		.addHeader("Accept","application/json")
		.id(file.hashCode())
		.tag(callback)
		.file(file)
		.build()
		.execute(callback);
	}
	
	public static void download(String url, FileCallBack callback)
	{
		OkHttpUtils.get()
			.url(url)
			.tag(callback)
			.build()
			.execute(callback);
	}
	
}
