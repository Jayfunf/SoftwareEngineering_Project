package com.example.softwareengineering_project;

import java.util.List;

        import retrofit2.Call;
        import retrofit2.http.Body;
        import retrofit2.http.DELETE;
        import retrofit2.http.Field;
        import retrofit2.http.FormUrlEncoded;
        import retrofit2.http.GET;
        import retrofit2.http.PATCH;
        import retrofit2.http.POST;
        import retrofit2.http.Path;

public interface MyAPI{
    @FormUrlEncoded
    @POST("/user/")
    Call<String> post_posts(@Field("id") String post, @Field("image") String pgo, @Field("login") String id,@Field("register") String data);

    @PATCH("/user/{pk}/")
    Call<PostItem> patch_posts(@Path("pk") int pk, @Body PostItem post);

    @DELETE("/user/{pk}/")
    Call<PostItem> delete_posts(@Path("pk") int pk);

    @GET("/user/")
    Call<List<PostItem>> get_posts();

    @GET("/user/{pk}/")
    Call<PostItem> get_post_pk(@Path("pk") int pk);
}