package co.sena.edu.themis.Business;

import co.sena.edu.themis.Dto.ProgramDto;
import co.sena.edu.themis.Entity.Program;
import co.sena.edu.themis.Service.ProgramService;
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
public class ProgramBusiness {

    @Autowired
    private ProgramService programService;

    private final ModelMapper modelMapper = new ModelMapper();
    private static final Logger logger = Logger.getLogger(ProgramBusiness.class);

    public Page<ProgramDto> findAll(int page, int size) {
        try {
            PageRequest pageRequest = PageRequest.of(page, size);
            Page<Program> programs = programService.findAll(pageRequest);

            if (programs.isEmpty()){
                logger.info("Programs not found!");
            }
            Page<ProgramDto> programDtoPage = programs.map(Program -> modelMapper.map(Program, ProgramDto.class));
            return programDtoPage;
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new CustomException("Error", "Error getting programs", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ProgramDto findById(Long id) {
        try {
            Program program = programService.getById(id);
            logger.info("Program: {}" + program);
            if (program != null) {
                return modelMapper.map(program, ProgramDto.class);
            } else {
                throw new CustomException("Not Found", "Not found program with that id", HttpStatus.NOT_FOUND);
            }
        } catch (EntityNotFoundException entNotFound) {
            logger.info(entNotFound.getMessage());
            throw new CustomException("Not Found", "Not found program with that id", HttpStatus.NOT_FOUND);
        } catch (Exception e){
            logger.error(e.getMessage());
            throw new CustomException("Error", "Error getting program by id", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    public boolean createProgram(ProgramDto programDto){
        try {
            Program program = modelMapper.map(programDto, Program.class);
            programService.save(program);
            logger.info("Program created successfully");
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new CustomException("Error", "Error creating program", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public boolean updateProgram(ProgramDto programDto){
        try {
            if (programDto.getId() == null) {
                logger.info("Can't update program because the id is null!");
            }

            Program existingProgram = programService.getById(programDto.getId());
            logger.info("Program: {}" + existingProgram);

            Program updatedProgram = modelMapper.map(programDto, Program.class);
            programService.save(updatedProgram);
            return true;
        } catch (EntityNotFoundException entNotFound) {
            logger.info("THe program you are trying to update is not registered");
            throw new CustomException("Not Found", "Can't update the program because it isn't registered", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new CustomException("Error", "Error update program", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public boolean deleteProgramById(Long id) {
        try {
            if (id == null) {
                logger.info("Can't delete program because the id is null");
            }

            Program deletingProgram = programService.getById(id);
            logger.info("Program: {}" + deletingProgram);

            programService.deleteById(id);
            logger.info("Program deleted successfully");
            return true;
        } catch (EntityNotFoundException entNotFound) {
            logger.info("The program you are trying to delete is not registered");
            throw new CustomException("Not Found", "Can't delete the program because it isn't registered", HttpStatus.NOT_FOUND);
        } catch (Exception e){
            logger.error(e.getMessage());
            throw new CustomException("Error", "Error delete program", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
