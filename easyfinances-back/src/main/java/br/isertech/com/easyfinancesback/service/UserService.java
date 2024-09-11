package br.isertech.com.easyfinancesback.service;

import br.isertech.com.easyfinancesback.constants.Messages;
import br.isertech.com.easyfinancesback.dto.UserDTO;
import br.isertech.com.easyfinancesback.entity.ITUser;
import br.isertech.com.easyfinancesback.entity.Item;
import br.isertech.com.easyfinancesback.entity.Role;
import br.isertech.com.easyfinancesback.error.exception.UserNotFoundException;
import br.isertech.com.easyfinancesback.repository.UserRepository;
import br.isertech.com.easyfinancesback.util.ITUserTransformer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
//    private final ItemService itemService;

    public List<ITUser> getAllUsers() {

        List<ITUser> users = userRepository.findAll();

        log.info("UserService - getAllUsers() - List<ITUser>={}", users);

        return users;
    }

//    public List<Item> getAllUserItems(String userId) {
//
//        ITUser user = userRepository.findById(userId)
//                .orElseThrow(() -> new UserNotFoundException(Messages.USER_NOT_FOUND_INFO));
//
//        log.info("UserService - getAllUserItems() - ITUser={}", user);
//
//        List<Item> items = itemService.getAllItemsByUserId(userId);
//
//        log.info("UserService - getAllUserItems() - List<Item>={}", items);
//
//        return items;
//    }

    public ITUser addUser(UserDTO userDTO) {

        ITUser user = getNewUserEntityReady(userDTO);
        user = userRepository.save(user);

        log.info("UserService - addUser() - ITUser={}", user);

        return user;
    }

    private ITUser getNewUserEntityReady(UserDTO userDTO) {

        LocalDateTime time = LocalDateTime.now();
        List<Role> roles = roleService.checkAndGetRoles(userDTO.getRoles());
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        ITUser user = ITUserTransformer.fromDTO(userDTO);
        user.setCreated(time);
        user.setUpdated(time);
        user.setRoles(roles);

        return user;
    }

    public ITUser getUserById(String userId) {

        ITUser user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(Messages.USER_NOT_FOUND_INFO));

        log.info("UserService - getUserById() - ITUser={}", user);

        return user;
    }

    public boolean existsByUsername(String username) {

        boolean exists = userRepository.findByUsername(username).isPresent();

        log.warn("UserService - existsByUsername() - " + Messages.USERNAME_ALREADY_EXISTS);

        return exists;
    }

    public boolean existsByEmail(String email) {

        boolean exists = userRepository.findByEmail(email).isPresent();

        log.warn("UserService - existsByEmail() - " + Messages.EMAIL_ALREADY_EXISTS);

        return exists;
    }

    public ITUser updateUserById(UserDTO userDTO, String userId) {

        ITUser user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(Messages.USER_NOT_FOUND_INFO));

        List<Role> roles;
        user = ITUserTransformer.fromDTO(user, userDTO);
        if (null != userDTO.getRoles() && !userDTO.getRoles().isEmpty()) {
            roles = roleService.checkAndGetRoles(userDTO.getRoles());
            user.setRoles(roles);
        }
        user = userRepository.save(user);

        log.info("UserService - updateUserById() - ITUser={}", user);

        return user;
    }

    public void deleteUserById(String userId) {

        ITUser user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(Messages.USER_NOT_FOUND_INFO));
        userRepository.delete(user);

        log.info("UserService - deleteUserById() - ".concat(Messages.USER_DELETED));
    }

    public void deleteAllUsers() {

        userRepository.deleteAll();

        log.info("UserService - deleteAllUsers() - ".concat(Messages.USERS_DELETED));
    }
}
