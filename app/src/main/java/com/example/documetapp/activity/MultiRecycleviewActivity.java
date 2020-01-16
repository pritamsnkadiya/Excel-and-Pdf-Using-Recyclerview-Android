package com.example.documetapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.documetapp.R;
import com.example.documetapp.adapter.ParantListAdapter;
import com.example.documetapp.api.ApiClient;
import com.example.documetapp.model.ResponsModel;
import com.example.documetapp.model.UserList;
import com.example.documetapp.utils.ItemOffsetDecoration;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MultiRecycleviewActivity extends AppCompatActivity {

    public static String TAG = MultiRecycleviewActivity.class.getName();
    public RecyclerView rv_parent_list;
    public RecyclerView.Adapter mParantAdapter;
    public List<UserList> userList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_recycleview);

        rv_parent_list = findViewById(R.id.rv_parent_list);
        rv_parent_list.addItemDecoration(new ItemOffsetDecoration(10));
        rv_parent_list.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        getAllArchitecture();
    }

    private void getAllArchitecture() {
        userList.clear();
        ApiClient.getSingletonApiClient().getAllArchitecture(new Callback<ResponsModel>() {
            @Override
            public void onResponse(Call<ResponsModel> call, Response<ResponsModel> response) {
                if (response.isSuccessful()) {
                    mParantAdapter = new ParantListAdapter(MultiRecycleviewActivity.this, response.body().result.aids);
                    rv_parent_list.setAdapter(mParantAdapter);
                }
            }

            @Override
            public void onFailure(Call<ResponsModel> call, Throwable t) {
                t.getMessage();
            }
        });
    }
}
