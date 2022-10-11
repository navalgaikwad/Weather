package edu.uncc.weather;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;

public class WeatherForecastAdapter extends RecyclerView.Adapter<WeatherForecastAdapter.ViewListViewHolder>{

    private final  FragmentActivity activity;

    List<Weather>weatherList=new ArrayList<>();

    public WeatherForecastAdapter(FragmentActivity activity, List<Weather> weatherList) {
        this.weatherList = weatherList;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.forecast_row_item, parent, false);
        ViewListViewHolder viewHolder=new ViewListViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewListViewHolder holder, int position) {
        Weather weather = weatherList.get(position);
        holder.textViewDateTime.setText(weather.getDateTime());
        holder.textViewTemp.setText(""+weather.Temperature+"F");
        holder.textViewTempMax.setText("Max:"+weather.Temperature_max+"F");
        holder.textViewTempMin.setText("Min:"+weather.Temperature_min+"F");
        holder.textViewHumidity.setText("Humidity:"+weather.Humidity+"%");

        holder.textViewDesc.setText(weather.Description.substring(0, 1).toUpperCase() + weather.Description.substring(1));



        String url = AppConst.ICON_API_URL + weather.icon + "@2x.png";
        Log.d("TAG", "getView: "+"https://openweathermap.org/img/wn/01d@2x.png");
        activity.runOnUiThread(()->{
            Picasso.get().load(url)
                    .resize(70, 70)
                    .centerCrop().into(holder.imageViewWeatherIcon);
        });
    }

    @Override
    public int getItemCount() {
        return weatherList.size();
    }

    public static class ViewListViewHolder extends RecyclerView.ViewHolder{
        TextView textViewDateTime;
        TextView textViewTemp;
        TextView  textViewTempMax;
        TextView  textViewTempMin;
        TextView  textViewHumidity;
        TextView  textViewDesc;
        ImageView imageViewWeatherIcon;
        View rootView;

        public ViewListViewHolder(@NonNull View itemView) {
            super(itemView);
            rootView=itemView;
            textViewDateTime= itemView.findViewById(R.id.textViewDateTime);
            textViewTemp=  itemView.findViewById(R.id.textViewTemp);
            textViewTempMax=  itemView.findViewById(R.id.textViewTempMax);
            textViewTempMin=  itemView.findViewById(R.id.textViewTempMin);
            textViewHumidity=  itemView.findViewById(R.id.textViewHumidity);
            textViewDesc=  itemView.findViewById(R.id.textViewDesc);
            imageViewWeatherIcon=  itemView.findViewById(R.id.imageViewWeatherIcon1);

        };

    }
}
