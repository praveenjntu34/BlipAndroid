package com.at2t.blipandroid.view.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.at2t.blipandroid.R;
import com.at2t.blipandroid.utils.BlipUtility;
import com.at2t.blipandroid.utils.Constants;
import com.at2t.blipandroid.utils.CustomButton;
import com.msg91.sendotpandroid.library.internal.SendOTP;
import com.msg91.sendotpandroid.library.listners.VerificationListener;
import com.msg91.sendotpandroid.library.roots.RetryType;
import com.msg91.sendotpandroid.library.roots.SendOTPConfigBuilder;
import com.msg91.sendotpandroid.library.roots.SendOTPResponseCode;


public class LoginUsingOtpActivity extends AppCompatActivity implements VerificationListener,
        View.OnClickListener {

    public static final String TAG = "LoginUsingOtp";
    private boolean isFirstParentLogin;
    private CustomButton customButton;
    private LinearLayout llLoginTopHeader;
    private TextView tvOtpMessage;
    private String mobileNumber;
    private int sectionId;
    private TextView tvResendOtpTextView;
    private TextView timerTextView;
    private String resendText;
    private CountDownTimer countDownTimer;

    private EditText otp1EditText;
    private EditText otp2EditText;
    private EditText otp3EditText;
    private EditText otp4EditText;
    private String userType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_otp);

        mobileNumber = getIntent().getStringExtra(Constants.MOBILE_NUMBER);
        isFirstParentLogin = BlipUtility.getIsParentFirstLoginId(this);

        SendOTP.initializeApp(getApplication());
        initializeViews();
        changeOtpScreenColor();

        otp1EditText = findViewById(R.id.pin1);
        otp2EditText = findViewById(R.id.pin2);
        otp3EditText = findViewById(R.id.pin3);
        otp4EditText = findViewById(R.id.pin4);

        addTextChangedListenerForTextWatcher(otp1EditText);
        addTextChangedListenerForTextWatcher(otp2EditText);
        addTextChangedListenerForTextWatcher(otp3EditText);
        addTextChangedListenerForTextWatcher(otp4EditText);

        otp1EditText.requestFocus();

        customButton.setOnClickListener(this);
        tvResendOtpTextView.setOnClickListener(this);

        setData();
        createVerification();
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    private void setData() {
        resendText = getApplicationContext().getResources().getString(R.string.didn_t_get_code);
        setText();
    }

    private void setText() {
        SpannableString spannableString = new SpannableString(resendText);
        int index = resendText.indexOf("Resend");
        spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.resend_color)), index, index + 6, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvResendOtpTextView.setText(spannableString);
    }

    private void addTextChangedListenerForTextWatcher(EditText editTextView) {
        editTextView.addTextChangedListener(new PinTextWatcher(editTextView));
        editTextView.setSelection(0);
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        SendOTP.getInstance().getTrigger().stop();
    }

    private void changeOtpScreenColor() {
        int instructorId = BlipUtility.getInstructorId(getApplicationContext());
        if (instructorId != 0) {
            customButton.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));
            llLoginTopHeader.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));
            tvResendOtpTextView.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
        } else {
            customButton.setBackgroundColor(ContextCompat.getColor(this, R.color.colorGreen));
            llLoginTopHeader.setBackgroundColor(ContextCompat.getColor(this, R.color.colorGreen));
            tvResendOtpTextView.setTextColor(ContextCompat.getColor(this, R.color.colorGreen));
        }
    }

    private void initializeViews() {
        customButton = findViewById(R.id.btnLogin);
        llLoginTopHeader = findViewById(R.id.llLogin);
        tvOtpMessage = findViewById(R.id.tvOtpSubtitle);
        tvResendOtpTextView = findViewById(R.id.resend_otp);
        timerTextView = findViewById(R.id.timer);
        tvOtpMessage.setText(this.getString(R.string.otp_text, mobileNumber.substring(mobileNumber.length() - 3)));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }

    public void verifyOtp(String otp) {
        SendOTP.getInstance().getTrigger().verify(otp);
    }

    private void createVerification() {
        new SendOTPConfigBuilder()
                .setCountryCode(91)
                .setMobileNumber(mobileNumber)
                .setAutoVerification(LoginUsingOtpActivity.this)//Auto read otp from Sms And Verify
                .setSenderId("ABCDEF")
                .setMessage("##OTP## is Your verification digits.")
                .setOtpLength(4)
                .setVerificationCallBack(this).build();

        SendOTP.getInstance().getTrigger().initiate();
    }

    public void resendCode() {
        startTimer();
        SendOTP.getInstance().getTrigger().resend(RetryType.TEXT);
        otp4EditText.setText("");
        otp3EditText.setText("");
        otp2EditText.setText("");
        otp1EditText.setText("");
    }

    private void startTimer() {
        tvResendOtpTextView.setClickable(false);
        new CountDownTimer(30000, 1000) {
            int secondsLeft = 0;

            public void onTick(long ms) {
                if (Math.round((float) ms / 1000.0f) != secondsLeft) {
                    secondsLeft = Math.round((float) ms / 1000.0f);
                    String text = "Please wait! Resend in " + secondsLeft + " sec";
                    tvResendOtpTextView.setText(text);
                }
            }

            public void onFinish() {
                tvResendOtpTextView.setClickable(true);
                setText();
            }
        }.start();
    }

    @Override
    public void onSendOtpResponse(final SendOTPResponseCode responseCode, final String message) {
        Log.e(TAG, "onSendOtpResponse: " + responseCode.getCode() + " " + responseCode + " " + message);
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {

                if (responseCode == SendOTPResponseCode.DIRECT_VERIFICATION_SUCCESSFUL_FOR_NUMBER || responseCode == SendOTPResponseCode.OTP_VERIFIED) {
                    userType = BlipUtility.getRole(getApplicationContext());
                    if (userType.equals("Parent") && isFirstParentLogin) {
                        launchParentRegistrationScreen();
                    } else {
                        otpVerified();
                    }
                } else if (responseCode == SendOTPResponseCode.READ_OTP_SUCCESS) {
                    setOTP(message);
                    verifyOtp(message);

                } else if (responseCode == SendOTPResponseCode.SMS_SUCCESSFUL_SEND_TO_NUMBER || responseCode == SendOTPResponseCode.DIRECT_VERIFICATION_FAILED_SMS_SUCCESSFUL_SEND_TO_NUMBER) {

                } else if (responseCode == SendOTPResponseCode.NO_INTERNET_CONNECTED) {
                    Toast.makeText(getApplicationContext(), "Please check internet connection", Toast.LENGTH_SHORT).show();
                } else if(responseCode == SendOTPResponseCode.SERVER_ERROR_OTP_NOT_VERIFIED) {
                    Toast.makeText(getApplicationContext(), "Invalid OTP, Please try again", Toast.LENGTH_SHORT).show();
                } else {
                    Log.d(TAG, "run: failed SendOTP response" + responseCode.getCode());
                }
            }


//                if (message != null) {
//                    if (responseCode == SendOTPResponseCode.DIRECT_VERIFICATION_SUCCESSFUL_FOR_NUMBER || responseCode == SendOTPResponseCode.OTP_VERIFIED) {
//                        if (BlipUtility.getIsParentFirstLoginId(getApplicationContext())) {
//                            launchParentRegistrationScreen();
//                        } else {
//                            otpVerified();
//                        }
//                    } else if (responseCode == SendOTPResponseCode.READ_OTP_SUCCESS) {
//                        if (otpTextView.getOTP() != null) {
//                            otpTextView.setOTP(message);
//                        }
//                    } else if (responseCode == SendOTPResponseCode.SMS_SUCCESSFUL_SEND_TO_NUMBER || responseCode == SendOTPResponseCode.DIRECT_VERIFICATION_FAILED_SMS_SUCCESSFUL_SEND_TO_NUMBER) {
//                        Log.e(TAG, "run: send message");
////                    if(responseCode == SendOTPResponseCode.OTP_VERIFIED) {
////                        otpVerified();
////                    }
//                    } else if (responseCode == SendOTPResponseCode.NO_INTERNET_CONNECTED) {
//                        Toast.makeText(getApplicationContext(), "Please check internet connection", Toast.LENGTH_SHORT).show();
//                    } else {
//                        //TODO need to change later
////                    otpVerified();
//                        Log.e(TAG, "run: send message error : " + responseCode.getCode());
////                    Toast.makeText(getApplicationContext(), "Invalid OTP, Please try again", Toast.LENGTH_SHORT).show();
//                    }
//                }

        });
    }

    private void launchParentRegistrationScreen() {
        Intent intent = new Intent(this, UserRegistrationActivity.class);
        startActivity(intent);
        finish();
    }

    private void otpVerified() {
        Intent intent = new Intent(this, MainDashboardActivity.class);
        startActivity(intent);
        finish();
    }

    public String getOTPtext() {
        return otp1EditText.getText().toString() + "" +
                otp2EditText.getText().toString() + "" +
                otp3EditText.getText().toString() + "" +
                otp4EditText.getText().toString();
    }

    private void setOTP(String s) {
        if (s.length() == 4) {
            otp1EditText.setText(s.charAt(0) + "");
            otp2EditText.setText(s.charAt(1) + "");
            otp3EditText.setText(s.charAt(2) + "");
            otp4EditText.setText(s.charAt(3) + "");
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnLogin) {
            if (getOTPtext().length() == 4) {
                verifyOtp(getOTPtext().trim());
            } else {
                Toast.makeText(this, "Please enter all fields", Toast.LENGTH_SHORT).show();
            }
        } else if (view.getId() == R.id.resend_otp) {
            Log.e(TAG, "onClick: resend otp");
            resendCode();
        }
    }

    public class PinTextWatcher implements TextWatcher {
        private EditText editTextView;


        PinTextWatcher(EditText editTextView) {
            this.editTextView = editTextView;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (s != null && s.length() >= 0) {
                int len = s.length();
                otp1EditText.setFocusableInTouchMode(false);
                otp2EditText.setFocusableInTouchMode(false);
                otp3EditText.setFocusableInTouchMode(false);
                otp4EditText.setFocusableInTouchMode(false);
                switch (editTextView.getId()) {
                    case R.id.pin1:
                        otp1EditText.setFocusableInTouchMode(true);
                        if (len == 1) {
                            otp2EditText.setFocusableInTouchMode(true);
                            otp2EditText.requestFocus();
                        }
                        break;
                    case R.id.pin2:
                        otp2EditText.setFocusableInTouchMode(true);
                        if (len == 0) {
                            otp1EditText.setFocusableInTouchMode(true);
                            otp1EditText.requestFocus();
                        } else {
                            otp3EditText.setFocusableInTouchMode(true);
                            otp3EditText.requestFocus();
                        }
                        break;
                    case R.id.pin3:
                        otp3EditText.setFocusableInTouchMode(true);
                        if (len == 1) {
                            otp4EditText.setFocusableInTouchMode(true);
                            otp4EditText.requestFocus();
                        } else {
                            otp2EditText.setFocusableInTouchMode(true);
                            otp2EditText.requestFocus();
                        }
                        break;
                    case R.id.pin4:
                        otp4EditText.setFocusableInTouchMode(true);
                        if (len == 0) {
                            otp3EditText.setFocusableInTouchMode(true);
                            otp3EditText.requestFocus();
                        }
                        break;
                }

            }

        }

    }

}
