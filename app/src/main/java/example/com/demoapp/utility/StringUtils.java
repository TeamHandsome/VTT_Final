package example.com.demoapp.utility;

/**
 * Created by Vampire on 7/15/2015.
 */
public final class StringUtils {


    public StringUtils() {
        //this prevents even the native class from
        //calling this constructor as well :
        throw new AssertionError();
    }
}
