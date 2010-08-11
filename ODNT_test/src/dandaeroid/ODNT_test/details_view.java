package dandaeroid.ODNT_test;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class details_view extends Activity {
	Intent _intent;
	Global _global;
	Question q;
	TextView _text;
	Button _bt_ok;
	Button _bt_edit_question;
	String st;
	int position;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.details_view);
		_intent = getIntent();
		_global = (Global) getApplication();
		_text = (TextView) findViewById(R.id.TextView01);
		_bt_ok = (Button) findViewById(R.id.Button01);
		_bt_edit_question = (Button) findViewById(R.id.Button02);
		position = _intent.getIntExtra("position", 0);

		q = _global._alQuestion.get(position);
		st = " " + q._keyword + " " + q._cate01 + q._cate02 + " " + q._answer
				+ " " + q._solution + " " + q._rate;
		_text.setText(st.toString());
		// _text.setText(st.toString());

		_bt_ok.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				finish();
			}
		});
		_bt_edit_question.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(details_view.this,
						edit_question.class);
				intent.putExtra("position", position);
				startActivityForResult(intent, 0);
				finish();
			}
		});

		// TODO Auto-generated method stub
	}

}
