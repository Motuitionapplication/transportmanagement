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



import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

        apiService = RetrofitClient.getClient().create(ApiService.class);

        // Initialize all UI references
        initializeFields();

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);

        // Get ownerId if passed (edit mode)
        String ownerIdStr = getIntent().getStringExtra("ownerId");
        if (!TextUtils.isEmpty(ownerIdStr)) {
            try {
                ownerId = Integer.parseInt(ownerIdStr);
            } catch (NumberFormatException ignored) {
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
        Owner owner = new Owner();

        // Personal Info
        owner.setFirstName(getText(firstName));
        owner.setLastName(getText(lastName));
        owner.setPhone(getText(phone));
        owner.setUsername(getText(username));
        owner.setFatherName(getText(fatherName));
        owner.setEmail(getText(email));

        // Present Address
        owner.setPresProofType(getText(presProofType));
        owner.setPresProofNumber(getText(presProofNumber));
        owner.setPresAt(getText(presAt));
        owner.setPresPo(getText(presPo));
        owner.setPresTown(getText(presTown));
        owner.setPresPs(getText(presPs));
        owner.setPresDist(getText(presDist));
        owner.setPresState(getText(presState));
        owner.setPresPin(getText(presPin));
        owner.setPresMob(getText(presMob));
        owner.setPresType(getText(presType));

        // Permanent Address
        owner.setPermProofType(getText(permProofType));
        owner.setPermProofNumber(getText(permProofNumber));
        owner.setPermAt(getText(permAt));
        owner.setPermPo(getText(permPo));
        owner.setPermTown(getText(permTown));
        owner.setPermPs(getText(permPs));
        owner.setPermDist(getText(permDist));
        owner.setPermState(getText(permState));
        owner.setPermPin(getText(permPin));
        owner.setPermMob(getText(permMob));
        owner.setPermType(getText(permType));

        // Account Info
        owner.setAccountNumber(getText(accountNumber));
        owner.setBranchName(getText(branchName));
        owner.setIfscCode(getText(ifscCode));
        owner.setBranchAddress(getText(branchAddress));
        owner.setUpiNumber(getText(upiNumber));
        owner.setGst(getText(gst));

        // Vehicle Info
        owner.setVehicleNumber(getText(vehicleNumber));
        owner.setVehicleType(getText(vehicleType));
        owner.setChassisNumber(getText(chassisNumber));
        owner.setInsurancePaper(getText(insurancePaper));
        owner.setFitnessCert(getText(fitnessCert));
        owner.setPermit(getText(permit));
        owner.setPollutionCert(getText(pollutionCert));
        owner.setRc(getText(rc));

        // Convert capacity to int if not empty
        String capText = getText(capacity);
        if (!capText.isEmpty()) {
            owner.setCapacity(String.valueOf(Integer.parseInt(capText)));
        }

        OwnerRegisterRequest request = new OwnerRegisterRequest();
        request.setOwner(owner);
        return request;
    }

    // Helper to get trimmed text
    private String getText(EditText field) {
        return field.getText().toString().trim();
    }


    private void populateFields(int ownerId) {
        progressDialog.show();
        apiService.getOwnerById(ownerId).enqueue(new Callback<Owner>() {
            @Override
            public void onResponse(Call<Owner> call, Response<Owner> response) {
                progressDialog.dismiss();
                if (response.isSuccessful() && response.body() != null) {
                    Owner owner = response.body();

                    // Personal Info
                    setTextSafe(firstName, owner.getFirstName());
                    setTextSafe(lastName, owner.getLastName());
                    setTextSafe(phone, owner.getPhone());
                    setTextSafe(username, owner.getUsername());
                    setTextSafe(fatherName, owner.getFatherName());
                    setTextSafe(email, owner.getEmail());

                    // Present Address
                    setTextSafe(presProofType, owner.getPresProofType());
                    setTextSafe(presProofNumber, owner.getPresProofNumber());
                    setTextSafe(presAt, owner.getPresAt());
                    setTextSafe(presPo, owner.getPresPo());
                    setTextSafe(presTown, owner.getPresTown());
                    setTextSafe(presPs, owner.getPresPs());
                    setTextSafe(presDist, owner.getPresDist());
                    setTextSafe(presState, owner.getPresState());
                    setTextSafe(presPin, owner.getPresPin());
                    setTextSafe(presMob, owner.getPresMob());
                    setTextSafe(presType, owner.getPresType());

                    // Permanent Address
                    setTextSafe(permProofType, owner.getPermProofType());
                    setTextSafe(permProofNumber, owner.getPermProofNumber());
                    setTextSafe(permAt, owner.getPermAt());
                    setTextSafe(permPo, owner.getPermPo());
                    setTextSafe(permTown, owner.getPermTown());
                    setTextSafe(permPs, owner.getPermPs());
                    setTextSafe(permDist, owner.getPermDist());
                    setTextSafe(permState, owner.getPermState());
                    setTextSafe(permPin, owner.getPermPin());
                    setTextSafe(permMob, owner.getPermMob());
                    setTextSafe(permType, owner.getPermType());

                    // Account Info
                    setTextSafe(accountNumber, owner.getAccountNumber());
                    setTextSafe(branchName, owner.getBranchName());
                    setTextSafe(ifscCode, owner.getIfscCode());
                    setTextSafe(branchAddress, owner.getBranchAddress());
                    setTextSafe(upiNumber, owner.getUpiNumber());
                    setTextSafe(gst, owner.getGst());

                    // Vehicle Info
                    setTextSafe(vehicleNumber, owner.getVehicleNumber());
                    setTextSafe(vehicleType, owner.getVehicleType());
                    setTextSafe(chassisNumber, owner.getChassisNumber());
                    setTextSafe(insurancePaper, owner.getInsurancePaper());
                    setTextSafe(fitnessCert, owner.getFitnessCert());
                    setTextSafe(permit, owner.getPermit());
                    setTextSafe(pollutionCert, owner.getPollutionCert());
                    setTextSafe(rc, owner.getRc());

                    // Capacity (convert int to string if needed)
                    if (owner.getCapacity() != null) {
                        capacity.setText(String.valueOf(owner.getCapacity()));
                    } else {
                        capacity.setText("");
                    }

                } else {
                    Toast.makeText(OwnerProfileActivity.this, "Failed to load profile", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Owner> call, Throwable t) {
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
