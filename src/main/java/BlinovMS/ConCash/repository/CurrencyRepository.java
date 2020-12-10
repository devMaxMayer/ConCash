package BlinovMS.ConCash.repository;

import BlinovMS.ConCash.entity.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface CurrencyRepository extends JpaRepository<Currency, Integer> {
    Currency findByName(String name);

    @Query(value = "select distinct cr " +
            "from Currency cr " +
            "join fetch cr.date " +
            "join cr.date u2 " +
            "where u2.date = :currentDate "
    )
    List<Currency> getCur(@Param("currentDate") LocalDate date);
}
