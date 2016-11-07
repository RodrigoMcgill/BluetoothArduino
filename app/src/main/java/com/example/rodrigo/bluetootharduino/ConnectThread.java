package com.example.rodrigo.bluetootharduino;

/**
 * Created by Rodrigo on 2016-11-06.
 */
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.*;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.util.UUID;
/**
 * Created by Rodrigo on 2016-10-19.
 */
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.provider.Telephony;
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
class ConnectThread extends Thread{
    private BluetoothSocket mmSocket;
    private BluetoothDevice mmDevice;
    private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
    private char ch[] ;
    private  static InputStream mmInStream ;
    private  static OutputStream mmOutStream ;

    public ConnectThread(BluetoothDevice savedDevice){
        System.out.println("Called connectThread");
        BluetoothSocket tmp = null;
        mmDevice = savedDevice;

        try{
            tmp = savedDevice.createRfcommSocketToServiceRecord(MY_UUID);
        }catch (IOException e){
            System.out.println("tmp did not connect!");
        }
        System.out.println("Connecting mmSocket to tmp");
        mmSocket = tmp;
    }

    public void run(){
        System.out.println("Everything went alright. Device passed should be the same : " + mmDevice.getName());
        try {
            System.out.println("Attempting to connect with socket");
            mmSocket.connect();
            System.out.println("Socket connected");
        } catch (IOException connectException) {
            try {
                mmSocket.close();
            } catch (IOException closeException) { }
            return;
        }
        try {
            mmInStream = mmSocket.getInputStream();
            mmOutStream = mmSocket.getOutputStream();
        }catch (Exception e){};

    }

    public void ledON() throws IOException {
        System.out.println("sending out on");
        byte[] buffer = {(byte) ('1' & 0x00FF)};
        mmOutStream.write(buffer);
    }

    public void ledOFF() throws IOException{
        byte[] buffer = {(byte) ('0'& 0x00FF)};
        mmOutStream.write(buffer);
    }


    /*public void sendData() throws IOException, InterruptedException {
        System.out.println("sendData() called succesfully ");
        byte [] buff = new byte[1000];
        int myByte = 0;
        mmInStream= mmSocket.getInputStream();
        mmOutStream = mmSocket.getOutputStream();
        System.out.println("dataStream succesfully connected ");
          byte[] buffer = {(byte) ('1' & 0x00FF)};
          System.out.println("sending data..........");
          mmOutStream.write(buffer);
          System.out.println("done sending data..........");
          while(true){
                try {
                        myByte = mmInStream.read();
                        System.out.print("DATA RECEIVED!:----->");
                        System.out.println(myByte);
                    wait(2000);
                }catch(IOException e){
                     break;
                }
          }
    }
    */


}