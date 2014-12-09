package in.sourabh.instacare;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


public class MainActivity extends ActionBarActivity   {
	
	public ProgressDialog pDialog;
	private static String url = "http://practo.0x10.info/api/speciality";
	private static String url2 = "http://practo.0x10.info/api/locality";
	String jsonStr, jsonStr2;
	Gson gson = new Gson();
	List<String> list = new ArrayList<String>();
	List<String> list2 = new ArrayList<String>();
	Type type;
	List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
	ArrayAdapter<String> adapter, adapter2;
	AutoCompleteTextView actv, actv2; 
	private RadioGroup group0,group1, group2;
	private RadioButton radioBtn1, radioBtn2, radioBtn0;
	public static Button  timebt, datebt, bookbt;
	public static TextView tvtime, tvdate;
	public String city = "bangalore";
	int groupid= 1;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        group1=(RadioGroup)findViewById(R.id.radioGroup1);
        group2=(RadioGroup)findViewById(R.id.radioGroup2);
        group0=(RadioGroup)findViewById(R.id.radioGroup0);
        timebt =(Button)findViewById(R.id.timebt);
        datebt =(Button)findViewById(R.id.datebt);
        bookbt =(Button)findViewById(R.id.bookbt);
       
        tvtime = (TextView)findViewById(R.id.tvtime);
        tvdate = (TextView)findViewById(R.id.tvdate);
        
        final Calendar c = Calendar.getInstance();
        
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		int day = c.get(Calendar.DAY_OF_MONTH);
		int hour = c.get(Calendar.HOUR_OF_DAY);
		int minute = c.get(Calendar.MINUTE);
		
		setTime(hour, minute);
		setDate(year, month, day);
        new GetLocality().execute();
        radioGroupListen();
        buttonListen();
        
    }





	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_history) {
        	Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
    		startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
    
    private void buttonListen() {
    	timebt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				showTimePickerDialog();
			}
		});
    	datebt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				showDatePickerDialog();
			}
		});
    	bookbt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
        		startActivity(intent);
			}
		});
    	

    	
	}
    
    public void radioGroupListen(){
    	
    	group0.setOnCheckedChangeListener(new OnCheckedChangeListener()
        {
    		
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
            	
            	radioBtn0 = (RadioButton)findViewById(checkedId);
                switch(checkedId)
                {
                case R.id.instantrb:
                	//Toast.makeText(MainActivity.this, radioBtn0.getText(), Toast.LENGTH_SHORT).show();
                    break;
                case R.id.shedulerb:
                	//Toast.makeText(MainActivity.this, radioBtn0.getText(), Toast.LENGTH_SHORT).show();
                    break;

                }
            }
        });
    	
    	group1.setOnCheckedChangeListener(new OnCheckedChangeListener()
        {
    		
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
            	
            	radioBtn1 = (RadioButton)findViewById(checkedId);
                switch(checkedId)
                {
                case R.id.radio0:
                	city = radioBtn1.getText().toString().toLowerCase();
                	new GetLocality().execute();
                    break;
                case R.id.radio1:
                	city = radioBtn1.getText().toString().toLowerCase();
                	new GetLocality().execute();
                    break;
                case R.id.radio2:
                	city = radioBtn1.getText().toString().toLowerCase();
                	new GetLocality().execute();
                    break;
                }
            }
        });
    	
    	group2.setOnCheckedChangeListener(new OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
            
            	radioBtn2 = (RadioButton)findViewById(checkedId);
                switch(checkedId)
                {
                case R.id.radio4:
                	city = radioBtn1.getText().toString().toLowerCase();
                	new GetLocality().execute();
                    break;
                case R.id.radio5:
                	city = radioBtn1.getText().toString().toLowerCase();
                	new GetLocality().execute();
                    break;
                case R.id.radio6:
                	city = radioBtn1.getText().toString().toLowerCase();
                	new GetLocality().execute();
                    break;
                }
            }
        });
    	
    }

    private class GetLocality extends AsyncTask<Void, Void, Void> {
 
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
          
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
 
        }
 
        @Override
        protected Void doInBackground(Void... arg0) {
            // Creating service handler class instance
            ServiceHandler sh = new ServiceHandler();
            nameValuePairs.add(new BasicNameValuePair("type", "json"));
            // Making a request to url and getting response
            jsonStr = sh.makeServiceCall(url, ServiceHandler.GET, nameValuePairs);
            //Log.d("Response: ", "> " + jsonStr);
 
            if (jsonStr != null) {

            } else {
                Log.e("ServiceHandler", "Couldn't get any data from the url");
            }
            
            nameValuePairs.add(new BasicNameValuePair("city", "bangalore"));
            jsonStr2 = sh.makeServiceCall(url2, ServiceHandler.GET, nameValuePairs);
            Log.d("Response: ", "> " + jsonStr2);
            if (jsonStr2 != null) {

            } else {
                Log.e("ServiceHandler", "Couldn't get any data from the url");
            }
 
            return null;
        }
 
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            /**
             * Updating parsed JSON data into ListView
             * */
            //Toast.makeText(MainActivity.this, jsonStr2, Toast.LENGTH_SHORT).show();
            
            type = new TypeToken<List<String>>(){}.getType();
            
            list = gson.fromJson(jsonStr, type);
            adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.select_dialog_item, list);
            actv= (AutoCompleteTextView)findViewById(R.id.specialtv);
            actv.setThreshold(1);
            actv.setAdapter(adapter);
            
            list2 = gson.fromJson(jsonStr2, type);
            adapter2 = new ArrayAdapter<String>(MainActivity.this, android.R.layout.select_dialog_item, list2);
            actv2= (AutoCompleteTextView)findViewById(R.id.localtv);
            actv2.setThreshold(1);
            actv2.setAdapter(adapter2);
        }
 
    }
    
    public static void setTime(int hourOfDay, int minute){
    	tvtime.setText(hourOfDay + " : " + minute);
    }
    
    
    public static void setDate(int year, int month, int day){
		tvdate.setText(day + "/" + month + "/" + year);
	}
    
    
	public static class TimePickerFragment extends DialogFragment implements
			TimePickerDialog.OnTimeSetListener {

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			// Use the current time as the default values for the picker
			final Calendar c = Calendar.getInstance();
			int hour = c.get(Calendar.HOUR_OF_DAY);
			int minute = c.get(Calendar.MINUTE);

			// Create a new instance of TimePickerDialog and return it
			return new TimePickerDialog(getActivity(), this, hour, minute,
					DateFormat.is24HourFormat(getActivity()));
		}

		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			setTime(hourOfDay, minute);
		}
	}
	
	
	
	public void showTimePickerDialog() {
	    DialogFragment newFragment = new TimePickerFragment();
	    newFragment.show(getFragmentManager(), "TImepicker");
	}
	
	public static class DatePickerFragment extends DialogFragment implements
			DatePickerDialog.OnDateSetListener {

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			// Use the current date as the default date in the picker
			final Calendar c = Calendar.getInstance();
			int year = c.get(Calendar.YEAR);
			int month = c.get(Calendar.MONTH);
			int day = c.get(Calendar.DAY_OF_MONTH);

			// Create a new instance of DatePickerDialog and return it
			return new DatePickerDialog(getActivity(), this, year, month, day);
		}

		public void onDateSet(DatePicker view, int year, int month, int day) {
			setDate(year, month, day);
		}
	}
	public void showDatePickerDialog() {
	    DialogFragment newFragment = new DatePickerFragment();
	    newFragment.show(getFragmentManager(), "datePicker");
	}
}
