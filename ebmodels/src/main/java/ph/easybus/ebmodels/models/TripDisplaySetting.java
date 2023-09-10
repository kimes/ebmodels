package ph.easybus.ebmodels.models;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class TripDisplaySetting implements Parcelable {

    private String header = "", footer = "", background = "";

    public TripDisplaySetting() {}

    public TripDisplaySetting(Parcel parcel) {
        String[] strings = new String[3];
        parcel.readStringArray(strings);
        header = strings[0];
        footer = strings[1];
        background = strings[2];
    }

    public TripDisplaySetting(JSONObject object) {
        try {
            if (object.has("header")) header = object.getString("header");
            if (object.has("footer")) footer = object.getString("footer");
            if (object.has("background")) background = object.getString("background");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeStringArray(new String[] { header, footer, background });
    }

    public JSONObject toJSON() {
        JSONObject object = new JSONObject();

        try {
            object.put("header", header);
            object.put("footer", footer);
            object.put("background", background);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }

    public int describeContents() { return 0; }

    public String getHeader() { return header; }
    public String getFooter() { return footer; }
    public String getBackground() { return background; }

    public void setHeader(String header) {
        this.header = header;
    }

    public void setFooter(String footer) {
        this.footer = footer;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public static final Parcelable.Creator<TripDisplaySetting> CREATOR = new Parcelable.Creator<TripDisplaySetting>() {
        public TripDisplaySetting createFromParcel(Parcel parcel) { return new TripDisplaySetting(parcel); }

        public TripDisplaySetting[] newArray(int size) {
            return new TripDisplaySetting[size];
        }
    };
}
