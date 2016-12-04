package vn.edu.usth.ircchat;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import org.jibble.pircbot.IrcException;
import org.jibble.pircbot.PircBot;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Arrays;

public class ChatActivity extends AppCompatActivity {

    TextView serverInfo;
    ScrollView serverInfoScrollView;
    EditText eText;
    Button btn;
    MyBot bot;
    ConnectIRCServer connectIRCServer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Toolbar toolbar = (Toolbar) findViewById(R.id.chat_toolbar);
        setSupportActionBar(toolbar);

        serverInfo = (TextView)findViewById(R.id.serverInfo);
        serverInfoScrollView = (ScrollView) findViewById(R.id.serverInfoScrollView);
        bot = new MyBot();
        connectIRCServer = new ConnectIRCServer();
        connectIRCServer.execute("irc.freenode.net");

        eText = (EditText) findViewById(R.id.chatEditText);
        btn = (Button) findViewById(R.id.sendButton);
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String str = eText.getText().toString();
                Toast msg = Toast.makeText(getBaseContext(),str,Toast.LENGTH_LONG);
                msg.show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_chat, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.addChannel:
                /* Alert Dialog Code Start*/
                AlertDialog.Builder alert = new AlertDialog.Builder(this);
                alert.setTitle("Alert Dialog With EditText"); //Set Alert dialog title here
                alert.setMessage("Enter Your Channel Name Here"); //Message here

                // Set an EditText view to get user input
                final EditText input = new EditText(this);
                alert.setView(input);

                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        //You will get as string input data in this variable.
                        // here we convert the input to a string and show in a toast.
                        String srt = input.getEditableText().toString();
                        Toast.makeText(getApplicationContext(),srt,Toast.LENGTH_LONG).show();
                    } // End of onClick(DialogInterface dialog, int whichButton)
                }); //End of alert.setPositiveButton
                alert.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // Canceled.
                        dialog.cancel();
                    }
                }); //End of alert.setNegativeButton
                AlertDialog alertDialog = alert.create();
                alertDialog.show();
                /* Alert Dialog Code End*/
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    private class ConnectIRCServer extends AsyncTask<String, String, String> {
        // The server to connect to and our details.
        String nick = "thao";
        String login = "thao";

        // The channel which the bot will join.
        String channel = "#usth";
        @Override
        protected String doInBackground(String... params) {
            String status = "Fail";
            // Enable debugging output.
//            bot.setVerbose(true);
//
//            // Connect to the IRC server.
//            try {
//                bot.connect(params[0]);
//                status = "Succes";
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            } catch (IrcException e) {
//                e.printStackTrace();
//            }

            // Connect directly to the IRC server.
            Socket socket = null;
            try {
                socket = new Socket(params[0], 6667);
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(socket.getOutputStream( )));
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(socket.getInputStream( )));
                // Log on to the server.
                writer.write("NICK " + nick + "\r\n");
                writer.write("USER " + login + " 8 * : Java IRC Hacks Bot\r\n");
                writer.flush( );

                // Read lines from the server until it tells us we have connected.
                String line = null;
                while ((line = reader.readLine( )) != null) {
                    if (line.indexOf("004") >= 0) {
                        // We are now logged in.
                        break;
                    }
                    else if (line.indexOf("433") >= 0) {
                        publishProgress("Nickname is already in use.");
                    }
                }

                // Join the channel.
                writer.write("JOIN " + channel + "\r\n");
                writer.write("PRIVMSG " + channel + " :hello from terminal\r\n");
                writer.flush( );

                // Keep reading lines from the server.
                while ((line = reader.readLine( )) != null) {
                    if (line.toLowerCase( ).startsWith("PING ")) {
                        // We must respond to PINGs to avoid being disconnected.
                        writer.write("PONG " + line.substring(5) + "\r\n");
                        writer.write("PRIVMSG " + channel + " :I got pinged!\r\n");
                        writer.flush( );
                    }
                    else {
                        // Print the raw line received by the bot.
                        publishProgress(line);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return status;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            serverInfo.append("\n" + Arrays.toString(values));
            serverInfoScrollView.fullScroll(View.FOCUS_DOWN);
        }

        @Override
        protected void onPostExecute(String s) {
            serverInfo.setText(s);
        }
    }

}
