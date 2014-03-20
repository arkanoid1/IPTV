package by.makarov.video;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import by.makarov.video.R.string;
import by.makarov.video.task.UpdateTVListTask;

public class SetupActivity extends Activity implements OnClickListener ,Update{
	private SharedPreferences pref;
    private EditText et;
    private Button btnSave;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.setup);
		
		et = (EditText) findViewById(R.id.etList);
		btnSave = (Button)findViewById(R.id.btnSave);
		btnSave.setOnClickListener(this);
		loadConfig();
		if((et.getText()).toString().equals("")){
			et.setText(string.default_url_chanel);
		}
	}

	 
	@Override
	public void onClick(View v) {
		saveConfig();
		new UpdateTVListTask(this).execute((et.getText()).toString(),null,null);
	}

	private void loadConfig() {
		pref = getSharedPreferences("iptv",MODE_PRIVATE);
		String s = pref.getString("urlIptv", "");
		et.setText(s);

	}


    void saveConfig() {
		pref = getSharedPreferences("iptv",MODE_PRIVATE);
		Editor ed = pref.edit();
		ed.putString("urlIptv", et.getText().toString());
		ed.commit();

	}


	@Override
	public void callBack() {
		Toast.makeText(this, string.ReadyUpdate , Toast.LENGTH_SHORT).show();
		startActivity(new Intent(this, MainActivity.class));
		finish();
	}
}
