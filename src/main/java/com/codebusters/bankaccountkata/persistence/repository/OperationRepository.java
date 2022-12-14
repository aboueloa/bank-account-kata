package com.codebusters.bankaccountkata.persistence.repository;

import com.codebusters.bankaccountkata.domain.model.Operation;
import com.codebusters.bankaccountkata.domain.model.OperationType;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class OperationRepository {
    private static final Map<String, List<Operation>> HISTORY = new HashMap<>();

    public OperationRepository() {
        HISTORY.put("ayman.aboueloula", new ArrayList<>());
    }
    public void save(String clientId, Operation operation) {
        var operations = HISTORY.getOrDefault(clientId, new ArrayList<>());
        operations.add(operation);
        HISTORY.put(clientId, operations);
    }

    public boolean contains(String clientId) {
        return HISTORY.containsKey(clientId);
    }

    public int computeBalance(String clientId) {
        return HISTORY.get(clientId).stream().reduce(0, (partialBalance, operation) -> partialBalance + operation.getAmount(), Integer::sum);
    }

    public List<Operation> getOperation(String clientId) {
        return HISTORY.get(clientId);
    }
}
