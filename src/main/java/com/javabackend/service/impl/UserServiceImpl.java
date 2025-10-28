package com.javabackend.service.impl;

import com.javabackend.common.UserStatus;
import com.javabackend.controller.request.UserCreateRequest;
import com.javabackend.controller.request.UserPasswordRequest;
import com.javabackend.controller.request.UserUpdateRequest;
import com.javabackend.controller.response.UserResponse;
import com.javabackend.domain.Address;
import com.javabackend.domain.User;
import com.javabackend.exception.ResourceNotFoundException;
import com.javabackend.repository.AddressRepository;
import com.javabackend.repository.UserRepository;
import com.javabackend.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final PasswordEncoder passwordEncoder;

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
        User user = new User();
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
    @Transactional(rollbackFor = Exception.class)
    public void update(UserUpdateRequest req) {
        log.info("Update user: {}", req);
        //Get user by id
        User user = this.getUserById(req.getId());

        //Set data
        user.setFirstName(req.getFirstName());
        user.setLastName(req.getLastName());
        user.setGender(req.getGender());
        user.setBirthday(req.getBirthday());
        user.setUsername(req.getUsername());
        user.setEmail(req.getEmail());
        user.setPhone(req.getPhone());

        // Save to DB
        this.userRepository.save(user);
        log.info("Updated user: {}", user);

        // Save address
        List<Address> addresses = new ArrayList<>();
        req.getAddresses().forEach(address -> {
            Address addressEntity = this.addressRepository.findByUserIdAndAddressType(user.getId(), address.getAddressType());
            if (addressEntity == null) {
                addressEntity = new Address();
            }
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

        // Save address
        this.addressRepository.saveAll(addresses);
        log.info("Updated addresses: {}", addresses);
    }

    @Override
    public void changePassword(UserPasswordRequest req) {
        log.info("Changing password for user: {}", req);

        User user = this.getUserById(req.getId());
        if (req.getPassword().equals(req.getConfirmPassword())) {
            user.setPassword(passwordEncoder.encode(req.getPassword()));
        }
        this.userRepository.save(user);
    }

    @Override
    public void delete(Long id) {
        log.info("Deleting user: {}", id);
        User user = this.getUserById(id);
        user.setStatus(UserStatus.INACTIVE);
        this.userRepository.save(user);
        log.info("Deleted user: {}", user);

    }

    /*
    * Get user by id
    * @param id
    * @return
    */
    private User getUserById(Long id) {
        return this.userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }
}
