package ph.easybus.ebmodels.models;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Kimuel on 11/7/2016.
 */
public class Route implements Parcelable {

    private int id, type;
    private double fareDeduct, kmp, timeAdjustment = 0;
    private String name;

    public Route() {}

    public Route(Parcel parcel) {
        int[] ints = new int[2];
        parcel.readIntArray(ints);
        id = ints[0];
        type = ints[1];

        double[] doubles = new double[3];
        parcel.readDoubleArray(doubles);
        fareDeduct = doubles[0];
        kmp = doubles[1];
        timeAdjustment = doubles[2];

        name = parcel.readString();
    }

    public Route(String name, double fareDeduct, int type) {
        this.name = name;
        this.fareDeduct = fareDeduct;
        this.type = type;
    }

    public Route(JSONObject object) {
        try {
            if (object.has("type")) type = object.getInt("type");
            if (object.has("kmp")) kmp = object.getDouble("kmp");
            if (object.has("time_adjustment")) timeAdjustment = object.getDouble("time_adjustment");
            if (object.has("fare_deduct")) fareDeduct = object.getDouble("fare_deduct");
            if (object.has("name")) name = object.getString("name");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public int describeContents() { return 0; }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeIntArray(new int[] { id, type });
        parcel.writeDoubleArray(new double[] { fareDeduct, kmp, timeAdjustment });
        parcel.writeString(name);
    }

    public int getId() { return id; }
    public int getType() { return type; }
    public double getFareDeduct() { return fareDeduct; }
    public double getKmp() { return kmp; }
    public double getTimeAdjustment() { return timeAdjustment; }
    public String getName() { return name; }
    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setType(int type) { this.type = type; }
    public void setFareDeduct(double fareDeduct) { this.fareDeduct = fareDeduct; }
    public void setKmp(double kmp) { this.kmp = kmp; }
    public void setTimeAdjustment(double timeAdjustment) { this.timeAdjustment = timeAdjustment; }
    public String toString() { return name; }

    public static final Creator<Route> CREATOR = new Creator<Route>() {
        public Route createFromParcel(Parcel parcel) { return new Route(parcel); }
        public Route[] newArray(int position) { return new Route[position]; }
    };

}
