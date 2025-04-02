package ph.easybus.ebmodels.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Passenger extends BaseObservable implements Parcelable {

    @Bindable
    private boolean hasNameError = false, hasDiscountError = false, selectedToPrint = true, edited = false,
        validated = false;

    @Bindable
    private int seatNo = 0, seatSpecialType = 0, seriesNo = 1;

    @Bindable
    private long ticketNo = 0;

    @Bindable
    private double editFee = 0d;

    @Bindable
    private String referenceNo = "", mobile = "", seatAlias = "";

    @Bindable
    private Name name = new Name();

    @Bindable
    private Discount discount = new Discount();

    @Bindable
    private ArrayList<Passenger> suggestedPassengers = new ArrayList<>();

    public Passenger() {}

    public Passenger(int seatNo) {
        this.seatNo = seatNo;
    }

    public Passenger(Name name, Discount discount) {
        this.name = name;
        this.discount = discount;
    }

    public Passenger(JSONObject object) {
        try {
            if (object.has("name")) name = new Name(object.getJSONObject("name"));
            if (object.has("discount")) discount = new Discount(object.getJSONObject("discount"));
            if (object.has("ticket_number")) ticketNo = object.getLong("ticket_number");
            if (object.has("edit_fee")) editFee = object.getDouble("edit_fee");
            if (object.has("validated")) validated = object.getBoolean("validated");

            if (object.has("reference_number")) referenceNo = object.getString("reference_number");
            // if (object.has("mobile")) mobile = object.getString("mobile");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public Passenger(Parcel parcel) {
        boolean[] booleans = new boolean[3];
        parcel.readBooleanArray(booleans);
        selectedToPrint = booleans[0];
        edited = booleans[1];
        validated = booleans[2];

        int[] ints = new int[3];
        parcel.readIntArray(ints);
        seatNo = ints[0];
        seatSpecialType = ints[1];
        seriesNo = ints[2];

        long[] longs = new long[1];
        parcel.readLongArray(longs);
        ticketNo = longs[0];

        double[] doubles = new double[1];
        parcel.readDoubleArray(doubles);
        editFee = doubles[0];

        String[] strings = new String[3];
        parcel.readStringArray(strings);
        referenceNo = strings[0];
        mobile = strings[1];
        seatAlias = strings[2];

        name = parcel.readParcelable(Name.class.getClassLoader());
        discount = parcel.readParcelable(Discount.class.getClassLoader());
    }

    public JSONObject toJSON() {
        JSONObject object = new JSONObject();
        try {
            object.put("name", name.toJSON());
            object.put("discount", discount.toJSON());
            object.put("ticket_number", ticketNo);
            object.put("reference_number", referenceNo);
            object.put("edit_fee", editFee);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }

    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeBooleanArray(new boolean[] { selectedToPrint, edited, validated });
        parcel.writeIntArray(new int[] { seatNo, seatSpecialType, seriesNo });
        parcel.writeLongArray(new long[] { ticketNo });
        parcel.writeDoubleArray(new double[] { editFee });
        parcel.writeStringArray(new String[] { referenceNo, mobile, seatAlias });
        parcel.writeParcelable(name, flags);
        parcel.writeParcelable(discount, flags);
    }

    public int describeContents() { return 0; }

    public boolean isHasNameError() { return hasNameError; }
    public boolean isHasDiscountError() { return hasDiscountError; }
    public boolean isSelectedToPrint() { return selectedToPrint; }
    public boolean isEdited() { return edited; }
    public boolean isValidated() { return validated; }
    public int getSeatNo() { return seatNo; }
    public int getSeatSpecialType() { return seatSpecialType; }
    public int getSeriesNo() { return seriesNo; }
    public long getTicketNo() { return ticketNo; }
    public double getEditFee() { return editFee; }
    public String getReferenceNo() { return referenceNo; }
    public String getMobile() { return mobile; }
    public String getSeatAlias() { return seatAlias; }
    public Name getName() { return name; }
    public Discount getDiscount() { return discount; }
    public ArrayList<Passenger> getSuggestedPassengers() { return suggestedPassengers; }
    public void setSeatNo(int seatNo) { this.seatNo = seatNo; }
    public void setSeatSpecialType(int seatSpecialType) { this.seatSpecialType = seatSpecialType; }
    public void setSeriesNo(int seriesNo) {
        this.seriesNo = seriesNo;
        notifyPropertyChanged(BR.seriesNo);
    }

    public void setHasNameError(boolean hasNameError) {
        this.hasNameError = hasNameError;
        notifyPropertyChanged(BR.hasNameError);
    }
    public void setHasDiscountError(boolean hasDiscountError) {
        this.hasDiscountError = hasDiscountError;
        notifyPropertyChanged(BR.hasDiscountError);
    }
    public void setSelectedToPrint(boolean selectedToPrint) {
        this.selectedToPrint = selectedToPrint;
        notifyPropertyChanged(BR.selectedToPrint);
    }
    public void setEdited(boolean edited) {
        this.edited = edited;
        notifyPropertyChanged(BR.edited);
    }

    public void setValidated(boolean validated) {
        this.validated = validated;
        notifyPropertyChanged(BR.validated);
    }

    public void setTicketNo(long ticketNo) {
        this.ticketNo = ticketNo;
        notifyPropertyChanged(BR.ticketNo);
    }
    public void setEditFee(double editFee) {
        this.editFee = editFee;
        notifyPropertyChanged(BR.editFee);
    }
    public void setReferenceNo(String referenceNo) {
        this.referenceNo = referenceNo;
        notifyPropertyChanged(BR.referenceNo);
    }
    public void setMobile(String mobile) {
        this.mobile = mobile;
        notifyPropertyChanged(BR.mobile);
    }
    public void setSeatAlias(String seatAlias) {
        this.seatAlias = seatAlias;
        notifyPropertyChanged(BR.seatAlias);
    }
    public void setName(Name name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }
    public void setDiscount(Discount discount) {
        this.discount = discount;
        notifyPropertyChanged(BR.discount);
    }
    public void setSuggestedPassengers(ArrayList<Passenger> suggestedPassengers) {
        this.suggestedPassengers = suggestedPassengers;
        notifyPropertyChanged(BR.suggestedPassengers);
    }

    public static final Creator<Passenger> CREATOR = new Creator<Passenger>() {
        public Passenger createFromParcel(Parcel parcel) { return new Passenger(parcel); }
        public Passenger[] newArray(int size) { return new Passenger[size]; }
    };
}
