package example.com.demoapp.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.ArraySwipeAdapter;

import java.util.ArrayList;

import example.com.demoapp.R;
import example.com.demoapp.activity.TagPagerActivity;
import example.com.demoapp.extend.ConfirmDeleteDialog;
import example.com.demoapp.extend.EditTagDialog;
import example.com.demoapp.model.DAO.TagDAO;
import example.com.demoapp.model.TagItem;
import example.com.demoapp.utility.Consts;
import example.com.demoapp.utility.Message;

/**
 * Created by Long on 7/21/2015.
 */
public class TagListAdapter extends ArraySwipeAdapter<TagItem> {
    Activity context;
    int idLayoutResource;
    ArrayList<TagItem> listTags;
    ArrayList<TagItem> listAll;
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

    public void setListTags(ArrayList<TagItem> listTags) {
        this.listTags = listTags;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
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

        this.setUpListView(holder, convertView, position);
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
        tagDAO = new TagDAO();
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
                setUpEditTagDialog(position);
            }
        });
    }

    //Setting up for confirm delete dialog
    private void setUpEditTagDialog(final int position) {
        String tag_name = listTags.get(position).getNameTag();
        EditTagDialog dialog = new EditTagDialog(context,tag_name) {
            @Override
            public void onClickAccept() {
                String tag_name = getTag_name();
                String message = validate(tag_name);
                if (message.equalsIgnoreCase("")) {
                    TagDAO dao = new TagDAO();
                    dao.updateTagName(listTags.get(position).getId(), tag_name);
                    listTags.get(position).setNameTag(tag_name);
                    TagListAdapter.this.notifyDataSetChanged();
                    setClose(true);
                }else{
                    setEt_Error(message);
                    setClose(false);
                }
            }
        };
        dialog.show();
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
                TagDAO dao = new TagDAO();
                dao.removeTagByID(listTags.get(position).getId());
                listTags.remove(position);
                TagListAdapter.this.notifyDataSetChanged();
            }
        };
        dialog.show();
    }

    private boolean isDuplicateTag(String tagName) {   //handle exception if duplicate available Tag
        for (TagItem i : listTags) {
            if (i.getNameTag().equalsIgnoreCase(tagName))
                return false;
        }
        return true;
    }

    private String validate(String tag_name){
        String message = "";
        if (tag_name == null || tag_name.trim().isEmpty()){
            message = Message.MUST_NOT_EMPTY(Consts.TAG_NAME);
            return message;
        }
        if(!isDuplicateTag(tag_name)){
            message = Message.ITEM_IS_DUPLICATED(tag_name);
            return message;
        };
        if(tag_name.length() > Consts.MAX_TAGNAME_LENGTH){
            message = Message.MAX_CHARACTER_LENGTH(Consts.TAG_NAME, Consts.MAX_TAGNAME_LENGTH);
            return message;
        }
        return message;
    }
    // Filter Class
    public Filter getFilter() {
        return new Filter() {

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                final FilterResults oReturn = new FilterResults();
                final ArrayList<TagItem> results = new ArrayList<TagItem>();
                if (listAll == null)
                    listAll = listTags;
                if (constraint != null) {
                    if (listAll != null && listAll.size() > 0) {
                        for (final TagItem g : listAll) {
                            if (g.getNameTag().toLowerCase().contains(constraint.toString()))

                                results.add(g);
                        }
                    }
                    oReturn.values = results;
                }
                return oReturn;
            }

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint,
                                          FilterResults results) {
                listTags = (ArrayList<TagItem>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }
}
