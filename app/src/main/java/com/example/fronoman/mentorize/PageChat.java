package com.example.fronoman.mentorize;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.nhaarman.listviewanimations.appearance.simple.SwingBottomInAnimationAdapter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Fahran on 3/1/2015.
 */
public class PageChat extends Fragment {

    private EditText etUserMessage;
    private RelativeLayout rlSend;
    private ListView lvMessages;

    private ArrayList<Message> messages;
    private ChatAdapter chat_adapter;

    private LayoutInflater inflater;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.page_chat, null);
        this.inflater = inflater;
        messages = new ArrayList<>();


        etUserMessage = (EditText) v.findViewById(R.id.etUserMessage);
        rlSend = (RelativeLayout) v.findViewById(R.id.rlSend);
        rlSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etUserMessage.getText().toString().length() > 0) {
                    Message m = new Message();
                    m.message = etUserMessage.getText().toString();
                    Log.d("etUserMessage", "etUserMessage = " + etUserMessage.getText().toString());

                    DateFormat dateFormat = new SimpleDateFormat("HH:mm");
                    Date date = new Date();
                    m.time_stamp = dateFormat.format(date);

                    m.isFromThis = true;
                    messages.add(m);


                    chat_adapter.notifyDataSetChanged();
                }
            }
        });
        lvMessages = (ListView) v.findViewById(R.id.lvMessages);

        chat_adapter = new ChatAdapter();

        SwingBottomInAnimationAdapter swing_adapter = new SwingBottomInAnimationAdapter(chat_adapter);
        swing_adapter.setAbsListView(lvMessages);
        lvMessages.setAdapter(swing_adapter);


        return v;
    }

    class ChatAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return messages.size();
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

            Message message = messages.get(position);

            return new ChatBubble(getActivity(), message);
        }
    }
}
