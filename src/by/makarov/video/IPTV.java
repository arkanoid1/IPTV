package by.makarov.video;

import android.app.Application;
import org.acra.ACRA;
import org.acra.ErrorReporter;
import org.acra.annotation.ReportsCrashes;

@ReportsCrashes(formUri = "http://www.bugsense.com/api/acra?api_key=dd991912", formKey = "")
public class IPTV extends Application{

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		ACRA.init(this);  
		ErrorReporter.getInstance().checkReportsOnApplicationStart();
		super.onCreate();
		
	}

}
