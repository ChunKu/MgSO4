package edu.uw.MGSO4;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MGSO4Activity extends Activity {

    private String radiostatus;
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		TextView questions = (TextView)findViewById(R.id.Questions);
		questions.setText("Which action do you want to perform?");
		RadioGroup actions = (RadioGroup)findViewById(R.id.radiogroup);
		actions.setOnCheckedChangeListener(listener);
		radiostatus = new String("none");
    }
    private RadioGroup.OnCheckedChangeListener listener = new RadioGroup.OnCheckedChangeListener() {
		
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			// TODO Auto-generated method stub
			switch(checkedId){
				case R.id.IVRadioButton:
					radiostatus = new String("IV");
					break;
				case R.id.IMRadioButton:
					radiostatus = new String("IM");
					break;
				case R.id.MaintenanceRadioButton:
					radiostatus = new String("Maintenance");
					break;
			}
			
			
		}
	};
    
    public void nextstep(View button) {
    	if(radiostatus.compareTo("none") == 0){
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
    	else{
    		Intent intent = new Intent(this, AvailableConcentration.class);
    		ArrayList<String> array = new ArrayList<String>();
    		array.add(radiostatus);
    		if(radiostatus.compareTo("Maintenance") == 0){
    			array.add("Maintenance dose");
    			Toast.makeText(this, "Coming Soon...", 5).show();
    			return;
    		}
    		else{
    			array.add("LOADING DOSE - "+ radiostatus);
    		}
    		intent.putStringArrayListExtra("data", array);
    		startActivity(intent);
    		
    	}
    }
}