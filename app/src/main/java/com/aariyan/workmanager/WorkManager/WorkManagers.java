package com.aariyan.workmanager.WorkManager;

import android.content.Context;

import androidx.work.WorkManager;

import com.aariyan.workmanager.WorkRequest.WorkRequest;

public class WorkManagers {

    private Context context;
    private WorkManager workManager;

    public WorkManagers(Context context) {
        this.context = context;
        workManager = WorkManager.getInstance(context);
    }

    public void enqueueWorkRequest() {
        workManager.enqueue(WorkRequest.getInstance().createPeriodicWorkRequest());
    }

}
