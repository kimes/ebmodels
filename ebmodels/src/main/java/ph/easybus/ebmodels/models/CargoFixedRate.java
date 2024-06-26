package ph.easybus.ebmodels.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.databinding.BaseObservable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CargoFixedRate extends BaseObservable implements Parcelable {

    private double weight = 0, maxDeclaredValue = 0;
    private String item;
    private ArrayList<Double> rates = new ArrayList<>();

    public CargoFixedRate() {}

    public CargoFixedRate(String item) {
        this.item = item;
    }

    public CargoFixedRate(JSONObject object) {
        try {
            if (object.has("item")) item = object.getString("item");
            if (object.has("weight")) weight = object.getDouble("weight");
            if (object.has("max_declared_value")) maxDeclaredValue = object.getDouble("max_declared_value");

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

        //weight = parcel.readDouble();
        double[] doubles = new double[2];
        parcel.readDoubleArray(doubles);
        weight = doubles[0];
        maxDeclaredValue = doubles[1];

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

        //parcel.writeDouble(weight);
        parcel.writeDoubleArray(new double[] { weight, maxDeclaredValue });

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
            object.put("weight", weight);
            object.put("max_declared_value", maxDeclaredValue);

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

    public double getWeight() { return weight; }
    public double getMaxDeclaredValue() { return maxDeclaredValue; }
    public String getItem() { return item; }
    public ArrayList<Double> getRates() { return rates; }

    public void setItem(String item) { this.item = item; }
    public void setWeight(double weight) { this.weight = weight; }
    public void setMaxDeclaredValue(double maxDeclaredValue) { this.maxDeclaredValue = maxDeclaredValue; }
    public void setRates(ArrayList<Double> rates) { this.rates = rates; }

    public static Parcelable.Creator<CargoFixedRate> CREATOR = new Parcelable.Creator<CargoFixedRate>() {
        public CargoFixedRate createFromParcel(Parcel parcel) { return new CargoFixedRate(parcel); }
        public CargoFixedRate[] newArray(int position) { return new CargoFixedRate[position]; }
    };
}
