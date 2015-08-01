package example.com.demoapp.extend;

import android.content.Context;

import example.com.demoapp.model.SentenceItem;

/**
 * Created by Tony on 31/7/2015.
 */
public interface Speaker {
    boolean playSound();

    boolean resumePlaySound();

    boolean stopPlaySound();

    boolean speak(SentenceItem item);

    boolean resumeSpeak();

    boolean stopSpeak();
}
