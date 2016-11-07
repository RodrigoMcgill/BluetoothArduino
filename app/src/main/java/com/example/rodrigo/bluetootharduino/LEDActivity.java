package com.example.rodrigo.bluetootharduino;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.io.IOException;

public class LEDActivity extends AppCompatActivity {
    private BluetoothDevice myDevice;
    private  ConnectThread connThread;
    private Button buttonLedON;
    private Button buttonLedOFF;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("new activity called");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_led);
        buttonLedON = (Button)findViewById(R.id.buttonON);
        buttonLedOFF = (Button)findViewById(R.id.buttonOFF);
        Intent myIntent = getIntent();
        myDevice = getIntent().getExtras().getParcelable("BTdeviceName");
        connThread = new ConnectThread(myDevice);
        System.out.println("calling connThread");
        connThread.start();
        led();
    }

    public  void led(){
        System.out.println("led class called");


        buttonLedON.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    System.out.println("Turn on");
                    connThread.ledON();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        buttonLedOFF.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                try {
                    System.out.println("Turn off");
                    connThread.ledOFF();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_led, menu);
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