package com.edvantis.training.parking.models;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Reservation.class)
public abstract class Reservation_ {

	public static volatile SingularAttribute<Reservation, Long> garageId;
	public static volatile SingularAttribute<Reservation, Long> parkingId;
	public static volatile SingularAttribute<Reservation, Date> end;
	public static volatile SingularAttribute<Reservation, Long> id;
	public static volatile SingularAttribute<Reservation, Long> ownerId;
	public static volatile SingularAttribute<Reservation, Date> begin;

}

