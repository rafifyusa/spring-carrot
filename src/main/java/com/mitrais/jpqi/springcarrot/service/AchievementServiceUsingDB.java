package com.mitrais.jpqi.springcarrot.service;

import com.mitrais.jpqi.springcarrot.controller.EmailController;
import com.mitrais.jpqi.springcarrot.model.Achievement;
import com.mitrais.jpqi.springcarrot.model.Employee;
import com.mitrais.jpqi.springcarrot.repository.AchievementRepository;
import com.mitrais.jpqi.springcarrot.responses.AchievementResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AchievementServiceUsingDB implements AchievementService{
    @Autowired
    AchievementRepository achievementRepository;

    @Autowired
    @Lazy
    EmployeeServiceUsingDB employeeServiceUsingDB;

    @Autowired
    EmailController emailController;

    public AchievementServiceUsingDB(AchievementRepository achievementRepository){ this.achievementRepository = achievementRepository;}

    @Override
    public AchievementResponse getAllAchievement() {
        AchievementResponse res = new AchievementResponse();
        res.setStatus(true);
        res.setMessage("List Achievement");
        res.setListAchievement(achievementRepository.findAll());
        return res;
    }

    @Override
    public Optional<Achievement> findByAchievementId(String id) {
        return achievementRepository.findById(id);
    }

    @Override
    public List<Achievement> findByAchievementRole(String role) {
        return achievementRepository.findByRole(role);
    }

    @Override
    public List<Achievement> createAchievement(Achievement achievement) {
        String subject = ("Achievement " + achievement.getTitle() + " has been added");
        String emailBody = ("Achievement " + achievement.getTitle() + " has been added \r" +
                "Achievement Description: " + achievement.getDescription() +
                "\r Carrot Amount: " + achievement.getCarrot() +
                "\r Role: " + achievement.getRole()
        );
        List<Employee> employees = employeeServiceUsingDB.getStaffRole("MANAGER").getListEmployee();
        List<String> emailList = employees.stream().map(employee -> employee.getEmailAddress()).collect(Collectors.toList());
        emailController.sendMailContent(emailList, subject, emailBody);
        achievementRepository.save(achievement);
        return achievementRepository.findAll();
    }

    @Override
    public List<Achievement> updateAchievement(String id, Achievement achievement) {
        achievement.setId(id);
        Achievement temp = achievementRepository.findById(id).orElse(null);
        try {
            if(!temp.isStatus() && achievement.isStatus()){
                String subject = ("Achievement " + achievement.getTitle() + " has been unavailable");
                String emailBody = ("Achievement " + achievement.getTitle() + " has been unavailable" +
                        "Reason: " + achievement.getReasoning()
                        );
                List<Employee> employees = employeeServiceUsingDB.getStaffRole(String.valueOf(achievement.getRole())).getListEmployee();
                List<String> emailList = employees.stream().map(employee -> employee.getEmailAddress()).collect(Collectors.toList());
                emailController.sendMailContent(emailList, subject, emailBody);
            }
            }
        catch (NullPointerException e){}

        achievementRepository.save(achievement);
        return achievementRepository.findAll();
    }

    @Override
    public void deleteAchievement(String id) {
        achievementRepository.deleteById(id);
    }

    @Override
    public AchievementResponse partialUpdateAchievement(String id, Achievement achievement) {
        AchievementResponse res = new AchievementResponse();
        Achievement temp = achievementRepository.findById(id).orElse(null);

        if (temp != null){
            if (achievement.getId() != null){
                temp.setId(achievement.getId());
            }
            if (achievement.getTitle() != null){
                temp.setTitle(achievement.getTitle());
            }
            if (achievement.getDescription() != null){
                temp.setDescription(achievement.getDescription());
            }
            if (achievement.getCarrot() != 0){
                temp.setCarrot(achievement.getCarrot());
            }
            if (achievement.getRole() != null){
                temp.setRole(achievement.getRole());
            }
            if (achievement.isStatus() || !achievement.isStatus()){
                temp.setStatus(achievement.isStatus());
            }
            if (achievement.getReasoning() != null){
                temp.setReasoning(achievement.getReasoning());
            }
        }
        try {
            achievementRepository.save(temp);
            res.setStatus(true);
            res.setMessage("Achievement successfully updated");
        } catch (NullPointerException e) {
            res.setStatus(false);
            res.setMessage(e.getMessage());
        }
        return res;
    }
}
