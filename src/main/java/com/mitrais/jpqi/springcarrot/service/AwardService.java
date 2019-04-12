package com.mitrais.jpqi.springcarrot.service;

import com.mitrais.jpqi.springcarrot.controller.EmailController;
import com.mitrais.jpqi.springcarrot.model.Award;
import com.mitrais.jpqi.springcarrot.model.Employee;
import com.mitrais.jpqi.springcarrot.repository.AwardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AwardService {
    @Autowired
    private AwardRepository awardRepository;

    @Autowired
    @Lazy
    private EmployeeServiceUsingDB employeeServiceUsingDB;

    @Autowired
    EmailController emailController;

    public List<Award> findAllAwards() {return awardRepository.findAll();}

    public void createAward (Award award) {awardRepository.save(award);}

    public void updateAward (String id, Award award) {
        award.setId(id);
        awardRepository.save(award);
        String status = null;
        if(award.isActive()) {
            status = "available";
        } else {
            status = "now not available";
        }
        String subject = ("Award " + award.getType_name() + " being " + status);
        String emailBody = ("Award " + award.getType_name() + " being " + status +
                "\r Award Description: " + award.getType() +
                "\r Carrot Given: " + award.getCarrot_amt());
        List<Employee> employees = employeeServiceUsingDB.getStaffRole("STAFF").getListEmployee();
        List<String> emailList = employees.stream().map(employee -> employee.getEmailAddress()).collect(Collectors.toList());
        emailController.sendMailContent(emailList, subject, emailBody);
    }

    public void deleteAward (String id) { awardRepository.deleteById(id);}
}
