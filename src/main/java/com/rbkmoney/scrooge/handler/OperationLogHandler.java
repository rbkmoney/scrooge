package com.rbkmoney.scrooge.handler;

import com.rbkmoney.damsel.shumaich.OperationLog;
import com.rbkmoney.scrooge.entity.OperationLogEntity;
import com.rbkmoney.scrooge.mapper.OperationLogMapper;
import com.rbkmoney.scrooge.repository.OperationLogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OperationLogHandler {

    private final OperationLogMapper mapper;
    private final OperationLogRepository repository;

    public void handle(List<OperationLog> batch) {
        if (CollectionUtils.isEmpty(batch)) return;

        List<OperationLogEntity> entities = mapper.fromThriftToEntity(batch);

        log.info("Inserting {} OperationLog entities to the database", entities.size());
        repository.saveAll(entities);
    }
}
