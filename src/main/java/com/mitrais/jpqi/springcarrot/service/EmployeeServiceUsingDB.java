package com.mitrais.jpqi.springcarrot.service;

import com.mitrais.jpqi.springcarrot.model.Employee;
import com.mitrais.jpqi.springcarrot.model.GroupCount;
import com.mitrais.jpqi.springcarrot.repository.EmployeeRepository;
//import jdk.vm.ci.meta.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;
import java.util.stream.Collectors;


@Service
public class EmployeeServiceUsingDB implements EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    MongoTemplate mongoTemplate;

    public EmployeeServiceUsingDB(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public void createEmployee(Employee employee) {
        employeeRepository.save(employee);
    }

    @Override
    public void deleteEmployee(int id) {
//        employeeRepository.delete(employeeRepository.findById(id));
//        Optional<Employee> temp = employeeRepository.findById(id);
//        employeeRepository.delete(temp);
        employeeRepository.deleteById(id);
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
//        Optional<Employee> temp = Optional.ofNullable(employeeRepository.findById(id));
        Optional<Employee> temp = employeeRepository.findById(id);
        if (temp.isPresent()) {
            return temp.get();
        }
        return null;
    }

    @Override
    public List<GroupCount> getAllEmployeeGroups() {

        Aggregation agg = newAggregation(
                group("group").count().as("total"),
                project("total").and("group").previousOperation(),
                sort(Sort.Direction.DESC, "total")
        );

        //convert to agg result to list
        AggregationResults<GroupCount> groupResult =
                mongoTemplate.aggregate(agg, Employee.class, GroupCount.class);

        List<GroupCount> groups = groupResult.getMappedResults();

        return groups;

    }

    public Map<String, String> findEmployeeByCredential(Map<String, String> body) {
        List<Employee> emp = employeeRepository.findAll().stream()
                .filter(e -> e.getEmailAddress().equals(body.get("email")))
                .filter(e->e.getPassword().equals(body.get("password")))
                .collect(Collectors.toList());

        Map<String, String> kembalian = new HashMap<>();
        Map<String, String> pegawai = new HashMap<>();

        if (emp.size() > 0) {
            kembalian.put("status", "berhasil");
            kembalian.put("message", "employee ditemukan");
            emp.forEach(e -> {
                pegawai.put("id", String.valueOf(e.getId()));
                pegawai.put("name", e.getName());
                pegawai.put("alamat", e.getAddress());
                pegawai.put("emailAddress", e.getEmailAddress());
                pegawai.put("profilePicture", e.getProfilePicture());
            });
            kembalian.put("employee", pegawai.toString());
        } else {
            kembalian.put("status", "gagal");
            kembalian.put("message", "employee tidak ditemukan");
        }
        return kembalian;
    }

    @Override
    public List<Employee> getRecentDOB() {
        LocalDate localDate = LocalDate.now();
        LocalDate recentDOB1 = localDate.minusDays(1);
        LocalDate recentDOB2 = localDate.minusDays(2);

        String date0 = localDate.toString().substring(5);
        String date1 = recentDOB1.toString().substring(5);
        String date2 = recentDOB2.toString().substring(5);
        System.out.println("0" + date0);
        System.out.println("1" +date1);
        System.out.println("2" +date2);
        List<Employee> emp = employeeRepository.findAll().stream()
                .filter(e -> e.getDob().toString().substring(5).equals(date1) || e.getDob().toString().substring(5).equals(date2)|| e.getDob().toString().substring(5).equals(date0))
                .collect(Collectors.toList());
        return emp;
    }

    // PATCH implementation manual version
    @Override
    public void partialUpdateEmployee(int id, Employee employee) {
        Employee temp = employeeRepository.findById(id).orElse(null);

        if (temp != null) {
            if (employee.getId() != 0) {
                temp.setId(employee.getId());
            }
            if (employee.getName() != null) {
                temp.setName(employee.getName());
            }
            if (employee.getDob() != null) {
                temp.setDob(employee.getDob());
            }
            if (employee.getAddress() != null) {
                temp.setAddress(employee.getAddress());
            }
            if (employee.getRole() != null) {
                temp.setRole(employee.getRole());
            }
            if (employee.getPassword() != null) {
                temp.setPassword(employee.getPassword());
            }
            if (employee.getProfilePicture() != null) {
                temp.setProfilePicture(employee.getProfilePicture());
            }
            if (employee.getEmailAddress() != null) {
                temp.setEmailAddress(employee.getEmailAddress());
            }
        }
        employeeRepository.save(temp);

    }

    // List all employee birhday
    @Override
    public List<Employee> getStaffRole(String role) {
        return employeeRepository.findByRole(role);
    }
}

