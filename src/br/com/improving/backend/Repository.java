package br.com.improving.backend;

import java.math.BigDecimal;

public interface Repository {

    Long generateFileId(String fileName);

    void reportInvalidLine(Long fileId, int lineNumber, Field field, String message);

    Long getClientId(String description);

    void save(Long fileId, Long lineNumber, Long clientId, BigDecimal value);
}
