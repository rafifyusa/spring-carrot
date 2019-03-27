package com.mitrais.jpqi.springcarrot.controller;

import com.mitrais.jpqi.springcarrot.model.*;
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

    @GetMapping("{id}")
    public Group getStaffGroups(@PathVariable String id) {
        return groupService.findGroupById(id);
    }

    @GetMapping("staff")
    public List<Group> getStaffGroups() {
        return groupService.findAllStaffGroup();
    }

    @PostMapping
    public void insertGroup(@RequestBody Group group){
        groupService.insertGroup(group);
    }

    @PutMapping("{id}")
    public void updateGroup(@PathVariable String id, @RequestBody Group group) {groupService.updateGroup(id,group);}

    @DeleteMapping("{id}")
    public void deleteGroup(@PathVariable String id) {groupService.deleteGroupById(id);}

    @PatchMapping("/add-achievement/{id}")
    public void insertAchievementToGroup(@PathVariable String id, @RequestBody List<Achievement> achievement){
        groupService.addAchievementToGroup(id, achievement);
    }
    @PatchMapping("/del-achievement/{id}")
    public void deleteAchievementFrom (@PathVariable String id, @RequestBody Achievement achievement) {
        groupService.deleteAchievementFromGroup( id, achievement);
    }

    @PatchMapping("/add-award/{id}")
    public void insertAwardsToGroup(@PathVariable String id, @RequestBody List<Award> awards){
        groupService.addAwardToGroup(id, awards);
    }
    @PatchMapping("/del-award/{id}")
    public void deleteAwardFromGroup (@PathVariable String id, @RequestBody Award award) {
        groupService.deleteAwardFromGroup( id, award);
    }

    @PatchMapping("/add-bazaar/{id}")
    public void insertBazaarsToGroup(@PathVariable String id, @RequestBody List<Bazaar> bazaars){
        groupService.addBazaarToGroup(id, bazaars);
    }
    @PatchMapping("/del-bazaar/{id}")
    public void deleteBazaarFromGroup (@PathVariable String id, @RequestBody Bazaar bazaar) {
        groupService.deleteBazaarFromGroup( id, bazaar);
    }
    @PatchMapping("/add-social/{id}")
    public void insertSocialFoundationsToGroup(@PathVariable String id, @RequestBody List<SocialFoundation> socialFoundations) {
        groupService.addSocialFoundationToGroup(id, socialFoundations);
    }
    @PatchMapping("/del-social/{id}")
    public void deleteSocialFoundationFromGroup(@PathVariable String id, @RequestBody SocialFoundation socialFoundation) {
        groupService.deleteSocialFoundationFromGroup(id, socialFoundation);
    }
}
