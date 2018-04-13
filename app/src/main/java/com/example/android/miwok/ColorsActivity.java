/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.miwok;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class ColorsActivity extends AppCompatActivity {

    MediaPlayer mediaPlayer;
    //Handling audio focus when playing an audio file
    private AudioManager mAudioManager;

    AudioManager.OnAudioFocusChangeListener  mOnAudioFocusChangeListener= new AudioManager.OnAudioFocusChangeListener() {

        @Override
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT||focusChange==AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK){
                // Pause playback
                mediaPlayer.pause();
                //if we gat the focus back, start the audio file from the beginning -position zero
                mediaPlayer.seekTo(0);
            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                // Resume playback
                mediaPlayer.start();
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                // Stop playback and release resaurces
                releaseMediaPlayer();
            }
        }
    };


    /**
     * This listener gets triggered when {@link MediaPlayer} has completed playing the audio file
     */
    private MediaPlayer.OnCompletionListener mCompletionlistener=new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            releaseMediaPlayer();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);
        // Create and setup the {@mlink AudioManager} to request audio focus
        mAudioManager = (AudioManager)getSystemService(AUDIO_SERVICE);

        // Create a list of words
        final ArrayList<Word> words = new ArrayList<Word>();
        words.add(new Word("red", "wetetti",R.raw.color_red, R.drawable.color_red));
        words.add(new Word("green", "chokokki",R.raw.color_green, R.drawable.color_green));
        words.add(new Word("brown", "takaakki",R.raw.color_gray, R.drawable.color_brown));
        words.add(new Word("grey", "topoppi",R.raw.color_gray, R.drawable.color_gray));
        words.add(new Word("black", "kululli", R.raw.color_black, R.drawable.color_black));
        words.add(new Word("white", "kelelli",R.raw.color_white, R.drawable.color_white));
        words.add(new Word("dusty yellow", "topiisә",R.raw.color_dusty_yellow, R.drawable.color_dusty_yellow));
        words.add(new Word("mustard yellow", "chiwiiṭә",R.raw.color_mustard_yellow, R.drawable.color_mustard_yellow));

        // Create an {@link WordAdapter}, whose data source is a list of {@link Word}s. The
        // adapter knows how to create list items for each item in the list.

        WordAdapter adapter = new WordAdapter(this, words, R.color.category_colors);

        // Find the {@link ListView} object in the view hierarchy of the {@link Activity}.
        // There should be a {@link ListView} with the view ID called list, which is declared in the
        // activity_numbers.xml layout file.
        // ArrayAdapter<Word> itemsAdapter = new ArrayAdapter<Word>(this, R.layout.list_item, words);
        ListView listView = (ListView) findViewById(R.id.list);

        // Make the {@link ListView} use the {@link WordAdapter} we created above, so that the
        // {@link ListView} will display list items for each {@link Word} in the list.
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Word current_word=words.get(i);
                int r=current_word.getAudioId();
                releaseMediaPlayer();
                //Request audio focus for playback
                int result=mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if(result==AudioManager.AUDIOFOCUS_REQUEST_GRANTED){
                    //   mAudioManager.registerMediaButtonEventReceiver(RemoteControlReceiver);
                    //We have the audio focus naw
                    mediaPlayer=MediaPlayer.create(ColorsActivity.this,r);
                    mediaPlayer.start();
                    /**
                     * Setup a liatener on the media player, so that we can stop and release the media player
                     * once the song has stoped
                     */
                    mediaPlayer.setOnCompletionListener(mCompletionlistener);
                }


            }
        });

    }

    @Override
    protected void onStop() {
        super.onStop();
        //When the activity is stopped, release the media player resaurces because we won't be playing any more sounds
        releaseMediaPlayer();
    }

    /**
     * Clean up the media player by releasing its resources.
     */
    private void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (mediaPlayer != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mediaPlayer.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mediaPlayer = null;
            // Regardless of whether or not we were granted audio focus, abandon it. This also
            // unregisters the AudioFocusChangeListener so we don't get anymore callbacks.
            mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
        }
    }
}
