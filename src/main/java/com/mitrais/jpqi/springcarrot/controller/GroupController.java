package com.mitrais.jpqi.springcarrot.controller;

import com.mitrais.jpqi.springcarrot.model.Achievement;
import com.mitrais.jpqi.springcarrot.model.Award;
import com.mitrais.jpqi.springcarrot.model.Bazaar;
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

    @GetMapping("{id}")
    public Group getStaffGroups(@PathVariable String id) {
        return groupService.findGroupById(id);
    }

    @GetMapping("staff")
    public List<Group> getStaffGroups() {
        return groupService.findAllStaffGroup();
    }

    @GetMapping("awards/{id}")
    public List<Award> getAllAwardsByGroup(@PathVariable String id) {return groupService.findAllAwardsByGroupId(id);}

    @GetMapping("achievements/{id}")
    public List<Achievement> getAllAchievementsByGroup (@PathVariable String id) {
        return groupService.findAllAchievementsByGroupId(id);
    }
    @GetMapping("bazaars/{id}")
    public List<Bazaar> getAllBazaarsByGroup (@PathVariable String id) {
        return groupService.findAllBazaarsByGroupId(id);
    }

    @PostMapping
    public void insertGroup(@RequestBody Group group){
        groupService.insertGroup(group);
    }

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


/*    @PatchMapping("{id}")
    public void insertMember(@RequestBody List<Employee> employee, @PathVariable int id){
        groupService.insertMemberToGroup(id, employee);
    }

    @PatchMapping("/delete/{id}")
    public void deleteMember(@RequestBody Employee employee, @PathVariable int id){
        groupService.deleteMemberFromGroup(id, employee);
    }*/
}
