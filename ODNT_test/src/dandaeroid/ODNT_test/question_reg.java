package dandaeroid.ODNT_test;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;

public class question_reg extends Activity {
	ArrayList<Integer> _ALint1;
	ArrayList<Integer> _ALint2;
	ArrayList<String> _autostr;
	ArrayAdapter<String> _aa1;
	ArrayAdapter<String> _aa2;
	ArrayAdapter<Integer> _aa3;
	ArrayAdapter<Integer> _aa4;
	ArrayAdapter<String> _autoAdapter;
	AutoCompleteTextView _autotv1;
	EditText _et2;
	Button _bt1;
	Button _bt2;
	Button _bt3;
	Global _gb;

	int _sp1temp = 0;
	int _sp2temp = 0;
	int _sp3temp = 0;
	int _sp4temp = 0;
	
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question_reg);
        
        _autotv1 = (AutoCompleteTextView) findViewById(R.id.AutoCompleteTextView01);
        _et2 = (EditText) findViewById(R.id.EditText02);
        _bt1 = (Button)findViewById(R.id.Button01);
        _bt2 = (Button)findViewById(R.id.Button02);
        _bt3 = (Button)findViewById(R.id.Button03);
        _gb = (Global)getApplication();      
        
        Spinner _sp1 = (Spinner) findViewById(R.id.Spinner01);
        Spinner _sp2 = (Spinner) findViewById(R.id.Spinner02);
        Spinner _sp3 = (Spinner) findViewById(R.id.Spinner03);
        Spinner _sp4 = (Spinner) findViewById(R.id.Spinner04);    
        
        _ALint1 = new ArrayList<Integer>();
        _ALint2 = new ArrayList<Integer>();
        
        _ALint1.add(0);
        _ALint1.add(1);
        _ALint1.add(2);
        _ALint1.add(3);
        _ALint1.add(4);
        _ALint1.add(5);
        
        _ALint2.add(0);
        _ALint2.add(1);
        _ALint2.add(2);
        _ALint2.add(3);
        _ALint2.add(4);
        _ALint2.add(5);
        
        _autoAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, _gb._autoCTVstr);
        _autotv1.setAdapter(_autoAdapter);
        
		_aa1 = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, _gb._alCate01Edited);		
		_aa1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		_sp1.setPrompt("카테고리1");
		_sp1.setAdapter(_aa1);
		
		_aa2 = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, _gb._alCate02Edited);		
		_aa2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		_sp2.setPrompt("카테고리2");
		_sp2.setAdapter(_aa2);
		
		_aa3 = new ArrayAdapter<Integer>(this,
				android.R.layout.simple_spinner_item, _ALint1);		
		_aa3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		_sp3.setPrompt("문제정답");
		_sp3.setAdapter(_aa3);
		
		_aa4 = new ArrayAdapter<Integer>(this,
				android.R.layout.simple_spinner_item, _ALint2);		
		_aa4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		_sp4.setPrompt("중요도");
		_sp4.setAdapter(_aa4);
        
		
        _bt1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(question_reg.this, category.class);
				startActivityForResult(intent, 1);
			}
		});
                
        _bt2.setOnClickListener(new OnClickListener() {
        	int check = 0;
			@Override
			public void onClick(View v) {
				for(int i=0;i < _gb._autoCTVstr.size();i++) {
					if (_autotv1.getText().toString().equals(_gb._autoCTVstr.get(i).toString())){
						check = 1;
				}
				}
				if(check==0) {
				_gb._autoCTVstr.add(_autotv1.getText().toString());
				}
				_gb._alQuestion.add(new Question(_autotv1.getText().toString(), _sp1temp, _sp2temp, _sp3temp,
						_et2.getText().toString(), _sp4temp));
//				ContentValues _cv1 = new ContentValues();
//				_cv1.put("title", _autotv1.getText().toString());
				
				String keyword = _autotv1.getText().toString();
				try {
			        HttpClient client = new DefaultHttpClient();
			        String postURL = "비밀/odnt_write.php";
			        HttpPost post = new HttpPost(postURL); 
			            List<NameValuePair> params = new ArrayList<NameValuePair>();
			            params.add(new BasicNameValuePair("keyword", keyword));
			            UrlEncodedFormEntity ent = new UrlEncodedFormEntity(params,HTTP.UTF_8);
			            post.setEntity(ent);
			            HttpResponse responsePOST = client.execute(post);  
			            HttpEntity resEntity = responsePOST.getEntity();  
			            if (resEntity != null) Log.w("RESPONSE",EntityUtils.toString(resEntity));
				} catch (Exception e) {
			        e.printStackTrace();
				}
				Intent intent = new Intent(question_reg.this, main.class);
				startActivity(intent);
			}
		});
        
        _bt3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(question_reg.this, main.class);
				startActivity(intent);
			}
		});
        
        _sp1.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				_sp1temp = _gb._alCate01Position.get(position);
			}			
			public void onNothingSelected(AdapterView<?> args0) {
			}
		});
        
        _sp2.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				_sp2temp = _gb._alCate02Position.get(position);
			}
			public void onNothingSelected(AdapterView<?> args0) {
			}
		});
        _sp3.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				_sp3temp = position;
			}			
			public void onNothingSelected(AdapterView<?> args0) {
			}
		});
        
        _sp4.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				_sp4temp = position;
			}			
			public void onNothingSelected(AdapterView<?> args0) {
			}
		});        
        
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	super.onActivityResult(requestCode, resultCode, data);
    	if(requestCode == 1 && resultCode == RESULT_OK)
    		_aa1.notifyDataSetChanged();
    		_aa2.notifyDataSetChanged();    	
    }
}