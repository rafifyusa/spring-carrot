package com.mitrais.jpqi.springcarrot.responses;

import com.mitrais.jpqi.springcarrot.model.Employee;
//import jdk.internal.dynalink.linker.LinkerServices;

import java.util.List;

public class EmployeeResponse extends Response {
    Employee employee;
    List<Employee> listEmployee;

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public List<Employee> getListEmployee() {
        return listEmployee;
    }

    public void setListEmployee(List<Employee> listEmployee) {
        this.listEmployee = listEmployee;
    }
}
