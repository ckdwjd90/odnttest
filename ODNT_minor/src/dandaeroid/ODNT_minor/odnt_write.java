package dandaeroid.ODNT_minor;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
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
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class odnt_write extends Activity {
	ImageView _ivPhoto;
	EditText _etSubject;
	EditText _etContext;
	Button _btSubmit;
	Intent intent;

	Global _global;
	int _id;
	Cursor _cursor;

	String lineEnd = "\r\n";
	String twoHyphens = "--";
	String boundary = "*****";
	String _filename;

	FileInputStream mFileInputStream = null;
	URL connectUrl = null;
	ProgressDialog _progressDialog;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.odnt_write);

		_ivPhoto = (ImageView) findViewById(R.id.ImageView01);
		_etSubject = (EditText) findViewById(R.id.EditText01);
		_etContext = (EditText) findViewById(R.id.EditText02);
		_btSubmit = (Button) findViewById(R.id.Button01);

		_global = (Global) getApplication();
		intent = getIntent();
		_id = intent.getIntExtra("id", 0);

		_cursor = _global._db.rawQuery(
				"SELECT * FROM question WHERE id=" + _id, null);
		_cursor.moveToFirst();
		String path = Environment.getExternalStorageDirectory()
				.getAbsolutePath();
		path += "/odnt/" + _cursor.getString(7);
		Bitmap bitmap = BitmapFactory.decodeFile(path);
		_ivPhoto.setImageBitmap(bitmap);

		_btSubmit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
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

	public void DoFileUpload(String filePath) throws IOException {
		Log.d("Test", "file path = " + filePath);
		HttpFileUpload("http://220.149.236.36:8080/DB2Connection/up.jsp", "",
				filePath);
	}

	public void HttpFileUpload(String urlString, String params, String fileName) {
		try {

			mFileInputStream = new FileInputStream(fileName);
			connectUrl = new URL(urlString);
			Log.d("Test", "mFileInputStream  is " + mFileInputStream);

			// open connection

			HttpURLConnection conn = (HttpURLConnection) connectUrl
					.openConnection();
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setUseCaches(false);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Connection", "Keep-Alive");
			conn.setRequestProperty("Content-Type",
					"multipart/form-data;boundary=" + boundary);

			// write data
			DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
			dos.writeBytes(twoHyphens + boundary + lineEnd);
			dos.writeBytes("Content-Disposition: form-data; name=\"uploadedfile\";filename=\""
					+ fileName + "\"" + lineEnd);
			dos.writeBytes(lineEnd);

			int bytesAvailable = mFileInputStream.available();
			int maxBufferSize = 1024;
			int bufferSize = Math.min(bytesAvailable, maxBufferSize);

			byte[] buffer = new byte[bufferSize];
			int bytesRead = mFileInputStream.read(buffer, 0, bufferSize);

			Log.d("Test", "image byte is " + bytesRead);

			// read image
			while (bytesRead > 0) {
				dos.write(buffer, 0, bufferSize);
				bytesAvailable = mFileInputStream.available();
				bufferSize = Math.min(bytesAvailable, maxBufferSize);
				bytesRead = mFileInputStream.read(buffer, 0, bufferSize);
			}

			dos.writeBytes(lineEnd);
			dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

			// close streams
			Log.e("Test", "File is written");
			mFileInputStream.close();
			dos.flush(); // finish upload...

			// get response
			int ch;
			InputStream is = conn.getInputStream();
			StringBuffer b = new StringBuffer();
			int counter = 0;
			while ((ch = is.read()) != -1) {
				if (counter > 3)
					b.append((char) ch);
				counter++;
			}
			_filename = b.toString();
			Log.e("FileName", "<" + _filename + ">");
			dos.close();
			
		} catch (Exception e) {
			Log.d("Test", "exception " + e.getMessage());
			// TODO: handle exception
		}
	}
	
	Handler _handler = new Handler() {
		public void handleMessage(Message msg) {
			_progressDialog.dismiss();
			switch (msg.what) {
			case 10:
				Toast.makeText(odnt_write.this, "질문 전송에 성공했습니다.", Toast.LENGTH_SHORT)
						.show();
				finish();
				break;
			case 11:
				Toast.makeText(odnt_write.this, "질문 전송에 실패했습니다.", Toast.LENGTH_SHORT)
						.show();
				finish();
				break;
			}
		}
	};

	class _thSubmit extends Thread {
		public void run() {
			try {
				DoFileUpload("/sdcard/odnt/" + _cursor.getString(7));
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			try {
				HttpClient client = new DefaultHttpClient();
				String postURL = "http://220.149.236.36:8080/DB2Connection/bridge.jsp";
				HttpPost post = new HttpPost(postURL);
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("subject", _etSubject
						.getText().toString()));
				params.add(new BasicNameValuePair("context", _etContext
						.getText().toString()));
				params.add(new BasicNameValuePair("filename", _filename));
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
