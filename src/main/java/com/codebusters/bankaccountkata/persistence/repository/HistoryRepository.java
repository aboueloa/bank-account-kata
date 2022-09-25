package com.codebusters.bankaccountkata.persistence.repository;

import com.codebusters.bankaccountkata.domain.model.History;
import com.codebusters.bankaccountkata.domain.model.Transaction;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class HistoryRepository {
    public static List<History> HISTORY = new ArrayList<>();

    public void save(History history) {
        HISTORY.add(history);
    }
}
