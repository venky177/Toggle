package android.venky.com.toggle;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import org.w3c.dom.Text;

import java.util.List;
import java.util.jar.Attributes;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.PersonViewHolder> {
    List<Alarms> alarms;

    CardAdapter(Context context){
        this.alarms = FireBase.alarmsInRoom;
    }





    @Override
    public CardAdapter.PersonViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.alarms, viewGroup, false);

        PersonViewHolder pvh = new PersonViewHolder(v);

        return pvh;
    }

    @Override
    public void onBindViewHolder(final PersonViewHolder personViewHolder, int i) {
        personViewHolder.Im.setImageResource(alarms.get(i).ImageID);
        personViewHolder.Date.setText(alarms.get(i).date.getDate() + "-" + alarms.get(i).date.getMonth() + "-" + alarms.get(i).date.getYear());
        personViewHolder.Name.setText(alarms.get(i).getName());
        personViewHolder.Time.setText(alarms.get(i).getDate().getHours()+" : "+alarms.get(i).date.getMinutes() );
        personViewHolder.room.setText(alarms.get(i).getRoom());
        if(alarms.get(i).State==1){
            personViewHolder.toggleButton.setChecked(true);
        }
        else if (alarms.get(i).State==0){
            personViewHolder.toggleButton.setChecked(false);
        }


    }
    @Override
    public int getItemCount() {
        return alarms.size();
    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public static class PersonViewHolder extends RecyclerView.ViewHolder {

        TextView Date,room,Name,Time;
        ImageView Im;
        ToggleButton toggleButton;
        CardView cv;
        PersonViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            Im=(ImageView)itemView.findViewById(R.id.imageView);
            Date = (TextView)itemView.findViewById(R.id.date);
            room=(TextView)itemView.findViewById(R.id.location);
            Name=(TextView)itemView.findViewById(R.id.name);
            Time=(TextView)itemView.findViewById(R.id.time);
            toggleButton = (ToggleButton) itemView.findViewById(R.id.toggleButton2);

        }
    }}