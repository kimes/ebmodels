package ph.easybus.ebmodels.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import ph.easybus.ebmodels.utils.DateTimeUtils;

public class Cargo extends BaseObservable implements Parcelable {

    public static final int TYPE_DEFAULT = 0, TYPE_CHECKED_IN = 1;

    @Bindable
    private int id = 0, status = 0, shipmentStatus = 0, packagesCount = 0,
            type = TYPE_DEFAULT, paxCount = 0;

    @Bindable
    private double declaredValue = 0, totalAmount = 0, totalWeight = 0, systemFee = 0, processingFee = 0;

    @Bindable
    private String mongoId, origin, destination, description, linerName,
            senderMobile = "", receiverMobile = "", referenceNumber, paymentType = "Cash", paymentRemarks,
            shippedBy, paymentQr = "", blNumber;

    private Date dropOffDate = Calendar.getInstance().getTime();

    @Bindable
    private Name senderName = new Name(), receiverName = new Name();

    private ArrayList<String> images = new ArrayList<>();

    @Bindable
    private ArrayList<CargoStatusLog> statusLogs = new ArrayList<>();

    public Cargo() {}

    public Cargo(Parcel parcel) {
        int[] ints = new int[6];
        parcel.readIntArray(ints);
        id = ints[0];
        status = ints[1];
        shipmentStatus = ints[2];
        packagesCount = ints[3];
        type = ints[4];
        paxCount = ints[5];

        double[] doubles = new double[5];
        parcel.readDoubleArray(doubles);
        declaredValue = doubles[0];
        totalAmount = doubles[1];
        totalWeight = doubles[2];
        systemFee = doubles[3];
        processingFee = doubles[4];

        String[] strings = new String[13];
        mongoId = strings[0];
        origin = strings[1];
        destination = strings[2];
        description = strings[3];
        linerName = strings[4];
        senderMobile = strings[5];
        receiverMobile = strings[6];
        referenceNumber = strings[7];
        paymentType = strings[8];
        paymentRemarks = strings[9];
        shippedBy = strings[10];
        paymentQr = strings[11];
        blNumber = strings[12];

        long[] longs = new long[1];
        parcel.readLongArray(longs);
        dropOffDate = new Date(longs[0]);

        senderName = parcel.readParcelable(Name.class.getClassLoader());
        receiverName = parcel.readParcelable(Name.class.getClassLoader());

        ArrayList<String> i = new ArrayList<>();
        parcel.readStringList(i);
        images = i;

        ArrayList<CargoStatusLog> c = new ArrayList<>();
        parcel.readParcelableList(c, CargoStatusLog.class.getClassLoader());
        statusLogs = c;
    }

