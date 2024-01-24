package com.andpostman.xmlclassgenerator.property;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
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
