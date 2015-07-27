package example.com.demoapp.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.ArraySwipeAdapter;

import java.util.ArrayList;

import example.com.demoapp.R;
import example.com.demoapp.activity.TagPagerActivity;
import example.com.demoapp.extend.ConfirmDeleteDialog;
import example.com.demoapp.model.DAO.TagDAO;
import example.com.demoapp.model.TagItem;
import example.com.demoapp.utility.Common;
import example.com.demoapp.utility.Consts;
import example.com.demoapp.utility.Message;
import example.com.demoapp.utility.StringUtils;

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
        ImageButton bt_Edit, bt_Delete;
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

        this.setUpListView(holder,convertView,position);
        this.setOnclickOnItemListView(holder, position);
        this.setUpBtnEdit(holder, convertView, position);
        this.setUpBtnDelete(holder,convertView,position);

        return convertView;
    }

    @Override
    public int getSwipeLayoutResourceId(int i) {
        return R.id.swipe;
    }
    @Override
    public int getCount() { return listTags!=null ? listTags.size():0; }

    @Override
    public Object getItem(int i) {
        return listTags.get(i);
    }

    @Override
    public long getItemId(int i) {
        return listTags.get(i).hashCode();
    }

    //Setting up data to show on List view
    private void setUpListView(final ViewHolder holder,View convertView,final int position){
        holder.tv_id.setText("" + (position+1));
        holder.tv_nameTag.setText(listTags.get(position).getNameTag());

        String tagId = listTags.get(position).getId();
        tagDAO = new TagDAO(context);
        ArrayList<String> listSentencesByTagId = tagDAO.listSentencesFollowTagId(tagId);
        holder.tv_countTag.setText(listSentencesByTagId.size() + "文");

        if (position % 2 == 1){
            final int back_color = getContext().getResources().getColor(R.color.colorPrimaryLight);
            convertView.setBackgroundColor(back_color);
        }else {
            convertView.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }
    }

    //Setting up onclick on a list view item
    private void setOnclickOnItemListView(final ViewHolder holder,final int position){
        holder.swipeLayout.getSurfaceView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, TagPagerActivity.class);
                TagItem item = listTags.get(position);
                //send tag id
                intent.putExtra(Consts.TAG_ID, item.getId());
                //send navigation text
                String text = Consts.TAG + "-" + item.getNameTag();
                text = StringUtils.addSpaceBetweenChar(text);
                intent.putExtra(Consts.NAVIGATION_TEXT, text);

                context.startActivity(intent);
            }
        });
    }
    //Setting up for Delete button
    private void setUpBtnEdit(final ViewHolder holder, View convertView,final int position){
        holder.bt_Edit = (ImageButton) convertView.findViewById(R.id.bt_Edit);
        holder.bt_Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    //Setting up for Tag button
    private void setUpBtnDelete(final ViewHolder holder, View convertView,final int position){
        holder.bt_Delete = (ImageButton) convertView.findViewById(R.id.bt_Delete);
        holder.bt_Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setUpConfirmDeleteDialog(position);
            }
        });
    }

    //Setting up for confirm delete dialog
    private void setUpConfirmDeleteDialog(final int position) {
        String tag_name = listTags.get(position).getNameTag();
        String title = Consts.DELETE_TAG;
        String message = Message.CONFIRM_DELETE(tag_name);
        ConfirmDeleteDialog dialog = new ConfirmDeleteDialog(context,title,message) {
            @Override
            public void onClickAccept() {
                TagDAO dao = new TagDAO(context);
                dao.removeTagByID(listTags.get(position).getId());
                listTags.remove(position);
                TagListAdapter.this.notifyDataSetChanged();
            }
        };
        dialog.show();
    }
}
