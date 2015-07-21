package example.com.demoapp.utility;

/**
 * Created by Vampire on 7/15/2015.
 */
public final class StringUtils {

    //Insert Space after every Character of an existing String
    public static String addSpaceBetweenChar(String string){
        return string.replaceAll(".(?!$)", "$0 ");
    }

    public StringUtils() {
        //this prevents even the native class from
        //calling this constructor as well :
        throw new AssertionError();
    }
}
