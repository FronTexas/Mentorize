package com.example.fronoman.mentorize;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.fron.customviews.MyTextView;
import com.example.fron.customviews.TypefaceIntellitap;

/**
 * Created by Fahran on 3/1/2015.
 */
public class CardQuestion extends LinearLayout {

    private int questionType;

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

    // fill in the blank
    LinearLayout fillInTheBlank;
    MyTextView tvQuestionFillBlank;
    EditText etAnswer;

    String question;

    private TypefaceIntellitap tfi;

    String[] agreeness;

    public CardQuestion(Context context, int questionType, String question, OnQuestionAnswered onQuestionAnsweredListener) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.question_card, null);
        scaleDP = ((MainActivity) getContext()).scaleDP;
        this.question = question;
        this.onQuestionAnsweredListener = onQuestionAnsweredListener;


        tfi = ((MainActivity) getContext()).tfi;

        initializeViews(v);

        if (questionType == 1) {
            tvQuestionAgreeness.setText(question);
            agreeDissagreeMode.setVisibility(View.VISIBLE);
            yesNoMode.setVisibility(View.GONE);
            fillInTheBlank.setVisibility(GONE);
        } else if (questionType == 0) {

            tvQuestion.setText(question);
            yesNoMode.setVisibility(View.VISIBLE);
            agreeDissagreeMode.setVisibility(View.GONE);
            fillInTheBlank.setVisibility(GONE);
        } else {
            tvQuestionFillBlank.setText(question);
            fillInTheBlank.setVisibility(VISIBLE);
            agreeDissagreeMode.setVisibility(View.GONE);
            yesNoMode.setVisibility(GONE);
        }

        addView(v);
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
                onQuestionAnsweredListener.onQuestionAnswered("No");
                changeBoxColor(rlNo, getResources().getColor(R.color.RedIntellitap), getResources().getColor(R.color.RedIntellitappDarker));
            }
        });

        rlYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onQuestionAnsweredListener.onQuestionAnswered("Yes");
                changeBoxColor(rlYes, getResources().getColor(R.color.GreenIntellitap), getResources().getColor(R.color.GreenIntellitappDarker));

            }
        });

        // agreeDisagreeMode
        agreeDissagreeMode = (LinearLayout) v.findViewById(R.id.agreeDissagreeMode);
        tvQuestionAgreeness = (MyTextView) v.findViewById(R.id.tvQuestionAgreeness);
        llAgreenessLevel = (LinearLayout) v.findViewById(R.id.llAgreenessLevel);

        if (agreeness == null)
            agreeness = new String[]{"Strongly Agree", "Agree", "Indifferent", "Disagree", "Strongly Disagree"};
        for (String s : agreeness) {
            TextView tv = createTextView(s);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(0, 0, 0, (int) (10 * scaleDP + 0.5f));
            tv.setLayoutParams(params);
            llAgreenessLevel.addView(tv);

        }

        // fill in the blank
        fillInTheBlank = (LinearLayout) v.findViewById(R.id.fillInTheBlank);
        tvQuestionFillBlank = (MyTextView) v.findViewById(R.id.tvQuestionFillBlank);
        etAnswer = (EditText) v.findViewById(R.id.etAnswer);
        tfi.setTypeface(etAnswer, TypefaceIntellitap.ROBOTO_BOLD);

    }

    public void changeAgreeness() {
        llAgreenessLevel.removeAllViews();
        for (String s : agreeness) {
            TextView tv = createTextView(s);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(0, 0, 0, (int) (10 * scaleDP + 0.5f));
            tv.setLayoutParams(params);
            llAgreenessLevel.addView(tv);
        }
    }

    private TextView createTextView(String text) {
        TextView tv = new TextView(getContext());
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

    private void changeBoxColor(final View v, int colorFrom, int colorTo) {
        ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
        colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                v.setBackgroundColor((int) animation.getAnimatedValue());
            }
        });
        colorAnimation.start();
    }

    public interface OnQuestionAnswered {
        public void onQuestionAnswered(String answer);
    }
}
