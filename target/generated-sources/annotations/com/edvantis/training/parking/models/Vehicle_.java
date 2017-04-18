package com.edvantis.training.parking.models;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Vehicle.class)
public abstract class Vehicle_ {

	public static volatile SingularAttribute<Vehicle, Owner> owner;
	public static volatile SingularAttribute<Vehicle, String> number;
	public static volatile SingularAttribute<Vehicle, VehicleType> carType;
	public static volatile SingularAttribute<Vehicle, String> model;
	public static volatile SingularAttribute<Vehicle, Long> id;

}

