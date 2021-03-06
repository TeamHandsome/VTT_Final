package example.com.demoapp.utility;

/**
 * Created by Tony on 8/7/2015.
 */
public final class Consts {
    public static final String API_KEY = "AIzaSyAH7nWJBI1KJ8yfQtgv3REfBzG-cV733e0";

    //key name when put in Intent and Bundle
    public static final String POSITION = "position";
    public static final String CATEGORY_ID = "category_id";
    public static final String CATEGORY_NAME = "category_name";
    public static final String SUBCATEGORY_ID ="subCategory_id";
    public static final String SENTENCE_ID = "sentence_id";
    public static final String NAME_VN = "name_vn";
    public static final String NAME_JP ="name_jp";
    public static final String REQUEST_ADD_NEW = "ADD_NEW_MYSEN";
    public static final String ACTION_TYPE = "action_type";
    public static final String AVAILABLE_TAG = "available_tag";
    public static final String DATA = "data";
    public static final String NAVIGATION_TEXT ="navigation_text";
    public static final String NAVIGATION_IMAGE ="navigation_image";
    public static final String TAG_ID = "tag_id";
    public static final String PAGER_PARENT = "pager_parent";
    public static final String URI = "uri";
    public static final String SOUND_PATH = "sound_path";

    //action type
    public static final int EDIT_TAG_NORMAL = 10;
    public static final int EDIT_TAG_ADD_SEN = 21;
    public static final int EDIT_TAG_MOD_SEN = 22;
    public static final int ADD_MY_SEN = 31;
    public static final int EDIT_MY_SEN = 32;

    //item's name
    public static final String TAG_NAME = "タグ名";
    public static final String TAG = "タグ";
    public static final String JAPANESE = "日本語";
    public static final String VIETNAMESE = "ベトナム語";
    public static final String IMAGE = "イメージ";
    public static final String AUDIO = "オーディオ";
    public static final String CONVERSATION = "会話集";
    public static final String RECOMMENDATION = "おすすめ";
    public static final String SENTENCE_LIST = "文章";
    public static final String IMAGE_LIST = "画像";
    public static final String FAVORITE_LIST = "お気に入り";
    public static final String HISTORY_LIST = "ヒストリー";
    public static final String MY_SEN_LIST = "私の文書";

    //item's length
    public static final int MAX_TAGNAME_LENGTH = 6;
    public static final int MAX_JAP_CHAR_LENGTH = 13;
    public static final int MAX_VIE_CHAR_LENGTH = 17;
    public static final int MAX_IMAGE_LENGTH = 2;
    public static final int MAX_HISTORY_LENGTH = 50;

    //type
    public static final String MByte = "MB";
    public static final String JPG = "jpeg";
    public static final String PNG = "png";

    //checked and unchecked
    public static final int CHECKED = 1;
    public static final int UNCHECKED = 0;

    //pager parent
    public static final int HOME = 0;
    public static final int SENTENCE_LIST_BY_SUB = 1;
    public static final int FAVORITE = 2;
    public static final int HISTORY = 3;
    public static final int MY_SENTENCE_LIST = 4;
    public static final int SENTENCE_LIST_BY_TAG = 5;

    //delete dialog title
    public static final String DELETE_SENTENCE = "文章を削除";
    public static final String DELETE_FROM_FAVORITE = "お気に入りリストから削除";
    public static final String DELETE_FROM_HISTORY = "ヒストリーから削除";
    public static final String DELETE_TAG = "タグを削除";
    public static final String DELETE_SEN_FROM_TAG = "タグから削除";
    public static final String DELETE_RECORD ="レコーターを削除";
    public static final String DELETE_PHOTO = "写真を削除";

    //image name in drawable
    public static final String NAVI_BACK_FAVORITE = "navi_back_favorite";
    public static final String NAVI_BACK_HISTORY = "navi_back_history";
    public static final String NAVI_BACK_MYSEN = "navi_back_mysen";
    public static final String NAVI_BACK_TAG = "navi_back_tag";

    //record value
    public static final int MAX_RECORD_TIME_MILLISECOND = 5000;

    //cancel dialog title
    public static final String CONFIRM_CANCEL = "キャンセルを確認";

    //Connect Internet
    public static final String CONNECT_INTERNET = "インターネット接続おねがいします！";

    //Add successful
    public static final String ADD_SUCCESSFUL = "正常に追加";
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

    public static final int FOUNDED = 0;

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
        //calling this constructor as well :
        throw new AssertionError();
    }
}
