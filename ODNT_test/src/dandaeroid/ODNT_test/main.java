package dandaeroid.ODNT_test;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class main extends Activity {
	Global _global;
	
	Button _btQuestion_reg;
	Button _btView_question;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        _global = (Global)getApplication();
        
        _btQuestion_reg = (Button)findViewById(R.id.Button01);
        _btView_question = (Button)findViewById(R.id.Button02);
        
        _btQuestion_reg.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(main.this, question_reg.class);
				startActivity(intent);
			}
        });
        
        _btView_question.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(main.this, view_question.class);
				startActivity(intent);
			}
        });

    }
}