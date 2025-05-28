package com.transportmanagementfrontend;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class OwnerProfileActivity extends AppCompatActivity {

    // Personal Info
    private EditText firstName, lastName, phone, username, fatherName, email;

    // Address Info (Present)
    private EditText presProofType, presProofNumber, presAt, presPo, presTown, presPs, presDist, presState, presPin, presMob, presType;

    // Address Info (Permanent)
    private EditText permProofType, permProofNumber, permAt, permPo, permTown, permPs, permDist, permState, permPin, permMob, permType;

    // Account Info
    private EditText accountNumber, branchName, ifscCode, branchAddress, upiNumber, gst;

    // Vehicle Info
    private EditText vehicleNumber, vehicleType, chassisNumber, insurancePaper, fitnessCert, permit, pollutionCert, rc, capacity;

    private CheckBox cbCopyPresentToPermanent;

    private Integer ownerId = null; // To track existing owner

    // Pages views
    private ViewGroup pagePersonal, pageAddress, pageAccount, pageVehicle;

    // Buttons
    private Button btnEdit, btnNext, btnPrev, btnSave;

    private ApiService apiService;
    private ProgressDialog progressDialog;

    private int currentPage = 0; // Tracks current page
    private static final int TOTAL_PAGES = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_profile);

        Gson gson = new GsonBuilder().setLenient().create();

        String baseUrlOwner = "http://10.0.2.2:5000/api/owners/";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrlOwner)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

         apiService = retrofit.create(ApiService.class);

        // Initialize all UI references
        initializeFields();

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);

        // Get ownerId from intent for edit mode (if present)
        String ownerIdStr = getIntent().getStringExtra("ownerId");
        if (!TextUtils.isEmpty(ownerIdStr)) {
            try {
                ownerId = Integer.parseInt(ownerIdStr);
            } catch (NumberFormatException e) {
                e.printStackTrace(); // Optional: log the error
                ownerId = null; // fallback if parsing fails
            }
        }


        // Checkbox listener to copy present to permanent
        cbCopyPresentToPermanent.setOnCheckedChangeListener((CompoundButton buttonView, boolean isChecked) -> {
            if (isChecked) {
                copyPresentToPermanentAddress();
                setPermanentAddressFieldsEnabled(false);
            } else {
                setPermanentAddressFieldsEnabled(true);
            }
        });

        // Button listeners
        btnEdit.setOnClickListener(v -> onEditClicked());
        btnNext.setOnClickListener(v -> {
            onNextClicked();
        });
        btnPrev.setOnClickListener(v -> {
            onPreviousClicked();
        });
        btnSave.setOnClickListener(v -> onSaveClicked(v));

        // Show first page by default
        showPage(0); // This also calls updateButtonVisibility()

        if (ownerId != null) {
            // Edit mode
            populateFields(ownerId);
            setInputsEnabled(false);             // Disable input fields
            btnSave.setEnabled(false);           // Disable Save button
            btnSave.setVisibility(View.GONE);    // Hide Save button
            btnEdit.setVisibility(View.VISIBLE); // Show Edit button
        } else {
            // New registration mode
            setInputsEnabled(true);              // Enable input fields
            btnSave.setEnabled(true);            // Enable Save
            btnEdit.setVisibility(View.GONE);    // Hide Edit button
            updateButtonVisibility();            // Adjust Save visibility based on initial page
        }
    }


    private void initializeFields() {
        // Personal Info
        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        phone = findViewById(R.id.phone);
        username = findViewById(R.id.username);
        fatherName = findViewById(R.id.fatherName);
        email = findViewById(R.id.email);

        // Present Address
        presProofType = findViewById(R.id.presProofType);
        presProofNumber = findViewById(R.id.presProofNumber);
        presAt = findViewById(R.id.presAt);
        presPo = findViewById(R.id.presPo);
        presTown = findViewById(R.id.presTown);
        presPs = findViewById(R.id.presPs);
        presDist = findViewById(R.id.presDist);
        presState = findViewById(R.id.presState);
        presPin = findViewById(R.id.presPin);
        presMob = findViewById(R.id.presMob);
        presType = findViewById(R.id.presType);

        // Permanent Address
        permProofType = findViewById(R.id.permProofType);
        permProofNumber = findViewById(R.id.permProofNumber);
        permAt = findViewById(R.id.permAt);
        permPo = findViewById(R.id.permPo);
        permTown = findViewById(R.id.permTown);
        permPs = findViewById(R.id.permPs);
        permDist = findViewById(R.id.permDist);
        permState = findViewById(R.id.permState);
        permPin = findViewById(R.id.permPin);
        permMob = findViewById(R.id.permMob);
        permType = findViewById(R.id.permType);

        // Account Info
        accountNumber = findViewById(R.id.accountNumber);
        branchName = findViewById(R.id.branchName);
        ifscCode = findViewById(R.id.ifscCode);
        branchAddress = findViewById(R.id.branchAddress);
        upiNumber = findViewById(R.id.upiNumber);
        gst = findViewById(R.id.gst);

        // Vehicle Info
        vehicleNumber = findViewById(R.id.vehicleNumber);
        vehicleType = findViewById(R.id.vehicleType);
        chassisNumber = findViewById(R.id.chassisNumber);
        insurancePaper = findViewById(R.id.insurancePaper);
        fitnessCert = findViewById(R.id.fitnessCert);
        permit = findViewById(R.id.permit);
        pollutionCert = findViewById(R.id.pollutionCert);
        rc = findViewById(R.id.rc);
        capacity = findViewById(R.id.capacity);

        cbCopyPresentToPermanent = findViewById(R.id.cbSameAsPresent);

        // Pages (wrap each group of fields in layouts with these IDs)
        pagePersonal = findViewById(R.id.page_personal);
        pageAddress = findViewById(R.id.page_address);
        pageAccount = findViewById(R.id.page_account);
        pageVehicle = findViewById(R.id.page_vehicle);

        btnEdit = findViewById(R.id.btnEdit);
        btnNext = findViewById(R.id.btnNext);
        btnPrev = findViewById(R.id.btnPrev);
        btnSave = findViewById(R.id.btnSave);
    }

    private void showPage(int pageIndex) {
        // Hide all pages first
        pagePersonal.setVisibility(View.GONE);
        pageAddress.setVisibility(View.GONE);
        pageAccount.setVisibility(View.GONE);
        pageVehicle.setVisibility(View.GONE);

        // Show requested page
        switch (pageIndex) {
            case 0:
                pagePersonal.setVisibility(View.VISIBLE);
                break;
            case 1:
                pageAddress.setVisibility(View.VISIBLE);
                break;
            case 2:
                pageAccount.setVisibility(View.VISIBLE);
                break;
            case 3:
                pageVehicle.setVisibility(View.VISIBLE);
                break;
            default:
                // Optional: handle invalid pageIndex
                break;
        }

        currentPage = pageIndex;

        // Manage Previous and Next buttons visibility
        btnPrev.setVisibility(pageIndex == 0 ? View.GONE : View.VISIBLE);
        btnNext.setVisibility(pageIndex == TOTAL_PAGES - 1 ? View.GONE : View.VISIBLE);

        // Edit button visible only if inputs disabled and ownerId exists
        btnEdit.setVisibility((!btnSave.isEnabled() && ownerId != null) ? View.VISIBLE : View.GONE);

        // Set Save button text according to whether ownerId is null
        btnSave.setText(ownerId == null ? "Save" : "Update");

        // Update Save button visibility via centralized method
        updateButtonVisibility();
    }

    private void updateButtonVisibility() {
        // Show Save button only on the last page, if enabled
        if (currentPage == TOTAL_PAGES - 1 && btnSave.isEnabled()) {
            btnSave.setVisibility(View.VISIBLE);
        } else {
            btnSave.setVisibility(View.GONE);
        }
    }

    private void onNextClicked() {
        if (currentPage < TOTAL_PAGES - 1) {
            showPage(currentPage + 1);
        }
    }

    private void onPreviousClicked() {
        if (currentPage > 0) {
            showPage(currentPage - 1);
        }
    }

    private void onEditClicked() {
        setInputsEnabled(true);
        btnEdit.setVisibility(View.GONE);
        btnSave.setEnabled(true); // Enable save button
        showPage(currentPage);    // Refresh page to update button visibility
    }

    private void onSaveClicked(View view) {
        // Your existing validation and save logic here
        if (!validateInputs()) {
            return;
        }


    // Show loading indicator
        progressDialog.show();

        // Collect data from input fields
        OwnerRegisterRequest request = collectInputData();

        Call<String> call;
        if (ownerId == null) {
            // Create new owner
            call = apiService.createOwner(request.getOwner());
        } else {
            // Update existing owner
            call = apiService.updateOwner(ownerId, request);
        }

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                progressDialog.dismiss();
                setInputsEnabled(true);

                if (response.isSuccessful()) {
                    Toast.makeText(OwnerProfileActivity.this,
                            ownerId == null ? "Profile created successfully" : "Profile updated successfully",
                            Toast.LENGTH_SHORT).show();

                    // Optionally handle ownerId update here
                } else {
                    String errorMsg = parseErrorResponse(response.errorBody());
                    Toast.makeText(OwnerProfileActivity.this, "Operation failed: " + errorMsg, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                progressDialog.dismiss();
                setInputsEnabled(true);
                Toast.makeText(OwnerProfileActivity.this, "Operation failed: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    // Example validation method - adjust fields and rules as needed
    private boolean validateInputs() {
        //  Validate first name
        EditText firstNameInput = findViewById(R.id.firstName);  // adjust id accordingly
        String firstName = firstNameInput.getText().toString().trim();
        if (firstName.isEmpty()) {
            firstNameInput.setError("First name is required");
            firstNameInput.requestFocus();
            return false;
        }

        // Validate email format
        EditText emailInput = findViewById(R.id.email);
        String email = emailInput.getText().toString().trim();
        if (email.isEmpty()) {
            emailInput.setError("Email is required");
            emailInput.requestFocus();
            return false;
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailInput.setError("Invalid email format");
            emailInput.requestFocus();
            return false;
        }


        return true; // All validations passed
    }

    /**
     * Parses error response body to extract meaningful message
     *
     * @param errorBody ResponseBody from Retrofit error
     * @return parsed error message or fallback string
     */
    private String parseErrorResponse(ResponseBody errorBody) {
        try {
            if (errorBody != null) {
                JSONObject json = new JSONObject(errorBody.string());
                return json.optString("message", "Unknown error");
            }
        } catch (Exception e) {
            return "Error parsing error response";
        }
        return "Unknown error";
    }


    private OwnerRegisterRequest collectInputData() {
        OwnerParameter ownerParameter = new OwnerParameter();

        // Personal Info
        ownerParameter.setFirstName(getText(firstName));
        ownerParameter.setLastName(getText(lastName));
        ownerParameter.setPhone(getText(phone));
        ownerParameter.setUsername(getText(username));
        ownerParameter.setFatherName(getText(fatherName));
        ownerParameter.setEmail(getText(email));

        // Present Address
        ownerParameter.setPresProofType(getText(presProofType));
        ownerParameter.setPresProofNumber(getText(presProofNumber));
        ownerParameter.setPresAt(getText(presAt));
        ownerParameter.setPresPo(getText(presPo));
        ownerParameter.setPresTown(getText(presTown));
        ownerParameter.setPresPs(getText(presPs));
        ownerParameter.setPresDist(getText(presDist));
        ownerParameter.setPresState(getText(presState));
        ownerParameter.setPresPin(getText(presPin));
        ownerParameter.setPresMob(getText(presMob));
        ownerParameter.setPresType(getText(presType));

        // Permanent Address
        ownerParameter.setPermProofType(getText(permProofType));
        ownerParameter.setPermProofNumber(getText(permProofNumber));
        ownerParameter.setPermAt(getText(permAt));
        ownerParameter.setPermPo(getText(permPo));
        ownerParameter.setPermTown(getText(permTown));
        ownerParameter.setPermPs(getText(permPs));
        ownerParameter.setPermDist(getText(permDist));
        ownerParameter.setPermState(getText(permState));
        ownerParameter.setPermPin(getText(permPin));
        ownerParameter.setPermMob(getText(permMob));
        ownerParameter.setPermType(getText(permType));

        // Account Info
        ownerParameter.setAccountNumber(getText(accountNumber));
        ownerParameter.setBranchName(getText(branchName));
        ownerParameter.setIfscCode(getText(ifscCode));
        ownerParameter.setBranchAddress(getText(branchAddress));
        ownerParameter.setUpiNumber(getText(upiNumber));
        ownerParameter.setGst(getText(gst));

        // Vehicle Info
        ownerParameter.setVehicleNumber(getText(vehicleNumber));
        ownerParameter.setVehicleType(getText(vehicleType));
        ownerParameter.setChassisNumber(getText(chassisNumber));
        ownerParameter.setInsurancePaper(getText(insurancePaper));
        ownerParameter.setFitnessCert(getText(fitnessCert));
        ownerParameter.setPermit(getText(permit));
        ownerParameter.setPollutionCert(getText(pollutionCert));
        ownerParameter.setRc(getText(rc));

        // Convert capacity to int if not empty
        String capText = getText(capacity);
        if (!capText.isEmpty()) {
            ownerParameter.setCapacity(String.valueOf(Integer.parseInt(capText)));
        }

        OwnerRegisterRequest request = new OwnerRegisterRequest();
        request.setOwner(ownerParameter);
        return request;
    }

    // Helper to get trimmed text
    private String getText(EditText field) {
        return field.getText().toString().trim();
    }


    private void populateFields(int ownerId) {
        progressDialog.show();
        apiService.getOwnerById(ownerId).enqueue(new Callback<OwnerParameter>() {
            @Override
            public void onResponse(Call<OwnerParameter> call, Response<OwnerParameter> response) {
                progressDialog.dismiss();
                if (response.isSuccessful() && response.body() != null) {
                    OwnerParameter ownerParameter = response.body();

                    // Personal Info
                    setTextSafe(firstName, ownerParameter.getFirstName());
                    setTextSafe(lastName, ownerParameter.getLastName());
                    setTextSafe(phone, ownerParameter.getPhone());
                    setTextSafe(username, ownerParameter.getUsername());
                    setTextSafe(fatherName, ownerParameter.getFatherName());
                    setTextSafe(email, ownerParameter.getEmail());

                    // Present Address
                    setTextSafe(presProofType, ownerParameter.getPresProofType());
                    setTextSafe(presProofNumber, ownerParameter.getPresProofNumber());
                    setTextSafe(presAt, ownerParameter.getPresAt());
                    setTextSafe(presPo, ownerParameter.getPresPo());
                    setTextSafe(presTown, ownerParameter.getPresTown());
                    setTextSafe(presPs, ownerParameter.getPresPs());
                    setTextSafe(presDist, ownerParameter.getPresDist());
                    setTextSafe(presState, ownerParameter.getPresState());
                    setTextSafe(presPin, ownerParameter.getPresPin());
                    setTextSafe(presMob, ownerParameter.getPresMob());
                    setTextSafe(presType, ownerParameter.getPresType());

                    // Permanent Address
                    setTextSafe(permProofType, ownerParameter.getPermProofType());
                    setTextSafe(permProofNumber, ownerParameter.getPermProofNumber());
                    setTextSafe(permAt, ownerParameter.getPermAt());
                    setTextSafe(permPo, ownerParameter.getPermPo());
                    setTextSafe(permTown, ownerParameter.getPermTown());
                    setTextSafe(permPs, ownerParameter.getPermPs());
                    setTextSafe(permDist, ownerParameter.getPermDist());
                    setTextSafe(permState, ownerParameter.getPermState());
                    setTextSafe(permPin, ownerParameter.getPermPin());
                    setTextSafe(permMob, ownerParameter.getPermMob());
                    setTextSafe(permType, ownerParameter.getPermType());

                    // Account Info
                    setTextSafe(accountNumber, ownerParameter.getAccountNumber());
                    setTextSafe(branchName, ownerParameter.getBranchName());
                    setTextSafe(ifscCode, ownerParameter.getIfscCode());
                    setTextSafe(branchAddress, ownerParameter.getBranchAddress());
                    setTextSafe(upiNumber, ownerParameter.getUpiNumber());
                    setTextSafe(gst, ownerParameter.getGst());

                    // Vehicle Info
                    setTextSafe(vehicleNumber, ownerParameter.getVehicleNumber());
                    setTextSafe(vehicleType, ownerParameter.getVehicleType());
                    setTextSafe(chassisNumber, ownerParameter.getChassisNumber());
                    setTextSafe(insurancePaper, ownerParameter.getInsurancePaper());
                    setTextSafe(fitnessCert, ownerParameter.getFitnessCert());
                    setTextSafe(permit, ownerParameter.getPermit());
                    setTextSafe(pollutionCert, ownerParameter.getPollutionCert());
                    setTextSafe(rc, ownerParameter.getRc());

                    // Capacity (convert int to string if needed)
                    if (ownerParameter.getCapacity() != null) {
                        capacity.setText(String.valueOf(ownerParameter.getCapacity()));
                    } else {
                        capacity.setText("");
                    }

                } else {
                    Toast.makeText(OwnerProfileActivity.this, "Failed to load profile", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<OwnerParameter> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(OwnerProfileActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Helper method to safely set text
    private void setTextSafe(EditText editText, String value) {
        editText.setText(value != null ? value : "");
    }


    private void setInputsEnabled(boolean enabled) {
        // Personal Info
        firstName.setEnabled(enabled);
        lastName.setEnabled(enabled);
        phone.setEnabled(enabled);
        username.setEnabled(enabled);
        fatherName.setEnabled(enabled);
        email.setEnabled(enabled);

        // Present Address
        presProofType.setEnabled(enabled);
        presProofNumber.setEnabled(enabled);
        presAt.setEnabled(enabled);
        presPo.setEnabled(enabled);
        presTown.setEnabled(enabled);
        presPs.setEnabled(enabled);
        presDist.setEnabled(enabled);
        presState.setEnabled(enabled);
        presPin.setEnabled(enabled);
        presMob.setEnabled(enabled);
        presType.setEnabled(enabled);

        // Permanent Address (copyPresentToPermanent checkbox state matters)
        setPermanentAddressFieldsEnabled(enabled && !cbCopyPresentToPermanent.isChecked());

        // Account Info
        accountNumber.setEnabled(enabled);
        branchName.setEnabled(enabled);
        ifscCode.setEnabled(enabled);
        branchAddress.setEnabled(enabled);
        upiNumber.setEnabled(enabled);
        gst.setEnabled(enabled);

        // Vehicle Info
        vehicleNumber.setEnabled(enabled);
        vehicleType.setEnabled(enabled);
        chassisNumber.setEnabled(enabled);
        insurancePaper.setEnabled(enabled);
        fitnessCert.setEnabled(enabled);
        permit.setEnabled(enabled);
        pollutionCert.setEnabled(enabled);
        rc.setEnabled(enabled);
        capacity.setEnabled(enabled);

        // Always allow toggling the checkbox
        cbCopyPresentToPermanent.setEnabled(true);

        // Enable/disable Save button
        btnSave.setEnabled(enabled);
    }

    // This method is unchanged
    private void setPermanentAddressFieldsEnabled(boolean enabled) {
        permProofType.setEnabled(enabled);
        permProofNumber.setEnabled(enabled);
        permAt.setEnabled(enabled);
        permPo.setEnabled(enabled);
        permTown.setEnabled(enabled);
        permPs.setEnabled(enabled);
        permDist.setEnabled(enabled);
        permState.setEnabled(enabled);
        permPin.setEnabled(enabled);
        permMob.setEnabled(enabled);
        permType.setEnabled(enabled);
    }
    private void copyPresentToPermanentAddress() {
        permProofType.setText(presProofType.getText().toString());
        permProofNumber.setText(presProofNumber.getText().toString());
        permAt.setText(presAt.getText().toString());
        permPo.setText(presPo.getText().toString());
        permTown.setText(presTown.getText().toString());
        permPs.setText(presPs.getText().toString());
        permDist.setText(presDist.getText().toString());
        permState.setText(presState.getText().toString());
        permPin.setText(presPin.getText().toString());
        permMob.setText(presMob.getText().toString());
        permType.setText(presType.getText().toString());
    }

}
