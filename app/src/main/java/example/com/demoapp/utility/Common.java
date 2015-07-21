package example.com.demoapp.utility;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.widget.Toast;

import com.example.tony.taglibrary.Tag;
import com.example.tony.taglibrary.TagView;

import example.com.demoapp.R;

/**
 * Created by Tony on 8/7/2015.
 */
public final class Common {
    //show message using toast
    public static void showToastMessage (Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void addNewTagToTagView(Activity activity,TagView tagView, String tagName) {
        Tag tag = new Tag(tagName);
        tag.layoutBorderSize = 1f;
        tag.layoutBorderColor = activity.getResources().getColor(R.color.colorPrimary);
        tag.tagTextSize = 23f;
        tag.radiusSet = new float[]{30,30,0,0,0,0,30,30};
        tag.isDeletable = true;

        tagView.addTag(tag);
    }
}
