CREATE TABLE EXCHANGE_RATES (
    ID SERIAL,
    SOURCE_CURRENCY VARCHAR(3) NOT NULL,
    TARGET_CURRENCY VARCHAR(3) NOT NULL,
    EXCHANGE_RATE NUMERIC(10, 4) NOT NULL,
    EFFECTIVE_START_DATE DATE NOT NULL,
    CONSTRAINT pk_exchange_rates PRIMARY KEY (ID),
    CONSTRAINT unique_currency_pair_date UNIQUE (SOURCE_CURRENCY, TARGET_CURRENCY, EFFECTIVE_START_DATE)
);