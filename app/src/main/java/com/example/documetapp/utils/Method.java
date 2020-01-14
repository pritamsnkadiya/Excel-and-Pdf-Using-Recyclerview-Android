package com.example.documetapp.utils;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.text.Html;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import com.example.documetapp.R;
import com.example.documetapp.init.ApplicationAppContext;
import com.example.documetapp.init.SessionManager;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class Method {

    private static final String TAG = Method.class.getSimpleName();
    public static AlertDialog alertDialog;
    private static SessionManager session;

    public static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

    public static boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) ApplicationAppContext.getAppContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static boolean checkPermissions(Context context) {
        int permissionState = ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }

    public static boolean validateFirstName(String firstName) {
        return firstName.matches("[A-Z][a-zA-Z]*");
    }

    // validate last name
    public static boolean validateLastName(String lastName) {
        return lastName.matches("[a-zA-z]+([ '-][a-zA-Z]+)*");
    }

    public final static boolean isValidPhone(CharSequence target) {
        if (target == null) {
            return false;
        } else {

            return Patterns.PHONE.matcher(target)
                    .matches() && (target.length() >= 10 && target.length() <= 20);
        }
    }

    public static void setFragment(Fragment fragment, boolean removeStack, FragmentActivity activity, int mContainer) {
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        FragmentTransaction ftTransaction = fragmentManager.beginTransaction();

        if (removeStack) {
            fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            ftTransaction.replace(mContainer, fragment);
        } else {
            ftTransaction.replace(mContainer, fragment);
        }
        //Log.e("TAG", "Fragment transition is completetd");
        ftTransaction.commit();
    }

    public static boolean checkEmailId(String emailId) {
        return Patterns.EMAIL_ADDRESS.matcher(emailId).matches();
    }

    public static void showToast(Context context, String string) {
        // Toast.makeText(context, Html.fromHtml("<font color='##262162' ><b>" + string + "</b></font>"), Toast.LENGTH_SHORT).show();
        Toast.makeText(context, string, Toast.LENGTH_SHORT).show();
    }

    public static void hide_keyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static void showAlertNew(String title, String message,
                                    final Context context, Activity activity) {

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        //  builder.setMessage(Html.fromHtml("<font color='#FF7F27'></font>"));
        builder.setMessage(Html.fromHtml("<font color='#FFFFFF'>" + message + "</font>"));

        AlertDialog dialog = builder.create();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(context.getColor(R.color.colorPrimaryDark)));

        } else {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(context.getResources().getColor(R.color.colorPrimaryDark)));
        }
        dialog.setButton(DialogInterface.BUTTON_POSITIVE, "Ok", (dialog1, which) -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                dialog1.dismiss();
            }
        });
        dialog.show();
    }

    public static void hideSoftKeyWord(Activity activity) {
        if (activity.getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        }
    }

    public static String getPreferences(Context context, String key) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(key, "");
    }

    public static void savePreferences(Context context, String key, String value) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }
}
