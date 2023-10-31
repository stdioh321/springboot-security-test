package com.example.securitytest.controllers.api;

import com.example.securitytest.dtos.user.UserCreateForm;
import com.example.securitytest.dtos.user.UserDto;
import com.example.securitytest.dtos.user.UserPatchForm;
import com.example.securitytest.exceptions.NotFoundException;
import com.example.securitytest.models.User;
import com.example.securitytest.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<?> get(@PageableDefault(size = 3, sort = "id", direction = Sort.Direction.DESC, page = 0) Pageable pageable) {
        Page<UserDto> result = this.userService.getPageabled(pageable).map(user -> new UserDto(user));
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> putById(@PathVariable("id") Long id) {
        try {
            var result = this.userService.getById(id);
            return ResponseEntity.ok(new UserDto(result));
        } catch (NotFoundException exception) {
            return ResponseEntity.notFound().build();
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

//    @PostMapping
//    public ResponseEntity<?> post(@Valid @RequestBody UserCreateForm userCreateForm, BindingResult bindingResult) {
//        if (bindingResult.getErrorCount() > 0) {
//            return ResponseEntity.badRequest().body(
//                    bindingResult.getAllErrors()
//            );
//        }
//        return ResponseEntity.ok(new UserDto(this.userService.post(userCreateForm)));
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<?> putById(@PathVariable("id") Long id, @Valid @RequestBody UserCreateForm userCreateForm) {
//        try {
//            var result = this.userService.putById(id, userCreateForm);
//            return ResponseEntity.ok(new UserDto(result));
//        } catch (NotFoundException exception) {
//            return ResponseEntity.notFound().build();
//        } catch (Exception exception) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }
//
//    @PatchMapping("/{id}")
//    public ResponseEntity<?> patchById(@PathVariable("id") Long id, @Valid @RequestBody UserPatchForm userPatchForm) throws NotFoundException {
//        User result = this.userService.patchById(id, userPatchForm);
//        return ResponseEntity.ok(new UserDto(result));
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<?> deleteById(@PathVariable("id") Long id) {
//        try {
//            this.userService.deleteById(id);
//            return ResponseEntity.ok().build();
//        } catch (NotFoundException exception) {
//            return ResponseEntity.notFound().build();
//        } catch (Exception exception) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }
}
