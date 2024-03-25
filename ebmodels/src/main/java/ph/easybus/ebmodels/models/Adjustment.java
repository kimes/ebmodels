package ph.easybus.ebmodels.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.Observable;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.library.baseAdapters.BR;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

import ph.easybus.ebmodels.utils.DateTimeUtils;

public class Adjustment extends BaseObservable implements Parcelable {

    @Bindable
    private boolean seatsLeft = false;

    @Bindable
    private int status;

    @Bindable
    private double amount = 0, fareDifference = 0;

    @Bindable
    private String related = "", rebookBy = "";

    @Bindable
    private Date rebookDate;

    @Bindable
    private ObservableArrayList<Integer> seatsNotRebook = new ObservableArrayList<>();

    @Bindable
    private ObservableArrayList<Integer> infantsNotRebook = new ObservableArrayList<>();

    public Adjustment() {}

    public Adjustment(Parcel parcel) {
        boolean[] booleans = new boolean[1];
        parcel.readBooleanArray(booleans);
        seatsLeft = booleans[0];

        int[] ints = new int[1];
        parcel.readIntArray(ints);
        status = ints[0];

        double[] doubles = new double[2];
        parcel.readDoubleArray(doubles);
        amount = doubles[0];
        fareDifference = doubles[1];

        String[] strings = new String[2];
        parcel.readStringArray(strings);
        related = strings[0];
        rebookBy = strings[1];

        long[] dates = new long[1];
        parcel.readLongArray(dates);
        if (dates[0] > 0) { rebookDate = new Date(dates[0]); }

        int size = parcel.readInt();
        int[] seatsData = new int[size];
        parcel.readIntArray(seatsData);

        seatsNotRebook = new ObservableArrayList<>();
        for (int seat: seatsData) {
            seatsNotRebook.add(seat);
        }

        int infantsSize = parcel.readInt();
        int[] infantsData = new int[infantsSize];
        parcel.readIntArray(infantsData);

        infantsNotRebook = new ObservableArrayList<>();
        for (int seat : infantsData) {
            infantsNotRebook.add(seat);
        }
    }

    public Adjustment(JSONObject object) {
        try {
            if (object.has("isSeatsLeft")) seatsLeft = object.getBoolean("isSeatsLeft");
            if (object.has("status")) status = object.getInt("status");
            if (object.has("amount")) amount = object.getDouble("amount");
            if (object.has("fare_difference")) fareDifference = object.getDouble("fare_difference");
            if (object.has("related")) related = object.getString("related");
            if (object.has("rebook_by")) rebookBy = object.getString("rebook_by");

            if (object.has("rebook_date"))
                rebookDate = DateTimeUtils.toDateUtc(object.getString("rebook_date"));

            if (object.has("seatsNotRebook")) {
                JSONArray seatsArray = object.getJSONArray("seatsNotRebook");
                seatsNotRebook = new ObservableArrayList<>();
                for (int i = 0; i < seatsArray.length(); i++) {
                    seatsNotRebook.add(seatsArray.getInt(i));
                }
            }

            if (object.has("infantsNotRebook")) {
                JSONArray infantsArray = object.getJSONArray("infantsNotRebook");
                infantsNotRebook = new ObservableArrayList<>();
                for (int i = 0; i < infantsArray.length(); i++) {
                    infantsNotRebook.add(infantsArray.getInt(i));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public JSONObject toJSON() {
        JSONObject object = new JSONObject();
        try {
            object.put("isSeatsLeft", seatsLeft);
            object.put("status", status);
            object.put("amount", amount);
            object.put("fare_difference", fareDifference);
            object.put("related", related);
            object.put("rebook_by", rebookBy);

            if (rebookDate != null) object.put("rebook_date", DateTimeUtils.toISODateUtc(rebookDate));

            JSONArray seatsArray = new JSONArray();
            for (int i = 0; i < seatsNotRebook.size(); i++) {
                seatsArray.put(seatsNotRebook.get(i));
            }
            object.put("seatsNotRebook", seatsArray);

            JSONArray infantsArray = new JSONArray();
            for (int i = 0; i < infantsNotRebook.size(); i++) {
                infantsArray.put(infantsNotRebook.get(i));
            }
            object.put("infantsNotRebook", infantsArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }

    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeBooleanArray(new boolean[] { seatsLeft });
        parcel.writeIntArray(new int[] { status });
        parcel.writeDoubleArray(new double[] { amount, fareDifference });
        parcel.writeStringArray(new String[] { related, rebookBy });

        parcel.writeLongArray(new long[] { (rebookDate != null ? rebookDate.getTime() : -1) });

        int[] parcelReservedSeats = new int[seatsNotRebook.size()];
        for (int i = 0; i < seatsNotRebook.size(); i++) {
            parcelReservedSeats[i] = seatsNotRebook.get(i);
        }
        parcel.writeInt(seatsNotRebook.size());
        parcel.writeIntArray(parcelReservedSeats);

        int[] parcelInfants = new int[infantsNotRebook.size()];
        for (int i = 0; i < infantsNotRebook.size(); i++) {
            parcelInfants[i] = infantsNotRebook.get(i);
        }
        parcel.writeInt(infantsNotRebook.size());
        parcel.writeIntArray(parcelInfants);
    }

    public int describeContents() { return 0; }

    public boolean isSeatsLeft() { return seatsLeft; }
    public int getStatus() { return status; }
    public double getAmount() { return amount; }
    public double getFareDifference() { return fareDifference; }
    public String getRelated() { return related; }
    public String getRebookBy() { return rebookBy; }
    public Date getRebookDate() { return rebookDate; }
    public ObservableArrayList<Integer> getSeatsNotRebook() { return seatsNotRebook; }
    public ObservableArrayList<Integer> getInfantsNotRebook() { return infantsNotRebook; }

    public void setSeatsLeft(boolean seatsLeft) {
        this.seatsLeft = seatsLeft;
        notifyPropertyChanged(BR.seatsLeft);
    }
    public void setStatus(int status) {
        this.status = status;
        notifyPropertyChanged(BR.status);
    }
    public void setAmount(double amount) {
        this.amount = amount;
        notifyPropertyChanged(BR.amount);
    }
    public void setFareDifference(double fareDifference) {
        this.fareDifference = fareDifference;
        notifyPropertyChanged(BR.fareDifference);
    }
    public void setRelated(String related) {
        this.related = related;
        notifyPropertyChanged(BR.related);
    }
    public void setRebookBy(String rebookBy) {
        this.rebookBy = rebookBy;
        notifyPropertyChanged(BR.rebookBy);
    }
    public void setRebookDate(Date rebookDate) {
        this.rebookDate = rebookDate;
        notifyPropertyChanged(BR.rebookDate);
    }
    public void setSeatsNotRebook(ObservableArrayList<Integer> seatsNotRebook) {
        this.seatsNotRebook = seatsNotRebook;
        notifyPropertyChanged(BR.seatsNotRebook);
    }
    public void setInfantsNotRebook(ObservableArrayList<Integer> infantsNotRebook) {
        this.infantsNotRebook = infantsNotRebook;
        notifyPropertyChanged(BR.infantsNotRebook);
    }

    public static final Creator<Adjustment> CREATOR = new Creator<Adjustment>() {
        public Adjustment createFromParcel(Parcel parcel) { return new Adjustment(parcel); }
        public Adjustment[] newArray(int size) { return new Adjustment[size]; }
    };
}
