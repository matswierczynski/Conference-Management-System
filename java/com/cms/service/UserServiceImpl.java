package com.cms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.cms.entity.Paper;
import com.cms.entity.Role;
import com.cms.entity.User;
import com.cms.repository.RoleRepository;
import com.cms.repository.UserRepository;

@Service
@ComponentScan(basePackages = { "com.cms.repository" })
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void saveUser(User user, String role) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        Role userRole = roleRepository.findByRole(role);
        user.setRole(userRole);
        userRepository.save(user);

    }

    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void AddPaper(User user, Paper paper) {
        List<Paper> listOfPapers = user.getPapers();
        listOfPapers.add(paper);
        user.setPapers(listOfPapers);
        userRepository.save(user);
    }

    @Override
    public List<User> findAllByRole(String role) {
        Role thisRole = roleRepository.findByRole(role);
        List<User> reviewers = userRepository.findAllByRole(thisRole);
        return reviewers;
    }

    @Override
    public int getNumberOfPapers(User user) {
        List<Paper> papers = user.getPapers();
        return papers == null ? 0 : papers.size();
    }

    @Override
    public List<Paper> getPapers(User user) {
        return user.getPapers();
    }

}
