package Home8core.model;

public class Weather {
    private static String date;
    private static String city;
    private static String temperature;
    private static String weatherText;

    public Weather(String date, String city, String temperature, String weatherText) {
        Weather.date = date;
        Weather.city = city;
        Weather.temperature = temperature;
        Weather.weatherText = weatherText;
    }
    public Weather() {
    }
    public static String getDate() {
        return date;
    }
    public void setDate(String date) {
        Weather.date = date;
    }
    public static String getCity() {
        return city;
    }
    public void setCity(String city) {
        Weather.city = city;
    }
    public static String getTemperature() {
        return temperature;
    }
    public void setTemperature(String temperature) {
        Weather.temperature = temperature;
    }
    public static String getWeatherText() {
        return weatherText;
    }
    public void setWeatherText(String weatherText) {
        Weather.weatherText = weatherText;
    }
    @Override
    public String toString() {
        return "\nНа дату "+ date + " в городе " + city + " ожидается погода " + weatherText + " с температурой окружающего воздуха " +temperature+" градусов Цельсия.";
    }
}
