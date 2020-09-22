package ru.sneg.android.bug

fun main(args: Array<String>){



        val words = readLine()

        var maxWord =""
        val list : List<String> = (words?.split(" ") ?: "") as List<String>
        val map : HashMap<String, Int>;

        list.forEach {
            var count = 0
            if (list.contains(it))
                count++
            map[it] = count
        }

       print(list[1])


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