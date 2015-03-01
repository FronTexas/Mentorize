package com.example.fronoman.mentorize;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.nhaarman.listviewanimations.appearance.simple.SwingBottomInAnimationAdapter;

/**
 * Created by Fahran on 3/1/2015.
 */
public class PageQuestion extends Fragment implements CardQuestion.OnQuestionAnswered {

    private LinearLayout greenbar;
    private LinearLayout graybar;
    private ListView lvQuestions;
    private LinearLayout progressBar;
    private int questionCounter;
    private String[] question = new String[]{"Are you currently employed?", "Do you regularly attend religious services?", "Do you have children?", "I have a close relationship with my family", "I am as healthy as anybody I know"};
    private int[] isAgreensss = new int[]{0, 0, 0, 1, 1};
    CardQuestion[] cqs;

    LinearLayout.LayoutParams params1;
    LinearLayout.LayoutParams params2;

    @Override
    public void onQuestionAnswered(String answer) {
        if (questionCounter < question.length - 1) {
            questionCounter++;
            updateProgressBar();
        } else if (questionCounter == question.length - 1) {
            ((MainActivity) getActivity()).replaceFragments(this, new PageMentorShown(), true, "");
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

        cqs = new CardQuestion[question.length];
        for (int i = 0; i < cqs.length; i++) {
            cqs[i] = new CardQuestion(getActivity(), isAgreensss[i], question[i], this);
        }

        params1 = (LinearLayout.LayoutParams) greenbar.getLayoutParams();
        params2 = (LinearLayout.LayoutParams) graybar.getLayoutParams();

        lvQuestions.setAdapter(new QuestionsAdapter(cqs));

        QuestionsAdapter qadapter = new QuestionsAdapter(cqs);
        SwingBottomInAnimationAdapter swingadapter = new SwingBottomInAnimationAdapter(qadapter);
        swingadapter.setAbsListView(lvQuestions);
        lvQuestions.setAdapter(swingadapter);


        return v;
    }


    public void initializeViews(View v) {
        greenbar = (LinearLayout) v.findViewById(R.id.greenbar);
        graybar = (LinearLayout) v.findViewById(R.id.graybar);
        lvQuestions = (ListView) v.findViewById(R.id.lvQuestions);
    }

    class QuestionsAdapter extends BaseAdapter {
        CardQuestion[] cc;

        public QuestionsAdapter(CardQuestion[] cc) {
            this.cc = cc;
        }

        @Override
        public int getCount() {
            Log.d("cqs length", "cqs length = " + cqs.length);
            return cc.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Log.d("cqs length", "cqs elm = " + cc[position]);
            return cc[position];
        }
    }


}
