package com.rbkmoney.scrooge.kafka.listener;

import com.rbkmoney.damsel.shumaich.OperationLog;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OperationLogListener {

    @KafkaListener(
            autoStartup = "${kafka.topic.operation-log.listener.enabled}",
            topics = "${kafka.topic.operation-log.name}",
            containerFactory = "operationLogListenerContainerFactory")
    public void listen(
            List<OperationLog> batch,
            @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition,
            @Header(KafkaHeaders.OFFSET) int offset,
            Acknowledgment ack) {
        log.info("Listening OperationLog: partition={}, offset={}, batch.size()={}", partition, offset, batch.size());
//        handleMessages(batch);
        ack.acknowledge();
    }
}
