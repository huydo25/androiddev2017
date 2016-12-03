package vn.edu.usth.ircchat;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import vn.edu.usth.ircchat.Fragment.EmptyFragment;
import vn.edu.usth.ircchat.Fragment.NameFragment;
import vn.edu.usth.ircchat.Fragment.ServerFragment;

import static vn.edu.usth.ircchat.R.id.container;

public class MainActivity extends AppCompatActivity{
    EditText et, et1 ;
    Spinner sp;
    private TextView tv;
    private ListView lv;
    private Button bn, bn1;
    ArrayList<String> nick_server =new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PagerAdapter adapter = new MainActivityFragmentPagerAdapter(getSupportFragmentManager());
        ViewPager pager = (ViewPager) findViewById(R.id.pager);
        pager.setOffscreenPageLimit(2);
        pager.setAdapter(adapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab);
        tabLayout.setupWithViewPager(pager);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
    }

    public void connectServer(View view) {
        // Do something in response to button click
        et = (EditText) findViewById(R.id.nick_name);
        et1 = (EditText) findViewById(R.id.alter_name);
        Button b = (Button)view;
        ColorDrawable bColor = (ColorDrawable) b.getBackground();
        int bColorId = bColor.getColor();
        Log.i("MainColorButton",String.valueOf(bColorId));
        if(bColorId == -12532481){
            b.setBackgroundColor(getResources().getColor(R.color.button_disconnect));
        }else{
            b.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        }
        String buttonText = b.getText().toString();
        String toastDisplay = et.getText().toString() + " " + et1.getText().toString() + " " + buttonText;
        Toast.makeText(getBaseContext(), toastDisplay, Toast.LENGTH_SHORT).show();
        et.getText().clear();
        et1.getText().clear();
    }


    public void onClickServer(View view){
        Intent intent = new Intent(this,ChatActivity.class);
        startActivity(intent);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                // do something when search is pressed here
                Toast toast = Toast.makeText(getApplicationContext(), "Add", Toast.LENGTH_SHORT);
                toast.show();
                Intent intent = new Intent(MainActivity.this, AddServerActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_setting:
                // do something when search is pressed here
                Toast toast1 = Toast.makeText(getApplicationContext(), "Setting", Toast.LENGTH_SHORT);
                toast1.show();
                return true;
            case R.id.action_help:
                // do something when search is pressed here
                Toast toast2 = Toast.makeText(getApplicationContext(), "Help", Toast.LENGTH_SHORT);
                toast2.show();
                return true;
            case R.id.action_exit:
                // do something when search is pressed here
                Toast toast3 = Toast.makeText(getApplicationContext(), "Exit", Toast.LENGTH_SHORT);
                toast3.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public class MainActivityFragmentPagerAdapter extends FragmentPagerAdapter {
        private final int PAGE_COUNT = 2;
        private String titles[] = new String[]{"New Network", "Server" +
                ""};

        public MainActivityFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return PAGE_COUNT;// number of pages for a ViewPager
        }

        @Override
        public Fragment getItem(int page) {
            // returns an instance of Fragment corresponding to the specified page
            switch(page) {
                case 0:
                    return new NameFragment();
                case 1:
                    return new ServerFragment();
    //            case  2:
    //                return WeatherAndForecastFragment.newInstance();
            }
            return new EmptyFragment();// failsafe
        }

        @Override
        public CharSequence getPageTitle(int page) {
            // returns a tab title corresponding to the specified page
            return titles[page];
        }
    }
}
