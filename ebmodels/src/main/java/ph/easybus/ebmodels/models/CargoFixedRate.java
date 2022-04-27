package ph.easybus.ebmodels.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.databinding.BaseObservable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CargoFixedRate extends BaseObservable implements Parcelable {

    private String item;
    private ArrayList<Double> rates = new ArrayList<>();

    public CargoFixedRate() {}

    public CargoFixedRate(String item) {
        this.item = item;
    }

    public CargoFixedRate(JSONObject object) {
        try {
            if (object.has("item")) item = object.getString("item");

            if (object.has("rates")) {
                JSONArray ratesArray = object.getJSONArray("rates");
                rates = new ArrayList<>();
                for (int i = 0; i < ratesArray.length(); i++) {
                    rates.add(ratesArray.getDouble(i));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public CargoFixedRate(Parcel parcel) {
        item = parcel.readString();

        int rateCount = parcel.readInt();
        double[] ratesArray = new double[rateCount];
        parcel.readDoubleArray(ratesArray);

        rates = new ArrayList<>();
        for (int i = 0; i < ratesArray.length; i++) {
            rates.add(ratesArray[i]);
        }
    }

    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(item);

        parcel.writeInt(rates.size());
        double[] ratesArray = new double[rates.size()];
        for (int i = 0; i < rates.size(); i++) {
            ratesArray[i] = rates.get(i);
        }
        parcel.writeDoubleArray(ratesArray);
    }

    public JSONObject toJSON() {
        JSONObject object = new JSONObject();
        try {
            object.put("item", item);

            JSONArray ratesArray = new JSONArray();
            for (int i = 0; i < rates.size(); i++) {
                ratesArray.put(rates.get(i));
            }
            object.put("rates", rates);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }

    public int describeContents() { return 0; }

    public String getItem() { return item; }
    public ArrayList<Double> getRates() { return rates; }

    public void setItem(String item) { this.item = item; }
    public void setRates(ArrayList<Double> rates) { this.rates = rates; }

    public static Parcelable.Creator<CargoFixedRate> CREATOR = new Parcelable.Creator<CargoFixedRate>() {
        public CargoFixedRate createFromParcel(Parcel parcel) { return new CargoFixedRate(parcel); }
        public CargoFixedRate[] newArray(int position) { return new CargoFixedRate[position]; }
    };
}
