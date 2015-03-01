package com.example.fronoman.mentorize;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.fron.customviews.MyTextView;
import com.example.fron.customviews.TypefaceIntellitap;

/**
 * Created by Fahran on 3/1/2015.
 */
public class CardQuestion extends Fragment {

    private boolean agreenessQuestion;

    float scaleDP;

    CardQuestion.OnQuestionAnswered onQuestionAnsweredListener;

    //    yesNoModea
    RelativeLayout yesNoMode;
    MyTextView tvQuestion;
    RelativeLayout rlNo, rlYes;

    // agreeDisagreeMode
    LinearLayout agreeDissagreeMode;
    MyTextView tvQuestionAgreeness;
    LinearLayout llAgreenessLevel;

    String question;

    private TypefaceIntellitap tfi;

    public static CardQuestion newInstance() {
        return new CardQuestion();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.question_card, null);
        scaleDP = ((MainActivity) getActivity()).scaleDP;
        boolean isAgreeness = getArguments().getBoolean("ISAGREENESS");
        this.question = getArguments().getString("QUESTION");


        tfi = ((MainActivity) getActivity()).tfi;

        initializeViews(v);

        if (isAgreeness) {
            agreeDissagreeMode.setVisibility(View.VISIBLE);
            yesNoMode.setVisibility(View.GONE);
        } else {
            yesNoMode.setVisibility(View.VISIBLE);
            agreeDissagreeMode.setVisibility(View.GONE);
        }

        return v;
    }


    public void initializeViews(View v) {
        //    yesNoModea
        yesNoMode = (RelativeLayout) v.findViewById(R.id.yesNoMode);
        tvQuestion = (MyTextView) v.findViewById(R.id.tvQuestion);
        rlNo = (RelativeLayout) v.findViewById(R.id.rlNo);
        rlYes = (RelativeLayout) v.findViewById(R.id.rlYes);

        rlNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onQuestionAnsweredListener.onQuestionAnswered("");
            }
        });

        rlYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onQuestionAnsweredListener.onQuestionAnswered("");
            }
        });

        // agreeDisagreeMode
        agreeDissagreeMode = (LinearLayout) v.findViewById(R.id.agreeDissagreeMode);
        tvQuestionAgreeness = (MyTextView) v.findViewById(R.id.tvQuestionAgreeness);
        llAgreenessLevel = (LinearLayout) v.findViewById(R.id.llAgreenessLevel);

        String[] agreeness = new String[]{"Strongly Agree", "Agree", "Indifferent", "Disagree", "Strongly Disagree"};
        for (String s : agreeness) {
            TextView tv = createTextView(s);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(0, 0, 0, (int) (10 * scaleDP + 0.5f));
            tv.setLayoutParams(params);
            llAgreenessLevel.addView(tv);

        }

    }

    private TextView createTextView(String text) {
        TextView tv = new TextView(getActivity());
        tv.setText(text);
        tfi.setTypeface(tv, TypefaceIntellitap.ROBOTO);
        tv.setTextColor(getResources().getColor(R.color.BlueIntellitap));
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView tv = (TextView) v;
                if (tv.getCurrentTextColor() == getResources().getColor(R.color.BlueIntellitap)) {
                    tfi.setTypeface(tv, TypefaceIntellitap.ROBOTO_BOLD);
                    changeTextViewColor(tv, getResources().getColor(R.color.BlueIntellitap), getResources().getColor(R.color.GreenIntellitap));
                } else {
                    tfi.setTypeface(tv, TypefaceIntellitap.ROBOTO);
                    changeTextViewColor(tv, getResources().getColor(R.color.GreenIntellitap), getResources().getColor(R.color.BlueIntellitap));
                }
                onQuestionAnsweredListener.onQuestionAnswered("");
            }
        });
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        return tv;
    }

    private void changeTextViewColor(final TextView tv, int colorFrom, int colorTo) {
        ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
        colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                tv.setTextColor((int) animation.getAnimatedValue());
            }
        });
        colorAnimation.start();
    }

    public interface OnQuestionAnswered {
        public void onQuestionAnswered(String answer);
    }
}
