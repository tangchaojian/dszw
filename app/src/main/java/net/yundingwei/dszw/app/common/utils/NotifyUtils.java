package net.yundingwei.dszw.app.common.utils;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.annotation.DrawableRes;
import android.support.v4.app.NotificationCompat;


public class NotifyUtils {

	public static void sendUINotification(Context context, Intent intent, int nofifyId, @DrawableRes int icon, String ticherText, String title, String content, String sendTime){
		try {

//			RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.view_default_notification);
//			remoteViews.setTextViewText(R.id.mTvTitle, title);
//			remoteViews.setTextViewText(R.id.mTvDate, sendTime);
//			remoteViews.setTextViewText(R.id.mTvContent, content);

			NotificationCompat.Builder mBuilder =
					new NotificationCompat.Builder(context)
							.setSmallIcon(icon)
							.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), icon))
							.setTicker(ticherText)
							.setContentTitle(title)
							.setContentText(content)
							.setAutoCancel(true)
//							.setContent(remoteViews)
							.setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE);

			PendingIntent resultPendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
			mBuilder.setContentIntent(resultPendingIntent);
			NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
			mNotificationManager.notify("KTX", nofifyId, mBuilder.build());

		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
