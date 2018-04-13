package com.example.android.miwok;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Velis on 01.04.2018.
 */

public class WordAdapter extends ArrayAdapter<Word> {

    /**
     * Resource ID for the background color for this list of words
     */
    private int mColorResourceID;

    //constructor
    public WordAdapter(Context context, ArrayList<Word> words, int colorResourceID) {
        //as this is not a single text . The adapter is not using the second argument
        //so, it can be any value. Here, I used 0.
        super(context, 0, words);
        mColorResourceID = colorResourceID;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // return super.getView(position, convertView, parent);

        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;

        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }
        Word local_word = getItem(position);
        TextView miwokTextView = (TextView) listItemView.findViewById(R.id.miwok_text_view);
        miwokTextView.setText(local_word.getMiwokTranslation());
        TextView defaultTextView = (TextView) listItemView.findViewById(R.id.default_text_view);
        defaultTextView.setText(local_word.getDefaultTranslation());
        ImageView imgView = (ImageView) listItemView.findViewById(R.id.image);
        if (local_word.hasImage()) {
            imgView.setImageResource(local_word.getImageRosurceID());
        } else {
            imgView.setVisibility(View.GONE);
        }

        //Set the team color for the list item
        View textContainer = listItemView.findViewById(R.id.text_container);
        //Find the color that the resource ID maps to
        int color = ContextCompat.getColor(getContext(), mColorResourceID);
        // return the hole list item layout so that it can be shone in the list view
        textContainer.setBackgroundColor(color);
        return listItemView;

    }
}
