package com.mitrais.jpqi.springcarrot.controller;
import com.mitrais.jpqi.springcarrot.model.*;
import com.mitrais.jpqi.springcarrot.responses.AchievementResponse;
import com.mitrais.jpqi.springcarrot.responses.BasketResponse;
import com.mitrais.jpqi.springcarrot.responses.EmployeeResponse;
import com.mitrais.jpqi.springcarrot.service.EmployeeServiceUsingDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@CrossOrigin
@RequestMapping("api/employees")
public class EmployeeController {
    @Autowired
    private EmployeeServiceUsingDB employeeServiceUsingDB;
    // Create newupdgroup
    @PostMapping
    public EmployeeResponse create(@RequestBody Employee employee) {
        return employeeServiceUsingDB.createEmployee(employee);
    }

    // Update
    @PutMapping("/{id}")
    public EmployeeResponse update(@PathVariable("id") String id, @RequestBody Employee employee) {
        return employeeServiceUsingDB.updateEmployee(id, employee);
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
    public EmployeeResponse delete(@PathVariable("id") String id) {
        return employeeServiceUsingDB.deleteEmployee(id);
    }

    // Delete employee's group
    @PatchMapping("/delgroup/{id}")
    public EmployeeResponse deleteGroupFromEmployee (@PathVariable String id, @RequestBody Group group) {
        return employeeServiceUsingDB.deleteEmployeeGroup( id, group);}

    //inserting groups to an employee
    @PatchMapping("/addgroup/{id}")
    public EmployeeResponse insertGroup(@RequestBody List<Group> group, @PathVariable String id){
        return employeeServiceUsingDB.insertMemberToGroup(id, group);
    }

    // Delete achievement from employee
    @PatchMapping("/del-achievement/{id}")
    public void deleteAchievementFromEmployee (@PathVariable String id, @RequestBody Achievement achievement) {
        employeeServiceUsingDB.deleteAchievementGroup( id, achievement);}

    //inserting achievement to employee
    @PatchMapping("/add-achievement/{id}")
    public void insertAchievement(@RequestBody Achievement achievements, @PathVariable String id){
        employeeServiceUsingDB.addAchievementToEmployee(id, achievements);
    }

    // Patch
    @PatchMapping("/{id}")
    public EmployeeResponse partialUpdate(@PathVariable("id") String id, @RequestBody Employee employee) {
        return employeeServiceUsingDB.partialUpdateEmployee(id, employee);
    }

    //-----------------------------------------GET MAPPING GROUP----------------------//
    // Get All
    @GetMapping
    public EmployeeResponse get() {
        return employeeServiceUsingDB.getAllEmployee();
    }

    // Get by Id
    @GetMapping("/{id}")
    public EmployeeResponse getById(@PathVariable("id") String id) {
        return employeeServiceUsingDB.getEmployeeById(id);
    }

    // Get birthday list of all employee
    @GetMapping("role")
    public EmployeeResponse getBirthday(@RequestParam String role) {
        return employeeServiceUsingDB.getStaffRole(role);
    }

    // Get recent (2 days) birthday of all employees
    @GetMapping("recentdob")
    public BasketResponse getByRecentDOB() {
        return employeeServiceUsingDB.getRecentDOB();
    }

    // Get employee group
    @GetMapping("groups")
    public List<GroupCount> getAllGroups() {
        return employeeServiceUsingDB.getAllEmployeeGroups();
    }

    @GetMapping("group-member/{id}")
    public EmployeeResponse getAllMemberOfAGroup(@PathVariable String id) {
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

    @GetMapping("achievement")
    public AchievementResponse getAnEmployeeAchivement(@RequestParam String empId) {
        return employeeServiceUsingDB.findAnEmployeeAchievement(empId);
    }

    //---------------------------- UPLOAD IMAGE -----------------------------//
    @PostMapping("upload")
    public void uploadImage(@RequestBody String imageString) {
//        System.out.println(imageString.replace("imageString=", ""));
//        employeeServiceUsingDB.storeImage(imageString.replace("imageString=", ""));
    }

    @PostMapping("uploadImage/{id}")
    public EmployeeResponse patchUploadImage(@RequestBody Map<String, String> param, @PathVariable String id) {
        System.out.println(param.get("img"));
        return employeeServiceUsingDB.picturePatch(param.get("img"), id);
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
