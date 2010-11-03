package dandaeroid.ODNT_minor;

import java.util.ArrayList;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

public class view_question extends ListActivity {
	Global global;
	Button button01;
	ArrayList<List> list;
	ArrayList<String> n_list01;
	ArrayList<String> n_list02;
	ArrayList<Integer> posi;
	ListAdapter m_adapter;
	Spinner sp01;
	Spinner sp02;
	int pos1;
	int pos2;
	EditText edittext01;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_question_1);

		global = (Global) getApplication();
		button01 = (Button) findViewById(R.id.Button01);
		sp01 = (Spinner) findViewById(R.id.Spinner01);
		sp02 = (Spinner) findViewById(R.id.Spinner02);
		list = new ArrayList<List>();
		n_list01 = new ArrayList<String>();
		n_list02 = new ArrayList<String>();
		posi = new ArrayList<Integer>();
		edittext01 = (EditText) findViewById(R.id.EditText01);
		m_adapter = new ListAdapter(this, R.layout.view_question_2, list);

		setListAdapter(m_adapter);
		refreshList();

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
				refreshList();
			}
		});
	}

	private class ListAdapter extends ArrayAdapter<List> {

		private ArrayList<List> items;

		public ListAdapter(Context context, int textViewResourceId,
				ArrayList<List> items) {
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
			List q = items.get(position);
			if (q != null) {
				TextView tt = (TextView) v.findViewById(R.id.toptext);
				TextView bt_01 = (TextView) v.findViewById(R.id.bottomtext01);
				TextView bt_02 = (TextView) v.findViewById(R.id.bottomtext02);
				if (tt != null) {
					tt.setText(q._keyword);
				}
				if (bt_01 != null) {
					bt_01.setText(q._cate01);
				}
				if (bt_02 != null) {
					bt_02.setText(q._cate02);
				}
			}
			return v;
		}
	}

	
	
	
	void refreshList() {
		
		list.clear();
		posi.clear();

		int controller = 0;
		if (pos1 == 0 && pos2 == 0)
			controller = 1;
		else if (pos1 != 0 && pos2 == 0)
			controller = 2;
		else if (pos1 == 0 && pos2 != 0)
			controller = 3;
		else if (pos1 != 0 && pos2 != 0)
			controller = 4;

		Cursor _cursor = global._db.rawQuery("SELECT * FROM question", null);
		if (_cursor.moveToFirst())
			do {
				switch (controller) {
				case 1:
					if (edittext01.getText().toString() == ""
							|| _cursor.getString(1).matches(
									".*" + edittext01.getText().toString()
											+ ".*")
							|| _cursor.getString(5).matches(
									".*" + edittext01.getText().toString()
											+ ".*"))
						list.add(new List(_cursor.getInt(0), _cursor
								.getString(1), _cursor.getString(2), _cursor
								.getString(3)));
					break;
				case 2:
					if (global._alCate01.get(pos1 - 1).equals(
							_cursor.getString(2)))
						if (edittext01.getText().toString() == ""
								|| _cursor.getString(1).matches(
										".*" + edittext01.getText().toString()
												+ ".*")
								|| _cursor.getString(5).matches(
										".*" + edittext01.getText().toString()
												+ ".*"))
							list.add(new List(_cursor.getInt(0), _cursor
									.getString(1), _cursor.getString(2),
									_cursor.getString(3)));
					break;
				case 3:
					if (global._alCate02.get(pos2 - 1).equals(
							_cursor.getString(3)))
						if (edittext01.getText().toString() == ""
								|| _cursor.getString(1).matches(
										".*" + edittext01.getText().toString()
												+ ".*")
								|| _cursor.getString(5).matches(
										".*" + edittext01.getText().toString()
												+ ".*"))
							list.add(new List(_cursor.getInt(0), _cursor
									.getString(1), _cursor.getString(2),
									_cursor.getString(3)));
					break;
				case 4:
					if (global._alCate01.get(pos1 - 1).equals(
							_cursor.getString(2)))
						if (global._alCate02.get(pos2 - 1).equals(
								_cursor.getString(3)))
							if (edittext01.getText().toString() == ""
									|| _cursor.getString(1).matches(
											".*"
													+ edittext01.getText()
															.toString() + ".*")
									|| _cursor.getString(5).matches(
											".*"
													+ edittext01.getText()
															.toString() + ".*"))
								list.add(new List(_cursor.getInt(0), _cursor
										.getString(1), _cursor.getString(2),
										_cursor.getString(3)));
					break;
				}
			} while (_cursor.moveToNext());
		_cursor.close();

		m_adapter.notifyDataSetChanged();
	}

	
	
	
	
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Intent intent = new Intent(view_question.this, details_view.class);
		intent.putExtra("id", list.get(position)._id);
		startActivity(intent);
	}
	
	@Override
	public void onRestart() {
		super.onRestart();
		refreshList();
	}
}
