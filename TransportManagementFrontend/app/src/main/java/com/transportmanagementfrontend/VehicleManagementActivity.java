package com.transportmanagementfrontend;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.*;
import java.util.*;

public class VehicleManagementActivity extends AppCompatActivity {

    RecyclerView recyclerVehicles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_management);

        recyclerVehicles = findViewById(R.id.recyclerVehicles);
        recyclerVehicles.setLayoutManager(new LinearLayoutManager(this));

        loadDummyVehicles();
    }

    private void loadDummyVehicles() {
        List<Vehicle> list = new ArrayList<>();
        list.add(new Vehicle("OD02AB1234", "Available", "Ramesh", "2025-06-21"));
        list.add(new Vehicle("OD05CD5678", "Unavailable", "Suresh", "2025-06-18"));
        list.add(new Vehicle("OD07EF9101", "Reserved", "Naresh", "2025-06-17"));
        list.add(new Vehicle("OR01AX8900", "Available", "Jitendra", "2025-06-15"));

        VehicleAdapter adapter = new VehicleAdapter(list);
        recyclerVehicles.setAdapter(adapter);
    }
}
