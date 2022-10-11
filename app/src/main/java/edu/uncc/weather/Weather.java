package edu.uncc.weather;

import java.io.Serializable;

public class Weather implements Serializable {
   Long Temperature;
    Long Temperature_max;
    Long Temperature_min;
    String Description;
    Long Humidity;
    Long Wind_speed;
    Long Wind_degree;
    Long Cloudiness;
    String icon;
    String dateTime;

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public Weather() {
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Long getTemperature() {
        return Temperature;
    }

    public void setTemperature(Long temperature) {
        Temperature = temperature;
    }

    public Long getTemperature_max() {
        return Temperature_max;
    }

    public void setTemperature_max(Long temperature_max) {
        Temperature_max = temperature_max;
    }

    public Long getTemperature_min() {
        return Temperature_min;
    }

    public void setTemperature_min(Long temperature_min) {
        Temperature_min = temperature_min;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public Long getHumidity() {
        return Humidity;
    }

    public void setHumidity(Long humidity) {
        Humidity = humidity;
    }

    public Long getWind_speed() {
        return Wind_speed;
    }

    public void setWind_speed(Long wind_speed) {
        Wind_speed = wind_speed;
    }

    public Long getWind_degree() {
        return Wind_degree;
    }

    public void setWind_degree(Long wind_degree) {
        Wind_degree = wind_degree;
    }

    public Long getCloudiness() {
        return Cloudiness;
    }

    public void setCloudiness(Long cloudiness) {
        Cloudiness = cloudiness;
    }

    @Override
    public String toString() {
        return "Weather{" +
                "Temperature='" + Temperature + '\'' +
                ", Temperature_max='" + Temperature_max + '\'' +
                ", Temperature_min='" + Temperature_min + '\'' +
                ", Description='" + Description + '\'' +
                ", Humidity='" + Humidity + '\'' +
                ", Wind_speed='" + Wind_speed + '\'' +
                ", Wind_degree='" + Wind_degree + '\'' +
                ", Cloudiness='" + Cloudiness + '\'' +
                '}';
    }
}
