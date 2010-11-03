package dandaeroid.ODNT_minor;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class odnt_net_listview extends ListActivity {

	Button _btPrevPage;
	Button _btNextPage;
	TextView _tvPageNumber;

	ProgressDialog _progressDialog;
	ListAdapter _adapter;
	Global _global;

	int _pageNumber;
	String _outputBuffer;
	int _position;
	Intent _intent;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.odnt_net_listview);

		_btPrevPage = (Button) findViewById(R.id.Button01);
		_btPrevPage.setEnabled(false);
		_btNextPage = (Button) findViewById(R.id.Button02);
		_btNextPage.setEnabled(false);
		_tvPageNumber = (TextView) findViewById(R.id.TextView02);
		_global = (Global) getApplication();

		if (_global._alBoardList.size() > 10) {
			_global._alBoardList.remove(10);
			_btNextPage.setEnabled(true);
		}

		_pageNumber = 1;

		_tvPageNumber.setText("Page " + _pageNumber);

		_adapter = new ListAdapter(this, R.layout.odnt_net_listview,
				_global._alBoardList);
		setListAdapter(_adapter);

		_btNextPage.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				_btNextPage.setEnabled(false);
				_btPrevPage.setEnabled(true);
				_pageNumber++;

				_progressDialog = new ProgressDialog(v.getContext());
				_progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
				_progressDialog.setTitle("");
				_progressDialog.setMessage("서버 연결중..");
				_progressDialog.setCancelable(true);
				_progressDialog.show();

				_thParsing _thParsing = new _thParsing();
				_thParsing.setDaemon(true);
				_thParsing.start();

				_tvPageNumber.setText("Page " + _pageNumber);
			}
		});

		_btPrevPage.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				_btNextPage.setEnabled(true);
				_pageNumber--;
				if (_pageNumber == 1)
					_btPrevPage.setEnabled(false);

				_progressDialog = new ProgressDialog(v.getContext());
				_progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
				_progressDialog.setTitle("");
				_progressDialog.setMessage("서버 연결중..");
				_progressDialog.setCancelable(true);
				_progressDialog.show();

				_thParsing _thParsing = new _thParsing();
				_thParsing.setDaemon(true);
				_thParsing.start();

				_tvPageNumber.setText("Page " + _pageNumber);
			}
		});
	}

	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);

		// Toast.makeText(odnt_net_listview.this,
		// _global._alBoardList.get(position)._subject, Toast.LENGTH_SHORT)
		// .show();

		_progressDialog = new ProgressDialog(v.getContext());
		_progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		_progressDialog.setTitle("");
		_progressDialog.setMessage("서버 연결중..");
		_progressDialog.setCancelable(true);
		_progressDialog.show();
		
		Log.v("DEBUG", "phase 00");

		_thParsing2 _thParsing2 = new _thParsing2();
		_thParsing2.setDaemon(true);
		_thParsing2.start();

		_position = position;
	}

	Handler _handler = new Handler() {
		public void handleMessage(Message msg) {
			_progressDialog.dismiss();
			switch (msg.what) {
			case 20:
				Toast.makeText(odnt_net_listview.this, "메세지 수신에 실패했습니다.",
						Toast.LENGTH_SHORT).show();
				break;
			case 21:
				Toast.makeText(odnt_net_listview.this, "JSON 파싱에 성공했습니다.",
						Toast.LENGTH_SHORT).show();

				_adapter.notifyDataSetChanged();

				if (_global._alBoardList.size() > 10) {
					_global._alBoardList.remove(10);
					_btNextPage.setEnabled(true);
				}
				break;
			case 30:
				break;
			case 31:
				startActivity(_intent);
				break;
			}
		}
	};

	private class ListAdapter extends ArrayAdapter<List2> {

		private ArrayList<List2> items;

		public ListAdapter(Context context, int textViewResourceId,
				ArrayList<List2> items) {
			super(context, textViewResourceId, items);
			this.items = items;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View v = convertView;
			if (v == null) {
				LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				v = vi.inflate(R.layout.odnt_net_listview_2, null);
			}
			List2 q = items.get(position);
			if (q != null) {
				TextView tt = (TextView) v.findViewById(R.id.toptext);
				if (tt != null) {
					tt.setText(q._subject);
				}
			}
			return v;
		}
	}

	class _thParsing extends Thread {
		public void run() {
			_outputBuffer = new String();
			HttpGet httpget = new HttpGet(
					"http://220.149.236.36:8080/DB2Connection/JSONlist.jsp?list="
							+ _pageNumber);
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
					_global._alBoardList.add(new List2(order.getInt("no"),
							order.getString("subject")));
				}
			} catch (JSONException e) {
			}
			_handler.sendEmptyMessage(21);
		}
	}

	class _thParsing2 extends Thread {
		public void run() {
			Log.v("DEBUG", "phase 01");
			_outputBuffer = new String();
			Log.v("DEBUG", "phase 02");
			HttpGet httpget = new HttpGet(
					"http://220.149.236.36:8080/DB2Connection/JSONanswer.jsp?no="
							+ _global._alBoardList.get(_position)._no);
			Log.v("DEBUG", "phase 03");
			DefaultHttpClient client = new DefaultHttpClient();
			StringBuilder html = new StringBuilder();
			Log.v("DEBUG", "phase 04");
			try {
				HttpResponse response = client.execute(httpget);
				BufferedReader br = new BufferedReader(new InputStreamReader(
						response.getEntity().getContent()));
				Log.v("DEBUG", "phase 04a");
				for (;;) {
					String line = br.readLine();
					if (line == null)
						break;
					html.append(line + '\n');
				}
				Log.v("DEBUG", "phase 04b");
				br.close();
			} catch (Exception e) {
				_handler.sendEmptyMessage(30);
			}
			Log.v("DEBUG", "phase 04c");

			try {
				JSONArray ja = new JSONArray(html.toString());
				Log.v("DEBUG", "phase 05");
				JSONObject order = ja.getJSONObject(0);
				Log.v("DEBUG", "phase 06");

				String imageurl = "http://220.149.236.36:8080/DB2Connection/upload/"
						+ order.getString("filename");
				Log.v("DEBUG", "phase 07");
				try {
					InputStream is = new URL(imageurl).openStream();
					_global._bitmap = BitmapFactory.decodeStream(is);
					is.close();
				} catch (Exception e) {
					;
				}

				_intent = new Intent(odnt_net_listview.this,
						odnt_net_answerview.class);
				_intent.putExtra("id", _global._alBoardList.get(_position)._no);
				_intent.putExtra("subject", order.getString("subject"));
				_intent.putExtra("context", order.getString("context"));
				_intent.putExtra("comment", order.getString("comment"));
			} catch (JSONException e) {
			}
			_handler.sendEmptyMessage(31);
		}
	}
}
