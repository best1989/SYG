package com.lapropuesta.paraloquetal.syg;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Gabriel on 07/12/2015.
 */
// In this case, the fragment displays simple text based on the page
public class PointsFragment extends Fragment {
    public static final String ARG_PAGE = "ARG_PAGE";

    private int mPage;

    public static PointsFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        PointsFragment fragment = new PointsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view;
        switch (getArguments().getInt(ARG_PAGE)) {

            case 1:
                view = inflater.inflate(R.layout.points_fragment, container, false);
                break;
            case 2:
                view = inflater.inflate(R.layout.rewards_fragment, container, false);
                break;
            default:
                view = inflater.inflate(R.layout.points_fragment, container, false);
                break;
        }
      //  TextView textView = (TextView) view;
      //  textView.setText("Fragment #" + mPage);
        return view;
    }
}
