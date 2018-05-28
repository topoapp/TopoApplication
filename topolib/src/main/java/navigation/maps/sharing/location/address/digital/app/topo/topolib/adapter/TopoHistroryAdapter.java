package navigation.maps.sharing.location.address.digital.app.topo.topolib.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;

import navigation.maps.sharing.location.address.digital.app.topo.topolib.R;
import navigation.maps.sharing.location.address.digital.app.topo.topolib.holders.TopoData;


/**
 * Created by User on 3/16/2018.
 */

public class TopoHistroryAdapter extends RecyclerView.Adapter<TopoHistroryAdapter.ViewHolder> {

    OnItemSelectedListener notifier;
    ArrayList<TopoData> topoData;

    public TopoHistroryAdapter(OnItemSelectedListener notifier, ArrayList<TopoData> topoData) {
        this.notifier = notifier;
        this.topoData = topoData;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.topo_histrory_list_item, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindContent(position);
    }

    @Override
    public int getItemCount() {
        return topoData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView title_textview;
        CheckBox selected;
        Context context;

        public ViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            title_textview = (TextView) itemView.findViewById(R.id.title_textview);
            selected = (CheckBox) itemView.findViewById(R.id.selected);
        }

        public void bindContent(final int position) {
            final TopoData data = topoData.get(position);

            if (data != null) {
                title_textview.setText(data.getTopo_name());
            }


            selected.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b) {
                        notifier.onItemSelected(data);
                        selected.setChecked(true);
                    } else {
                        notifier.onItemRemoved(data);
                        selected.setChecked(false);
                    }
                }
            });

        }
    }

    public interface OnItemSelectedListener {
        void onItemSelected(TopoData data);

        void onItemRemoved(TopoData data);
    }
}

