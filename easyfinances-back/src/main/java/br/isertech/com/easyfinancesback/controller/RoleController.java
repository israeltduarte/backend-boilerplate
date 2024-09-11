package br.isertech.com.easyfinancesback.controller;

import br.isertech.com.easyfinancesback.dto.RoleDTO;
import br.isertech.com.easyfinancesback.entity.Role;
import br.isertech.com.easyfinancesback.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<List<Role>> getAllRoles() {

        List<Role> roles = roleService.getAllRoles();

        return ResponseEntity.status(HttpStatus.OK).body(roles);
    }

    @PostMapping
    public ResponseEntity<Role> registerRole(@RequestBody RoleDTO dto) {

        Role role = roleService.registerRole(dto);

        return ResponseEntity.status(HttpStatus.OK).body(role);
    }

}