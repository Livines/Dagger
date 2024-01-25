package com.example.daggerworkbench.ui

import android.app.Fragment
import android.content.Context
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Checkbox
import androidx.compose.material.ContentAlpha
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.mapSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.daggerworkbench.R
import com.example.daggerworkbench.data.model.User
import com.example.daggerworkbench.utils.DataManager
import com.example.daggerworkbench.utils.Status
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.ref.WeakReference

@AndroidEntryPoint
class MainActivity : AppCompatActivity(),Communicator {
    private val viewModel: DogViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel.getRandomDogs()
        setUpObserver()
   /*     setContent {
            MaterialTheme {
                //TimerScreen()
                var name by remember { mutableStateOf("") }
           // HelloContent(name, onValueChange = {name=it})
                //Counter()
               // SideEffectSample()
                MyComponent(title = "Hello")
                //RememberSaveableExample()
              *//*  var state= remember{ mutableStateOf(true) }
                Row(horizontalArrangement = Arrangement.SpaceBetween) {
                    Column {
                        Text(text = "Select Your Gender")
                        SharedPrefsToggle(text = "MALE", value = state.value, onValueChanged = {
                            state.value=it
                        } )
                        SharedPrefsToggle(text = "FEMALE", value = state.value, onValueChanged = {
                            state.value=it
                        } )
                    }
                }*//*

            }
        }*/
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container_view,MessageSenderFragment()).commit()

        // Example for Unstructured Concurrency .... Need of Coroutine and coroutine
        /*
        This is an example of Unstructured Concurrency where it is not guaranteed
        that child coroutine would complete before returning.
        Thus, caller/parent coroutine would get wrong value returned by child coroutine.
         */
        CoroutineScope(Dispatchers.Main).launch {
            val result = downloadUserData_unstructured()
            Toast.makeText(applicationContext, "Result : $result", Toast.LENGTH_LONG).show()
        }

        /*

      When we need to communicate between multiple coroutines, we need to make sure Structured Concurrency
       */
        CoroutineScope(Dispatchers.Main).launch {
            val result = downloadUserData_structured()
            Toast.makeText(applicationContext, "Result : $result", Toast.LENGTH_LONG).show()
        }
        /*

        Launch vs Async
         */
        var resultOne = "Android"
        var resultTwo = "Kotlin"
        Log.i("Launch==========", "Before")
        lifecycleScope.launch {
            launch(Dispatchers.IO) { resultOne = function1() }
            launch(Dispatchers.IO) { resultTwo = function2() }
            Log.i("Launch==========", "After")
            val resultText = resultOne + resultTwo
            Log.i("Launch==========", resultText)


            Log.i("Async========", "Before")
            val resultOne1 = async(Dispatchers.IO) { function3() }
            val resultTwo1 = async(Dispatchers.IO) { function4() }
            Log.i("AsAsync========ync", "After")
            val resultText1 = resultOne1.await() + resultTwo1.await()
            Log.i("Async========", resultText1)
        }
        //Coroutine Dispatcher
        CoroutineScope(Dispatchers.Main).launch {
            dispatcherSample()
        }

        //Mutable stateflow
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.stateFlowSample()
                viewModel.stateFlowInt.collect(){
                    println("======================MUTABLESTATE :"+  viewModel.stateFlowInt.value)
                }
            }



           /* viewModel.stateFlow.collect{
                println("======================MUTABLE"+it.data)
            }*/

        }
        lifecycleScope.launch {
            viewModel.SharedFlowInt.collect() {
                println("======================MUTABLESHARED :"+  it)
            }
        }



        val batman = Hero("Bruce Wayne")
        val wonderWoman = Hero (name = "Diana Prince")

        val mailMan = Hero("Stan Lee")
        val deadPool = Hero("Wade Winston Wilson")

        val marvel = Universe(listOf(mailMan, deadPool))
        val dc = Universe(listOf(batman, wonderWoman))

        val allHeroes: List<Universe> = listOf(marvel, dc)
       val mapSample= allHeroes.map {
            it.heroes
        }
        println("======="+mapSample)
        val flatMap=allHeroes.flatMap { it.heroes }
        println("======="+flatMap)
        val dmy= Dummy()
        System.gc()
        println("=========DMMMM"+dmy)
        val obj=WeakReference(Dummy()) //weak referenece
        System.gc()
        println("=========DMMMM"+obj.get())

    }

    override fun onAttachFragment(fragment: androidx.fragment.app.Fragment) {
        if (fragment is MessageSenderFragment) {
            fragment.initializeListener(this)
        }
    }

    data class Hero (val name:String)
    data class Universe (val heroes: List<Hero>)
    private suspend fun dispatcherSample(): Unit = coroutineScope{

        launch(Dispatchers.Default) {
            println("Default : Thread ${Thread.currentThread().name}")
        }

        launch(Dispatchers.Unconfined) {
            println("Unconfined : Thread ${Thread.currentThread().name}")
        }

        launch(Dispatchers.IO) {
            println("IO : Thread ${Thread.currentThread().name}")
        }
    }
    private suspend fun function1(): String
    {
        delay(1000L)
        val message = "function1"
        Log.i("Launch==========", message)
        return message
    }

    private suspend fun function2(): String
    {
        delay(100L)
        val message = "function2"
        Log.i("Launch==========", message)
        return message
    }
    private suspend fun function3(): String
    {
        delay(1000L)
        val message = "function3"
        Log.i("Async========", message)
        return message
    }

    private suspend fun function4(): String
    {
        delay(100L)
        val message = "function4"
        Log.i("Async========", message)
        return message
    }
    private fun setUpObserver() {
        with(viewModel) {
            dogResponse.observe(this@MainActivity) {
                println("===========" + it.status)

                viewModel.error.observe(this@MainActivity) {
                }
            }
        }
        viewModel.users.observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> {

                }

                Status.LOADING -> {

                }

                Status.ERROR -> {
                   // Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                }
            }
        })

    }
    private suspend fun downloadUserData_unstructured(): Int {
        var result = 0
        // Here, we use CoroutineScope (Capital C version) which will start a new scope and
        // launch coroutine in new scope Dispatchers.IO, Not In Parent Scope which is Dispatchers.Main
        // Thus, this function would directly return without waiting for loop completion and will return 0
        CoroutineScope(Dispatchers.IO).launch {
            for (i in 0 until 100) {
                kotlinx.coroutines.delay(10)
                result++
            }
        }
        return result
    }
    private suspend fun downloadUserData_structured(): Int {
        var result = 0
    /*

    When we need to communicate between multiple coroutines, we need to make sure Structured Concurrency (Recommended)

This can be done by re-using parent/caller coroutine scope inside child/callee
coroutine. This can be achieved by coroutineScope {} (Smaller c) version inside child/callee coroutine.
     */
        coroutineScope{
            for (i in 0 until 100) {
                delay(10)
                result++
            }
        }
        return result
    }
    @Composable
    fun TimerScreen() {
        var clickCount by remember { mutableIntStateOf(0) }
        val scope = rememberCoroutineScope()
        Column {
            Button(onClick = {
                println("Timer started")
                scope.launch {
                    try {
                        startTimer(5000) {
                            println("Timer ended")
                        }
                    } catch (ex: Exception) {
                        println("Timer cancelled")
                    }
                }
            }) {
                Text("Start Timer")
            }
        }

        ClickCounter(clicks = clickCount, onClick = {
            clickCount += 1
           println( "I've been clicked $clickCount times")
        })

    }

    private fun startTimer(i: Int, function: () -> Unit) {
        object: CountDownTimer(i.toLong(), 1000){
            override fun onTick(p0: Long) {
                println(p0)
            }
            override fun onFinish() {
                //add your code here
                function.invoke()
            }
        }.start()
    }

    override fun sendMessage(text: String) {
        val bundle=Bundle()
        bundle.putString("MESSAGE",text)
        val frb=MessageReceiverFragment()
        frb.arguments=bundle
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container_view,frb).commit()

    }
}

