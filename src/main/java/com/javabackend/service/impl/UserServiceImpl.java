package com.javabackend.service.impl;

import com.javabackend.common.UserStatus;
import com.javabackend.controller.request.UserCreateRequest;
import com.javabackend.controller.request.UserPasswordRequest;
import com.javabackend.controller.request.UserUpdateRequest;
import com.javabackend.controller.response.UserResponse;
import com.javabackend.domain.Address;
import com.javabackend.domain.User;
import com.javabackend.repository.AddressRepository;
import com.javabackend.repository.UserRepository;
import com.javabackend.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j(topic = "USER-SERVICE")
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;

    @Override
    public List<UserResponse> findAll() {
        return List.of();
    }

    @Override
    public UserResponse findById(Long id) {
        return null;
    }

    @Override
    public UserResponse findByUsername(String username) {
        return null;
    }

    @Override
    public UserResponse findByEmail(String email) {
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public long save(UserCreateRequest req) {
        log.info("Saving user: {}", req);
        User user  = new User();
        user.setFirstName(req.getFirstName());
        user.setLastName(req.getLastName());
        user.setGender(req.getGender());
        user.setBirthday(req.getBirthday());
        user.setEmail(req.getEmail());
        user.setPhone(req.getPhone());
        user.setUsername(req.getUsername());
        user.setType(req.getType());
        user.setStatus(UserStatus.NONE);

        userRepository.save(user);
        log.info("Saved user: {}", user);

        if (user.getId() != null) {
            log.info("User id: {}", user.getId());
            List<Address> addresses = new ArrayList<>();
            req.getAddresses().forEach(address -> {
                Address addressEntity = new Address();
                addressEntity.setApartmentNumber(address.getApartmentNumber());
                addressEntity.setFloor(address.getFloor());
                addressEntity.setBuilding(address.getBuilding());
                addressEntity.setStreetNumber(address.getStreetNumber());
                addressEntity.setStreet(address.getStreet());
                addressEntity.setCity(address.getCity());
                addressEntity.setCountry(address.getCountry());
                addressEntity.setAddressType(address.getAddressType());
                addressEntity.setUserId(user.getId());
                addresses.add(addressEntity);
            });
            addressRepository.saveAll(addresses);
            log.info("Saved addresses: {}", addresses);
        }
        return user.getId();
    }

    @Override
    public void update(UserUpdateRequest req) {

    }

    @Override
    public void changePassword(UserPasswordRequest req) {

    }

    @Override
    public void delete(Long id) {

    }
}
