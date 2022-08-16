package ph.easybus.ebmodels.models;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;

import ph.easybus.ebmodels.utils.DateTimeUtils;

public class AgentTransaction implements Parcelable {

    private int type = 0;
    private double amount = 0;
    private String email = "", title, info, remarks, transactedBy = "";
    private Date transactionDate = Calendar.getInstance().getTime();

    public AgentTransaction() {}
    public AgentTransaction(Parcel parcel) {
        int[] ints = new int[1];
        parcel.readIntArray(ints);
        type = ints[0];

        double[] doubles = new double[1];
        parcel.readDoubleArray(doubles);
        amount = doubles[0];

        String[] strings = new String[5];
        parcel.readStringArray(strings);
        email = strings[0];
        title = strings[1];
        info = strings[2];
        remarks = strings[3];
        transactedBy = strings[4];

        long[] dates = new long[1];
        parcel.readLongArray(dates);
        transactionDate = new Date(dates[0]);
    }

    public AgentTransaction(JSONObject object) {
        try {
            if (object.has("type")) type = object.getInt("type");
            if (object.has("amount")) amount = object.getDouble("amount");
            if (object.has("email")) email = object.getString("email");
            if (object.has("title")) title = object.getString("title");
            if (object.has("info")) info = object.getString("info");
            if (object.has("remarks")) remarks = object.getString("remarks");
            if (object.has("transacted_by")) transactedBy = object.getString("transacted_by");
            if (object.has("transaction_date")) {
                transactionDate = DateTimeUtils.toDateUtc(object.getString("transaction_date"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public JSONObject toJSON() {
        JSONObject object = new JSONObject();
        try {
            object.put("type", type);
            object.put("amount", amount);
            object.put("email", email);
            object.put("title", title);
            object.put("info", info);
            object.put("remarks", remarks);
            object.put("transacted_by", transactedBy);
            object.put("transaction_date", DateTimeUtils.toISODateUtc(transactionDate));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }

    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeIntArray(new int[] { type });
        parcel.writeDoubleArray(new double[] { amount });
        parcel.writeStringArray(new String[] { email, title, info, remarks, transactedBy });
        parcel.writeLongArray(new long[] { transactionDate.getTime() });
    }

    public int describeContents() { return 0; }

    public int getType() { return type; }
    public double getAmount() { return amount; }
    public String getEmail() { return email; }
    public String getTitle() { return title; }
    public String getInfo() { return info; }
    public String getRemarks() { return remarks; }
    public String getTransactedBy() { return transactedBy; }
    public Date getTransactionDate() { return transactionDate; }
    public void setType(int type) { this.type = type; }
    public void setAmount(double amount) { this.amount = amount; }
    public void setEmail(String email) { this.email = email; }
    public void setTitle(String title) { this.title = title; }
    public void setInfo(String info) { this.info = info; }
    public void setRemarks(String remarks) { this.remarks = remarks; }
    public void setTransactedBy(String transactedBy) { this.transactedBy = transactedBy; }
    public void setTransactionDate(Date transactionDate) { this.transactionDate = transactionDate; }

    public static final Creator<AgentTransaction> CREATOR = new Creator<AgentTransaction>() {
        public AgentTransaction createFromParcel(Parcel parcel) { return new AgentTransaction(parcel); }
        public AgentTransaction[] newArray(int size) { return new AgentTransaction[size]; }
    };
}
