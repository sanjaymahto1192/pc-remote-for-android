package hu.za.pc_remote.ui;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import hu.za.pc_remote.R;
import hu.za.pc_remote.transport.ConnectionHandlingService;
import hu.za.pc_remote.transport.TransportManager;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Set;
import java.util.UUID;

/**
 * Created by IntelliJ IDEA.
 * User: Andor
 * Date: 9/22/11
 * Time: 6:49 PM
 * To change this template use File | Settings | File Templates.
 */
public class ConnectionSettings extends UIActivityBase {

    private static final String tag = "ConnectionSettings";
    private boolean mIsBound;
    private static final UUID mServiceUUID = UUID.fromString("01234567-0000-1000-8000-00805F9B34FB");
    private ConnectionHandlingService mConnService;
    private ArrayAdapter mDevicesAdapter;
    private String mAddress;

    private AdapterView.OnItemClickListener mDeviceClickListener = new AdapterView.OnItemClickListener() {


        public void onItemClick(AdapterView<?> av, View v, int arg2, long arg3) {
            String info = mDevicesAdapter.getItem(arg2).toString();
            mAddress = info.substring(info.length() - 17);
            doBindService();
        }
    };

    private ServiceConnection mConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder service) {
            mConnService = ((ConnectionHandlingService.LocalBinder) service).getService();

            BluetoothDevice device = BluetoothAdapter.getDefaultAdapter().getRemoteDevice(mAddress);
            connectToDevice(device);

            Toast.makeText(ConnectionSettings.this, "Connect",
                    Toast.LENGTH_SHORT).show();
        }

        public void onServiceDisconnected(ComponentName className) {
            mConnService = null;
            Toast.makeText(ConnectionSettings.this, "Disconnect",
                    Toast.LENGTH_SHORT).show();
        }
    };

    void doBindService() {
        bindService(new Intent(ConnectionSettings.this,
                ConnectionHandlingService.class), mConnection, Context.BIND_AUTO_CREATE);


        mIsBound = true;
    }

    void doUnbindService() {
        if (mIsBound) {
            unbindService(mConnection);
            mIsBound = false;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        doUnbindService();
    }

    private View.OnClickListener mBindListener = new View.OnClickListener() {
        public void onClick(View v) {
            doBindService();
        }
    };

    private View.OnClickListener mUnbindListener = new View.OnClickListener() {
        public void onClick(View v) {
            doUnbindService();
        }
    };

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.consettings);

        ListView listView = (ListView) findViewById(R.id.devicelist);
        mDevicesAdapter = new ArrayAdapter(this, R.layout.devicelistitem);
        listView.setAdapter(mDevicesAdapter);
        listView.setOnItemClickListener(mDeviceClickListener);

        Set<BluetoothDevice> devices = BluetoothAdapter.getDefaultAdapter().getBondedDevices();
        if (devices.size() > 0) {
            findViewById(R.id.devicelist).setVisibility(View.VISIBLE);
            for (BluetoothDevice device : devices) {
                mDevicesAdapter.add(device.getName() + "\n" + device.getAddress());
            }
        } else {
            String noDevices = "No paired device found";
            mDevicesAdapter.add(noDevices);
        }

    }

    private void connectToDevice(BluetoothDevice device) {
        BluetoothSocket socket = null;
        try {
            socket = device.createRfcommSocketToServiceRecord(mServiceUUID);
            socket.connect();
            TransportManager transportManager = new TransportManager(new ObjectOutputStream(socket.getOutputStream()));
            mConnService.setTransportManager(transportManager);
        } catch (IOException e) {
            if (socket != null)
                try {
                    socket.close();
                } catch (IOException e1) {
                    Log.e(tag, "Failed to close socket", e1);
                }
            Log.e(tag, "Failed to open Socket:", e);

        }
    }
}