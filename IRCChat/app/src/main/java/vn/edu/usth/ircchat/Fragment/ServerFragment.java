package vn.edu.usth.ircchat.Fragment;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import vn.edu.usth.ircchat.ListMyServerActivity;
import vn.edu.usth.ircchat.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ServerFragment extends Fragment {


    public ServerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_server, container, false);
    }


}
