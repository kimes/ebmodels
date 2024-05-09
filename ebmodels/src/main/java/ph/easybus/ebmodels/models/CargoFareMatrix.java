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
        portersFee = "", systemFee = "", additionalFee = "",
        baseAmountRegular = "", baseAmountFixed = "",
        totalAmountRegular = "", totalAmountFixed = "",
        totalAmountCheckin = "", insuranceFee = "", baseAmountCheckin = "", declaredValueFee = "",
        baseAmountSpecial = "", totalAmountSpecial = "", maxDeclaredValue = "";

    private ArrayList<Double> regularRates = new ArrayList<>(), regularRatesCheckin = new ArrayList<>(),
        regularMinRates = new ArrayList<>(), regularAdditionalRates = new ArrayList<>();

    private ArrayList<String> destinations = new ArrayList<>(), destinationsCheckin = new ArrayList<>();

    private ArrayList<CargoFixedRate> fixedRates = new ArrayList<>(), fixedRatesCheckin = new ArrayList<>();

    public CargoFareMatrix() {}

    @RequiresApi(api = Build.VERSION_CODES.Q)
    public CargoFareMatrix(Parcel parcel) {
        double[] doubles = new double[2];
        parcel.readDoubleArray(doubles);
        minWeight = doubles[0];
        declaredValueFactor = doubles[1];

        String[] strings = new String[18];
        parcel.readStringArray(strings);
        mongoId = strings[0];
        name = strings[1];
        origin = strings[2];
        linerName = strings[3];
        portersFee = strings[4];
        systemFee = strings[5];
        baseAmountRegular = strings[6];
        baseAmountFixed = strings[7];
        totalAmountRegular = strings[8];
        totalAmountFixed = strings[9];
        additionalFee = strings[10];
        totalAmountCheckin = strings[11];
        insuranceFee = strings[12];
        baseAmountCheckin = strings[13];
        declaredValueFee = strings[14];
        baseAmountSpecial = strings[15];
        totalAmountSpecial = strings[16];
        maxDeclaredValue = strings[17];

        int size = parcel.readInt();
        double[] regRates = new double[size];
        regularRates = new ArrayList<>();
        parcel.readDoubleArray(regRates);
        for (int i = 0; i < regRates.length; i++) {
            regularRates.add(regRates[i]);
        }

        size = parcel.readInt();
        regRates = new double[size];
        regularMinRates = new ArrayList<>();
        for (int i = 0; i < regRates.length; i++) {
            regularMinRates.add(regRates[i]);
        }

        size = parcel.readInt();
        regRates = new double[size];
        regularAdditionalRates = new ArrayList<>();
        for (int i = 0; i < regRates.length; i++) {
            regularAdditionalRates.add(regRates[i]);
        }

        size = parcel.readInt();
        regRates = new double[size];
        regularRatesCheckin = new ArrayList<>();
        parcel.readDoubleArray(regRates);
        for (int i = 0; i < regRates.length; i++) {
            regularRatesCheckin.add(regRates[i]);
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
            if (object.has("additional_fee"))
                additionalFee = object.getString("additional_fee");
            if (object.has("insurance_fee"))
                insuranceFee = object.getString("insurance_fee");
            if (object.has("declared_value_fee"))
                declaredValueFee = object.getString("declared_value_fee");
            if (object.has("max_declared_value"))
                maxDeclaredValue = object.getString("max_declared_value");

            if (object.has("base_amount_regular"))
                baseAmountRegular = object.getString("base_amount_regular");
            if (object.has("base_amount_fixed"))
                baseAmountFixed = object.getString("base_amount_fixed");

            if (object.has("total_amount_regular"))
                totalAmountRegular = object.getString("total_amount_regular");
            if (object.has("total_amount_fixed"))
                totalAmountFixed = object.getString("total_amount_fixed");

            if (object.has("base_amount_special"))
                baseAmountSpecial = object.getString("base_amount_special");
            if (object.has("total_amount_special"))
                totalAmountSpecial = object.getString("total_amount_special");

            if (object.has("base_amount_checkin"))
                baseAmountCheckin = object.getString("base_amount_checkin");
            if (object.has("total_amount_checkin"))
                totalAmountCheckin = object.getString("total_amount_checkin");

            if (object.has("regular_rates")) {
                JSONArray regRates = object.getJSONArray("regular_rates");
                regularRates = new ArrayList<>();
                for (int i = 0; i < regRates.length(); i++) {
                    regularRates.add(regRates.getDouble(i));
                }
            }

            if (object.has("regular_min_rates")) {
                JSONArray regRates = object.getJSONArray("regular_min_rates");
                regularMinRates = new ArrayList<>();
                for (int i = 0; i < regRates.length(); i++) {
                    regularMinRates.add(regRates.getDouble(i));
                }
            }

            if (object.has("regular_additional_rates")) {
                JSONArray regRates = object.getJSONArray("regular_additional_rates");
                regularAdditionalRates = new ArrayList<>();
                for (int i = 0; i < regRates.length(); i++) {
                    regularAdditionalRates.add(regRates.getDouble(i));
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

            if (object.has("regular_rates_checkin")) {
                JSONArray rates = object.getJSONArray("regular_rates_checkin");
                regularRatesCheckin = new ArrayList<>();
                for (int i = 0; i < rates.length(); i++) {
                    regularRatesCheckin.add(rates.getDouble(i));
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
            totalAmountRegular, totalAmountFixed, additionalFee, totalAmountCheckin,
            insuranceFee, baseAmountCheckin, declaredValueFee, baseAmountSpecial,
            totalAmountSpecial, maxDeclaredValue });

        double[] regRates = new double[regularRates.size()];
        for (int i = 0; i < regularRates.size(); i++) {
            regRates[i] = regularRates.get(i);
        }
        parcel.writeInt(regularRates.size());
        parcel.writeDoubleArray(regRates);

        regRates = new double[regularMinRates.size()];
        for (int i = 0; i < regularMinRates.size(); i++) {
            regRates[i] = regularMinRates.get(i);
        }
        parcel.writeInt(regularMinRates.size());
        parcel.writeDoubleArray(regRates);

        regRates = new double[regularAdditionalRates.size()];
        for (int i = 0; i < regularAdditionalRates.size(); i++) {
            regRates[i] = regularAdditionalRates.get(i);
        }
        parcel.writeInt(regularAdditionalRates.size());
        parcel.writeDoubleArray(regRates);

        regRates = new double[regularRatesCheckin.size()];
        for (int i = 0; i < regularRatesCheckin.size(); i++) {
            regRates[i] = regularRatesCheckin.get(i);
        }
        parcel.writeInt(regularRatesCheckin.size());
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
            object.put("additional_fee", additionalFee);
            object.put("insurance_fee", insuranceFee);
            object.put("declared_value_fee", declaredValueFee);
            object.put("max_declared_value", maxDeclaredValue);

            object.put("base_amount_regular", baseAmountRegular);
            object.put("total_amount_regular", totalAmountRegular);

            object.put("base_amount_fixed", baseAmountFixed);
            object.put("total_amount_fixed", totalAmountFixed);

            object.put("base_amount_special", baseAmountSpecial);
            object.put("total_amount_special", totalAmountSpecial);

            object.put("base_amount_checkin", baseAmountCheckin);
            object.put("total_amount_checkin", totalAmountCheckin);

            JSONArray regRates = new JSONArray();
            for (int i = 0; i < regularRates.size(); i++) {
                regRates.put(regularRates.get(i));
            }
            object.put("regular_rates", regRates);

            regRates = new JSONArray();
            for (int i = 0; i < regularMinRates.size(); i++) {
                regRates.put(regularMinRates.get(i));
            }
            object.put("regular_min_rates", regRates);

            regRates = new JSONArray();
            for (int i = 0; i < regularAdditionalRates.size(); i++) {
                regRates.put(regularAdditionalRates.get(i));
            }
            object.put("regular_additional_rates", regRates);

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
    public String getAdditionalFee() { return additionalFee; }
    public String getInsuranceFee() { return insuranceFee; }
    public String getDeclaredValueFee() { return declaredValueFee; }
    public String getMaxDeclaredValue() { return maxDeclaredValue; }
    public String getBaseAmountRegular() { return baseAmountRegular; }
    public String getTotalAmountRegular() { return totalAmountRegular; }
    public String getBaseAmountFixed() { return baseAmountFixed; }
    public String getTotalAmountFixed() { return totalAmountFixed; }
    public String getBaseAmountSpecial() { return baseAmountSpecial; }
    public String getTotalAmountSpecial() { return totalAmountSpecial; }
    public String getBaseAmountCheckin() { return baseAmountCheckin; }
    public String getTotalAmountCheckin() { return totalAmountCheckin; }
    public ArrayList<Double> getRegularRates() { return regularRates; }
    public ArrayList<Double> getRegularMinRates() { return regularMinRates; }
    public ArrayList<Double> getRegularAdditionalRates() { return regularAdditionalRates; }
    public ArrayList<Double> getRegularRatesCheckin() { return regularRatesCheckin; }
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
    public void setAdditionalFee(String additionalFee) { this.additionalFee = additionalFee; }
    public void setInsuranceFee(String insuranceFee) { this.insuranceFee = insuranceFee; }
    public void setDeclaredValueFee(String declaredValueFee) { this.declaredValueFee = declaredValueFee; }
    public void setMaxDeclaredValue(String maxDeclaredValue) { this.maxDeclaredValue = maxDeclaredValue; }
    public void setBaseAmountRegular(String baseAmountRegular) { this.baseAmountRegular = baseAmountRegular; }
    public void setTotalAmountRegular(String totalAmountRegular) { this.totalAmountRegular = totalAmountRegular; }
    public void setBaseAmountFixed(String baseAmountFixed) { this.baseAmountFixed = baseAmountFixed; }
    public void setTotalAmountFixed(String totalAmountFixed) { this.totalAmountFixed = totalAmountFixed; }
    public void setBaseAmountSpecial(String baseAmountSpecial) { this.baseAmountSpecial = baseAmountSpecial; }
    public void setTotalAmountSpecial(String totalAmountSpecial) { this.totalAmountSpecial = totalAmountSpecial; }
    public void setBaseAmountCheckin(String baseAmountCheckin) { this.baseAmountCheckin = baseAmountCheckin; }
    public void setTotalAmountCheckin(String totalAmountCheckin) { this.totalAmountCheckin = totalAmountCheckin; }
    public void setRegularRates(ArrayList<Double> regularRates) { this.regularRates = regularRates; }
    public void setRegularMinRates(ArrayList<Double> regularMinRates) { this.regularMinRates = regularMinRates; }
    public void setRegularAdditionalRates(ArrayList<Double> regularAdditionalRates) { this.regularAdditionalRates = regularAdditionalRates; }
    public void setRegularRatesCheckin(ArrayList<Double> regularRatesCheckin) { this.regularRatesCheckin = regularRatesCheckin; }
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
