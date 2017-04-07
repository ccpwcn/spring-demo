package com.sinoiov.lhjh.beans;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 雇员类
 * Created by lidawei on 2017/4/7.
 */
public class Employee implements Serializable {
    private static final long serialVersionUID = -3151268981719789527L;
    private long id;
    private String firstName;
    private String lastName;
    private BigDecimal salary;

    public Employee() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }
}
