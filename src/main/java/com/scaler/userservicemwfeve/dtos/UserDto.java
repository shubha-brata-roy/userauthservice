package com.scaler.userservicemwfeve.dtos;

import com.scaler.userservicemwfeve.models.Role;
import com.scaler.userservicemwfeve.models.User;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserDto {
    private String name;
    private String email;
    private List<Role> roles;
    private boolean emailVerified;

    public static UserDto from(User user) {

        if(user == null) {
            return null;
        }

        UserDto userDto = new UserDto();
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        userDto.setRoles(user.getRoles());
        userDto.setEmailVerified(user.isEmailVerified());

        return userDto;
    }
}
