package com.example.broadcastreceiver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;

public class MainActivity extends AppCompatActivity {

    private Switch wifiswitch;
    private WifiManager wifiManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        wifiswitch = findViewById(R.id.wifiswitch);
        wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);

        wifiswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    wifiManager.setWifiEnabled(true);
                    wifiswitch.setText("Wifi is ON");
                } else {
                    wifiManager.setWifiEnabled(false);
                    wifiswitch.setText("Wifi is OFF");

                }
            }
        });
        if (wifiManager.isWifiEnabled()) {
            wifiswitch.setChecked(true);
            wifiswitch.setText("Wifi is ON");
        } else {
            wifiswitch.setChecked(false);
            wifiswitch.setText("Wifi is OFF");
        }
    }

    @Override
    protected void onStart() {
        IntentFilter intentFilter = new IntentFilter((WifiManager.WIFI_STATE_CHANGED_ACTION));
        registerReceiver(wifistatereceiver,intentFilter);
        super.onStart();
    }

    @Override
    protected void onStop() {
        unregisterReceiver(wifistatereceiver);
        super.onStop();
    }

    private BroadcastReceiver wifistatereceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int wifistateextra = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, wifiManager.WIFI_STATE_UNKNOWN);
            switch (wifistateextra) {
                case WifiManager.WIFI_STATE_ENABLED:
                    wifiswitch.setChecked(true);
                    wifiswitch.setText("Wifi is ON");
                    break;
                case WifiManager.WIFI_STATE_DISABLED:
                    wifiswitch.setChecked(false);
                    wifiswitch.setText("Wifi is OFF");
                    break;
            }
        }
    };
}
