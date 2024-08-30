package co.sena.edu.themis.Business;

import co.sena.edu.themis.Dto.CoordinationDto;
import co.sena.edu.themis.Dto.RoleDto;
import co.sena.edu.themis.Entity.Coordination;
import co.sena.edu.themis.Entity.Role;
import co.sena.edu.themis.Service.RoleService;
import co.sena.edu.themis.Util.Exception.CustomException;
import jakarta.persistence.EntityNotFoundException;
import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RoleBusiness {

    @Autowired
    private RoleService roleService;

    private final ModelMapper modelMapper = new ModelMapper();
    private static final Logger logger = Logger.getLogger(RoleBusiness.class);

    public Page<RoleDto> findAll(int page, int size) {
        try {
            PageRequest pageRequest = PageRequest.of(page, size);
            Page<Role> roles = roleService.findAll(pageRequest);

            if (roles.isEmpty()) {
                logger.info("Roles not found!");
            }
            Page<RoleDto> roleDtoPage = roles.map(Role-> modelMapper.map(Role, RoleDto.class));
            return roleDtoPage;
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new CustomException("Error", "Error getting roles", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public List<RoleDto> findById(Long id) {
        List<RoleDto> roleDtoList = new ArrayList<>();
        try {
            Role role = roleService.getById(id);
            logger.info("Role: {}" + role);
            if (role != null) {
                roleDtoList.add(modelMapper.map(role, RoleDto.class));
                return roleDtoList;
            } else {
                return new ArrayList<>();
            }
        } catch (EntityNotFoundException entNotFound) {
            logger.info(entNotFound.getMessage());
            throw new CustomException("Not found", "Not found rol with that id", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new CustomException("Error", "Error getting rol by id", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public boolean createRole(RoleDto roleDto) {
        try {
            Role role = modelMapper.map(roleDto, Role.class);
            roleService.save(role);
            logger.info("Role created successfully");
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new CustomException("Error", "Error creating role", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public boolean updateRole(RoleDto roleDto) {
        try {
            if (roleDto.getId() == null) {
                logger.info("Can't update role because the id is null!");
            }

            Role existingRole = roleService.getById(roleDto.getId());
            logger.info("Role: {}" + existingRole);

            Role updatedRole = modelMapper.map(roleDto, Role.class);
            roleService.save(updatedRole);
            return true;
        } catch (EntityNotFoundException entNotFound) {
            logger.info("The role you are tying to update is not registered");
            throw new CustomException("Not Found", "Can't update the role because it isn't registered", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new CustomException("Error", "Error update role", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public boolean deleteRoleById(Long id) {
        try {
            if (id == null) {
                logger.info("Can't delete role because the id is null");
            }

            Role deletingRole = roleService.getById(id);
            logger.info("Role: {}" + deletingRole);

            roleService.deleteById(id);
            logger.info("Role deleted successfully");
            return true;
        } catch (EntityNotFoundException entNotFound) {
            logger.info("The role you are trying to delete is not registered");
            throw new CustomException("Not found", "Can't delete the role because it isn't registered", HttpStatus.NOT_FOUND);
        } catch (Exception e){
            logger.error(e.getMessage());
            throw new CustomException("Error", "Error delete role", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
