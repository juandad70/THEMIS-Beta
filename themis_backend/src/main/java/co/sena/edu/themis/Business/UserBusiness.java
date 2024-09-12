package co.sena.edu.themis.Business;

import co.sena.edu.themis.Dto.CoordinationDto;
import co.sena.edu.themis.Dto.RoleDto;
import co.sena.edu.themis.Dto.UserDto;
import co.sena.edu.themis.Entity.Coordination;
import co.sena.edu.themis.Entity.User;
import co.sena.edu.themis.Service.CoordinationService;
import co.sena.edu.themis.Service.UserService;
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
public class UserBusiness {
    @Autowired
    private UserService userService;

    private final ModelMapper modelMapper = new ModelMapper();
    private static final Logger logger = Logger.getLogger(UserBusiness.class);

    public Page<UserDto> findAll(int page, int size) {
        try {
            PageRequest pageRequest = PageRequest.of(page, size);
            Page<User> users = userService.findAll(pageRequest);

            if (users.isEmpty()) {
                logger.info("Users not found!");
            }

            Page<UserDto> userDtoPage = users.map(user -> {
                UserDto dto = modelMapper.map(user, UserDto.class);
                // Convertir Role a RoleDto antes de asignar
                if (user.getRoleList() != null) {
                    dto.setFk_id_role(modelMapper.map(user.getRoleList(), RoleDto.class));
                }
                return dto;
            });

            return userDtoPage;
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new CustomException("Error", "Error getting users", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    public List<UserDto> findById(Long id) {
        List<UserDto> userDtoList = new ArrayList<>();
        try {
            User user = userService.getById(id);
            logger.info("User: {}" + user);
            if (user != null) {
                userDtoList.add(modelMapper.map(user, UserDto.class));
                return userDtoList;
            } else {
                return new ArrayList<>();
            }
        } catch (EntityNotFoundException entNotFound) {
            logger.info(entNotFound.getMessage());
            throw new CustomException("Not found", "Not found user with that id", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new CustomException("Error", "Error getting user by id", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public boolean createUser(UserDto userDto) {
        try {
            User user = modelMapper.map(userDto, User.class);
            userService.save(user);
            logger.info("User created successfully");
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new CustomException("Error", "Error creating user", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public boolean updateUser(UserDto userDto) {
        try {
            if (userDto.getDocument() == null) {
                logger.info("Can't update user because the document is null!");
            }

            User existingUser = userService.getById(userDto.getDocument());
            logger.info("User: {}" + existingUser);

            User updatedUser = modelMapper.map(userDto, User.class);
            userService.save(updatedUser);
            return true;
        } catch (EntityNotFoundException entNotFound) {
            logger.info("The user you are tying to update is not registered");
            throw new CustomException("Not Found", "Can't update the user because it isn't registered", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new CustomException("Error", "Error update user", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public boolean deleteUserById(Long id) {
        try {
            if (id == null) {
                logger.info("Can't delete user because the id is null");
            }

            User deletingUser = userService.getById(id);
            logger.info("User: {}" + deletingUser);

            userService.deleteById(id);
            logger.info("User deleted successfully");
            return true;
        } catch (EntityNotFoundException entNotFound) {
            logger.info("The user you are trying to delete is not registered");
            throw new CustomException("Not found", "Can't delete the user because it isn't registered", HttpStatus.NOT_FOUND);
        } catch (Exception e){
            logger.error(e.getMessage());
            throw new CustomException("Error", "Error delete user", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
