package com.andpostman.routeprocessor.model;

import lombok.*;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "code_cur_dep", schema = "dbo")
@NoArgsConstructor
@Getter
@RequiredArgsConstructor
@EqualsAndHashCode(exclude = "id")
public class CodeCurDep implements Persistable<Integer> {
    @Id
    private Integer id;
    @Column("code_cur_dep")
    private @NonNull String codeCurDep;
    @Column("currency_form_id")
    private @NonNull int currencyFormId;

    @Transient
    public boolean newCodeCurDep;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("xcode_cur_dep", codeCurDep)
                .append("currency_form_id", currencyFormId)
                .toString();
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    @Transient
    public boolean isNew() {
        return this.newCodeCurDep|| id == null;
    }
}
