package Home8core.model;
import Home8core.AppGlobalState;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SQLiteWeatherRepository implements IWeatherRepository {
    @Override
    public List<Weather> getAllData() {
        Statement statement = AppGlobalState.getStatement();
        List<Weather> result = new ArrayList<>();
        try {
            ResultSet rs = statement.executeQuery("SELECT * FROM weather");
            while (rs.next()){
                result.add(new Weather(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4)));
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return result;
    }

    @Override
    public void saveWeatherObject(Weather weather) {
        PreparedStatement insertOne = AppGlobalState.getInsertWeatherPreparedStatement();
        try {
            insertOne.setString(1, Weather.getDate());
            insertOne.setString(2, Weather.getCity());
            insertOne.setString(3, Weather.getTemperature());
            insertOne.setString(4, Weather.getWeatherText());
            insertOne.executeUpdate();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }
}
