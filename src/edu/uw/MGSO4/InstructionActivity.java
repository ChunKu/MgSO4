package edu.uw.MGSO4;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


public class InstructionActivity extends Activity {
	/** Called when the activity is first created. */
	String status;
	String which_dose_title;
	int screen_num;
	int available_concentration;
	int final_concentration;
	int final_gram;
	
	int extract_origin;  //extract X ml from origin MgSo4
	int add_water;   //add Y ml of sterile water
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.instructions_layout);
        ArrayList<String> array = getIntent().getStringArrayListExtra("data");
        
        for (int i=0; i<array.size(); i++){
        	if(i==0)     	status = new String(array.get(i));
        	else if (i==1)	which_dose_title = new String(array.get(i));
        	else if (i==2)	screen_num = Integer.parseInt(array.get(i));
        	else if (i==3)	available_concentration = Integer.parseInt(array.get(i));
        	else if (i==4)	final_concentration = Integer.parseInt(array.get(i));
        	else if (i==5)	final_gram = Integer.parseInt(array.get(i));
        }
        calculate();
        TextView tmp = (TextView) findViewById(R.id.instruction_title);
        tmp.setText(which_dose_title);
        tmp = (TextView) findViewById(R.id.instruction_screen_number);
        tmp.setText("Screen "+Integer.toString(screen_num)+"/6   ");
        tmp = (TextView) findViewById(R.id.instruction_final_solution);
        tmp.setText("Final Solution: "+ final_gram+"g " + final_concentration+"% "+status);
        tmp = (TextView) findViewById(R.id.instruction_warning_text);
        if((status.compareTo("IV")==0)||(status.compareTo("IV substitute IM")==0)){
        	tmp.setText(Html.fromHtml("<b>NOTE: </b>"+ getString(R.string.IV_solution_note)));
        	//Note: how much decimal is enough?
        	tmp = (TextView) findViewById(R.id.instructions_detailed_steps);
        	tmp.setText(Html.fromHtml(
        			"<p><b>Step 1/3: </b> Extract <font color=\"#7b5b06\"><b>"+ extract_origin +"ml </b></font>of <font color=\"#7b5b06\"><b>"+ available_concentration +"% </b></font>MgSO4 solution into a new syringe from the vial.</p>"
        			+"<p><b>Step 2/3: </b>Add <font color=\"#7b5b06\"><b>"+ add_water +"ml </b></font>of sterile water to the syringe.</p>"
        			+"<p><b>Step 3/3: </b>Give the IV solution slowly for 5 minutes.</p>"
        			));
        }
        else if (status.compareTo("IM")==0){
        	tmp.setText(Html.fromHtml("<b>WARNING: </b>"+ getString(R.string.IM_solution_note)));
        	tmp.setBackgroundResource(R.color.Yellow);
        	LinearLayout tmp2 = (LinearLayout) findViewById(R.id.instruction_warning_linearlayout);
        	tmp2.setBackgroundResource(R.color.Yellow);
        	tmp = (TextView) findViewById(R.id.instructions_detailed_steps);
        	tmp.setText(Html.fromHtml(
        			"<p><b>Step 1/4: </b> Extract <font color=\"#7b5b06\"><b>"+ extract_origin +"ml </b></font>of <font color=\"#7b5b06\"><b>"+ available_concentration +"% </b></font>MgSO4 solution into a new syringe from the vial.</p>"
        			+"<p><b>Step 2/4: </b>Add <font color=\"#7b5b06\"><b>"+ add_water +"ml </b></font>of sterile water to the syringe.</p>"
        			+"<p><b>Step 3/4: </b>Add 1g of 2% Lignocaine solution in the syringe.</p>"
        			+"<p><b>Step 4/4: </b>Inject the syringe into one of the buttocks.</p>"
        			));
        }
        
    }
    
    public void backToPreviousPage (View view){
		 super.onBackPressed();
	     finish();
	 }
	 
	 public void goToNextPage(View view){
		
		if(status.compareTo("IV") == 0){
			Intent intent = new Intent(this, IMLimitationActivity.class);
			ArrayList<String> array = new ArrayList<String>();
			array.add("IM");
			array.add("LOADING DOSE - IM");
			array.add(Integer.toString(screen_num+1));
			intent.putStringArrayListExtra("data", array);
			startActivity(intent);
		}
		else if ((status.compareTo("IM") ==0) || (status.compareTo("IV substitute IM")==0)){
			//go to alarm page
			Intent intent = new Intent(this, Loading_dose_final_step.class);
			ArrayList<String> array = new ArrayList<String>();
			array.add("IM");
			array.add("LOADING DOSE - IM");
			array.add(Integer.toString(screen_num+1));
			intent.putStringArrayListExtra("data", array);
			startActivity(intent);
		}
	
	}
	 
	void calculate(){
    	extract_origin = final_gram*100/available_concentration;
    	add_water = final_gram*100/final_concentration- extract_origin;
    	
	}
}
