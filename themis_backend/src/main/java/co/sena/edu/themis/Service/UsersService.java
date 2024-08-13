package co.sena.edu.themis.Service;

import co.sena.edu.themis.Entity.Users;
import co.sena.edu.themis.Repository.UserRepository;
import co.sena.edu.themis.Service.Dao.Idao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsersService implements Idao<Users, Long> {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<Users> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Users getById(Long id) {
        return userRepository.getById(id);
    }

    @Override
    public void save(Users users) {
        userRepository.save(users);
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public Page<Users> findAllPage(PageRequest pageable) {
        return this.userRepository.findAll(pageable);
    }
}
