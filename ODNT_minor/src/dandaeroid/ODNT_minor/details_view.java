package dandaeroid.ODNT_minor;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class details_view extends Activity {
	Intent _intent;
	Global _global;
	Question q;
	TextView _text01;
	TextView _text02;
	String _st;
	int _id;
	ImageView _imageView;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.details_view);

		_intent = getIntent();

		_global = (Global) getApplication();
		_imageView = (ImageView) findViewById(R.id.ImageView01);
		_text01 = (TextView) findViewById(R.id.TextView01);
		_text02 = (TextView) findViewById(R.id.TextView02);
		_id = _intent.getIntExtra("id", 0);

		Log.v("DEBUG", "id: <" + _id + ">");

		loadContent();
	}

	@Override
	public boolean onCreateOptionsMenu(android.view.Menu menu) {
		super.onCreateOptionsMenu(menu);
		menu.add("수정");
		menu.add("삭제");
		menu.add("오답노트넷에 질문");
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getTitle().toString() == "수정") {
			Intent intent = new Intent(details_view.this, question_edit.class);
			intent.putExtra("id", _id);
			startActivityForResult(intent, 1);

		} else if (item.getTitle().toString() == "삭제") {
			AlertDialog.Builder alt_bld = new AlertDialog.Builder(this);
			alt_bld.setMessage("정말로 지우시겠어요 ?")
					.setCancelable(false)
					.setPositiveButton("Yes",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									_global._db
											.execSQL("DELETE FROM question WHERE id ="
													+ _id);
									Toast.makeText(details_view.this,
											"삭제 되었습니다", Toast.LENGTH_SHORT)
											.show();
									Intent intent = new Intent(
											details_view.this,
											view_question.class);
									startActivity(intent);
								}
							})
					.setNegativeButton("No",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									dialog.cancel();
								}
							});
			AlertDialog alert = alt_bld.create();
			// Title for AlertDialog
			alert.setTitle("");
			// Icon for AlertDialog
			alert.setIcon(R.drawable.icon);
			alert.show();
		} else if (item.getTitle().toString() == "오답노트넷에 질문") {
			Intent intent = new Intent(details_view.this, odnt_write.class);
			intent.putExtra("id", _id);
			startActivity(intent);
		}
		return true;
	}

	public void loadContent() {
		Cursor _cursor = _global._db.rawQuery(
				"SELECT * FROM question WHERE id=" + _id, null);
		_cursor.moveToFirst();

		_text01.setText(_cursor.getString(5));
		_text02.setText("<" + _cursor.getString(1) + ">");

		String path = Environment.getExternalStorageDirectory()
				.getAbsolutePath();
		path += "/odnt/"+_cursor.getString(7);
		Bitmap bitmap = BitmapFactory.decodeFile(path);
		_imageView.setImageBitmap(bitmap);

		_cursor.close();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
//		if (requestCode == 1 && resultCode == RESULT_OK)
			loadContent();
	}

}
