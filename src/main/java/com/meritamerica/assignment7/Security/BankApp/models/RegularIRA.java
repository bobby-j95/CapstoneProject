package com.meritamerica.assignment7.Security.BankApp.models;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("Regular_ACCOUNT")
public class RegularIRA  extends IRAAccount{

}
