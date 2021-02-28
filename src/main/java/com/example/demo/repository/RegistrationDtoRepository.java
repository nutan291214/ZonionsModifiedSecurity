package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.RegistrationEntity;
import com.example.demo.entity.RegistrationEntityDto;

@Repository
public interface RegistrationDtoRepository extends JpaRepository<RegistrationEntityDto, Long> {
	Optional<RegistrationEntityDto> findByUsername(String username);
	Boolean deleteByUsername(String username);
}
