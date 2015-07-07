package example.com.demoapp.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.ArraySwipeAdapter;

import java.util.ArrayList;

import example.com.demoapp.R;
import example.com.demoapp.model.DisplaySentencesItem;
import example.com.demoapp.subCategory.PopupActivity;

/**
 * Created by Long on 6/21/2015.
 */
public class SentencesAdapter extends ArraySwipeAdapter<DisplaySentencesItem> {

    Activity context;
    int idLayoutResource;
    ArrayList<DisplaySentencesItem> listSentences;

    private static class ViewHolder {
        TextView tvDisplayID, tvDisplayName;
        ImageButton btDelete, btFavorite, btSound;
        SwipeLayout swipeLayout;

    }

    public SentencesAdapter(Activity context, int idLayoutResource, ArrayList<DisplaySentencesItem> listSentences) {
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
            holder.tvDisplayID = (TextView) convertView.findViewById(R.id.tvDisplayId);
            holder.tvDisplayName = (TextView) convertView.findViewById(R.id.tvDisplayName);
            holder.btDelete = (ImageButton) convertView.findViewById(R.id.btDelete);
            //holder.btFavorite = (ImageButton) convertView.findViewById(R.id.btFavorite);

            holder.swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);        //set kiểu swipe
            holder.swipeLayout.addDrag(SwipeLayout.DragEdge.Left, holder.swipeLayout.findViewById(R.id.drag_left)); //add swipe left
            holder.swipeLayout.addDrag(SwipeLayout.DragEdge.Right, holder.swipeLayout.findViewById(R.id.drag_right));

            final ViewHolder finalHolder = holder;  // final
            holder.btDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Toast.makeText(v.getContext(), "Deleted " + finalHolder.tvDisplayName.getText().toString() + "!", Toast.LENGTH_SHORT).show();
                }
            });
//            holder.btFavorite.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Toast.makeText(v.getContext(), "Favorite " + finalHolder.tvDisplayName.getText().toString()+"!", Toast.LENGTH_SHORT).show();
//                }
//            });

            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();
        // gán soundPath lên PopUp, setText cho row ListView
        final String soundPath = listSentences.get(position).getSound();
        holder.swipeLayout.getSurfaceView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, PopupActivity.class);
                i.putExtra("position", soundPath);
                context.startActivity(i);
            }
        });
        holder.tvDisplayID.setText(""+(position+1));
        holder.tvDisplayName.setText(listSentences.get(position).getNameJp());

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
