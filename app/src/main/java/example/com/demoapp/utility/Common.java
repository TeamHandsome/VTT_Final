package example.com.demoapp.utility;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Tony on 8/7/2015.
 */
public final class Common {

public static void showToastMessage (Context context, String message) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
}
}
