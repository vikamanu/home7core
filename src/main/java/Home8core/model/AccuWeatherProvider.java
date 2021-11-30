package Home8core.model;
import Home8core.AppGlobalState;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AccuWeatherProvider implements IWeatherProvider {
    private static final String BASE_HOST = "dataservice.accuweather.com";
    private static final String CONDITIONS_PATH = "currentconditions";
    private static final String FORECASTS = "forecasts";
    private static final String API_VERSION = "v1";
    private static final String DAILY = "daily";
    private static final String DAYS = "5day";
    private static final String API_KEY = AppGlobalState.getInstance().getApiKey();
    private final OkHttpClient client = new OkHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Weather getCurrentWeather(String cityKey) throws IOException, ParseException {
        //http://dataservice.accuweather.com/currentconditions/v1/27497?apikey={{accuweatherApiKey}}
        HttpUrl getWeatherUrl = new HttpUrl.Builder()
                .scheme("http")
                .host(BASE_HOST)
                .addPathSegment(CONDITIONS_PATH)
                .addPathSegment(API_VERSION)
                .addPathSegment(cityKey)
                .addQueryParameter("apikey", API_KEY)
                .build();
        Request getWeatherRequest = new Request.Builder()
                .addHeader("accept", "application/json")
                .url(getWeatherUrl)
                .build();

        Response response = client.newCall(getWeatherRequest).execute();
        if (!response.isSuccessful()) {
            throw new IOException("Ошибка сети\n");
        }
        assert response.body() != null;
        String jsonBody2 = response.body().string();

        String OneDayCityName = AppGlobalState.getInstance().getCityName1();
        String OneDay = objectMapper.readTree(jsonBody2).get(0).at("/LocalObservationDateTime").asText().substring(0,10);
        SimpleDateFormat oldDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat newDateFormat = new SimpleDateFormat("dd.MM.yyyy");
        Date date = oldDateFormat.parse(OneDay);
        String OneDayForecastDate = newDateFormat.format(date);
        String OneDayDescription = objectMapper.readTree(jsonBody2).get(0).at("/WeatherText").asText().toLowerCase();
        String OneDayTemperature = objectMapper.readTree(jsonBody2).get(0).at("/Temperature/Metric/Value").asText();

        Weather weather = new Weather(OneDayForecastDate, OneDayCityName, OneDayTemperature, OneDayDescription);
        return weather;
    }

    @Override
    public void getWeatherForFiveDays(String cityKey) throws IOException, ParseException {
//http://dataservice.accuweather.com/forecasts/v1/daily/5day/295212?apikey=XYHQlFgxfVgXHA2ydmRCbLLAQacY1QbQ&language=ru&metric=true
        HttpUrl getWeatherUrl = new HttpUrl.Builder()
                .scheme("http")
                .host(BASE_HOST)
                .addPathSegment(FORECASTS)
                .addPathSegment(API_VERSION)
                .addPathSegment(DAILY)
                .addPathSegment(DAYS)
                .addPathSegment(cityKey)
                .addQueryParameter("apikey", API_KEY)
                .addQueryParameter("metric","true")
                .build();
        Request getWeatherRequest = new Request.Builder()
                .addHeader("accept", "application/json")
                .url(getWeatherUrl)
                .build();
        Response response = client.newCall(getWeatherRequest).execute();
        if (!response.isSuccessful()) {
            throw new IOException("Ошибка сети\n");
        }
        assert response.body() != null;
        String jsonBody2 = response.body().string();

        System.out.println(" ");

        for (int i = 0; i < 5; i++) {
            String firstDay = objectMapper.readTree(jsonBody2).at("/DailyForecasts/"+i+"/Date").asText().substring(0,10);
            SimpleDateFormat oldDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat newDateFormat = new SimpleDateFormat("dd.MM.yyyy");
            Date date = oldDateFormat.parse(firstDay);
            String dayForecastDate = newDateFormat.format(date);
            String dayCityName = AppGlobalState.getInstance().getCityName1();
            String dayTemperatureMin = objectMapper.readTree(jsonBody2).at("/DailyForecasts/"+i+"/Temperature/Minimum/Value").asText();
            String dayTemperatureMax = objectMapper.readTree(jsonBody2).at("/DailyForecasts/"+i+"/Temperature/Maximum/Value").asText();
            String dayDescriptionDay = objectMapper.readTree(jsonBody2).at("/DailyForecasts/"+i+"/Day/IconPhrase").asText().toLowerCase();
            String dayDescriptionNight = objectMapper.readTree(jsonBody2).at("/DailyForecasts/"+i+"/Night/IconPhrase").asText().toLowerCase();
            System.out.println("* * * * * * * * * *");
            System.out.printf("На дату %s в городе %s погода днем %s, погода ночью %s, а температура от %sC до %sC\n", dayForecastDate, dayCityName, dayDescriptionDay,dayDescriptionNight, dayTemperatureMin, dayTemperatureMax);
        }
    }
}
