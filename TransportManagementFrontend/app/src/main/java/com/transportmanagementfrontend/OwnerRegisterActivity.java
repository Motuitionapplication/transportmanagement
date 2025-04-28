package com.transportmanagementfrontend;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OwnerRegisterActivity extends AppCompatActivity {

    // Page containers & navigation
    private FrameLayout pagePersonal, pageAddress, pageAccount, pageVehicle;
    private Button btnPrev, btnNext, btnSkip, btnSave;
    private int currentPage = 1;

    // PERSONAL fields
    private TextInputEditText etFirstName, etLastName, etPhone, etUsername, etPassword, etFatherName, etEmail,  etAddressProofType, etAddressProofNumber,
            etIdentityProofType, etIdentityProofNumber;;
    private TextInputLayout loFirstName, loLastName, loPhone, loUsername, loPassword, loFatherName, loEmail;

    // ADDRESS fields
    private TextInputEditText etPresAt, etPresPo,
            etPresTown, etPresPs, etPresDist, etPresState, etPresPin, etPresMob, etPresType;
    private TextInputEditText etPermAt, etPermPo,
            etPermTown, etPermPs, etPermDist, etPermState, etPermPin, etPermMob, etPermType;
    private CheckBox cbSameAsPresent;

    // ACCOUNT fields (optional)
    private TextInputEditText etAccountNumber, etBranchName, etIfscCode, etBranchAddress, etUpiNumber, etGst;
    private TextInputLayout loAccountNumber, loBranchName, loIfscCode, loBranchAddress, loUpiNumber, loGst;

    // VEHICLE fields
    private TextInputEditText etVehicleNumber, etVehicleType, etChassisNumber,
            etInsurancePaper, etFitnessCert, etPermit, etPollutionCert, etRc, etCapacity;
    private TextInputLayout loVehicleNumber, loVehicleType, loChassisNumber,
            loInsurancePaper, loFitnessCert, loPermit, loPollutionCert, loRc, loCapacity;

    private final String BASE_URL = "http://10.0.2.2:8080/api/owners/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_register);

        bindViews();
        showPage(1);

        btnNext.setOnClickListener(v -> {
            if (validatePage(currentPage)) {
                showPage(++currentPage);
            }
        });
        btnPrev.setOnClickListener(v -> showPage(--currentPage));
        btnSkip.setOnClickListener(v -> {
            currentPage = 4;
            showPage(currentPage);
        });
        btnSave.setOnClickListener(v -> {
            if (validatePage(currentPage)) {
                collectAndSubmit();
            }
        });

        cbSameAsPresent.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                copyPresentToPermanent();
                setPermanentFieldsVisibility(false);
            } else {
                setPermanentFieldsVisibility(true);
            }
        });
    }

    private void bindViews() {
        pagePersonal = findViewById(R.id.page_personal);
        pageAddress = findViewById(R.id.page_address);
        pageAccount = findViewById(R.id.page_account);
        pageVehicle = findViewById(R.id.page_vehicle);
        btnPrev = findViewById(R.id.btnPrev);
        btnNext = findViewById(R.id.btnNext);
        btnSkip = findViewById(R.id.btnSkip);
        btnSave = findViewById(R.id.btnSave);

        loFirstName = findViewById(R.id.firstNameLayout);
        loLastName = findViewById(R.id.lastNameLayout);
        loPhone = findViewById(R.id.phoneLayout);
        loUsername = findViewById(R.id.usernameLayout);
        loPassword = findViewById(R.id.passwordLayout);
        loFatherName = findViewById(R.id.fatherNameLayout);
        loEmail = findViewById(R.id.emailLayout);
        etFirstName = findViewById(R.id.firstName);
        etLastName = findViewById(R.id.lastName);
        etPhone = findViewById(R.id.phone);
        etUsername = findViewById(R.id.username);
        etPassword = findViewById(R.id.password);
        etFatherName = findViewById(R.id.fatherName);
        etEmail = findViewById(R.id.email);
        etAddressProofType = findViewById(R.id.addressProofType);
        etAddressProofNumber = findViewById(R.id.addressProofNumber);
        etIdentityProofType = findViewById(R.id.identityProofType);
        etIdentityProofNumber = findViewById(R.id.identityProofNumber);

        cbSameAsPresent = findViewById(R.id.cbSameAsPresent);
        etPresAt = findViewById(R.id.presAt);
        etPresPo = findViewById(R.id.presPo);
        etPresTown = findViewById(R.id.presTown);
        etPresPs = findViewById(R.id.presPs);
        etPresDist = findViewById(R.id.presDist);
        etPresState = findViewById(R.id.presState);
        etPresPin = findViewById(R.id.presPin);
        etPresMob = findViewById(R.id.presMob);
        etPresType = findViewById(R.id.presType);
        etPermAt = findViewById(R.id.permAt);
        etPermPo = findViewById(R.id.permPo);
        etPermTown = findViewById(R.id.permTown);
        etPermPs = findViewById(R.id.permPs);
        etPermDist = findViewById(R.id.permDist);
        etPermState = findViewById(R.id.permState);
        etPermPin = findViewById(R.id.permPin);
        etPermMob = findViewById(R.id.permMob);
        etPermType = findViewById(R.id.permType);

        loAccountNumber = findViewById(R.id.accountNumberLayout);
        loBranchName = findViewById(R.id.branchNameLayout);
        loIfscCode = findViewById(R.id.ifscLayout);
        loBranchAddress = findViewById(R.id.branchAddressLayout);
        loUpiNumber = findViewById(R.id.upiLayout);
        loGst = findViewById(R.id.gstLayout);
        etAccountNumber = findViewById(R.id.accountNumber);
        etBranchName = findViewById(R.id.branchName);
        etIfscCode = findViewById(R.id.ifscCode);
        etBranchAddress = findViewById(R.id.branchAddress);
        etUpiNumber = findViewById(R.id.upiNumber);
        etGst = findViewById(R.id.gst);

        loVehicleNumber = findViewById(R.id.vehicleNumberLayout2);
        loVehicleType = findViewById(R.id.vehicleTypeLayout);
        loChassisNumber = findViewById(R.id.chassisNumberLayout);
        loInsurancePaper = findViewById(R.id.insurancePaperLayout);
        loFitnessCert = findViewById(R.id.fitnessCertLayout);
        loPermit = findViewById(R.id.permitLayout);
        loPollutionCert = findViewById(R.id.pollutionCertLayout);
        loRc = findViewById(R.id.rcLayout);
        loCapacity = findViewById(R.id.capacityLayout);
        etVehicleNumber = findViewById(R.id.vehicleNumber2);
        etVehicleType = findViewById(R.id.vehicleType);
        etChassisNumber = findViewById(R.id.chassisNumber);
        etInsurancePaper = findViewById(R.id.insurancePaper);
        etFitnessCert = findViewById(R.id.fitnessCert);
        etPermit = findViewById(R.id.permit);
        etPollutionCert = findViewById(R.id.pollutionCert);
        etRc = findViewById(R.id.rc);
        etCapacity = findViewById(R.id.capacity);
    }

    private void showPage(int page) {
        currentPage = page;
        pagePersonal.setVisibility(page == 1 ? View.VISIBLE : View.GONE);
        pageAddress.setVisibility(page == 2 ? View.VISIBLE : View.GONE);
        pageAccount.setVisibility(page == 3 ? View.VISIBLE : View.GONE);
        pageVehicle.setVisibility(page == 4 ? View.VISIBLE : View.GONE);

        btnPrev.setVisibility(page > 1 ? View.VISIBLE : View.GONE);
        btnNext.setVisibility(page < 4 ? View.VISIBLE : View.GONE);
        btnSkip.setVisibility(page == 3 ? View.VISIBLE : View.GONE);
        btnSave.setVisibility(page == 4 ? View.VISIBLE : View.GONE);
    }

    private boolean validatePage(int page) {
        switch (page) {
            case 1:
                return validateText(etFirstName, loFirstName, "Required")
                        & validateText(etLastName, loLastName, "Required")
                        & validatePhone()
                        & validateText(etUsername, loUsername, "Required")
                        & validateText(etPassword, loPassword, "Min 6 chars", 6)
                        & validateText(etFatherName, loFatherName, "Required")
                        & validateEmail();
            case 2:
                boolean ok = true;
                for (TextInputEditText e : new TextInputEditText[]{
                        etPresAt, etPresPo,
                        etPresTown, etPresPs, etPresDist, etPresState,
                        etPresPin, etPresMob, etPresType}) {
                    ok &= validateText(e, null, "Required");
                }
                if (!cbSameAsPresent.isChecked()) {
                    for (TextInputEditText e : new TextInputEditText[]{
                            etPermAt, etPermPo,
                            etPermTown, etPermPs, etPermDist, etPermState,
                            etPermPin, etPermMob, etPermType}) {
                        ok &= validateText(e, null, "Required");
                    }
                }
                return ok;
            case 3:
                return true; // optional
            case 4:
                boolean ok2 = true;
                for (TextInputEditText e : new TextInputEditText[]{
                        etVehicleNumber, etVehicleType, etChassisNumber,
                        etInsurancePaper, etFitnessCert, etPermit,
                        etPollutionCert, etRc, etCapacity}) {
                    ok2 &= validateText(e, null, "Required");
                }
                return ok2;
        }
        return true;
    }

    private boolean validateText(EditText et, TextInputLayout layout, String errMsg) {
        if (TextUtils.isEmpty(et.getText())) {
            if (layout != null) layout.setError(errMsg);
            return false;
        }
        if (layout != null) layout.setError(null);
        return true;
    }

    private boolean validateText(EditText et, TextInputLayout layout, String errMsg, int minLen) {
        if (et.getText().length() < minLen) {
            if (layout != null) layout.setError(errMsg);
            return false;
        }
        if (layout != null) layout.setError(null);
        return true;
    }

    private boolean validatePhone() {
        String p = etPhone.getText().toString().trim();
        if (!p.matches("\\d{10}")) {
            loPhone.setError("10 digits");
            return false;
        }
        loPhone.setError(null);
        return true;
    }

    private boolean validateEmail() {
        String email = etEmail.getText().toString().trim();
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            loEmail.setError("Invalid email");
            return false;
        }
        loEmail.setError(null);
        return true;
    }

    private void copyPresentToPermanent() {
        etPermAt.setText(etPresAt.getText());
        etPermPo.setText(etPresPo.getText());
        etPermTown.setText(etPresTown.getText());
        etPermPs.setText(etPresPs.getText());
        etPermDist.setText(etPresDist.getText());
        etPermState.setText(etPresState.getText());
        etPermPin.setText(etPresPin.getText());
        etPermMob.setText(etPresMob.getText());
        etPermType.setText(etPresType.getText());
    }

    private void setPermanentFieldsVisibility(boolean visible) {
        int v = visible ? View.VISIBLE : View.GONE;
        findViewById(R.id.permAtLayout).setVisibility(v);
        findViewById(R.id.permPoLayout).setVisibility(v);
        findViewById(R.id.permTownLayout).setVisibility(v);
        findViewById(R.id.permPsLayout).setVisibility(v);
        findViewById(R.id.permDistLayout).setVisibility(v);
        findViewById(R.id.permStateLayout).setVisibility(v);
        findViewById(R.id.permPinLayout).setVisibility(v);
        findViewById(R.id.permMobLayout).setVisibility(v);
        findViewById(R.id.permTypeLayout).setVisibility(v);
    }

    private void collectAndSubmit() {
        // Personal
        String fn = etFirstName.getText().toString().trim();
        String ln = etLastName.getText().toString().trim();
        String ph = etPhone.getText().toString().trim();
        String fa = etFatherName.getText().toString().trim();
        String em = etEmail.getText().toString().trim();
        String un = etUsername.getText().toString().trim();
        String pw = etPassword.getText().toString().trim();
        String role = "owner";
        String addressProofType = etAddressProofType.getText().toString().trim();
        String addressProofNumber = etAddressProofNumber.getText().toString().trim();
        String identityProofType = etIdentityProofType.getText().toString().trim();
        String identityProofNumber = etIdentityProofNumber.getText().toString().trim();


        // Present Address
        String presAt = etPresAt.getText().toString().trim();
        String presPo = etPresPo.getText().toString().trim();
        String presTown = etPresTown.getText().toString().trim();
        String presPs = etPresPs.getText().toString().trim();
        String presDist = etPresDist.getText().toString().trim();
        String presState = etPresState.getText().toString().trim();
        String presPin = etPresPin.getText().toString().trim();
        String presMob = etPresMob.getText().toString().trim();
        String presType = etPresType.getText().toString().trim();

        // Permanent Address
        String permAt = etPermAt.getText().toString().trim();
        String permPo = etPermPo.getText().toString().trim();
        String permTown = etPermTown.getText().toString().trim();
        String permPs = etPermPs.getText().toString().trim();
        String permDist = etPermDist.getText().toString().trim();
        String permState = etPermState.getText().toString().trim();
        String permPin = etPermPin.getText().toString().trim();
        String permMob = etPermMob.getText().toString().trim();
        String permType = etPermType.getText().toString().trim();

        // Account (optional)
        String accountNumber = etAccountNumber.getText().toString().trim();
        String branchName = etBranchName.getText().toString().trim();
        String ifscCode = etIfscCode.getText().toString().trim();
        String branchAddress = etBranchAddress.getText().toString().trim();
        String upiNumber = etUpiNumber.getText().toString().trim();
        String gst = etGst.getText().toString().trim();

        // Vehicle
        String vehicleNumber = etVehicleNumber.getText().toString().trim();
        String vehicleType = etVehicleType.getText().toString().trim();
        String chassisNumber = etChassisNumber.getText().toString().trim();
        String insurancePaper = etInsurancePaper.getText().toString().trim();
        String fitnessCert = etFitnessCert.getText().toString().trim();
        String permit = etPermit.getText().toString().trim();
        String pollutionCert = etPollutionCert.getText().toString().trim();
        String rc = etRc.getText().toString().trim();
        String capacity = etCapacity.getText().toString().trim();

        OwnerRegisterRequest req = new OwnerRegisterRequest(
                fn, ln, ph, fa, em, un, pw, role,
                addressProofType, addressProofNumber, identityProofType, identityProofNumber,
                presAt, presPo,
                presTown, presPs, presDist, presState,
                presPin, presMob, presType,
                 permAt, permPo,
                permTown, permPs, permDist, permState,
                permPin, permMob, permType,
                accountNumber, branchName, ifscCode,
                branchAddress, upiNumber, gst,
                vehicleNumber, vehicleType, chassisNumber,
                insurancePaper, fitnessCert, permit,
                pollutionCert, rc, capacity
        );

        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(retrofit2.converter.scalars.ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        ApiService api = retrofit.create(ApiService.class);

        api.registerOwner(req).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> res) {
                if (res.isSuccessful()) {
                    Toast.makeText(OwnerRegisterActivity.this, "Registered!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(OwnerRegisterActivity.this, OwnerHomeActivity.class)
                            .putExtra("FIRST_NAME", fn));
                    finish();
                } else {
                    // Log the HTTP code and error body
                    int code = res.code();
                    String errorBody = "‹empty›";
                    try {
                        if (res.errorBody() != null) {
                            errorBody = res.errorBody().string();
                        }
                    } catch (IOException e) {
                        Log.e("OwnerReg", "Error reading errorBody", e);
                    }
                    Log.e("OwnerReg", "Registration failed, HTTP " + code + ": " + errorBody);
                    Toast.makeText(OwnerRegisterActivity.this,
                            "Registration failed: HTTP " + code,
                            Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e("OwnerReg", "Network error", t);
                Toast.makeText(OwnerRegisterActivity.this,
                        "Network error: " + t.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        });
    }
}

