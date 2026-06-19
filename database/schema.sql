CREATE DATABASE IF NOT EXISTS money_transfer_system;

USE money_transfer_system;


CREATE TABLE IF NOT EXISTS account (
  id            BIGINT NOT NULL AUTO_INCREMENT,
  balance       DECIMAL(18,2) NOT NULL,
  holder_name   VARCHAR(255) NOT NULL,
  last_updated  DATETIME(6) NOT NULL,
  status        SMALLINT NOT NULL,
  version       INT NOT NULL DEFAULT 0,
  PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS transaction_log (
  id               VARCHAR(36)   NOT NULL,
  amount           DECIMAL(18,2) NOT NULL,
  created_on       DATETIME(6)   NOT NULL,
  failure_reason   VARCHAR(255)  NULL,
  from_account_id  BIGINT        NOT NULL,
  idempotency_key  VARCHAR(100)  NOT NULL,
  status           SMALLINT      NOT NULL,
  to_account_id    BIGINT        NOT NULL,

  PRIMARY KEY (id),
  UNIQUE KEY uq_idempotency_key (idempotency_key),
  KEY idx_from_account (from_account_id),
  KEY idx_to_account (to_account_id)
);


CREATE TABLE IF NOT EXISTS reward_account (
  account_id     BIGINT NOT NULL,
  points_balance BIGINT NOT NULL DEFAULT 0,
  last_updated   DATETIME(6) NOT NULL,
  version        INT NOT NULL DEFAULT 0,
  PRIMARY KEY (account_id),
  CONSTRAINT fk_reward_account FOREIGN KEY (account_id) REFERENCES account(id)
);