package example.com.demoapp.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.ArraySwipeAdapter;

import java.util.ArrayList;

import example.com.demoapp.R;
import example.com.demoapp.activity.AddEditTagActivity;
import example.com.demoapp.model.SentenceItem;
import example.com.demoapp.subCategory.PopupActivity;
import example.com.demoapp.utility.Consts;

/**
 * Created by Long on 6/21/2015.
 */
public class SentencesAdapter extends ArraySwipeAdapter<SentenceItem> {

    Activity context;
    int idLayoutResource;
    ArrayList<SentenceItem> listSentences;

    public static final int REQUEST_CODE_TAG = 113;

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
//        View v =super.getView(position, convertView, parent);
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
        //click button tag lay position cua sentences, gui len Tag
        holder.btDelete = (ImageButton) convertView.findViewById(R.id.btDelete);
        final ViewHolder finalHolder = holder;
        holder.btDelete.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(v.getContext(), "Deleted " + finalHolder.tvDisplayName.getText().toString() + "!", Toast.LENGTH_SHORT).show();
            }
        });
        holder.btTag = (ImageButton) convertView.findViewById(R.id.btTag);
        holder.btTag.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AddEditTagActivity.class);
                int sentences_id =  listSentences.get(position).getId(); // lay Id cua sentence trong db
                intent.putExtra(Consts.ACTION_TYPE,Consts.EDIT_TAG_NORMAL);
                intent.putExtra(Consts.SENTENCE_ID, sentences_id);
                context.startActivity(intent);
            }
        });
        // gán soundPath lên PopUp, setText cho row ListView
        final String soundPath = listSentences.get(position).getSound();
        holder.swipeLayout.getSurfaceView().setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, PopupActivity.class);
                i.putExtra("position", soundPath);
                context.startActivity(i);
            }
        });
        holder.tvDisplayID.setText("" + (position+1));
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
