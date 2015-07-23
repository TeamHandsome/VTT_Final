package example.com.demoapp.adapter.image_adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.net.Uri;
import android.os.SystemClock;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.ArraySwipeAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import example.com.demoapp.R;
import example.com.demoapp.activity.AddEditMySentencesActivity;
import example.com.demoapp.activity.AddEditTagActivity;
import example.com.demoapp.model.DAO.FavoriteDAO;
import example.com.demoapp.model.SentenceItem;
import example.com.demoapp.activity.PopupActivity;
import example.com.demoapp.utility.Common;
import example.com.demoapp.utility.Consts;
import example.com.demoapp.utility.Message;

/**
 * Created by Tony on 23/7/2015.
 */
public abstract class BaseImageAdapter extends ArraySwipeAdapter<SentenceItem> {
    Activity context;
    int idLayoutResource;
    ArrayList<SentenceItem> listSentences;
    protected long mLastClickTime = 0;
    protected ViewHolder holder = null;

    private static class ViewHolder {
        ImageView imageView;
        ImageButton btDelete, btFavorite, btTag, btEdit;
        SwipeLayout swipeLayout;
    }

    public BaseImageAdapter(Activity context, int idLayoutResource, ArrayList<SentenceItem> listSentences) {
        super(context, idLayoutResource, listSentences);
        this.context = context;
        this.idLayoutResource = idLayoutResource;
        this.listSentences = listSentences;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        WindowManager wm = (WindowManager)parent.getContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int y = width/2;

        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = context.getLayoutInflater();
            convertView = inflater.inflate(idLayoutResource, parent, false);

            holder.swipeLayout = (SwipeLayout) convertView.findViewById(R.id.swipe);
            holder.imageView = (ImageView) convertView.findViewById(R.id.imageView);

//            holder.imageView.setLayoutParams(new GridView.LayoutParams(y, y));
            holder.imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            holder.imageView.setPadding(2, 2, 2, 2);

            holder.swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);        //set kiểu swipe
            holder.swipeLayout.addDrag(SwipeLayout.DragEdge.Left, holder.swipeLayout.findViewById(R.id.drag_left)); //add swipe left
            holder.swipeLayout.addDrag(SwipeLayout.DragEdge.Right, holder.swipeLayout.findViewById(R.id.drag_right));

            convertView.setTag(holder);

        } else
            holder = (ViewHolder) convertView.getTag();

        String pathImage = listSentences.get(position).getImage();
        System.out.println(pathImage + "aaa");
        Uri uri=Uri.parse("android.resource://" + context.getPackageName() + "/" +
                "drawable" + "/" + pathImage);

        Picasso.with(context)
                .load(uri)
                .resize(360, 360)
                .centerCrop()
                .into(holder.imageView);
//        holder.imageView.setImageResource(context.getResources().getIdentifier(pathImage, "drawable", context.getPackageName()));

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
    protected void setUpSound(final ViewHolder holder,final int position){
        final String soundPath = listSentences.get(position).getSound();
        final String vn_name = listSentences.get(position).getNameVn();
        final String img = listSentences.get(position).getImage();
        holder.swipeLayout.getSurfaceView().setOnClickListener(new View.OnClickListener() {
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
    protected void setUpBtnFavorite(final ViewHolder holder,View convertView,final int position){
        final FavoriteDAO dao = new FavoriteDAO(getContext());
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
                    Common.showToastMessage(getContext(), Message.FAVORITE_SIGN_UP);
                } else {
                    dao.removeFromFavorite(item.getId());
                    setUnCheckedBtnFavorite(holder.btFavorite);
                    item.setFavorite(false);
                    Common.showToastMessage(getContext(), Message.FAVORITE_UN_SIGN);
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
                onclickDelete(position);
                listSentences.remove(position);
                BaseImageAdapter.this.notifyDataSetChanged();
            }
        });
    }

    protected abstract void onclickDelete(int position);

    //Setting up for Tag button
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
}
