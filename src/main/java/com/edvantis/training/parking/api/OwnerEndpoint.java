package com.edvantis.training.parking.api;

import com.edvantis.training.parking.models.Owner;
import com.edvantis.training.parking.services.OwnerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
@RequestMapping("/parking/api")
public class OwnerEndpoint {

    private final static Logger logger = LoggerFactory.getLogger(OwnerEndpoint.class);

    private final OwnerService ownerService;

    @Autowired
    public OwnerEndpoint(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @RequestMapping(value = "/owner/{ownerId}", method = GET)
    public Owner getOwner(@PathVariable("ownerId") long ownerId) {
        return ownerService.getOwner(ownerId);
    }

    @RequestMapping(value = "/owner", method = GET)
    public Owner getOwnerByLastName(@RequestParam(value = "name") String name) {
        return ownerService.getOwnerByLastName(name);
    }

    @RequestMapping(value = "/owners", method = GET)
    public List<Owner> getAllOwners() {
        return ownerService.getAllOwners();
    }

    @RequestMapping(value = "/owners/active", method = GET)
    public List<Owner> getAllActiveOwners() {
        return ownerService.getAllActiveOwners();
    }

    @RequestMapping(value = "/owners/inactive", method = GET)
    public List<Owner> getAllInactiveOwners() {
        return ownerService.getAllInactiveOwners();
    }

    @ResponseStatus(CREATED)
    @RequestMapping(value = "/owner/createOwner", method = POST)
    public Owner addNewOwner(@RequestBody Owner owner) {
        ownerService.addNewOwner(owner);
        return ownerService.getOwnerByLastName(owner.getLastName());
    }

    @RequestMapping(value = "/owner/updateOwner", method = PUT)
    public Owner updateOwner(@RequestBody Owner owner) {
        return ownerService.updateOwner(owner.getId(), owner);
    }

    @RequestMapping(value = "/owner/delete/{ownerId}", method = DELETE)
    public void deleteOwner(@PathVariable("ownerId") long ownerId) {
        ownerService.deleteOwner(ownerId);
    }

    @RequestMapping(value = "/owners/{ownerId}", method = GET)
    public long getProfitForOwner(@PathVariable("ownerId") long ownerId,
                                  @RequestParam(value = "from") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date from,
                                  @RequestParam(value = "to") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date to) {
        return ownerService.countProfitFromOwner(from, to, ownerId);
    }

    @RequestMapping(value = "/owners/profit", method = GET)
    public long getProfitForAllOwner(@RequestParam(value = "from") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date from,
                                     @RequestParam(value = "to") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date to) throws Exception {
        Map<Owner, Long> map = ownerService.getProfitForAllOwners(from, to);
        return map.values().stream().mapToLong(Long::longValue).sum();
    }
}
