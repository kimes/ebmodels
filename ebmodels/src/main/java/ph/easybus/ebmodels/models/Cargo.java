package ph.easybus.ebmodels.models;

import android.os.Build;
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

    @Deprecated
    public static final int TYPE_CHECKED_IN = 1;

    public static final int TYPE_DEFAULT = 0, TYPE_CHECK_IN = 1;

    public static final int AMOUNT_TYPE_REGULAR = 0, AMOUNT_TYPE_FIXED = 1, AMOUNT_TYPE_SPECIAL = 2;

    @Bindable
    private boolean fixed = false, d2d = false;

    @Bindable
    private int id = 0, status = 0, shipmentStatus = 0, packagesCount = 0,
            type = TYPE_DEFAULT, paxCount = 0, minWeight = 0, seriesNo = 1,
            amountType = AMOUNT_TYPE_REGULAR;

    @Bindable
    private double declaredValue = 0, totalAmount = 0, totalWeight = 0, processingFee = 0,
            baseAmount = 0, excessFee = 0, portersFee = 0, declaredValueFee = 0,
            systemFee = 0, discountPercent = 0, discountAmount = 0, additionalFee = 0, insuranceFee = 0;

    @Bindable
    private String mongoId, origin, destination, description, linerName,
            senderMobile = "", receiverMobile = "", referenceNumber, paymentType = "Cash", paymentRemarks,
            shippedBy, paymentQr = "", blNumber, discountRemarks = "", sellerCode = "", cancelledBy = "",
            office = "", fixedItem = "", cancelledRemarks = "";

    @Bindable
    private Date dropOffDate = Calendar.getInstance().getTime(), cancelledDate = null;

    @Bindable
    private Name senderName = new Name(), receiverName = new Name();

    @Bindable
    private Trip trip;

    private ArrayList<String> images = new ArrayList<>();

    @Bindable
    private ArrayList<CargoStatusLog> statusLogs = new ArrayList<>();

    public Cargo() {}

    public Cargo(Parcel parcel) {
        boolean[] booleans = new boolean[2];
        parcel.readBooleanArray(booleans);
        fixed = booleans[0];
        d2d = booleans[1];

        int[] ints = new int[8];
        parcel.readIntArray(ints);
        id = ints[0];
        status = ints[1];
        shipmentStatus = ints[2];
        packagesCount = ints[3];
        type = ints[4];
        paxCount = ints[5];
        minWeight = ints[6];
        seriesNo = ints[7];

        /* { declaredValue, totalAmount, totalWeight, processingFee,
                systemFee, baseAmount, excessFee, portersFee, declaredValueFee,
                discountPercent, discountAmount } */
        double[] doubles = new double[13];
        parcel.readDoubleArray(doubles);
        declaredValue = doubles[0];
        totalAmount = doubles[1];
        totalWeight = doubles[2];
        processingFee = doubles[3];
        systemFee = doubles[4];
        baseAmount = doubles[5];
        excessFee = doubles[6];
        portersFee = doubles[7];
        declaredValueFee = doubles[8];
        discountPercent = doubles[9];
        discountAmount = doubles[10];
        additionalFee = doubles[11];
        insuranceFee = doubles[12];

        String[] strings = new String[19];
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
        discountRemarks = strings[13];
        sellerCode = strings[14];
        cancelledBy = strings[15];
        office = strings[16];
        fixedItem = strings[17];
        cancelledRemarks = strings[18];

        long[] longs = new long[2];
        parcel.readLongArray(longs);
        dropOffDate = new Date(longs[0]);
        if (longs[1] > 0) cancelledDate = new Date(longs[1]);

        senderName = parcel.readParcelable(Name.class.getClassLoader());
        receiverName = parcel.readParcelable(Name.class.getClassLoader());

        trip = parcel.readParcelable(Trip.class.getClassLoader());

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
            if (object.has("fixed")) fixed = object.getBoolean("fixed");
            if (object.has("d2d")) d2d = object.getBoolean("d2d");

            if (object.has("status")) status = object.getInt("status");
            if (object.has("shipment_status")) shipmentStatus = object.getInt("shipment_status");
            if (object.has("packages_count")) packagesCount = object.getInt("packages_count");
            if (object.has("type")) type = object.getInt("type");
            if (object.has("amount_type")) amountType = object.getInt("amount_type");
            if (object.has("pax_count")) paxCount = object.getInt("pax_count");
            if (object.has("min_weight")) minWeight = object.getInt("min_weight");

            if (object.has("declared_value")) declaredValue = object.getDouble("declared_value");
            if (object.has("total_amount")) totalAmount = object.getDouble("total_amount");
            if (object.has("total_weight")) totalWeight = object.getDouble("total_weight");
            if (object.has("system_fee")) systemFee = object.getDouble("system_fee");
            if (object.has("processing_fee")) processingFee = object.getDouble("processing_fee");
            if (object.has("base_amount")) baseAmount = object.getDouble("base_amount");
            if (object.has("excess_fee")) excessFee = object.getDouble("excess_fee");
            if (object.has("porters_fee")) portersFee = object.getDouble("porters_fee");
            if (object.has("additional_fee")) additionalFee = object.getDouble("additional_fee");
            if (object.has("insurance_fee")) insuranceFee = object.getDouble("insurance_fee");
            if (object.has("declared_value_fee")) declaredValueFee = object.getDouble("declared_value_fee");
            if (object.has("discount_percent")) discountPercent = object.getDouble("discount_percent");
            if (object.has("discount_amount")) discountAmount = object.getDouble("discount_amount");

            if (object.has("origin")) origin = object.getString("origin");
            if (object.has("destination")) destination = object.getString("destination");
            if (object.has("description")) description = object.getString("description");
            if (object.has("office")) office = object.getString("office");
            if (object.has("liner_name")) linerName = object.getString("liner_name");
            if (object.has("fixed_item")) fixedItem = object.getString("fixed_item");
            if (object.has("sender_mobile")) senderMobile = object.getString("sender_mobile");
            if (object.has("receiver_mobile")) receiverMobile = object.getString("receiver_mobile");
            if (object.has("reference_number")) referenceNumber = object.getString("reference_number");
            if (object.has("payment_type")) paymentType = object.getString("payment_type");
            if (object.has("payment_remarks")) paymentRemarks = object.getString("payment_remarks");
            if (object.has("shipped_by")) shippedBy = object.getString("shipped_by");
            if (object.has("bl_number")) blNumber = object.getString("bl_number");
            if (object.has("discount_remarks")) discountRemarks = object.getString("discount_remarks");
            if (object.has("seller_code")) sellerCode = object.getString("seller_code");
            if (object.has("cancelled_by")) cancelledBy = object.getString("cancelled_by");
            if (object.has("cancelled_remarks")) cancelledRemarks = object.getString("cancelled_remarks");

            if (object.has("drop_off_date")) {
                dropOffDate = DateTimeUtils.toDateUtc(object.getString("drop_off_date"));
            }

            if (object.has("cancelled_date")) {
                cancelledDate = DateTimeUtils.toDateUtc(object.getString("cancelled_date"));
            }

            if (object.has("sender_name")) {
                senderName = new Name(object.getJSONObject("sender_name"));
            }

            if (object.has("receiver_name")) {
                receiverName = new Name(object.getJSONObject("receiver_name"));
            }

            if (object.has("trip")) {
                Date tripDate = DateTimeUtils.toDate(object.getString("trip_date"));
                trip = new Trip(object.getJSONObject("trip"), tripDate);
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
            object.put("fixed", fixed);
            object.put("d2d", d2d);

            object.put("status", status);
            object.put("shipment_status", shipmentStatus);
            object.put("packages_count", packagesCount);
            object.put("type", type);
            object.put("amount_type", amountType);
            object.put("pax_count", paxCount);
            object.put("min_weight", minWeight);

            object.put("declared_value", declaredValue);
            object.put("total_amount", totalAmount);
            object.put("total_weight", totalWeight);
            object.put("system_fee", systemFee);
            object.put("processing_fee", processingFee);
            object.put("base_amount", baseAmount);
            object.put("excess_fee", excessFee);
            object.put("porters_fee", portersFee);
            object.put("additional_fee", additionalFee);
            object.put("insurance_fee", insuranceFee);
            object.put("declared_value_fee", declaredValueFee);
            object.put("discount_percent", discountPercent);
            object.put("discount_amount", discountAmount);

            object.put("origin", origin);
            object.put("destination", destination);
            object.put("description", description);
            object.put("office", office);
            object.put("liner_name", linerName);
            object.put("fixed_item", fixedItem);
            object.put("sender_mobile", senderMobile);
            object.put("receiver_mobile", receiverMobile);
            object.put("reference_number", referenceNumber);
            object.put("payment_type", paymentType);
            object.put("payment_remarks", paymentRemarks);
            object.put("shipped_by", shippedBy);
            object.put("bl_number", blNumber);
            object.put("discount_remarks", discountRemarks);
            object.put("seller_code", sellerCode);
            object.put("cancelled_remarks", cancelledRemarks);

            object.put("drop_off_date", DateTimeUtils.toISODateUtc(dropOffDate));
            object.put("sender_name", senderName.toJSON());
            object.put("receiver_name", receiverName.toJSON());

            if (trip != null) {
                object.put("trip_id", trip.getMongoId());
                object.put("trip_date", DateTimeUtils.toISODate(trip.getDate()));
            }

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
        parcel.writeBooleanArray(new boolean[] { fixed, d2d });
        parcel.writeIntArray(new int[] { id, status, shipmentStatus, packagesCount, type,
                paxCount, minWeight, seriesNo, amountType });
        parcel.writeDoubleArray(new double[] { declaredValue, totalAmount, totalWeight, processingFee,
                systemFee, baseAmount, excessFee, portersFee, declaredValueFee,
                discountPercent, discountAmount, additionalFee, insuranceFee });
        parcel.writeStringArray(new String[] { mongoId, origin, destination, description, linerName,
                senderMobile, receiverMobile, referenceNumber, paymentType, paymentRemarks,
                shippedBy, paymentQr, blNumber, discountRemarks, sellerCode, cancelledBy,
                office, fixedItem, cancelledRemarks });
        parcel.writeLongArray(new long[] { dropOffDate.getTime(),
            cancelledDate != null ? cancelledDate.getTime() : -1 });

        parcel.writeParcelable(senderName, flags);
        parcel.writeParcelable(receiverName, flags);
        parcel.writeParcelable(trip, flags);
        parcel.writeStringList(images);
        parcel.writeParcelableList(statusLogs, flags);
    }

    public int describeContents() { return 0; }

    public boolean isFixed() { return fixed; }
    public boolean isD2d() { return d2d; }
    public int getId() { return id; }
    public int getStatus() { return status; }
    public int getShipmentStatus() { return shipmentStatus; }
    public int getPackagesCount() { return packagesCount; }
    public int getType() { return type; }
    public int getAmountType() { return amountType; }
    public int getPaxCount() { return paxCount; }
    public int getMinWeight() { return minWeight; }
    public int getSeriesNo() { return seriesNo; }
    public double getDeclaredValue() { return declaredValue; }
    public double getTotalAmount() { return totalAmount; }
    public double getTotalWeight() { return totalWeight; }
    public double getSystemFee() { return systemFee; }
    public double getProcessingFee() { return processingFee; }
    public double getBaseAmount() { return baseAmount; }
    public double getExcessFee() { return excessFee; }
    public double getPortersFee() { return portersFee; }
    public double getAdditionalFee() { return additionalFee; }
    public double getInsuranceFee() { return insuranceFee; }
    public double getDeclaredValueFee() { return declaredValueFee; }
    public double getDiscountPercent() { return discountPercent; }
    public double getDiscountAmount() { return discountAmount; }
    public String getMongoId() { return mongoId; }
    public String getOrigin() { return origin; }
    public String getDestination() { return destination; }
    public String getDescription() { return description; }
    public String getOffice() { return office; }
    public String getLinerName() { return linerName; }
    public String getFixedItem() { return fixedItem; }
    public String getSenderMobile() { return senderMobile; }
    public String getReceiverMobile() { return receiverMobile; }
    public String getReferenceNumber() { return referenceNumber; }
    public String getPaymentType() { return paymentType; }
    public String getPaymentRemarks() { return paymentRemarks; }
    public String getShippedBy() { return shippedBy; }
    public String getPaymentQr() { return paymentQr; }
    public String getBlNumber() { return blNumber; }
    public String getDiscountRemarks() { return discountRemarks; }
    public String getSellerCode() { return sellerCode; }
    public String getCancelledBy() { return cancelledBy; }
    public String getCancelledRemarks() { return cancelledRemarks; }
    public Date getDropOffDate() { return dropOffDate; }
    public Date getCancelledDate() { return cancelledDate; }
    public Name getSenderName() { return senderName; }
    public Name getReceiverName() { return receiverName; }
    public Trip getTrip() { return trip; }
    public ArrayList<String> getImages() { return images; }
    public ArrayList<CargoStatusLog> getStatusLogs() { return statusLogs; }

    public void setFixed(boolean fixed) {
        this.fixed = fixed;
        notifyPropertyChanged(BR.fixed);
    }

    public void setD2d(boolean d2d) {
        this.d2d = d2d;
        notifyPropertyChanged(BR.d2d);
    }
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

    public void setAmountType(int amountType) {
        this.amountType = amountType;
        notifyPropertyChanged(BR.amountType);
    }

    public void setPaxCount(int paxCount) {
        this.paxCount = paxCount;
        notifyPropertyChanged(BR.paxCount);
    }

    public void setMinWeight(int minWeight) {
        this.minWeight = minWeight;
        notifyPropertyChanged(BR.minWeight);
    }

    public void setSeriesNo(int seriesNo) {
        this.seriesNo = seriesNo;
        notifyPropertyChanged(BR.seriesNo);
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

    public void setBaseAmount(double baseAmount) {
        this.baseAmount = baseAmount;
        notifyPropertyChanged(BR.baseAmount);
    }

    public void setExcessFee(double excessFee) {
        this.excessFee = excessFee;
        notifyPropertyChanged(BR.excessFee);
    }

    public void setPortersFee(double portersFee) {
        this.portersFee = portersFee;
        notifyPropertyChanged(BR.portersFee);
    }

    public void setAdditionalFee(double additionalFee) {
        this.additionalFee = additionalFee;
        notifyPropertyChanged(BR.additionalFee);
    }

    public void setInsuranceFee(double insuranceFee) {
        this.insuranceFee = insuranceFee;
        notifyPropertyChanged(BR.insuranceFee);
    }

    public void setDeclaredValueFee(double declaredValueFee) {
        this.declaredValueFee = declaredValueFee;
        notifyPropertyChanged(BR.declaredValueFee);
    }

    public void setDiscountPercent(double discountPercent) {
        this.discountPercent = discountPercent;
        notifyPropertyChanged(BR.discountPercent);
    }

    public void setDiscountAmount(double discountAmount) {
        this.discountAmount = discountAmount;
        notifyPropertyChanged(BR.discountAmount);
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

    public void setOffice(String office) {
        this.office = office;
        notifyPropertyChanged(BR.office);
    }

    public void setLinerName(String linerName) {
        this.linerName = linerName;
        notifyPropertyChanged(BR.linerName);
    }

    public void setFixedItem(String fixedItem) {
        this.fixedItem = fixedItem;
        notifyPropertyChanged(BR.fixedItem);
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

    public void setDiscountRemarks(String discountRemarks) {
        this.discountRemarks = discountRemarks;
        notifyPropertyChanged(BR.discountRemarks);
    }

    public void setShippedBy(String shippedBy) {
        this.shippedBy = shippedBy;
        notifyPropertyChanged(BR.shippedBy);
    }

    public void setSellerCode(String sellerCode) {
        this.sellerCode = sellerCode;
        notifyPropertyChanged(BR.sellerCode);
    }

    public void setCancelledBy(String cancelledBy) {
        this.cancelledBy = cancelledBy;
        notifyPropertyChanged(BR.cancelledBy);
    }

    public void setCancelledRemarks(String cancelledRemarks) {
        this.cancelledRemarks = cancelledRemarks;
        notifyPropertyChanged(BR.cancelledRemarks);
    }

    public void setSenderName(Name senderName) {
        this.senderName = senderName;
        notifyPropertyChanged(BR.senderName);
    }

    public void setReceiverName(Name receiverName) {
        this.receiverName = receiverName;
        notifyPropertyChanged(BR.receiverName);
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
        notifyPropertyChanged(BR.trip);
    }

    public void setPaymentQr(String paymentQr) {
        this.paymentQr = paymentQr;
    }

    public void setDropOffDate(Date dropOffDate) { this.dropOffDate = dropOffDate; }

    public void setCancelledDate(Date cancelledDate) {
        this.cancelledDate = cancelledDate;
        notifyPropertyChanged(BR.cancelledDate);
    }

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
