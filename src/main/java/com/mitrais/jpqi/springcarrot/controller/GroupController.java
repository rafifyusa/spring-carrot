package com.mitrais.jpqi.springcarrot.controller;

import com.mitrais.jpqi.springcarrot.model.*;
import com.mitrais.jpqi.springcarrot.responses.GroupResponse;
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
    public GroupResponse getGroups(){
        return groupService.findAllGroup();
    }

    @GetMapping("{id}")
    public GroupResponse getStaffGroups(@PathVariable String id) {
        return groupService.findGroupById(id);
    }

    @GetMapping("staff")
    public GroupResponse getStaffGroups() {
        return groupService.findAllStaffGroup();
    }

    @PostMapping
    public GroupResponse insertGroup(@RequestBody Group group){
        return groupService.insertGroup(group);
    }

    @PutMapping("{id}")
    public GroupResponse updateGroup(@PathVariable String id, @RequestBody Group group) {return groupService.updateGroup(id,group);}

    @DeleteMapping("{id}")
    public GroupResponse deleteGroup(@PathVariable String id) {return groupService.deleteGroupById(id);}

    @PatchMapping("/add-achievement/{id}")
    public GroupResponse insertAchievementToGroup(@PathVariable String id, @RequestBody List<Achievement> achievement){
        return groupService.addAchievementToGroup(id, achievement);
    }
    @PatchMapping("/del-achievement/{id}")
    public GroupResponse deleteAchievementFrom (@PathVariable String id, @RequestBody Achievement achievement) {
        return groupService.deleteAchievementFromGroup( id, achievement);
    }
    @PatchMapping("/add-award/{id}")
    public GroupResponse insertAwardsToGroup(@PathVariable String id, @RequestBody List<Award> awards){
        return groupService.addAwardToGroup(id, awards);
    }
    @PatchMapping("/del-award/{id}")
    public GroupResponse deleteAwardFromGroup (@PathVariable String id, @RequestBody Award award) {
        return groupService.deleteAwardFromGroup( id, award);
    }

    @PatchMapping("/add-bazaar/{id}")
    public GroupResponse insertBazaarsToGroup(@PathVariable String id, @RequestBody List<Bazaar> bazaars){
        return groupService.addBazaarToGroup(id, bazaars);
    }
    @PatchMapping("/del-bazaar/{id}")
    public GroupResponse deleteBazaarFromGroup (@PathVariable String id, @RequestBody Bazaar bazaar) {
        return groupService.deleteBazaarFromGroup( id, bazaar);
    }
    @PatchMapping("/add-social/{id}")
    public GroupResponse insertSocialFoundationsToGroup(@PathVariable String id, @RequestBody List<SocialFoundation> socialFoundations) {
        return groupService.addSocialFoundationToGroup(id, socialFoundations);
    }
    @PatchMapping("/del-social/{id}")
    public GroupResponse deleteSocialFoundationFromGroup(@PathVariable String id, @RequestBody SocialFoundation socialFoundation) {
        return groupService.deleteSocialFoundationFromGroup(id, socialFoundation);
    }
}
