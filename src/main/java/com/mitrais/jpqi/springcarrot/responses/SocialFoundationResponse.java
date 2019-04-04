package com.mitrais.jpqi.springcarrot.responses;

import com.mitrais.jpqi.springcarrot.model.SocialFoundation;

import java.util.List;

public class SocialFoundationResponse extends Response {
    SocialFoundation socialFoundation;
    List<SocialFoundation> listSocialFoundation;

    public SocialFoundation getSocialFoundation() {
        return socialFoundation;
    }

    public void setSocialFoundation(SocialFoundation socialFoundation) {
        this.socialFoundation = socialFoundation;
    }

    public List<SocialFoundation> getListSocialFoundation() {
        return listSocialFoundation;
    }

    public void setListSocialFoundation(List<SocialFoundation> listSocialFoundation) {
        this.listSocialFoundation = listSocialFoundation;
    }
}
