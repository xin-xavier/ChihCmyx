package com.chih.mecm.cmyx

fun main() {

/*    val strings = ArrayList<String>()
    strings.add("a")
    strings.add("b")
    strings.add("c")
    strings.add("d")
    strings.add("e")
    var size = strings.size
    for (i in 0 until size) {
        val s = strings[i]
        if (s == "a") {
            strings.add("aa")
            size++
        }
        println(":: $i")
    }
    println(strings)*/

    //val strings = ArrayList<String>()
    /*val strings = mutableListOf<String>()
    strings.add("a")
    strings.add("b")
    strings.add("c")
    strings.add("d")
    strings.add("e")*/

    //var size = strings.size
    /*var indices = strings.indices
    for (i in indices) {
        val s = strings[i]
        if (s == "a") {
            strings.add("aa")
            val last = indices.last
            indices = 0..(last + 1)
        }
        if (s == "b") {
            strings.add("bb")
            val last = indices.last
            indices = 0..(last + 1)
        }
        println(":: $i")
    }
    println(strings)*/

    /*val numbers = mutableListOf("one", "four", "four")
    val mutableListIterator = numbers.listIterator()
    mutableListIterator.next()
    mutableListIterator.add("two")
    mutableListIterator.next()
    mutableListIterator.set("three")
    println(numbers)*/

    val numbers = listOf("one", "two", "three", "four")
    val listIterator = numbers.listIterator()
    while (listIterator.hasNext()) listIterator.next()
    println("Iterating backwards:")
    while (listIterator.hasPrevious()) {
        print("Index: ${listIterator.previousIndex()}")
        println(", value: ${listIterator.previous()}")
    }

    val letters = mutableListOf("a", "b", "c", "d")
    var mutableListIterator = letters.listIterator()
    while (mutableListIterator.hasNext()) {
        val next = mutableListIterator.next()
        println(":: $next")
        if (!letters.contains("aa")) {
            letters.add("aa")
            mutableListIterator=letters.listIterator()
            mutableListIterator.next()
        }
        if (!letters.contains("bb")) {
            letters.add("bb")
            mutableListIterator=letters.listIterator()
            mutableListIterator.next()
        }
    }
    println(letters)
}