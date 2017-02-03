CREATE SEQUENCE LOOKUP_ADDRESS_PK_LOOKUP_ADDRESS_SEQ
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
  
CREATE TABLE LOOKUP_ADDRESS
(
  PK_LOOKUP_ADDRESS bigint NOT NULL DEFAULT nextval('LOOKUP_ADDRESS_PK_LOOKUP_ADDRESS_SEQ'::regclass),
  address_group character varying(50) NOT NULL,
  address_name character varying(100) NOT NULL,
  created_by character varying(200) NOT NULL,
  creation_time timestamp with time zone NOT NULL DEFAULT now(),
  modified_by character varying(200),
  modification_time timestamp with time zone,
  order_no bigint,
  status integer NOT NULL DEFAULT 1,
  CONSTRAINT PK_LOOKUP_ADDRESS PRIMARY KEY (PK_LOOKUP_ADDRESS)
)
WITH (
  OIDS=FALSE
);
