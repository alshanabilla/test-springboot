package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import com.example.demo.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    //PURE JPQL
    // @Query("SELECT u FROM User u JOIN u.employee e where e.email = ?1 AND u.password = ?2")

    //NATIVE JPQL
    // @Query(
    //     value = "SELECT * FROM tb_m_user u JOIN tb_m_employee e ON u.user_id = e.employee_id WHERE e.email = ?1 AND u.password = ?2", 
    //     nativeQuery = true)

    // public User login(String email, String password);

    @Query("SELECT new com.example.demo.config.MyUserDetails(e.email, u.password, r.name) FROM User u JOIN u.employee e JOIN u.role r WHERE e.email = ?1")
    public UserDetails login(String email);
}
