package dandaeroid.ODNT_minor;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

public class question_edit extends Activity {
	ArrayList<Integer> _ALint1;
	ArrayList<Integer> _ALint2;
	ArrayList<String> _autostr;
	ArrayAdapter<String> _aa1;
	ArrayAdapter<String> _aa2;
	ArrayAdapter<Integer> _aa3;
	ArrayAdapter<Integer> _aa4;
	ArrayAdapter<String> _autoAdapter;
	AutoCompleteTextView _actvKeyword;
	ImageView _ivPhoto;
	EditText _etSolution;
	Button _btCategoryEdit;
	Button _btReg;
	Button _btCancle;
	Button _btRetake;
	Global _global;
	Cursor _cursor;

	int _sp1temp = 0;
	int _sp2temp = 0;
	int _sp3temp = 0;
	int _sp4temp = 0;
	int _id;
	Intent intent;
	String path;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.question_reg);

		_actvKeyword = (AutoCompleteTextView) findViewById(R.id.AutoCompleteTextView01);
		_ivPhoto = (ImageView) findViewById(R.id.ImageView01);

		_etSolution = (EditText) findViewById(R.id.EditText02);
		_btCategoryEdit = (Button) findViewById(R.id.Button01);
		_btReg = (Button) findViewById(R.id.Button02);
		_btReg.setText("문제를 수정합니다.");
		_btCancle = (Button) findViewById(R.id.Button03);
		_btCancle.setText("수정하지 않습니다.");
		_btRetake = (Button) findViewById(R.id.Button04);
		_global = (Global) getApplication();

		Spinner _spCate1 = (Spinner) findViewById(R.id.Spinner01);
		Spinner _spCate2 = (Spinner) findViewById(R.id.Spinner02);

		intent = getIntent();
		_id = intent.getIntExtra("id", 0);
		
		_cursor = _global._db.rawQuery(
				"SELECT * FROM question WHERE id=" + _id, null);
		_cursor.moveToFirst();
		path = Environment.getExternalStorageDirectory()
				.getAbsolutePath();
		path += "/odnt/"+_cursor.getString(7);
		Bitmap _bitmap = BitmapFactory.decodeFile(path);
		_ivPhoto.setImageBitmap(_bitmap);
		
		path = "/sdcard/odnt/" + _cursor.getString(7);

		_actvKeyword.setText(_cursor.getString(1));
		_etSolution.setText(_cursor.getString(5));

		_autoAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_dropdown_item_1line,
				_global._autoCTVstr);
		_actvKeyword.setAdapter(_autoAdapter);

		_aa1 = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, _global._alCate01);
		_aa1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		_spCate1.setPrompt("카테고리1");
		_spCate1.setAdapter(_aa1);
		_sp1temp = _global._alCate01.indexOf(_cursor.getString(2));
		_spCate1.setSelection(_sp1temp);

		_aa2 = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, _global._alCate02);
		_aa2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		_spCate2.setPrompt("카테고리2");
		_spCate2.setAdapter(_aa2);
		_sp2temp = _global._alCate02.indexOf(_cursor.getString(3));
		_spCate2.setSelection(_sp2temp);

		_btRetake.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(question_edit.this,
						retake_photo.class);
				startActivityForResult(intent, 2);
			}
		});

		_btCategoryEdit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(question_edit.this, category.class);
				startActivityForResult(intent, 1);
			}
		});

		_btReg.setOnClickListener(new OnClickListener() {
			int check = 0;

			@Override
			public void onClick(View v) {
				for (int i = 0; i < _global._autoCTVstr.size(); i++) {
					if (_actvKeyword.getText().toString()
							.equals(_global._autoCTVstr.get(i).toString())) {
						check = 1;
					}
				}
				if (check == 0)
					_global._autoCTVstr.add(_actvKeyword.getText().toString());

				ByteArrayOutputStream stream = new ByteArrayOutputStream();
				_global._bitmap.compress(CompressFormat.JPEG, 100, stream);
				byte[] data = stream.toByteArray();

				File file = new File(path);

				try {
					FileOutputStream fos = new FileOutputStream(file);
					fos.write(data);
					fos.flush();
					fos.close();
				} catch (Exception e) {
					Toast.makeText(question_edit.this,
							"파일 저장 중 에러 발생 : " + e.getMessage(), 0).show();
					return;
				}

				Intent intent = new Intent(
						Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
				Uri uri = Uri.parse("file://" + path);
				intent.setData(uri);
				sendBroadcast(intent);

				Toast.makeText(question_edit.this, "사진 저장 완료 : " + path, 0)
						.show();

				ContentValues _cvInsert = new ContentValues();
				_cvInsert.put("keyworkd", _actvKeyword.getText().toString());
				_cvInsert.put("cate01", _global._alCate01.get(_sp1temp));
				_cvInsert.put("cate02", _global._alCate02.get(_sp2temp));
				_cvInsert.put("answer", _sp3temp);
				_cvInsert.put("solution", _etSolution.getText().toString());
				_cvInsert.put("rate", _sp4temp);
				_global._db.update("question", _cvInsert, "id=" + _id, null);

				setResult(RESULT_OK, intent);
				finish();
			}
		});

		_btCancle.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});

		_spCate1.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				_sp1temp = position;
			}

			public void onNothingSelected(AdapterView<?> args0) {
			}
		});

		_spCate2.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				_sp2temp = position;
			}

			public void onNothingSelected(AdapterView<?> args0) {
			}
		});

		_cursor.close();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 1 && resultCode == RESULT_OK) {
			_aa1.notifyDataSetChanged();
			_aa2.notifyDataSetChanged();
		} else if (requestCode == 2 && resultCode == RESULT_OK)
			_ivPhoto.setImageBitmap(_global._bitmap);
	}
}
