CREATE SEQUENCE APP_ROLE_FUNCTION_PK_APP_ROLE_FUNCTION_SEQ
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
  
CREATE TABLE app_role_function
(
  pk_app_role_function bigint NOT NULL DEFAULT nextval('APP_ROLE_FUNCTION_PK_APP_ROLE_FUNCTION_SEQ'::regclass),
  fk_app_role bigint NOT NULL,
  fk_app_function bigint NOT NULL,
  CONSTRAINT pk_app_role_function PRIMARY KEY (pk_app_role_function),
  CONSTRAINT arf_fk_app_function_af_pk_app_function FOREIGN KEY (fk_app_function)
      REFERENCES app_function (pk_app_function) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT arf_fk_app_role_ap_pk_app_role FOREIGN KEY (fk_app_role)
      REFERENCES app_role (pk_app_role) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
