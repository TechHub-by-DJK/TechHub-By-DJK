package com.janith.Service;

import com.janith.config.JwtProvider;
import com.janith.model.User;
import com.janith.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImp implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtProvider jwtProvider;
    
    @Override
    public User findUserByJwtToken(String jwt) throws Exception {
        String email = jwtProvider.getEmailFromJwtToken(jwt);
        User user = findUserByEmail(email);
        return user;
    }

    @Override
    public User findUserByEmail(String email) throws Exception {
        User user = userRepository.findByEmail(email);

        if(user == null){
            throw new Exception("user not found");
        }

        return user;
    }

    @Override
    public User updateProfileImage(Long userId, String imageUrl) throws Exception {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new Exception("user not found"));
        user.setProfileImageUrl(imageUrl);
        return userRepository.save(user);
    }
}
