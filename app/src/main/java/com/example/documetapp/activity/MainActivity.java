package com.example.documetapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.documetapp.R;
import com.example.documetapp.adapter.UserListAdapter;
import com.example.documetapp.api.ApiClient;
import com.example.documetapp.model.ResponsModel;
import com.example.documetapp.model.UserList;
import com.example.documetapp.utils.ItemOffsetDecoration;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import com.opencsv.CSVWriter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.documetapp.init.ApplicationAppContext.getAppContext;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static String TAG = MainActivity.class.getName();
    public RecyclerView rv_training_list;
    public UserListAdapter userListAdapter;
    public Button ll_excel, ll_pdf;
    public List<UserList> userList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ll_excel = findViewById(R.id.ll_excel);
        ll_pdf = findViewById(R.id.ll_pdf);

        rv_training_list = findViewById(R.id.rv_users_list);
        rv_training_list.addItemDecoration(new ItemOffsetDecoration(10));
        rv_training_list.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        ll_excel.setOnClickListener(this);
        ll_pdf.setOnClickListener(this);

        getAllList();
    }

    private void getAllList() {
        userList.clear();
        ApiClient.getSingletonApiClient().getAllList(new Callback<ResponsModel>() {
            @Override
            public void onResponse(Call<ResponsModel> call, Response<ResponsModel> response) {
                if (response.isSuccessful()) {
                    for (int i = 0; i < response.body().result.userList.size(); i++)
                        userList.add(response.body().result.userList.get(i));

                    userListAdapter = new UserListAdapter(MainActivity.this, userList);
                    rv_training_list.setAdapter(userListAdapter);
                } else {
                }
            }

            @Override
            public void onFailure(Call<ResponsModel> call, Throwable t) {
                t.getMessage();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_excel:
                callMakeExcel();
                break;
            case R.id.ll_pdf:
                Toast.makeText(this, "Start", Toast.LENGTH_SHORT).show();
                callMakePdf();
                break;
        }
    }

    private void callMakeExcel() {
        CSVWriter writer = null;
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                requestPermissions(new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            }

            String csv = (Environment.getExternalStorageDirectory().getAbsolutePath() + "/download/Csv_" + System.currentTimeMillis() + ".csv");
            writer = new CSVWriter(new FileWriter(csv));

            List<String[]> data = new ArrayList<>();
            data.add(new String[]
                    {
                            "Id",
                            "Name",
                            "Mobile",
                            "Email",
                            "Address",
                            "Details",
                    });
            for (int i = 0; i < userList.size(); i++) {
                data.add(new String[]
                        {
                                userList.get(i).id,
                                userList.get(i).name,
                                userList.get(i).mobile,
                                userList.get(i).email,
                                userList.get(i).address,
                                userList.get(i).detail,
                        });
            }

            writer.writeAll(data); // data is adding to csv
            Log.d(TAG, "CSV " + csv);
            Log.d(TAG, "Writer  " + writer.toString());
            writer.close();

            Toast.makeText(getAppContext(), "Excel Saved in Memory !", Toast.LENGTH_LONG).show();

        } catch (IOException e) {
            Log.d(TAG, "Error  " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void callMakePdf() {

        ArrayList<View> viewArrayList = userListAdapter.getPrintView(); // A function from Adapter class which returns ArrayList of VIEWS
        Document document = new Document(PageSize.A4);
        final File extStore = new File((Environment.getExternalStorageDirectory() + File.separator + "Users"), "Pdf");

        if (!extStore.exists()) {
            extStore.mkdirs();
        }
        String path = extStore.getAbsolutePath() + "/" + System.currentTimeMillis() + ".pdf";
        Log.d(TAG, "Save to: " + path);

        try {
            PdfWriter.getInstance(document, new FileOutputStream(path));
        } catch (DocumentException | FileNotFoundException e) {
            e.printStackTrace();
        }

        for (int im = 0; im < viewArrayList.size(); im++) {
            // Iterate till the last of the array list and add each view individually to the document.
            try {
                viewArrayList.get(im).buildDrawingCache();         //Adding the content to the document
                Bitmap bmp = viewArrayList.get(im).getDrawingCache();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
                Image image = Image.getInstance(stream.toByteArray());
                image.scalePercent(70);
                image.setAlignment(Image.MIDDLE);
                if (!document.isOpen()) {
                    document.open();
                }
                document.add(image);

            } catch (Exception ex) {
                Log.e("TAG-ORDER PRINT ERROR", ex.getMessage());
            }
        }

        if (document.isOpen()) {
            document.close();
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Success")
                .setMessage("PDF File Generated Successfully.")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.ok, (dialog, whichButton) -> {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setDataAndType(Uri.parse(path), "application/pdf");
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                    startActivity(intent);
                }).show();
    }
}