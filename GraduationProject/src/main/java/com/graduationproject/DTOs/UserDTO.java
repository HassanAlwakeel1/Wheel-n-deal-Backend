package com.graduationproject.DTOs;

import com.graduationproject.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class UserDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String phoneNumber;
    private String username;
    private Long amount;
    private Gender gender;
    private String profilePictureUrl;
    private String nationalId;
    private Integer totalDelivers;
    private Integer cancelDelivers;
}
