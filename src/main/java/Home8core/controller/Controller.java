package Home8core.controller;
import Home8core.AppGlobalState;
import Home8core.model.*;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public class Controller implements IController {
    ICityCodeProvider codeProvider = new AccuWeatherCityCodeProvider();
    IWeatherProvider weatherProvider = (IWeatherProvider) new AccuWeatherProvider();
    IWeatherRepository weatherRepository = new SQLiteWeatherRepository();

    @Override
    public void onCityInput(String city) throws IOException {
        if(city.length() == 1) {
            throw new  IOException("Недопустимо короткое название города");
        }
        codeProvider.getCodeByCityName(city);
    }

    @Override
    public void onCommandChosen(int selectedCommand) throws IOException, ParseException {
        System.out.println(" ");
        switch (selectedCommand) {
            case 1: {
                Weather currentWeather = weatherProvider.getCurrentWeather(AppGlobalState.getInstance().getCityKey());
                System.out.println(currentWeather);
                weatherRepository.saveWeatherObject(currentWeather);
                break;
            }
            case 2: {
                weatherProvider.getWeatherForFiveDays(AppGlobalState.getInstance().getCityKey());
                break;
            }
            case 3: {
                List<Weather> allData = weatherRepository.getAllData();
                allData.forEach(System.out::println);
                break;
            }
            default: {
                throw new IOException("Вы ввели данные неверно.\n");
            }
        }
    }
}
