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
import ru.yellowshark.dsr_weather.R
import ru.yellowshark.dsr_weather.domain.repository.Repository
import ru.yellowshark.dsr_weather.ui.main.MainActivity
import javax.inject.Inject


@AndroidEntryPoint
class AlertService : Service() {
    @Inject
    lateinit var repository: Repository
    var alerts: List<Any?> = arrayListOf()

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        Log.d("TAGGG", "onCreate: service started")
        startForeground(123, createNotification(
            getString(R.string.checking_for_updates),
            "",
            isImportant = false)
        )

        repository.requestAlerts().subscribe(
            {
                it.forEach {
                    Log.d("TAGGG", "onCreate: $it. ")
                    stopSelf()
                }
            },
            {
                it.printStackTrace()
                stopSelf()
            }
        )
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_STICKY
    }

    private fun createNotification(
        contentText: String,
        detailText: String,
        isImportant: Boolean
    ): Notification {
        val intent = Intent(applicationContext, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(applicationContext, 0, intent, 0)
        val notificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val defaultSoundUri: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                applicationContext.getString(R.string.default_notification_channel_id),
                "Rewards Notifications",
                if (isImportant) NotificationManager.IMPORTANCE_HIGH else NotificationManager.IMPORTANCE_LOW
            )
            notificationChannel.description = "Channel description"
            if (isImportant) {
                notificationChannel.enableLights(true)
                notificationChannel.lightColor = Color.WHITE
                notificationChannel.vibrationPattern = longArrayOf(0, 500, 200, 500, 200, 500, 200)
                notificationChannel.enableVibration(true)
            }
            notificationManager.createNotificationChannel(notificationChannel)
        }

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
//            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent)
            .setOngoing(true)
//            .setPriority(Notification.PRIORITY_MAX)

        if (isImportant)
            notification.setSound(defaultSoundUri)

        return notification.build()
    }

}