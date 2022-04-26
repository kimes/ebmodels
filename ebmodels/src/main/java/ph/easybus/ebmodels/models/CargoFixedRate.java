package ph.easybus.ebmodels.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.databinding.BaseObservable;

import org.json.JSONException;
import org.json.JSONObject;

public class CargoFixedRate extends BaseObservable implements Parcelable {

    private double rate;
    private String item;

    public CargoFixedRate() {}

    public CargoFixedRate(double rate, String item) {
        this.rate = rate;
        this.item = item;
    }

    public CargoFixedRate(JSONObject object) {
        try {
            if (object.has("rate")) rate = object.getDouble("rate");
            if (object.has("item")) item = object.getString("item");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public CargoFixedRate(Parcel parcel) {
        rate = parcel.readDouble();
        item = parcel.readString();
    }

    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeDouble(rate);
        parcel.writeString(item);
    }

    public JSONObject toJSON() {
        JSONObject object = new JSONObject();
        try {
            object.put("rate", rate);
            object.put("item", item);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }

    public int describeContents() { return 0; }

    public double getRate() { return rate; }
    public String getItem() { return item; }
    public void setRate(double rate) { this.rate = rate; }
    public void setItem(String item) { this.item = item; }

    public static Parcelable.Creator<CargoFixedRate> CREATOR = new Parcelable.Creator<CargoFixedRate>() {
        public CargoFixedRate createFromParcel(Parcel parcel) { return new CargoFixedRate(parcel); }
        public CargoFixedRate[] newArray(int position) { return new CargoFixedRate[position]; }
    };
}
