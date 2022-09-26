package com.codebusters.bankaccountkata.persistence.repository;

import com.codebusters.bankaccountkata.domain.model.Operation;
import com.codebusters.bankaccountkata.domain.model.OperationType;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class HistoryRepository {
    private static final Map<String, List<Operation>> HISTORY = new HashMap<>();

    public void save(String clientId, Operation operation) {
        var operations = HISTORY.getOrDefault(clientId, new ArrayList<>());
        operations.add(operation);
        HISTORY.put(clientId, operations);
    }
}
