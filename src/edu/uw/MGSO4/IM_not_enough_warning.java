package edu.uw.MGSO4;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class IM_not_enough_warning extends Activity {
	/** Called when the activity is first created. */
	String status;
	String which_dose_title;
	int screen_num;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.im_concentration_not_enough_layout);
        ArrayList<String> array = getIntent().getStringArrayListExtra("data");
        for (int i=0; i<array.size(); i++){
        	if(i==0)     	status = new String(array.get(i));
        	else if (i==1)	which_dose_title = new String(array.get(i));
        	else if (i==2)	screen_num = Integer.parseInt(array.get(i));
        }
        TextView tmp = (TextView) findViewById(R.id.im_not_enough_title);
        tmp.setText(which_dose_title);
        tmp = (TextView) findViewById(R.id.im_not_enought_screen_num);
        tmp.setText("Screen "+ screen_num+"/6");
        
    }
    public void goToNextPage(View button) {
    	Intent intent = new Intent(this, AvailableConcentration.class);
		ArrayList<String> array = new ArrayList<String>();
		array.add(status);
		array.add(which_dose_title);
		array.add(Integer.toString(screen_num+1));
		intent.putStringArrayListExtra("data", array);
		startActivity(intent);
    }
    public void backToPreviousPage (View view){
		 super.onBackPressed();
	     finish();
	 }
    
}
