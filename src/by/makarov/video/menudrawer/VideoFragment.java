package by.makarov.video.menudrawer;

import android.app.ActionBar;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.VideoView;

public class VideoFragment extends Fragment {
    private static String srcPath;
    private final static String CURRENT_POSITION = "currentPosition";

    public static void setSrc(String src) {
        srcPath = src;
    }

    FrameLayout frameLayout;
    VideoView myVideoView;
    private long mPosition = 0;

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
        mPosition = myVideoView.getCurrentPosition();
        myVideoView.stopPlayback();
        super.onPause();
    }

    @Override
    public void onResume() {
        if (mPosition > 0) {
            myVideoView.seekTo(mPosition);
            mPosition = 0;
        }

        super.onResume();
        myVideoView.start();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        // TODO Auto-generated method stub
        super.onConfigurationChanged(newConfig);
    }
}
