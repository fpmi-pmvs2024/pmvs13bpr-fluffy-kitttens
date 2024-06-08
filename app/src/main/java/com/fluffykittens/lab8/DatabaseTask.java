package com.fluffykittens.lab8;

import android.os.AsyncTask;

import java.lang.ref.WeakReference;

public class DatabaseTask extends AsyncTask<Void, Void, Void> {
    private WeakReference<Game> activityReference;

    DatabaseTask(Game activity) {
        activityReference = new WeakReference<>(activity);
    }

    @Override
    protected Void doInBackground(Void... voids) {
        Game activity = activityReference.get();
        if (activity == null || activity.isFinishing()) return null;

        activity.setRecordIntoDatabase();
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        // Вы можете выполнить действия здесь после завершения выполнения setRecordIntoDatabase()
    }
}