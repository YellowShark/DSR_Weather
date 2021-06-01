package ru.yellowshark.dsr_weather.app

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.*
import dagger.hilt.android.HiltAndroidApp
import io.reactivex.plugins.RxJavaPlugins
import ru.yellowshark.dsr_weather.worker.AlertWorker
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltAndroidApp
class App : Application(), Configuration.Provider {
    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    override fun onCreate() {
        super.onCreate()
        RxJavaPlugins.setErrorHandler { it.printStackTrace() }
        initWorker()
    }

    private fun initWorker() {
        val constraints =
            Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()

        val alertWorkRequest = OneTimeWorkRequestBuilder<AlertWorker>()
            .setConstraints(constraints)
            .setBackoffCriteria(BackoffPolicy.LINEAR, 1, TimeUnit.MINUTES)
            .build()

        WorkManager.getInstance(this)
            .enqueueUniqueWork(
                AlertWorker::javaClass.name,
                ExistingWorkPolicy.REPLACE,
                alertWorkRequest
            )
    }

    override fun getWorkManagerConfiguration(): Configuration =
        Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()
}