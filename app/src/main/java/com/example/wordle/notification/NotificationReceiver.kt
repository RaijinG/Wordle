package com.example.wordle.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.wordle.R
import com.example.wordle.main.MainActivity

/**
 * NotificationReceiver handles the reception of broadcast intents for notifications.
 * It creates and displays a notification when triggered.
 */
class NotificationReceiver : BroadcastReceiver() {
    /**
     * Called when the BroadcastReceiver receives an Intent broadcast.
     * This method creates and displays a notification.
     * @param context The Context in which the receiver is running.
     * @param intent The Intent being received.
     */
    override fun onReceive(context: Context, intent: Intent) {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel("CHANNEL_ID", "Wordle Notifications", NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(channel)
        }

        val notificationIntent = Intent(context, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        val notification = NotificationCompat.Builder(context, "CHANNEL_ID")
            .setContentTitle(context.getString(R.string.notification1))
            .setContentText(context.getString(R.string.notification2))
            .setSmallIcon(R.drawable.ic_launcher_round)
            .setContentIntent(pendingIntent)
            .build()

        notificationManager.notify(1, notification)
    }
}