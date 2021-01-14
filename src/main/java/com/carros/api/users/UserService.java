package com.carros.api.users;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository rep;

    public List<UserDTO> getUsers() {
        return rep.findAll().stream().map(UserDTO::create).collect(Collectors.toList());
    }

    public UserDTO getUserByLogin(String login) {
        ModelMapper mm = new ModelMapper();

        return mm.map(rep.findByLogin(login), UserDTO.class);
    }
}
