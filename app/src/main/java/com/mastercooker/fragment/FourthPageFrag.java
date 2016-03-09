package com.mastercooker.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mastercooker.R;

public class FourthPageFrag extends Fragment {
    public static FourthPageFrag newInstance() {
        return new FourthPageFrag();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.four_page_frag, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //放置关于软件
        getActivity().findViewById(R.id.four_page_frag_up).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "感谢您的支持,鑫鑫会更努力的!", Toast.LENGTH_SHORT).show();
            }
        });
        getActivity().findViewById(R.id.four_page_frag_down).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "鑫鑫仍需努力,感谢您的评价!", Toast.LENGTH_SHORT).show();
            }
        });
        getActivity().findViewById(R.id.four_page_frag_conn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "开发者:\n鑫鑫\n联系电话:\n18875016382\n如果联系不上\n请多试几次!\n(づ￣3￣)づ╭❤～", Toast.LENGTH_LONG).show();
            }
        });
    }
}
