package example.com.demoapp.utility;

import android.net.Uri;

/**
 * Created by Vampire on 7/15/2015.
 */
public final class StringUtils {

    //Insert Space after every Character of an existing String
    public static String addSpaceBetweenChar(String string){
        return string.replaceAll(".(?!$)", "$0 ");
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

    public StringUtils() {
        //this prevents even the native class from
        //calling this constructor as well :
        throw new AssertionError();
    }
}
