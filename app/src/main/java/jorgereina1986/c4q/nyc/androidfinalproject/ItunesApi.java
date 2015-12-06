package jorgereina1986.c4q.nyc.androidfinalproject;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by c4q-jorgereina on 12/5/15.
 */
public interface ItunesApi {
    //https://itunes.apple.com/search?media=music&term=beyonce

    @GET("/search")
    Call<SearchResponse> search(@Query("media") String media, @Query("term") String term);
}
