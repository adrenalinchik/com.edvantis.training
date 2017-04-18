package com.edvantis.training.parking.models;

import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Owner.class)
public abstract class Owner_ {

	public static volatile SingularAttribute<Owner, String> firstName;
	public static volatile SingularAttribute<Owner, String> lastName;
	public static volatile SingularAttribute<Owner, Gender> gender;
	public static volatile SingularAttribute<Owner, LocalDate> dob;
	public static volatile SetAttribute<Owner, Vehicle> userVehicles;
	public static volatile SingularAttribute<Owner, Long> id;

}

