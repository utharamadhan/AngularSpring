CREATE SEQUENCE APP_FUNCTION_PK_APP_FUNCTION_SEQ
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;

CREATE TABLE APP_FUNCTION
(
  pk_app_function bigint NOT NULL DEFAULT nextval('APP_FUNCTION_PK_APP_FUNCTION_SEQ'::regclass),
  fk_app_function_parent bigint,
  name character varying(100) NOT NULL,
  descr character varying(200) NOT NULL,
  access_page character varying(200),
  is_active boolean DEFAULT false,
  user_type integer,
  order_no integer NOT NULL DEFAULT 0,
  CONSTRAINT pk_app_function PRIMARY KEY (pk_app_function),
  CONSTRAINT af_fk_app_function_parent_af_pk_app_function FOREIGN KEY (fk_app_function_parent)
      REFERENCES app_function (pk_app_function) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);