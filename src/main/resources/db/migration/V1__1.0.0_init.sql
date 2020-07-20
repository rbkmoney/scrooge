CREATE SCHEMA IF NOT EXISTS scrooge;

CREATE TYPE scrooge.operation_type AS ENUM ('hold', 'commit', 'rollback');
CREATE TYPE scrooge.validation_error AS ENUM ('hold_not_exist');

CREATE TABLE IF NOT EXISTS scrooge.operation_log
(
    plan_id                CHARACTER VARYING           NOT NULL,
    batch_id               BIGINT                      NOT NULL,
    sequence_id            BIGINT                      NOT NULL,
    plan_operations_count  BIGINT                      NOT NULL,
    operation_type         scrooge.operation_type      NOT NULL,
    account_id             CHARACTER VARYING           NOT NULL,
    amount_with_sign       BIGINT                      NOT NULL,
    currency_symbolic_code CHARACTER VARYING           NOT NULL,
    description            CHARACTER VARYING           NOT NULL,
    batch_hash             BIGINT                      NOT NULL,
    creation_time          TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    validation_error       scrooge.validation_error,
    span_id                CHARACTER VARYING,
    parent_id              CHARACTER VARYING,
    trace_id               CHARACTER VARYING,
    CONSTRAINT operation_log_pkey PRIMARY KEY (plan_id, batch_id, sequence_id, plan_operations_count)
);

-- TODO [a.romanov]: add more indexes