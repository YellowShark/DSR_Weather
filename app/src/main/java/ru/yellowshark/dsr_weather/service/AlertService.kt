package ru.yellowshark.dsr_weather.service

import android.annotation.SuppressLint
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
import ru.yellowshark.dsr_weather.R
import ru.yellowshark.dsr_weather.domain.repository.Repository
import ru.yellowshark.dsr_weather.ui.main.MainActivity
import javax.inject.Inject


@AndroidEntryPoint
class AlertService : Service() {
    companion object {
        const val TRIGGER_ID = "TRIGGER_ID"
    }
    private lateinit var notificationManager: NotificationManager
    @Inject lateinit var repository: Repository

    override fun onBind(intent: Intent): IBinder? = null

    @SuppressLint("CheckResult")
    override fun onCreate() {
        super.onCreate()
        Log.d("TAGGG", "onCreate: service started")
        initNotificationManager()
        startForeground(
            123, createNotification(
                "",
                getString(R.string.checking_for_updates),
                "",
                isImportant = false,
            )
        )

        repository.requestAlerts().subscribe(
            {
                it.forEach { alert ->
                    Log.d("TAGGG", "alert: $alert. ")
                    if (alert.value.alertData != null)
                        notificationManager.notify(
                            123, createNotification(
                                alert.key,
                                "Hi",
                                alert.value.alertData.toString(),
                                isImportant = true
                            )
                        )
                }
                stopSelf()
            },
            {
                it.printStackTrace()
                stopSelf()
            }
        )
    }

    private fun initNotificationManager() {
        notificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                applicationContext.getString(R.string.default_notification_channel_id),
                "Rewards Notifications",
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
        Log.d("TAGGG", "onDestroy: service stopped")
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
            action = triggerId
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
            notification.setSound(defaultSoundUri)
                .priority = Notification.PRIORITY_MAX

        return notification.build()
    }
}