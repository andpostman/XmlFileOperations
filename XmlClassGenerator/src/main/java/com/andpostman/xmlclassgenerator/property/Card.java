package com.andpostman.xmlclassgenerator.property;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Card implements Serializable, JmsMessage {
    String nameJur;
    String type;
    String tmpPrizn;
    String prizn;
    String forma;
    String formaFull;
    String INN;
    String KPP;
    String OKPO;
    String act;
    String tmpCardType;
    String nameJurEng;
    String PBOYuL;
    String birthday;
    String grade;
    String lastName;
    String firstName;
    String middleName;
    String title;
    String lastNameE;
    String firstNameE;
    String middleNameE;
    String country;
    String zip;
    String region;
    String area;
    String town;
    String street;
    String home;
    String building;
    String flat;
    String liveCountry;
    String liveZip;
    String liveRegion;
    String liveArea;
    String liveTown;
    String liveStreet;
    String liveHome;
    String liveBuilding;
    String liveFlat;
    String countryE;
    String zipD;
    String regionE;
    String areaE;
    String townE;
    String streetE;
    String homeE;
    String buildingE;
    String flatE;
    String locations;
    String unid;
    String docPar;
    String linkLN;
    String idClientUpdateQueue;
    String idClient;
    String modifiedUser;
    String urPhone;
    String livePhoneCity;
    String phone;
    String isCustomer;
    String structure;
    String structureUnid;
    String contrRole;
}
