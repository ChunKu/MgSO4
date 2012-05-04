package edu.uw.MGSO4;


import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Loading_dose_final_step extends Activity {
	/** Called when the activity is first created. */
	String status;
	String which_dose_title;
	int screen_num;
	CountDownTimer timer;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading_dose_final_step);
        ArrayList<String> array = getIntent().getStringArrayListExtra("data");
        for (int i=0; i<array.size(); i++){
        	if(i==0)     	status = new String(array.get(i));
        	else if (i==1)	which_dose_title = new String(array.get(i));
        	else if (i==2)	screen_num = Integer.parseInt(array.get(i));
        }
        TextView tmp = (TextView) findViewById(R.id.final_step_title);
        tmp.setText(which_dose_title);
        tmp = (TextView) findViewById(R.id.final_step_screen_num);
        tmp.setText("Screen "+ screen_num+"/6");
        tmp = (TextView) findViewById(R.id.count_down_timer);
        tmp.setText("remain: 15 min");

        timer = new CountDownTimer(1000*60*15, 1000){
        		public void onTick(long millisUntilFinished) {
        			TextView time_left = (TextView) findViewById(R.id.count_down_timer);
        			if(millisUntilFinished> 1000*60) {
        				time_left.setText("time remaining: " + millisUntilFinished / 1000 /60 +"min.");
        			}
        			else{
        				time_left.setText("time remaining: " + millisUntilFinished / 1000 +"sec.");
        			}
        		}     
        		public void onFinish() {
        			TextView time_left = (TextView) findViewById(R.id.count_down_timer);
        			time_left.setText("DONE");
        		}
        			
        };
        //problem: coundowntimer won't have alarm sound
        
    }
    public void startTimer(View start_button){
    	timer.start();
    	start_button.setVisibility(4); //set invisible
    }
    public void goToNextPage(View button) {
    	Toast.makeText(this, "Coming Soon", 5).show();
    	
    }
	 public void backToPreviousPage (View view){
			 super.onBackPressed();
		     finish();
	 }
}


