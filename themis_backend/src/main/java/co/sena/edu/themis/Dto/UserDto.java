package co.sena.edu.themis.Dto;

import co.sena.edu.themis.Entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDto {
    private Long id;
    private Long document;
    private String password;
    private String type_document;
    private List<RoleDto> fk_id_role;
}
