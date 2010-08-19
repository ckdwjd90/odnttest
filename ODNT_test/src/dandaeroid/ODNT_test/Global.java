package dandaeroid.ODNT_test;

import java.util.ArrayList;

import android.app.Application;

public class Global extends Application {
	ArrayList<Question> _alQuestion;
	ArrayList<String> _alCate01;
	ArrayList<String> _alCate01Edited;
	ArrayList<Integer> _alCate01Position;
	ArrayList<String> _alCate02;
	ArrayList<String> _alCate02Edited;
	ArrayList<Integer> _alCate02Position;
	ArrayList<String> _autoCTVstr;

	public void onCreate(){
		_alQuestion = new ArrayList<Question>();
		_alCate01 = new ArrayList<String>();
		_alCate01Edited = new ArrayList<String>();
		_alCate01Position = new ArrayList<Integer>();
		_alCate02 = new ArrayList<String>();
		_alCate02Edited = new ArrayList<String>();
		_alCate02Position = new ArrayList<Integer>();
		_autoCTVstr = new ArrayList<String>();
		
        _alCate01.add("미분류");
        _alCate01Edited.add("미분류");
        _alCate01Position.add(0);
        _alCate02.add("미분류");
        _alCate02Edited.add("미분류");
        _alCate02Position.add(0);
	}
}