package com.AbdoHalim.JobPortal.Repository;

import com.AbdoHalim.JobPortal.Entity.Job;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobRepo extends JpaRepository<Job,Long> {
    @Query( value = "select * from Job where job_id=?1 and pause=false",nativeQuery = true)
    Job GetJob(Long id);
    @Query( value = "select * from Job where user_id=?1",nativeQuery = true)
    List<Job> findAllJobsByUserId(Long userId);
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM job WHERE job_id = ?1" ,nativeQuery = true)
    void deletejobById(Long id);
    @Query(value = "select * FROM job WHERE job_id = ?1" ,nativeQuery = true)

    Job findByJobId(long id);
    @Query( value = "select * from Job where  pause=false",nativeQuery = true)

    List<Job> findAllJobs();
    @Query( value = "select * from Job where user_id=?1 and pause=false ",nativeQuery = true)

    List<Job> findAllJobsByCompanyId(Long userId);
    @Query(value = "SELECT * FROM Job WHERE " +
            "(:country IS NULL OR :country = '' OR country = :country) " +
            "AND (:title IS NULL OR :title = '' OR title LIKE %:title%) " +
            "AND (:city IS NULL OR :city = '' OR city = :city) " +
            "AND (:pause IS NULL OR pause = :pause) " +
            "AND (:user_id IS NULL OR user_id = 0 OR user_id = :user_id)", nativeQuery = true)
    List<Job> Search(String country, String city, String title, Boolean pause, Long user_id);

}
