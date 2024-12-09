package com.graduationproject.services;

import org.springframework.http.ResponseEntity;

public interface CommuterProfileService {
    ResponseEntity<Object> getFullCommuterProfile(Integer commuterId);

}
