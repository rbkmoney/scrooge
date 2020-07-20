package com.rbkmoney.scrooge.repository;

import com.rbkmoney.scrooge.entity.OperationLogEntity;
import com.rbkmoney.scrooge.entity.OperationLogPk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OperationLogRepository extends JpaRepository<OperationLogEntity, OperationLogPk> {
}