@Composable
fun ClickCounter(clicks: Int, onClick: () -> Unit) {
    Button(onClick = { 
        onClick.invoke() 
    }) {
        Text("I've been clicked $clicks times")
    }

}
@Composable
fun SharedPrefsToggle(
    text: String,
    value: Boolean,
    onValueChanged: (Boolean) -> Unit
) {
    Row {
        var selected:Boolean=false
        Text(text)
        Checkbox(checked = value, onCheckedChange = onValueChanged)
    }
}
@Composable
fun HelloContent(name: String, onValueChange: (String) -> Unit) {
    Column(modifier = Modifier.padding(16.dp)) {
        if (name.isNotEmpty()) {
            Text(
                text = "Hello, $name!",
                modifier = Modifier.padding(bottom = 8.dp),
                style = MaterialTheme.typography.body1
            )
        }
        OutlinedTextField(
            value = name,
            onValueChange = onValueChange,
            label = { Text("Name") }
        )
    }
}
@Composable
fun Counter() {
    var count by remember { mutableStateOf(0) }
    Button(
        onClick = { count++ },
    ) {
        Text(text = "Increment Counter")
    }
    Text(text = "Count: $count")
}
@Composable
fun RememberSaveableExample() {
    var savedText by remember { mutableStateOf("Initial Text") }
    Row(modifier = Modifier.testTag("TEST")) {

    }
    TextField(
        value = savedText,
        onValueChange = { newText ->
            savedText=newText
        }
    )
    Text(text = "Text Entered: $savedText")
}
data class City(val name: String, val country: String)

