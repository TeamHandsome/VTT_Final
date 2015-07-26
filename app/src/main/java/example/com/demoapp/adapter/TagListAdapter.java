package example.com.demoapp.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.ArraySwipeAdapter;

import java.util.ArrayList;

import example.com.demoapp.R;
import example.com.demoapp.model.DAO.TagDAO;
import example.com.demoapp.model.TagItem;

/**
 * Created by Long on 7/21/2015.
 */
public class TagListAdapter extends ArraySwipeAdapter<TagItem> {
    Activity context;
    int idLayoutResource;
    ArrayList<TagItem> listTags;
    TagDAO tagDAO = null;

    public TagListAdapter(Activity context, int idLayoutResource, ArrayList<TagItem> listTags) {
        super(context, idLayoutResource, listTags);
        this.context = context;
        this.idLayoutResource = idLayoutResource;
        this.listTags = listTags;
    }

    public class ViewHolder {
        TextView tv_id, tv_nameTag, tv_countTag;
        ImageButton bt_editTag, bt_Delete;
        SwipeLayout swipeLayout;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = context.getLayoutInflater();
            convertView = inflater.inflate(idLayoutResource, parent, false);

            holder.swipeLayout = (SwipeLayout) convertView.findViewById(R.id.swipe);
            holder.tv_id = (TextView) convertView.findViewById(R.id.tv_tagId);
            holder.tv_nameTag = (TextView) convertView.findViewById(R.id.tv_tagName);
            holder.tv_countTag = (TextView) convertView.findViewById(R.id.tv_countTag);

            holder.swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);
            holder.swipeLayout.addDrag(SwipeLayout.DragEdge.Right, holder.swipeLayout.findViewById(R.id.drag_right));
            convertView.setTag(holder);
        }
        holder = (ViewHolder) convertView.getTag();
        holder.bt_editTag = (ImageButton) convertView.findViewById(R.id.bt_editTag);
        holder.bt_editTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        holder.bt_Delete = (ImageButton) convertView.findViewById(R.id.bt_Delete);
        holder.bt_Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        holder.tv_id.setText("" + (position+1));
        holder.tv_nameTag.setText(listTags.get(position).getNameTag());
        ////
        String tagId = listTags.get(position).getId();
        tagDAO = new TagDAO(context);
        ArrayList<String> listSentencesByTagId = tagDAO.listSentencesFollowTagId(tagId);
        holder.tv_countTag.setText(listSentencesByTagId.size()+"文");

        if (position % 2 == 1){
            final int back_color = getContext().getResources().getColor(R.color.colorPrimaryLight);
            convertView.setBackgroundColor(back_color);
        }else {
            convertView.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }


        return convertView;
    }

    @Override
    public int getSwipeLayoutResourceId(int i) {
        return 0;
    }
    @Override
    public int getCount() {

        return listTags.size();
    }

    @Override
    public Object getItem(int i) {
        return listTags.get(i);
    }

    @Override
    public long getItemId(int i) {
        return listTags.get(i).hashCode();
    }

}
