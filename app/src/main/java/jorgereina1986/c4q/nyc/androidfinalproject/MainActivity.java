package jorgereina1986.c4q.nyc.androidfinalproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    ListView listView;
    EditText searchField;
    String searchInput;
    Button searchButton;
    List<Result> resultList;
    CustomAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.list_view);
        searchField = (EditText) findViewById(R.id.search_field);
        searchButton = (Button) findViewById(R.id.search_button);
        resultList = new ArrayList<>();
        adapter = new CustomAdapter(this, resultList);
        listView.setAdapter(adapter);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchInput = searchField.getText().toString();
                retrofitConnection();
            }
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    public void retrofitConnection(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://itunes.apple.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ItunesApi itunesApi = retrofit.create(ItunesApi.class);
        Call<SearchResponse> call = itunesApi.search("music", searchInput);
        call.enqueue(new Callback<SearchResponse>() {
            @Override
            public void onResponse(Response<SearchResponse> response, Retrofit retrofit) {
                Log.d(TAG, "response: " + response.body().getResultCount());
                Log.d(TAG, "first: " + response.body().getResults().get(0).getTrackName());

                List<Result> results = response.body().getResults();
                load(results);

            }

            @Override
            public void onFailure(Throwable t) {
                Log.e(TAG, "error: " + t);
            }
        });

    }

    private void load(List<Result> results) {
        resultList.clear();
        resultList.addAll(results);
        adapter.notifyDataSetChanged();
    }
}
