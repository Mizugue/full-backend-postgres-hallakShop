package com.hallakShop.hallakShop.services;

import com.hallakShop.hallakShop.dto.UserDTO;
import com.hallakShop.hallakShop.entities.Role;
import com.hallakShop.hallakShop.entities.User;
import com.hallakShop.hallakShop.projections.UserDetailsProjection;
import com.hallakShop.hallakShop.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {


    private UserRepository userRepository;
    private ModelMapper modelMapper;

    @Autowired
    public UserService(UserRepository userRepository, ModelMapper modelMapper){
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<UserDetailsProjection> result = userRepository.searchUserAndRolesByEmail(username);
        if(result.isEmpty()){
            throw new UsernameNotFoundException("User not found");
        }

        User user = new User();
        user.setEmail(username);
        user.setPassword(result.getFirst().getPassword());
        for(UserDetailsProjection projection : result){
            user.addRole(new Role(projection.getRoleId(), projection.getAuthority()));
        }

        return user;
    }

    protected User authenticated(){
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Jwt jwtMain = (Jwt) authentication.getPrincipal();
            String username = jwtMain.getClaim("username");
            return userRepository.findByEmail(username).get();

        } catch (Exception e){
            throw new UsernameNotFoundException("User not found");
        }
    }

    @Transactional(readOnly = true)
    public UserDTO getMe(){
        return modelMapper.map(authenticated(), UserDTO.class);
    }

}
