package com.edvantis.training.parking.api;

import com.edvantis.training.parking.models.Garage;
import com.edvantis.training.parking.models.Owner;
import com.edvantis.training.parking.models.Vehicle;
import com.edvantis.training.parking.services.ParkingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.web.bind.annotation.RequestMethod.*;

/**
 * Created by taras.fihurnyak on 5/26/2017.
 */

@RestController
@RequestMapping("/parking/api")
public class VehicleEndpoint {

    private final static Logger logger = LoggerFactory.getLogger(VehicleEndpoint.class);

    private final ParkingService parkingService;

    @Autowired
    public VehicleEndpoint(ParkingService parkingService) {
        this.parkingService = parkingService;
    }

    @RequestMapping(value = "/vehicle/{vehicleId}", method = GET)
    public Vehicle getVehicle(@PathVariable("vehicleId") long vehicleId) {
        return parkingService.getVehicle(vehicleId);
    }

    @RequestMapping(value = "/vehicles", method = GET)
    public List<Vehicle> getAllVehicles() {
        return parkingService.getAllVehicles();
    }

    @RequestMapping(value = "/vehicles/active", method = GET)
    public List<Vehicle> getAllActiveVehicles() {
        return parkingService.getAllActiveVehicles();
    }

    @RequestMapping(value = "/vehicles/inactive", method = GET)
    public List<Vehicle> getAllInactiveVehicles() {
        return parkingService.getAllInactiveVehicles();
    }

    @RequestMapping(value = "/vehicle/updateVehicle", method = PUT)
    public Vehicle updateVehicle(@RequestBody Vehicle vehicle) {
        return parkingService.updateVehicle(vehicle.getId(), vehicle);
    }

    @ResponseStatus(CREATED)
    @RequestMapping(value = "/vehicle/createVehicle", method = POST)
    public Vehicle addNewVehicle(@RequestBody Vehicle vehicle) {
        parkingService.addNewVehicle(vehicle);
        return parkingService.getVehicleByNumber(vehicle.getNumber());
    }

    @RequestMapping(value = "/vehicle/delete/{vehicleId}", method = DELETE)
    public void deleteVehicle(@PathVariable("vehicleId") long vehicleId) {
        parkingService.deleteVehicle(vehicleId);
    }
}
