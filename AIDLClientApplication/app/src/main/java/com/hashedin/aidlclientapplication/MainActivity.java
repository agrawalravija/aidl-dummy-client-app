package com.hashedin.aidlclientapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.hashedin.hvac.HvacAidlInterface;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private HvacAidlInterface hvacAidl;
    private boolean acValue = false;
    private ServiceConnection serviceCon = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
           hvacAidl = HvacAidlInterface.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button_bind).setOnClickListener(v -> {
            Intent intent = new Intent("com.hashedin.hvac.utils.IPCHvacService");
            Intent newIntent = convertImplicitToExplicit(intent);
           // if(newIntent != null)
            bindService(newIntent, serviceCon, BIND_AUTO_CREATE);
        });

        findViewById(R.id.button_get_left_temp).setOnClickListener(v -> {
            try {
                boolean value = hvacAidl.getAcValue();
                Log.i("HvacAIDL", "value: " + value);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        });

        findViewById(R.id.button_get_right_temp).setOnClickListener(v -> {
            try {
                hvacAidl.setAcValue(acValue);
                acValue = !acValue;
                //Log.i("HvacAIDL", "value: " + value);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        });
    }

    public Intent convertImplicitToExplicit(Intent implicitIntent){
        PackageManager pm = getPackageManager();
        List<ResolveInfo> resolveInfoList = pm.queryIntentServices(implicitIntent,0);
        if(resolveInfoList == null || resolveInfoList.size() != 1){
            return null;
        }
        ResolveInfo serviceInfo = resolveInfoList.get(0);
        ComponentName componentName = new ComponentName(serviceInfo.serviceInfo.packageName,serviceInfo.serviceInfo.name);
        Intent explicitIntent = new Intent(implicitIntent);
        explicitIntent.setComponent(componentName);
        return explicitIntent;
    }
}