package com.mitrais.jpqi.springcarrot.service;

import com.google.gson.Gson;
import com.mitrais.jpqi.springcarrot.controller.AchievementController;
import com.mitrais.jpqi.springcarrot.controller.EmailController;
import com.mitrais.jpqi.springcarrot.model.*;
import com.mitrais.jpqi.springcarrot.repository.AchievementRepository;
import com.mitrais.jpqi.springcarrot.repository.EmployeeRepository;
import com.mitrais.jpqi.springcarrot.repository.GroupRepository;
import com.mitrais.jpqi.springcarrot.responses.GroupResponse;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class GroupService {
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private EmployeeServiceUsingDB employeeServiceUsingDB;
    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    AchievementController achievementController;

    @Autowired
    EmailController emailController;

    public GroupResponse insertGroup(Group group) {
        GroupResponse res = new GroupResponse();
        try {
            groupRepository.save(group);
            res.setStatus(true);
            res.setMessage("Group successfully inserted");
        } catch (NullPointerException e) {
            res.setStatus(false);
            res.setMessage(e.getMessage());
        }
        return res;
    }

    public GroupResponse updateGroup(String id, Group group) {
        GroupResponse res = new GroupResponse();
        try {
            group.setId(id);
            groupRepository.save(group);
            res.setStatus(true);
            res.setMessage("Group successfully updated");
        } catch (NullPointerException e) {
            res.setStatus(false);
            res.setMessage(e.getMessage());
        }
        return res;
    }

    public GroupResponse deleteGroupById(String id) {
        GroupResponse res = new GroupResponse();
        try {
            groupRepository.deleteById(id);
            res.setStatus(true);
            res.setMessage("Group successfully deleted");
        } catch (NullPointerException e) {
            res.setStatus(false);
            res.setMessage(e.getMessage());
        }
        return res;
    }

    public GroupResponse findAllGroup() {
        GroupResponse res = new GroupResponse();
        List<Group> listGroup= groupRepository.findAll();
        res.setStatus(true);
        res.setListGroup(listGroup);
        if (listGroup.size() > 0) {
            res.setMessage("List Group");
        } else {
            res.setMessage("Group not found");
        }
        return res;
    }


    public GroupResponse findGroupById (String id) {
        GroupResponse res = new GroupResponse();
        if (groupRepository.findById(id).isPresent()) {
            res.setStatus(true);
            res.setMessage("Group has Found");
            res.setGroup(groupRepository.findById(id).get());
        }
        else {
            res.setStatus(false);
            res.setMessage("Group Not Found");
        }
        return res;
    }

    public GroupResponse findAllStaffGroup() {
        GroupResponse res = new GroupResponse();
        List<Group> groups = groupRepository.findAll();
        List<Group>result = groups.stream().filter(grp -> grp.getType() == Group.Type.STAFF)
                .collect((Collectors.toList()));
        res.setStatus(true);
        res.setListGroup(result);
        if (result.size() > 0) {
            res.setMessage("Group Found");
        } else {
            res.setMessage("Group Not Found");
        }
        return res;
    }

    public GroupResponse addAchievementToGroup(String id, List<Achievement> achievements) {
        GroupResponse res = new GroupResponse();
        Optional<Group> g = groupRepository.findById(id);
        Group group = g.get();

        if (group.getAchievements() == null) {
            group.setAchievements(new ArrayList<>());
        }
        List<Achievement> achievements_set = group.getAchievements();
        achievements.forEach( e -> achievements_set.add(e));

        group.setAchievements(achievements_set);
        try {
            groupRepository.save(group);
            res.setStatus(true);
            res.setMessage("Achievement successfully added");
            Optional<Achievement> temp = achievementController.findByAchievementId(achievements.get(0).getId());
            String subject = ("New Achievement on Carrot to group" + group.getName());
            String emailBody = ("New Achievement has been added to group" + group.getName() + "\r" +
                "Achievement Title: " + temp.get().getTitle() + "\r" +
                "Achievement Description: " + temp.get().getDescription() + "\r" +
                "Carrot that can be achieved: " + temp.get().getCarrot()
                );
            List<Employee> employees = employeeServiceUsingDB.getGroupMember(group.getId()).getListEmployee();
            List<String> emailList = employees.stream().map(employee -> employee.getEmailAddress()).collect(Collectors.toList());
            emailController.sendMailContent(emailList, subject, emailBody);
        } catch (NullPointerException e) {
            res.setStatus(false);
            res.setMessage(e.getMessage());
        }
        return res;
    }

    public GroupResponse deleteAchievementFromGroup(String id, Achievement achievement){
        GroupResponse res = new GroupResponse();
        Optional<Group> group = groupRepository.findById(id);
        if (group.isPresent()) {
            Group g = group.get();
            if (g.getAchievements() != null) {
                g.getAchievements().remove(achievement);
            }

            try {
                groupRepository.save(g);
                res.setStatus(true);
                res.setMessage("Achievement successfully removed");
            } catch (NullPointerException e) {
                res.setStatus(false);
                res.setMessage(e.getMessage());
            }
        } else {
            res.setStatus(false);
            res.setMessage("Group not found");
        }
        return res;
    }

    public GroupResponse addAwardToGroup(String id, List<Award> awards) {
        GroupResponse res = new GroupResponse();
        Optional<Group> g = groupRepository.findById(id);
        Group group = g.get();
        System.out.println(group.getName());

        if (group.getAwards() == null) {
            group.setAwards(new ArrayList<>());
        }

        List<Award> awards_set = group.getAwards();
        System.out.println(awards_set.size());
        awards_set.addAll(awards);
        System.out.println("added awards");
        group.setAwards(awards_set);
        try {
            groupRepository.save(group);
            res.setStatus(true);
            res.setMessage("Award successfully inserted");
        } catch (NullPointerException e) {
            res.setStatus(false);
            res.setMessage(e.getMessage());
        }
        return res;
    }

    public GroupResponse deleteAwardFromGroup(String id, Award award){
        GroupResponse res = new GroupResponse();
        Optional<Group> group = groupRepository.findById(id);
        if (group.isPresent()) {
            Group g = group.get();
            if (g.getAwards() != null) {
                g.getAwards().remove(award);
            }
            try {
                groupRepository.save(g);
                res.setStatus(true);
                res.setMessage("Award successfully removed");
            } catch (NullPointerException e) {
                res.setStatus(false);
                res.setMessage(e.getMessage());
            }
        } else {
            res.setStatus(false);
            res.setMessage("Group not found");
        }
        return res;
    }

    public GroupResponse addBazaarToGroup (String id, List<Bazaar> bazaars) {
        GroupResponse res = new GroupResponse();
        Optional<Group> g = groupRepository.findById(id);
        if(g.isPresent()) {
            Group group = g.get();

            if (group.getBazaars() == null) {
                group.setBazaars(new ArrayList<>());
            }
            List<Bazaar> bazaarList = group.getBazaars();
            bazaarList.addAll(bazaars);
            group.setBazaars(bazaarList);
            try {
                groupRepository.save(group);
                res.setStatus(true);
                res.setMessage("Bazaar successfully inserted");
            } catch (NullPointerException e) {
                res.setStatus(false);
                res.setMessage(e.getMessage());
            }
        }else {
            res.setStatus(false);
            res.setMessage("Group not found");
        }
        return res;
    }
    public GroupResponse deleteBazaarFromGroup(String id, Bazaar bazaar){
        GroupResponse res = new GroupResponse();
        Optional<Group> group = groupRepository.findById(id);
        if (group.isPresent()) {
            Group g = group.get();
            if (g.getBazaars() != null) {
                g.getBazaars().remove(bazaar);
            }
            try {
                groupRepository.save(g);
                res.setStatus(true);
                res.setMessage("Achievement successfully removed");
            } catch (NullPointerException e) {
                res.setStatus(false);
                res.setMessage(e.getMessage());
            }
        } else {
            res.setStatus(false);
            res.setMessage("Group not found");
        }
        return res;
    }
    public GroupResponse addSocialFoundationToGroup (String id, List<SocialFoundation> socialFoundations) {
        GroupResponse res = new GroupResponse();
        Group group = findGroupById(id).getGroup();
        if(group.getSocialFoundations() == null) {
            group.setSocialFoundations(new ArrayList<>());
        }

        List<SocialFoundation> socialFoundationList = group.getSocialFoundations();
        socialFoundationList.addAll(socialFoundations);

        group.setSocialFoundations(socialFoundationList);
        try {
            groupRepository.save(group);
            res.setStatus(true);
            res.setMessage("Social Foundation successfully added");
        } catch (NullPointerException e) {
            res.setStatus(false);
            res.setMessage(e.getMessage());
        }
        return res;
    }

    public GroupResponse deleteSocialFoundationFromGroup(String id, SocialFoundation socialFoundation) {
        GroupResponse res = new GroupResponse();
        Group group = findGroupById(id).getGroup();
        group.getSocialFoundations().remove(socialFoundation);
        try {
            groupRepository.save(group);
            res.setStatus(true);
            res.setMessage("Social foundation successfully removed");
        } catch (NullPointerException e) {
            res.setStatus(false);
            res.setMessage(e.getMessage());
        }
        return res;
    }

    public List<Group> findGroupId(String ownerId) {
        List<Group> myGroup = groupRepository.findGroupIdByOwner(new ObjectId(ownerId));
        return myGroup;
    }

    public long sumOfStaff(String groupId) {
        long sum = 0;
        Group group = findGroupById(groupId).getGroup();

        if (group.getType() == Group.Type.MANAGEMENT) {
            List<Employee> memberOfSMGroup = employeeServiceUsingDB.getGroupMember(group.getId()).getListEmployee();
            for (int j = 0; j<memberOfSMGroup.size(); j++){
                List<Group> mGroup = findGroupId(memberOfSMGroup.get(j).getId());
                //mGroup.forEach(gg -> System.out.println(gg.getId()));
                for (int i = 0; i < mGroup.size(); i++) {
                    List<Employee> memberOfMGroup = employeeServiceUsingDB.getGroupMember(mGroup.get(i).getId()).getListEmployee();
                    //System.out.println(memberOfMGroup.size());
                    sum += memberOfMGroup.size();
                    //System.out.println("Sum:" + sum);
                }
            }
        }
        else {
            List<Employee> memberOfMGroup = employeeServiceUsingDB.getGroupMember(group.getId()).getListEmployee();
            sum += memberOfMGroup.size();
        }
        return sum;
    }

    // Get Group Id (Only Management Group)
    public List<Group> findManagementGroupIdByOwner(String ownerId) {
        List<Group> myGroup = groupRepository.findGroupIdByOwner(new ObjectId(ownerId)).stream()
                .filter(g -> g.getType().equals(Group.Type.MANAGEMENT))
                .collect(Collectors.toList());
        System.out.println(myGroup.size());
        return myGroup;
    }

}
