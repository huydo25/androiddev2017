package vn.edu.usth.ircchat.Fragment;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringDef;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import vn.edu.usth.ircchat.ChatActivity;
import vn.edu.usth.ircchat.R;
import vn.edu.usth.ircchat.RecyclerViewAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class ServerFragment extends Fragment {
    int color;

    public ServerFragment() {
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public ServerFragment(int color) {
        this.color = color;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_server, container, false);
    }
}
