package com.example.daggerworkbench.ui

/*

// Liskov Substitution Principle Violation:
// As we saw in this example, the method we wrote in our main class should work properly in its subclasses according to the Liskov principle,
// but when our subclass inherited from our superclass, our fly method did not work as expected.

open class Bird {
    open fun fly() {}
}

class Penguin : Bird() {
    override fun fly() {
        print("Penguins can't fly!")
    }
}
As we saw in this example, the method we wrote in our
main class should work properly in its subclasses according to
the Liskov principle, but when our subclass inherited from our superclass, our fly method did not work as expected.
 */




// Liskov Substitution Principle Correct Usage
// As you can see in this example, all the things we write in the superclass will be valid in the subclasses,
// because we have implemented the method that is not valid for subclasses by creating an interface and implementing it where we need it.

open class Birds {
    // common bird methods and properties
}

interface IFlyingBirds {
    fun fly(): Boolean
}

class Penguins : Bird() {
    // methods and properties specific to penguins
}

class Eagles : Bird(), IFlyingBird {
    override fun fly(): Boolean {
        return true
    }
}

interface Waste {
    fun process()
}
class OrganicWaste : Waste {
    override fun process() {
        println("Processing Organic Waste")
    }
}
class PlasticWaste : Waste {
    override fun process() {
        println("Processing Plastic Waste")
    }
}
class WasteManagementService {

    fun processWaste(waste: Waste) {
        waste.process()
    }
}
fun main() {

    val wasteManagementService = WasteManagementService()

    wasteManagementService.processWaste(OrganicWaste()) // Output: Processing Organic Waste

    wasteManagementService.processWaste(PlasticWaste()) // Output: Processing Plastic Waste
}