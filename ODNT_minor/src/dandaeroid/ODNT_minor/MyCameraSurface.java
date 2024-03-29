package dandaeroid.ODNT_minor;

import java.io.IOException;
import java.util.List;

import android.content.Context;
import android.hardware.Camera;
import android.hardware.Camera.Size;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

class MyCameraSurface extends SurfaceView implements SurfaceHolder.Callback {
	SurfaceHolder mHolder;
	Camera mCamera;

	public MyCameraSurface(Context context, AttributeSet attrs) {
		super(context, attrs);
		mHolder = getHolder();
		mHolder.addCallback(this);
		mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	}

	// 표면 생성시 카메라 오픈하고 미리보기 설정
	public void surfaceCreated(SurfaceHolder holder) {
		mCamera = Camera.open();
		try {
			mCamera.setPreviewDisplay(mHolder);
		} catch (IOException e) {
			mCamera.release();
			mCamera = null;
		}
	}

	// 표면 파괴시 카메라도 파괴한다.
	public void surfaceDestroyed(SurfaceHolder holder) {
		if (mCamera != null) {
			mCamera.stopPreview();
			mCamera.release();
			mCamera = null;
		}
	}

	// 표면의 크기가 결정될 때 최적의 미리보기 크기를 구해 설정한다.
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		Camera.Parameters params = mCamera.getParameters();
		List<Size> arSize = params.getSupportedPreviewSizes();
		if (arSize == null) {
			params.setPreviewSize(width, height);
		} else {
			int diff = 10000;
			Size opti = null;
			for (Size s : arSize) {
				if (Math.abs(s.height - height) < diff) {
					diff = Math.abs(s.height - height);
					opti = s;

				}
			}
			params.setPreviewSize(opti.width, opti.height);
			params.setPictureSize(800, 600);
		}
		mCamera.setParameters(params);
		mCamera.startPreview();
	}
}
