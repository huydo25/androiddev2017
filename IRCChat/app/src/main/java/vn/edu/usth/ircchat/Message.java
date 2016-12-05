package vn.edu.usth.ircchat;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
/**
 * Created by AVISHOP on 12/5/2016.
 */

public class Message {
    public String text1 = "";
    public String text2 = "";//JOIN, PRIVMSG, ...
    public String text3 = "";
    public String text4 = "";// message of user, list of users, general message

    public Message(String line){
        Matcher matcher = Pattern.compile("^(:(\\S+) )?(\\S+)( (?!:)(.+?))?( :(.+))?$").matcher(line);
        if (matcher.matches()){
            text1 = matcher.group(2) != null? matcher.group(2): "";
            text2 = matcher.group(3) != null? matcher.group(3): "";
            text3 = matcher.group(5) != null? matcher.group(5): "";
            text4 = matcher.group(7) != null? matcher.group(7): "";

        }
    }

    @Override
    public String toString() {
        return String.format("text1=[%s] text2=[%s] text3=[%s] text4=[%s]", text1, text2, text3, text4);
    }

    public String printMess(){
        switch (text2){
            case "PING":
                return text2 + " " + text4;
            case "JOIN":
                return text1 +" has "+ text2 + " " + text3;
            case "PRIVMSG":
                return "<"+text1.substring(0,text1.indexOf('!'))+"> "+text4;
            case "353":
                return "Current users in channel: "+ text4;
            case "QUIT":
                return text1 + " has " + text2;
            default:
                return text4;
        }
    }
}


