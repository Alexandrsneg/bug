package ru.sneg.android.bug

fun main(args: Array<String>){



        val words = "PDDPEM MXWHL XJID XJID XJID DTQEHGC DTQEHGC MXWHL XJID DTQEHGC DTQEHGC MXWHL PDDPEM QUCO DTQEHGC MXWHL DTQEHGC MXWHL PDDPEM XJID MXWHL DTQEHGC DTQEHGC XJID XJID XJID MXWHL MXWHL MXWHL MXWHL DTQEHGC PDDPEM DTQEHGC DTQEHGC PDDPEM"

        var maxWord =""
        val list : List<String> = words.split(" ")
        val frequenciesByWord = list.groupingBy{it}.eachCount()

    val filteredValuesMap = frequenciesByWord.filterValues { it == frequenciesByWord.values.max() }

    print(frequenciesByWord)
    var key: String = filteredValuesMap.keys.toString().removePrefix("[").removeSuffix("]")
    print(key)

//    fun calculateWordStat(input:String): String{
//        val list : List<String> = input.split(" ")
//        val frequenciesByWord = list.groupingBy{it}.eachCount()
//
//        val filteredValuesMap = frequenciesByWord.filterValues { it == frequenciesByWord.values.max() }
//
//
//        return filteredValuesMap.keys.toString()
//    }


//    fun getCubeList(n:Int): List<Int>{
//        val list : List<Int> = List<Int>(n-1, {i -> i*i*i})
//        return list
//    }
//
//
//    open class Bug(val rank: Int, val name:String) {
//        open fun getSugarLimit(): Int{
//            return rank
//        }
//
//        fun getId(): String{
//            return "${rank}.${name}"
//        }
//    }
//
//    class  BugCivilian(rank: Int, name:String): Bug(rank, name){
//        override fun getSugarLimit(): Int {
//            return super.getSugarLimit()/2
//        }
//
//    }
//
//
//
//
//    val numbers = readLine()
//
//    if (numbers != null) {
//        var sum =0;
//        for(char in numbers){
//            if (char.toString().toInt()%2 ==0)
//              sum += char.toString().toInt()
//        }
//            println(sum)
//    }
}