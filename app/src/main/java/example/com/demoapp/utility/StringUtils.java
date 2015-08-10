package example.com.demoapp.utility;

import android.net.Uri;

/**
 * Created by Vampire on 7/15/2015.
 */
public final class StringUtils {

    //Insert Space after every Character of an existing String
    public static String addSpaceBetweenChar(String string){
        string = string.replaceAll(".(?!$)", "$0 ");
        string = string.replace(" 「", "「");
        string = string.replace(" ・ ", "・");
        return string;
    }

    //build Uri for resource in drawable folder
    public static Uri buildDrawableUri(String packageName, String resourceName){
        Uri uri = Uri.parse("android.resource://" + packageName + "/" +
                "drawable" + "/" + resourceName);
        return uri;
    }

    //build Uri for resource in raw folder
    public static Uri buildRawUri(String packageName, String resourceName){
        Uri uri = Uri.parse("android.resource://" + packageName + "/" +
                "raw" + "/" + resourceName);
        return uri;
    }

    //build String time clock from millisecond
    public static String buildStringTime(int millisecond){
        String time = "00:00";
        millisecond = millisecond/1000;
        int minute = millisecond/60;
        int modulusSecond = millisecond%60;
        if (minute >=0 && minute < 60) {
            time = String.format("%02d", minute);
            time +=":";
            if (modulusSecond>0) {
                time += String.format("%02d", modulusSecond);
            }
        }
        return time;
    }

    public StringUtils() {
        //this prevents even the native class from
        //calling this constructor as well :
        throw new AssertionError();
    }
}
