package jorgereina1986.c4q.nyc.androidfinalproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.list_view);
        searchField = (EditText) findViewById(R.id.search_input);
        String searchInput;
        searchInput = searchField.getText().toString();


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
                Log.d(TAG, "first: " + response.body().getResults().get(0).getArtistName());
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e(TAG, "error: " + t);
            }
        });



        List<String> list = new ArrayList<>();
        list.add("hello");
        list.add("world");
        list.add("java");
        list.add("face");
        list.add("book");
        list.add("brook");
        list.add("crook");

        listView.setAdapter(new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, list));


    }
}