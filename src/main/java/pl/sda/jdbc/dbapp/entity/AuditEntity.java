package pl.sda.jdbc.dbapp.entity;

import lombok.Getter;

import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;
@Getter
@MappedSuperclass
public abstract class AuditEntity extends BaseEntity{
    @Temporal(TemporalType.TIMESTAMP)
    private Date added;
}
