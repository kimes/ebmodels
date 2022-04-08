package ph.easybus.ebmodels.models;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class ThirdParty implements Parcelable {

    private String name, reference, origin, destination,
            tripCode, tripTime, trip = "", reservationPath;

    public ThirdParty() {}

    public ThirdParty(Parcel parcel) {
        String[] data = new String[8];
        parcel.readStringArray(data);
        name = data[0];
        reference = data[1];
        origin = data[2];
        destination = data[3];
        tripCode = data[4];
        tripTime = data[5];
        trip = data[6];
        reservationPath = data[7];
    }

    public ThirdParty(JSONObject object) {
        try {
            if (object.has("name")) name = object.getString("name");
            if (object.has("reference")) reference = object.getString("reference");
            if (object.has("origin")) origin = object.getString("origin");
            if (object.has("destination")) destination = object.getString("destination");
            if (object.has("trip_code")) tripCode = object.getString("trip_code");
            if (object.has("trip_time")) tripTime = object.getString("trip_time");
            if (object.has("trip")) trip = object.getString("trip");
            if (object.has("reservation_path")) reservationPath = object.getString("reservation_path");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public JSONObject toJSON() {
        JSONObject object = new JSONObject();
        try {
            object.put("name", name);
            object.put("reference", reference);
            object.put("origin", origin);
            object.put("destination", destination);
            object.put("trip_code", tripCode);
            object.put("trip_time", tripTime);
            object.put("trip", trip);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }

    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeStringArray(new String[] {
            name, reference, origin, destination,
            tripCode, tripTime, trip, reservationPath });
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
    public String getTrip() { return trip; }
    public String getReservationPath() { return reservationPath; }

    public void setName(String name) { this.name = name; }
    public void setReference(String reference) { this.reference = reference; }
    public void setOrigin(String origin) { this.origin = origin; }
    public void setDestination(String destination) { this.destination = destination; }
    public void setTripCode(String tripCode) { this.tripCode = tripCode; }
    public void setTripTime(String tripTime) { this.tripTime = tripTime; }
    public void setTrip(String trip) { this.trip = trip; }
    public void setReservationPath(String reservationPath) { this.reservationPath = reservationPath; }

    public static final Creator<ThirdParty> CREATOR = new Creator<ThirdParty>() {
        public ThirdParty createFromParcel(Parcel parcel) { return new ThirdParty(parcel); }
        public ThirdParty[] newArray(int position) { return new ThirdParty[position]; }
    };
}
