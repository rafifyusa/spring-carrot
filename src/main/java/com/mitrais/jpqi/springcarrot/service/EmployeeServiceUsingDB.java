package com.mitrais.jpqi.springcarrot.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.google.gson.Gson;
import com.mitrais.jpqi.springcarrot.model.*;
import com.mitrais.jpqi.springcarrot.model.AggregateModel.GroupCount;
import com.mitrais.jpqi.springcarrot.oauth2.JwtTokenProvider;
import com.mitrais.jpqi.springcarrot.repository.BasketRepository;
import com.mitrais.jpqi.springcarrot.repository.EmployeeRepository;
import com.mitrais.jpqi.springcarrot.repository.FreezerRepository;
import com.mitrais.jpqi.springcarrot.responses.AchievementResponse;
import com.mitrais.jpqi.springcarrot.responses.BasketResponse;
import com.mitrais.jpqi.springcarrot.responses.EmployeeResponse;
import com.mitrais.jpqi.springcarrot.responses.Login;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.*;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@Service
public class EmployeeServiceUsingDB implements EmployeeService {
    @Autowired
    JwtTokenProvider jwtTokenProvider;
    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    BasketRepository basketRepository;

    @Autowired
    FreezerRepository freezerRepository;

    @Autowired
    GroupService groupService;

