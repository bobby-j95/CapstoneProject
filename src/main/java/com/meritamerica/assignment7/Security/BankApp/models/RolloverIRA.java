package com.meritamerica.assignment7.Security.BankApp.models;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("Rollover_ACCOUNT")
public class RolloverIRA extends IRAAccount{

}
