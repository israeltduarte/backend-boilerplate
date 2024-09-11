package br.isertech.com.easyfinancesback.service;

import br.isertech.com.easyfinancesback.constants.Messages;
import br.isertech.com.easyfinancesback.dto.RoleDTO;
import br.isertech.com.easyfinancesback.entity.Role;
import br.isertech.com.easyfinancesback.enums.RoleType;
import br.isertech.com.easyfinancesback.error.exception.OperationFailedException;
import br.isertech.com.easyfinancesback.error.exception.RoleAlreadyExistsException;
import br.isertech.com.easyfinancesback.error.exception.RoleNotFoundException;
import br.isertech.com.easyfinancesback.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class RoleService {

    private final RoleRepository roleRepository;

    public List<Role> getAllRoles() {

        List<Role> roles = roleRepository.findAll();
        log.info("RoleService - getAllRoles() - List<Role>={}", roles);

        return roles;
    }

    public Role registerRole(RoleDTO roleDTO) {

        RoleType roleName = RoleType.valueOf(roleDTO.getRoleName());

        Optional<Role> role = roleRepository.findByRoleName(roleName);
        if (role.isPresent()) {
            throw new RoleAlreadyExistsException(Messages.ROLE_ALREADY_EXISTS);
        }

        Role newRole = Role.builder()
                .roleName(RoleType.valueOf(roleDTO.getRoleName()))
                .build();
        try {
            newRole = roleRepository.save(newRole);
        } catch (DataIntegrityViolationException e) {
            String message = Messages.ROLE_ALREADY_EXISTS.concat(". RoleType = " + roleDTO.getRoleName());
            log.error(message);
            throw new RoleAlreadyExistsException(message);
        } catch (Exception e) {
            log.error(Messages.OPERATION_FAILED);
            throw new OperationFailedException(Messages.OPERATION_FAILED);
        }
        log.info("RoleService - registerRole() - Role={}", newRole);

        return newRole;
    }

    public Role findByRoleName(String roleName) {

        RoleType roleType;
        try {
            roleType = RoleType.valueOf(roleName);
        } catch (Exception e) {
            throw new RoleNotFoundException(Messages.ROLE_NOT_FOUND_INFO.concat(roleName));
        }
        Role role = roleRepository.findByRoleName(roleType)
                .orElseThrow(() -> new RoleNotFoundException(Messages.ROLE_NOT_FOUND_INFO));
        log.info("RoleService - findByRoleName() - Role={}", role);

        return role;
    }

    public List<Role> checkAndGetRoles(List<String> rolesDTO) {

        return rolesDTO.parallelStream()
                .map(this::findByRoleName)
                .toList();
    }
}
