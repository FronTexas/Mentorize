package com.example.fronoman.mentorize;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.example.fron.customviews.MyTextView;
import com.nhaarman.listviewanimations.appearance.simple.SwingBottomInAnimationAdapter;

import java.util.List;

/**
 * Created by Fahran on 3/1/2015.
 */
public class PageMentorShown extends Fragment {
    private String[] names = new String[]{"Weston Selleck", "Mark Daniel", "Nicholas Moreles"};
    private View[] mentors;
    private RelativeLayout startChatButton;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.page_mentor_shown, null);
        ListView lvMentorList = (ListView) v.findViewById(R.id.lvMentorList);
        mentors = buildMentors(inflater);

        SomeAdapter some_adapter = new SomeAdapter();
        SwingBottomInAnimationAdapter swingAdapter = new SwingBottomInAnimationAdapter(some_adapter);
        swingAdapter.setAbsListView(lvMentorList);
        lvMentorList.setAdapter(swingAdapter);

        startChatButton = (RelativeLayout) v.findViewById(R.id.startChatButton);
        startChatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).replaceFragments(PageMentorShown.this, new PageChat(), true, "");
            }
        });


        return v;
    }

    public View[] buildMentors(LayoutInflater inflater) {
        View[] vs = new View[names.length];
        for (int i = 0; i < names.length; i++) {
            View v = inflater.inflate(R.layout.card_provider_listing, null);
            MyTextView tvName = (MyTextView) v.findViewById(R.id.tvName);
            tvName.setText(names[i]);
            vs[i] = v;
        }

        return vs;

    }

    class SomeAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return names.length;
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
            return mentors[position];
        }
    }
}
