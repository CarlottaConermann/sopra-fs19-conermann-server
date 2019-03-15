package ch.uzh.ifi.seal.soprafs19.service;

import ch.uzh.ifi.seal.soprafs19.constant.UserStatus;
import ch.uzh.ifi.seal.soprafs19.entity.User;
import ch.uzh.ifi.seal.soprafs19.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;

import java.util.UUID;

@Service
@Transactional
public class UserService {

    private final Logger log = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;


    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User searchUsername(String name ){
        return this.userRepository.findByUsername(name );
    }

    public User searchID(long id ){
        return this.userRepository.findById(id );
    }

    public void setUserOnline(User user){
        user.setStatus(UserStatus.ONLINE);
    }

    public void setUserOffline(User user){
        user.setStatus(UserStatus.OFFLINE);
    }

    public void saveUser(User user){
        userRepository.save(user);
    }

    public void updateProfile(User referent, long id){
        // make sure client gives valid values
        User record = this.searchID(id);

        record.setUsername(referent.getUsername() );
        record.setBirthday(referent.getBirthday() );

        userRepository.save(record);
    }

    public Iterable<User> getUsers() {
        return this.userRepository.findAll();
    }

    public User createUser(User newUser) {
        newUser.setToken(UUID.randomUUID().toString());
        newUser.setStatus(UserStatus.OFFLINE);
        newUser.setCreationDate();
        if( newUser.getBirthday() == null) {
            newUser.setBirthday("2001-01-01");
        }
        userRepository.save(newUser);
        log.debug("Created Information for User: {}", newUser);
        return newUser;
    }
}

/**package ch.uzh.ifi.seal.soprafs19.service;

import ch.uzh.ifi.seal.soprafs19.constant.UserStatus;
import ch.uzh.ifi.seal.soprafs19.entity.User;
import ch.uzh.ifi.seal.soprafs19.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
public class UserService {

    private final Logger log = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;


    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Iterable<User> getUsers() {
        return this.userRepository.findAll();
    }

    public User createUser(User newUser) {
        newUser.setToken(UUID.randomUUID().toString());
        newUser.setStatus(UserStatus.ONLINE);
        newUser.setCreationDate();
        newUser.setBirthday("01.01.1800");
        newUser.setName("Unknown");
        userRepository.save(newUser);
        log.debug("Created Information for User: {}", newUser);
        return newUser;
    }

    public User findByUsername(String name ){
        return this.userRepository.findByUsername(name);
    }

    public User findById(Long id){
        return this.userRepository.findUserById(id);
    }

    public void setOnline(User user){
        user.setStatus(UserStatus.ONLINE);
        userRepository.save(user);
    }

    public void setOffline(User user){
        user.setStatus(UserStatus.OFFLINE);
        userRepository.save(user);
    }
}
**/
