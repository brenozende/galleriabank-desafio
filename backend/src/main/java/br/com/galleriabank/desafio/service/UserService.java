package br.com.galleriabank.desafio.service;

import br.com.galleriabank.desafio.exception.BusinessException;
import br.com.galleriabank.desafio.exception.ResourceNotFoundException;
import br.com.galleriabank.desafio.model.dto.request.CreateUserRequest;
import br.com.galleriabank.desafio.model.entity.User;
import br.com.galleriabank.desafio.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User createUser(CreateUserRequest request) {
        if (userRepository.existsByUsername(request.username()))
            throw new BusinessException("Username already taken");

        User user = new User();
        user.setName(request.name());
        user.setUsername(request.username());
        user.setPassword(passwordEncoder.encode(request.password()));

        return userRepository.save(user);
    }

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    public User updateUser(Long id, CreateUserRequest request) {
        User user = findById(id);

        if (userRepository.findByUsername(request.username())
                .filter(existingUser -> !existingUser.getId().equals(id))
                .isPresent())
            throw new BusinessException("Username already taken");

        user.setUsername(request.username());
        user.setName(request.name());
        user.setPassword(passwordEncoder.encode(request.password()));

        return userRepository.save(user);
    }

    public void delete(Long id) {
        User user = findById(id);
        user.setEnabled(false);
        userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found."));
    }
}
