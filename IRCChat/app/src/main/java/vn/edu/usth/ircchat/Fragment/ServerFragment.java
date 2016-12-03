package vn.edu.usth.ircchat.Fragment;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringDef;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;

import vn.edu.usth.ircchat.ListMyServerActivity;
import vn.edu.usth.ircchat.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ServerFragment extends Fragment {


    public ServerFragment() {
        // Required empty public constructor
    }
//    public static ServerFragment newInstance(ArrayList<String> my_servers) {
//        ServerFragment serverFragment = new ServerFragment();
//        Bundle args = new Bundle();
//        args.putStringArrayList("myServers",my_servers);
//        serverFragment.setArguments(args);
//        return serverFragment;
//    }

//    public void createButton(ArrayList<String> myServers) {
//        LinearLayout linearLayout = (LinearLayout) getView().findViewById(R.id.list_my_server);
//        for (int i=0; i<myServers.size(); i++){
//            Button button = new Button(this.getContext());
//            button.setText(myServers.get(i));
//            linearLayout.addView(button);
//        }
//    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_server, container, false);
    }

//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        ArrayList<String> myServers = getArguments().getStringArrayList("myServers");
//        createButton(myServers);
//    }
}
