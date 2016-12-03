package vn.edu.usth.ircchat;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import org.jibble.pircbot.IrcException;

import java.io.IOException;

public class ChatActivity extends AppCompatActivity {

    TextView serverInfo;
    MyBot bot;
    ConnectIRCServer connectIRCServer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Toolbar toolbar = (Toolbar) findViewById(R.id.chat_toolbar);
        setSupportActionBar(toolbar);

        serverInfo = (TextView)findViewById(R.id.serverInfo);
        bot = new MyBot();
        connectIRCServer = new ConnectIRCServer();
        connectIRCServer.execute("irc.freenode.net");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_chat, menu);
        return true;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    private class ConnectIRCServer extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String status = "Fail";
            String line = "Fail";
            // Enable debugging output.
            bot.setVerbose(true);

            // Connect to the IRC server.
            try {
                bot.connect(params[0]);
                status = "Succes";
                //line = bot.getServerResponse();

            } catch (IOException e) {
                e.printStackTrace();
            } catch (IrcException e) {
                e.printStackTrace();
            }
            return status;
        }

        @Override
        protected void onPostExecute(String s) {
            serverInfo.setText(s);
        }
    }

}