    @Autowired
    MongoTemplate mongoTemplate;

    Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
            "cloud_name", "dc1lp90qy",
            "api_key", "194312298198378",
            "api_secret", "FCxNYbqo0okfaWU_GDPhJdKR0TQ"));

    public EmployeeServiceUsingDB(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public EmployeeResponse createEmployee(Employee employee) {
        EmployeeResponse res = new EmployeeResponse();
        employeeRepository.save(employee);

        if(employee.getRole() == Employee.Role.SENIOR_MANAGER || employee.getRole() == Employee.Role.MANAGER){
            Freezer freezer = new Freezer();
            freezer.setName(employee.getName() + "'s Freezer");
            freezer.setCarrot_amt(0);
            freezer.setCreated_at(LocalDate.now());
            freezer.setEmployee(employee);
            freezerRepository.save(freezer);
        }

        Basket basket = new Basket();
        basket.setCarrot_amt(0);
        basket.setCreated_at(LocalDateTime.now());
        basket.setEmployee(employee);
        basket.setName(employee.getName() + "'s Basket");
        try {
            basketRepository.save(basket);
            res.setStatus(true);
            res.setMessage("Employee successfully inserted");
        } catch (NullPointerException e) {
            res.setStatus(false);
            res.setMessage(e.getMessage());
        }
        return res;
    }

    @Override
    public EmployeeResponse deleteEmployee(String id) {
        EmployeeResponse res = new EmployeeResponse();
        try {
            employeeRepository.deleteById(id);
            res.setStatus(true);
            res.setMessage("Employee successfully removed");
        } catch (NullPointerException e) {
            res.setStatus(false);
            res.setMessage(e.getMessage());
        }
        return res;
    }

    @Override
    public EmployeeResponse updateEmployee(String id, Employee employee) {
        EmployeeResponse res = new EmployeeResponse();
        try {
            employee.setId(id);
            employeeRepository.save(employee);
            res.setStatus(true);
            res.setMessage("Employee successfully updated");
        } catch (NullPointerException e) {
            res.setStatus(false);
            res.setMessage(e.getMessage());
        }
        return res;
    }

    @Override
    public EmployeeResponse getAllEmployee() {
        EmployeeResponse res = new EmployeeResponse();
        res.setStatus(true);
        res.setListEmployee(employeeRepository.findAll());
        if (res.getListEmployee().size() > 0) {
            res.setMessage("Employee found");
        } else{
            res.setMessage("Employee not found");
        }
        return res;
    }

    @Override
    public EmployeeResponse getEmployeeById(String id) {
        EmployeeResponse res = new EmployeeResponse();
        Optional<Employee> temp = employeeRepository.findById(id);
        if (temp.isPresent()) {
            res.setEmployee(temp.get());
            res.setStatus(true);
            res.setMessage("Employee found");
        } else {
            res.setStatus(false);
            res.setMessage("Employee not found");
        }
        return res;
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

    public Login findEmployeeByCredential(Map<String, String> body) {
        Login res = new Login();
        Optional<Employee> employee = employeeRepository
                                        .findByEmailAddressAndPassword(body.get("email"), body.get("password"));
        Map<String, String> kembalian = new HashMap<>();
        if (employee.isPresent()) {
            Employee emp = employee.get();
            List<Employee.Role> role = new ArrayList<>();
            role.add(emp.getRole());
            String token = jwtTokenProvider.createToken(body.get("email"), role);

            Gson gson = new Gson();
            Optional<Basket> basket = basketRepository.findByEmployee(new ObjectId(emp.getId()));
            Freezer freezer = freezerRepository.findByOwner(new ObjectId(emp.getId()));
            res.setStatus(true);
            res.setMessage("employee ditemukan");
            res.setEmployee(emp);
            if (basket.isPresent()) {
                res.setBasket(basket.get());
            }
            res.setFreezer(freezer);
            res.setToken(token);
        } else {
            res.setStatus(false);
            res.setMessage("employee tidak ditemukan");
        }
        return res;
    }

    @Override
    public BasketResponse getRecentDOB() { // get the matching employee's dob with last 2 days.
        BasketResponse res = new BasketResponse();
        LocalDate localDate = LocalDate.now();
        LocalDate recentDOB1 = localDate.minusDays(1);
        LocalDate recentDOB2 = localDate.minusDays(2);

        String date0 = localDate.toString().substring(5);
        String date1 = recentDOB1.toString().substring(5);
        String date2 = recentDOB2.toString().substring(5);
        List<Basket> listBasket = new ArrayList<>();
        List<Employee> emp = employeeRepository.findAll()
                .stream()
                .filter(e -> e.getDob().toString().substring(5).equals(date1) || e.getDob().toString().substring(5).equals(date2)|| e.getDob().toString().substring(5).equals(date0))
                .collect(Collectors.toList());
        emp.forEach(employee -> {
            Optional<Basket> basket1 = basketRepository.findByEmployee(new ObjectId(employee.getId()));
            if (basket1.isPresent()) {
                listBasket.add(basket1.get());
            }
        });
        res.setStatus(true);
        res.setListBasket(listBasket);
        return res;
    }

    // PATCH implementation manual version
    @Override
    public EmployeeResponse partialUpdateEmployee(String id, Employee employee) {
        EmployeeResponse res = new EmployeeResponse();
        Employee temp = employeeRepository.findById(id).orElse(null);
        if (temp != null) {
            if (employee.getId() != null) {
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
            if (employee.getSupervisor() != null) {
                temp.setSupervisor(employee.getSupervisor());
            }
            if (employee.getSpvLevel() != null) {
                temp.setSpvLevel(employee.getSpvLevel());
            }
            if (employee.getGroup() != null) {
                temp.setGroup(employee.getGroup());
            }
        }
        try {
            employeeRepository.save(temp);
            res.setStatus(true);
            res.setMessage("employee successfully updated");
            res.setEmployee(temp);
        } catch (NullPointerException e) {
            res.setStatus(false);
            res.setMessage(e.getMessage());
        }
        return res;
    }

    // List employee birthday by role
    @Override
    public EmployeeResponse getStaffRole(String role) {
        EmployeeResponse res = new EmployeeResponse();
        List<Employee>  list = employeeRepository.findByRole(role);
        res.setStatus(true);
        res.setListEmployee(list);
        return res;
    }
    public EmployeeResponse getGroupMember(String id) {
        EmployeeResponse res = new EmployeeResponse();
        List<Employee> members = employeeRepository.findByGroupId(new ObjectId(id));
        if (members.isEmpty()){
            System.out.println("member empty");
            res.setStatus(false);
            res.setMessage("member empty");
            res.setListEmployee(members);
        } else {
            res.setStatus(true);
            res.setMessage("member found");
            res.setListEmployee(members);
        }

        return res;
    }

    @Override
    public List<Employee> getEmployeeBySpvLevel(String spvlevel){
        return employeeRepository.findBySpvLevel(spvlevel);
    }

    public EmployeeResponse insertMemberToGroup(String id, List<Group> group) {
        EmployeeResponse res = new EmployeeResponse();
        Optional<Employee> employee = employeeRepository.findById(id);
        if (employee.isPresent()) {
            Employee emp = employee.get();
            if (emp.getGroup() == null) {
                emp.setGroup(new ArrayList<>());
            }
//
            group.forEach(e -> emp.getGroup().add(e));
            try {
                employeeRepository.save((emp));
                res.setStatus(true);
                res.setMessage("Employee successfully inserted");
            } catch (NullPointerException e) {
                res.setStatus(false);
                res.setMessage(e.getMessage());
            }
        } else {
            res.setStatus(false);
            res.setMessage("employee not found");
        }
        return res;
    }

    public EmployeeResponse deleteEmployeeGroup(String id, Group group){
        EmployeeResponse res = new EmployeeResponse();
        Optional<Employee> employee = employeeRepository.findById(id);
        if (employee.isPresent()) {
            Employee emp = employee.get();
            if (emp.getGroup() != null) {
                emp.getGroup().remove(group);
            }

            try {
                employeeRepository.save(emp);
                res.setStatus(true);
                res.setMessage("Employee successfully deleted");
            } catch (NullPointerException e) {
                res.setStatus(false);
                res.setMessage(e.getMessage());
            }
        } else{
            res.setStatus(false);
            res.setMessage("employee not found");
        }
        return res;
    }

    public List<Employee> findAllEmployeeWithoutStaffGroup() {
        List<Employee> employee = employeeRepository.findAll();
        List<Employee> employeeWithoutGroup = new ArrayList<>();

        employee.stream().filter(emp -> emp.getRole() == Employee.Role.STAFF)
                .forEach(emp1 -> {
                    if (emp1.getGroup() == null) {
                        employeeWithoutGroup.add(emp1);
                    }
                });
        return employeeWithoutGroup;
    }

    public List<Employee> findAllManagerWithoutManagementGroup() {
        List<Employee> employee = employeeRepository.findAll();
        List<Employee> employeeWithoutGroup = new ArrayList<>();

        employee.stream().filter(emp -> emp.getRole() == Employee.Role.MANAGER)
                .forEach(emp1 -> {
                    if (emp1.getGroup() == null) {
                        employeeWithoutGroup.add(emp1);
                    }
                });
        return employeeWithoutGroup;
    }

    public Employee findByEmailAddressAndPassword(String user, String pass) {
        Optional<Employee> e = employeeRepository.findByEmailAddressAndPassword(user,pass);
        return e.get();
    }

    public EmployeeResponse makeEmployeeAsAdmin (String id) {
        EmployeeResponse res = new EmployeeResponse();
        Employee emp = getEmployeeById(id).getEmployee();
        emp.setRole(Employee.Role.ADMIN);
        try {
            employeeRepository.save(emp);
            res.setStatus(true);
            res.setMessage("Employee's Role changed to ADMIN");
        }catch (NullPointerException e) {
            res.setStatus(false);
            res.setMessage(e.getMessage());
        }
        return res;
    }

    public EmployeeResponse revokeEmployeefromAdmin(String id) {
        EmployeeResponse res = new EmployeeResponse();
        Employee emp = getEmployeeById(id).getEmployee();
        emp.setRole(Employee.Role.STAFF);
        try {
            employeeRepository.save(emp);
            res.setStatus(true);
            res.setMessage("Employee's Role changed to ADMIN");
        }catch (NullPointerException e) {
            res.setStatus(false);
            res.setMessage(e.getMessage());
        }
        return res;
    }

    //Upload File
    public String storeImage(String imageString, String id) {
        byte[] imageByteArray = Base64.getDecoder().decode(imageString);
        String url = "";
        try {
            Map uploadResult = cloudinary.uploader().upload(imageByteArray, ObjectUtils.asMap("folder", "pictures/",
                    "public_id", id));
            url = (String) uploadResult.get("secure_url");
            System.out.println(uploadResult.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return  url;
    }

    // Patch upload images
    public EmployeeResponse picturePatch (String imageString, String id) {
        EmployeeResponse res = new EmployeeResponse();
        // Find employee first
        Employee employee = employeeRepository.findById(id).orElse(null);

        if (employee != null) {
            // Everything except profilePicture field was not changed during process
            employee.setName(employee.getName());
            employee.setDob(employee.getDob());
            employee.setAddress(employee.getAddress());
            employee.setRole(employee.getRole());
            employee.setPassword(employee.getPassword());
            employee.setEmailAddress(employee.getEmailAddress());
            employee.setGroup(employee.getGroup());
            employee.setSupervisor(employee.getSupervisor());
            employee.setSpvLevel(employee.getSpvLevel());

            // set profile picture with profile picture location
            String profilePictureLoc = storeImage(imageString, id);
            employee.setProfilePicture(profilePictureLoc);
        }
        try {
            employeeRepository.save(employee);
            res.setStatus(true);
            res.setMessage("image successfully updated");
            res.setEmployee(employee);
        } catch (NullPointerException e) {
            res.setStatus(false);
            res.setMessage(e.getMessage());
        }
        return res;
    }

    /**
     * Get member amount of each group
     * @return map contain key-value pair of group id and number of member in the group
     */
    public HashMap<String, Integer> getGroupMemberAmount() {
        // Create empty hashmap to holding key-value pair
        HashMap<String, Integer> map = new HashMap<String, Integer>();

        // List all employee who has group
        List <Employee> employees = employeeRepository.findAll().stream()
                .filter(e -> e.getGroup() != null)
                .collect(Collectors.toList());

        // Iterate over all employee in employees list
        employees.forEach(e -> {
            // get group set of an employee
            List<Group> mySet = e.getGroup();
            mySet.forEach(g -> {
                String key = g.getId();
                // check if contain the keys or not
                if (!map.containsKey(key)) {
                    map.put(key, 1); // buat baru
                } else {
                    map.put(key, map.get(key) + 1); // +1 jika ditemukan
                }
            });
        });
        return map;
    }

    public void addAchievementToEmployee (String id, Achievement achievement) {
        Optional<Employee> employee = employeeRepository.findById(id);
        if (employee.isPresent()) {
            Employee emp = employee.get();
            if (emp.getAchievement() == null) {
                emp.setAchievement(new ArrayList<>());
            }
            emp.getAchievement().add(achievement);
            employeeRepository.save((emp));
        }
    }

    public void deleteAchievementFromEmployee(String id, Achievement achievement){
        Optional<Employee> employee = employeeRepository.findById(id);
        if (employee.isPresent()) {
            Employee emp = employee.get();
            if (emp.getAchievement() != null) {
                emp.getAchievement().remove(achievement);
            }
            employeeRepository.save(emp);
        }
    }

    public AchievementResponse findAnEmployeeAchievement(String id) {
        AchievementResponse res = new AchievementResponse();
        res.setStatus(true);
        res.setMessage("List Achievement");
        res.setListAchievement(getEmployeeById(id).getEmployee().getAchievement());
        return res;
    }

    public EmployeeResponse getStaffRoles(List<Employee> roles) {
        EmployeeResponse res = new EmployeeResponse();
        String[] a = new String[roles.size()];
        final int[] i = {0};
        roles.forEach(e -> {
            a[i[0]] = e.getRole().toString();
            i[0]++;
        });

        List<Employee> mm = employeeRepository.findByRoles(a);
        res.setStatus(true);
        res.setMessage("List of Employee");
        res.setListEmployee(mm);
        return res;
    }

    public EmployeeResponse getMultipleGroupMember(List<Group> group) {
        EmployeeResponse res = new EmployeeResponse();
        List<Employee> empList = new ArrayList<>();
        group.forEach(g -> {
            List<Employee> ee = employeeRepository.findByGroupId(new ObjectId(g.getId()));
            empList.addAll(ee);
        });
        if (empList.isEmpty()) {
            System.out.println("member empty");
            res.setStatus(false);
            res.setMessage("member empty");
            res.setListEmployee(empList);
        } else {
            res.setStatus(true);
            res.setMessage("member found");
            res.setListEmployee(empList);
        }

        return res;
    }
    public List<Employee> findAllEmployeeHavingBirthdayToday(){
        LocalDate today = LocalDate.now();
        String date = today.toString().substring(5);

        List<Employee> employeesHavingBirthday = employeeRepository.findAll().stream()
                .filter(emp -> emp.getDob().toString().substring(5).equals(date))
                .collect(Collectors.toList());
        return  employeesHavingBirthday;
    }

    public int checkBirthdayCarrotEligibility(String id){
        Employee emp = getEmployeeById(id).getEmployee();
        List<Group> groups= emp.getGroup();
        int eligibleGroup = 0;

        Award birthdayAward = new Award();
        birthdayAward.setId("5c943ae5b73f4133b45a1da8");
        for(int i = 0; i< groups.size(); i++){
            if (groups.get(i).getAwards() == null){
                System.out.println("dont have birthday award");
            }
            else if (groups.get(i).getAwards().contains(birthdayAward)){
                eligibleGroup+=1;
            }
        }

        return eligibleGroup;
    }
}



