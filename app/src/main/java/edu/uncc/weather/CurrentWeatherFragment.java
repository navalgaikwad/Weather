package edu.uncc.weather;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import edu.uncc.weather.databinding.FragmentCurrentWeatherBinding;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class CurrentWeatherFragment extends Fragment {
    private static final String ARG_PARAM_CITY = "ARG_PARAM_CITY";
    private DataService.City mCity;
    FragmentCurrentWeatherBinding binding;
    private final OkHttpClient client = new OkHttpClient();
    Weather weather=new Weather();

    public CurrentWeatherFragment() {
        // Required empty public constructor
    }

    public static CurrentWeatherFragment newInstance(DataService.City city) {
        CurrentWeatherFragment fragment = new CurrentWeatherFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM_CITY, city);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mCity = (DataService.City) getArguments().getSerializable(ARG_PARAM_CITY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCurrentWeatherBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Current Weather");
        String city=(mCity.getCity()+","+mCity.getCountry()).toLowerCase();
        apiCall(city);
        binding.buttonCheckForecast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.viewWeatherForecast(city);
            }
        });

    }

    private void apiCall(String city) {

        Request request = new Request.Builder()
                .url("https://api.openweathermap.org/data/2.5/weather?q="+city+"&appid=4241353f49f5b2a7b9217d2829429568")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                try (ResponseBody responseBody = response.body()) {
                    if (!response.isSuccessful())
                        throw new IOException("Unexpected code " + response);


                    JSONObject jsonObject=new JSONObject(responseBody.string());
                    JSONObject main = jsonObject.getJSONObject("main");
                    weather.setTemperature(main.getLong("temp"));;
                    weather.setTemperature_min(main.getLong("temp_min"));;
                    weather.setTemperature_max(main.getLong("temp_max"));
                    weather.setHumidity(main.getLong("humidity"));;
                    JSONObject wind = jsonObject.getJSONObject("wind");
                    weather.setWind_speed(wind.getLong("speed"));;
                    weather.setWind_degree(wind.getLong("deg"));;
                    JSONObject clouds = jsonObject.getJSONObject("clouds");
                    weather.setCloudiness(clouds.getLong("all"));;



                    JSONArray jsonArray=jsonObject.getJSONArray("weather");
                    JSONObject   object = jsonArray.getJSONObject(0);
                    weather.setDescription(object.getString("description"));;
                    weather.setIcon(object.getString("icon"));;
                    String url = AppConst.ICON_API_URL + weather.icon + "@2x.png";
                    Log.d("TAG", "getView: "+"https://openweathermap.org/img/wn/01d@2x.png");



                    getActivity().runOnUiThread(()->{
                        Picasso.get().load(url)
                                .resize(50, 50)
                                .centerCrop().into(binding.imageViewWeatherIcon1);
                     //   contactListAdapter.notifyDataSetChanged();
                    });
                    binding.textViewCityName.setText((mCity.getCity()+","+mCity.getCountry()));
                    binding.textViewTemp.setText(weather.Temperature+" F");
                    binding.textViewTempMax.setText(""+weather.Temperature_max+" F");
                    binding.textViewTempMin.setText(""+weather.Temperature_min+" F");
                    binding.textViewHumidity.setText(""+weather.Humidity+" %");
                    binding.textViewDesc.setText(""+weather.Description);
                    binding.textViewWindDegree.setText(""+weather.Wind_degree+" degrees");
                    binding.textViewWindSpeed.setText(""+weather.getWind_speed()+" miles/hr");
                    binding.textViewCloudiness.setText(""+weather.Cloudiness+" %");

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

    CitiesListListFragmentListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof CitiesListListFragmentListener) {
            mListener = (CitiesListListFragmentListener) context;
        } else {
            throw new RuntimeException(context + " must implement OnFragmentInteractionListener");
        }
    }

    interface CitiesListListFragmentListener {
        void viewWeatherForecast(String city);
    }
}