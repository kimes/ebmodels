package ph.easybus.ebmodels.models;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Kimuel on 11/18/2016.
 */
public class Office implements Parcelable {

    private String label, address, phoneNo, mapUrl;

    public Office() { }

    public Office(String label) {
        this.label = label;
    }

    public Office(String label, String address, String phoneNo, String mapUrl) {
        this.label = label;
        this.address = address;
        this.phoneNo = phoneNo;
        this.mapUrl = mapUrl;
    }

    public Office(Parcel parcel) {
        String[] data = new String[4];
        parcel.readStringArray(data);

        label = data[0];
        address = data[1];
        phoneNo = data[2];
        mapUrl = data[3];
    }

    public Office(JSONObject object) {
        try {
            if (object.has("label")) label = object.getString("label");
            if (object.has("address")) address = object.getString("address");
            if (object.has("phone")) phoneNo = object.getString("phone");
            if (object.has("map")) mapUrl = object.getString("map");
        } catch(JSONException ex) {
            ex.printStackTrace();
        }
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeStringArray(new String[] { label, address, phoneNo, mapUrl });
    }

    @Deprecated
    public static Office getOffice(JSONObject officeObject) {
        Office nOffice = new Office();
        try {
            nOffice.setLabel(officeObject.getString("label"));
            nOffice.setAddress(officeObject.getString("address"));
            nOffice.setPhoneNumber(officeObject.getString("phone"));
            nOffice.setMapUrl(officeObject.getString("map"));
        } catch(JSONException ex) {
            ex.printStackTrace();
        }
        return nOffice;
    }

    public String getLabel() { return label; }
    public String getAddress() { return address; }
    public String getPhoneNumber() { return phoneNo; }
    public String getMapUrl() { return mapUrl; }
    public void setLabel(String label) { this.label = label; }
    public void setAddress(String address) { this.address = address; }
    public void setPhoneNumber(String phoneNo) { this.phoneNo = phoneNo; }
    public void setMapUrl(String mapUrl) { this.mapUrl = mapUrl; }

    public static final Creator<Office> CREATOR = new Creator<Office>() {
        public Office createFromParcel(Parcel parcel) {
            return new Office(parcel);
        }

        public Office[] newArray(int i) {
            return new Office[i];
        }
    };
}
