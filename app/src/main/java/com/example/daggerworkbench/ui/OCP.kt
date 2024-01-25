package com.example.daggerworkbench.ui

/*
enum class Notification {
    PUSH_NOTIFICATION, EMAIL, SMS
}
class NotificationService {

    fun sendNotification(notification: Notification) {
        when (notification) {
            Notification.PUSH_NOTIFICATION -> {
                // send push notification
            }

            Notification.EMAIL -> {
                // send email notification
            }

            Notification.SMS -> {
                // send sms notification
            }
        }
    }
}
This means that every time we change the notification type, we will have to update the NotificationService to support the change.
 */
interface Notification {
    fun sendNotification()
}
class PushNotification : Notification {
    override fun sendNotification() {
        // send push notification
        println("PushNotification")
    }
}
class EmailNotification : Notification {
    override fun sendNotification() {
        // send email notification
        println("EMAIL NOTIFICATION")
    }
}
class NotificationService {

    fun sendNotification(notification: Notification) {
        notification.sendNotification()
    }
}
//added SMSNotification without modifying NotificationService thus following the Open/Closed Principle.
class SMSNotification : Notification {
    override fun sendNotification() {
        // send sms notification
        println("SMSNotification")
    }
}

fun main(){
    NotificationService().sendNotification(EmailNotification())// OCP
    NotificationService().sendNotification(PushNotification())// OCP
    NotificationService().sendNotification(SMSNotification())// OCP
}
