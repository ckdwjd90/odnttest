package dandaeroid.ODNT_test;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class category extends Activity {
	Button _button01;
	Button _button02;
	Button _button03;
	Button _button04;
	Button _button05;
	Button _button06;
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
	int p;
	int forposition;
	int forposition2;
	
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
	    _button05 = (Button)findViewById(R.id.Button05);
	    _button06 = (Button)findViewById(R.id.Button06);
	    _spinner01 = (Spinner)findViewById(R.id.Spinner01);
	    _spinner02 = (Spinner)findViewById(R.id.Spinner02);
	    _edittext01 = (EditText)findViewById(R.id.EditText01);
	    _edittext02 = (EditText)findViewById(R.id.EditText02);
	    _textview01 = (TextView)findViewById(R.id.TextView01);
	    
	    _textview01.setText("- Change category -");
	    intent = getIntent();
        p= intent.getIntExtra("position", 0);
	    
	    
	    
	    _adapter01 = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, _ppp._alCate01);
	    _spinner01.setAdapter(_adapter01);
	    _spinner01.setPrompt("Category1");
	    
	    _adapter02 = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, _ppp._alCate02);
	    _spinner02.setAdapter(_adapter02);
	    _spinner02.setPrompt("Category2");
	    
	    _spinner01.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				Toast.makeText(category.this, ""+position , Toast.LENGTH_SHORT).show();
		    	 
				forposition = position;
				_edittext01.setText(_ppp._alCate01.get(forposition));
			}
			public void onNothingSelected(AdapterView<?> args0) {
			}
		});
	    _spinner02.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				Toast.makeText(category.this, ""+position , Toast.LENGTH_SHORT).show();
		    	 
				forposition2 = position;
				_edittext01.setText(_ppp._alCate02.get(forposition));
			}
			public void onNothingSelected(AdapterView<?> args0) {
			}
		});
	    
	    _button01.setOnClickListener(new View.OnClickListener() {
			 public void onClick(View v) {
				 
				 _ppp._alCate01.set(forposition, _edittext01.getText().toString());
				 _spinner01.setAdapter(_adapter01);
			}
		});
	    
	    _button02.setOnClickListener(new View.OnClickListener() {
			 public void onClick(View v) {
				 
				 _ppp._alCate01.remove(forposition);
				 _spinner01.setAdapter(_adapter01);
			}
		});
	    
	    _button03.setOnClickListener(new View.OnClickListener() {
			 public void onClick(View v) {
				 
				 _ppp._alCate02.set(forposition2, _edittext02.getText().toString());
				 _spinner02.setAdapter(_adapter02);
			}
		});
	    
	    _button04.setOnClickListener(new View.OnClickListener() {
			 public void onClick(View v) {
				 
				 _ppp._alCate02.remove(forposition2);
				 _spinner02.setAdapter(_adapter02);
			}
		});
	    
	    _button05.setOnClickListener(new View.OnClickListener() {
			 public void onClick(View v) {

				 setResult(RESULT_OK,intent);
				 finish();		
				

			}
		});
	    
	    _button06.setOnClickListener(new View.OnClickListener() {
			 public void onClick(View v) {

				 setResult(RESULT_OK,intent);
				 finish();		
				

			}
		});
	    
	    
	    
	    // TODO Auto-generated method stub
	}

}
