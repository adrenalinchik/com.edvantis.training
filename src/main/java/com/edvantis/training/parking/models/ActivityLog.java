package com.edvantis.training.parking.models;

import com.edvantis.training.parking.models.enums.Actions;
import com.edvantis.training.parking.models.enums.ModelState;
import com.edvantis.training.parking.models.enums.ModelType;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by taras.fihurnyak on 6/14/2017.
 */

@Entity
@Table(name = "ACTIVITIES")
public class ActivityLog {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "ID", unique = true, nullable = false)
    private long id;

    @Column(name = "OBJECT_TYPE")
    @Enumerated(EnumType.STRING)
    private ModelType objectType;

    @Column(name = "OBJECT_ID")
    private long objectId;

    @Column(name = "ACTION")
    @Enumerated(EnumType.STRING)
    private Actions actionType;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "DATE")
    private Date createdDate;
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public long getObjectId() {
        return objectId;
    }

    public void setObjectId(long objectId) {
        this.objectId = objectId;
    }

    public ModelType getObjectType() {
        return objectType;
    }

    public void setObjectType(ModelType objectType) {
        this.objectType = objectType;
    }

    public Actions getActionType() {
        return actionType;
    }

    public void setActionType(Actions actionType) {
        this.actionType = actionType;
    }

}
