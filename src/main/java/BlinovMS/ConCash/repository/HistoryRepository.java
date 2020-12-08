package BlinovMS.ConCash.repository;

import BlinovMS.ConCash.entity.History;
import BlinovMS.ConCash.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoryRepository extends JpaRepository<History, Integer> {
    History findByUser (User user);
}
