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
	ArrayList<String> n_list01;
	ArrayList<String> n_list02;
	ArrayList<Integer> posi;
	QuestionAdapter m_adapter;
	Spinner sp01;
	Spinner sp02;
	int pos1;
	int pos2;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_question_1);

		global = (Global) getApplication();
		button01 = (Button) findViewById(R.id.Button01);
		sp01 = (Spinner) findViewById(R.id.Spinner01);
		sp02 = (Spinner) findViewById(R.id.Spinner02);
		list = new ArrayList<Question>();
		n_list01 = new ArrayList<String>();
		n_list02 = new ArrayList<String>();
		posi = new ArrayList<Integer>();

		n_list01.add("전체");

		for (int j = 0; j < global._alCate01.size(); j++) {
			n_list01.add(global._alCate01.get(j));
		}

		n_list02.add("전체");

		for (int k = 0; k < global._alCate02.size(); k++) {
			n_list02.add(global._alCate02.get(k));
		}

		ArrayAdapter<String> aa = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, n_list01);

		ArrayAdapter<String> aa1 = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, n_list02);

		aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		sp01.setPrompt("category");
		sp01.setAdapter(aa);

		aa1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		sp02.setPrompt("category");
		sp02.setAdapter(aa1);

		m_adapter = new QuestionAdapter(this, R.layout.view_question_2, list);

		setListAdapter(m_adapter);

		sp01.setOnItemSelectedListener(new OnItemSelectedListener() {

			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				pos1 = position;
				refreshList();
			}

			public void onNothingSelected(AdapterView<?> args0) {

			}
		});

		sp02.setOnItemSelectedListener(new OnItemSelectedListener() {

			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				pos2 = position;
				refreshList();
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

	void refreshList() {
		list.clear();
		posi.clear();
		for (int i = 0; i < global._alQuestion.size(); i++) {
			if (pos1 == 0 && pos2 == 0) {
				list.add(global._alQuestion.get(i));
				posi.add(i);
			} else if (pos1 == 0 && pos2 != 0) {
				if (global._alQuestion.get(i)._cate02 == pos2 - 1) {
					list.add(global._alQuestion.get(i));
					posi.add(i);
				}
			} else if (pos1 != 0 && pos2 == 0) {
				if (global._alQuestion.get(i)._cate01 == pos1 - 1) {
					list.add(global._alQuestion.get(i));
					posi.add(i);
				}
			} else {
				if (global._alQuestion.get(i)._cate01 == pos1 - 1
						&& global._alQuestion.get(i)._cate02 == pos2 - 1) {
					list.add(global._alQuestion.get(i));
					posi.add(i);
				}
			}
			m_adapter.notifyDataSetChanged();
		}
	}

	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Intent intent = new Intent(view_question.this, details_view.class);
		intent.putExtra("position", posi.get(position));
		startActivity(intent);
	}
}