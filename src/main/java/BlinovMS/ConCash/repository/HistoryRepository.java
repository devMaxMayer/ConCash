package BlinovMS.ConCash.repository;

import BlinovMS.ConCash.entity.Currency;
import BlinovMS.ConCash.entity.History;
import BlinovMS.ConCash.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface HistoryRepository extends JpaRepository<History, Integer> {
    //History findByUser (User user);

    @Query(value = "select distinct cr " +
            "from History cr " +
            "join fetch cr.user " +
            "join cr.user u2 " +
            "where u2.id = :currentId "
    )
    List<History> getHistoryByUser(@Param("currentId") Integer id);
}
