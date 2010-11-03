package dandaeroid.ODNT_minor;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class odnt_net_answerview extends Activity {
	TextView _tvSubject;
	ImageView _ivPhoto;
	TextView _tvContext;
	TextView _tvComment;
	EditText _etComment;
	Button _btDownload;
	Button _btSubmit;

	Intent intent;
	Global _global;
	String _comment;
	int _id;
	
	ProgressDialog _progressDialog;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.odnt_net_answerview);

		intent = getIntent();
		_global = (Global) getApplication();

		_tvSubject = (TextView) findViewById(R.id.TextView01);
		_ivPhoto = (ImageView) findViewById(R.id.ImageView01);
		_tvContext = (TextView) findViewById(R.id.TextView02);
		_tvComment = (TextView) findViewById(R.id.TextView03);
		_etComment = (EditText) findViewById(R.id.EditText01);
		_btDownload = (Button) findViewById(R.id.Button01);
		_btSubmit = (Button) findViewById(R.id.Button02);

		_id = intent.getIntExtra("id", 0);
		_tvSubject.setText(intent.getStringExtra("subject"));
		_tvContext.setText(intent.getStringExtra("context"));
		_comment = intent.getStringExtra("comment");
		if (_comment.equals("null"))
			_comment = "";
		_tvComment.setText(_comment);
		_ivPhoto.setImageBitmap(_global._bitmap);

		_btDownload.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(odnt_net_answerview.this,
						question_reg.class);
				startActivity(intent);
			}
		});

		_btSubmit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				_comment = _etComment.getText().toString();
				
				_progressDialog = new ProgressDialog(v.getContext());
				_progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
				_progressDialog.setTitle("");
				_progressDialog.setMessage("서버로 전송중..");
				_progressDialog.setCancelable(true);
				_progressDialog.show();
				
				_thSubmit _thSubmit = new _thSubmit();
				_thSubmit.setDaemon(true);
				_thSubmit.start();
			}
		});
	}
	
	
	Handler _handler = new Handler() {
		public void handleMessage(Message msg) {
			_progressDialog.dismiss();
			switch (msg.what) {
			case 10:
				Toast.makeText(odnt_net_answerview.this, "의견 전송에 성공했습니다.", Toast.LENGTH_SHORT)
						.show();
				finish();
				break;
			case 11:
				Toast.makeText(odnt_net_answerview.this, "의견 전송에 실패했습니다.", Toast.LENGTH_SHORT)
						.show();
				finish();
				break;
			}
		}
	};
	
	class _thSubmit extends Thread {
		public void run() {
			try {
				HttpClient client = new DefaultHttpClient();
				String postURL = "http://220.149.236.36:8080/DB2Connection/bridge2.jsp";
				HttpPost post = new HttpPost(postURL);
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("id", ""+_id));
				params.add(new BasicNameValuePair("comment", _comment));
				UrlEncodedFormEntity ent = new UrlEncodedFormEntity(params,
						HTTP.UTF_8);
				post.setEntity(ent);
				HttpResponse responsePOST = client.execute(post);
				HttpEntity resEntity = responsePOST.getEntity();
				if (resEntity != null)
					Log.w("RESPONSE", EntityUtils.toString(resEntity));
				
				_handler.sendEmptyMessage(10);
			} catch (Exception e) {
				e.printStackTrace();
				_handler.sendEmptyMessage(11);
			}
		}
	}
}
