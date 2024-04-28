package com.wolroys.wellbeing.service.impl;

import com.wolroys.wellbeing.entity.User;
import com.wolroys.wellbeing.repository.UserRepository;
import com.wolroys.wellbeing.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    public List<User> getAll() {
        return userRepository.findAll();
    }

//    public User getById(Long id) {
//        return userRepository.findById(id);
//    }

}
