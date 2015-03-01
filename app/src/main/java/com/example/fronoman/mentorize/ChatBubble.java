package com.example.fronoman.mentorize;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.fron.customviews.MyTextView;

/**
 * Created by Fahran on 3/1/2015.
 */
public class ChatBubble extends RelativeLayout {

    String message, timeStamp;

    RelativeLayout otherUserChatBubbleArea, thisUserChatBubbleArea;
    MyTextView tvMessageOther, timeStampOther;
    ImageView ivOtherPeoplePic;

    MyTextView tvMessageThis, timeStampThis;
    ImageView ivThisPeoplePic;

    public ChatBubble(Context context, Message message) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.chat_bubble, null);
        this.message = message.message;
        this.timeStamp = message.time_stamp;

        initializeViews(v);

        if (message.isFromThis) {
            thisUserChatBubbleArea.setVisibility(VISIBLE);
            otherUserChatBubbleArea.setVisibility(GONE);

            tvMessageThis.setText("" + message);
            timeStampThis.setText(timeStamp);
        } else {
            thisUserChatBubbleArea.setVisibility(GONE);
            otherUserChatBubbleArea.setVisibility(VISIBLE);

            tvMessageOther.setText("" + message);
            timeStampOther.setText(timeStamp);
        }

        addView(v);
    }

    public void initializeViews(View v) {
        otherUserChatBubbleArea = (RelativeLayout) v.findViewById(R.id.otherUserChatBoubleArea);
        thisUserChatBubbleArea = (RelativeLayout) v.findViewById(R.id.thisUserChatBoubleArea);

        tvMessageOther = (MyTextView) v.findViewById(R.id.tvMessageOther);
        timeStampOther = (MyTextView) v.findViewById(R.id.timestampOther);

        ivOtherPeoplePic = (ImageView) v.findViewById(R.id.ivOtherPeoplePic);

        tvMessageThis = (MyTextView) v.findViewById(R.id.tvMessageThis);
        timeStampThis = (MyTextView) v.findViewById(R.id.timestampThis);
        ivThisPeoplePic = (ImageView) v.findViewById(R.id.ivThisPeoplePic);

    }
}
