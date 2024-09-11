package br.isertech.com.easyfinancesback.repository;

import br.isertech.com.easyfinancesback.entity.Role;
import br.isertech.com.easyfinancesback.enums.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, String>, JpaSpecificationExecutor<Role> {

    Optional<Role> findByRoleName(RoleType roleType);

}
