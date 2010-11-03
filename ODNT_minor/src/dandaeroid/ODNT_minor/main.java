package dandaeroid.ODNT_minor;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.Toast;


public class main extends Activity {
	ImageButton _btQuestion_reg;
	ImageButton _btView_question;
	ImageButton _btODNTnet;
	
	ProgressDialog _progressDialog;
	
	String _outputBuffer;
	Global _global;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        _global = (Global)getApplication();
        
        _btQuestion_reg = (ImageButton)findViewById(R.id.ImageButton01);
        _btView_question = (ImageButton)findViewById(R.id.ImageButton02);
        _btODNTnet = (ImageButton)findViewById(R.id.ImageButton03);
        
        _btQuestion_reg.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(main.this, take_photo.class);
				startActivity(intent);
			}
        });
        
        _btView_question.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(main.this, view_question.class);
				startActivity(intent);
			}
        });
        
        _btODNTnet.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				_progressDialog = new ProgressDialog(v.getContext());
				_progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
				_progressDialog.setTitle("");
				_progressDialog.setMessage("서버 연결중..");
				_progressDialog.setCancelable(true);
				_progressDialog.show();
				
				_thParsing _thParsing = new _thParsing();
				_thParsing.setDaemon(true);
				_thParsing.start();
			}
        }); 

    }
    
    Handler _handler = new Handler() {
		public void handleMessage(Message msg) {
			_progressDialog.dismiss();
			switch (msg.what) {
			case 20:
				Toast.makeText(main.this, "메세지 수신에 실패했습니다.", Toast.LENGTH_SHORT)
						.show();
				break;
			case 21:
				Toast.makeText(main.this, "JSON 파싱에 성공했습니다.",
						Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(main.this, odnt_net_listview.class);
				startActivity(intent);
				break;
			}
		}
	};
    
    class _thParsing extends Thread {
		public void run() {
			_outputBuffer = new String();
			HttpGet httpget = new HttpGet(
					"http://220.149.236.36:8080/DB2Connection/JSONlist.jsp?list=1");
			DefaultHttpClient client = new DefaultHttpClient();
			StringBuilder html = new StringBuilder();
			try {
				HttpResponse response = client.execute(httpget);
				BufferedReader br = new BufferedReader(new InputStreamReader(
						response.getEntity().getContent()));
				for (;;) {
					String line = br.readLine();
					if (line == null)
						break;
					html.append(line + '\n');
				}
				br.close();
			} catch (Exception e) {
				_handler.sendEmptyMessage(20);
			}

			try {
				JSONArray ja = new JSONArray(html.toString());
				_global._alBoardList.clear();
				for (int i = 0; i < ja.length(); i++) {
					JSONObject order = ja.getJSONObject(i);
					_global._alBoardList.add(new List2(order.getInt("no"), order.getString("subject")));
				}
			} catch (JSONException e) {
			}
			_handler.sendEmptyMessage(21);
		}
	}
}