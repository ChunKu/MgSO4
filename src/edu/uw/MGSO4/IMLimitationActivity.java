package edu.uw.MGSO4;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;

public class IMLimitationActivity extends Activity {
	/** Called when the activity is first created. */
	String status;
	String which_dose_title;
	int screen_num;
	int available_concentration;
	int final_concentration;
	int final_gram;
	
	String radiostatus;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.im_limitation_layout);
        ArrayList<String> array = getIntent().getStringArrayListExtra("data");
        for (int i=0; i<array.size(); i++){
        	if(i==0)     	status = new String(array.get(i));
        	else if (i==1)	which_dose_title = new String(array.get(i));
        	else if (i==2)	screen_num = Integer.parseInt(array.get(i));
        	else if (i==3)	available_concentration = Integer.parseInt(array.get(i));
        	else if (i==4)	final_concentration = Integer.parseInt(array.get(i));
        	else if (i==5)	final_gram = Integer.parseInt(array.get(i));
        }
        //set layout
        TextView tmp = (TextView) findViewById(R.id.IM_limitation_question);
        tmp.setText(Html.fromHtml(getString(R.string.IM_limitation_question)));
        tmp = (TextView) findViewById(R.id.im_limitation_note);
        tmp.setText(Html.fromHtml("<b>NOTE: </b>"+ getString(R.string.IM_limitation_note)+" "+getString(R.string.IM_limitation_note2)));
        tmp = (TextView) findViewById(R.id.im_limitation_screen_num);
        tmp.setText("Screen "+screen_num+"/6");
        tmp = (TextView) findViewById(R.id.im_limitation_title);
        tmp.setText(which_dose_title);
        //set radiogroup listner
        RadioGroup actions = (RadioGroup)findViewById(R.id.limitation_answer);
		actions.setOnCheckedChangeListener(listener);
		radiostatus = new String("none");
    }
    private RadioGroup.OnCheckedChangeListener listener = new RadioGroup.OnCheckedChangeListener() {
		
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			// TODO Auto-generated method stub
			switch(checkedId){
				case R.id.YesRadioButton:
					radiostatus = new String("YES");
					break;
				case R.id.NoRadioButton:
					radiostatus = new String("NO");
					break;
			}
		}
	};
    
    public void backToPreviousPage (View view){
		 super.onBackPressed();
	     finish();
	 }
	 
	 public void goToNextPage(View view){
		if(radiostatus.compareTo("YES")==0){
			//proceed to calculate concentration
			Intent intent = new Intent(this, AvailableConcentration.class);
    		ArrayList<String> array = new ArrayList<String>();
    		array.add("IM");
    		array.add("LOADING DOSE - IM");
    		array.add(Integer.toString(screen_num+1));
    		intent.putStringArrayListExtra("data", array);
    		startActivity(intent);
		}
		else if (radiostatus.compareTo("NO")==0){
			Intent intent = new Intent(this, IM_not_enough_warning.class);
    		ArrayList<String> array = new ArrayList<String>();
    		array.add("IV substitute IM");
    		array.add("LOADING DOSE - IV");
    		array.add(Integer.toString(screen_num+1));
    		intent.putStringArrayListExtra("data", array);
    		startActivity(intent);
		}
		else if (radiostatus.compareTo("none")==0){
				AlertDialog.Builder builder = new AlertDialog.Builder(this);
	    		builder.setTitle("Warning")
	    				.setMessage("Please choose one action to proceed.")
	    				.setCancelable(false)       
	    				.setPositiveButton("OK", new DialogInterface.OnClickListener() {           
	    						public void onClick(DialogInterface dialog, int id) {                
	    							return;          
	    						}
	    					});
	    		AlertDialog alert = builder.create();
	    		alert.show();
	    	
		}
	}
}
