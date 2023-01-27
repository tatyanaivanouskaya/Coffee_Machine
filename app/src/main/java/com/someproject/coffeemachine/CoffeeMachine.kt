package com.someproject.coffeemachine

fun main() {
    val coffeeMachine = CoffeeMachine()
    var exit = false
    while (!exit) {
        if (!coffeeMachine.actionBuy) {
            println("Write action (buy, fill, take, remaining, exit):")
            exit = coffeeMachine.start(readln())
        } else {
            println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu:")
            exit = coffeeMachine.start(readln())
        }
    }

}


class CoffeeMachine(
    private var waterInMachine: Int = 400,
    private var milkInMachine: Int = 540,
    private var coffeeBeansInMachine: Int = 120,
    private var money: Int = 550,
    private var disposableCups: Int = 9,

) {
    val actionBuy: Boolean get() = _actionBuy
    private var _actionBuy = false
    override fun toString(): String {
        return (
                """
        The coffee machine has:
        $waterInMachine ml of water
        $milkInMachine ml of milk
        $coffeeBeansInMachine g of coffee beans
        $disposableCups disposable cups
        ${'$'}${money} of money
        
    """.trimIndent()
                )
    }

    fun start(action: String): Boolean {
        return when (action) {
            "buy" -> {
                _actionBuy = true
                false
            }
            "1" -> {
                val espresso = Espresso()
                getCoffee(espresso)
                _actionBuy = false
                false
            }
            "2" -> {
                val latte = Latte()
                getCoffee(latte)
                _actionBuy = false
                false
            }
            "3" -> {
                val cappuccino = Cappuccino()
                getCoffee(cappuccino)
                _actionBuy = false
                false

            }
            "back" -> {
                _actionBuy = false
                false
            }

            "fill" -> {
                fill()
                false
            }
            "take" -> {
                takeMoney()
                false
            }
            "remaining" -> {
                println(toString())
                false
            }
            else -> {
                true
            }
        }

    }

    private fun getCoffee(coffeeType: CoffeeType) {
        val enable = checkResources(coffeeType)
        if (enable) {
            modifyCoffeeMachine(coffeeType)
        } else {
            checkNotEnough(coffeeType)
        }
    }

    private fun checkResources(coffeeType: CoffeeType): Boolean {
        return if (waterInMachine >= coffeeType.water &&
            milkInMachine >= coffeeType.milk &&
            coffeeBeansInMachine >= coffeeType.coffeeBeans
        ) {
            println("I have enough resources, making you a coffee!")
            true
        } else {
            false
        }
    }

    private fun modifyCoffeeMachine(
        coffeeType: CoffeeType
    ) {
        waterInMachine -= coffeeType.water
        milkInMachine -= coffeeType.milk
        coffeeBeansInMachine -= coffeeType.coffeeBeans
        money += coffeeType.price
        disposableCups -= 1
    }

    private fun checkNotEnough(coffeeType: CoffeeType) {
        if (waterInMachine < coffeeType.water) {
            println("Sorry, not enough water!")
        } else if (milkInMachine < coffeeType.milk) {
            println("Sorry, not enough milk!")
        } else if (coffeeBeansInMachine < coffeeType.coffeeBeans) {
            println("Sorry, not enough coffee beans!")
        }
    }

    private fun takeMoney() {
        println("I gave you \$${money}")
        money = 0
    }

    private fun fill() {
        println("Write how many ml of water you want to add:")
        val water = readln().toInt()
        waterInMachine += water

        println("Write how many ml of milk you want to add:")
        val milk = readln().toInt()
        milkInMachine += milk

        println("Write how many grams of coffee beans you want to add:")
        val coffeeBeans = readln().toInt()
        coffeeBeansInMachine += coffeeBeans

        println("Write how many disposable cups you want to add:")
        val cups = readln().toInt()
        disposableCups += cups
    }
}

abstract class CoffeeType(
    open val water: Int,
    open val milk: Int,
    open val coffeeBeans: Int,
    open val price: Int
)

data class Espresso(
    override val water: Int = 250,
    override val milk: Int = 0,
    override val coffeeBeans: Int = 16,
    override val price: Int = 4
) : CoffeeType(water, milk, coffeeBeans, price)

data class Latte(
    override val water: Int = 350,
    override val milk: Int = 75,
    override val coffeeBeans: Int = 20,
    override val price: Int = 7
) : CoffeeType(water, milk, coffeeBeans, price)

data class Cappuccino(
    override val water: Int = 200,
    override val milk: Int = 100,
    override val coffeeBeans: Int = 12,
    override val price: Int = 6
) : CoffeeType(water, milk, coffeeBeans, price)
