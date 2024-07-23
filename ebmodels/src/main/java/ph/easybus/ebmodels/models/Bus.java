package ph.easybus.ebmodels.models;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Kimuel on 11/7/2016.
 */
public class Bus implements Parcelable {

    private String name, description, linerName, layout, busNumber;
    private int blockedSeats = 0, pwdSeats = 0, totalSeats = 0, allocatedSeats = 0;
    private ArrayList<String> seatMap;
    private ArrayList<Integer> amenities = new ArrayList<>();

    private ArrayList<ArrayList<Integer>> seatNumbers = new ArrayList<>();

    public Bus() {}

    public Bus(String name) {
        this.name = name;
    }

    public Bus(Parcel parcel) {
        String[] data = new String[5];
        parcel.readStringArray(data);
        name = data[0];
        description = data[1];
        linerName = data[2];
        layout = data[3];
        busNumber = data[4];

        int[] busSeatsInfo = new int[4];
        parcel.readIntArray(busSeatsInfo);
        blockedSeats = busSeatsInfo[0];
        pwdSeats = busSeatsInfo[1];
        totalSeats = busSeatsInfo[2];
        allocatedSeats = busSeatsInfo[3];

        int amenSize = parcel.readInt();
        int[] amenList = new int[amenSize];
        parcel.readIntArray(amenList);

        amenities = new ArrayList<>();
        for (int i = 0; i < amenList.length; i++) { amenities.add(amenList[i]); }

        ArrayList<String> seatMapList = new ArrayList<>();
        parcel.readStringList(seatMapList);
        seatMap = seatMapList;

        ArrayList<ArrayList<Integer>> seatNumbersList = new ArrayList<>();
        int seatNumbersListSize = parcel.readInt();
        for (int i = 0; i < seatNumbersListSize; i++) {
            int seatNumbersItemSize = parcel.readInt();
            ArrayList<Integer> seatNumbersItem = new ArrayList<>();

            int[] seatNumbersItemList = new int[seatNumbersItemSize];
            parcel.readIntArray(seatNumbersItemList);
            for (int j = 0; j < seatNumbersItemSize; j++) {
                seatNumbersItem.add(seatNumbersItemList[j]);
            }
            seatNumbersList.add(seatNumbersItem);
        }
        seatNumbers = seatNumbersList;
    }

