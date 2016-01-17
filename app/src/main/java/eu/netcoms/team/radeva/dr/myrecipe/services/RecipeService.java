package eu.netcoms.team.radeva.dr.myrecipe.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

public class RecipeService extends Service {
    @Override
    public void onCreate() {
        super.onCreate();
        Toast.makeText(this, "Service created", Toast.LENGTH_SHORT).show();
    }

    @Override
    public IBinder onBind(Intent intent) {
       return  null;
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "Service Started", Toast.LENGTH_SHORT).show();
        stopSelf();
        return super.onStartCommand(intent, flags, startId);
   }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "Service Stopped", Toast.LENGTH_SHORT).show();
        super.onDestroy();
    }


}
