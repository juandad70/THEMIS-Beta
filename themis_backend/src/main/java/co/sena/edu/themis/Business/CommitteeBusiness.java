package co.sena.edu.themis.Business;

import co.sena.edu.themis.Dto.CommitteeDto;
import co.sena.edu.themis.Entity.Committee;
import co.sena.edu.themis.Service.CommitteeService;
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
public class CommitteeBusiness {

    @Autowired
    private CommitteeService committeeService;

    private final ModelMapper modelMapper = new ModelMapper();
    private static final Logger logger = Logger.getLogger(CommitteeBusiness.class);

    public Page<CommitteeDto> findAll(int page, int size) {
        try {
            PageRequest pageRequest = PageRequest.of(page, size);
            Page<Committee> committees = committeeService.findAll(pageRequest);

            if (committees.isEmpty()) {
                logger.info("Committees not found!");
                return Page.empty();
            }
            Page<CommitteeDto> committeeDtoPage = committees.map(Committee -> modelMapper.map(Committee, CommitteeDto.class));
            return committeeDtoPage;
        } catch (Exception e) {
            logger.info(e.getMessage());
            throw new CustomException("Error", "Error getting committees", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public List<CommitteeDto> findById(Long id){
        List<CommitteeDto> committeeDtoList = new ArrayList<>();
        try {
            Committee committee = committeeService.getById(id);
            logger.info("Committee: {}" + committee);
            if (committee != null) {
                committeeDtoList.add(modelMapper.map(committee, CommitteeDto.class));
                return committeeDtoList;
            } else {
                return new ArrayList<>();
            }
        } catch (EntityNotFoundException entNotFound) {
            logger.info(entNotFound.getMessage());
            throw new CustomException("Not found", "Not found committe with that id", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new CustomException("Error", "Error getting by id committee", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public boolean createCommittee(CommitteeDto committeeDto){
        try {
            Committee committee = modelMapper.map(committeeDto, Committee.class);
            committeeService.save(committee);
            logger.info("Committee created successfully");
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new CustomException("Error", "Error creating committee", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public boolean updateCommittee(CommitteeDto committeeDto) {
        try {
            if (committeeDto.getId() == null) {
                logger.info("Can't uodate committee because the id is null!");
            }

            Committee existingCommittee = committeeService.getById(committeeDto.getId());
            logger.info("Committee: {}" + existingCommittee);

            Committee updatedCommittee = modelMapper.map(committeeDto, Committee.class);
            committeeService.save(updatedCommittee);
            return true;
        } catch (EntityNotFoundException entNotFound) {
            logger.info("The committee you are trying to update is not registered");
            throw new CustomException("Not found", "Can't update the committee because it isn't registered!", HttpStatus.NOT_FOUND);
        } catch (Exception e){
            logger.error(e.getMessage());
            throw new CustomException("Error", "Error update committee", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public boolean deleteCommitteeById(Long id) {
        try {
            if (id == null) {
                logger.info("Can't delete committee because the id is null");
            }

            Committee deletingCommittee = committeeService.getById(id);
            logger.info("Committee: {}" + deletingCommittee);

            committeeService.deleteById(id);
            logger.info("Committee deleted successfully");
            return true;
        } catch (EntityNotFoundException entNotFound) {
            logger.info("The committee you are trying to delete is not registered");
            throw new CustomException("Not Found", "Can't delete the committee because it isn't registered!", HttpStatus.NOT_FOUND);
        } catch (Exception e){
            logger.error(e.getMessage());
            throw new CustomException("Error", "Error delete committee", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
