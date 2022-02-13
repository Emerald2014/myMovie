package ru.kudesnik.mymovie.services

import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MessagingService : FirebaseMessagingService() {
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        //здесь можно отправить токен на сервер

        //если нужно получить токен еще раз
        FirebaseMessaging.getInstance().token.addOnSuccessListener { token ->
            //получили токен
        }
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        val custom = remoteMessage.data["ticketId"]


    }
}