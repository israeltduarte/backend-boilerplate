package br.isertech.com.easyfinancesback.config.security;

import br.isertech.com.easyfinancesback.constants.Messages;
import br.isertech.com.easyfinancesback.entity.ITUser;
import br.isertech.com.easyfinancesback.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    public UserRepository userRepository;

    @Transactional
    public UserDetails loadUserById(String id) throws UsernameNotFoundException {

        ITUser user = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException(Messages.USER_NOT_FOUND_INFO));

        return UserDetailsImpl.build(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        ITUser user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(Messages.USER_NOT_FOUND_INFO.concat(". username: " + username)));

        return UserDetailsImpl.build(user);
    }

}
