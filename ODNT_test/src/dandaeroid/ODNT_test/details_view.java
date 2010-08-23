package dandaeroid.ODNT_test;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

public class details_view extends Activity {
	Intent _intent;
	Global _global;
	Question q;
	TextView _text;
	TextView _m_text;
	ImageButton _bt_before;
	ImageButton _bt_after;
	ImageButton _bt_ok;
	ImageButton _bt_edit_question;
	String _st;
	String _message;
	int position;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.details_view);
		_intent = getIntent();
		_global = (Global) getApplication();
		_text = (TextView) findViewById(R.id.TextView01);
		_m_text = (TextView) findViewById(R.id.TextView02);
		_bt_before = (ImageButton) findViewById(R.id.ImageButton01);
		_bt_after = (ImageButton) findViewById(R.id.ImageButton02);
		_bt_ok = (ImageButton) findViewById(R.id.ImageButton03);
		_bt_edit_question = (ImageButton) findViewById(R.id.ImageButton04);
		position = _intent.getIntExtra("position", 0);

		if (_global._alQuestion.isEmpty()) {
			_st = "";
			_message = "It is Empty ";
			_text.setText(_message.toString());
		} else {
			q = _global._alQuestion.get(position);
			_Print_Question();
			_text.setText(_st.toString());

			_bt_before.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					// TODO Auto-generated method stub

					if (position > 0) {
						position = position - 1;
						
						q = _global._alQuestion.get(position);
						_message = "";
						_Print_Question();
						_text.setText(_st.toString());
					} else {
						_message = "It is first page & size is "
						+ _global._alQuestion.size() + "\n";
						_Print_Question();
						_text.setText(_st.toString());
						_m_text.setText(_message.toString());
					}
				}
			});

			_bt_after.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if (position < (_global._alQuestion.size() - 1)) {
						position = position + 1;
						
						q = _global._alQuestion.get(position);
						_message = "";
						_Print_Question();
						_text.setText(_st.toString());
						_m_text.setText(_message.toString());
					} else {
						_message = "It is final page & size is "
								+ _global._alQuestion.size() + "\n";
						_Print_Question();
						_text.setText(_st.toString());
						_m_text.setText(_message.toString());
					}
				}
			});
		}

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

	public void  _Print_Question() {
		_st ="Page: " + position + "\n" + 
			"keyword: " + q._keyword + "\n" + 
			" q._cate01: " + q._cate01+ "\n" + 
			"q._cate02: " + q._cate02 + "\n" + 
			" q._answer: "	+ q._answer + "\n" + 
			" q._rate: " + q._rate + "\n" + 
			" q._solution: " + q._solution + "\n";
		
	}
}


