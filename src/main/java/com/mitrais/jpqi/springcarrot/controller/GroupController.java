package com.mitrais.jpqi.springcarrot.controller;

import com.mitrais.jpqi.springcarrot.model.Employee;
import com.mitrais.jpqi.springcarrot.model.StaffGroup;
import com.mitrais.jpqi.springcarrot.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/groups")
public class GroupController {
    @Autowired
    private GroupService groupService;

    @GetMapping
    public List<StaffGroup> getGroups(){
        return groupService.findAllGroup();
    }

    @PostMapping
    public void insertGroup(@RequestBody StaffGroup group){
        groupService.insertGroup(group);
    }

    @PatchMapping("{id}")
    public void insertMember(@RequestBody Employee employee, @PathVariable int id){
        groupService.insertMemberToGroup(id, employee);
    }
}
