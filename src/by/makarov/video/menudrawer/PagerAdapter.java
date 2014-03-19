package by.makarov.video.menudrawer;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

public class PagerAdapter extends FragmentStatePagerAdapter {

	private final Context mContext;
	private final ArrayList<TabInfo> mTabs = new ArrayList<TabInfo>();

	// static final class TabInfo {}

	public PagerAdapter(FragmentActivity activity) {
		super(activity.getSupportFragmentManager());
		mContext = activity;
	}

	@Override
	public int getCount() {
		return mTabs.size();
	}

	@Override
	public Fragment getItem(int position) {
		TabInfo info = mTabs.get(position);
		return Fragment.instantiate(mContext, info.mClss.getName(), info.mArgs);
	}

	public void addTab(Class<?> clss, Bundle args) {
		TabInfo info = new TabInfo(clss, args);
		mTabs.add(info);
		notifyDataSetChanged();
	}


	@Override
	public CharSequence getPageTitle(int position) {
		// TODO Auto-generated method stub
		return "Title " + position;
	}


	@Override
	public int getItemPosition(Object object) {
		// TODO Auto-generated method stub
		return PagerAdapter.POSITION_NONE;
	}

	
}
