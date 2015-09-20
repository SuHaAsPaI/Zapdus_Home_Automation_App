package com.spsdmv.homeauto;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;


public class MainActivity extends Activity {

    ListView list;
    String[] web = {"Room 1","Room 2","Room 3","Room 4"} ;
    String[] device_pair = {"No Device Selected","No Device Selected","No Device Selected","No Device Selected"} ;
    int activateor=0;

    final Context context = this;
    String[] rooms={"room11","room22","room33","room44"};

    Integer[] imageId = { R.drawable.aas,R.drawable.as,R.drawable.ssa, R.drawable.ass };
    SharedPreferences someData;
    public static String EXTRA_DEVICE_ADDRESS;

    String address;
    private BluetoothAdapter mBtAdapter;
    public static String filename_room="Room";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setup_variable();
        checkBTState();

        CustomList adapter = new CustomList(MainActivity.this, web, imageId,device_pair);
        list=(ListView)findViewById(R.id.list);
        list.setAdapter(adapter);
        
        /*ColorDrawable devidrColor = new ColorDrawable(this.getResources().getColor(R.color.devidrColor));
        	list.setDivider(devidrColor);
        	list.setDividerHeight(1);*/
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                checkBTState();
                someData = getSharedPreferences(filename_room, 0);
                address = someData.getString(rooms[position], "******");
                //Toast.makeText(MainActivity.this, address, Toast.LENGTH_SHORT).show();
                if(activateor==0){

                if (address.equals("******")) {
                    device_notConnected();
                } else {
                    Intent il = new Intent(MainActivity.this, Main_code.class);
                    il.putExtra(EXTRA_DEVICE_ADDRESS, address);
                    startActivity(il);
                }
                }
            }
        });
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub
                //rename_dialog(position);
                rename_select(position);
                activateor=1;
                //Toast.makeText(MainActivity.this, "You Clicked at " +web[+ position], Toast.LENGTH_SHORT).show();
                return false;
            }

        });
    }

    private void setup_variable() {
        // TODO Auto-generated method stub
        someData=getSharedPreferences(filename_room, 0);
        web[0]=someData.getString("R1","Room 1");
        web[1]=someData.getString("R2","Room 2");
        web[2]=someData.getString("R3","Room 3");
        web[3]=someData.getString("R4","Room 4");
        
        device_pair[0]=someData.getString("pd1","No Device Selected");
        device_pair[1]=someData.getString("pd2","No Device Selected");
        device_pair[2]=someData.getString("pd3","No Device Selected");
        device_pair[3]=someData.getString("pd4","No Device Selected");

    }
    

    private void checkBTState()
    {
        // Check device has Bluetooth and that it is turned on
        mBtAdapter= BluetoothAdapter.getDefaultAdapter(); // CHECK THIS OUT THAT IT WORKS!!!
        if(mBtAdapter==null) {
            Toast.makeText(getBaseContext(), "Device does not support Bluetooth", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            if (!mBtAdapter.isEnabled()) {
                //Prompt user to turn on Bluetooth
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, 1);
            }
        }
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
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent sread3=new Intent("com.spsdmv.homeauto.SETTING1");
                startActivity(sread3);
                return true;
            case R.id.action_resettings:
                reset_device();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    
    

    private void rename_dialog(int ik){
        final Dialog dialog1 = new Dialog(context);
        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog1.setContentView(R.layout.activity_dialog_rename);
        final int jh=ik;
        final String[] web2 = {
                "R1",
                "R2",
                "R3",
                "R4"
        };
        final EditText ed=(EditText)dialog1.findViewById(R.id.editname);

        Button dialogButton1 = (Button) dialog1.findViewById(R.id.dialog_button1);
        // if button is clicked, close the custom dialog
        dialogButton1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String sd1=ed.getText().toString();
                someData=getSharedPreferences(filename_room, 0);
                SharedPreferences.Editor editor=someData.edit();
                editor.putString(web2[jh], sd1);
                editor.commit();
                web[jh]=sd1;
                activateor=0;
                dialog1.dismiss();
            }
        });

        dialog1.show();
        dialog1.setOnCancelListener(new OnCancelListener() {
			
			@Override
			public void onCancel(DialogInterface dialog) {
				// TODO Auto-generated method stub
				activateor=0;	
			}
		});

    }
    
    


    private void device_notConnected(){

        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setMessage("Select the device for this Room in Bluetooth Device");
        builder1.setCancelable(true);
        builder1.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();

	}


    private void rename_select(int lk){
        final Dialog dialog2 = new Dialog(context);
        dialog2.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog2.setContentView(R.layout.activity_menu_rename_select);
        dialog2.setTitle("Select");
        final int kk=lk;
        activateor=1;
        String[] menulist_name = {"Rename Name","Bluetooth Device"};
        final String str[]={"Room 1","Room 2","Room 3","Room 4"};
        ListView menulist1=(ListView)dialog2.findViewById(R.id.ls);
        ArrayAdapter<String>  mPairedDevicesArrayAdapter = new ArrayAdapter<String>(this, R.layout.activity_rem,menulist_name);
        menulist1.setAdapter(mPairedDevicesArrayAdapter);
        menulist1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                switch (position) {

                    case 0:
                        //Calling Rename Function
                        rename_dialog(kk);
                        dialog2.dismiss();
                        break;
                    case 1:
                        //Calling Class Device_list for selecting device
                        String zaabu = str[kk];
                        Bundle basket = new Bundle();
                        basket.putString("key1", zaabu);
                        Intent asd = new Intent("com.spsdmv.homeauto.DEVICE_LIST");
                        asd.putExtras(basket);
                        startActivity(asd);
                        activateor=0;
                        dialog2.dismiss();
                        finish();
                        break;
                }

            }
        });
        dialog2.show();
        dialog2.setOnCancelListener(new OnCancelListener() {
			
			@Override
			public void onCancel(DialogInterface dialog) {
				// TODO Auto-generated method stub
				activateor=0;
				
			}
		});

    }


    private void reset_device(){

        AlertDialog.Builder builder2 = new AlertDialog.Builder(context);
        builder2.setMessage("Do you want to reset the Zapdus?");
        builder2.setCancelable(true);
        final String[] web2 = {
                "R1",
                "R2",
                "R3",
                "R4"
        };
        final String[] key_pd={"pd1","pd2","pd3","pd4"};
        final String[] web3 = {"Room 1","Room 2","Room 3","Room 4"} ;
        builder2.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        someData = getSharedPreferences(filename_room, 0);
                        SharedPreferences.Editor editor = someData.edit();
                        for (int i = 0; i <= 3; i++) {
                            editor.putString(web2[i], web3[i]);
                            editor.commit();
                            web[i] = web3[i];
                        }
                        for (int i = 0; i <= 3; i++) {
                            editor.putString(rooms[i],"******");
                            editor.commit();

                        }
                        for(int i=0;i<=3;i++){
                        	editor.putString(key_pd[i],"No Device Selected");
                            editor.commit();
                        }
                        Toast.makeText(MainActivity.this, "Zapdus Resetted\nStart Zapdus Again", Toast.LENGTH_LONG).show();
                        finish();
                        dialog.cancel();
                    }
                });
        builder2.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

        AlertDialog alert12 = builder2.create();
        alert12.show();

    }
}
