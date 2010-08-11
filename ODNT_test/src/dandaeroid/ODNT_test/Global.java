package dandaeroid.ODNT_test;

import java.util.ArrayList;

import android.app.Application;

public class Global extends Application {
	ArrayList<Question> _alQuestion;
	ArrayList<String> _alCate01;
	ArrayList<String> _alCate02;

	public void onCreate(){
		_alQuestion = new ArrayList<Question>();
		_alCate01 = new ArrayList<String>();
		_alCate02 = new ArrayList<String>();
	}
}