val CitySaver = run {
    val nameKey = "Name"
    val countryKey = "Country"
    mapSaver(
        save = { mapOf(nameKey to it.name, countryKey to it.country) },
        restore = { City(it[nameKey] as String, it[countryKey] as String) }
    )
}

@Composable
fun CityScreen() {
    var selectedCity = rememberSaveable(stateSaver = CitySaver) {
        mutableStateOf(City("Madrid", "Spain"))
    }
}
// Side Effect
private var i = 0

@Composable
fun SideEffectSample() {
    var text by remember {
        mutableStateOf("")
    }
    Column {
        Button(onClick = { text += "@" }) {
            i++
            Text(text)
        }
    }
}
// Launched Effect


@Composable
fun LaunchedEffect() {
    var text by remember {
        mutableStateOf("")
    }

    LaunchedEffect(key1 = text) {
        i++
    }
    Column {
        Button(onClick = { text += "@" }) {
            Text(text)
        }
    }
}
@Composable
inline fun LogCompositions(tag: String) {
    val count = remember { mutableStateOf(0) }
   SideEffect {
        count.value++
    }
    Log.d("","$tag Compositions: ${count.value}")
}


@Composable
fun MyComponent(title: String) {
    var data by remember { mutableStateOf("") }

    val updatedData by rememberUpdatedState(title)

    LaunchedEffect(Unit) {
        delay(5000L)
        data = updatedData
    }

    Text(text = data)

    LazyColumn{
        items(listOf(1,2,3)){

        }
    }
}
@Composable
fun LocalCompositionSample(){
CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium ) {

    
}

}

//Design patterns Android..................
class Laptop(builder: Builder) {
    private val processor: String = builder.processor
    private val ram: String = builder.ram
    private val battery: String = builder.battery
    private val screenSize: String = builder.screenSize

    // Builder class
    class Builder(processor: String) {
        var processor: String = processor // this is necessary

        // optional features
        var ram: String = "2GB"
        var battery: String = "5000MAH"
        var screenSize: String = "15inch"

