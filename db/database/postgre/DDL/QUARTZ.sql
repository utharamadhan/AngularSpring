CREATE TABLE qrtz_blob_triggers
(
  sched_name character varying(120),
  TRIGGER_NAME character varying(200),
  TRIGGER_GROUP character varying(200),
  BLOB_DATA bytea
)
WITH (
  OIDS=FALSE
);

CREATE TABLE qrtz_calendars
(
  sched_name character varying(120),
  calendar_name character varying(200),
  calendar bytea
)
WITH (
  OIDS=FALSE
);

CREATE TABLE qrtz_cron_triggers
(
  sched_name character varying(120),
  trigger_name character varying(200),
  trigger_group character varying(200),
  cron_expression character varying(120),
  time_zone_id character varying(80)
)
WITH (
  OIDS=FALSE
);

CREATE TABLE qrtz_fired_triggers
(
  sched_name character varying(120),
  entry_id character varying(95),
  trigger_name character varying(200),
  trigger_group character varying(200),
  instance_name character varying(200),
  fired_time bigint,
  sched_time bigint,
  priority bigint,
  state character varying(16),
  job_name character varying(200),
  job_group character varying(200),
  is_nonconcurrent character varying(10),
  requests_recovery character varying(10)
)
WITH (
  OIDS=FALSE
);

CREATE TABLE qrtz_job_details
(
  sched_name character varying(120),
  job_name character varying(200),
  job_group character varying(200),
  description character varying(250),
  job_class_name character varying(250),
  is_durable character varying(10),
  is_nonconcurrent character varying(10),
  is_update_data character varying(10),
  requests_recovery character varying(10),
  job_data bytea
)
WITH (
  OIDS=FALSE
);

CREATE TABLE qrtz_locks
(
  sched_name character varying(120),
  lock_name character varying(40)
)
WITH (
  OIDS=FALSE
);

CREATE TABLE qrtz_paused_trigger_grps
(
  sched_name character varying(120),
  trigger_group character varying(200)
)
WITH (
  OIDS=FALSE
);

CREATE TABLE qrtz_scheduler_state
(
  sched_name character varying(120),
  instance_name character varying(200),
  last_checkin_time bigint,
  checkin_interval bigint
)
WITH (
  OIDS=FALSE
);

CREATE TABLE qrtz_simple_triggers
(
  sched_name character varying(120),
  trigger_name character varying(200),
  trigger_group character varying(200),
  repeat_count bigint,
  repeat_interval bigint,
  times_triggered bigint
)
WITH (
  OIDS=FALSE
);

CREATE TABLE qrtz_simprop_triggers
(
  sched_name character varying(120),
  trigger_name character varying(200),
  trigger_group character varying(200),
  str_prop_1 character varying(512),
  str_prop_2 character varying(512),
  str_prop_3 character varying(512),
  int_prop_1 bigint,
  int_prop_2 bigint,
  long_prop_1 bigint,
  long_prop_2 bigint,
  dec_prop_1 bigint,
  dec_prop_2 bigint,
  bool_prop_1 character varying(20),
  bool_prop_2 character varying(20)
)
WITH (
  OIDS=FALSE
);

CREATE TABLE qrtz_triggers
(
  sched_name character varying(120),
  trigger_name character varying(200),
  trigger_group character varying(200),
  job_name character varying(200),
  job_group character varying(200),
  description character varying(250),
  next_fire_time bigint,
  prev_fire_time bigint,
  priority bigint,
  trigger_state character varying(16),
  trigger_type character varying(8),
  start_time bigint,
  end_time bigint,
  calendar_name character varying(200),
  misfire_instr bigint,
  job_data bytea
)
WITH (
  OIDS=FALSE
);



