package com.example.android.miwok;

/**
 * Created by Velis on 30.03.2018.
 */

public class Word {
    /** Default translation for the word */
    private String mDefaultTranslation;

    /** Miwok translation for the word */
    private String mMiwokTranslation;

    /**Image rosurce ID for the world*/
    private int mImageRsourceID=NO_IMAGE_PROVIDE;

    private static final int NO_IMAGE_PROVIDE=-1;

    //audio resource id
    private int mAudioResouceId;

     /**
     * Create a new Word object.
     *
     * @param defaultTranslation is the word in a language that the user is already familiar with
     *                           (such as English)
     * @param miwokTranslation is the word in the Miwok language
     *
     * @param i is the resource id of the audio file
     *
     */

     //constructor
    public Word(String defaultTranslation, String miwokTranslation, int i) {
        mDefaultTranslation = defaultTranslation;
        mMiwokTranslation = miwokTranslation;
        mAudioResouceId=i;
    }

    //second constructor
    public Word(String defaultTranslation, String miwokTranslation,int i, int r) {
        mDefaultTranslation = defaultTranslation;
        mMiwokTranslation = miwokTranslation;
        mImageRsourceID=r;
        mAudioResouceId=i;
    }

    /**Get image resurce ID*/
    public int getImageRosurceID(){
        return mImageRsourceID;
    }

    /**
     * Get the default translation of the word.
     */
    public String getDefaultTranslation() {
        return mDefaultTranslation;
    }

    /**
     * get audio resource id of the word
     */
    public int getAudioId(){
        return mAudioResouceId;
    }

    /**
     * Get the Miwok translation of the word.
     */
    public String getMiwokTranslation() {
        return mMiwokTranslation;
    }

    /**
     * Return wheather or not there is an image for thiw word
     */
    public boolean hasImage(){
        return mImageRsourceID!=NO_IMAGE_PROVIDE;
    }

    @Override
    public String toString() {
        return "Word{" +
                "mDefaultTranslation='" + mDefaultTranslation + '\'' +
                ", mMiwokTranslation='" + mMiwokTranslation + '\'' +
                ", mImageRsourceID=" + mImageRsourceID +
                ", mAudioResouceId=" + mAudioResouceId +
                '}';
    }
}
