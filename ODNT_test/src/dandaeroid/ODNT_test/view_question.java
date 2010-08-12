package dandaeroid.ODNT_test;

import java.util.ArrayList;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

public class view_question extends ListActivity {
	Global global;
	Button button01;
	ArrayList<Question> list;
	QuestionAdapter m_adapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_question_1);

		global = (Global) getApplication();
		button01 = (Button) findViewById(R.id.Button01);
		Spinner sp = (Spinner) findViewById(R.id.Spinner01);
		list = new ArrayList<Question>();

		ArrayAdapter<String> aa = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, global._alCate01);

		aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		sp.setPrompt("category");
		sp.setAdapter(aa);

		m_adapter = new QuestionAdapter(this, R.layout.view_question_2, list);

		setListAdapter(m_adapter);

		sp.setOnItemSelectedListener(new OnItemSelectedListener() {

			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				list.clear();
				for (int i = 0; i < global._alQuestion.size(); i++) {
					if (global._alQuestion.get(i)._cate01 == position) {
						list.add(global._alQuestion.get(i));
					}
				}
				m_adapter.notifyDataSetChanged();
			}

			public void onNothingSelected(AdapterView<?> args0) {

			}
		});

		button01.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(view_question.this, category.class);
				startActivity(intent);
			}
		});
	}

	private class QuestionAdapter extends ArrayAdapter<Question> {
		private ArrayList<Question> items;

		public QuestionAdapter(Context context, int textViewResourceId,
				ArrayList<Question> items) {
			super(context, textViewResourceId, items);
			this.items = items;

		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View v = convertView;
			if (v == null) {
				LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				v = vi.inflate(R.layout.view_question_2, null);
			}
			Question q = items.get(position);
			if (q != null) {
				TextView tt = (TextView) v.findViewById(R.id.toptext);
				TextView bt_01 = (TextView) v.findViewById(R.id.bottomtext01);
				TextView bt_02 = (TextView) v.findViewById(R.id.bottomtext02);
				if (tt != null) {
					tt.setText(q._keyword);
				}
				if (bt_01 != null) {
					bt_01.setText(global._alCate01.get(q._cate01));
				}
				if (bt_02 != null) {
					bt_02.setText(global._alCate02.get(q._cate02));
				}
			}
			return v;
		}
	}

	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Intent intent = new Intent(view_question.this, details_view.class);
		intent.putExtra("position", position);
		startActivity(intent);
	}
}