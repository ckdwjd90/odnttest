package dandaeroid.ODNT_test;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class category extends Activity {
	Button _button01;
	Button _button02;
	Button _button03;
	Button _button04;
	Spinner _spinner01;
	Spinner _spinner02;
	EditText _edittext01;
	EditText _edittext02;
	TextView _textview01;
	Global _ppp;
	Question _qqq;
	ArrayAdapter<String> _adapter01;
	ArrayAdapter<String> _adapter02;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.category1); 
	    _ppp = (Global)getApplication();
	    
	    _button01 = (Button)findViewById(R.id.Button01);
	    _button02 = (Button)findViewById(R.id.Button02);
	    _button03 = (Button)findViewById(R.id.Button03);
	    _button04 = (Button)findViewById(R.id.Button04);
	    _spinner01 = (Spinner)findViewById(R.id.Spinner01);
	    _spinner02 = (Spinner)findViewById(R.id.Spinner02);
	    _edittext01 = (EditText)findViewById(R.id.EditText01);
	    _edittext02 = (EditText)findViewById(R.id.EditText02);
	    _textview01 = (TextView)findViewById(R.id.TextView01);
	    
	    _adapter01 = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, _ppp._alCate01);
	    _spinner01.setAdapter(_adapter01);
	    _spinner01.setPrompt("Category1");
	    
	    _adapter02 = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, _ppp._alCate02);
	    _spinner02.setAdapter(_adapter02);
	    _spinner02.setPrompt("Category2");
	    
	    
	    
	    
	    
	    // TODO Auto-generated method stub
	}

}
