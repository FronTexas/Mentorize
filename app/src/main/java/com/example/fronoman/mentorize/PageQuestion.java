package com.example.fronoman.mentorize;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

/**
 * Created by Fahran on 3/1/2015.
 */
public class PageQuestion extends Fragment implements CardQuestion.OnQuestionAnswered {

    private LinearLayout greenbar;
    private LinearLayout graybar;
    private FrameLayout questionFrameLayout;
    private LinearLayout progressBar;
    private int questionCounter;
    private String[] question = new String[]{"Are you A?", "Are you B?", "Are you C?", "Are you D?"};
    private boolean[] isAgreensss = new boolean[]{false, false, true, true};

    LinearLayout.LayoutParams params1;
    LinearLayout.LayoutParams params2;

    @Override
    public void onQuestionAnswered(String answer) {
        if (questionCounter < question.length) {
            Bundle b = new Bundle();
            b.putString("QUESTION", question[questionCounter]);
            b.putBoolean("ISAGREENESS", isAgreensss[questionCounter]);
            CardQuestion cc = CardQuestion.newInstance();
            cc.setArguments(b);
            cc.onQuestionAnsweredListener = this;
            updateProgressBar();
            replaceFragments(cc, true, "");

        }


    }

    public void updateProgressBar() {
        params1.weight = (float) (1 - ((double) questionCounter / (double) question.length));
        greenbar.requestLayout();
        params2.weight = (float) ((double) questionCounter / (double) question.length);
        graybar.requestLayout();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.page_question, null);
        questionCounter = 0;

        progressBar = (LinearLayout) v.findViewById(R.id.progressBar);

        initializeViews(v);
        CardQuestion cc = CardQuestion.newInstance();
        Bundle b = new Bundle();
        b.putString("QUESTION", question[questionCounter]);
        b.putBoolean("ISAGREENESS", isAgreensss[questionCounter]);
        cc.onQuestionAnsweredListener = this;
        cc.setArguments(b);

        params1 = (LinearLayout.LayoutParams) greenbar.getLayoutParams();
        params2 = (LinearLayout.LayoutParams) graybar.getLayoutParams();

        updateProgressBar();

        replaceFragments(cc, true, "");



        return v;
    }

    public void replaceFragments(Fragment fragment, boolean addToBackStack, String tag) {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        if (addToBackStack) {
            transaction.addToBackStack(null);
        }
        transaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);

        transaction.replace(R.id.questionFrameLayout, fragment, tag).commit();
        questionCounter++;

    }

    public void initializeViews(View v) {
        greenbar = (LinearLayout) v.findViewById(R.id.greenbar);
        graybar = (LinearLayout) v.findViewById(R.id.graybar);
        questionFrameLayout = (FrameLayout) v.findViewById(R.id.questionFrameLayout);
    }


}
