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
        val alertWorkRequest = OneTimeWorkRequestBuilder<AlertWorker>()
            .setInitialDelay(5, TimeUnit.MINUTES)
            .setBackoffCriteria(BackoffPolicy.LINEAR, 15, TimeUnit.MINUTES)
            .build()

        WorkManager.getInstance(this)
            .enqueueUniqueWork(
                AlertWorker::javaClass.name,
                ExistingWorkPolicy.KEEP,
                alertWorkRequest
            )
    }

    override fun getWorkManagerConfiguration(): Configuration =
        Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()
}