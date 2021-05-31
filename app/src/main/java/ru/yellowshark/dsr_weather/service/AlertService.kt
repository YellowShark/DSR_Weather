package ru.yellowshark.dsr_weather.service

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import ru.yellowshark.dsr_weather.R
import ru.yellowshark.dsr_weather.data.db.entity.TriggerEntity
import ru.yellowshark.dsr_weather.data.remote.response.ForecastResponse
import ru.yellowshark.dsr_weather.domain.repository.ServiceRepository
import ru.yellowshark.dsr_weather.ui.main.MainActivity
import java.util.*
import javax.inject.Inject


@AndroidEntryPoint
class AlertService : Service() {
    companion object {
        private const val TAG = "TAGGG"
        const val TRIGGER_ID = "TRIGGER_ID"
    }

    private val disposables = CompositeDisposable()
    private lateinit var notificationManager: NotificationManager
    @Inject
    lateinit var repository: ServiceRepository

    override fun onBind(intent: Intent): IBinder? = null

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "onCreate: service started")
        initNotificationManager()
        startForeground(
            123, createNotification(
                "",
                getString(R.string.checking_for_updates),
                "",
                isImportant = false,
            )
        )
        disposables.add(
            repository.getTriggers().zipWith(
                repository.getLocations().toObservable(), { triggers, locations ->
                    Log.d(TAG, "onCreate: $triggers\n$locations")
                    if (triggers.isNotEmpty() && locations.isNotEmpty())
                        locations.forEach {
                            loadForecastAndCompareWithTriggers(it.lat, it.lon, triggers)
                        }
                    else
                        stopSelf()
                }
            )
                .doOnError { it.printStackTrace() }
                .subscribeOn(Schedulers.io())
                .subscribe(
                    { },
                    {
                        it.printStackTrace()
                        stopSelf()
                    }
                )
        )
    }

    private fun loadForecastAndCompareWithTriggers(
        lat: Double,
        lon: Double,
        triggers: List<TriggerEntity>
    ) {
        disposables.add(
            repository.getForecast(lat, lon)
                .subscribeOn(Schedulers.io())
                .map { forecast ->
                    triggers.map { trigger ->
                        if (matchedWithTrigger(forecast, trigger))
                            trigger.id to forecast
                        else
                            null
                    }
                }
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError {
                    it.printStackTrace()
                }
                .subscribe(
                    { list ->
                        list.forEach { data ->
                            data?.let { pair ->
                                val detailText = buildString {
                                    this.apply {
                                        append(getString(R.string.curr_temp))
                                        append(": ${pair.second.main.temp.toInt()}")
                                        append(repository.getUnitSymbol())
                                        append("\n")
                                        append(getString(R.string.humidity_detail))
                                        append(": ${pair.second.main.humidity}%\n")
                                        append(getString(R.string.wind_speed))
                                        append(": ${pair.second.wind.speed} ")
                                        append(getString(R.string.m_s))
                                        append("\n")
                                    }
                                }
                                notificationManager.notify(
                                    pair.second.id, createNotification(
                                        pair.first,
                                        pair.second.name,
                                        detailText,
                                        isImportant = true
                                    )
                                )
                            }
                        }
                        stopSelf()
                    },
                    {
                        it.printStackTrace()
                        stopSelf()
                    }
                )
        )
    }

    private fun matchedWithTrigger(
        forecast: ForecastResponse,
        trigger: TriggerEntity
    ): Boolean {
        val timeInMillis = Calendar.getInstance().timeInMillis
        Log.d(TAG, "loadAndCompareForecast: $forecast\n$trigger")
        if (trigger.endMillis > timeInMillis && timeInMillis >= trigger.startMillis)
            if (forecast.main.temp.toInt() == trigger.temp) {
                return if (trigger.wind == null && trigger.humidity == null)
                    true
                else {
                    !(trigger.wind != null && trigger.wind != forecast.wind.speed.toInt() ||
                            trigger.humidity != null && trigger.humidity != forecast.main.humidity)
                }
            }
        return false
    }

    private fun initNotificationManager() {
        notificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                applicationContext.getString(R.string.default_notification_channel_id),
                "Alert Notifications",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationChannel.apply {
                description = "Channel description"
                enableLights(true)
                lightColor = Color.WHITE
                vibrationPattern = longArrayOf(0, 500, 200, 500, 200, 500, 200)
                enableVibration(true)
            }
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_STICKY
    }

    override fun onDestroy() {
        Log.d(TAG, "onDestroy: service stopped")
        disposables.clear()
        super.onDestroy()
    }

    private fun createNotification(
        triggerId: String,
        contentText: String,
        detailText: String,
        isImportant: Boolean
    ): Notification {
        val intent = Intent(applicationContext, MainActivity::class.java)
        intent.apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            putExtra(TRIGGER_ID, triggerId)
        }
        val pendingIntent = PendingIntent.getActivity(
            applicationContext,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        val defaultSoundUri: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        val notification = NotificationCompat.Builder(
            applicationContext,
            applicationContext.getString(R.string.default_notification_channel_id)
        )
            .setContentTitle(contentText)
            .setContentText(detailText)
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText(detailText)
            )
            .setAutoCancel(true)
            .setSmallIcon(R.drawable.ic_trigger)
            .setContentIntent(pendingIntent)
            .setOngoing(true)

        if (isImportant)
            notification.setSound(defaultSoundUri).priority = Notification.PRIORITY_MAX

        return notification.build()
    }
}