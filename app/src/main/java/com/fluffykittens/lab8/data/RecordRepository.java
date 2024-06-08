package com.fluffykittens.lab8.data;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RecordRepository {
    private final RecordDao recordDao;
    private final UserRepository userRepository;
    private final ExecutorService executorService;

    public RecordRepository(Context context) {
        AppDatabase db = AppDatabaseProvider.getAppDb(context);
        recordDao = db.recordDao();
        userRepository = new UserRepository(context);
        executorService = AppDatabaseProvider.getDatabaseWriteExecutor();
    }

    public void getRecordsByUsername(String username, RecordsFetchCallback callback) {
        executorService.execute(() -> {
            LiveData<List<RecordData>> recordsLiveData = recordDao.getAllByUsername(username);
            new Handler(Looper.getMainLooper()).post(() -> recordsLiveData.observeForever(records -> {
                callback.onRecordsFetched(records);
                recordsLiveData.removeObserver(callback::onRecordsFetched);
            }));
        });
    }

    // Method to get records by userId
    public void getRecordsByUserId(Integer userId, RecordsFetchCallback callback) {
        executorService.execute(() -> {
            LiveData<List<RecordData>> recordsLiveData = recordDao.getAllByUserId(userId);
            new Handler(Looper.getMainLooper()).post(() -> recordsLiveData.observeForever(records -> {
                callback.onRecordsFetched(records);
                recordsLiveData.removeObserver(callback::onRecordsFetched);
            }));
        });
    }

    // Modified method to insert a record with constructor arguments
    public void insertRecord(Integer userId, Integer result) {
        RecordData record = new RecordData();
        record.userId = userId;
        record.result = result;
        executorService.execute(() -> recordDao.insert(record));
    }

    public void insertRecord(String username, Integer result, InsertCallback callback) {
        executorService.execute(() -> userRepository.getUserByUsername(username, userData -> {
            if (userData != null) {
                RecordData record = new RecordData();
                record.userId = userData.uid;
                record.result = result;
                executorService.execute(() -> {
                    recordDao.insert(record);
                    new Handler(Looper.getMainLooper()).post(() -> callback.onInsertSuccess(record));
                });
            } else {
                new Handler(Looper.getMainLooper()).post(() -> callback.onInsertFailure("User not found"));
            }
        }));
    }

    // New method to delete a record
    public void deleteRecord(RecordData record) {
        executorService.execute(() -> recordDao.delete(record));
    }

    public interface RecordsFetchCallback {
        void onRecordsFetched(List<RecordData> records);
    }

    public interface InsertCallback {
        void onInsertSuccess(RecordData record);

        void onInsertFailure(String message);
    }
}
