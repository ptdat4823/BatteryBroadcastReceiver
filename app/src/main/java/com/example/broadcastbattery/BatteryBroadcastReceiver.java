package com.example.broadcastbattery;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;

public class BatteryBroadcastReceiver extends BroadcastReceiver {

    BatteryInfo batteryInfo;
    public BatteryBroadcastReceiver(BatteryInfo batteryInfo)
    {
        this.batteryInfo = batteryInfo;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().compareTo(Intent.ACTION_BATTERY_CHANGED) == 0)
        {
            //receive percent of battery
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
            int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

            float batteryPercent = level * 100 / (float)scale;
            this.batteryInfo.setPercentBattery(String.valueOf((int)batteryPercent));

            //check if battery is charging or not
            int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
            boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING ||
                    status == BatteryManager.BATTERY_STATUS_FULL;
            this.batteryInfo.setStateCharging(isCharging);

            boolean isLowBattery = intent.getBooleanExtra(BatteryManager.EXTRA_BATTERY_LOW, false);
            this.batteryInfo.setBatteryStatus(isLowBattery);

            this.batteryInfo.setStateCharging(isCharging);
        }
    }

    public interface BatteryInfo{
        void setStateCharging(boolean isCharging);
        void setPercentBattery(String percent);
        void setBatteryStatus(boolean isBatteryLow);
    }
}
