package dandaeroid.ODNT_test;

import java.util.ArrayList;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class view_question extends ListActivity {
	Global global;
	Button button01;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_question_1);
        
        global=(Global)getApplication();
        button01 = (Button)findViewById(R.id.Button01);
               
        QuestionAdapter m_adapter = new QuestionAdapter(this,R.layout.view_question_2,global._alQuestion);
        setListAdapter(m_adapter);
        
    	button01.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(view_question.this,
						category.class);
				startActivity(intent);

			}
		});
	}

	private class QuestionAdapter extends ArrayAdapter<Question>{
		private ArrayList<Question> items;
		
		 public QuestionAdapter(Context context, int textViewResourceId, ArrayList<Question> items) {
             super(context, textViewResourceId, items);
             this.items=items;
             
             
		 }
		 @Override
		 public View getView(int position,View convertView, ViewGroup parent){
			 View v=convertView;
			 if(v==null){
				 LayoutInflater vi = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				 v=vi.inflate(R.layout.view_question_2, null);
			 }
			 Question q = items.get(position);
			 if(q !=null){
				 TextView tt=(TextView) v.findViewById(R.id.toptext);
				 TextView bt_01=(TextView) v.findViewById(R.id.bottomtext01);
				 TextView bt_02=(TextView) v.findViewById(R.id.bottomtext02);
				 if(tt!=null){
					 tt.setText(q._keyword);
				 }
				 if(bt_01!=null){
					 bt_01.setText(q._cate01);
				 }
				 if(bt_02!=null){
					 bt_02.setText(q._cate02);
				 }
			 }
			 return v;
		 }
	}
	

	 protected void onListItemClick(ListView l, View v, int position, long id){
	    	super.onListItemClick(l, v, position, id);
	    	Intent intent=new Intent(view_question.this,details_view.class);
	    	intent.putExtra("position",position);
	    	startActivity(intent);
	    }
}