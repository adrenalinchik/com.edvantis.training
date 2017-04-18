package com.edvantis.training.parking.models;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Garage.class)
public abstract class Garage_ {

	public static volatile SingularAttribute<Garage, Parking> parking;
	public static volatile SingularAttribute<Garage, Float> square;
	public static volatile SingularAttribute<Garage, GarageType> garageType;
	public static volatile SingularAttribute<Garage, Long> id;

}

