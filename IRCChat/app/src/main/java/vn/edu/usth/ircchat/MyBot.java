package vn.edu.usth.ircchat;
import org.jibble.pircbot.PircBot;

/**
 * Created by AVISHOP on 12/4/2016.
 */

public class MyBot extends PircBot{
    String serverResponse;

    public void setServerResponse(String serverResponse) {
        this.serverResponse = serverResponse;
    }

    public String getServerResponse() {

        return serverResponse;
    }

    public MyBot() {
        this.setName("MyBot");
    }

    @Override
    protected void onServerResponse(int i, String s) {
        while (s != null){
            setServerResponse(s + "\n");
        }
    }
}
