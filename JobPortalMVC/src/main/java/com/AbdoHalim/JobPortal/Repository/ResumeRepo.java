package com.AbdoHalim.JobPortal.Repository;

import com.AbdoHalim.JobPortal.Entity.Resume;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResumeRepo extends JpaRepository<Resume,Long> {
    @Query(value = "SELECT r.* FROM resume r INNER JOIN job_resume jr ON r.id = jr.resume_id WHERE jr.job_id = ?1", nativeQuery = true)

    List<Resume> findAllbyJobId(long id);
    @Query(value = "select  * from resume where user_id=?1",nativeQuery = true)

    Resume GetUserResume(Long userId);
}
