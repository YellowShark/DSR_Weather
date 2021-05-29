package ru.yellowshark.dsr_weather.worker

import android.content.Context
import android.content.Intent
import androidx.hilt.work.HiltWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import ru.yellowshark.dsr_weather.service.AlertService

@HiltWorker
class AlertWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted params: WorkerParameters,
) : Worker(appContext, params) {

    override fun doWork(): Result {
        applicationContext.startForegroundService(
            Intent(
                applicationContext,
                AlertService::class.java
            )
        )
        return Result.retry()
    }
}