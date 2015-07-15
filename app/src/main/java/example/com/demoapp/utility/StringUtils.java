package example.com.demoapp.utility;

/**
 * Created by Vampire on 7/15/2015.
 */
public final class StringUtils {
    //Message error
    public static final String TEXT_TOO_LONG (String name, int length){
        return String.format("%s must smaller than %d", name, length);
    }
    public static final String TEXT_EMPTY (String name){
        return String.format("%s is not empty!", name);
    }
    public static final String TEXT_EXISTED (String name){
        return String.format("%s is existed!", name);
    }
}
