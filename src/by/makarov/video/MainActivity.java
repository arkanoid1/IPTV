package by.makarov.video;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.wifi.WifiManager;
import android.net.wifi.WifiManager.MulticastLock;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.*;
import android.widget.AdapterView;
import android.widget.ListView;
import by.makarov.video.R.string;
import by.makarov.video.database.DBManager;
import by.makarov.video.entitys.ChanelM3U;
import by.makarov.video.menudrawer.Item;
import by.makarov.video.menudrawer.MenuAdapter;
import by.makarov.video.menudrawer.PagerAdapter;
import by.makarov.video.menudrawer.VideoFragment;
import by.makarov.video.task.UpdateTVListTask;
import net.simonvt.menudrawer.MenuDrawer;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity implements Update {
    private static String srcPath = "";
    private String URL = "";
    private static final String STATE_ACTIVE_POSITION = "net.simonvt.menudrawer.samples.ContentSample.activePosition";
    private MenuDrawer mMenuDrawer;
    private MenuAdapter mAdapter;
    private ListView mList;
    private int mActivePosition = -1;
    private PagerAdapter mPagerAdapter;
    private ViewPager mViewPager;
    private URL url;
    private DBManager dbm;
    private SharedPreferences sp;
    private final int UPDATE = 1;
    private final int SETUP = 2;
    private final int EXIT = 3;

    private ArrayList<ChanelM3U> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mActivePosition = savedInstanceState.getInt(STATE_ACTIVE_POSITION);
        }

        setContentView(R.layout.main);
        // get url list programm from shared preferences
        sp = getSharedPreferences("iptv", MODE_PRIVATE);
        URL = sp.getString("urlIptv", "");
        // if URL empty to need setup
        if (URL.isEmpty()) {
            startActivity(new Intent(this, SetupActivity.class));
            finish();
        }

        VideoFragment.setSrc(srcPath); // Default tv chanel
        // init to external lib to play video
        if (!io.vov.vitamio.LibsChecker.checkVitamioLibs(this))
            return;

        unlockMulticast(); // call method to unlock multicast

        mMenuDrawer = MenuDrawer.attach(this, MenuDrawer.MENU_DRAG_CONTENT);
        mMenuDrawer.setContentView(R.layout.activity_viewpager);

        createMenu(); // call method to create left drag menu

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }

        mMenuDrawer.setTouchMode(MenuDrawer.TOUCH_MODE_FULLSCREEN);

        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mViewPager
                .setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
                    @Override
                    public void onPageSelected(final int position) {
                        mMenuDrawer
                                .setTouchMode(position == 0 ? MenuDrawer.TOUCH_MODE_FULLSCREEN
                                        : MenuDrawer.TOUCH_MODE_NONE);
                    }
                });

        mPagerAdapter = new PagerAdapter(this);
        ((PagerAdapter) mPagerAdapter).addTab(
                VideoFragment.class, null);

        mViewPager.setAdapter(mPagerAdapter);

    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
//		fullScrean();
        super.onResume();
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();

    }

    private void createMenu() {
        List<Object> items = new ArrayList<Object>();
        // items.add(new Category("Chanel 1"));

        // Get tvlist from database
        dbm.createInstance(this);
        dbm.getInstance().connect();

        list = dbm.getInstance().getChanels();
        for (ChanelM3U x : list) {
            items.add(new Item(x.getCanalName(),
                    R.drawable.ic_action_refresh_dark));
        }
        dbm.getInstance().disconnect();

        // A custom ListView is needed so the drawer can be notified when it's
        // scrolled. This is to update the position
        // of the arrow indicator.
        mList = new ListView(this);
        mAdapter = new MenuAdapter(this, items);
        mList.setAdapter(mAdapter);
        mList.setOnItemClickListener(mItemClickListener);

        mMenuDrawer.setMenuView(mList);
    }

    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        return null;
    }

    private AdapterView.OnItemClickListener mItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            mActivePosition = position;
            srcPath = list.get(position).getUrlCanal();
            VideoFragment.setSrc(srcPath);
            mPagerAdapter.notifyDataSetChanged();
            // mViewPager.setOffscreenPageLimit(TRIM_MEMORY_RUNNING_LOW);
            mMenuDrawer.setActiveView(view, position);
            mMenuDrawer.closeMenu();
        }
    };

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STATE_ACTIVE_POSITION, mActivePosition);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        savedInstanceState.getInt(STATE_ACTIVE_POSITION, mActivePosition);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mMenuDrawer.toggleMenu();
                return true;
            case UPDATE:
                new UpdateTVListTask(this).execute(URL, null, null);
                return true;
            case SETUP:
                startActivity(new Intent(this, SetupActivity.class));
                finish();
                return true;
            case EXIT:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        final int drawerState = mMenuDrawer.getDrawerState();
        if (drawerState == MenuDrawer.STATE_OPEN
                || drawerState == MenuDrawer.STATE_OPENING) {
            mMenuDrawer.closeMenu();
            return;
        }
        super.onBackPressed();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // TODO Auto-generated method stub
        menu.add(0, UPDATE, 0, string.update);
        menu.add(0, SETUP, 0, string.setup);
        menu.add(0, EXIT, 0, string.exit);
        return super.onCreateOptionsMenu(menu);
    }

    void unlockMulticast() {
        WifiManager wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        MulticastLock multicastLock = wifi.createMulticastLock("multicastLock");
        multicastLock.setReferenceCounted(true);
        multicastLock.acquire();
        if (multicastLock != null) {
            multicastLock.release();
            multicastLock = null;
        }
    }

    public void fullScrean() {
        if (Build.VERSION.SDK_INT < 16) {

            requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }


    }

    @Override
    public void callBack() {
        // TODO Auto-generated method stub
        createMenu();
        mPagerAdapter.notifyDataSetChanged();
    }
}
