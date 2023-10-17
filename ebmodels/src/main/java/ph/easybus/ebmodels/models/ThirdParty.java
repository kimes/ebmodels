package ph.easybus.ebmodels.models;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

import ph.easybus.ebmodels.utils.DateTimeUtils;

public class ThirdParty implements Parcelable {

    private int trip;
    private String name, reference, origin, destination,
            tripCode, reservationPath;
    private Date tripTime;

    public ThirdParty() {}

    public ThirdParty(Parcel parcel) {
        int[] ints = new int[1];
        parcel.readIntArray(ints);
        trip = ints[0];

        String[] data = new String[6];
        parcel.readStringArray(data);
        name = data[0];
        reference = data[1];
        origin = data[2];
        destination = data[3];
        tripCode = data[4];
        reservationPath = data[5];

        long[] longs = new long[1];
        parcel.readLongArray(longs);
        if (longs[0] > 0) { tripTime = new Date(longs[0]); }
    }

    public ThirdParty(JSONObject object) {
        try {
            if (object.has("trip")) trip = object.getInt("trip");

            if (object.has("name")) name = object.getString("name");
            if (object.has("reference")) reference = object.getString("reference");
            if (object.has("origin")) origin = object.getString("origin");
            if (object.has("destination")) destination = object.getString("destination");
            if (object.has("trip_code")) tripCode = object.getString("trip_code");
            if (object.has("reservation_path")) reservationPath = object.getString("reservation_path");

            if (object.has("trip_time")) {
                if (!object.isNull("trip_time")) {
                    tripTime = DateTimeUtils.toDateUtc(object.getString("trip_time"));
                }
            }
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
            object.put("trip", trip);

            if (tripTime != null) object.put("trip_time", DateTimeUtils.toISODateUtc(tripTime));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }

    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeIntArray(new int[] { trip });
        parcel.writeStringArray(new String[] {
            name, reference, origin, destination,
            tripCode, reservationPath });
        parcel.writeLongArray(new long[] {
                (tripTime != null) ? tripTime.getTime() : -1 });
    }

    public int describeContents() {
        return 0;
    }

    public int getTrip() { return trip; }
    public String getName() { return name; }
    public String getReference() { return reference; }
    public String getOrigin() { return origin; }
    public String getDestination() { return destination; }
    public String getTripCode() { return tripCode; }
    public String getReservationPath() { return reservationPath; }
    public Date getTripTime() { return tripTime; }
    public void setTrip(int trip) { this.trip = trip; }

    public void setName(String name) { this.name = name; }
    public void setReference(String reference) { this.reference = reference; }
    public void setOrigin(String origin) { this.origin = origin; }
    public void setDestination(String destination) { this.destination = destination; }
    public void setTripCode(String tripCode) { this.tripCode = tripCode; }
    public void setReservationPath(String reservationPath) { this.reservationPath = reservationPath; }
    public void setTripTime(Date tripTime) { this.tripTime = tripTime; }

    public static final Creator<ThirdParty> CREATOR = new Creator<ThirdParty>() {
        public ThirdParty createFromParcel(Parcel parcel) { return new ThirdParty(parcel); }
        public ThirdParty[] newArray(int position) { return new ThirdParty[position]; }
    };
}
