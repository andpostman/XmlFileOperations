package com.andpostman.filegenerator.property;

import lombok.*;
import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CurrencyForm implements Serializable, JmsMessage {
    String curType;
    String codeCur;
    String xcodeCur;
    String course;
    List<String> curTypeDep;
    List<String> codeCurDep;
    List<String> xcodeCurDep;
    String date;
    int cur;
}
