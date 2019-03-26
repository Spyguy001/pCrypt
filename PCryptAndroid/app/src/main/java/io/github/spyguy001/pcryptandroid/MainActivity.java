package io.github.spyguy001.pcryptandroid;

import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import android.util.Base64;

/**
 * A password generation screen that offers passwords based on text and secret codes.
 */
public class MainActivity extends AppCompatActivity{

    // UI references.
    private AutoCompleteTextView mainTextString;
    private EditText secretCode;
    private View mLoginFormView;
    private TextView pwText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Set up the login form.
        mainTextString = (AutoCompleteTextView) findViewById(R.id.email);

        secretCode = (EditText) findViewById(R.id.password);
        pwText = (TextView) findViewById(R.id.pwText);
        secretCode.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        // Reset errors.
        mainTextString.setError(null);
        secretCode.setError(null);

        // Store values at the time of the login attempt.
        String mainText = mainTextString.getText().toString();
        String secretCodeText = secretCode.getText().toString();

        boolean cancel = false;
        View focusView = null;

        /* Check for a valid secretCodeText, if the user entered one.
        if (!TextUtils.isEmpty(secretCodeText) && secretCodeText.length() < 6) {
            secretCode.setError(getString(R.string.error_invalid_password));
            focusView = secretCode;
            cancel = true;
        }

        // Check for a valid mainText address.
        if (TextUtils.isEmpty(mainText)) {
            mainTextString.setError(getString(R.string.error_field_required));
            focusView = mainTextString;
            cancel = true;
        } else if (mainText.length() < 6) {
            mainTextString.setError(getString(R.string.error_invalid_email));
            focusView = mainTextString;
            cancel = true;
        }*/

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            String t1 = mainText;
            String t2 = secretCodeText;
            String password = mainText + secretCodeText;
            MessageDigest digest = null;
            byte[] byteData = null;
            try {
                digest = MessageDigest.getInstance("SHA-256");
                byteData = digest.digest(password.getBytes("UTF-8"));
                digest.reset();
            }
            catch (Exception e){
                e.printStackTrace();
                return;
            }
            String st1 = Base64.encodeToString(byteData, Base64.DEFAULT);
            pwText.setText(getString(R.string.give_password, st1.substring(0 ,12)));
        }
    }
}

