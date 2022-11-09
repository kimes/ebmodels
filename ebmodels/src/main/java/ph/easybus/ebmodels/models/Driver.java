package ph.easybus.ebmodels.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.databinding.BaseObservable;

import org.json.JSONException;
import org.json.JSONObject;

public class Driver extends BaseObservable implements Parcelable {

    private String mongoId = "", name = "";

    public Driver() {}
    public Driver(JSONObject object) {
        try {
            if (object.has("_id")) mongoId = object.getString("_id");
            if (object.has("name")) name = object.getString("name");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public Driver(Parcel parcel) {
        String[] strings = new String[2];
        parcel.readStringArray(strings);
        mongoId = strings[0];
        name = strings[1];
    }

    public JSONObject toJSON() {
        JSONObject object = new JSONObject();
        try {
            object.put("_id", mongoId);
            object.put("name", name);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }

    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeStringArray(new String[] { mongoId, name });
    }

    public int describeContents() { return 0; }

    public String getMongoId() { return mongoId; }
    public String getName() { return name; }
    public void setMongoId(String mongoId) { this.mongoId = mongoId; }
    public void setName(String name) { this.name = name; }

    public static final Creator<Driver> CREATOR = new Creator<Driver>() {
        public Driver createFromParcel(Parcel parcel) { return new Driver(parcel); }
        public Driver[] newArray(int size) { return new Driver[size]; }
    };
}
