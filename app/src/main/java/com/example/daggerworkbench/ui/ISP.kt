package com.example.daggerworkbench.ui

/*

// Interface Segregation Principle Violation
// When we look at our example, we see that the interface we created contains many methods.
// If we do everything inside a common interface, we may have made unnecessary use in the places that implement our interface.
// Instead, we can divide our system into smaller interface parts.

interface Animal {
    fun swim()
    fun fly()
}

class Duck : Animal {
    override fun swim() {
        println("Duck swimming")
    }

    override fun fly() {
        println("Duck flying")
    }
}

class Penguin : Animal {
    override fun swim() {
        println("Penguin swimming")
    }

    override fun fly() {
        throw UnsupportedOperationException("Penguin cannot fly")
    }
}
 */


// Interface Segregation Principle Correct Usage
// As we saw in the correct usage example, dividing the system into smaller interfaces and using them where we needed them made it much easier to change the system in the future.

interface CanSwim1 {
    fun swim()
}

interface CanFly1 {
    fun fly()
}

class Duck1 : CanSwim, CanFly {
    override fun swim() {
        println("Duck swimming")
    }

    override fun fly() {
        println("Duck flying")
    }
}

class Penguin1 : CanSwim {
    override fun swim() {
        println("Penguin swimming")
    }
}

