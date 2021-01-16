package com.springboot.studentservices.userservices;

import com.springboot.studentservices.entities.User;

public interface UserService {
	void save(User user);

    User findByUsername(String username);
}
