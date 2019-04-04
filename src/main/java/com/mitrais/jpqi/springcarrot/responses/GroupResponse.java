package com.mitrais.jpqi.springcarrot.responses;

import com.mitrais.jpqi.springcarrot.model.Group;

import java.util.List;

public class GroupResponse extends Response {
    Group group;
    List<Group> listGroup;

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public List<Group> getListGroup() {
        return listGroup;
    }

    public void setListGroup(List<Group> listGroup) {
        this.listGroup = listGroup;
    }
}
