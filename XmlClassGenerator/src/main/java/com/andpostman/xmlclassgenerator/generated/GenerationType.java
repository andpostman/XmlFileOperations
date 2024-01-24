package com.andpostman.xmlclassgenerator.generated;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum GenerationType {

    VALID_ONE(1),
    VALID_THREE(3),
    VALID_CAPACITY(25_000),
    XSD_ERROR(1),
    FORM_TYPE_ERROR(1);

    private int count;

    public void setCount(int count) {
        this.count = count;
    }
}
