package maxdistructo.discord.bots.droidbot.commands

import com.mashape.unirest.http.Unirest
import com.mashape.unirest.http.exceptions.UnirestException
import org.json.JSONObject

object Horoscope {

    fun onHoroscopeCommand(args: Array<String>): String { // !horoscope <AstralSign>
        val signIn = args[1].toString().toLowerCase() //If command parameters above is met, this will be the astral sign.
        var signOut: String? = null
        when (signIn) {
            "aquarius" -> signOut = "Aquarius"
            "aries" -> signOut = "Aries"
            "taurus" -> signOut = "Taurus"
            "gemini" -> signOut = "Gemini"
            "cancer" -> signOut = "Cancer"
            "leo" -> signOut = "Leo"
            "virgo" -> signOut = "Virgo"
            "libra" -> signOut = "Libra"
            "scorpio" -> signOut = "Scorpio"
            "sagittarius" -> signOut = "Sagittarius"
            "capricorn" -> signOut = "Capricorn"
            "pisces" -> signOut = "Pisces"
            "sagitarius" -> signOut = "Sagittarius" //Cause I typo
        }
        var horoscope: JSONObject? = null
        try {
            horoscope = Unirest.get("http://horoscope-api.herokuapp.com/horoscope/today/" + signOut!!).asJson().body.`object`
        } catch (e: UnirestException) {
            e.printStackTrace()
        }

        return if (horoscope != null) {
            "The horoscope for " + signOut + " is " + horoscope.getString("horoscope").replace("]".toRegex(), "").replace('[', ' ').replace('\'', ' ') //Removes unnessesary characters in API json return.
        } else {
            "Your horoscope has been clouded out of my vision at this time. (API Error)"
        }

    }
}