        fun setRam(ram: String): Builder {
            this.ram = ram
            return this
        }

        fun setBattery(battery: String): Builder {
            this.battery = battery
            return this
        }

        fun setScreenSize(screenSize: String): Builder {
            this.screenSize = screenSize
            return this
        }

        fun create(): Laptop {
            return Laptop(this)
        }
    }
}



// Open/Closed Principle Correct Usage
// As in correct usage, instead of changing the class itself,
// we wrote new classes using our existing class
// and implemented our functions under new classes.

interface Shape {
    fun area(): Double
}

class Area{
    class Rectangle(val width: Double, val height: Double) : Shape {
        override fun area() = width * height
    }

    class Circle(val radius: Double) : Shape {
        override fun area() = Math.PI * radius * radius
    }

    fun calculateArea(shape: Shape) = shape.area()
}
class MailManager() {
    fun sendEmail(user: User, email: String) {}
}

class NotificationManager() {
    fun sendNotification(notification: String) {}
}

class UserManager {
    fun addUser(user: User) {}
    fun deleteUser(user: User) {}
}

// Liskov Substitution Principle Correct Usage
// As you can see in this example, all the things we write in the superclass will be valid in the subclasses,
// because we have implemented the method that is not valid for subclasses by creating an interface and implementing it where we need it.

open class Bird {
    // common bird methods and properties
}

interface IFlyingBird {
    fun fly(): Boolean
}

class Penguin : Bird() {
    // methods and properties specific to penguins
}

class Eagle : Bird(), IFlyingBird {
    override fun fly(): Boolean {
        return true
    }
}
// Interface Segregation Principle Correct Usage
// As we saw in the correct usage example, dividing the system into smaller interfaces and using them where we needed them made it much easier to change the system in the future.

interface CanSwim {
    fun swim()
}

interface CanFly {
    fun fly()
}

class Duck : CanSwim, CanFly {
    override fun swim() {
        println("Duck swimming")
    }

    override fun fly() {
        println("Duck flying")
    }
}

class PenguinOne : CanSwim {
    override fun swim() {
        println("Penguin swimming")
    }
}

// WEAK REFRERENCE

object MySingleton {
    var context: WeakReference<Context>? = null
    fun init(ctx: Context) {
        this.context = WeakReference(ctx)
    }
}
class Dummy{

}
fun main(args: Array<String>)
{
    //declaring a map of integer to string
    val map = mapOf(1 to "One", 2 to "Two" , 3 to "Three", 4 to "Four")

    println("Map Entries : $map")

    println("Map Keys: "+map.keys )

    println("Map Values: "+map.values )

    //DESIGN PATTERNS

    //Creation patterns Example
    // Builder patterns
    Laptop.Builder("SAMPLE")
        .setRam("8GB")            // this is optional
        .setBattery("6000MAH")    // this is optional
        .create()

// Design pattern Singleton Example
    DataManager.getUserData()
    // Structural  Patterns
    //Ex: Adapter, Facade

    //Behavior

    // Ex:   Observer pattern live data



// SOLID PRINCIPLES

    /*
    Creational
    Structural
    Behavior
     */

    //1. SRP
    val umngr= UserManager()
        .addUser(User(12))


    val area= Area()
    val res=area.calculateArea(Area.Circle(20.00))
    println(" OPEN CLOSED PRINCIPLE:$res")
    val penguin=Penguin()


// Koltin Scope function

    val stringVal = "Hello, World!"
    val resultValue = stringVal.let {
        it.length // Perform operations on the object
    }
    println(resultValue) // Output: 13


    val str = "Hello, World!"
    val result = str.run {
        length // Perform operations on the object
    }
    println(result) // Output: 13

    val numberList = mutableListOf<Double>()
    numberList.also { println("Populating the list") }
        .apply {
            add(2.71)
            add(3.14)
            add(1.0)
        }
        .also { println("Sorting the list") }
        .sort()
    println(numberList)
}



