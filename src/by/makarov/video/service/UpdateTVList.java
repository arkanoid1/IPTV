package by.makarov.video.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.IBinder;
import by.makarov.video.Update;

public class UpdateTVList extends Service implements Update {

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();

	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		// need checked file update time to run update task
		ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr
				.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		boolean isWifiConn = networkInfo.isConnected();

		networkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_ETHERNET);
		boolean isNetworkConn = networkInfo.isConnected();

		if (isWifiConn) {
//			new UpdateTVListTask(this).execute();
			return super.onStartCommand(intent, flags, startId);
		}

		if (isNetworkConn) {
//			new UpdateTVListTask(this).execute();
		}

		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void callBack() {
		// TODO Auto-generated method stub
		// if run update to notify
		// NotificationManager nm =
		// (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
		// Notification not = new
		// Notification(R.drawable.ic_notification_overlay, "Update TV list",
		// System.currentTimeMillis());
		// Intent intent = new Intent(this,MainActivity.class);
		// PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent,
		// 0);
		// not.setLatestEventInfo(this, "Update", "Update Tv list", pIntent);
		// not.flags |= Notification.FLAG_ONLY_ALERT_ONCE;
		// nm.notify(1,not);
	}

}
