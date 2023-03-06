package com.maction.springmvc.repository;

import com.maction.springmvc.model.Department;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {

    @Query("SELECT d FROM Department d LEFT JOIN fetch d.employees")
    List<Department> getAllOneQuery();

    @EntityGraph(attributePaths = {"employees"})
    List<Department> findAll();
}
