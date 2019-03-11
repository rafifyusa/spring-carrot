package com.mitrais.jpqi.springcarrot.service;

import com.mitrais.jpqi.springcarrot.model.Employee;
import com.mitrais.jpqi.springcarrot.model.StaffGroup;
import com.mitrais.jpqi.springcarrot.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import javax.persistence.Query;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class GroupService {
    @Autowired
    private GroupRepository groupRepository;

    public void insertGroup(StaffGroup group) {
        groupRepository.save(group);
    }

    public void updateGroup(StaffGroup group) {
        groupRepository.save(group);
    }

    public List<StaffGroup> findAllGroup() { return groupRepository.findAll(); }

    public void deleteGroupById(int id) {groupRepository.deleteById(id);}

    public void insertMemberToGroup(int id, Employee employee){
        Optional<StaffGroup> group = groupRepository.findById(id);

        if (group.isPresent()) {

            Employee e = new Employee();
            e.setId(5);
            e.setName("rrrr");

            StaffGroup sg = group.get();
            sg.getMember().add(e);
//            Set<Employee> members = sg.getMember();
//            System.out.println(employee.getName());
//            members.add(employee);
//            sg.setMember(members);
            groupRepository.save(sg);
        }
    }

}
