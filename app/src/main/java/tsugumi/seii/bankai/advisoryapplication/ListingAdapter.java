package tsugumi.seii.bankai.advisoryapplication;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import tsugumi.seii.bankai.advisoryapplication.model.ListingItem;

public class ListingAdapter extends RecyclerView.Adapter<ListingAdapter.ViewHolder> {

    private List<ListingItem> mListing;

    public ListingAdapter(List<ListingItem> listing){
        mListing = listing;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView idText;
        public TextView listNameText;
        public TextView distanceText;

        public ViewHolder(View itemView, final Context context) {
            super(itemView);

            idText = itemView.findViewById(R.id.id_text);
            listNameText = itemView.findViewById(R.id.list_name_text);
            distanceText = itemView.findViewById(R.id.distance_text);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context,listNameText.getText()+", "+distanceText.getText(),Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View contactView = inflater.inflate(R.layout.listing_item_view, parent, false);

        ViewHolder viewHolder = new ViewHolder(contactView, context);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        ListingItem listingItem = mListing.get(position);
        viewHolder.idText.setText(listingItem.getId());
        viewHolder.listNameText.setText(listingItem.getListName());
        viewHolder.distanceText.setText(listingItem.getDistance());
    }

    @Override
    public int getItemCount() {
        return mListing.size();
    }

    public List<ListingItem> getListing(){
        return mListing;
    }

}
