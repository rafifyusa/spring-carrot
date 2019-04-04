package com.mitrais.jpqi.springcarrot.service;

import com.mitrais.jpqi.springcarrot.model.SocialFoundation;
import com.mitrais.jpqi.springcarrot.responses.SocialFoundationResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SocialFoundationService {
    SocialFoundationResponse createSocialFoundation(SocialFoundation socialFoundation);
    SocialFoundationResponse deleteSocialFoundation(String id );
    SocialFoundationResponse updateSocialFoundation(String id, SocialFoundation socialFoundation);
    SocialFoundationResponse getAllSocialFoundation();
    SocialFoundationResponse getSocialFoundationById(String id);

    SocialFoundationResponse partialUpdate(String id, SocialFoundation socialFoundation);
}