    public Cargo(JSONObject object) {
        try {
            if (object.has("_id")) mongoId = object.getString("_id");

            if (object.has("status")) status = object.getInt("status");
            if (object.has("shipment_status")) shipmentStatus = object.getInt("shipment_status");
            if (object.has("packages_count")) packagesCount = object.getInt("packages_count");
            if (object.has("type")) type = object.getInt("type");
            if (object.has("pax_count")) paxCount = object.getInt("pax_count");

            if (object.has("declared_value")) declaredValue = object.getDouble("declared_value");
            if (object.has("total_amount")) totalAmount = object.getDouble("total_amount");
            if (object.has("total_weight")) totalWeight = object.getDouble("total_weight");
            if (object.has("system_fee")) systemFee = object.getDouble("system_fee");
            if (object.has("processing_fee")) processingFee = object.getDouble("processing_fee");

            if (object.has("origin")) origin = object.getString("origin");
            if (object.has("destination")) destination = object.getString("destination");
            if (object.has("description")) description = object.getString("description");
            if (object.has("liner_name")) linerName = object.getString("liner_name");
            if (object.has("sender_mobile")) senderMobile = object.getString("sender_mobile");
            if (object.has("receiver_mobile")) receiverMobile = object.getString("receiver_mobile");
            if (object.has("reference_number")) referenceNumber = object.getString("reference_number");
            if (object.has("payment_type")) paymentType = object.getString("payment_type");
            if (object.has("payment_remarks")) paymentRemarks = object.getString("payment_remarks");
            if (object.has("shipped_by")) shippedBy = object.getString("shipped_by");
            if (object.has("bl_number")) blNumber = object.getString("bl_number");

            if (object.has("drop_off_date")) {
                dropOffDate = DateTimeUtils.toDateUtc(object.getString("drop_off_date"));
            }

            if (object.has("sender_name")) {
                senderName = new Name(object.getJSONObject("sender_name"));
            }

            if (object.has("receiver_name")) {
                receiverName = new Name(object.getJSONObject("receiver_name"));
            }

            if (object.has("images")) {
                images = new ArrayList<>();
                JSONArray array = object.getJSONArray("images");
                for (int i = 0; i < array.length(); i++) {
                    images.add(array.getString(i));
                }
            }

            if (object.has("status_logs")) {
                statusLogs = new ArrayList<>();
                JSONArray array = object.getJSONArray("status_logs");
                for (int i = 0; i < array.length(); i++) {
                    statusLogs.add(new CargoStatusLog(array.getJSONObject(i)));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public JSONObject toJSON() {
        JSONObject object = new JSONObject();
        try {
            object.put("_id", mongoId);
            object.put("status", status);
            object.put("shipment_status", shipmentStatus);
            object.put("packages_count", packagesCount);
            object.put("type", type);
            object.put("pax_count", paxCount);

            object.put("declared_value", declaredValue);
            object.put("total_amount", totalAmount);
            object.put("total_weight", totalWeight);
            object.put("system_fee", systemFee);
            object.put("processing_fee", processingFee);

            object.put("origin", origin);
            object.put("destination", destination);
            object.put("description", description);
            object.put("liner_name", linerName);
            object.put("sender_mobile", senderMobile);
            object.put("receiver_mobile", receiverMobile);
            object.put("reference_number", referenceNumber);
            object.put("payment_type", paymentType);
            object.put("payment_remarks", paymentRemarks);
            object.put("shipped_by", shippedBy);
            object.put("bl_number", blNumber);

            object.put("drop_off_date", DateTimeUtils.toISODateUtc(dropOffDate));
            object.put("sender_name", senderName.toJSON());
            object.put("receiver_name", receiverName.toJSON());

            JSONArray array = new JSONArray();
            for (int i = 0; i < images.size(); i++) {
                array.put(images.get(i));
            }
            object.put("images", array);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }

    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeIntArray(new int[] { id, status, shipmentStatus, packagesCount, type, paxCount });
        parcel.writeDoubleArray(new double[] { declaredValue, totalAmount, totalWeight,
                systemFee, processingFee });
        parcel.writeStringArray(new String[] { mongoId, origin, destination, description, linerName,
                senderMobile, receiverMobile, referenceNumber, paymentType, paymentRemarks,
                shippedBy, paymentQr, blNumber });
        parcel.writeLongArray(new long[] { dropOffDate.getTime() });

        parcel.writeParcelable(senderName, flags);
        parcel.writeParcelable(receiverName, flags);
        parcel.writeStringList(images);
        parcel.writeParcelableList(statusLogs, flags);
    }

    public int describeContents() { return 0; }

    public int getId() { return id; }
    public int getStatus() { return status; }
    public int getShipmentStatus() { return shipmentStatus; }
    public int getPackagesCount() { return packagesCount; }
    public int getType() { return type; }
    public int getPaxCount() { return paxCount; }
    public double getDeclaredValue() { return declaredValue; }
    public double getTotalAmount() { return totalAmount; }
    public double getTotalWeight() { return totalWeight; }
    public double getSystemFee() { return systemFee; }
    public double getProcessingFee() { return processingFee; }
    public String getMongoId() { return mongoId; }
    public String getOrigin() { return origin; }
    public String getDestination() { return destination; }
    public String getDescription() { return description; }
    public String getLinerName() { return linerName; }
    public String getSenderMobile() { return senderMobile; }
    public String getReceiverMobile() { return receiverMobile; }
    public String getReferenceNumber() { return referenceNumber; }
    public String getPaymentType() { return paymentType; }
    public String getPaymentRemarks() { return paymentRemarks; }
    public String getShippedBy() { return shippedBy; }
    public String getPaymentQr() { return paymentQr; }
    public String getBlNumber() { return blNumber; }
    public Date getDropOffDate() { return dropOffDate; }
    public Name getSenderName() { return senderName; }
    public Name getReceiverName() { return receiverName; }
    public ArrayList<String> getImages() { return images; }
    public ArrayList<CargoStatusLog> getStatusLogs() { return statusLogs; }

    public void setId(int id) { this.id = id; }

    public void setStatus(int status) {
        this.status = status;
        notifyPropertyChanged(BR.status);
    }

    public void setShipmentStatus(int shipmentStatus) {
        this.shipmentStatus = shipmentStatus;
        notifyPropertyChanged(BR.shipmentStatus);
    }

    public void setPackagesCount(int packagesCount) {
        this.packagesCount = packagesCount;
        notifyPropertyChanged(BR.packagesCount);
    }

    public void setType(int type) {
        this.type = type;
        notifyPropertyChanged(BR.type);
    }

    public void setPaxCount(int paxCount) {
        this.paxCount = paxCount;
        notifyPropertyChanged(BR.paxCount);
    }

    public void setDeclaredValue(double declaredValue) {
        this.declaredValue = declaredValue;
        notifyPropertyChanged(BR.declaredValue);
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
        notifyPropertyChanged(BR.totalAmount);
    }

    public void setTotalWeight(double totalWeight) {
        this.totalWeight = totalWeight;
        notifyPropertyChanged(BR.totalWeight);
    }

    public void setSystemFee(double systemFee) {
        this.systemFee = systemFee;
        notifyPropertyChanged(BR.systemFee);
    }

    public void setProcessingFee(double processingFee) {
        this.processingFee = processingFee;
        notifyPropertyChanged(BR.processingFee);
    }

    public void setMongoId(String mongoId) { this.mongoId = mongoId; }

    public void setOrigin(String origin) {
        this.origin = origin;
        notifyPropertyChanged(BR.origin);
    }

    public void setDestination(String destination) {
        this.destination = destination;
        notifyPropertyChanged(BR.destination);
    }

    public void setDescription(String description) {
        this.description = description;
        notifyPropertyChanged(BR.description);
    }

    public void setLinerName(String linerName) {
        this.linerName = linerName;
        notifyPropertyChanged(BR.linerName);
    }

    public void setSenderMobile(String senderMobile) {
        this.senderMobile = senderMobile;
        notifyPropertyChanged(BR.senderMobile);
    }

    public void setReceiverMobile(String receiverMobile) {
        this.receiverMobile = receiverMobile;
        notifyPropertyChanged(BR.receiverMobile);
    }

    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
        notifyPropertyChanged(BR.referenceNumber);
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
        notifyPropertyChanged(BR.paymentType);
    }

    public void setPaymentRemarks(String paymentRemarks) {
        this.paymentRemarks = paymentRemarks;
        notifyPropertyChanged(BR.paymentRemarks);
    }

    public void setBlNumber(String blNumber) {
        this.blNumber = blNumber;
        notifyPropertyChanged(BR.blNumber);
    }

    public void setShippedBy(String shippedBy) {
        this.shippedBy = shippedBy;
        notifyPropertyChanged(BR.shippedBy);
    }

    public void setSenderName(Name senderName) {
        this.senderName = senderName;
        notifyPropertyChanged(BR.senderName);
    }

    public void setReceiverName(Name receiverName) {
        this.receiverName = receiverName;
        notifyPropertyChanged(BR.receiverName);
    }

    public void setPaymentQr(String paymentQr) {
        this.paymentQr = paymentQr;
    }

    public void setDropOffDate(Date dropOffDate) { this.dropOffDate = dropOffDate; }
    public void setImages(ArrayList<String> images) { this.images = images; }
    public void setStatusLogs(ArrayList<CargoStatusLog> statusLogs) {
        this.statusLogs = statusLogs;
        notifyPropertyChanged(BR.statusLogs);
    }

    public static final Creator<Cargo> CREATOR = new Creator<Cargo>() {
        public Cargo createFromParcel(Parcel parcel) { return new Cargo(parcel); }
        public Cargo[] newArray(int size) { return new Cargo[size]; }
    };
}
