package vn.edu.usth.ircchat;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    static String[] SubjectValues;
    View view1;
    ViewHolder viewHolder1;
    String letter;
    ColorGenerator generator = ColorGenerator.MATERIAL;

    public RecyclerViewAdapter(String[] SubjectValues1) {
        SubjectValues = SubjectValues1;
    }



    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView letter;
        public TextView textView;
        public final Context context;
        public ViewHolder(View v) {
            super(v);
            context = itemView.getContext();
            letter = (ImageView) v.findViewById(R.id.gmailitem_letter);
            textView = (TextView)v.findViewById(R.id.subject_textview);
            textView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    String[] sendToChatActivity = getNameServerChannel(SubjectValues[pos]);
                    Intent intent =  new Intent(context, ChatActivity.class);
                    intent.putExtra("nickName",sendToChatActivity[0]);
                    intent.putExtra("channel",sendToChatActivity[2]);
                    context.startActivity(intent);
                }
            });
        }
        private String[] getNameServerChannel(String s){
            int firstDash = s.indexOf('-');
            int lastDash = s.lastIndexOf('-');
            String nickName = s.substring(0,firstDash);
            nickName = nickName.replaceAll("\\s","");
            String server = s.substring(firstDash+1, lastDash);
            server = server.replaceAll("\\s","");
            String channel = s.substring(lastDash+1);
            channel = channel.replaceAll("\\s","");
            String[] arr = new String[3];
            arr[0] = nickName;
            arr[1] = server;
            arr[2] = "#"+channel;
            return arr;
        }
    }


    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_items, parent, false);
        viewHolder1 = new ViewHolder(view1);
        return viewHolder1;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.textView.setText(SubjectValues[position]);
        if(SubjectValues[position] != null) {
            //        Get the first letter of list item
            letter = SubjectValues[position].substring(0, 1);
//        Create a new TextDrawable for our image's background
            TextDrawable drawable = TextDrawable.builder()
                    .buildRound(letter, generator.getRandomColor());
            holder.letter.setImageDrawable(drawable);
        }

    }

    @Override
    public int getItemCount() {
        return SubjectValues.length;
    }
}
