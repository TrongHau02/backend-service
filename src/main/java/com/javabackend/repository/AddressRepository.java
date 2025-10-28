package com.javabackend.repository;

import com.javabackend.domain.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address,Long> {
    Address findByUserIdAndAddressType(Long userId, Integer addressType);
}