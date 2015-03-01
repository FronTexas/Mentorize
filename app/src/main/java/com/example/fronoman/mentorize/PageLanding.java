package com.example.fronoman.mentorize;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.fron.customviews.TypefaceIntellitap;

/**
 * Created by Fahran on 1/18/2015.
 */
public class PageLanding extends Fragment {

    public static String FRAGMENT_TAG = "planding";

    private TextView tvMentorizeText;
    private TypefaceIntellitap tfi;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.page_landing, null);
        ((MainActivity) getActivity()).hideActionBar();

        tfi = ((MainActivity) getActivity()).tfi;


        tvMentorizeText = (TextView) v.findViewById(R.id.tvMentorizeText);
        tfi.setTypeface(tvMentorizeText, TypefaceIntellitap.LOBSTER);

        RelativeLayout rlSignUpButton = (RelativeLayout) v.findViewById(R.id.rlSignUpButton);
        rlSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).replaceFragments(PageLanding.this, new PageSignUp(), true, PageSignUp.FRAGMENT_TAG);
            }
        });

        LinearLayout llSignInField = (LinearLayout) v.findViewById(R.id.llSignInField);
        String[] hints = new String[]{"Email", "Password"};
        for (String hint : hints) {
            llSignInField.addView(new EditTextCustom(getActivity(), hint));
        }
        return v;
    }
}
