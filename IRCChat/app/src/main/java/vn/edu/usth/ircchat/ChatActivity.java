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
    Socket socket = null;
    BufferedWriter writer;
    BufferedReader reader;
    BufferedWriter userInput;
    String server = "irc.freenode.net";
    String channel = "#usth";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Toolbar toolbar = (Toolbar) findViewById(R.id.chat_toolbar);
        setSupportActionBar(toolbar);

        serverInfo = (TextView)findViewById(R.id.serverInfo);
        serverInfoScrollView = (ScrollView) findViewById(R.id.serverInfoScrollView);
        //bot = new MyBot();
        connectIRCServer = new ConnectIRCServer();
        connectIRCServer.execute(server);

        eText = (EditText) findViewById(R.id.chatEditText);
        btn = (Button) findViewById(R.id.sendButton);
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final String str = eText.getText().toString();
                new Thread(){
                    public void run(){
                        if(socket == null) {
                            ChatActivity.this.runOnUiThread(new Runnable() {
                                public void run() {
                                    Toast.makeText(ChatActivity.this, "Socket Null", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }else{
                            try {
                                userInput = new BufferedWriter(
                                        new OutputStreamWriter(socket.getOutputStream( )));
                                userInput.write("PRIVMSG " + channel + " :"+str+"\r\n");
                                userInput.flush( );
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            serverInfo.post(new Runnable() {
                                public void run() {
                                    serverInfo.append("PRIVMSG " + channel + " :"+str+"\r\n");
                                    eText.getText().clear();
                                }
                            });
                        }
                    }
                }.start();
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
                        serverInfo.setText(srt);
                        Toast.makeText(getApplicationContext(),srt,Toast.LENGTH_LONG).show();
                        // Join the channel.
//                        try {
//                            writer.write("JOIN " + srt + "\r\n");
//                            writer.write("PRIVMSG " + srt + " :hello from terminal\r\n");
//                            writer.flush( );
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
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
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class ConnectIRCServer extends AsyncTask<String, String, String> {
        // The server to connect to and our details.
        String nick = "thao111";
        String login = "thao111";

        // The channel which the bot will join.
        @Override
        protected String doInBackground(String... params) {
            String status = "Fail";
             //Enable debugging output.
//            bot.setVerbose(true);
//
//            // Connect to the IRC server.
//            try {
//                bot.connect(params[0]);
//                status = "Succes";
//
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            } catch (IrcException e) {
//                e.printStackTrace();
//            }

            // Connect directly to the IRC server.
            try {
                socket = new Socket(params[0], 6667);
                writer = new BufferedWriter(
                        new OutputStreamWriter(socket.getOutputStream( )));
                reader = new BufferedReader(
                        new InputStreamReader(socket.getInputStream( )));
                //userInput = new BufferedReader(new InputStreamReader())

                // Log on to the server.
                writer.write("NICK " + nick + "\r\n");
                writer.write("USER " + login + " 8 * : Java IRC Hacks Bot\r\n");
                writer.flush( );

                writer.write("JOIN " + channel + "\r\n");
                writer.write("PRIVMSG " + channel + " :hello from terminal\r\n");
                writer.flush( );

                // Read lines from the server until it tells us we have connected.
                String line = null;
                while ((line = reader.readLine( )) != null) {
                   if (line.indexOf("433") >= 0) {
                        publishProgress("Nickname is already in use.");
                    }
                    else if (line.toLowerCase( ).startsWith("PING ")) {
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

    public class MyBot extends PircBot {

        public MyBot() {
            this.setName("MyBot");
        }
    }
}
