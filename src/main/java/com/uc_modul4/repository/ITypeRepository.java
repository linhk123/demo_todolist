package com.uc_modul4.repository;

import com.uc_modul4.entity.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITypeRepository extends JpaRepository<Type, Long> {
    // Spring Boot sẽ tự động cung cấp các hàm save, findAll, findById...
    Type findByName(String name);
}