package by.makarov.video.menudrawer;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import io.vov.vitamio.utils.Log;
import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.VideoView;

public class VideoFragment extends Fragment {
    private static String srcPath;
    private final static String CURRENT_POSITION  = "currentPosition";

    public static void setSrc(String src) {
        srcPath = src;
    }

    FrameLayout frameLayout;
    VideoView myVideoView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setRetainInstance(true);
        frameLayout = new FrameLayout(getActivity());
        frameLayout.setLayoutParams(new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT, Gravity.CENTER));

        runVideo();

        return frameLayout;
    }

    private void runVideo() {
        myVideoView = new VideoView(getActivity());
        myVideoView.setKeepScreenOn(true);
        myVideoView.setLayoutParams(new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT, Gravity.CENTER));
        myVideoView.setMediaController(new MediaController(getActivity()));
        myVideoView.setBufferSize(4096);
        myVideoView.setX(1);
        myVideoView.setY(1);


        myVideoView.requestFocus();

        if (srcPath.length() > 1) {
            myVideoView.setVideoURI(Uri.parse(srcPath));
            myVideoView.start();
        }
        frameLayout.addView(myVideoView);
    }

    @Override
    public void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        SharedPreferences.Editor myPrefs = getActivity().getSharedPreferences(CURRENT_POSITION, Context.MODE_WORLD_WRITEABLE).edit();
        myPrefs.putLong(CURRENT_POSITION, myVideoView.getCurrentPosition());

        myPrefs.commit();
        myVideoView.pause();
    }

    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        if(myVideoView == null)
            return;
        SharedPreferences myPrefs = getActivity().getSharedPreferences(CURRENT_POSITION, Context.MODE_WORLD_READABLE);
        myVideoView.seekTo(myPrefs.getLong(CURRENT_POSITION, myVideoView.getCurrentPosition()));
//        myVideoView.start();

        myVideoView.resume();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        // TODO Auto-generated method stub
        super.onConfigurationChanged(newConfig);
    }
}
