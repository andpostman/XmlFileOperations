package com.andpostman.routeprocessor.model;

import lombok.*;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import java.time.LocalDate;

@Table(name = "currency_form", schema = "dbo")
@NoArgsConstructor
@Getter
@Setter
@RequiredArgsConstructor
@EqualsAndHashCode(exclude = "currencyFormId")
public class CurrencyFormDto implements Persistable<Integer> {

    @Id
    private Integer currencyFormId;
    @Column("cur_type")
    private @NonNull String curType;
    @Column("code_cur")
    private @NonNull String codeCur;
    @Column("xcode_cur")
    private @NonNull String xcodeCur;
    @Column("course")
    private @NonNull String course;
    @Column("date")
    private @NonNull LocalDate date;
    @Column("cur")
    private @NonNull int cur;

    @Transient
    public boolean newCurrencyForm;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("cur_type", curType)
                .append("code_cur", codeCur)
                .append("xcode_cur",xcodeCur)
                .append("course", course)
                .append("date", date)
                .append("cur",cur)
                .toString();
    }

    @Override
    public Integer getId() {
        return currencyFormId;
    }

    @Override
    @Transient
    public boolean isNew() {
        return this.newCurrencyForm || currencyFormId == null;
    }

}
