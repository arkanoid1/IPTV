package by.makarov.video.task;

import android.os.AsyncTask;
import android.os.StrictMode;
import by.makarov.video.Update;
import by.makarov.video.database.DBManager;
import by.makarov.video.entitys.ChanelM3U;
import by.makarov.video.tvlist.ParserM3U;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class UpdateTVListTask extends AsyncTask<String, Void, Void> {
    private DBManager dbm;
    private URL url;
    private WeakReference<Update> wrC;


    public UpdateTVListTask(Update c) {
        // TODO Auto-generated constructor stub
        wrC = new WeakReference<Update>(c);

    }

    @Override
    protected Void doInBackground(String... params) {

        // TODO Auto-generated method stub
//		dbm.createInstance(this);
        dbm.getInstance().connect();
        dbm.getInstance().removeAll(); //delete all chanel

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        ParserM3U pars = new ParserM3U();

        try {
            url = new URL(params[0].toString());
            ArrayList<ChanelM3U> list = pars.pars(url);

            for (ChanelM3U x : list) {
                dbm.getInstance().addChanel(x);
            }

        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        dbm.getInstance().disconnect();
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        // TODO Auto-generated method stub
        wrC.get().callBack();
        super.onPostExecute(result);
    }

}
