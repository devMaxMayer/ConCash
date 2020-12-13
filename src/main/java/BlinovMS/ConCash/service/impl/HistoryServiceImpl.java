package BlinovMS.ConCash.service.impl;

import BlinovMS.ConCash.entity.History;
import BlinovMS.ConCash.entity.User;
import BlinovMS.ConCash.repository.HistoryRepository;
import BlinovMS.ConCash.service.HistoruService;
import BlinovMS.ConCash.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HistoryServiceImpl implements HistoruService {

    @Autowired
    private HistoryRepository historyRepository;
    @Autowired
    private UserService userService;

    @Override
    public List<History> getAll(String userName) {
        User user = userService.findByLogin(userName);
        return historyRepository.getHistoryByUser(user.getId());
    }
}
