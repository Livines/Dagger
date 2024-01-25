package com.example.daggerworkbench.ui

/*

class Order {

    fun sendOrderUpdateNotification() {
        // sends notification about order updates to the user.
    }

    fun generateInvoice() {
        // generates invoice
    }

    fun save() {
        // insert/update data in the db
    }
}

The violation is that the Order handles more than one
responsibility which means it has more than one reason to change.
 */
data class Order(
    val id: Long,
    val name: String,
    // ... other properties.
)
class OrderNotificationSender {

    fun sendNotification(order: Order) {
        // send order notifications
        println("sendNotification===="+order.name)
    }
}
class OrderInvoiceGenerator {

    fun generateInvoice(order: Order) {
        // generate invoice
        println("generateInvoice===="+order.name)
    }
}
class OrderRepository {

    fun save(order: Order) {
        // insert/update data in the db.
        println("save===="+order.name)
    }
}
class OrderFacade(
    private val orderNotificationSender: OrderNotificationSender,
    private val orderInvoiceGenerator: OrderInvoiceGenerator,
    private val orderRepository: OrderRepository
) {

    fun sendNotification(order: Order) {
        // sends notification about order updates to the user.
        orderNotificationSender.sendNotification(order)
    }

    fun generateInvoice(order: Order) {
        // generates invoice
        orderInvoiceGenerator.generateInvoice(order)
    }

    fun save(order: Order) {
        // insert/update data in the db
        orderRepository.save(order)
    }
}
fun main(){
    val obj=OrderFacade(OrderNotificationSender(),OrderInvoiceGenerator(),OrderRepository())
    obj.sendNotification(Order(10,"SAMPLE"))
    obj.generateInvoice(Order(10,"SAMPLE"))
    obj.save(Order(10,"SAMPLE"))
}