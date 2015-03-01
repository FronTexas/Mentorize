package com.example.fronoman.mentorize;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.fron.customviews.MyTextView;
import com.example.fron.customviews.TypefaceIntellitap;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Fahran on 1/18/2015.
 */
public class PageSignUp extends Fragment {


    private LinearLayout llMain;
    private RelativeLayout rlSignUpButton;
    private TypefaceIntellitap tfi;
    private float scaleDP;

    public static final String FRAGMENT_TAG = "pagesignup";

    private boolean sign_up_button_shown;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        sign_up_button_shown = false;
        ((MainActivity) getActivity()).hideActionBar();
        scaleDP = ((MainActivity) getActivity()).scaleDP;

        View v = inflater.inflate(R.layout.page_sign_up, null);
        tfi = ((MainActivity) getActivity()).tfi;

        llMain = (LinearLayout) v.findViewById(R.id.llMain);

        final LinearLayout llSignUpField = (LinearLayout) v.findViewById(R.id.llSignUpField);
        String[] hints = new String[]{"Alias", "Email", "Password", "Year born", "Region", "Gender",};
        for (String hint : hints) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            int margin = (int) (13 * scaleDP + 0.5f);
            params.setMargins(margin, 0, 0, 0);
            EditTextCustom etcustom = new EditTextCustom(getActivity(), hint);
            etcustom.setLayoutParams(params);
            if (hint.equals("Password")) {
                etcustom.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                llSignUpField.addView(etcustom);
            } else if (hint.equals("Region")) {
                AutoCompleteTextView atv = new AutoCompleteTextView(getActivity());
                String[] cities = new String[]{"Austin , TX", "Dallas/Fort Worth, TX", "Houston , TX"};
                ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_dropdown_item_1line, cities);
                atv.setAdapter(adapter);
                tfi.setTypeface(atv, TypefaceIntellitap.ROBOTO_BOLD);
                atv.setBackgroundResource(getResources().getColor(android.R.color.transparent));
                atv.setHint("Region");
                atv.setSingleLine();
                margin = (int) (10 * scaleDP + 0.5f);
                params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.setMargins(0, 0, 0, margin);
                atv.setLayoutParams(params);
                llSignUpField.addView(atv);
            } else {
                llSignUpField.addView(etcustom);
            }
        }

        rlSignUpButton = (RelativeLayout) v.findViewById(R.id.rlSignUpButton);
        rlSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Users user = new Users();
                for (int i = 0; i < llSignUpField.getChildCount(); i++) {
                    String text = "";
                    if (llSignUpField.getChildAt(i) instanceof EditTextCustom) {
                        EditTextCustom et = (EditTextCustom) llSignUpField.getChildAt(i);
                        text = et.getText();
                    } else if (llSignUpField.getChildAt(i) instanceof AutoCompleteTextView) {
                        AutoCompleteTextView atv = (AutoCompleteTextView) llSignUpField.getChildAt(i);
                        text = atv.getText().toString();
                    }
                    if (i == 0) {
                        user.alias = text;
                    } else if (i == 1) {
                        user.email = text;
                    } else if (i == 2) {
                        user.password = text;
                    } else if (i == 3) {
                        user.birth_year = text;
                    } else if (i == 4) {
                        int n = 0;
                        if (text.equals("Male")) {
                            n = 1;
                        } else {
                            n = 2;
                        }
                        user.region_id = n;
                    } else if (i == 5) {
                        int n = 0;
                        if (text.contains("Male")) {
                            n = 1;
                        } else {
                            n = 2;
                        }
                        user.gender_id = n;
                    }
                }

                ((MainActivity) getActivity()).mentorizeService.addUser(user, new Callback<Metadata>() {

                    @Override
                    public void success(Metadata users, Response response) {
                        Log.d("SUCCESS POSTING", "RETRO success usersId = " + users);
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.d("Failure POSTING", "RETRO error = " + error.toString());
                    }
                });


                ((MainActivity) getActivity()).replaceFragments(PageSignUp.this, new PageQuestion2(), true, "");
            }
        });

        return v;
    }

}
