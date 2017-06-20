package com.edvantis.training.parking.repository.jdbc.impl;

import com.edvantis.training.parking.models.enums.Gender;
import com.edvantis.training.parking.models.enums.ModelState;
import com.edvantis.training.parking.models.Owner;
import com.edvantis.training.parking.repository.OwnerRepository;
import com.edvantis.training.parking.repository.jdbc.AbstractJdbcRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by taras.fihurnyak on 2/9/2017.
 */
@Repository
@Profile("jdbc")
public class OwnerJdbcRepositoryImp extends AbstractJdbcRepository implements OwnerRepository {
    private final Logger logger = LoggerFactory.getLogger(OwnerJdbcRepositoryImp.class);
    private final String getOwnerByLastname = "SELECT * FROM OWNER WHERE LASTNAME = ?";

    @Autowired
    public OwnerJdbcRepositoryImp(String dbName) {
        super(dbName);
    }

    @Override
    public Set<Owner> getActiveOrInactive(ModelState status) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public Owner getById(long id) {
        Owner owner = null;
        try {
            PreparedStatement pstmt = getConnection().prepareStatement("SELECT * FROM OWNER WHERE ID=" + id);
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
            logger.warn(e.getMessage());
        }
        return owner;
    }

    @Override
    public void insert(Owner owner) {
        try {
            PreparedStatement pstmt = getConnection().prepareStatement("INSERT INTO OWNER(FIRSTNAME, LASTNAME, GENDER, DOB) VALUES(?,?,?,?)");
            pstmt.setString(1, owner.getFirstName());
            pstmt.setString(2, owner.getLastName());
            if (owner.getGender().toString().toLowerCase().equals("male")) {
                pstmt.setInt(3, 1);
            } else pstmt.setInt(3, 2);
            pstmt.setDate(4, convertDateToDatabaseColumn(owner.getDOB()));
            pstmt.executeUpdate();
            logger.info("Owner {} {} saved to db successfully.", owner.getFirstName(), owner.getFirstName());
        } catch (SQLException e) {
            logger.warn(e.getMessage());
        }
    }

    @Override
    public void update(long id, Owner owner) {
        try {
            PreparedStatement pstmt = getConnection().prepareStatement("UPDATE OWNER SET FIRSTNAME = ?, LASTNAME = ?, GENDER = ?, DOB = ? WHERE ID=?");
            pstmt.setString(1, owner.getFirstName());
            pstmt.setString(2, owner.getLastName());
            if (owner.getGender().equals(Gender.MALE)) {
                pstmt.setInt(3, 1);
            } else pstmt.setInt(3, 2);
            pstmt.setDate(4, convertDateToDatabaseColumn(owner.getDOB()));
            pstmt.setLong(5, id);
            pstmt.executeUpdate();
            logger.info("Owner with id={} id updated successfully.", owner.getId());
        } catch (SQLException e) {
            logger.warn(e.getMessage());
        }
    }

    @Override
    public void delete(long id) {
        try {
            PreparedStatement pstmt = getConnection()
                    .prepareStatement("DELETE FROM OWNER WHERE ID=?");
            pstmt.setLong(1, id);
            pstmt.executeUpdate();
            logger.info("Owner with id={} id removed from db successfully.", id);
        } catch (SQLException e) {
            logger.warn(e.getMessage());
        }
    }

    @Override
    public Owner getByVehicleNumber(String vehicleNumber) {
        return getById(getOwnerIdFromVehicleByNumber(vehicleNumber));
    }

    public Set<Owner> getAll() {
        Set<Owner> ownerSet = new HashSet<>();
        try {
            PreparedStatement pstmt = getConnection().prepareStatement("SELECT * FROM OWNER");
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
            logger.warn(e.getMessage());
        }
        return ownerSet;
    }

    public Owner getByLastName(String lastName) {
        Owner owner = null;
        try {
            PreparedStatement pstmt = getConnection().prepareStatement(getOwnerByLastname);
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
            logger.warn(e.getMessage());
        }
        return owner;
    }

    public int getOwnerIdByLastName(String ownerLastName) {
        int ownerId = 0;
        try {
            PreparedStatement pstmt = getConnection().prepareStatement(getOwnerByLastname);
            pstmt.setString(1, ownerLastName);
            ResultSet rs = pstmt.executeQuery();
            rs.next();
            ownerId = rs.getInt(1);
        } catch (SQLException e) {
            logger.warn(e.getMessage());
        }
        return ownerId;
    }

    private int getOwnerIdFromVehicleByNumber(String vehicleNumber) {
        int ownerId = 0;
        try {
            PreparedStatement pstmt = getConnection().prepareStatement(VehicleJdbcRepositoryImp.GET_VEHICLE_BY_NUMBER);
            pstmt.setString(1, vehicleNumber);
            ResultSet rs = pstmt.executeQuery();
            rs.next();
            ownerId = rs.getInt(2);
        } catch (SQLException e) {
            logger.warn(e.getMessage());
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
