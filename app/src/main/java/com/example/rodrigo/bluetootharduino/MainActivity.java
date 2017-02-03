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

        myButtonStart = (Button)findViewById(R.id.buttonStart);  //starts the first steps of BT protocols
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
                mBluetoothAdapter.cancelDiscovery();
            }
        });

    }
   /*
   function that finds actively devices a proximity
    */
    private void findBT() {
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            txtV.setText("The current  Device does not Support Bluetooth");
            for (int time=0;time < 5000 ;time++){};
            finish();
        }
        //ask user to turn on bluetooth
        if (!mBluetoothAdapter.isEnabled()) {
            Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
            discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
            startActivity(discoverableIntent);
        }

        //acquiring all bluetooth available devices and make a SET. Hardcoded name for HC-05
        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
        if (pairedDevices.size() > 0) {
            for(BluetoothDevice device: pairedDevices){
                System.out.println("Printing device: "+ device.getName());
                System.out.println("Printing address: "+ device.getAddress());
                if(device.getName().equals("HC-05")){  //the name can be change to target any Bluetooth module
                    condition = true;
                    myDevice = device;
                    break;
                }
            }
        }
    }
}