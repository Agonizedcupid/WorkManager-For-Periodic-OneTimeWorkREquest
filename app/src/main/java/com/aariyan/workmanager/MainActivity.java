package com.aariyan.workmanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.work.BackoffPolicy;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import android.os.Bundle;

import com.aariyan.workmanager.WorkManager.WorkManagers;
import com.aariyan.workmanager.WorkRequest.WorkRequest;
import com.aariyan.workmanager.Worker.MainWorker;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private WorkManager workManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //call the worker Manager to start the work:
//        WorkManagers managers = new WorkManagers(this);
//        managers.enqueueWorkRequest();

//        workManager = WorkManager.getInstance(this);
//
//        PeriodicWorkRequest periodicWorkRequest =
//                new PeriodicWorkRequest.Builder(MainWorker.class, 15, TimeUnit.MINUTES)
//                        .setBackoffCriteria(BackoffPolicy.LINEAR, PeriodicWorkRequest.MIN_BACKOFF_MILLIS, TimeUnit.MILLISECONDS)
//                        .build();
//
//        //workManager.enqueue(periodicWorkRequest);
//
//        workManager.enqueueUniquePeriodicWork(
//                "SYNC_DATA_WORK_NAME",
//                ExistingPeriodicWorkPolicy.KEEP, //Existing Periodic Work policy
//                periodicWorkRequest //work request
//        );

        //trying to start the service when the app launch for the first time by worker:
        //Log.d(TAG, "startServiceViaWorker called");
        String UNIQUE_WORK_NAME = "StartMyServiceViaWorker";
        //String WORKER_TAG = "MyServiceWorkerTag";
        WorkManager workManager = WorkManager.getInstance(MainActivity.this);

        // As per Documentation: The minimum repeat interval that can be defined is 15 minutes (
        // same as the JobScheduler API), but in practice 15 doesn't work. Using 16 here
        PeriodicWorkRequest request =
                new PeriodicWorkRequest.Builder(
                        MainWorker.class,
                        16,
                        TimeUnit.MINUTES)
                        //.addTag(WORKER_TAG)
                        .build();


        // below method will schedule a new work, each time app is opened
        //workManager.enqueue(request);

        // to schedule a unique work, no matter how many times app is opened i.e. startServiceViaWorker gets called
        // https://developer.android.com/topic/libraries/architecture/workmanager/how-to/unique-work
        // do check for AutoStart permission
        //workManager.enqueueUniquePeriodicWork(UNIQUE_WORK_NAME, ExistingPeriodicWorkPolicy.KEEP, request);
        int hour = 13;
        int minute = 52;
        workManager.enqueue(WorkRequest.getInstance().createOnetimeRequest(hour, minute));
    }
}