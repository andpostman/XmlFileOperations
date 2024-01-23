package com.andpostman.routeprocessor.model;

import lombok.*;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "card", schema = "dbo")
@NoArgsConstructor
@Getter
@RequiredArgsConstructor
@EqualsAndHashCode
public class CardDto implements Persistable<Integer> {

    @Id
    private Integer cardId;
    @Column("name_jur")
    private @NonNull String nameJur;
    @Column("type")
    private @NonNull String type;
    @Column("tmp_prizn")
    private @NonNull String tmpPrizn;
    @Column("prizn")
    private @NonNull String prizn;
    @Column("forma")
    private @NonNull String forma;
    @Column("forma_full")
    private @NonNull String formaFull;
    @Column("INN")
    private @NonNull String INN;
    @Column("KPP")
    private @NonNull String KPP;
    @Column("OKPO")
    private @NonNull String OKPO;
    @Column("act")
    private @NonNull String act;
    @Column("tmp_card_type")
    private @NonNull String tmpCardType;
    @Column("name_jur_eng")
    private @NonNull String nameJurEng;
    @Column("PBOYuL")
    private @NonNull String PBOYuL;
    @Column("birthday")
    private @NonNull String birthday;
    @Column("grade")
    private @NonNull String grade;
    @Column("last_name")
    private @NonNull String lastName;
    @Column("first_name")
    private @NonNull String firstName;
    @Column("middle_name")
    private @NonNull String middleName;
    @Column("title")
    private @NonNull String title;
    @Column("last_name_e")
    private @NonNull String lastNameE;
    @Column("first_name_e")
    private @NonNull String firstNameE;
    @Column("middle_name_e")
    private @NonNull String middleNameE;
    @Column("country")
    private @NonNull String country;
    @Column("zip")
    private @NonNull String zip;
    @Column("region")
    private @NonNull String region;
    @Column("area")
    private @NonNull String area;
    @Column("town")
    private @NonNull String town;
    @Column("street")
    private @NonNull String street;
    @Column("home")
    private @NonNull String home;
    @Column("building")
    private @NonNull String building;
    @Column("flat")
    private @NonNull String flat;
    @Column("live_country")
    private @NonNull String liveCountry;
    @Column("live_zip")
    private @NonNull String liveZip;
    @Column("live_region")
    private @NonNull String liveRegion;
    @Column("live_area")
    private @NonNull String liveArea;
    @Column("live_town")
    private @NonNull String liveTown;
    @Column("live_street")
    private @NonNull String liveStreet;
    @Column("live_home")
    private @NonNull String liveHome;
    @Column("live_building")
    private @NonNull String liveBuilding;
    @Column("live_flat")
    private @NonNull String liveFlat;
    @Column("country_e")
    private @NonNull String countryE;
    @Column("zip_d")
    private @NonNull String zipD;
    @Column("region_e")
    private @NonNull String regionE;
    @Column("area_e")
    private @NonNull String areaE;
    @Column("town_e")
    private @NonNull String townE;
    @Column("street_e")
    private @NonNull String streetE;
    @Column("home_e")
    private @NonNull String homeE;
    @Column("building_e")
    private @NonNull String buildingE;
    @Column("flat_e")
    private @NonNull String flatE;
    @Column("locations")
    private @NonNull String locations;
    @Column("unid")
    private @NonNull String unid;
    @Column("doc_par")
    private @NonNull String docPar;
    @Column("link_LN")
    private @NonNull String linkLN;
    @Column("id_client_update_queue")
    private @NonNull String idClientUpdateQueue;
    @Column("id_client")
    private @NonNull String idClient;
    @Column("modified_user")
    private @NonNull String modifiedUser;
    @Column("ur_phone")
    private @NonNull String urPhone;
    @Column("live_phone_city")
    private @NonNull String livePhoneCity;
    @Column("phone")
    private @NonNull String phone;
    @Column("is_customer")
    private @NonNull String isCustomer;
    @Column("structure")
    private @NonNull String structure;
    @Column("structure_unid")
    private @NonNull String structureUnid;
    @Column("contr_role")
    private @NonNull String contrRole;
    @Transient
    public boolean newCard;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("name_jur", nameJur)
                .append("type", type)
                .append("tmp_prizn", lastName)
                .append("prizn",middleName)
                .append("forma", unid)
                .append("forma_full", formaFull)
                .append("INN", INN)
                .append("KPP", KPP)
                .append("OKPO",OKPO)
                .append("act",act)
                .append("tmp_card_type",tmpCardType)
                .append("name_jur_eng",nameJurEng)
                .append("PBOYuL",PBOYuL)
                .append("birthday",birthday)
                .append("grade",grade)
                .append("last_name",lastName)
                .append("first_name",firstName)
                .append("middle_name",middleName)
                .append("title",title)
                .append("last_name_e",lastNameE)
                .append("first_name_e",firstNameE)
                .append("middle_name_e",middleNameE)
                .append("country",country)
                .append("zip",zip)
                .append("region",region)
                .append("area",area)
                .append("town",town)
                .append("street",street)
                .append("home",home)
                .append("building",building)
                .append("flat",flat)
                .append("live_country",liveCountry)
                .append("live_zip",liveZip)
                .append("live_region",liveRegion)
                .append("live_area",liveArea)
                .append("live_town",liveTown)
                .append("live_street",liveStreet)
                .append("live_home",liveHome)
                .append("live_building",liveBuilding)
                .append("live_flat",liveFlat)
                .append("country_e",countryE)
                .append("zip_d",zipD)
                .append("region_e",regionE)
                .append("area_e",areaE)
                .append("town_e",townE)
                .append("street_e",streetE)
                .append("home_e",homeE)
                .append("building_e",buildingE)
                .append("flat_e",flatE)
                .append("locations",locations)
                .append("unid",unid)
                .append("doc_par",docPar)
                .append("link_LN",linkLN)
                .append("id_client_update_queue",idClientUpdateQueue)
                .append("id_client",idClient)
                .append("modified_user",modifiedUser)
                .append("ur_phone",urPhone)
                .append("live_phone_city",livePhoneCity)
                .append("phone",phone)
                .append("is_customer",isCustomer)
                .append("structure",structure)
                .append("structure_unid",structureUnid)
                .append("contr_role",contrRole)
                .toString();
    }

    @Override
    public Integer getId() {
        return cardId;
    }

    @Override
    @Transient
    public boolean isNew() {
        return this.newCard || cardId == null;
    }

}
