package com.bikkadit.electronicstore.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {

    private String userId;
    @Size(min = 3, max = 30, message = "Invalid User Name")
    private String name;

    // @Email(message = "Invalid UserEmail...........")
    @Pattern(regexp = "^[a-z0-9][-a-z0-9]+@([-a-z0-9]+\\.)+[a-z]{2,5}$", message = "Invalid UserEmail.")
    @NotBlank(message = "emailId must be required.........")
    private String email;

    @Size(min = 4, max = 30, message = "password must be min 4 and max 30 char")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&*+-=]).*$",message = "Password must be one lowercase letter," +
            "one uppercase letter,one digit and one special character ")
    @NotBlank(message = "password must be required")
    private String password;

    @Size(min = 2, max = 6, message = "Invalid  gender")
    private String gender;

    @NotBlank(message = "Write something about yourself.")
    private String about;

    private String imageName;


}
