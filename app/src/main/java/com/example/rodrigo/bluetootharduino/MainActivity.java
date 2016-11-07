package com.example.rodrigo.bluetootharduino;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.Set;
import java.util.UUID;
//Rodrigo : I repaired android
public class MainActivity extends ActionBarActivity {

    TextView txtV = null;
    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothDevice myDevice;
    private BluetoothSocket mySocket;
    private Button myButtonStart;
    private boolean condition = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BluetoothDevice mDevice = null;
        //start

        myButtonStart = (Button)findViewById(R.id.buttonStart);
        myButtonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                while (!condition) {
                    try {
                        findBT();
                    } catch (Exception f) {
                        txtV.setText((CharSequence) f);
                    }
                }

                Intent intent = new Intent(MainActivity.this, LEDActivity.class);
                intent.putExtra("BTdeviceName", myDevice);
                startActivity(intent);

                /*
                ConnectThread connThread = new ConnectThread(myDevice);
                mBluetoothAdapter.cancelDiscovery();
                connThread.start();
                */
                mBluetoothAdapter.cancelDiscovery();
            }
        });

    }

    private void findBT() {
        //checks that device supports bluetooth
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            txtV.setText("You're Device does Not Support Bluetooth");
            finish();
        }
        //ask user to turn on bluetooth
        if (!mBluetoothAdapter.isEnabled()) {
            Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
            discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
            startActivity(discoverableIntent);
        }

        //acquiring all bluetooth available devices and make a SET
        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();

        if (pairedDevices.size() > 0) {
            for(BluetoothDevice device: pairedDevices){
                System.out.println("Printing device: "+ device.getName());
                System.out.println("Printing address: "+ device.getAddress());
                if(device.getName().equals("HC-05")){
                    condition = true;
                    myDevice = device;

                    break;
                }
            }
        }
    }
/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    */
}