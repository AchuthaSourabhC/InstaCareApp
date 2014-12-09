package in.sourabh.instacare;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class HistoryActivity extends Activity {
	
	 ListView listView ;
	  @Override
      protected void onCreate(Bundle savedInstanceState) {
          super.onCreate(savedInstanceState);
          setContentView(R.layout.activity_history);
          
          // Get ListView object from xml
          listView = (ListView) findViewById(R.id.list);
          
          // Defined Array values to show in ListView
          String[] values = new String[] { "Forties, OPP, IIM-B    upcoming", 
                                           "Muncipal Hospital      Book Again",
                                           "Swethi Skin Clinic     Book Again ",
                                          };
  
          // Define a new Adapter
          // First parameter - Context
          // Second parameter - Layout for the row
          // Third parameter - ID of the TextView to which the data is written
          // Forth - the Array of data
  
          ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
            android.R.layout.simple_list_item_1, android.R.id.text1, values);
  
  
          // Assign adapter to ListView
          listView.setAdapter(adapter); 
          
	  }
}
