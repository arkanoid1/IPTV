package by.makarov.video.receiver;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import by.makarov.video.service.UpdateTVList;

public class BootReceiver extends BroadcastReceiver {
	private final int HOURS = 60 * 60 * 1000;
	private final int MINUTE = 60 * 1000;

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		AlarmManager alarm = (AlarmManager) context
				.getSystemService(Context.ALARM_SERVICE);
		Intent i = new Intent(context, UpdateTVList.class);
		i.setFlags(i.FLAG_ACTIVITY_NEW_TASK);
		PendingIntent pi = PendingIntent.getService(context, 0, i, 0);
		if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
			alarm.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
					SystemClock.elapsedRealtime() + 5 * MINUTE, 3 * HOURS, pi);
		}
	}

}
