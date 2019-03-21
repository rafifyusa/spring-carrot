package com.mitrais.jpqi.springcarrot.controller;

import com.mitrais.jpqi.springcarrot.model.*;
import com.mitrais.jpqi.springcarrot.service.EmployeeServiceUsingDB;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("api/employees")
public class EmployeeController {
    private ServletContext servletContext;

    private EmployeeServiceUsingDB employeeServiceUsingDB;

    public EmployeeController(EmployeeServiceUsingDB employeeServiceUsingDB) {
        this.employeeServiceUsingDB = employeeServiceUsingDB;
    }

    // Create new
    @PostMapping
    public void create(@RequestBody Employee employee) {
        employeeServiceUsingDB.createEmployee(employee);
    }

    // Update
    @PutMapping("/{id}")
    public void update(@PathVariable("id") String id, @RequestBody Employee employee) {
        employeeServiceUsingDB.updateEmployee(id, employee);
    }

    @PatchMapping("admin")
    public void makeAdmin(@RequestParam String id) {
        employeeServiceUsingDB.makeEmployeeAsAdmin(id);
    }

    @PatchMapping("revoke")
    public void revokeAdmin(@RequestParam String id, @RequestBody Employee role) {
        employeeServiceUsingDB.revokeEmployeefromAdmin(id, role);
    }

    // Delete employee
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") String id) {
        employeeServiceUsingDB.deleteEmployee(id);
    }

    // Delete employee's group
    @DeleteMapping("/delgroup/{id}")
    public void deleteGroupFromEmployee (@PathVariable String id, @RequestBody Group group) {
        employeeServiceUsingDB.deleteEmployeeGroup( id, group);}

    //inserting groups to an employee
    @PatchMapping("/addgroup/{id}")
    public void insertGroup(@RequestBody List<Group> group, @PathVariable String id){
        employeeServiceUsingDB.insertMemberToGroup(id, group);
    }

    // Patch
    @PatchMapping("/{id}")
    public void partialUpdate(@PathVariable("id") String id, @RequestBody Employee employee) {
        employeeServiceUsingDB.partialUpdateEmployee(id, employee);
    }

    //-----------------------------------------GET MAPPING GROUP----------------------//
    // Get All
    @GetMapping
    public List<Employee> get() {
        return employeeServiceUsingDB.getAllEmployee();
    }

    // Get by Id
    @GetMapping("/{id}")
    public Employee getById(@PathVariable("id") String id) {
        return employeeServiceUsingDB.getEmployeeById(id);
    }

    // Get birthday list of all employee
    @GetMapping("role")
    public List<Employee> getBirthday(@RequestParam String role) {
        return employeeServiceUsingDB.getStaffRole(role);
    }

    // Get recent (2 days) birthday of all employees
    @GetMapping("recentdob")
    public List<Basket> getByRecentDOB() {
        return employeeServiceUsingDB.getRecentDOB();
    }

    // Get employee group
    @GetMapping("groups")
    public List<GroupCount> getAllGroups() {
        return employeeServiceUsingDB.getAllEmployeeGroups();
    }

    @GetMapping("group-member/{id}")
    public List<Employee> getAllMemberOfAGroup(@PathVariable String id) {
        return employeeServiceUsingDB.getGroupMember(id);
    }

    @GetMapping("nostaffgroup")
    public List<Employee> getAllEmployeeWithoutGroup() {
        return employeeServiceUsingDB.findAllEmployeeWithoutStaffGroup();
    }

    @GetMapping("nomanagementgroup")
    public List<Employee> getAllManagerWithoutGroup() {
        return employeeServiceUsingDB.findAllManagerWithoutManagementGroup();
    }

    //use this to show the list of employee based on their spv level
    @GetMapping("spvlevel")
    public List<Employee> getSpvByLevel(@RequestParam String spvLevel){
        return employeeServiceUsingDB.getEmployeeBySpvLevel(spvLevel);
    }

    @GetMapping("credential")
    public Employee getEmployeeByEmailAndPass(@RequestParam String email, @RequestParam String password) {
        return employeeServiceUsingDB.findByEmailAddressAndPassword(email, password);
    }

    //---------------------------- UPLOAD IMAGE -----------------------------//
    @PostMapping("upload")
    public void uploadImage(@RequestBody String imageString) {
//        System.out.println(imageString.replace("imageString=", ""));
//        employeeServiceUsingDB.storeImage(imageString.replace("imageString=", ""));
    }

    @PostMapping("uploadImage/{id}")
    public void patchUploadImage(@RequestBody Map<String, String> param, @PathVariable String id) {
        System.out.println(param.get("img"));
        employeeServiceUsingDB.picturePatch(param.get("img"), id);
    }

    //---------------------------- TEST BED ---------------------------------//

    /**
     * Get member amount of staff group
     * From PBI -> list all existing Staff groups ordered by Staff amount
     * @return map
     */
    @GetMapping("getGroup")
    public HashMap<String, Integer> getGroupIdByMemberAmount(){
        return employeeServiceUsingDB.getGroupMemberAmount();
    }
}
