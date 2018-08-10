package maxdistructo.discord.bots.droidbot.background

import org.json.JSONArray

object PrivUtils {
    fun arrayToList(array : Array<String>) : List<String> {
        var output : List<String> = listOf()
        for(value in array){
            output += value
        }
        return output
    }
    fun listToArray(list : List<String>) : Array<String>{
        val output : ArrayList<String> = arrayListOf()
        for(value in list){
            output.add(value)
        }
        return output.toTypedArray()
    }
    fun jsonArrayToList(jsonArray: JSONArray) : List<Any>{
        var i = 0
        var outList = listOf<Any>()
        while(i < jsonArray.count()){
            outList += jsonArray[i]
            i++
        }
        return outList
    }

}