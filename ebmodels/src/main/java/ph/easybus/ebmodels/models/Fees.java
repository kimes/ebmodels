package ph.easybus.ebmodels.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import org.json.JSONException;
import org.json.JSONObject;

public class Fees extends BaseObservable implements Parcelable {

    @Bindable
    private double choice, upperFare, insurance, liner;

    public Fees() {}

    public Fees(Parcel parcel) {
        double[] doubles = new double[4];
        parcel.readDoubleArray(doubles);

        choice = doubles[0];
        upperFare = doubles[1];
        insurance = doubles[2];
        liner = doubles[3];
    }

    public Fees(JSONObject object) {
        try {
            if (object.has("choice")) choice = object.getDouble("choice");
            if (object.has("upper_fare")) upperFare = object.getDouble("upper_fare");
            if (object.has("insurance")) insurance = object.getDouble("insurance");
            if (object.has("liner")) liner = object.getDouble("liner");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeDoubleArray(new double[] { choice, upperFare, insurance, liner });
    }

    public JSONObject toJSON() {
        JSONObject object = new JSONObject();
        try {
            object.put("choice", choice);
            object.put("upper_fare", upperFare);
            object.put("insurance", insurance);
            object.put("liner", liner);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }

    public int describeContents() { return 0; }

    public double getChoice() { return choice; }
    public double getUpperFare() { return upperFare; }
    public double getInsurance() { return insurance; }
    public double getLiner() { return liner; }
    public void setChoice(double choice) { this.choice = choice; }
    public void setUpperFare(double upperFare) { this.upperFare = upperFare; }
    public void setInsurance(double insurance) { this.insurance = insurance; }
    public void setLiner(double liner) { this.liner = liner; }

    public double getTotalFees() {
        return choice + upperFare + insurance + liner;
    }

    public static final Creator<Fees> CREATOR = new Creator<Fees>() {
        public Fees createFromParcel(Parcel parcel) { return new Fees(parcel); }
        public Fees[] newArray(int position) { return new Fees[position]; }
    };
}
