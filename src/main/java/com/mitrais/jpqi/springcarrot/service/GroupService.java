package com.mitrais.jpqi.springcarrot.service;

import com.mitrais.jpqi.springcarrot.model.Group;
import com.mitrais.jpqi.springcarrot.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public void deleteGroupById(int id) {groupRepository.deleteById(id);}

    public List<Group> findAllStaffGroup() {
        List<Group> groups = groupRepository.findAll();
        List<Group>result = groups.stream().filter(grp -> grp.getType() == Group.Type.STAFF)
                .collect((Collectors.toList()));
        return result;
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
