package uz.hp.hrapp.payload;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
public class UserDto {

    @NotNull
    private String username; //official

    @Email
    private String email;

    @NotNull
    private String position;//katta boshliq )))))//ROLE_MANAGER

    @NotNull
    private Integer roleId; //3 xodim 2 manager
}
