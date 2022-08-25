CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE public.airp_airplane
(
    airp_uuid_airplane  uuid DEFAULT    uuid_generate_v4(),
    airp_tx_code        varchar     NOT NULL,
    airp_tx_model       varchar     NOT NULL,
    airp_qt_passenger   int4        NOT NULL,
    airp_dt_register    timestamp   NOT NULL,
    CONSTRAINT airp_airplane_pkey PRIMARY KEY (airp_uuid_airplane)
);

CREATE UNIQUE INDEX ix_un_airp_tx_code ON public.airp_airplane USING btree (airp_tx_code);

