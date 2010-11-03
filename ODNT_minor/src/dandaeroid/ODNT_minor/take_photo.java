package dandaeroid.ODNT_minor;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.PictureCallback;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class take_photo extends Activity {
	MyCameraSurface _surface;

	Button _buttonShutter;

	Global _global;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.take_photo);

		_global = (Global) getApplication();
		_surface = (MyCameraSurface) findViewById(R.id.preview);
		_buttonShutter = (Button) findViewById(R.id.Button02);

		// 사진 촬영
		_buttonShutter.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				_surface.mCamera.takePicture(null, null, mPicture);
			}
		});
	}

	
	// 포커싱 성공하면 촬영 허가
	AutoFocusCallback mAutoFocus = new AutoFocusCallback() {
		public void onAutoFocus(boolean success, Camera camera) {
			_buttonShutter.setEnabled(success);
		}
	};

	// 사진 버퍼에 저장
	PictureCallback mPicture = new PictureCallback() {
		public void onPictureTaken(byte[] data, Camera camera) {
	
			_global._bitmap = BitmapFactory.decodeByteArray(data, 0,
					data.length);
			Matrix matrix = new Matrix();
			matrix.postRotate(90);
			_global._bitmap = Bitmap.createBitmap(_global._bitmap, 0, 0,
					_global._bitmap.getWidth(), _global._bitmap.getHeight(),
					matrix, true);
			_surface.mCamera.startPreview();
			
			Intent intent = new Intent(take_photo.this, question_reg.class);
			startActivity(intent);
		}
	};
}