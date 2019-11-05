package com.zhy.http.okhttp.callback;

import okhttp3.Request;
import okhttp3.Response;

public abstract class Callback<T>
{
	public final static int SUCCESS = 0;
	public final static int NET_OFFLINE = -1;
	public final static int NET_CANCELED = -2;
	public final static int NET_EXCEPTION = -3;
	public final static int CONTENT_PARSE_ERROR = -4;
	public final static int CONTENT_IS_EMPTY = -5;
	public final static int CONTENT_HANDEL_EXCEPTION = -6;
	
	protected int Status = SUCCESS;
	
	public boolean isOK()
	{
		return Status == SUCCESS;
	}
	
	public int getStatus()
	{
		return Status;
	}
	
	
	protected int viewId = 0;
	
	public int getViewId()
	{
		return viewId;
	}
	
	public Callback()
	{
		
	}
	
	public Callback(int viewId)
	{
		this.viewId = viewId;
	}
	
    /**
     * UI Thread
     *
     * @param request
     */
    public void onBefore(Request request, int id)
    {
    }

    /**
     * UI Thread
     *
     * @param
     */
    public void onAfter(int id)
    {
    }

    /**
     * UI Thread
     *
     * @param progress
     */
    public void inProgress(float progress, long total)
    {

    }

    /**
     * if you parse reponse code in parseNetworkResponse, you should make this method return true.
     *
     * @param response
     * @return
     */
    public boolean validateReponse(Response response, int id)
    {
        return response.isSuccessful();
    }

    /**
     * Thread Pool Thread
     *
     * @param response
     */
    public abstract T parseNetworkResponse(Response response) throws Exception;

    public abstract void onError(int errorId, String errorMsg);

    public abstract void onCancel();

    public abstract void onResponse(T response);


    public static Callback CALLBACK_DEFAULT = new Callback()
    {

        @Override
        public Object parseNetworkResponse(Response response) throws Exception
        {
            return null;
        }

        @Override
        public void onError(int errorId, String errorMsg)
        {

        }

        @Override
        public void onCancel() {

        }

        @Override
        public void onResponse(Object response)
        {

        }
    };

}