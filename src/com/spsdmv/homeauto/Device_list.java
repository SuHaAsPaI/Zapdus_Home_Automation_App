package com.spsdmv.homeauto;

import java.util.Set;
import android.app.ActionBar;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class Device_list extends Activity{


    // textview for connection status
    TextView tSelect;
    ListView pairedListView;
    String asf;
    EditText ed;
    String s;
    String regexp = "\n"; // these are my delimiters
    //An EXTRA to take the device MAC to the next activity
    SharedPreferences someData;
    public static String filename_room="Room";
    public static String EXTRA_DEVICE_ADDRESS;
  
    // Member fields
    private BluetoothAdapter mBtAdapter;
    private ArrayAdapter<String> mPairedDevicesArrayAdapter;
  
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_list);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ActionBar actionBar = getActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

		
        Bundle gotBasket=getIntent().getExtras();
        asf=gotBasket.getString("key1");
  
        tSelect=(TextView)findViewById(R.id.title_paired_devices);
        tSelect.setText("Select btSerial device from paired devices for "+asf+":");
        someData=getSharedPreferences(filename_room, 0);
        
         
        // Initialize array adapter for paired devices
        mPairedDevicesArrayAdapter = new ArrayAdapter<String>(this, R.layout.device_name);
         
        // Find and set up the ListView for paired devices
        pairedListView = (ListView) findViewById(R.id.paired_devices);
        pairedListView.setOnItemClickListener(mDeviceClickListener);
        pairedListView.setAdapter(mPairedDevicesArrayAdapter);
         
    }
    
    @Override
    public void onResume()
    {
      super.onResume();
      //It is best to check BT status at onResume in case something has changed while app was paused etc
      checkBTState();
       
      mPairedDevicesArrayAdapter.clear();// clears the array so items aren't duplicated when resuming from onPause

       
      // Get the local Bluetooth adapter
      mBtAdapter = BluetoothAdapter.getDefaultAdapter();
  
      // Get a set of currently paired devices and append to pairedDevices list
      Set<BluetoothDevice> pairedDevices = mBtAdapter.getBondedDevices();
      
      // Add previously paired devices to the array
      if (pairedDevices.size() > 0) {
          findViewById(R.id.title_paired_devices).setVisibility(View.VISIBLE);//make title viewable
          for (BluetoothDevice device : pairedDevices) {
              mPairedDevicesArrayAdapter.add(device.getName() + "\n" + device.getAddress());
          }
      } else {
          mPairedDevicesArrayAdapter.add("no devices paired");
      }
  }
  
    //method to check if the device has Bluetooth and if it is on.
    //Prompts the user to turn it on if it is off
    private void checkBTState()
    {
        // Check device has Bluetooth and that it is turned on
                mBtAdapter=BluetoothAdapter.getDefaultAdapter(); // CHECK THIS OUT THAT IT WORKS!!!
        if(mBtAdapter==null) {
               Toast.makeText(getBaseContext(), "Device does not support Bluetooth", Toast.LENGTH_SHORT).show();
               finish();
        } else {
          if (!mBtAdapter.isEnabled()) {
            //Prompt user to turn on Bluetooth
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, 1);
            }
          else{
      
          }
          }
        }
     
    // Set up on-click listener for the listview
    private OnItemClickListener mDeviceClickListener = new OnItemClickListener()
    {
        public void onItemClick(AdapterView<?> av, View v, int arg2, long arg3)
        {  
            // Get the device MAC address, which is the last 17 chars in the View
        	someData=getSharedPreferences(filename_room, 0);
            String info = ((TextView) v).getText().toString();
            String address = info.substring(info.length() - 17);
            String [] tokens = null; 
            for(int i=0;i<2;i++){
    	        tokens = info.split(regexp);
    	        //System.out.println(tokens[i]);
    	    }
            Log.d("Tag", info);
            s="Device: "+tokens[0].toString();
          
            
            
            if(asf.equals("Room 1")){
            	SharedPreferences.Editor editor=someData.edit();
				editor.putString("room11", address);
				editor.putString("pd1", s);
				editor.commit();
				Intent sread=new Intent("com.spsdmv.homeauto.MAINACTIVITY");
                startActivity(sread);
            	//Toast.makeText(Device_list.this, "You Clicked at Room 1 & Address :"+address, Toast.LENGTH_SHORT).show();
            }
            
            if(asf.equals("Room 2")){
            	SharedPreferences.Editor editor=someData.edit();
				editor.putString("room22", address);
				editor.putString("pd2", s);
				editor.commit();
				Intent sread=new Intent("com.spsdmv.homeauto.MAINACTIVITY");
                startActivity(sread);
            	//Toast.makeText(Device_list.this, "You Clicked at Room 2 & Address :"+address, Toast.LENGTH_SHORT).show();
            }
            
            if(asf.equals("Room 3")){
            	SharedPreferences.Editor editor=someData.edit();
				editor.putString("room33", address);
				editor.putString("pd3", s);
				editor.commit();
				Intent sread=new Intent("com.spsdmv.homeauto.MAINACTIVITY");
                startActivity(sread);
            	//Toast.makeText(Device_list.this, "You Clicked at Room 3 & Address :"+address, Toast.LENGTH_SHORT).show();
            }
            
            if(asf.equals("Room 4")){
            	SharedPreferences.Editor editor=someData.edit();
				editor.putString("room44", address);
				editor.putString("pd4", s);
				editor.commit();
				Intent sread=new Intent("com.spsdmv.homeauto.MAINACTIVITY");
                startActivity(sread);
            	//Toast.makeText(Device_list.this, "You Clicked at Room 4 & Address :"+address, Toast.LENGTH_SHORT).show();
            }
            finish();
  
            
                                             
        }
    };
    
public void onBackPressed() {
	Intent sread=new Intent("com.spsdmv.homeauto.MAINACTIVITY");
    startActivity(sread);
    finish();
};
      
    @Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	        case android.R.id.home:
	            // app icon in action bar clicked; goto parent activity.
	            this.finish();
	            Intent sread=new Intent("com.spsdmv.homeauto.MAINACTIVITY");
                startActivity(sread);
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }

	}
	

}
