package ar.edu.ort.bmon.rootsapp.ui.plant;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import ar.edu.ort.bmon.rootsapp.R;
import ar.edu.ort.bmon.rootsapp.constants.Constants;

public class ReminderBroadcast extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String tarea = intent.getStringExtra("tarea");
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,"notifyORT")
                .setSmallIcon(getTaskIcon(tarea))
                .setContentTitle("Tenés una tarea pendiente")
                .setContentText("No te olvides de " + tarea + "!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);

        notificationManagerCompat.notify(200, builder.build());
    }

    private int getTaskIcon(String tarea){
        int icon = 0;

        switch (tarea) {
            case Constants.ADD_TASK_FUMIGATE:
                icon = R.drawable.ic_fumigate_spray;
                break;
            case Constants.ADD_TASK_FERTILIZE:
                icon = R.drawable.ic_fertilizer;
                break;
            case Constants.ADD_TASK_PRUNE:
                icon = R.drawable.poda;
                break;
            case Constants.ADD_TASK_RAISE_HUMIDITY:
                icon = R.drawable.ic_water_drop;
                break;
            case Constants.ADD_TASK_LOWER_HUMIDITY:
                icon = R.drawable.ic_no_water_drop;
                break;
            case Constants.ADD_TASK_CHECK_PLAGES:
                icon = R.drawable.ic_search_bugs;
                break;
        }

        return icon;
    }
}
