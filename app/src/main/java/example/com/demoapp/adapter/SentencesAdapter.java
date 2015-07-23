package example.com.demoapp.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.ArraySwipeAdapter;

import java.util.ArrayList;

import example.com.demoapp.R;
import example.com.demoapp.activity.AddEditTagActivity;
import example.com.demoapp.model.DAO.FavoriteDAO;
import example.com.demoapp.model.SentenceItem;
import example.com.demoapp.subCategory.PopupActivity;
import example.com.demoapp.utility.Common;
import example.com.demoapp.utility.Consts;
import example.com.demoapp.utility.Message;

/**
 * Created by Long on 6/21/2015.
 */
public class SentencesAdapter extends ArraySwipeAdapter<SentenceItem> {

    Activity context;
    int idLayoutResource;
    ArrayList<SentenceItem> listSentences;
    long mLastClickTime = 0;

    private static class ViewHolder {
        TextView tvDisplayID, tvDisplayName;
        ImageButton btDelete, btFavorite, btTag;
        SwipeLayout swipeLayout;
    }

    public SentencesAdapter(Activity context, int idLayoutResource, ArrayList<SentenceItem> listSentences) {
        super(context, idLayoutResource, listSentences);
        this.context = context;
        this.idLayoutResource = idLayoutResource;
        this.listSentences = listSentences;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = context.getLayoutInflater();
            convertView = inflater.inflate(idLayoutResource, parent, false);

            holder.swipeLayout = (SwipeLayout) convertView.findViewById(R.id.swipe);
            holder.tvDisplayID = (TextView) convertView.findViewById(R.id.tvDisplayId);
            holder.tvDisplayName = (TextView) convertView.findViewById(R.id.tvDisplayName);

            holder.swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);        //set kiểu swipe
            holder.swipeLayout.addDrag(SwipeLayout.DragEdge.Left, holder.swipeLayout.findViewById(R.id.drag_left)); //add swipe left
            holder.swipeLayout.addDrag(SwipeLayout.DragEdge.Right, holder.swipeLayout.findViewById(R.id.drag_right));

            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();

        //set text for list view
        holder.tvDisplayID.setText("" + (position + 1));
        holder.tvDisplayName.setText(listSentences.get(position).getNameJp());

        this.setUpSound(holder, position);
        this.setUpBtnFavorite(holder, convertView, position);
        this.setUpBtnTag(holder, convertView, position);
        this.setUpBtnDelete(holder, convertView, position);

        return convertView;
    }

    @Override
    public int getSwipeLayoutResourceId(int i) {
        return R.id.swipe;
    }

    @Override
    public int getCount() {

        return listSentences.size();
    }

    @Override
    public Object getItem(int i) {
        return listSentences.get(i);
    }

    @Override
    public long getItemId(int i) {
        return listSentences.get(i).hashCode();
    }

    //Setting up sound for a sentence
    private void setUpSound(final ViewHolder holder,final int position){
        final String soundPath = listSentences.get(position).getSound();
        final String vn_name = listSentences.get(position).getNameVn();
        final String img = listSentences.get(position).getImage();
        holder.swipeLayout.getSurfaceView().setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle prevent click many times
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();
                Intent i = new Intent(context, PopupActivity.class);
                i.putExtra("position", soundPath);
                i.putExtra("vn_name", vn_name);
                i.putExtra("img", img);
                context.startActivity(i);
            }
        });
    }

    //Setting up for Favorite button
    private void setUpBtnFavorite(final ViewHolder holder,View convertView,final int position){
        final FavoriteDAO dao = new FavoriteDAO(getContext());
        final SentenceItem item = listSentences.get(position);

        holder.btFavorite = (ImageButton) convertView.findViewById(R.id.btFavorite);
        if (item.getFavorite()){
            holder.btFavorite = this.setCheckedBtnFavorite(holder.btFavorite);
        }else {
            holder.btFavorite = this.setUnCheckedBtnFavorite(holder.btFavorite);
        }

        holder.btFavorite.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if((int)holder.btFavorite.getTag()==Consts.UNCHECKED) {
                    dao.addToFavorite(item.getId());
                    setCheckedBtnFavorite(holder.btFavorite);
                }else {
                    dao.removeFromFavorite(item.getId());
                    setUnCheckedBtnFavorite(holder.btFavorite);
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
    private void setUpBtnTag(final ViewHolder holder, View convertView,final int position){
        holder.btTag = (ImageButton) convertView.findViewById(R.id.btTag);
        holder.btTag.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AddEditTagActivity.class);
                String sentences_id =  listSentences.get(position).getId(); // lay Id cua sentence trong db
                intent.putExtra(Consts.ACTION_TYPE,Consts.EDIT_TAG_NORMAL);
                intent.putExtra(Consts.SENTENCE_ID, sentences_id);
                context.startActivity(intent);
            }
        });
    }

    //Setting up for Delete button
    private void setUpBtnDelete(final ViewHolder holder, View convertView,final int position){
        holder.btDelete = (ImageButton) convertView.findViewById(R.id.btDelete);
        holder.btDelete.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String mess = Message.ITEM_IS_DELETED(holder.tvDisplayName.getText().toString()+" ");
                Common.showToastMessage(getContext(),mess);
            }
        });
    }
}
