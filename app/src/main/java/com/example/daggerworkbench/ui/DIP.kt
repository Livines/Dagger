package com.example.daggerworkbench.ui

class NotificationService1 {

    // this can be injected through constructor as well and it would be constructor injection
    var notification: Notification1? = null

    fun sendNotification(message: String) {
        notification?.sendNotificationSample(message)
    }
}
class SmsNotification1 : Notification1 {
    override fun sendNotificationSample(message: String) {
        println("Sending sms notification with message \"$message\"")
    }
}
interface Notification1 {
    fun sendNotificationSample(message: String)
}
fun main() {
    val message = "Happy Coding"
    val notificationService = NotificationService1()
    var notification= SmsNotification1()

    notificationService.notification = notification
    notificationService.sendNotification(message)
    // Output: Sending sms notification with message "Happy Coding"
}