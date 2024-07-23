package ph.easybus.app;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;

import ph.easybus.ebmodels.models.Bus;

public class MainActivity extends AppCompatActivity {

    private ArrayList<String> map = new ArrayList<>(
            Arrays.asList(
                    "RR_UL",
                    "/D_UL",
                    "RA_UL",
                    "//_UL",
                    "RR_uR",
                    "RR_uR",
                    "AA_UL",
                    "AA_UL",
                    "UU_UL",
                    "LL_UL"
            ));

    private ArrayList<ArrayList<Integer>> seatNumbers = new ArrayList<>(
            Arrays.asList(
                    new ArrayList<>(Arrays.asList(0, 0, 0, 3, 4)),
                    new ArrayList<>(Arrays.asList(1, 2, 0, 3, 4)),
                    new ArrayList<>(Arrays.asList(0, 7, 0, 5, 6)),
                    new ArrayList<>(Arrays.asList(9, 8, 0, 5, 6)),
                    new ArrayList<>(Arrays.asList(0, 0, 0, 10, 0)),
                    new ArrayList<>(Arrays.asList(0, 0, 0, 10, 0)),
                    new ArrayList<>(Arrays.asList(11, 13, 0, 16, 15)),
                    new ArrayList<>(Arrays.asList(12, 14, 0, 16, 15)),
                    new ArrayList<>(Arrays.asList(17, 17, 0, 18, 19)),
                    new ArrayList<>(Arrays.asList(20, 20, 0, 18, 19))
            ));

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*
        Bus bus = new Bus();
        bus.setLayout("c");
        bus.setSeatMap(map);
        bus.setSeatNumbers(seatNumbers);

        Parcel parcel = Parcel.obtain();
        bus.writeToParcel(parcel, 0);

        parcel.readSerializable();
        Bus parcelBus = Bus.CREATOR.createFromParcel(parcel);

        //Bus parcelBus = new Bus(parcel);
        String builder = "";
        for (int i = 0; i < parcelBus.getSeatNumbers().size(); i++) {
            for (int j = 0; j < parcelBus.getSeatNumbers().get(i).size(); j++) {
                builder += parcelBus.getSeatNumbers().get(i).get(j) + " | ";
            }
            builder += "\n";
        }
        System.out.println("parcel bus: " + builder);

        parcel.recycle(); */
    }
}
