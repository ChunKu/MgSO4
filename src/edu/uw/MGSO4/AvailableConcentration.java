package edu.uw.MGSO4;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

public class AvailableConcentration extends Activity {
	String status;  //IV or IM or Maintenance
	String which_dose_title; // LOADING DOSE -IV/IM or Maintenance DOSE
	int screen_num = 1;
	int available_concentration;
	int final_concentration;
	int final_gram;  //currently not allow user input final gram
	
	String concentration;
	
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
		 	super.onCreate(savedInstanceState);
	        setContentView(R.layout.concentration_layout);
	        ArrayList<String> array = getIntent().getStringArrayListExtra("data");
	        
	        for (int i=0; i<array.size(); i++){
	        	if(i==0)     	status = array.get(i);
	        	else if (i==1)	which_dose_title = new String(array.get(i));
	        	else if (i==2)	screen_num = Integer.parseInt(array.get(i));
	        	else if (i==3)	available_concentration = Integer.parseInt(array.get(i));
	        	else if (i==4)	final_concentration = Integer.parseInt(array.get(i));
	        	else if (i==5)	final_gram = Integer.parseInt(array.get(i));
	        }
	        //set title
		 	TextView title = (TextView) findViewById(R.id.concentration_title);
		 	title.setText(which_dose_title);
		 	//set screen number
		 	title = (TextView) findViewById(R.id.concentration_screen_num);
		 	title.setText("Screen "+Integer.toString(screen_num)+"/6");
		 	//set questions
		 	title = (TextView) findViewById(R.id.concentration_questions);
		 	if (array.size() < 5) {
		 		title.setText(Html.fromHtml("What is the percent (%) concentration of MgSO4 <b>available</b>?"));
		 		if (status.compareTo("IV") == 0){
		 			//set fix final_gram and final_concentration for IV
		 			final_concentration = 20;
			        final_gram = 4;
		 		}
		 		else if (status.compareTo("IM") == 0){
		 			//set fix final_gram and final_concentration for IM
		 			final_concentration = 50;
		 			final_gram = 5;
		 		}
		 		else if (status.compareTo("IV substitute IM") == 0){
		 			//set fix final_gram and final _concentration for using IV to substitute IM
		 			final_concentration = 20;
		 			final_gram = 1;
		 		}
		 		
		 	}
		 	else { // this condition is set for not knowning final concentration.
		 		title.setText(Html.fromHtml("What is the <b>final</b>percent (%) concentration of MgSO4?"));
		 	}
		 	//create spinner
		 	Spinner spinner = (Spinner) findViewById(R.id.spinner1);
		 	ArrayAdapter<CharSequence> adapter;
		 	if ((status.compareTo("IV")==0) || (status.compareTo("IV substitute IM")==0)){
		 		adapter = ArrayAdapter.createFromResource(
		 		this, R.array.IV_concentrations, android.R.layout.simple_spinner_item);
		 		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		 		spinner.setAdapter(adapter);
		 	}
		 	else if(status.compareTo("IM")==0){
		 		adapter = ArrayAdapter.createFromResource(
				this, R.array.IM_concentrations, android.R.layout.simple_spinner_item);
		 		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		 		spinner.setAdapter(adapter);
		 	}
		 	
		 	
		 	spinner.setOnItemSelectedListener(new SpinnerOnItemSelectedListener());
		 	LinearLayout linear_spec = (LinearLayout) findViewById(R.id.linearlayout_spec);
		 	linear_spec.setVisibility(4); //set invisible
		 	
	 }
	 
	 public void backToPreviousPage (View view){
		 super.onBackPressed();
	     finish();
	 }
	 
	 public void goToNextPage(View view){
		 String tmp;
		 //extract available concentration
		 if(concentration.compareTo("other") != 0){
			 tmp = new String(concentration.substring(0, concentration.length()-1));
		 }
		 else{
			 LinearLayout linear_spec = (LinearLayout) findViewById(R.id.linearlayout_spec);
			 EditText number = (EditText) linear_spec.getChildAt(0);
			 tmp = new String(number.getText().toString());
		 }
		 
		 if (Integer.parseInt(tmp) > 100){
			 AlertDialog.Builder builder = new AlertDialog.Builder(this);
	    		builder.setTitle("Warning")
	    				.setMessage("Solution should be less than or equal to 100%.")
	    				.setCancelable(false)       
	    				.setPositiveButton("OK", new DialogInterface.OnClickListener() {           
	    						public void onClick(DialogInterface dialog, int id) {                
	    							return;          
	    						}
	    					});
	    		AlertDialog alert = builder.create();
	    		alert.show();
		 }
		 else if(status.compareTo("IM") == 0){
			 if (Integer.parseInt(tmp)<50){
				 AlertDialog.Builder builder = new AlertDialog.Builder(this);
		    		builder.setTitle("Warning")
		    				.setMessage("IM solution should be greater than or equal to 50%.")
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
		 else if (Integer.parseInt(tmp) <= 100){
			 available_concentration = Integer.parseInt(tmp);
		 
			 Intent intent = new Intent(this, InstructionActivity.class);
			 ArrayList<String> array = new ArrayList<String>();

			 array.add(status);
			 array.add(which_dose_title);
			 array.add(Integer.toString(screen_num+1));
			 array.add(Integer.toString(available_concentration));
			 array.add(Integer.toString(final_concentration));
			 array.add(Integer.toString(final_gram));
		 
			 intent.putStringArrayListExtra("data", array);
			 startActivity(intent);
		 }
		
	}
	 
	 public class SpinnerOnItemSelectedListener implements OnItemSelectedListener {    
			public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
				LinearLayout linear_spec = (LinearLayout) findViewById(R.id.linearlayout_spec);
				concentration = new String(parent.getItemAtPosition(pos).toString());
				if (concentration.compareTo("other") == 0){
					linear_spec.setVisibility(0); //set visible
					//To do: try to show keyboard at the same time.
				}
				else {
					EditText tmp = (EditText) linear_spec.getChildAt(0);
					tmp.setText("");
					InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(tmp.getWindowToken(), 0); 
					linear_spec.setVisibility(4); //set invisible
				}
			}

			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
			
	}
}
