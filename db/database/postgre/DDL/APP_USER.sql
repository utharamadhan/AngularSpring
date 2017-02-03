CREATE SEQUENCE APP_USER_PK_APP_USER_SEQ
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;

CREATE TABLE app_user
(
  pk_app_user bigint NOT NULL DEFAULT nextval('APP_USER_PK_APP_USER_SEQ'::regclass),
  fk_party bigint NOT NULL,
  is_super_user boolean NOT NULL DEFAULT false,
  user_type integer NOT NULL,
  user_name character varying(200) NOT NULL,
  email character varying(200) NOT NULL,
  password character varying(255) NOT NULL,
  random_key character varying(255),
  status integer,
  login_failed integer NOT NULL DEFAULT 0,
  last_action character varying(100),
  last_login_access character varying(100),
  last_login_device character varying(100),
  last_login_date time with time zone,
  is_lock boolean,
  created_by character varying(200) NOT NULL,
  creation_time timestamp with time zone NOT NULL DEFAULT now(),
  modified_by character varying(200) NOT NULL,
  modification_time timestamp with time zone NOT NULL DEFAULT now(),
  activation_code character varying(50),
  initial_wizard_step integer,
  activation_method integer,
  CONSTRAINT pk_app_user PRIMARY KEY (pk_app_user),
  CONSTRAINT ap_fk_party_p_pk_party FOREIGN KEY (fk_party)
      REFERENCES party (pk_party) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT app_user_unique_email UNIQUE (email)
)
WITH (
  OIDS=FALSE
);
