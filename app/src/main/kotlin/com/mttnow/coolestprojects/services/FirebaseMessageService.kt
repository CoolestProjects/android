package com.mttnow.coolestprojects.services

import android.app.PendingIntent
import android.content.Intent
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationManagerCompat
import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.mttnow.coolestprojects.R
import com.mttnow.coolestprojects.screens.home.HomeActivity


open class FirebaseMessageService : FirebaseMessagingService() {

    private val NOTIFICATION_ID = 0x4578;

    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
//        super.onMessageReceived(remoteMessage)

        Log.i("Firebase", "Message Received - $remoteMessage ")

        val title = remoteMessage?.notification?.title
        val body = remoteMessage?.notification?.body
        if (title.isNullOrBlank() && body.isNullOrBlank()) {
            return
        }

        val intent = Intent(this, HomeActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)

        val resultPendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        val builder = NotificationCompat.Builder(this)
        builder.setContentTitle(title)
                .setContentText(body)
                .setSmallIcon(R.drawable.ic_notification)
        .setContentIntent(resultPendingIntent)

        val notificationService = NotificationManagerCompat.from(this)
        notificationService.notify(NOTIFICATION_ID, builder.build())
    }


}