package ch.uzh.ifi.seal.soprafs19.controller;

import ch.uzh.ifi.seal.soprafs19.constant.UserStatus;
import ch.uzh.ifi.seal.soprafs19.entity.User;
import ch.uzh.ifi.seal.soprafs19.repository.UserRepository;
import ch.uzh.ifi.seal.soprafs19.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class UserController {

    private final UserService service;

    UserController(UserService service) {
        this.service = service;
    }

    @GetMapping("/users")
    ResponseEntity all() {

        Iterable<User> allUsers = service.getUsers();
        for (User user: allUsers){
            user.hidePassword();
        }
        return ResponseEntity.status(HttpStatus.OK).body(allUsers);
    }

    @GetMapping("/users/{id}")
    ResponseEntity userByID(@PathVariable Long id) {

        User temp = service.searchID(id);
        if (temp != null)
            return ResponseEntity.status(HttpStatus.OK).body(temp.hidePassword());
        else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("There is no user with id " + id + "in the database.");
    }

    @PostMapping("/login")
    ResponseEntity getUser(@RequestBody User user) {

        User record = service.searchUsername(user.getUsername());

        if (record != null) {
            if (record.getPassword().equals(user.getPassword())) {

                service.setUserOnline(record);
                service.saveUser(record);

                return ResponseEntity.status(HttpStatus.OK).body(record.hidePassword());
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Wrong username or password.");
    }

    @GetMapping("/logout/{id}")
    ResponseEntity logoutUser(@PathVariable Long id) {

        User record = service.searchID(id);

        if (record != null) {

            service.setUserOffline(record);
            service.saveUser(record);

            return ResponseEntity.status(HttpStatus.OK).body("Logout successful.");
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Logout unsuccessful.");
    }


    @PostMapping("/users")
    ResponseEntity createUser(@RequestBody User user) {

        User record = service.searchUsername(user.getUsername());

        if (record == null) {

            User newRecord = service.createUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(newRecord.hidePassword());
        }

        return ResponseEntity.status(HttpStatus.CONFLICT).body("The username you have chosen is already taken.");
    }

    @PutMapping("/users/{id}")
    ResponseEntity userByID(@PathVariable long id, @RequestBody User updatedRec) {

        User record = service.searchID(id);

        if (record == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("There is no user with id " + id + "in the database.");
        }
        // user name did not change
        if (service.searchUsername(updatedRec.getUsername()) == record) {
            service.updateProfile(updatedRec, id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Update successful, no change to username.");
        }
        // user name is not given yet
        if (service.searchUsername(updatedRec.getUsername()) == null) {
            service.updateProfile(updatedRec, id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("The record was updated successfully.");
        }
        // everything else
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The username is already taken.");

    }

}