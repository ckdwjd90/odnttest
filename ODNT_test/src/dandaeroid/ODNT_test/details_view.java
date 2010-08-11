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
	TextView _text;
	Button _ok;
	String st;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.details_view);
	    _intent = getIntent();
	    _global = (Global)getApplication();
	    _text = (TextView)findViewById(R.id.TextView01);
	    _ok = (Button)findViewById(R.id.Button01);
	    
	    st = "This is Test Text";
	    _text.setText(st.toString());
	    
	    _ok.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {	
				finish();
			}
		});
	    
	    
	
	    // TODO Auto-generated method stub
	}

}
