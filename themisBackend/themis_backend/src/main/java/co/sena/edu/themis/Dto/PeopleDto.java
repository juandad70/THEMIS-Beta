package co.sena.edu.themis.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class PeopleDto {
    private  int id;
    private  String name;
    private String lastName;
    private  String email;
    private  Long phone;

}
