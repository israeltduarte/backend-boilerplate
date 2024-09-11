package br.isertech.com.easyfinancesback.controller;

import br.isertech.com.easyfinancesback.dto.UserDTO;
import br.isertech.com.easyfinancesback.entity.ITUser;
import br.isertech.com.easyfinancesback.entity.Item;
import br.isertech.com.easyfinancesback.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<List<ITUser>> getAllUsers() {

        List<ITUser> users = userService.getAllUsers();
        if (!users.isEmpty()) {
            for (ITUser user : users) {
                user.add(linkTo(methodOn(UserController.class).getUserById(user.getId())).withSelfRel());
            }
        }

        return ResponseEntity.status(HttpStatus.OK).body(users);
    }

//    @GetMapping("/{userId}/items")
//    public ResponseEntity<List<Item>> getAllUserItems(@PathVariable String userId) {
//
//        List<Item> items = userService.getAllUserItems(userId);
//        if (!items.isEmpty()) {
//            for (Item item : items) {
//                item.add(linkTo(methodOn(ItemController.class).getItemById(item.getId())).withSelfRel());
//            }
//        }
//
//        return ResponseEntity.status(HttpStatus.OK).body(items);
//    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<ITUser> getUserById(@PathVariable String id) {

        ITUser user = userService.getUserById(id);

        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ITUser> updateUserById(@Valid @RequestBody UserDTO dto, @PathVariable String id) {

        ITUser user = userService.updateUserById(dto, id);

        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAllUsers() {

        userService.deleteAllUsers();

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable String id) {

        userService.deleteUserById(id);

        return ResponseEntity.ok().build();
    }
}