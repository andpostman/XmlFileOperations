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
public class Person implements Serializable, JmsMessage {
    String revisions;
    String additionalInfo;
    String adminPersonUid;
    String chief;
    String contactMan;
    String dateBorn;
    String firstName;
    String lastName;
    String middleName;
    String personId;
    String title;
    String unid;
    String internetAddress;
    String phone;
    String callSign;
}
