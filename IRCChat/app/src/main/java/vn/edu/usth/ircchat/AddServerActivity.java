package vn.edu.usth.ircchat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AddServerActivity extends AppCompatActivity {
    Button bn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_server);
        bn = (Button) findViewById(R.id.go_back);
        bn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddServerActivity.this, MainActivity.class);
                startActivity(intent);

            }
        });
    }
}
