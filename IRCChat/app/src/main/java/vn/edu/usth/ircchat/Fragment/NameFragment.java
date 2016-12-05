package vn.edu.usth.ircchat.Fragment;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;

import vn.edu.usth.ircchat.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class NameFragment extends Fragment {
    int color;
    public NameFragment() {
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public NameFragment(int color) {
        this.color = color;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_name, container, false);
    }

}
