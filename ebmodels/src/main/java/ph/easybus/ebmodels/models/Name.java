package ph.easybus.ebmodels.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;

import org.json.JSONException;
import org.json.JSONObject;

public class Name extends BaseObservable implements Parcelable {

    @Bindable
    private String first = "", last = "";

    public Name() {}

    public Name(String first, String last) {
        this.first = first;
        this.last = last;
    }

    public Name(JSONObject object) {
        try {
            if (object.has("first")) first = object.getString("first");
            if (object.has("last")) last = object.getString("last");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public Name(Parcel parcel) {
        String[] names = new String[2];
        parcel.readStringArray(names);

        first = names[0];
        last = names[1];
    }

    public JSONObject toJSON() {
        JSONObject object = new JSONObject();
        try {
            object.put("first", first);
            object.put("last", last);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }

    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeStringArray(new String[] { first, last });
    }

    public int describeContents() { return 0; }

    public String getFirst() { return first; }
    public String getLast() { return last; }
    public void setFirst(String first) {
        this.first = first;
        notifyPropertyChanged(BR.first);
    }
    public void setLast(String last) {
        this.last = last;
        notifyPropertyChanged(BR.last);
    }

    public String toString() { return first + " " + last; }

    public static final Creator<Name> CREATOR = new Creator<Name>() {
        public Name createFromParcel(Parcel parcel) { return new Name(parcel); }
        public Name[] newArray(int size) { return new Name[size]; }
    };

}
