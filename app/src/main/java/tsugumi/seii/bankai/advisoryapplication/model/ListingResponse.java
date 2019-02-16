package tsugumi.seii.bankai.advisoryapplication.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ListingResponse {

    @SerializedName("listing")
    @Expose
    private List<ListingItem> listing = null;
    @SerializedName("status")
    @Expose
    private Status status;

    public List<ListingItem> getListing() {
        return listing;
    }

    public void setListing(List<ListingItem> listing) {
        this.listing = listing;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

}