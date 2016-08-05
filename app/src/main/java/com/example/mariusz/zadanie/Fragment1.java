package com.example.mariusz.zadanie;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.mariusz.zadanie.Database.MyDatabaseHelper;
import com.example.mariusz.zadanie.Database.MyLongUrl;
import com.j256.ormlite.dao.Dao;
import java.sql.SQLException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mariusz on 03.08.16.
 */
public class Fragment1 extends Fragment {
    String apiKey= "AIzaSyD538u4zrBrDBBZsmdBfeB7X6Iwi6Sl3JI";
    ImageButton okIButton;
    EditText urlEditText;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragemnt1_layout, container, false);
        getActivity().setTitle("Fragment1");
        urlEditText = (EditText) view.findViewById(R.id.ED_web_addr);
        okIButton = (ImageButton) view.findViewById(R.id.IButton_ok);
        okIButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(urlEditText!=null && urlEditText.getText()!=null && urlEditText.getText().toString()!=null){
                    String longUrl = urlEditText.getText().toString();
                    boolean isValid = Patterns.WEB_URL.matcher(longUrl).matches();
                    if(isValid){
                        getShortenURL(longUrl);
                    }
                    else{
                        Toast.makeText(getActivity().getApplicationContext(), R.string.incorrectUrl, Toast.LENGTH_LONG).show();
                    }
                }
                urlEditText.setText("");
                urlEditText.setHint(R.string.write_url_adress);
            }
        });
        return view;
    }


    @Override
    public void onPause() {
        super.onPause();
    }

    public void getShortenURL(String longUrl){
        APIService service = ServiceGenerator.createService(APIService.class);
        MyLongUrl myLongUrl = new MyLongUrl(longUrl," ");

        Call<MyLongUrl> call = service.getShortenedURL(apiKey, myLongUrl);
        call.enqueue(new Callback<MyLongUrl>() {
            @Override
            public void onResponse(Call<MyLongUrl> call, Response<MyLongUrl> response) {
                if (response.code()!=200){
                    Log.d("onResponse", "status code != 200");
                    Toast.makeText(getActivity().getApplicationContext(), R.string.generate_url_error, Toast.LENGTH_LONG).show();
                }
                else{
                    MyLongUrl myUrl= response.body();
                    saveInDatabase(myUrl);
                }
            }

            @Override
            public void onFailure(Call<MyLongUrl> call, Throwable t) {
                Log.d("onFailure", t.getMessage());
                t.printStackTrace();
            }
        });
    }

    public void saveInDatabase(MyLongUrl myUrl){
        MyDatabaseHelper helper  = new MyDatabaseHelper(getActivity().getApplicationContext());
        Dao<MyLongUrl, Integer> userDao = null;
        try {
            userDao = helper.geturl();
            userDao.createOrUpdate(myUrl);
        } catch (SQLException e) {
            Log.d("saveInDatabase", "Nie udało sie zapisac linku w bazie danych");
            e.printStackTrace();
        }
    }
}
