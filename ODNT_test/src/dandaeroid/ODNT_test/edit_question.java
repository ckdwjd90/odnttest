package dandaeroid.ODNT_test;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;

public class edit_question extends Activity {
	int _position;
	Global _global;
	Question _question;
	
	EditText _etKeyword;
	ArrayList<String> _alCate01;
	ArrayList<String> _alCate02;
	ArrayList<String> _alAnswer;
	EditText _etSolution;
	ArrayList<String> _alRate;

	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Intent intent = getIntent();
        _position = intent.getIntExtra("position", 65535);
        _question = _global._alQuestion.get(_position);
        
        _etKeyword.setText(_question._keyword);
        _etSolution.setText(_question._solution);
        
	}
}
