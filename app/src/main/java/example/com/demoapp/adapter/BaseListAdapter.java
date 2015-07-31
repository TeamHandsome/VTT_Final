package example.com.demoapp.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.ArraySwipeAdapter;

import java.util.ArrayList;

import example.com.demoapp.R;
import example.com.demoapp.activity.AddEditMySentencesActivity;
import example.com.demoapp.activity.AddEditTagActivity;
import example.com.demoapp.activity.PopupActivity;
import example.com.demoapp.extend.ConfirmDeleteDialog;
import example.com.demoapp.model.DAO.FavoriteDAO;
import example.com.demoapp.model.DAO.HistoryDAO;
import example.com.demoapp.model.SentenceItem;
import example.com.demoapp.utility.Common;
import example.com.demoapp.utility.Consts;
import example.com.demoapp.utility.Message;
import example.com.demoapp.utility.MySingleton;

/**
 * Created by Tony on 28/7/2015.
 */
public abstract class BaseListAdapter extends ArraySwipeAdapter<SentenceItem>{
    protected Activity context;
    protected int idLayoutResource;
    protected ArrayList<SentenceItem> listSentences;
    protected long mLastClickTime = 0;
    protected ViewHolder holder = null;

    public static class ViewHolder {
        public TextView tvDisplayID;
        public TextView tvDisplayName;
        public ImageView imageView;
        public ImageButton btDelete, btFavorite, btTag, btEdit;
        public SwipeLayout swipeLayout;
    }

    public BaseListAdapter(Activity context, int idLayoutResource, ArrayList<SentenceItem> listSentences) {
        super(context, idLayoutResource, listSentences);
        this.context = context;
        this.idLayoutResource = idLayoutResource;
        this.listSentences = listSentences;
    }

    public ArrayList<SentenceItem> getListSentences() {
        return listSentences;
    }

    public void setListSentences(ArrayList<SentenceItem> listSentences) {
        this.listSentences = listSentences;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = context.getLayoutInflater();
            convertView = inflater.inflate(idLayoutResource, parent, false);

            holder.swipeLayout = (SwipeLayout) convertView.findViewById(R.id.swipe);
            holder = this.buildHolder(holder,convertView);

            holder.swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);        //set kiểu swipe
            holder.swipeLayout.addDrag(SwipeLayout.DragEdge.Left, holder.swipeLayout.findViewById(R.id.drag_left)); //add swipe left
            holder.swipeLayout.addDrag(SwipeLayout.DragEdge.Right, holder.swipeLayout.findViewById(R.id.drag_right));

