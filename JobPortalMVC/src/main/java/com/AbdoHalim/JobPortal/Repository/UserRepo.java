package com.AbdoHalim.JobPortal.Repository;

import com.AbdoHalim.JobPortal.Entity.Job;
import com.AbdoHalim.JobPortal.Entity.User;
import com.AbdoHalim.JobPortal.Model.SearchModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepo extends JpaRepository<User,Long> {

    User findByEmail(String email);

    User findByName(String authentication);

}
