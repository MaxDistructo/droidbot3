package maxdistructo.droidbot2.commands;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import maxdistructo.droidbot2.core.api.http.HTTPGet;
import maxdistructo.droidbot2.core.message.Message;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;

public class Horoscope {

    public static String onHoroscopeCommand(Object[] args) { // !horoscope <AstralSign>
        String signIn = args[1].toString().toLowerCase(); //If command parameters above is met, this will be the astral sign.
        String signOut = null;
        switch (signIn) {
            case "aquarius":
                signOut = "Aquarius";
                break;
            case "aries":
                signOut = "Aries";
                break;
            case "taurus":
                signOut = "Taurus";
                break;
            case "gemini":
                signOut = "Gemini";
                break;
            case "cancer":
                signOut = "Cancer";
                break;
            case "leo":
                signOut = "Leo";
                break;
            case "virgo":
                signOut = "Virgo";
                break;
            case "libra":
                signOut = "Libra";
                break;
            case "scorpio":
                signOut = "Scorpio";
                break;
            case "sagittarius":
                signOut = "Sagittarius";
                break;
            case "capricorn":
                signOut = "Capricorn";
                break;
            case "pisces":
                signOut = "Pisces";
                break;
            case "sagitarius":
                signOut = "Sagittarius"; //Cause I typo
                break;

        }
        JSONObject horoscope = null;
        try {
                horoscope = Unirest.get("http://horoscope-api.herokuapp.com/horoscope/today/" + signOut).asJson().getBody().getObject();
            } catch (UnirestException e) {
                e.printStackTrace();
            }
            if (horoscope != null) {
                return "The horoscope for " + signOut + " is " + horoscope.getString("horoscope").replaceAll("]", "").replace('[', ' ').replace('\'', ' '); //Removes unnessesary characters in API json return.
            } else {
                return "Your horoscope has been clouded out of my vision at this time. (API Error)";
            }

    }
}
