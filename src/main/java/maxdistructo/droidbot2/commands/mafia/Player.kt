package maxdistructo.droidbot2.commands.mafia

class Player constructor(private var details: Array<Any>) {
    fun getRole(): String{
        return details[2] as String
    }
}