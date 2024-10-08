package ph.easybus.ebmodels.models;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Kimuel on 11/18/2016.
 */
public class Liner implements Parcelable {

    private boolean paymayaEnabled = false, gCashEnabled = false;
    private boolean serviceFeeEnabled = false, pencilExpirationDisability = false,
            restrictOnlineAllocations = false;

    private String policies, linerName, rebookingFee = "", pencilExpiration = "";

    private Fees fees = new Fees();

    private TripDisplaySetting tripDisplay = new TripDisplaySetting();

    private ArrayList<String> subLiners = new ArrayList<>();
    private ArrayList<Office> offices = new ArrayList<>();

    public Liner() {}

    public Liner(String linerName, String policies) {
        this.policies = policies;
        this.linerName = linerName;
    }

    public Liner(Parcel parcel) {
        boolean[] booleans = new boolean[5];
        parcel.readBooleanArray(booleans);
        serviceFeeEnabled = booleans[0];
        paymayaEnabled = booleans[1];
        gCashEnabled = booleans[2];
        pencilExpirationDisability = booleans[3];
        restrictOnlineAllocations = booleans[4];

        String[] data = new String[4];
        parcel.readStringArray(data);
        policies = data[0];
        linerName = data[1];
        rebookingFee = data[2];
        pencilExpiration = data[3];

        fees = parcel.readParcelable(Fees.class.getClassLoader());

        tripDisplay = parcel.readParcelable(TripDisplaySetting.class.getClassLoader());

        parcel.readStringList(subLiners);

        ArrayList<Office> officeList = new ArrayList<>();
        parcel.readTypedList(officeList, Office.CREATOR);

        offices = officeList;
    }

    public Liner(JSONObject object) {
        try {
            if (object.has("name")) linerName = object.getString("name");
            if (object.has("policies")) policies = object.getString("policies");

            if (object.has("bookr_settings")) {
                JSONObject settings = object.getJSONObject("bookr_settings");
                if (settings.has("service_fee")) serviceFeeEnabled = settings.getBoolean("service_fee");
                if (settings.has("payments")) {
                    JSONObject payments = settings.getJSONObject("payments");
                    if (payments.has("paymaya")) paymayaEnabled = payments.getBoolean("paymaya");
                    if (payments.has("gcash")) gCashEnabled = payments.getBoolean("gcash");
                }
            }

            if (object.has("fees")) {
                fees = new Fees(object.getJSONObject("fees"));
            }

            if (object.has("trip_display")) {
                tripDisplay = new TripDisplaySetting(object.getJSONObject("trip_display"));
            }

            if (object.has("rebooking_fee")) rebookingFee = object.getString("rebooking_fee");

            if (object.has("pencil_expiration")) pencilExpiration = object.getString("pencil_expiration");

            if (object.has("pencil_expiration_disability"))
                pencilExpirationDisability = object.getBoolean("pencil_expiration_disability");

            if (object.has("restrict_online_allocations"))
                restrictOnlineAllocations = object.getBoolean("restrict_online_allocations");

            if (object.has("offices")) {
                JSONArray officeJSONArray = object.getJSONArray("offices");

                ArrayList<Office> officeList = new ArrayList<>();
                for (int i = 0; i < officeJSONArray.length(); i++) {
                    officeList.add(new Office(officeJSONArray.getJSONObject(i)));
                }
                offices = officeList;
            }

            if (object.has("sub_liners")) {
                JSONArray subLinersArray = object.getJSONArray("sub_liners");

                subLiners = new ArrayList<>();
                for (int i = 0; i < subLinersArray.length(); i++) {
                    subLiners.add(subLinersArray.getString(i));
                }
            }
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
    }

    public JSONObject toJSON() {
        JSONObject object = new JSONObject();
        try {
            object.put("name", linerName);
            object.put("policies", policies);

            JSONObject settings = new JSONObject();
            settings.put("service_fee", serviceFeeEnabled);

            JSONObject payments = new JSONObject();
            payments.put("paymaya", paymayaEnabled);
            payments.put("gcash", gCashEnabled);
            settings.put("payments", payments);

            object.put("booker_settings", settings);

            object.put("rebooking_fee", rebookingFee);

            object.put("pencil_expiration_disability", pencilExpirationDisability);
            object.put("pencil_expiration", pencilExpiration);

            object.put("restrict_online_allocations", restrictOnlineAllocations);

            object.put("fees", fees.toJSON());

            object.put("trip_display", tripDisplay.toJSON());

            JSONArray subLinersArray = new JSONArray();
            for (int i = 0; i < subLiners.size(); i++) {
                subLinersArray.put(subLiners.get(i));
            }
            object.put("sub_liners", subLinersArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeBooleanArray(new boolean[] { serviceFeeEnabled, paymayaEnabled,
                gCashEnabled, pencilExpirationDisability, restrictOnlineAllocations });
        parcel.writeStringArray(new String[] { policies, linerName, rebookingFee, pencilExpiration });

        parcel.writeParcelable(fees, flags);

        parcel.writeParcelable(tripDisplay, flags);

        parcel.writeStringList(subLiners);

        parcel.writeTypedList(offices);
    }

    public boolean isServiceFeeEnabled() { return serviceFeeEnabled; }
    public boolean isPaymayaEnabled() { return paymayaEnabled; }
    public boolean isGCashEnabled() { return gCashEnabled; }
    public boolean isPencilExpirationDisability() { return pencilExpirationDisability; }
    public boolean isRestrictOnlineAllocations() { return restrictOnlineAllocations; }
    public String getPolicies() { return policies; }
    public String getLinerName() { return linerName; }
    public String getRebookingFee() { return rebookingFee; }
    public String getPencilExpiration() { return pencilExpiration; }
    public Fees getFees() { return fees; }
    public TripDisplaySetting getTripDisplay() { return tripDisplay; }
    public ArrayList<String> getSubLiners() { return subLiners; }
    public ArrayList<Office> getOffices() { return offices; }

    public void setServiceFeeEnabled(boolean serviceFeeEnabled) { this.serviceFeeEnabled = serviceFeeEnabled; }
    public void setPaymayaEnabled(boolean paymayaEnabled){ this.paymayaEnabled = paymayaEnabled; }
    public void setGCashEnabled(boolean gCashEnabled) { this.gCashEnabled = gCashEnabled; }
    public void setPencilExpirationDisability(boolean pencilExpirationDisability) {
        this.pencilExpirationDisability = pencilExpirationDisability;
    }
    public void setRestrictOnlineAllocations(boolean restrictOnlineAllocations) {
        this.restrictOnlineAllocations = restrictOnlineAllocations;
    }
    public void setFees(Fees fees) { this.fees = fees; }

    public void setTripDisplay(TripDisplaySetting tripDisplay) {
        this.tripDisplay = tripDisplay;
    }

    public void setSubLiners(ArrayList<String> subLiners) {
        this.subLiners = subLiners;
    }
    public void setOffices(ArrayList<Office> offices) { this.offices = offices; }
    public void setPolicies(String policies) { this.policies = policies; }
    public void setLinerName(String linerName) { this.linerName = linerName; }

    public void setRebookingFee(String rebookingFee) { this.rebookingFee = rebookingFee; }
    public void setPencilExpiration(String pencilExpiration) { this.pencilExpiration = pencilExpiration; }

    public static final Creator<Liner> CREATOR = new Creator<Liner>() {
        public Liner createFromParcel(Parcel parcel) {
            return new Liner(parcel);
        }

        public Liner[] newArray(int i) {
            return new Liner[i];
        }
    };
}
