package com.cms.service;

import java.util.List;

import com.cms.entity.Paper;
import com.cms.entity.User;

public interface UserService {

    public User findUserByEmail(String email);

    public List<User> findAllByRole(String role);

    public void saveUser(User user, String role);

    public void saveUser(User user);

    public void AddPaper(User user, Paper paper);

    public int getNumberOfPapers(User user);

    public List<Paper> getPapers(User user);

}
