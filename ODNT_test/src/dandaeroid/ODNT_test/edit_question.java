package dandaeroid.ODNT_test;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

public class edit_question extends Activity {
	int _position;
	Global _global;
	Question _question;
	
	EditText _etKeyword;
	Spinner _spCate01;
	ArrayAdapter<String> _aaCate01;
	Spinner _spCate02;
	ArrayAdapter<String> _aaCate02;
	Spinner _spAnswer;
	ArrayList<String> _alAnswer;
	ArrayAdapter<String> _aaAnswer;
	EditText _etSolution;
	Spinner _spRate;
	ArrayList<String> _alRate;
	ArrayAdapter<String> _aaRate;

	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_question);
        
        _global = (Global)getApplication();
        _etKeyword = (EditText)findViewById(R.id.EditText01);
        _spCate01 = (Spinner)findViewById(R.id.Spinner01);
        _spCate02 = (Spinner)findViewById(R.id.Spinner02);
        _spAnswer = (Spinner)findViewById(R.id.Spinner03);
        _etSolution = (EditText)findViewById(R.id.EditText02);
        
        Intent intent = getIntent();
        _position = intent.getIntExtra("position", 0);
        _question = _global._alQuestion.get(_position);
        
        _etKeyword.setText(_question._keyword);
        _etSolution.setText(_question._solution);
        
	}
}
