package maxdistructo.discord.bots.droidbot.background

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

}