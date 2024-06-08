package com.fluffykittens.lab8.data;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import androidx.lifecycle.LiveData;

import java.util.concurrent.ExecutorService;

public class UserRepository {
    private final UserDao userDao;
    private final ExecutorService executorService;

    public UserRepository(Context context) {
        AppDatabase db = AppDatabaseProvider.getAppDb(context);
        userDao = db.userDao();
        executorService = AppDatabaseProvider.getDatabaseWriteExecutor();
    }

    public void checkUserExists(String username, UserExistsCallback callback) {
        executorService.execute(() -> {
            LiveData<UserData> userLiveData = userDao.getByUsername(username);
            new Handler(Looper.getMainLooper()).post(() -> userLiveData.observeForever(user -> {
                callback.onUserChecked(user);
                userLiveData.removeObserver(callback::onUserChecked);
            }));
        });
    }

    public void getUserByUsername(String username, UserFetchCallback callback) {
        executorService.execute(() -> {
            LiveData<UserData> userLiveData = userDao.getByUsername(username);
            new Handler(Looper.getMainLooper()).post(() -> userLiveData.observeForever(user -> {
                callback.onUserFetched(user);
                userLiveData.removeObserver(callback::onUserFetched);
            }));
        });
    }

    public interface UserExistsCallback {
        void onUserChecked(UserData user);
    }

    public interface UserFetchCallback {
        void onUserFetched(UserData user);
    }

    public void insertUser(String username, String password) {
        executorService.execute(() -> {
            UserData newUser = new UserData();
            newUser.username = username;
            newUser.password = password + "/0/0/0";
            userDao.insert(newUser);
        });
    }
}


