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
        int icon;
        if(tarea.equals(Constants.ADD_TASK_FUMIGATE)){
            icon = R.drawable.fumigate;
        } else if (tarea.equals(Constants.ADD_TASK_FERTILIZE)){
            icon = R.drawable.fertilize;
        } else {
            icon = R.drawable.poda;
        }
        return icon;
    }
}
