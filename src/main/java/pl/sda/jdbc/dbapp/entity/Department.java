package pl.sda.jdbc.dbapp.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;

//@Entity
@Getter
@Setter
@ToString
public class Department {
    @Id
    private int deptno;

    private String dname;

    private String location;

}
