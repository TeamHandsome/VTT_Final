package example.com.demoapp.favorite;

import android.app.Activity;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.ArraySwipeAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import example.com.demoapp.R;
import example.com.demoapp.model.SentenceItem;

/**
 * Created by dmonkey on 7/15/2015.
 */
public class ListImageAdapter extends ArraySwipeAdapter<SentenceItem> {
    Activity context;
    int idLayoutResource;
    ArrayList<SentenceItem> listSentences;

    private static class ViewHolder {
        ImageView imageView;
        ImageButton btDelete, btFavorite, btSound;
        SwipeLayout swipeLayout;
        TextView test1;

    }

    public ListImageAdapter(Activity context, int idLayoutResource, ArrayList<SentenceItem> listSentences) {
        super(context, idLayoutResource, listSentences);
        this.context = context;
        this.idLayoutResource = idLayoutResource;
        this.listSentences = listSentences;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
//        View v =super.getView(position, convertView, parent);
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = context.getLayoutInflater();
            convertView = inflater.inflate(idLayoutResource, parent, false);

            holder.swipeLayout = (SwipeLayout) convertView.findViewById(R.id.swipe);
            holder.imageView = (ImageView) convertView.findViewById(R.id.imageView1);

//            holder.imageView.setLayoutParams(new GridView.LayoutParams(355, 355));
//            holder.imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//            holder.imageView.setPadding(0, 0, 0, 0);
            //holder.btFavorite = (ImageButton) convertView.findViewById(R.id.btFavorite);

            holder.swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);        //set ki?u swipe
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

}

