package BlinovMS.ConCash.service.impl;

import BlinovMS.ConCash.entity.History;
import BlinovMS.ConCash.entity.User;
import BlinovMS.ConCash.repository.HistoryRepository;
import BlinovMS.ConCash.service.HistoruService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HistoryServiceImpl implements HistoruService {

    @Autowired
    private HistoryRepository historyRepository;

    @Override
    public List<History> getAll(User user) {
        return historyRepository.getHistoryByUser(user.getId());
    }
}
