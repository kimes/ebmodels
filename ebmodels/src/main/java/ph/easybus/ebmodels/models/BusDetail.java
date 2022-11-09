package ph.easybus.ebmodels.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.databinding.BaseObservable;

import org.json.JSONException;
import org.json.JSONObject;

public class BusDetail extends BaseObservable implements Parcelable {

    private String mongoId = "", bodyNumber = "", plateNumber = "", busType = "";

    public BusDetail() {}
    public BusDetail(JSONObject object) {
        try {
            if (object.has("_id")) mongoId = object.getString("_id");
            if (object.has("body_number")) bodyNumber = object.getString("body_number");
            if (object.has("plate_number")) plateNumber = object.getString("plate_number");
            if (object.has("bus_type")) busType = object.getString("bus_type");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public BusDetail(Parcel parcel) {
        String[] strings = new String[4];
        parcel.readStringArray(strings);
        mongoId = strings[0];
        bodyNumber = strings[1];
        plateNumber = strings[2];
        busType = strings[3];
    }

    public JSONObject toJSON() {
        JSONObject object = new JSONObject();
        try {
            object.put("_id", mongoId);
            object.put("body_number", bodyNumber);
            object.put("plate_number", plateNumber);
            object.put("bus_type", busType);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }

    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeStringArray(new String[] { mongoId, bodyNumber, plateNumber, busType });
    }

    public int describeContents() { return 0; }

    public String getMongoId() { return mongoId; }
    public String getBodyNumber() { return bodyNumber; }
    public String getPlateNumber() { return plateNumber; }
    public String getBusType() { return busType; }

    public void setMongoId(String mongoId) { this.mongoId = mongoId; }
    public void setBodyNumber(String bodyNumber) { this.bodyNumber = bodyNumber; }
    public void setPlateNumber(String plateNumber) { this.plateNumber = plateNumber; }
    public void setBusType(String busType) { this.busType = busType; }

    public static final Creator<BusDetail> CREATOR = new Creator<BusDetail>() {
        public BusDetail createFromParcel(Parcel source) { return new BusDetail(source); }
        public BusDetail[] newArray(int size) {
            return new BusDetail[size];
        }
    };
}
