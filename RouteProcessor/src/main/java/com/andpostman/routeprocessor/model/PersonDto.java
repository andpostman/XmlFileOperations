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
import java.time.LocalDateTime;

@Table(name = "person", schema = "dbo")
@NoArgsConstructor
@Getter
@Setter
@RequiredArgsConstructor
@EqualsAndHashCode(exclude = "personId")
public class PersonDto implements Persistable<Integer> {

    @Id
    private Integer personId;
    @Column("date_born")
    private @NonNull LocalDate dateBorn;
    @Column("first_name")
    private @NonNull String firstName;
    @Column("last_name")
    private @NonNull String lastName;
    @Column("middle_name")
    private @NonNull String middleName;
    @Column("UN_ID")
    private @NonNull String unid;
    @Column("internet_address")
    private @NonNull String internetAddress;
    @Column("modified_date_time")
    private @NonNull LocalDateTime modifiedDateTime;
    @Column("insert_date")
    private @NonNull LocalDateTime insertDate;
    @Column("insert_user")
    private @NonNull String insertUser;
    @Transient
    public boolean newPerson;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("date_born", dateBorn)
                .append("first_name", firstName)
                .append("last_name", lastName)
                .append("middle_name",middleName)
                .append("UN_ID", unid)
                .append("internet_address", internetAddress)
                .append("modified_date_time", modifiedDateTime)
                .append("insert_date", insertDate)
                .append("insert_user",insertUser)
                .toString();
    }

    @Override
    public Integer getId() {
        return personId;
    }

    @Override
    @Transient
    public boolean isNew() {
        return this.newPerson || personId == null;
    }

}
