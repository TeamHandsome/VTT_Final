package example.com.demoapp.utility;

/**
 * Created by Tony on 8/7/2015.
 */
public final class Consts {
    /**
     * Common variable name when put in Intent
     */
    public static final String POSITION = "position";
    public static final String CATEGORY_ID = "category_id";
    public static final String SUBCATEGORY_ID ="subCategory_id";
    public static final String SENTENCE_ID = "sentence_id";
    public static final String REQUEST_ADD_NEW = "ADD_NEW_MYSEN";

    /**
     * Opposite of {@link #FAILS}.
     */
    public static final boolean PASSES = true;
    /**
     * Opposite of {@link #PASSES}.
     */
    public static final boolean FAILS = false;

    /**
     * Opposite of {@link #FAILURE}.
     */
    public static final boolean SUCCESS = true;
    /**
     * Opposite of {@link #SUCCESS}.
     */
    public static final boolean FAILURE = false;

    /**
     * Opposite of {@link #INFO}.
     */
    public static final int INFO = 0;
    /**
     * Opposite of {@link #WARNING}.
     */
    public static final int WARNING = 1;
    /**
     * Opposite of {@link #ERROR}.
     */
    public static final int ERROR = 2;

    /**
     * Useful for {@link String} operations, which return an index of
     * <tt>-1</tt> when an item is not found.
     */
    public static final int NOT_FOUND = -1;

    /**
     * System property - <tt>line.separator</tt>
     */
    public static final String NEW_LINE = System.getProperty("line.separator");
    /**
     * System property - <tt>file.separator</tt>
     */
    public static final String FILE_SEPARATOR = System.getProperty("file.separator");
    /**
     * System property - <tt>path.separator</tt>
     */
    public static final String PATH_SEPARATOR = System.getProperty("path.separator");
    public static final String EMPTY_STRING = "";
    public static final String SPACE = " ";
    public static final String TAB = "\t";
    public static final String SINGLE_QUOTE = "'";
    public static final String PERIOD = ".";
    public static final String DOUBLE_QUOTE = "\"";

    // PRIVATE //
    /**
     * The caller references the constants using <tt>Consts.EMPTY_STRING</tt>,
     * and so on. Thus, the caller should be prevented from constructing objects
     * of this class, by declaring this private constructor.
     */
    private Consts() {
        //this prevents even the native class from
        //calling this ctor as well :
        throw new AssertionError();
    }
}
