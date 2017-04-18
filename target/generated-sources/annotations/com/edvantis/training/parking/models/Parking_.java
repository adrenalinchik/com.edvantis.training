package com.edvantis.training.parking.models;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Parking.class)
public abstract class Parking_ {

	public static volatile SetAttribute<Parking, Garage> garages;
	public static volatile SingularAttribute<Parking, String> address;
	public static volatile SingularAttribute<Parking, Long> id;
	public static volatile SingularAttribute<Parking, Integer> freeGarages;

}

