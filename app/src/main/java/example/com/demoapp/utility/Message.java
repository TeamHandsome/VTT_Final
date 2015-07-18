package example.com.demoapp.utility;

/**
 * Created by Tony on 8/7/2015.
 */
public final class Message {
    //Message
    public static final String NO_DATA = "データはありません";
    public static final String CONFIRM_DELETE = "削除しても宜しいですか？";
    public static final String CONFIRM_CANCEL = "取り消しても宜しいですか？";

    //ERROR
    public static final String ERROR_LETTERS_ONLY= "英字で入力してください。";
    public static final String ERROR_NO_WHITE_SPACE = "空白は入力できません。";
    public static final String ERROR_REQUIRED = "この項目は必ず入力して下さい。";
    public static final String ERROR_REQUIRED_DIGIT = "数字のみを入力してください。";
    public static final String ERROR_REQUIRED_NUMBER = "有効な数字を入力してください。";
    public static final String ERROR_REQUIRED_INTEGER = "整数で入力してください。";

    //Message error
    public static final String MAX_CHARACTER_LENGTH (String name, int length){
        return String.format("%sは%d文字以内で入力してください。", name, length);
    }
    public static final String MUST_NOT_EMPTY (String name){
        return String.format("%sに空白は入力できません。", name);
    }
    public static final String ITEM_IS_DUPLICATED (String name){
        return String.format("%sが重複しています。", name);
    }
    public static final String MIN_CHAR_LENGTH (String name, int length){
        return String.format("%sは%d文字以上で入力してください。", name, length);
    }
    public static final String RANGE_CHAR (String name, int max, int min){
        return String.format("%sは%d文字から%d 文字までの値を入力してください。", name, max, min);
    }
    public static final String REQUIRED_INPUT (String name){
        return String.format("%sは必ず入力して下さい。", name);
    }
    public static final String MAX_IMAGE_SIZE (int length,String typeLength){
        return String.format("イメージのサイズは$d$s以下です。",length,typeLength);
    }
    public static final String MAX_AUDIO_SIZE (int length,String typeLength){
        return String.format("オーディオのサイズは$d$s以下です。",length,typeLength);
    }
    //Constructor
    public Message() {
        //this prevents even the native class from
        //calling this constructor as well :
        throw new AssertionError();
    }
}
