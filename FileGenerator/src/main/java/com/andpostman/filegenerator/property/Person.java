package com.andpostman.filegenerator.property;

import lombok.*;
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
