package ph.easybus.ebmodels.models;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.RequiresApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CargoFareMatrix implements Parcelable {

    private String mongoId = "", name = "", origin = "", linerName = "";
    private ArrayList<Double> regularRates = new ArrayList<>();
    private ArrayList<String> destinations = new ArrayList<>();
    private ArrayList<CargoFixedRate> fixedRates = new ArrayList<>();

    public CargoFareMatrix() {}

    @RequiresApi(api = Build.VERSION_CODES.Q)
    public CargoFareMatrix(Parcel parcel) {
        String[] strings = new String[4];
        parcel.readStringArray(strings);
        mongoId = strings[0];
        name = strings[1];
        origin = strings[2];
        linerName = strings[3];

        int size = parcel.readInt();
        double[] regRates = new double[size];
        regularRates = new ArrayList<>();
        parcel.readDoubleArray(regRates);
        for (int i = 0; i < regRates.length; i++) {
            regularRates.add(regRates[i]);
        }

        destinations = new ArrayList<>();
        parcel.readStringList(destinations);

        fixedRates = new ArrayList<>();
        parcel.readParcelableList(fixedRates, CargoFixedRate.class.getClassLoader());
    }

    public CargoFareMatrix(JSONObject object) {
        try {
            if (object.has("_id")) mongoId = object.getString("_id");
            if (object.has("name")) name = object.getString("name");
            if (object.has("origin")) origin = object.getString("origin");
            if (object.has("liner_name")) linerName = object.getString("liner_name");

            if (object.has("regular_rates")) {
                JSONArray regRates = object.getJSONArray("regular_rates");
                regularRates = new ArrayList<>();
                for (int i = 0; i < regRates.length(); i++) {
                    regularRates.add(regRates.getDouble(i));
                }
            }

            if (object.has("destinations")) {
                JSONArray dests = object.getJSONArray("destinations");
                destinations = new ArrayList<>();
                for (int i = 0; i < dests.length(); i++) {
                    destinations.add(dests.getString(i));
                }
            }

            if (object.has("fixed_rates")) {
                JSONArray fixRates = object.getJSONArray("fixed_rates");
                fixedRates = new ArrayList<>();
                for (int i = 0; i < fixRates.length(); i++) {
                    fixedRates.add(new CargoFixedRate(fixRates.getJSONObject(i)));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeStringArray(new String[] { mongoId, name, origin, linerName });

        double[] regRates = new double[regularRates.size()];
        for (int i = 0; i < regularRates.size(); i++) {
            regRates[i] = regularRates.get(i);
        }
        parcel.writeInt(regularRates.size());
        parcel.writeDoubleArray(regRates);

        parcel.writeStringList(destinations);

        parcel.writeParcelableList(fixedRates, flags);
    }

    public JSONObject toJSON() {
        JSONObject object = new JSONObject();
        try {
            object.put("_id", mongoId);
            object.put("name", name);
            object.put("origin", origin);
            object.put("liner_name", linerName);

            JSONArray regRates = new JSONArray();
            for (int i = 0; i < regularRates.size(); i++) {
                regRates.put(regularRates.get(i));
            }
            object.put("regular_rates", regRates);

            JSONArray dests = new JSONArray();
            for (int i = 0; i < destinations.size(); i++) {
                dests.put(destinations.get(i));
            }
            object.put("destinations", dests);

            JSONArray fixRates = new JSONArray();
            for (int i = 0; i < fixedRates.size(); i++) {
                fixRates.put(fixedRates.get(i).toJSON());
            }
            object.put("fixed_rates", fixRates);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }

    public int describeContents() { return 0; }

    public String getMongoId() { return mongoId; }
    public String getName() { return name; }
    public String getOrigin() { return origin; }
    public String getLinerName() { return linerName; }
    public ArrayList<Double> getRegularRates() { return regularRates; }
    public ArrayList<String> getDestinations() { return destinations; }
    public ArrayList<CargoFixedRate> getFixedRates() { return fixedRates; }

    public void setMongoId(String mongoId) { this.mongoId = mongoId; }
    public void setName(String name) { this.name = name; }
    public void setOrigin(String origin) { this.origin = origin; }
    public void setLinerName(String linerName) { this.linerName = linerName; }
    public void setRegularRates(ArrayList<Double> regularRates) { this.regularRates = regularRates; }
    public void setDestinations(ArrayList<String> destinations) { this.destinations = destinations; }
    public void setFixedRates(ArrayList<CargoFixedRate> fixedRates) {
        this.fixedRates = fixedRates;
    }

    public static Parcelable.Creator<CargoFareMatrix> CREATOR = new Parcelable.Creator<CargoFareMatrix>() {
        @RequiresApi(api = Build.VERSION_CODES.Q)
        public CargoFareMatrix createFromParcel(Parcel parcel) { return new CargoFareMatrix(parcel); }
        public CargoFareMatrix[] newArray(int position) { return new CargoFareMatrix[position]; }
    };
}
