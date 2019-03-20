package com.mitrais.jpqi.springcarrot.service;

import com.mitrais.jpqi.springcarrot.model.Achievement;
import com.mitrais.jpqi.springcarrot.model.Award;
import com.mitrais.jpqi.springcarrot.model.Group;
import com.mitrais.jpqi.springcarrot.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class GroupService {
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private MongoTemplate mongoTemplate;

    public void insertGroup(Group group) {
        groupRepository.save(group);
    }

    public void updateGroup(Group group) {
        groupRepository.save(group);
    }

    public List<Group> findAllGroup() { return groupRepository.findAll(); }

    public void deleteGroupById(String id) {groupRepository.deleteById(id);}

    public List<Group> findAllStaffGroup() {
        List<Group> groups = groupRepository.findAll();
        List<Group>result = groups.stream().filter(grp -> grp.getType() == Group.Type.STAFF)
                .collect((Collectors.toList()));
        return result;
    }

    public void addAchievementToGroup(String id, Set<Achievement> achievements) {
        Optional<Group> g = groupRepository.findById(id);
        Group group = g.get();

        if (group.getAchievements() == null) {
            group.setAchievements(new HashSet<>());
        }
        Set<Achievement> achievements_set = group.getAchievements();
        achievements.forEach( e -> achievements_set.add(e));

        group.setAchievements(achievements_set);
        groupRepository.save(group);
    }

    public void addAwardToGroup(String id, Set<Award> awards) {
        Optional<Group> g = groupRepository.findById(id);
        Group group = g.get();

        if (group.getAwards() == null) {
            group.setAwards(new HashSet<>());
        }
        Set<Award> awards_set = group.getAwards();
        awards.forEach( e -> awards_set.add(e));

        group.setAwards(awards_set);
        groupRepository.save(group);
    }

/*    public void insertMemberToGroup(int id, List<Employee> employee){
        Optional<Group> group = groupRepository.findById(id);

        if (group.isPresent()) {
            Group sg = group.get();
            if (sg.getMember() == null) {
                sg.setMember(new HashSet<>());
            }

            employee.forEach(e -> sg.getMember().add(e));
            groupRepository.save(sg);
        }
    }

    public void deleteMemberFromGroup(int id, Employee employee){
        Optional<Group> group = groupRepository.findById(id);
        if (group.isPresent()) {
           Group sg = group.get();
            if (sg.getMember() != null) {
                sg.getMember().remove(employee);
            }
            groupRepository.save(sg);
        }
    }*/

}
