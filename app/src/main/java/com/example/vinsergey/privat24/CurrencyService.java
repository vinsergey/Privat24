package com.example.vinsergey.privat24;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.util.Log;
import com.example.vinsergey.privat24.db.AppDatabase;
import com.example.vinsergey.privat24.rest.ModelCurrency;
import com.example.vinsergey.privat24.rest.RestClient;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import static com.example.vinsergey.privat24.MainActivity.mapEntity;
import static com.example.vinsergey.privat24.MainActivity.currentDateTime;
import static com.example.vinsergey.privat24.MainActivity.hasConnection;

public class CurrencyService extends Service {
    private static final String LOG_TAG = "MyLog";

    public CurrencyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(LOG_TAG, "onStart");
        MyService();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.d(LOG_TAG, "onDestroy");
        super.onDestroy();
    }

    private void MyService() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        if (hasConnection(getApplicationContext())) {
                            getData();
                        }
                        TimeUnit.MINUTES.sleep(1);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    private void getData(){
        RestClient.getInstance().getAllCurrency().enqueue(new Callback<List<ModelCurrency>>() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onResponse(@NonNull Call<List<ModelCurrency>> call, @NonNull Response<List<ModelCurrency>> response) {

                TimeZone tz = TimeZone.getTimeZone("GMT+03:00");
                Calendar c = Calendar.getInstance(tz);

                currentDateTime = String.format("%02d", c.get(Calendar.DAY_OF_MONTH))+"."+
                        String.format("%02d", c.get(Calendar.MONTH)+1)+"."+
                        String.format("%02d", c.get(Calendar.YEAR))+" "+
                        String.format("%02d", c.get(Calendar.HOUR_OF_DAY))+":"+
                        String.format("%02d", c.get(Calendar.MINUTE))+":"+
                        String.format("%02d", c.get(Calendar.SECOND));

                AppDatabase.getInstance(getApplicationContext()).currencyDao().saveCurrency(mapEntity(response));
            }

            @Override
            public void onFailure(@NonNull Call<List<ModelCurrency>> call, @NonNull Throwable t) {

            }
        });
    }
}
