package ph.easybus.ebmodels.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;

import org.json.JSONException;
import org.json.JSONObject;

public class Discount extends BaseObservable implements Parcelable {

    @Bindable
    private int type = 0;

    @Bindable
    private double percent;

    private String idNumber;

    public Discount() {}

    public Discount(Parcel parcel) {
        type = parcel.readInt();
        percent = parcel.readDouble();
        idNumber = parcel.readString();
    }

    public Discount(JSONObject object) {
        try {
            if (object.has("type")) type = object.getInt("type");
            if (object.has("percent")) percent = object.getDouble("percent");
            if (object.has("id_number")) idNumber = object.getString("id_number");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public JSONObject toJSON() {
        JSONObject object = new JSONObject();
        try {
            object.put("type", type);
            object.put("percent", percent);
            object.put("id_number", idNumber);
        } catch(JSONException e) {
            e.printStackTrace();
        }
        return object;
    }

    @Deprecated
    public static Discount getDiscount(JSONObject object) {
        Discount discount = new Discount();
        try {
            if (object.has("type")) discount.setType(object.getInt("type"));
            if (object.has("percent")) discount.setPercent(object.getDouble("percent"));
            if (object.has("id_number")) discount.setIdNumber(object.getString("id_number"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return discount;
    }

    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeInt(type);
        parcel.writeDouble(percent);
        parcel.writeString(idNumber);
    }

    public int describeContents() { return 0; }

    public int getType() { return type; }
    public double getPercent() { return percent; }
    public String getIdNumber() { return idNumber; }
    public void setType(int type) {
        this.type = type;
        notifyPropertyChanged(BR.type);
    }
    public void setPercent(double percent) {
        this.percent = percent;
        notifyPropertyChanged(BR.percent);
    }
    public void setIdNumber(String idNumber) { this.idNumber = idNumber; }

    public static final Creator<Discount> CREATOR = new Creator<Discount>() {
        public Discount createFromParcel(Parcel parcel) { return new Discount(parcel); }
        public Discount[] newArray(int position) { return new Discount[position]; }
    };
}
