CREATE SEQUENCE PARTY_PK_PARTY_SEQ
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;

CREATE TABLE party
(
  pk_party bigint NOT NULL DEFAULT nextval('PARTY_PK_PARTY_SEQ'::regclass),
  fk_party_parent bigint,
  name character varying(200) NOT NULL,
  created_by character varying(200) NOT NULL,
  creation_time timestamp with time zone NOT NULL DEFAULT now(),
  modified_by character varying(200) NOT NULL,
  modification_time timestamp with time zone NOT NULL DEFAULT now(),
  npwp character varying(25),
  code character varying(5),
  status integer NOT NULL DEFAULT 1,
  CONSTRAINT pk_party PRIMARY KEY (pk_party),
  CONSTRAINT p_fk_party_parent_p_pk_party FOREIGN KEY (fk_party_parent)
      REFERENCES party (pk_party) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
