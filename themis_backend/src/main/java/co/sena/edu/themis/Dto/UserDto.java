package co.sena.edu.themis.Dto;

import co.sena.edu.themis.Entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDto {
    private Long document;
    private String password;
    private String type_document;
    private Role fk_id_role;
}
