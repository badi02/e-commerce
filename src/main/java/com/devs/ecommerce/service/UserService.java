package com.devs.ecommerce.service;

import com.devs.ecommerce.dto.ChangePasswordRequest;
import com.devs.ecommerce.exception.ResourceNotFoundException;
import com.devs.ecommerce.model.User;
import com.devs.ecommerce.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /*public User registerUser(User user){
        if(userRepository.findByEmail(user.getEmail()).isPresent()){
            throw new IllegalStateException("Email already taken");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(User.Role.ADMIN);
        //user.setConfirmationCode(generateConfirmationCode());
        //user.setEmailConfirmation(false);
        //emailService.sendConfirmationCode(user);
        return userRepository.save(user);
    }*/

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    public void changePassword(String email, ChangePasswordRequest request){
        User user = getUserByEmail(email);
        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())){
            throw new BadCredentialsException("Current password is incorrect");
        }
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }

    /*
    public void confirmEmail(String email, String confirmationCode){
        User user = getUserByEmail(email);
        System.out.println(user.getConfirmationCode());
        System.out.println(confirmationCode);

        if(user.getConfirmationCode().equals(confirmationCode)){
            user.setEmailConfirmation(true);
            user.setConfirmationCode(null);
            userRepository.save(user);
        }
        else{
            throw new BadCredentialsException("Invalid confirmation code");
        }
    }
     */


    /*
    private String generateConfirmationCode(){
        Random random = new Random();
        int code = 100000 + random.nextInt(900000);
        return String.valueOf(code);
    }
     */
    
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    }
}
