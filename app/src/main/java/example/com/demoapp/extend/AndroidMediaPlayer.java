package example.com.demoapp.extend;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import example.com.demoapp.model.SentenceItem;
import example.com.demoapp.utility.StringUtils;

/**
 * Created by Tony on 31/7/2015.
 */
public class AndroidMediaPlayer implements Speaker {
    MediaPlayer mediaPlayer;
    Context context;

    public AndroidMediaPlayer(Context context) {
        this.mediaPlayer = getMediaPlayer(context);
        this.context = context;
    }

    public AndroidMediaPlayer(Context context, MediaPlayer mediaPlayer) {
        this.mediaPlayer = mediaPlayer;
        this.context = context;
    }

    @Override
    public boolean playSound() {
        return true;
    }

    @Override
    public boolean resumePlaySound() {
        return true;
    }

    @Override
    public boolean stopPlaySound() {
        return true;
    }

    @Override
    public boolean speak(SentenceItem item) {
        mediaPlayer = getMediaPlayer(context);
        String sound = item.getSound();
        if (item.getId().contains("s")) {
            return this.startMediaPlayerByFilePath(sound);
        }else {
            return this.startMediaPlayerByRawName(sound);
        }
    }

    @Override
    public boolean resumeSpeak() {
        try {
            if (!mediaPlayer.isPlaying()) {
                mediaPlayer.start();
            }
        }catch (IllegalStateException e){
            return false;
        }
        return true;
    }

    @Override
    public boolean stopSpeak() {
        try {
            mediaPlayer.stop();
        }catch (IllegalStateException e){
            return false;
        }
        return true;
    }

    /**Start Media player base on inputted sound's name
     * @param path
     * @return
     */
    private boolean startMediaPlayerByFilePath(String path){
        if (!path.isEmpty()) {
            try {
                mediaPlayer.setDataSource(path);
                mediaPlayer.prepare();
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                }
                mediaPlayer.start();
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }else {
            return false;
        }
        return true;
    }

    private boolean startMediaPlayerByRawName(String soundName){
        if (!soundName.isEmpty()) {
            try {
                Uri uri = StringUtils.buildRawUri(context.getPackageName(), soundName);
                mediaPlayer = MediaPlayer.create(context, uri);
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                }
                mediaPlayer.start();
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }else {
            return false;
        }
        return true;
    }

    static MediaPlayer getMediaPlayer(Context context){

        MediaPlayer mediaplayer = new MediaPlayer();

        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.KITKAT) {
            return mediaplayer;
        }

        try {
            Class<?> cMediaTimeProvider = Class.forName( "android.media.MediaTimeProvider" );
            Class<?> cSubtitleController = Class.forName( "android.media.SubtitleController" );
            Class<?> iSubtitleControllerAnchor = Class.forName( "android.media.SubtitleController$Anchor" );
            Class<?> iSubtitleControllerListener = Class.forName( "android.media.SubtitleController$Listener" );

            Constructor constructor = cSubtitleController.getConstructor(new Class[]{Context.class, cMediaTimeProvider, iSubtitleControllerListener});

            Object subtitleInstance = constructor.newInstance(context, null, null);

            Field f = cSubtitleController.getDeclaredField("mHandler");

            f.setAccessible(true);
            try {
                f.set(subtitleInstance, new Handler());
            }
            catch (IllegalAccessException e) {return mediaplayer;}
            finally {
                f.setAccessible(false);
            }

            Method setsubtitleanchor = mediaplayer.getClass().getMethod("setSubtitleAnchor", cSubtitleController, iSubtitleControllerAnchor);

            setsubtitleanchor.invoke(mediaplayer, subtitleInstance, null);
        } catch (Exception e) {}

        return mediaplayer;
    }
}
