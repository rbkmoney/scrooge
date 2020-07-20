package com.rbkmoney.scrooge.handler;

import com.rbkmoney.damsel.shumaich.OperationLog;
import com.rbkmoney.scrooge.entity.OperationLogEntity;
import com.rbkmoney.scrooge.mapper.OperationLogMapper;
import com.rbkmoney.scrooge.repository.OperationLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OperationLogHandler {

    private final OperationLogMapper mapper;
    private final OperationLogRepository repository;

    public void handle(List<OperationLog> batch) {
        if (CollectionUtils.isEmpty(batch)) return;

        List<OperationLogEntity> entities = mapper.fromThriftToEntity(batch);
        repository.saveAll(entities);
    }
}
