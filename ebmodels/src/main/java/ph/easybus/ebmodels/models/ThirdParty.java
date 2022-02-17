package ph.easybus.ebmodels.models;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class ThirdParty implements Parcelable {

    private String name, reference, origin, destination, tripCode, tripTime;

    public ThirdParty() {}

    public ThirdParty(Parcel parcel) {
        String[] data = new String[6];
        parcel.readStringArray(data);
        name = data[0];
        reference = data[1];
        origin = data[2];
        destination = data[3];
        tripCode = data[4];
        tripTime = data[5];
    }

    public ThirdParty(JSONObject object) {
        try {
            if (object.has("name")) name = object.getString("name");
            if (object.has("reference")) reference = object.getString("reference");
            if (object.has("origin")) origin = object.getString("origin");
            if (object.has("destination")) destination = object.getString("destination");
            if (object.has("trip_code")) tripCode = object.getString("trip_code");
            if (object.has("trip_time")) tripTime = object.getString("trip_time");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Deprecated
    public static ThirdParty getThirdParty(JSONObject object) {
        ThirdParty nThirdParty = new ThirdParty();
        try {
            if (object.has("name")) nThirdParty.setName(object.getString("name"));
            if (object.has("reference")) nThirdParty.setReference(object.getString("reference"));
            if (object.has("origin")) nThirdParty.setOrigin(object.getString("origin"));
            if (object.has("destination")) nThirdParty.setDestination(object.getString("destination"));
            if (object.has("trip_code")) nThirdParty.setTripCode(object.getString("trip_code"));
            if (object.has("trip_time")) nThirdParty.setTripTime(object.getString("trip_time"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return nThirdParty;
    }

    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeStringArray(new String[] { name, reference, origin, destination,
            tripCode, tripTime });
    }

    public int describeContents() {
        return 0;
    }

    public String getName() { return name; }
    public String getReference() { return reference; }
    public String getOrigin() { return origin; }
    public String getDestination() { return destination; }
    public String getTripCode() { return tripCode; }
    public String getTripTime() { return tripTime; }

    public void setName(String name) { this.name = name; }
    public void setReference(String reference) { this.reference = reference; }
    public void setOrigin(String origin) { this.origin = origin; }
    public void setDestination(String destination) { this.destination = destination; }
    public void setTripCode(String tripCode) { this.tripCode = tripCode; }
    public void setTripTime(String tripTime) { this.tripTime = tripTime; }

    public static final Creator<ThirdParty> CREATOR = new Creator<ThirdParty>() {
        public ThirdParty createFromParcel(Parcel parcel) { return new ThirdParty(parcel); }
        public ThirdParty[] newArray(int position) { return new ThirdParty[position]; }
    };
}
