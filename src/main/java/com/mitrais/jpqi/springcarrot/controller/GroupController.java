package com.mitrais.jpqi.springcarrot.controller;

import com.mitrais.jpqi.springcarrot.model.Group;
import com.mitrais.jpqi.springcarrot.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("api/groups")
public class GroupController {
    @Autowired
    private GroupService groupService;

    @GetMapping
    public List<Group> getGroups(){
        return groupService.findAllGroup();
    }

    @GetMapping ("staff")
    public List<Group> getStaffGroups() {
        return groupService.findAllStaffGroup();
    }

    @PostMapping
    public void insertGroup(@RequestBody Group group){
        groupService.insertGroup(group);
    }

/*    @PatchMapping("{id}")
    public void insertMember(@RequestBody List<Employee> employee, @PathVariable int id){
        groupService.insertMemberToGroup(id, employee);
    }

    @PatchMapping("/delete/{id}")
    public void deleteMember(@RequestBody Employee employee, @PathVariable int id){
        groupService.deleteMemberFromGroup(id, employee);
    }*/
}
