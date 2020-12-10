package BlinovMS.ConCash.service;

import BlinovMS.ConCash.entity.History;
import BlinovMS.ConCash.entity.User;

import java.util.List;

public interface HistoruService {
    List<History> getAll (User user);
}
