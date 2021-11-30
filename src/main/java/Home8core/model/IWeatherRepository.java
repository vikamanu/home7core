package Home8core.model;
import java.util.List;

public interface IWeatherRepository {
    List<Weather> getAllData();
    void saveWeatherObject (Weather weather);
}
