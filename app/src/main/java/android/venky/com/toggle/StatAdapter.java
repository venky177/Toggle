package android.venky.com.toggle;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

public class StatAdapter extends RecyclerView.Adapter<StatAdapter.PersonViewHolder> {
    List<Device> dev;

    StatAdapter(List<Device> devices){
        this.dev = devices;
    }





    @Override
    public StatAdapter.PersonViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.statdevice, viewGroup, false);

        PersonViewHolder pvh = new PersonViewHolder(v);

        return pvh;
    }

    @Override
    public void onBindViewHolder(final PersonViewHolder personViewHolder, int i) {
       personViewHolder.Location.setText(dev.get(i).getRoom());
        personViewHolder.Devname.setText(dev.get(i).getName()+"  ");
        /*SimpleDateFormat dateformat = new SimpleDateFormat("MMM dd, yyyy HH:mm");
        personViewHolder.Date.setText(dateformat.format(dev.get(i).getId()));*/
      /*  personViewHolder.Duration.setText(dev.get(i).getHoursOn()+ " Hrs");*/
        personViewHolder.Power.setText((dev.get(i).getVolt()*dev.get(i).getHoursOn())/1000+" kWH");

    }
    @Override
    public int getItemCount() {
        return dev.size();
    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public static class PersonViewHolder extends RecyclerView.ViewHolder {

      TextView Devname,Power,Location,Date;
        PersonViewHolder(View itemView) {
            super(itemView);
            Devname=(TextView)itemView.findViewById(R.id.devname);
           /* Duration=(TextView)itemView.findViewById(R.id.Durationval);*/
            Power=(TextView)itemView.findViewById(R.id.powerval);
            Location=(TextView)itemView.findViewById(R.id.locationval);
           // Date=(TextView)itemView.findViewById(R.id.dateval);
        }
    }}