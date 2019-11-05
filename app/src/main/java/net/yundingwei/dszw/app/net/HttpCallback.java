package net.yundingwei.dszw.app.net;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.zhy.http.okhttp.callback.Callback;

import net.yundingwei.dszw.app.net.MessageDeserializer;
import net.yundingwei.dszw.app.net.resmsg.ResMessage;

import okhttp3.Request;
import okhttp3.Response;

/**
 * 网络消息响应回调
 * @param要解析的消息体类型
 */
public abstract class HttpCallback<T> extends Callback<ResMessage> {

	private String method;

	public HttpCallback(String method) {
		this.method = method;
	}


	@Override
	public void onBefore(Request request, int id) {
		super.onBefore(request, id);
	}

	@Override
	public void onAfter(int id) {
		super.onAfter(id);
	}

	@Override
	public ResMessage parseNetworkResponse(Response response) throws Exception {
		String content = response.body().string();
//		Log.d("DSZW", "返回报文");
//		Log.d("DSZW", "=========================================================");
//		Log.d("DSZW", content);
//		Log.d("DSZW", "=========================================================");

		if(TextUtils.isEmpty(content)) {
			Status = CONTENT_IS_EMPTY;
			return null;
		}

		ResMessage resMsg = null;
		try {
			Gson gson = new Gson();
			resMsg = gson.fromJson(content, ResMessage.class);
			Object data = resMsg.getData();
			T t = gson.fromJson(gson.toJson(data), MessageDeserializer.typeMap.get(method));
			resMsg.setData(t);
		}
		catch(Exception e) {
			e.printStackTrace();
			Log.e("内容解析异常！", e.getMessage());
		}

		if(resMsg == null) {
			Status = CONTENT_PARSE_ERROR;
			return null;
		}
		Status = SUCCESS;
		return resMsg;
	}
	
	@SuppressWarnings("unchecked")
	@Override
    public void onResponse(ResMessage resMsg)
    {
		try
		{
			onMessage((T)resMsg.getData());
		}
		catch(Exception e)
		{
			e.printStackTrace();
			Log.e("内容处理异常！", e.getMessage());
			onError(CONTENT_HANDEL_EXCEPTION, "内容处理异常！");
		}
    }

	@Override
	public void onCancel() {

	}

	public abstract void onMessage(T body);

}
