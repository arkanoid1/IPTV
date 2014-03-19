package by.makarov.video.menudrawer;

import android.os.Bundle;

  public class TabInfo {


	final Class<?> mClss;
	final Bundle mArgs;

	public TabInfo(Class<?> aClass, Bundle args) {
		mClss = aClass;
		mArgs = args;
	}

}
