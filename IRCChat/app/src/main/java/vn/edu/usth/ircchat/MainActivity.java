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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import java.util.List;

import vn.edu.usth.ircchat.Fragment.EmptyFragment;
import vn.edu.usth.ircchat.Fragment.NameFragment;
import vn.edu.usth.ircchat.Fragment.ServerFragment;

import static vn.edu.usth.ircchat.R.id.container;

public class MainActivity extends AppCompatActivity{
    EditText et, et1, et2;
    Spinner sp;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private RecyclerViewAdapter mAdapter; // was RecyclerView.Adapter mAdapter;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    List<String> list_servers = new ArrayList<>();
    String[] arr_servers = new String[20];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //PagerAdapter adapter = new MainActivityFragmentPagerAdapter(getSupportFragmentManager());
        //ViewPager pager = (ViewPager) findViewById(R.id.pager);
        viewPager = (ViewPager) findViewById(R.id.pager);
        setupViewPager(viewPager);
        //pager.setOffscreenPageLimit(2);
        //pager.setAdapter(adapter);

        tabLayout = (TabLayout) findViewById(R.id.tab);
        tabLayout.setupWithViewPager(viewPager);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        // 1.
        mAdapter = new RecyclerViewAdapter(arr_servers);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new NameFragment(getResources().getColor(R.color.accent_material_light)), "New");
        adapter.addFrag(new ServerFragment(getResources().getColor(R.color.ripple_material_light)), "Active Server(s)");
        viewPager.setAdapter(adapter);
    }

    public void click(View v){
        et = (EditText)findViewById(R.id.nick_name);
        et1 = (EditText)findViewById(R.id.alter_name);
        sp = (Spinner)findViewById(R.id.list_server);
        et2 = (EditText)findViewById(R.id.channel);
        if(et.getText().length() == 0){
            Toast.makeText(getBaseContext(), "Please enter a nickname", Toast.LENGTH_SHORT).show();
        }else if(et2.getText().length() == 0){
            Toast.makeText(getBaseContext(), "Please enter a channel", Toast.LENGTH_SHORT).show();
        }
        else{
            checkNameServerChannel(et.getText().toString().replaceAll("\\s", ""),sp.getSelectedItem().toString().replaceAll("\\s", ""),et2.getText().toString().replaceAll("\\s", ""));
        }
    }

    public void populateMyServers(String s){
        if(list_servers.add(s)){
            //NOTIFY RECYCLERVIEW that we have add an item.\
            mAdapter.notifyDataSetChanged();
            list_servers.toArray(arr_servers);
            Log.i("Current servers", Arrays.toString(arr_servers));
            Toast.makeText(getBaseContext(), "Added " + s, Toast.LENGTH_SHORT).show();
            // 2.
            mLinearLayoutManager = new LinearLayoutManager(this);
            mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

            // 3.
            mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview1);
            mRecyclerView.setHasFixedSize(true);
            mRecyclerView.setClickable(true);
            mRecyclerView.setLayoutManager(mLinearLayoutManager);
            mRecyclerView.setAdapter(mAdapter);
        }else{
            Toast.makeText(getBaseContext(), "Not added", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean compareWithPreviousServer(String s1, String s2, String s3){
        boolean duplicate = false;
        for (int i=0; i<arr_servers.length; i++) {
            if (arr_servers[i] != null) {
                int firstDash = arr_servers[i].indexOf('-');
                int lastDash = arr_servers[i].lastIndexOf('-');
                String nickName = arr_servers[i].substring(0, firstDash);
                nickName = nickName.replaceAll("\\s", "");
                String server = arr_servers[i].substring(firstDash + 1, lastDash);
                server = server.replaceAll("\\s", "");
                String channel = arr_servers[i].substring(lastDash + 1);
                channel = channel.replaceAll("\\s", "");
                if (s2.equals(server) && s3.equalsIgnoreCase(channel)) {
                    Log.i("MainActivity","same channel and server");
                    duplicate = true;
                    break;
                }
            }else{
                break;
            }
        }
        return duplicate;
    }

    public void checkNameServerChannel(String s1, String s2, String s3){
        String s = s1 +" - "+ s2 +" - #"+ s3;
        if (arr_servers[0] == null){
            Log.i("MainActivity","First added!");
            populateMyServers(s);
        }else{
            Log.i("MainActivity","Check next added!");
            if(compareWithPreviousServer(s1,s2,"#"+s3)){
                Toast.makeText(getBaseContext(), "This channel and server already exist!", Toast.LENGTH_SHORT).show();
            }else{
                Log.i("MainActivity","Next added!");
                populateMyServers(s);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        list_servers.clear();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("MainActivity","onStop");
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
                Toast toast = Toast.makeText(getApplicationContext(), "Add", Toast.LENGTH_SHORT);
                toast.show();
                Intent intent = new Intent(MainActivity.this, AddServerActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_setting:
                Toast toast1 = Toast.makeText(getApplicationContext(), "Setting", Toast.LENGTH_SHORT);
                toast1.show();
                return true;
            case R.id.action_help:
                Toast toast2 = Toast.makeText(getApplicationContext(), "Help", Toast.LENGTH_SHORT);
                toast2.show();
                return true;
            case R.id.action_exit:
                Toast toast3 = Toast.makeText(getApplicationContext(), "Exit", Toast.LENGTH_SHORT);
                toast3.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

//    public class MainActivityFragmentPagerAdapter extends FragmentPagerAdapter {
//        private final int PAGE_COUNT = 2;
//        private String titles[] = new String[]{"New Network", "Server" +
//                ""};
//
//        public MainActivityFragmentPagerAdapter(FragmentManager fm) {
//            super(fm);
//        }
//
//        @Override
//        public int getCount() {
//            return PAGE_COUNT;// number of pages for a ViewPager
//        }
//
//        @Override
//        public Fragment getItem(int page) {
//            // returns an instance of Fragment corresponding to the specified page
//            switch(page) {
//                case 0:
//                    return new NameFragment();
//                case 1:
//                    return new ServerFragment();
//            }
//            return new EmptyFragment();// failsafe
//        }
//
//        @Override
//        public CharSequence getPageTitle(int page) {
//            // returns a tab title corresponding to the specified page
//            return titles[page];
//        }
//    }
    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();
        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }
        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }
        @Override
        public int getCount() {
            return mFragmentList.size();
        }
        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }
        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
