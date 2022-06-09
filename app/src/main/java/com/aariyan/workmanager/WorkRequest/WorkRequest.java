package com.aariyan.workmanager.WorkRequest;

import android.app.Activity;
import android.content.Context;

import androidx.work.BackoffPolicy;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;

import com.aariyan.workmanager.Worker.MainWorker;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class WorkRequest {

    private static WorkRequest workRequest;

    public static WorkRequest getInstance() {
        if (workRequest == null) {
            workRequest = new WorkRequest();
        }
        return workRequest;
    }


    public PeriodicWorkRequest createPeriodicWorkRequest() {
        PeriodicWorkRequest periodicWorkRequest =
                new PeriodicWorkRequest.Builder(MainWorker.class, 15, TimeUnit.MINUTES)
                        .build();

        return periodicWorkRequest;
    }

    public OneTimeWorkRequest createOnetimeRequest(int hour, int minute) {
        Calendar currentDate = Calendar.getInstance();
        Calendar dueDate = Calendar.getInstance();
        // Set Execution around 05:00:00 AM
        dueDate.set(Calendar.HOUR_OF_DAY, hour);
        dueDate.set(Calendar.MINUTE, minute);
        dueDate.set(Calendar.SECOND, 0);
        if (dueDate.before(currentDate)) {
            dueDate.add(Calendar.HOUR_OF_DAY, 24);
        }
        long timeDiff = dueDate.getTime().getTime() - currentDate.getTime().getTime();

        OneTimeWorkRequest oneTimeWorkRequest = new OneTimeWorkRequest.Builder(MainWorker.class)
                .setInitialDelay(timeDiff, TimeUnit.MILLISECONDS)
                .build();
        return oneTimeWorkRequest;
    }
}
