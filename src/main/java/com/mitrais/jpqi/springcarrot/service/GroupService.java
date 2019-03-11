package com.mitrais.jpqi.springcarrot.service;

import com.mitrais.jpqi.springcarrot.model.Employee;
import com.mitrais.jpqi.springcarrot.model.StaffGroup;
import com.mitrais.jpqi.springcarrot.repository.GroupRepository;
import com.mongodb.BasicDBObject;
import com.mongodb.Mongo;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class GroupService {
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private MongoTemplate mongoTemplate;

    public void insertGroup(StaffGroup group) {
        groupRepository.save(group);
    }

    public void updateGroup(StaffGroup group) {
        groupRepository.save(group);
    }

    public List<StaffGroup> findAllGroup() { return groupRepository.findAll(); }

    public void deleteGroupById(int id) {groupRepository.deleteById(id);}

    public void insertMemberToGroup(int id, List<Employee> employee){
        Optional<StaffGroup> group = groupRepository.findById(id);

        if (group.isPresent()) {
            StaffGroup sg = group.get();
            if (sg.getMember() == null) {
                sg.setMember(new HashSet<>());
            }

            employee.forEach(e -> sg.getMember().add(e));
            groupRepository.save(sg);
        }
    }

    public void deleteMemberFromGroup(int id, Employee employee){
        Optional<StaffGroup> group = groupRepository.findById(id);
        if (group.isPresent()) {
           StaffGroup sg = group.get();
            if (sg.getMember() != null) {
                sg.getMember().remove(employee);
            }
            groupRepository.save(sg);
        }

    }


}
