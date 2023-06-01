package com.example.broadcastbattery;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ApplicationErrorReport;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.BatteryManager;
import android.os.Bundle;

import com.example.broadcastbattery.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements BatteryBroadcastReceiver.BatteryInfo {

    ActivityMainBinding binding;

    BatteryBroadcastReceiver receiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());

        //register Broadcast
        receiver = new BatteryBroadcastReceiver(this);
        IntentFilter batteryFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = this.registerReceiver(receiver, batteryFilter);

        //get info battery percent
        int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

        float batteryPercent = level * 100 / (float)scale;
        this.setPercentBattery(String.valueOf((int)batteryPercent));

        //get info is battery charging
        int status = batteryStatus.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
        boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING ||
                status == BatteryManager.BATTERY_STATUS_FULL;
        this.setStateCharging(isCharging);

        //check if battery is low or not
        boolean isLowBattery = batteryStatus.getBooleanExtra(BatteryManager.EXTRA_BATTERY_LOW, false);
        this.setBatteryStatus(isLowBattery);

        setContentView(binding.getRoot());
    }

    @Override
    public void setStateCharging(boolean isCharging) {
        if(isCharging)
            binding.batteryStatus.setText("The phone is charging");
        else
            binding.batteryStatus.setText("the phone is not charging");
    }

    @Override
    public void setPercentBattery(String percent) {
        binding.batteryPercent.setText(percent + "%");
    }

    @Override
    public void setBatteryStatus(boolean isBatteryLow) {
        if(isBatteryLow)
            binding.theme.setBackgroundColor(Color.YELLOW);
        else
            binding.theme.setBackgroundColor(Color.GREEN);
    }
}