            convertView.setTag(holder);

        } else
            holder = (ViewHolder) convertView.getTag();

        return convertView;
    }

    @Override
    public int getSwipeLayoutResourceId(int i) {
        return R.id.swipe;
    }

    @Override
    public int getCount() {
        return listSentences!=null ? listSentences.size():0;
    }

    @Override
    public Object getItem(int i) {
        return listSentences.get(i);
    }

    @Override
    public long getItemId(int i) {
        return listSentences.get(i).hashCode();
    }

    //build holder
    protected abstract ViewHolder buildHolder(ViewHolder holder,View convertView);

    //Setting up sound for a sentence
    protected void setUpSound(final ViewHolder holder,final int position){
        final String soundPath = listSentences.get(position).getSound();
        final String vn_name = listSentences.get(position).getNameVn();
        final String img = listSentences.get(position).getImage();
        final String sentences_id = listSentences.get(position).getId();
        holder.swipeLayout.getSurfaceView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle prevent click many times
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();

                HistoryDAO dao = new HistoryDAO();
                dao.addHistory(sentences_id);

                Intent i = new Intent(context, PopupActivity.class);
                if (sentences_id.contains("s")){
                    i.putExtra(Consts.ACTION_TYPE, Consts.POP_UP_MYSEN);
                }
                i.putExtra(Consts.SOUND_PATH, soundPath);
                i.putExtra(Consts.NAME_VN, vn_name);
                i.putExtra(Consts.URI, img);
                context.startActivity(i);
            }
        });
    }

    //Setting up for Favorite button
    protected void setUpBtnFavorite(final ViewHolder holder,View convertView,final int position){
        final FavoriteDAO dao = new FavoriteDAO();
        final SentenceItem item = listSentences.get(position);

        holder.btFavorite = (ImageButton) convertView.findViewById(R.id.btFavorite);
        if (item.getFavorite()){
            holder.btFavorite = this.setCheckedBtnFavorite(holder.btFavorite);
        }else {
            holder.btFavorite = this.setUnCheckedBtnFavorite(holder.btFavorite);
        }
        holder.btFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((int) holder.btFavorite.getTag() == Consts.UNCHECKED) {
                    dao.addToFavorite(item.getId());
                    setCheckedBtnFavorite(holder.btFavorite);
                    item.setFavorite(true);
                } else {
                    dao.removeFromFavorite(item.getId());
                    setUnCheckedBtnFavorite(holder.btFavorite);
                    item.setFavorite(false);
                }
            }
        });
    }

    private ImageButton setCheckedBtnFavorite(ImageButton imageButton){
        final int back_color = getContext().getResources().getColor(R.color.yellow);
        imageButton.setBackgroundColor(back_color);
        imageButton.setImageResource(R.mipmap.ic_action_favorite_check);
        imageButton.setTag(Consts.CHECKED);
        return imageButton;
    }

    private ImageButton setUnCheckedBtnFavorite(ImageButton imageButton){
        final int back_color = getContext().getResources().getColor(R.color.dark_yellow);
        imageButton.setBackgroundColor(back_color);
        imageButton.setImageResource(R.mipmap.ic_action_favorite_uncheck);
        imageButton.setTag(Consts.UNCHECKED);
        return imageButton;
    }
    //Setting up for Tag button
    protected void setUpBtnTag(final ViewHolder holder, View convertView,final int position){
        holder.btTag = (ImageButton) convertView.findViewById(R.id.btTag);
        holder.btTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AddEditTagActivity.class);
                String sentences_id = listSentences.get(position).getId(); // lay Id cua sentence trong db
                intent.putExtra(Consts.ACTION_TYPE, Consts.EDIT_TAG_NORMAL);
                intent.putExtra(Consts.SENTENCE_ID, sentences_id);
                context.startActivity(intent);
            }
        });
    }

    //Setting up for Delete button
    protected void setUpBtnDelete(final ViewHolder holder, View convertView,final int position){
        holder.btDelete = (ImageButton) convertView.findViewById(R.id.btDelete);
        holder.btDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setUpConfirmDeleteDialog(position);
            }
        });
    }

    //Setting up for confirm delete dialog
    private void setUpConfirmDeleteDialog(final int position) {
        String tag_name = listSentences.get(position).getNameJp();
        String title = setTitleConfirmDeleteDialog();
        String message = Message.CONFIRM_DELETE(tag_name);
        ConfirmDeleteDialog dialog = new ConfirmDeleteDialog(context,title,message) {
            @Override
            public void onClickAccept() {
                onclickDelete(position);
                listSentences.remove(position);
                MySingleton.getInstance().setSentenceList(listSentences);
                notifyDataSetChanged();
            }
        };
        dialog.show();
    }

    //define database action for onclick button delete
    protected abstract void onclickDelete(int position);

    //set title for confirm delete dialog
    protected String setTitleConfirmDeleteDialog(){
        return Consts.DELETE_SENTENCE;
    }

    //Setting up for Edit button
    protected void setUpBtnEdit(final ViewHolder holder, View convertView,final int position){
        holder.btEdit = (ImageButton) convertView.findViewById(R.id.btEdit);
        holder.btEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AddEditMySentencesActivity.class);
                String sentences_id = listSentences.get(position).getId(); // lay Id cua sentence trong db
                intent.putExtra(Consts.ACTION_TYPE, Consts.EDIT_MY_SEN);
                intent.putExtra(Consts.SENTENCE_ID, sentences_id);
                context.startActivity(intent);
            }
        });
    }

    protected void update(ArrayList<SentenceItem> listSentences){
        listSentences.clear();
        notifyDataSetChanged();
    }
}
