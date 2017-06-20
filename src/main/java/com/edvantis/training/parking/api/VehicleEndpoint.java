package com.edvantis.training.parking.api;

import com.edvantis.training.parking.models.Vehicle;
import com.edvantis.training.parking.services.VehicleService;
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

    private final VehicleService vehicleService;

    @Autowired
    public VehicleEndpoint(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @RequestMapping(value = "/vehicle/{vehicleId}", method = GET)
    public Vehicle getVehicle(@PathVariable("vehicleId") long vehicleId) {
        return vehicleService.getVehicle(vehicleId);
    }

    @RequestMapping(value = "/vehicles", method = GET)
    public List<Vehicle> getAllVehicles() {
        return vehicleService.getAllVehicles();
    }

    @RequestMapping(value = "/vehicles/active", method = GET)
    public List<Vehicle> getAllActiveVehicles() {
        return vehicleService.getAllActiveVehicles();
    }

    @RequestMapping(value = "/vehicles/inactive", method = GET)
    public List<Vehicle> getAllInactiveVehicles() {
        return vehicleService.getAllInactiveVehicles();
    }

    @RequestMapping(value = "/vehicles/owner/{ownerId}", method = GET)
    public List<Vehicle> getAllOwnerVehicles(@PathVariable("ownerId") long ownerId) {
        return vehicleService.getOwnerVehicles(ownerId);
    }

    @RequestMapping(value = "/vehicle/updateVehicle", method = PUT)
    public Vehicle updateVehicle(@RequestBody Vehicle vehicle) {
        return vehicleService.updateVehicle(vehicle.getId(), vehicle);
    }

    @ResponseStatus(CREATED)
    @RequestMapping(value = "/vehicle/createVehicle", method = POST)
    public Vehicle addNewVehicle(@RequestBody Vehicle vehicle) {
        vehicleService.addNewVehicle(vehicle);
        return vehicleService.getVehicleByNumber(vehicle.getNumber());
    }

    @RequestMapping(value = "/vehicle/delete/{vehicleId}", method = DELETE)
    public void deleteVehicle(@PathVariable("vehicleId") long vehicleId) {
        vehicleService.deleteVehicle(vehicleId);
    }
}
