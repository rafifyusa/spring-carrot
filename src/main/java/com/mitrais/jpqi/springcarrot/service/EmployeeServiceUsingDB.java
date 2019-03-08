package com.mitrais.jpqi.springcarrot.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mitrais.jpqi.springcarrot.model.Employee;
import com.mitrais.jpqi.springcarrot.repository.EmployeeRepository;
import jdk.nashorn.internal.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceUsingDB implements EmployeeService{
    @Autowired
    EmployeeRepository employeeRepository;
    public EmployeeServiceUsingDB(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }
    @Override
    public void createEmployee(Employee employee) {
        employeeRepository.save(employee);
    }
    @Override
    public void deleteEmployee(int id) {
        employeeRepository.delete(employeeRepository.findById(id));
    }
    @Override
    public void updateEmployee(int id, Employee employee) {
        employee.setId(id);
        employeeRepository.save(employee);
    }
    @Override
    public List<Employee> getAllEmployee() {
        return employeeRepository.findAll();
    }
    @Override
    public Employee getEmployeeById(int id) {
        Optional<Employee> temp = Optional.ofNullable(employeeRepository.findById(id));
        if(temp.isPresent()) {
            return temp.get();
        }
        return null;
    }

    @Override
    public Map<String, String> findEmployeeByCredential(Map<String, String> body) {
        List<Employee> emp = employeeRepository.findAll().stream().filter(e -> e.getName().equals(body.get("name"))).filter(e->e.getAddress().equals(body.get("address"))).collect(Collectors.toList());
        Map<String, String> kembalian = new HashMap<>();
        Map<String, String> pegawai = new HashMap<>();
        if(emp.size() > 0) {
            kembalian.put("status", "berhasil");
            kembalian.put("message", "employee ditemukan");
            emp.forEach(e -> {
                pegawai.put("name", e.getName());
                pegawai.put("alamat", e.getAddress());
                pegawai.put("role", e.getRole());
            });
            kembalian.put("employee", pegawai.toString());
        } else {
            kembalian.put("status", "gagal");
            kembalian.put("message", "employee tidak ditemukan");
        }
        return kembalian;
    }
}
