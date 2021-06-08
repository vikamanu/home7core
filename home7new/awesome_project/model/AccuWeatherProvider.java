package ru.vikamanu.home7new.awesome_project.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import ru.vikamanu.home7new.awesome_project.GlobalState;
import ru.vikamanu.home7new.awesome_project.model.IWeatherProvider;
import ru.vikamanu.home7new.awesome_project.model.Period;

import java.io.IOException;
import java.rmi.RemoteException;

public class AccuWeatherProvider implements IWeatherProvider {
    private final String BASE_HOST = "dataservice.accuweather.com";
    private final String VERSION = "v1";
    private final OkHttpClient okHttpClient = new OkHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public AccuWeatherProvider() {
    }
    @Override
    public void getWeather(Period period) {
        String key = this.detectCityKeyByName();
        if (period.equals(Period.FIVE_DAYS)) {
            HttpUrl url = (new HttpUrl.Builder())
                    .scheme("http")
                    .host("dataservice.accuweather.com")
                    .addPathSegment("forecasts")
                    .addPathSegment("v1")
                    .addPathSegment("daily")
                    .addPathSegment("5day")
                    .addPathSegment(key)
                    .addQueryParameter("apikey", GlobalState.getInstance().API_KEY)
                    .build();
            Request request = (new okhttp3.Request.Builder())
                    .addHeader("accept", "application/json")
                    .url(url)
                    .build();
            try {
                Response response = this.okHttpClient.newCall(request).execute();
                System.out.println(response.body().string());
            } catch (IOException var6){
                throw new RuntimeException(var6);
            }
        }

    }
    private String detectCityKeyByName() {
        String selectedCity = GlobalState.getInstance().getSelectedCity();
        HttpUrl detectLocationURL = (new HttpUrl.Builder())
                .scheme("http")
                .host("dataservice.accuweather.com")
                .addPathSegment("locations")
                .addPathSegment("v1")
                .addPathSegment("cities")
                .addPathSegment("search")
                .addQueryParameter("apikey", GlobalState.getInstance().API_KEY)
                .addQueryParameter("q", selectedCity)
                .build();
        Request request = (new okhttp3.Request.Builder())
                .addHeader("accept", "application/json")
                .url(detectLocationURL)
                .build();
        Response locationResponse = null;
        try {
            locationResponse = this.okHttpClient.newCall(request).execute();
            if (!locationResponse.isSuccessful()) {
                throw new RuntimeException("Сервер ответил " + locationResponse.code());
            } else {
                String jsonResponse = locationResponse.body().string();
                if (this.objectMapper.readTree(jsonResponse).size() > 0) {
                    String code = this.objectMapper.readTree(jsonResponse).get(0).at("/Key").asText();
                    String cityName = this.objectMapper.readTree(jsonResponse).get(0).at("/LocalizedName").asText();
                    String countryName = this.objectMapper.readTree(jsonResponse).get(0).at("/Country/LocalizedName").asText();
                    System.out.printf("Найден город %s в стране %s, код - %s\n", cityName, countryName, code);
                    return code;
                } else {
                    throw new RuntimeException(selectedCity + " - такой город не найден");
                }
            }
        } catch (IOException var9) {
            var9.printStackTrace();
            throw new RuntimeException(var9);
        }
    }
}