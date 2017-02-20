package com.edvantis.training.parking.repository.jdbc.impl;

import com.edvantis.training.parking.models.Gender;
import com.edvantis.training.parking.models.Owner;
import com.edvantis.training.parking.repository.OwnerJdbcRepository;
import com.edvantis.training.parking.repository.jdbc.AbstractJdbcRepository;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static com.edvantis.training.parking.jdbc.Constants.*;

/**
 * Created by taras.fihurnyak on 2/9/2017.
 */
public class OwnerJdbcRepositoryImp extends AbstractJdbcRepository implements OwnerJdbcRepository {

    private final Logger logger = Logger.getLogger(OwnerJdbcRepositoryImp.class);

    public OwnerJdbcRepositoryImp(String dbName, String login, String password) {
        super(dbName, login, password);
    }

    @Override
    public Owner getById(int id) {
        Owner owner = null;
        try {
            PreparedStatement pstmt = getConnection().prepareStatement(GET_OWNER_BY_ID + id);
            ResultSet rs = pstmt.executeQuery();
            owner = new Owner();
            rs.next();
            owner.setFirstName(rs.getString(2));
            owner.setLastName(rs.getString(3));
            if (rs.getInt(4) == 1)
                owner.setGender(Gender.MALE);
            else owner.setGender(Gender.FEMALE);
            owner.setDOB(convertDateToOwnerAttribute(rs.getDate(5)));
        } catch (SQLException e) {
            logger.warn(e);
        } catch (Exception e) {
            logger.error(e);
        }
        return owner;
    }

    @Override
    public void insert(Owner owner) {
        try {
            PreparedStatement pstmt = getConnection().prepareStatement(CREATE_OWNER);
            pstmt.setString(1, owner.getFirstName());
            pstmt.setString(2, owner.getLastName());
            if (owner.getGender().toString().toLowerCase().equals("male")) {
                pstmt.setInt(3, 1);
            } else pstmt.setInt(3, 2);
            pstmt.setDate(4, convertDateToDatabaseColumn(owner.getDOB()));
            pstmt.executeUpdate();
            logger.info("Owner " + owner.getFirstName() + " " + owner.getFirstName() + " saved to db successfully.");
        } catch (SQLException e) {
            logger.warn(e);
        } catch (Exception e) {
            logger.error(e);
        }
    }

    @Override
    public void update(int ownerId, Owner owner) {
        try {
            PreparedStatement pstmt = getConnection().prepareStatement(UPDATE_OWNER);
            pstmt.setString(1, owner.getFirstName());
            pstmt.setString(2, owner.getLastName());
            if (owner.getGender().equals(Gender.MALE)) {
                pstmt.setInt(3, 1);
            } else pstmt.setInt(3, 2);
            pstmt.setDate(4, convertDateToDatabaseColumn(owner.getDOB()));
            pstmt.setInt(5, ownerId);
            pstmt.executeUpdate();
            logger.info("Owner with " + ownerId + " id updated successfully.");
        } catch (SQLException e) {
            logger.warn(e);
        } catch (Exception e) {
            logger.error(e);
        }
    }

    @Override
    public void delete(int ownerId) {
        try {
            PreparedStatement pstmt = getConnection()
                    .prepareStatement(DELETE_OWNER);
            pstmt.setInt(1, ownerId);
            pstmt.executeUpdate();
            logger.info("Owner with " + ownerId + " id removed from db successfully.");
        } catch (SQLException e) {
            logger.warn(e);
        } catch (Exception e) {
            logger.error(e);
        }
    }

    public Set<Owner> getAllOwnersFromDb() {
        Set<Owner> ownerSet = new HashSet<>();
        try {
            PreparedStatement pstmt = getConnection().prepareStatement(GET_ALL_OWNERS);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Owner owner = new Owner();
                owner.setFirstName(rs.getString(2));
                owner.setLastName(rs.getString(3));
                if (rs.getInt(4) == 1)
                    owner.setGender(Gender.MALE);
                else owner.setGender(Gender.FEMALE);
                owner.setDOB(convertDateToOwnerAttribute(rs.getDate(5)));
                ownerSet.add(owner);
            }
        } catch (SQLException e) {
            logger.warn(e);
        } catch (Exception e) {
            logger.error(e);
        }

        return ownerSet;
    }

    public Owner getOwnerByLastName(String lastName) {
        Owner owner = null;
        try {
            PreparedStatement pstmt = getConnection().prepareStatement(GET_OWNER_BY_LASTNAME);
            pstmt.setString(1, lastName);
            ResultSet rs = pstmt.executeQuery();
            owner = new Owner();
            rs.next();
            owner.setFirstName(rs.getString(2));
            owner.setLastName(rs.getString(3));
            if (rs.getInt(4) == 1)
                owner.setGender(Gender.MALE);
            else owner.setGender(Gender.FEMALE);
            owner.setDOB(convertDateToOwnerAttribute(rs.getDate(5)));
        } catch (SQLException e) {
            logger.warn(e);
        } catch (Exception e) {
            logger.error(e);
        }
        return owner;
    }

    public Owner getOwnerByVehicleNymber(String vehicleNumber) {

        return getById(getOwnerIdFromVehicleByNumber(vehicleNumber));

    }

    public int getOwnerIdByLastName(String ownerLastName) {
        int ownerId = 0;
        try {
            PreparedStatement pstmt = getConnection().prepareStatement(GET_OWNER_BY_LASTNAME);
            pstmt.setString(1, ownerLastName);
            ResultSet rs = pstmt.executeQuery();
            rs.next();
            ownerId = rs.getInt(1);
        } catch (SQLException e) {
            logger.warn(e);
        } catch (Exception e) {
            logger.error(e);
        }
        return ownerId;
    }

    private int getOwnerIdFromVehicleByNumber(String vehicleNumber) {
        int ownerId = 0;
        try {
            PreparedStatement pstmt = getConnection().prepareStatement(GET_VEHICLE_BY_NUMBER);
            pstmt.setString(1, vehicleNumber);
            ResultSet rs = pstmt.executeQuery();
            rs.next();
            ownerId = rs.getInt(2);
        } catch (SQLException e) {
            logger.warn(e);
        } catch (Exception e) {
            logger.error(e);
        }
        return ownerId;
    }

    private java.sql.Date convertDateToDatabaseColumn(LocalDate date) {
        return java.sql.Date.valueOf(date);
    }

    private LocalDate convertDateToOwnerAttribute(java.sql.Date databaseValue) {
        return databaseValue.toLocalDate();
    }


}
