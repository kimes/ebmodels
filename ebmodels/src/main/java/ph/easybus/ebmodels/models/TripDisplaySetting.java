package ph.easybus.ebmodels.models;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class TripDisplaySetting implements Parcelable {

    private String header = "", footer = "", background = "",
            theadBackgroundColor = "", theadFontColor = "", dateFontColor = "";

    public TripDisplaySetting() {}

    public TripDisplaySetting(Parcel parcel) {
        String[] strings = new String[6];
        parcel.readStringArray(strings);
        header = strings[0];
        footer = strings[1];
        background = strings[2];
        theadBackgroundColor = strings[3];
        theadFontColor = strings[4];
        dateFontColor = strings[5];
    }

    public TripDisplaySetting(JSONObject object) {
        try {
            if (object.has("header")) header = object.getString("header");
            if (object.has("footer")) footer = object.getString("footer");
            if (object.has("background")) background = object.getString("background");

            if (object.has("thead_background_color"))
                theadBackgroundColor = object.getString("thead_background_color");

            if (object.has("thead_font_color"))
                theadFontColor = object.getString("thead_font_color");

            if (object.has("date_font_color"))
                dateFontColor = object.getString("date_font_color");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeStringArray(new String[] { header, footer, background,
                theadBackgroundColor, theadFontColor, dateFontColor });
    }

    public JSONObject toJSON() {
        JSONObject object = new JSONObject();

        try {
            object.put("header", header);
            object.put("footer", footer);
            object.put("background", background);
            object.put("thead_background_color", theadBackgroundColor);
            object.put("thead_font_color", theadFontColor);
            object.put("date_font_color", dateFontColor);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }

    public int describeContents() { return 0; }

    public String getHeader() { return header; }
    public String getFooter() { return footer; }
    public String getBackground() { return background; }
    public String getTheadBackgroundColor() { return theadBackgroundColor; }
    public String getTheadFontColor() { return theadFontColor; }
    public String getDateFontColor() { return dateFontColor; }

    public void setHeader(String header) {
        this.header = header;
    }

    public void setFooter(String footer) {
        this.footer = footer;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public void setTheadBackgroundColor(String theadBackgroundColor) {
        this.theadBackgroundColor = theadBackgroundColor;
    }

    public void setTheadFontColor(String theadFontColor) {
        this.theadFontColor = theadFontColor;
    }

    public void setDateFontColor(String dateFontColor) {
        this.dateFontColor = dateFontColor;
    }

    public static final Parcelable.Creator<TripDisplaySetting> CREATOR = new Parcelable.Creator<TripDisplaySetting>() {
        public TripDisplaySetting createFromParcel(Parcel parcel) { return new TripDisplaySetting(parcel); }

        public TripDisplaySetting[] newArray(int size) {
            return new TripDisplaySetting[size];
        }
    };
}
