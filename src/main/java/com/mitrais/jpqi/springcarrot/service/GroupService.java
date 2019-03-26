package com.mitrais.jpqi.springcarrot.service;

import com.google.gson.Gson;
import com.mitrais.jpqi.springcarrot.model.*;
import com.mitrais.jpqi.springcarrot.repository.GroupRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
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

    public Group findGroupById (String id) {
        if (groupRepository.findById(id).isPresent()) {
            return groupRepository.findById(id).get();
        }
        else {
            return null;
        }
    }

    public List<Group> findAllStaffGroup() {
        List<Group> groups = groupRepository.findAll();
        List<Group>result = groups.stream().filter(grp -> grp.getType() == Group.Type.STAFF)
                .collect((Collectors.toList()));
        return result;
    }

    public void addAchievementToGroup(String id, List<Achievement> achievements) {
        Optional<Group> g = groupRepository.findById(id);
        Group group = g.get();

        if (group.getAchievements() == null) {
            group.setAchievements(new ArrayList<>());
        }
        List<Achievement> achievements_set = group.getAchievements();
        achievements.forEach( e -> achievements_set.add(e));

        group.setAchievements(achievements_set);
        groupRepository.save(group);
    }

    public void addAwardToGroup(String id, List<Award> awards) {
        Optional<Group> g = groupRepository.findById(id);
        Group group = g.get();

        if (group.getAwards() == null) {
            group.setAwards(new ArrayList<>());
        }
        List<Award> awards_set = group.getAwards();
        awards.forEach( e -> awards_set.add(e));

        group.setAwards(awards_set);
        groupRepository.save(group);
    }

    public void addBazaarToGroup (String id, List<Bazaar> bazaars) {
        Optional<Group> g = groupRepository.findById(id);
        if(g.isPresent()) {
            Group group = g.get();

            if (group.getBazaars() == null) {
                group.setBazaars(new ArrayList<>());
            }

            //System.out.println(new Gson().toJson(group));
            List<Bazaar> bazaarList = group.getBazaars();
            bazaarList.addAll(bazaars);

            group.setBazaars(bazaarList);
            groupRepository.save(group);
        }
    }

    public void addSocialFoundationToGroup (String id, List<SocialFoundation> socialFoundations) {
        Group group = findGroupById(id);

        if(group.getSocialFoundations() == null) {
            group.setSocialFoundations(new ArrayList<>());
        }

        List<SocialFoundation> socialFoundationList = group.getSocialFoundations();
        socialFoundationList.addAll(socialFoundations);

        group.setSocialFoundations(socialFoundationList);
        groupRepository.save(group);
    }

    public void deleteAchievementFromGroup(String id, Achievement achievement){
        Optional<Group> group = groupRepository.findById(id);
        if (group.isPresent()) {
            Group g = group.get();
            if (g.getAchievements() != null) {
                g.getAchievements().remove(achievement);
            }
            groupRepository.save(g);
        }
    }

    public void deleteAwardFromGroup(String id, Award award){
        Optional<Group> group = groupRepository.findById(id);
        if (group.isPresent()) {
            Group g = group.get();
            if (g.getAwards() != null) {
                g.getAwards().remove(award);
            }
            groupRepository.save(g);
        }
    }

    public void deleteBazaarFromGroup(String id, Bazaar bazaar){
        Optional<Group> group = groupRepository.findById(id);
        if (group.isPresent()) {
            Group g = group.get();
            if (g.getBazaars() != null) {
                g.getBazaars().remove(bazaar);
            }
            groupRepository.save(g);
        }
    }

    public void deleteSocialFoundationFromGroup(String id, SocialFoundation socialFoundation) {
        Group group = findGroupById(id);
        group.getSocialFoundations().remove(socialFoundation);
        groupRepository.save(group);
    }

}
