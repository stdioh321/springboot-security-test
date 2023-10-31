package com.example.securitytest.services;

import com.example.securitytest.dtos.user.UserCreateForm;
import com.example.securitytest.dtos.user.UserDto;
import com.example.securitytest.dtos.user.UserPatchForm;
import com.example.securitytest.enums.UserRole;
import com.example.securitytest.exceptions.NotFoundException;
import com.example.securitytest.models.User;
import com.example.securitytest.repositories.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User getById(Long id) throws NotFoundException {
        return this.userRepository.findById(id).orElseThrow(() -> new NotFoundException("User entity with %d not found".formatted(id)));
    }

    public List<User> getAll() {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        return this.userRepository.findAll(sort);
    }

    public Page<User> getPageabled(Pageable pageable) {
        return this.userRepository.findPaginated(pageable);
    }

    public User post(UserCreateForm objCreateForm) {
        return this.userRepository.save(objCreateForm.toEntity());
    }

    public void deleteById(Long id) throws NotFoundException {
        this.getById(id);
        this.userRepository.deleteById(id);
    }

    public User patchById(Long id, UserPatchForm objPatchForm) throws NotFoundException {
        User currentUser = this.getById(id);

        if (objPatchForm.getName() != null) currentUser.setName(objPatchForm.getName());
        if (objPatchForm.getUsername() != null) currentUser.setUsername(objPatchForm.getUsername());
        if (objPatchForm.getEmail() != null) currentUser.setEmail(objPatchForm.getEmail());

        return this.userRepository.save(currentUser);
    }

    public User putById(Long id, UserCreateForm objCreateForm) throws NotFoundException {
        User currentUser = this.getById(id);
        BeanUtils.copyProperties(objCreateForm, currentUser);

        return this.userRepository.save(currentUser);
    }

    public User getUserByUsername(String username) {
        return this.userRepository.findByUsername(username);
    }

    public User post(User user) {
        return this.userRepository.save(user);
    }
}
