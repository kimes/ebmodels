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

    private double minWeight = 0, declaredValueFactor = 0;
    private String mongoId = "", name = "", origin = "", linerName = "",
        portersFee = "", systemFee = "",
        baseAmountRegular = "", baseAmountFixed = "",
        totalAmountRegular = "", totalAmountFixed = "";
    private ArrayList<Double> regularRates = new ArrayList<>();
    private ArrayList<String> destinations = new ArrayList<>(), destinationsCheckin = new ArrayList<>();
    private ArrayList<CargoFixedRate> fixedRates = new ArrayList<>(), fixedRatesCheckin = new ArrayList<>();

    public CargoFareMatrix() {}

    @RequiresApi(api = Build.VERSION_CODES.Q)
    public CargoFareMatrix(Parcel parcel) {
        double[] doubles = new double[2];
        parcel.readDoubleArray(doubles);
        minWeight = doubles[0];
        declaredValueFactor = doubles[1];

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

        destinationsCheckin = new ArrayList<>();
        parcel.readStringList(destinationsCheckin);

        fixedRates = new ArrayList<>();
        parcel.readParcelableList(fixedRates, CargoFixedRate.class.getClassLoader());

        fixedRatesCheckin = new ArrayList<>();
        parcel.readParcelableList(fixedRatesCheckin, CargoFixedRate.class.getClassLoader());
    }

    public CargoFareMatrix(JSONObject object) {
        try {
            if (object.has("_id")) mongoId = object.getString("_id");
            if (object.has("name")) name = object.getString("name");
            if (object.has("origin")) origin = object.getString("origin");
            if (object.has("liner_name")) linerName = object.getString("liner_name");

            if (object.has("min_weight")) minWeight = object.getDouble("min_weight");
            if (object.has("declared_value_factor")) declaredValueFactor = object.getDouble("declared_value_factor");

            if (object.has("porters_fee"))
                portersFee = object.getString("porters_fee");
            if (object.has("system_fee"))
                systemFee = object.getString("system_fee");

            if (object.has("base_amount_regular"))
                baseAmountRegular = object.getString("base_amount_regular");
            if (object.has("base_amount_fixed"))
                baseAmountFixed = object.getString("base_amount_fixed");

            if (object.has("total_amount_regular"))
                totalAmountRegular = object.getString("total_amount_regular");
            if (object.has("total_amount_fixed"))
                totalAmountFixed = object.getString("total_amount_fixed");

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

            if (object.has("destinations_checkin")) {
                JSONArray dests = object.getJSONArray("destinations_checkin");
                destinationsCheckin = new ArrayList<>();
                for (int i = 0; i < dests.length(); i++) {
                    destinationsCheckin.add(dests.getString(i));
                }
            }

            if (object.has("fixed_rates_checkin")) {
                JSONArray fixRates = object.getJSONArray("fixed_rates_checkin");
                fixedRatesCheckin = new ArrayList<>();
                for (int i = 0; i < fixRates.length(); i++) {
                    fixedRatesCheckin.add(new CargoFixedRate(fixRates.getJSONObject(i)));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeDoubleArray(new double[] { minWeight, declaredValueFactor });
        parcel.writeStringArray(new String[] { mongoId, name, origin, linerName,
            portersFee, systemFee, baseAmountRegular, baseAmountFixed,
            totalAmountRegular, totalAmountFixed });

        double[] regRates = new double[regularRates.size()];
        for (int i = 0; i < regularRates.size(); i++) {
            regRates[i] = regularRates.get(i);
        }
        parcel.writeInt(regularRates.size());
        parcel.writeDoubleArray(regRates);

        parcel.writeStringList(destinations);
        parcel.writeStringList(destinationsCheckin);

        parcel.writeParcelableList(fixedRates, flags);
        parcel.writeParcelableList(fixedRatesCheckin, flags);
    }

    public JSONObject toJSON() {
        JSONObject object = new JSONObject();
        try {
            object.put("_id", mongoId);
            object.put("name", name);
            object.put("origin", origin);
            object.put("liner_name", linerName);

            object.put("min_weight", minWeight);
            object.put("declared_value_factor", declaredValueFactor);
            object.put("porters_fee", portersFee);
            object.put("system_fee", systemFee);
            object.put("base_amount_regular", baseAmountRegular);
            object.put("base_amount_fixed", baseAmountFixed);
            object.put("total_amount_regular", totalAmountRegular);
            object.put("total_amount_fixed", totalAmountFixed);

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

            JSONArray destsCheckin = new JSONArray();
            for (int i = 0; i < destinationsCheckin.size(); i++) {
                destsCheckin.put(destinationsCheckin.get(i));
            }
            object.put("destinations_checkin", destsCheckin);

            JSONArray fixRatesCheckin = new JSONArray();
            for (int i = 0; i < fixedRatesCheckin.size(); i++) {
                fixRatesCheckin.put(fixedRatesCheckin.get(i).toJSON());
            }
            object.put("fixed_rates_checkin", fixRatesCheckin);
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

    public double getMinWeight() { return minWeight; }
    public double getDeclaredValueFactor() { return declaredValueFactor; }
    public String getPortersFee() { return portersFee; }
    public String getSystemFee() { return systemFee; }
    public String getBaseAmountRegular() { return baseAmountRegular; }
    public String getBaseAmountFixed() { return baseAmountFixed; }
    public String getTotalAmountRegular() { return totalAmountRegular; }
    public String getTotalAmountFixed() { return totalAmountFixed; }

    public ArrayList<Double> getRegularRates() { return regularRates; }
    public ArrayList<String> getDestinations() { return destinations; }
    public ArrayList<String> getDestinationsCheckin() { return destinationsCheckin; }
    public ArrayList<CargoFixedRate> getFixedRates() { return fixedRates; }
    public ArrayList<CargoFixedRate> getFixedRatesCheckin() { return fixedRatesCheckin; }

    public void setMongoId(String mongoId) { this.mongoId = mongoId; }
    public void setName(String name) { this.name = name; }
    public void setOrigin(String origin) { this.origin = origin; }
    public void setLinerName(String linerName) { this.linerName = linerName; }
    public void setMinWeight(double minWeight) { this.minWeight = minWeight; }
    public void setDeclaredValueFactor(double declaredValueFactor) { this.declaredValueFactor = declaredValueFactor; }
    public void setPortersFee(String portersFee) { this.portersFee = portersFee; }
    public void setSystemFee(String systemFee) { this.systemFee = systemFee; }
    public void setBaseAmountRegular(String baseAmountRegular) { this.baseAmountRegular = baseAmountRegular; }
    public void setBaseAmountFixed(String baseAmountFixed) { this.baseAmountFixed = baseAmountFixed; }
    public void setTotalAmountRegular(String totalAmountRegular) { this.totalAmountRegular = totalAmountRegular; }
    public void setTotalAmountFixed(String totalAmountFixed) { this.totalAmountFixed = totalAmountFixed; }
    public void setRegularRates(ArrayList<Double> regularRates) { this.regularRates = regularRates; }
    public void setDestinations(ArrayList<String> destinations) { this.destinations = destinations; }
    public void setDestinationsCheckin(ArrayList<String> destinationsCheckin) {
        this.destinationsCheckin = destinationsCheckin;
    }
    public void setFixedRates(ArrayList<CargoFixedRate> fixedRates) {
        this.fixedRates = fixedRates;
    }
    public void setFixedRatesCheckin(ArrayList<CargoFixedRate> fixedRatesCheckin) {
        this.fixedRatesCheckin = fixedRatesCheckin;
    }

    public static Parcelable.Creator<CargoFareMatrix> CREATOR = new Parcelable.Creator<CargoFareMatrix>() {
        @RequiresApi(api = Build.VERSION_CODES.Q)
        public CargoFareMatrix createFromParcel(Parcel parcel) { return new CargoFareMatrix(parcel); }
        public CargoFareMatrix[] newArray(int position) { return new CargoFareMatrix[position]; }
    };
}
