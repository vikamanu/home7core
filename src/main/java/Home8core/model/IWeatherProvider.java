package Home8core.model;
import java.io.IOException;
import java.text.ParseException;

public interface IWeatherProvider {
    Weather getCurrentWeather(String cityKey) throws IOException, ParseException;
    void getWeatherForFiveDays(String cityKey) throws IOException, ParseException;
}
