import com.edvantis.training.parking.jdbc.ParkingJdbcService;
import com.edvantis.training.parking.jdbc.ParkingJdbcServiceImpl;

/**
 * Created by taras.fihurnyak on 2/9/2017.
 */
public class Application {
    public static void main(String[] args) {
        ParkingJdbcService service = new ParkingJdbcServiceImpl();

        if (args.length > 0) {
            service.createDb(args[0], args[1], args[2]);

        }
    }
}
