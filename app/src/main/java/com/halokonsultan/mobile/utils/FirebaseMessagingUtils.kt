package com.halokonsultan.mobile.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.halokonsultan.mobile.R
import com.halokonsultan.mobile.data.BaseRepository
import com.halokonsultan.mobile.ui.main.MainActivity
import javax.inject.Inject

class FirebaseMessagingUtils: FirebaseMessagingService() {

    companion object {
        const val CHANNEL_ID = "halo konsultan channel"
        const val CHANNEL_NAME = "com.halokonsultan.mobile.fcmnotification"
    }

    override fun onMessageReceived(message: RemoteMessage) {
        if (message.notification != null) {
            generateNotification(message.notification!!.title ?: "Default title",
                message.notification!!.body ?: "Default message")
        }
    }

    private fun generateNotification(title: String, message: String) {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)

        val builder = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setAutoCancel(true)
            .setVibrate(longArrayOf(500,0,500,0))
            .setOnlyAlertOnce(true)
            .setContentIntent(pendingIntent)
            .setContentTitle(title)
            .setContentText(message)

        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH)
            manager.createNotificationChannel(notificationChannel)
        }

        manager.notify(101, builder.build())
    }
}