    public Bus(JSONObject object) {
        try {
            if (object.has("name")) name = object.getString("name");
            if (object.has("description")) description = object.getString("description");
            if (object.has("blocked_seats")) blockedSeats = object.getInt("blocked_seats");
            if (object.has("pwd_seats")) pwdSeats =  object.getInt("pwd_seats");
            if (object.has("total_seats")) totalSeats = object.getInt("total_seats");
            if (object.has("allocated_seats")) allocatedSeats = object.getInt("allocated_seats");
            if (object.has("layout")) layout = object.getString("layout");
            if (object.has("bus_number")) busNumber = object.getString("bus_number");

            /* Getting Amenities */
            if (object.has("amenities")) {
                JSONArray busAmen = object.getJSONArray("amenities");
                ArrayList<Integer> amenities = new ArrayList<Integer>();
                for (int i = 0; i < busAmen.length(); i++) amenities.add(busAmen.getInt(i));
                this.amenities = amenities;
            }

            /* Getting Seat Map */
            if (object.has("seat_map")) {
                JSONArray busSeatMapArray = object.getJSONArray("seat_map");
                ArrayList<String> seatMap = new ArrayList<String>();
                for (int i = 0; i < busSeatMapArray.length(); i++) seatMap.add(busSeatMapArray.getString(i));
                this.seatMap = seatMap;
            }

            /* Getting Seat Numbers */
            if (object.has("seat_numbers")) {
                JSONArray seatsNumberArray = object.getJSONArray("seat_numbers");
                ArrayList<ArrayList<Integer>> seatNumbers = new ArrayList<>();
                for (int i = 0; i < seatsNumberArray.length(); i++) {
                    JSONArray currSeatsArray = seatsNumberArray.getJSONArray(i);
                    ArrayList<Integer> currSeats = new ArrayList<>();
                    for (int j = 0; j < currSeatsArray.length(); j++) {
                        currSeats.add(currSeatsArray.getInt(j));
                    }
                    seatNumbers.add(currSeats);
                }
                this.seatNumbers = seatNumbers;
            }
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
    }

    public JSONObject toJSON() {
        JSONObject object = new JSONObject();
        try {
            object.put("name", name);
            object.put("description", description);
            object.put("blocked_seats", blockedSeats);
            object.put("pwd_seats", pwdSeats);
            object.put("total_seats", totalSeats);
            object.put("allocated_seats", allocatedSeats);
            object.put("layout", layout);
            object.put("bus_number", busNumber);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }

    public String getSeatMapText()
    {
        String seatMapText = "";
        for (int i = 0; i < seatMap.size(); i++)
        {
            seatMapText += seatMap.get(i);
            if (i < seatMap.size() - 1) seatMapText += ",";
        }
        return seatMapText;
    }

    public void loadSeatMap(String seatMapText)
    {
        String[] seatMapArray = seatMapText.split(",");
        seatMap = new ArrayList<>();
        for (int i = 0; i < seatMapArray.length; i++) seatMap.add(seatMapArray[i]);
    }

    public int describeContents() { return 0; }

    public void writeToParcel(Parcel parcel, int flags) {
        System.out.println("WRITING TO PARCEL..");
        parcel.writeStringArray(new String[] { name, description, linerName, layout, busNumber });

        parcel.writeIntArray(new int[] { blockedSeats, pwdSeats, totalSeats, allocatedSeats });

        int[] parcelAmen = new int[amenities.size()];
        for (int i = 0; i < amenities.size(); i++) { parcelAmen[i] = amenities.get(i); }
        parcel.writeInt(amenities.size());
        parcel.writeIntArray(parcelAmen);

        parcel.writeStringList(seatMap);

        parcel.writeInt(seatNumbers.size());
        for (int i = 0; i < seatNumbers.size(); i++) {
            int[] currRow = new int[seatNumbers.get(i).size()];

            parcel.writeInt(seatNumbers.get(i).size());
            for (int j = 0; j < seatNumbers.get(i).size(); j++) {
                currRow[j] = seatNumbers.get(i).get(j);
            }
            parcel.writeIntArray(currRow);
        }
    }

    public int getTotalSeats() { return totalSeats; }
    public int getBlockedSeats() { return blockedSeats; }
    public int getPwdSeats() { return pwdSeats; }
    public int getAllocatedSeats() { return allocatedSeats; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public String getLinerName() { return linerName; }
    public String getLayout() { return layout; }
    public String getBusNumber() { return busNumber; }
    public ArrayList<String> getSeatMap() { return seatMap; }
    public ArrayList<Integer> getAmenities() { return amenities; }
    public ArrayList<ArrayList<Integer>> getSeatNumbers() { return seatNumbers; }

    public void setPwdSeats(int pwdSeats) { this.pwdSeats = pwdSeats; }
    public void setTotalSeats(int totalSeats) { this.totalSeats = totalSeats; }
    public void setBlockedSeats(int blockedSeats) { this.blockedSeats = blockedSeats; }
    public void setAllocatedSeats(int allocatedSeats) { this.allocatedSeats = allocatedSeats; }
    public void setName(String name) { this.name = name; }
    public void setDescription(String description) { this.description = description; }
    public void setLinerName(String linerName) { this.linerName = linerName; }
    public void setLayout(String layout) { this.layout = layout; }
    public void setBusNumber(String busNumber) { this.busNumber = busNumber; }
    public void setSeatMap(ArrayList<String> seatMap) { this.seatMap = seatMap; }
    public void setSeatNumbers(ArrayList<ArrayList<Integer>> seatNumbers) {
        this.seatNumbers = seatNumbers;
    }
    public void setAmenities(ArrayList<Integer> amenities) {
        this.amenities = amenities;
    }

    public static final Creator<Bus> CREATOR = new Creator<Bus>() {
        public Bus createFromParcel(Parcel parcel) { return new Bus(parcel); }
        public Bus[] newArray(int position) { return new Bus[position]; }
    };
}
