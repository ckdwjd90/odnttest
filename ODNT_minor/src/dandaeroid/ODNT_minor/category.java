package dandaeroid.ODNT_minor;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class category extends Activity {
	Button _button01;
	Button _button02;
	Button _button03;
	Button _button04;
	Button _button05;
	Button _button06;
	Button _button07;

	Spinner _spinner01;
	Spinner _spinner02;
	EditText _edittext01;
	EditText _edittext02;
	TextView _textview01;
	Global _ppp;
	Question _qqq;
	ArrayAdapter<String> _adapter01;
	ArrayAdapter<String> _adapter02;

	Intent intent;
	int forposition;
	int forposition2;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.category1);

		_ppp = (Global) getApplication();
		_button01 = (Button) findViewById(R.id.Button01);
		_button02 = (Button) findViewById(R.id.Button02);
		_button03 = (Button) findViewById(R.id.Button03);
		_button04 = (Button) findViewById(R.id.Button04);
		_button05 = (Button) findViewById(R.id.Button05);
		_button06 = (Button) findViewById(R.id.Button06);
		_button07 = (Button) findViewById(R.id.Button07);
		_spinner01 = (Spinner) findViewById(R.id.Spinner01);
		_spinner02 = (Spinner) findViewById(R.id.Spinner02);
		_edittext01 = (EditText) findViewById(R.id.EditText01);
		_edittext02 = (EditText) findViewById(R.id.EditText02);
		_textview01 = (TextView) findViewById(R.id.TextView01);
		intent = getIntent();

		_adapter01 = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, _ppp._alCate01);
		_adapter01
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		_spinner01.setAdapter(_adapter01);
		_spinner01.setPrompt("Category1");
		_adapter02 = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, _ppp._alCate02);
		_adapter02
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		_spinner02.setAdapter(_adapter02);
		_spinner02.setPrompt("Category2");

		_spinner01.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {

				forposition = position;
				if (forposition != 0) {
					_edittext01.setText(_ppp._alCate01.get(forposition));
				}
			}

			public void onNothingSelected(AdapterView<?> args0) {
			}
		});
		_spinner02.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {

				forposition2 = position;
				if (forposition2 != 0) {
					_edittext02.setText(_ppp._alCate02.get(forposition2));
				}
			}

			public void onNothingSelected(AdapterView<?> args0) {
			}
		});

		_button01.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				if (forposition == 0) {
					Toast.makeText(category.this, "�߸� �����߾��, �ٽ��غ���",
							Toast.LENGTH_SHORT).show();
				} else {
					_ppp._db.execSQL("UPDATE cate01 SET label ='"
							+ _edittext01.getText().toString()
							+ "' WHERE label = '"
							+ _ppp._alCate01.get(forposition2) + "'");
					_ppp._alCate01.set(forposition, _edittext01.getText()
							.toString());
					// _ppp._alCate01Edited.set(forposition,
					// _edittext01.getText()
					// .toString());
					_spinner01.setAdapter(_adapter01);

					Toast.makeText(category.this, "���� �Ǿ����ϴ�",
							Toast.LENGTH_SHORT).show();

					_edittext01.setText("");
				}
			}

		});

		_button03.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if (forposition2 == 0) {
					Toast.makeText(category.this, "�߸� �����߾��, �ٽ��غ���",
							Toast.LENGTH_SHORT).show();
				} else {
					_ppp._db.execSQL("UPDATE cate02 SET label ='"
							+ _edittext02.getText().toString()
							+ "' WHERE label = '"
							+ _ppp._alCate02.get(forposition2) + "'");
					_ppp._alCate02.set(forposition2, _edittext02.getText()
							.toString());

					// _ppp._alCate02Edited.set(forposition2, _edittext02
					// .getText().toString());
					_spinner02.setAdapter(_adapter02);

					Toast.makeText(category.this, "���� �Ǿ����ϴ�",
							Toast.LENGTH_SHORT).show();

					_edittext02.setText("");
				}
			}
		});

		_button02.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				int check = 0;
				for (int q = 0; q < _ppp._alCate01.size(); q++) {
					if (_ppp._alCate01.get(q).equals(
							_edittext01.getText().toString())) {
						Toast.makeText(category.this, "�ߺ��� ī�װ��׿�, �ٽ��غ���",
								Toast.LENGTH_SHORT).show();
						check = 1;
					}
				}

				if (_edittext01.getText().toString().length() == 0) {
					Toast.makeText(category.this, "�����Դϴ�, �ٽ��غ���",
							Toast.LENGTH_SHORT).show();
				} else {
					if (check != 1) {
						_ppp._alCate01.add(_edittext01.getText().toString());
						// _ppp._alCate01Edited.add(_edittext01.getText()
						// .toString());
						// _ppp._alCate01Position.add(_ppp._alCate01.size() -
						// 1);

						ContentValues _cvInsert = new ContentValues();
						_cvInsert
								.put("label", _edittext01.getText().toString());
						_ppp._db.insert("cate01", null, _cvInsert);

						_spinner01.setAdapter(_adapter01);

						Toast.makeText(category.this, "�߰� �Ǿ����ϴ�",
								Toast.LENGTH_SHORT).show();

						_edittext01.setText("");

					}
				}

			}
		});

		_button04.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				for (int q = 0; q < _ppp._alCate02.size(); q++) {
					if (_ppp._alCate02.get(q).equals(
							_edittext02.getText().toString())) {
						Toast.makeText(category.this, "�ߺ��� ī�װ��׿�, �ٽ��غ���",
								Toast.LENGTH_SHORT).show();

					}
				}
				if (_edittext02.getText().toString().length() != 0) {
					_ppp._alCate02.add(_edittext02.getText().toString());
					// _ppp._alCate02Edited.add(_edittext02.getText().toString());
					// _ppp._alCate02Position.add(_ppp._alCate02.size() - 1);

					ContentValues _cvInsert = new ContentValues();
					_cvInsert.put("label", _edittext02.getText().toString());
					_ppp._db.insert("cate02", null, _cvInsert);

					Toast.makeText(category.this, "�߰� �Ǿ����ϴ�",
							Toast.LENGTH_SHORT).show();

					_spinner02.setAdapter(_adapter02);
					_edittext02.setText("");
				} else
					Toast.makeText(category.this, "�����Դϴ�, �ٽ��غ���",
							Toast.LENGTH_SHORT).show();

			}
		});

		_button05.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				setResult(RESULT_OK, intent);
				finish();
			}
		});

		_button06.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if (forposition == 0) {
					Toast.makeText(category.this, "�߸� �����߾��, �ٽ��غ���",
							Toast.LENGTH_SHORT).show();
				}
				DialogSimple();

			}
		});

		_button07.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if (forposition2 == 0) {
					Toast.makeText(category.this, "�߸� �����߾��, �ٽ��غ���",
							Toast.LENGTH_SHORT).show();
				}
				DialogSimple2();
			}
		});
		// TODO Auto-generated method stub
	}

	private void DialogSimple() {
		AlertDialog.Builder alt_bld = new AlertDialog.Builder(this);
		alt_bld.setMessage("������ ����ðھ�� ?")
				.setCancelable(false)
				.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {

							public void onClick(DialogInterface dialog, int id) {

								// Action for 'Yes' Button
								if (forposition > 0) {
									_ppp._db.execSQL("DELETE FROM cate01 WHERE label ='"
											+ _ppp._alCate01.get(forposition)
											+ "'");
									_ppp._alCate01.remove(forposition);

									_spinner01.setAdapter(_adapter01);

									Toast.makeText(category.this, "���� �Ǿ����ϴ�",
											Toast.LENGTH_SHORT).show();

									_edittext01.setText("");
								}

							}

						}).setNegativeButton("No",

				new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int id) {

						// Action for 'NO' Button

						dialog.cancel();
					}
				});
		AlertDialog alert = alt_bld.create();
		// Title for AlertDialog
		alert.setTitle(_edittext01.getText().toString() + "��");
		// Icon for AlertDialog
		alert.setIcon(R.drawable.icon);
		alert.show();
	}

	private void DialogSimple2() {
		AlertDialog.Builder alt_bld2 = new AlertDialog.Builder(this);
		alt_bld2.setMessage("������ ����ðھ�� ?")
				.setCancelable(false)
				.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {

								// Action for 'Yes' Button
								if (forposition2 > 0) {
									_ppp._db.execSQL("DELETE FROM cate02 WHERE label ='"
											+ _ppp._alCate02.get(forposition2)
											+ "'");
									_ppp._alCate02.remove(forposition2);

									_spinner02.setAdapter(_adapter02);

									Toast.makeText(category.this, "���� �Ǿ����ϴ�",
											Toast.LENGTH_SHORT).show();

									_edittext02.setText("");
								}

							}

						}).setNegativeButton("No",

				new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int id) {

						// Action for 'NO' Button

						dialog.cancel();
					}
				});
		AlertDialog alert = alt_bld2.create();
		// Title for AlertDialog
		alert.setTitle(_edittext02.getText().toString() + "��");
		// Icon for AlertDialog
		alert.setIcon(R.drawable.icon);
		alert.show();
	}

}