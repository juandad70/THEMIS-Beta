package co.sena.edu.themis.Business;

import co.sena.edu.themis.Dto.RoleDto;
import co.sena.edu.themis.Dto.StudySheetDto;
import co.sena.edu.themis.Entity.Role;
import co.sena.edu.themis.Entity.StudySheet;
import co.sena.edu.themis.Service.RoleService;
import co.sena.edu.themis.Service.StudySheetService;
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
public class StudySheetBusiness {
    @Autowired
    private StudySheetService studySheetService;

    private final ModelMapper modelMapper = new ModelMapper();
    private static final Logger logger = Logger.getLogger(StudySheetBusiness.class);

    public Page<StudySheetDto> findAll(int page, int size) {
        try {
            PageRequest pageRequest = PageRequest.of(page, size);
            Page<StudySheet> studySheets = studySheetService.findAll(pageRequest);

            if (studySheets.isEmpty()) {
                logger.info("Study sheet not found!");
            }
            Page<StudySheetDto> studySheetDto = studySheets.map(StudySheet -> modelMapper.map(StudySheet, StudySheetDto.class));
            return studySheetDto;
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new CustomException("Error", "Error getting study sheets", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public StudySheetDto findById(Long id) {
        try {
            StudySheet studySheet = studySheetService.getById(id);
            logger.info("Study sheet: {}" + studySheet);
            if (studySheet != null) {
                return modelMapper.map(studySheet, StudySheetDto.class);
            } else {
                throw new CustomException("Not Found", "Not found study sheet wwith that id", HttpStatus.NOT_FOUND);
            }
        } catch (EntityNotFoundException entNotFound) {
            logger.info(entNotFound.getMessage());
            throw new CustomException("Not found", "Not found study sheet with that id", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new CustomException("Error", "Error getting study sheet by id", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public boolean createStudySheet(StudySheetDto studySheetDto) {
        try {
            StudySheet studySheet = modelMapper.map(studySheetDto, StudySheet.class);
            studySheetService.save(studySheet);
            logger.info("Study sheet created successfully");
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new CustomException("Error", "Error creating study sheet", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public boolean updateStudySheet(StudySheetDto studySheetDto) {
        try {
            if (studySheetDto.getId() == null) {
                logger.info("Can't update study sheet because the id is null!");
            }

            StudySheet existingStudySheet = studySheetService.getById(studySheetDto.getId());
            logger.info("Role: {}" + existingStudySheet);

            StudySheet updatedStudySheet = modelMapper.map(studySheetDto, StudySheet.class);
            studySheetService.save(updatedStudySheet);
            return true;
        } catch (EntityNotFoundException entNotFound) {
            logger.info("The study sheet you are tying to update is not registered");
            throw new CustomException("Not Found", "Can't update the study sheet because it isn't registered", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new CustomException("Error", "Error update study sheet", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public boolean deleteStudySheetById(Long id) {
        try {
            if (id == null) {
                logger.info("Can't delete study sheet because the id is null");
            }

            StudySheet deletingStudySheet = studySheetService.getById(id);
            logger.info("Study sheet: {}" + deletingStudySheet);

            studySheetService.deleteById(id);
            logger.info("Study sheet deleted successfully");
            return true;
        } catch (EntityNotFoundException entNotFound) {
            logger.info("The study sheet you are trying to delete is not registered");
            throw new CustomException("Not found", "Can't delete the study sheet because it isn't registered", HttpStatus.NOT_FOUND);
        } catch (Exception e){
            logger.error(e.getMessage());
            throw new CustomException("Error", "Error delete study sheet", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
