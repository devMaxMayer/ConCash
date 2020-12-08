package BlinovMS.ConCash.repository;

import BlinovMS.ConCash.entity.DateCourse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface DateCourseRepository extends JpaRepository<DateCourse, Integer> {
    DateCourse findByDate(LocalDate date);
}
