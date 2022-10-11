package edu.uncc.weather;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.uncc.weather.databinding.FragmentWeatherForecastBinding;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;


public class WeatherForecastFragment extends Fragment {


    private static final String ARG_PARAM_CITY = "city";
    private static final String ARG_PARAM2 = "param2";

    private final OkHttpClient client = new OkHttpClient();
    List<Weather>weatherList=new ArrayList<>();
    FragmentWeatherForecastBinding binding;
    LinearLayoutManager linearLayoutManager;
    WeatherForecastAdapter weatherForecastAdapter;
    RecyclerView recyclerView;
    DividerItemDecoration dividerItemDecoration;


    private String mCity;
    private String mParam2;

    public WeatherForecastFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static WeatherForecastFragment newInstance(String city) {
        WeatherForecastFragment fragment = new WeatherForecastFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM_CITY, city);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mCity = getArguments().getString(ARG_PARAM_CITY);
           // mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       // return inflater.inflate(R.layout.fragment_weather_forecast, container, false);
        binding = FragmentWeatherForecastBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        apiCall(mCity);
        binding.recycleView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(getContext());
        dividerItemDecoration = new DividerItemDecoration(binding.recycleView.getContext(), linearLayoutManager.getOrientation());
        binding.recycleView.addItemDecoration(dividerItemDecoration);
        binding.recycleView.setLayoutManager(linearLayoutManager);
        FragmentActivity activity = getActivity();

        weatherForecastAdapter = new WeatherForecastAdapter(activity,weatherList);
        binding.recycleView.setAdapter(weatherForecastAdapter);
        binding.textViewCityName.setText(mCity);
    }

    private void apiCall(String city) {

        Request request = new Request.Builder()
                .url(AppConst.HTTPS_API_OPENWEATHERMAP_ORG_DATA_2_5_FORECAST_Q +city+ "&appid=" + AppConst.API_KEY)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                try (ResponseBody responseBody = response.body()) {
                    if (!response.isSuccessful())
                        throw new IOException("Unexpected code " + response);

                    JSONObject jsonObject=new JSONObject(responseBody.string());

                    JSONArray jsonArray=jsonObject.getJSONArray("list");

                    for (int i=0;i<jsonArray.length();i++){
                        Weather weather=new Weather();
                        JSONObject   object = jsonArray.getJSONObject(i);
                        JSONObject main = object.getJSONObject("main");
                        weather.setTemperature(main.getLong("temp"));;
                        weather.setTemperature_min(main.getLong("temp_min"));;
                        weather.setTemperature_max(main.getLong("temp_max"));
                        weather.setHumidity(main.getLong("humidity"));;
                        JSONObject wind = object.getJSONObject("wind");
                        weather.setWind_speed(wind.getLong("speed"));;
                        weather.setWind_degree(wind.getLong("deg"));;
                        JSONObject clouds = object.getJSONObject("clouds");
                        weather.setCloudiness(clouds.getLong("all"));;
                        weather.setDateTime(object.getString("dt_txt"));



                        JSONArray weatherjsonArray=object.getJSONArray("weather");
                        JSONObject   object0 = weatherjsonArray.getJSONObject(0);
                        weather.setDescription(object0.getString("description"));
                        weather.setIcon(object0.getString("icon"));

                        weatherList.add(weather);

                    }

                    getActivity().runOnUiThread(()->{

                        weatherForecastAdapter.notifyDataSetChanged();
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }
        });
    }

   /* WeatherForecastFragmentListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof WeatherForecastFragmentListener) {
            mListener = (WeatherForecastFragmentListener) context;
        } else {
            throw new RuntimeException(context + " must implement OnFragmentInteractionListener");
        }
    }

    interface WeatherForecastFragmentListener {

        void  viewContactDetails(FragmentActivity activity);


    }*/

}