package com.edvantis.training.parking.services;

import com.edvantis.training.parking.models.Owner;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * Created by taras.fihurnyak on 6/14/2017.
 */
public interface OwnerService {

    void addNewOwner(Owner owner);

    List<Owner> getAllOwners();

    Owner getOwner(long id);

    Owner getOwnerByLastName(String ownerLastName);

    Owner getOwnerByVehicleNumber(String vehicleNumber);

    Owner updateOwner(long id, Owner owner);

    void deleteOwner(long id);

    Map<Owner, Long> getProfitForAllOwners(Date from, Date to) throws ExecutionException, InterruptedException;

    long countProfitFromOwner(Date from, Date to, long ownerId);

    List<Owner> getAllActiveOwners();

    List<Owner> getAllInactiveOwners();
}
