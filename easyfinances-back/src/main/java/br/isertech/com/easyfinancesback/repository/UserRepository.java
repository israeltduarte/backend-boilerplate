package br.isertech.com.easyfinancesback.repository;

import br.isertech.com.easyfinancesback.entity.ITUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<ITUser, String>, JpaSpecificationExecutor<ITUser> {

    Optional<ITUser> findByUsername(String username);

    Optional<ITUser> findByEmail(String email);

}
