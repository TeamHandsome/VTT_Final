package example.com.demoapp.utility;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.net.Uri;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tony.taglibrary.Tag;
import com.example.tony.taglibrary.TagView;
import com.squareup.picasso.Picasso;

import example.com.demoapp.R;
import example.com.demoapp.extend.CustomToast;

/**
 * Created by Tony on 8/7/2015.
 */
public final class Common {
    //show message using toast_error
    public static void showToast(Activity activity, String message) {
        CustomToast toast = new CustomToast(activity,message);
        toast.show();
    }
    public static void showToast(Activity activity, String message,int toast_type) {
        CustomToast toast = new CustomToast(activity,message,toast_type);
        toast.show();
    }
    public static void showToast(Activity activity, String message,int toast_type,int duration) {
        CustomToast toast = new CustomToast(activity,message,toast_type,duration);
        toast.show();
    }

    public static void addNewTagToTagView(Activity activity,TagView tagView, String tagName) {
        Tag tag = new Tag(tagName);
        tag.layoutColor = Color.parseColor("#00BCD4");
        tag.tagTextSize = 20f;
        tag.radiusSet = new float[]{20,20,0,0,0,0,20,20};

        tagView.addTag(tag);
    }

    public static void initNavigationHeaderView(final View view, final Context context,
                                                String text , String image) {
        TextView textView = (TextView)view.findViewById(R.id.navigation_text);
        textView.setText(StringUtils.addSpaceBetweenChar(text));
        setImageForNavigationHeader(view, context, image);
    }

    public static void setImageForNavigationHeader(View view, final Context context, String image){
        WindowManager wm = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.y;
        int height = size.x*131/960;
        ImageView imageView = (ImageView)view.findViewById(R.id.navigation_back);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        Uri uri=StringUtils.buildDrawableUri(context.getPackageName(),image);
        Picasso.with(context)
                .load(uri)
                .resize(width, height)
                .centerCrop()
                .into(imageView);
    }

    public static Point getScreenSizeInPixels(Context context){
        WindowManager wm = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size;
    }

    public Common() {
        //this prevents even the native class from
        //calling this constructor as well :
        throw new AssertionError();
    }
}
