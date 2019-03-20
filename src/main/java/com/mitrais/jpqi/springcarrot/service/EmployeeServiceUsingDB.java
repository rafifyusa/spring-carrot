package com.mitrais.jpqi.springcarrot.service;

import com.google.gson.Gson;
import com.mitrais.jpqi.springcarrot.model.Basket;
import com.mitrais.jpqi.springcarrot.model.Employee;
import com.mitrais.jpqi.springcarrot.model.Group;
import com.mitrais.jpqi.springcarrot.model.GroupCount;
import com.mitrais.jpqi.springcarrot.repository.BasketRepository;
import com.mitrais.jpqi.springcarrot.repository.EmployeeRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.stereotype.Service;
import java.io.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@Service
public class EmployeeServiceUsingDB implements EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    BasketRepository basketRepository;

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
    public void deleteEmployee(String id) {
        employeeRepository.deleteById(id);
    }

    @Override
    public void updateEmployee(String id, Employee employee) {
        employee.setId(id);
        employeeRepository.save(employee);
    }

    @Override
    public List<Employee> getAllEmployee() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee getEmployeeById(String id) {
        Optional<Employee> temp = employeeRepository.findById(id);
        return temp.orElse(null);
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
        Optional<Employee> employee = employeeRepository.findByEmailAddressAndPassword(body.get("email"), body.get("password"));
//        List<Employee> emp = employeeRepository.findAll().stream()
//                .filter(e -> e.getEmailAddress().equals(body.get("email")))
//                .filter(e->e.getPassword().equals(body.get("password")))
//                .collect(Collectors.toList());

        Map<String, String> kembalian = new HashMap<>();

        if (employee.isPresent()) {
            Gson gson = new Gson();
            Employee emp = employee.get();
            Optional<Basket> basket = basketRepository.findByEmployee(new ObjectId(emp.getId()));
            if (basket.isPresent()) {
                kembalian.put("basket", gson.toJson(basket.get()));
            }
            kembalian.put("status", "berhasil");
            kembalian.put("message", "employee ditemukan");
            kembalian.put("employee", gson.toJson(emp));
        } else {
            kembalian.put("status", "gagal");
            kembalian.put("message", "employee tidak ditemukan");
        }
        return kembalian;
    }

    @Override
    public List<Basket> getRecentDOB() { // get the matching employee's dob with last 2 days.
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
        return listBasket;
    }

    // PATCH implementation manual version
    @Override
    public void partialUpdateEmployee(String id, Employee employee) {
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
        employeeRepository.save(temp);
    }

    // List employee birthday by role
    @Override
    public List<Employee> getStaffRole(String role) {
        return employeeRepository.findByRole(role);
    }

    @Override
    public List<Employee> getEmployeeByGroup(String group){
        return employeeRepository.findByGroupName(group);
    }

    ///delete if group is only one for each employee
/*    @Override
    public void deleteEmployeeGroup(int id) {
        Employee temp = employeeRepository.findById(id).orElse(null);

        if (temp != null) {
            temp.setGroup(null);
            employeeRepository.save(temp);
        }
    }*/

    @Override
    public List<Employee> getEmployeeBySpvLevel(String spvlevel){
        return employeeRepository.findBySpvLevel(spvlevel);
    }

    public void insertMemberToGroup(String id, List<Group> group) {
        Optional<Employee> employee = employeeRepository.findById(id);
        if (employee.isPresent()) {
            Employee emp = employee.get();
            if (emp.getGroup() == null) {
                emp.setGroup(new HashSet<>());
            }
//
            group.forEach(e -> emp.getGroup().add(e));
            employeeRepository.save((emp));
        }
    }

    public void deleteEmployeeGroup(String id, Group group){
        Optional<Employee> employee = employeeRepository.findById(id);
        if (employee.isPresent()) {
            Employee emp = employee.get();
            if (emp.getGroup() != null) {
                emp.getGroup().remove(group);
            }
            employeeRepository.save(emp);
        }
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

    public void makeEmployeeAsAdmin (String id) {
        Employee emp = getEmployeeById(id);
        emp.setRole(Employee.Role.ADMIN);
        employeeRepository.save(emp);
    }

    public void revokeEmployeefromAdmin(String id, Employee role) {
        System.out.println(role);
        Employee emp = getEmployeeById(id);
        emp.setRole(role.getRole());
        employeeRepository.save(emp);
    }

    //Upload File
    public String storeImage(String imageString, String id) {
        // Path File
        String pathFile = "src\\main\\resources\\images\\";
        String outputFileLocation = null;

        // Decode image string int
        byte[] imageByteArray = Base64.getDecoder().decode(imageString);
        try {
            File dir = new File(pathFile);

            if (!dir.exists()) {
                System.out.println("Creating directory : " + dir.getName());
                boolean result = false;

                try {
                    dir.mkdir();
                    result = true;
                } catch (SecurityException se) {
                    se.printStackTrace();
                }

                if (result) {
                    System.out.println("Directory created");
                }
            }

            // Rename picture by id
            outputFileLocation = pathFile + id + ".jpg";

            new FileOutputStream(outputFileLocation).write(imageByteArray);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return id + ".jpg";
    }

    // Patch upload images
    public void picturePatch (String imageString, String id) {

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
        employeeRepository.save(employee);
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
            Set<Group> mySet = e.getGroup();
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

//        // print key-value pair in hashmap
//        for (String key : map.keySet()) {
//            System.out.println(key + " : " + map.get(key));
//        }
        return map;
//        Set<Group> mySet = employees.get(0).getGroup();
//        mySet.forEach(g -> {
//            System.out.println(g.getId());
//            String key = g.getId();
//            // check if contain the keys or not
//            if (!map.containsKey(key)) {
//                map.put(key, 1); // buat baru
//            } else {
//                map.put(key, map.get(key) + 1); // +1 jika ditemukan
//            }
//        });



//        Iterator itr = mySet.iterator();
//        while (itr.hasNext()) {
//            Group g = (Group) itr.next();
//            g.getId();
//            System.out.println(itr.next());
//        }

//        System.out.println(employees);
    }
}

