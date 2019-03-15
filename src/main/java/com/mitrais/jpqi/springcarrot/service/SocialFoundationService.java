package com.mitrais.jpqi.springcarrot.service;

import com.mitrais.jpqi.springcarrot.model.SocialFoundation;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SocialFoundationService {
    void createSocialFoundation(SocialFoundation socialFoundation);
    void deleteSocialFoundation(String id );
    void updateSocialFoundation(String id, SocialFoundation socialFoundation);
    List<SocialFoundation> getAllSocialFoundation();
    List<SocialFoundation> getSocialFoundationById(String id);

    void partialUpdate(String id, SocialFoundation socialFoundation);
}
