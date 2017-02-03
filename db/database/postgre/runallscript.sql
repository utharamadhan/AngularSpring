-- start running script

-- DDL init script
\i DDL/LOOKUP.sql;
\i DDL/LOOKUP_GROUP.sql;
\i DDL/LOOKUP_ADDRESS.sql;
\i DDL/LOOKUP_ADDRESS_GROUP.sql;

\i DDL/APP_PARAMETER.sql;
\i DDL/APP_ROLE.sql;
\i DDL/APP_FUNCTION.sql;
\i DDL/APP_ROLE_FUNCTION.sql;
\i DDL/PARTY.sql;
\i DDL/PARTY_ADDRESS.sql;
\i DDL/PARTY_CONTACT.sql;
\i DDL/PARTY_ROLE.sql;
\i DDL/APP_USER.sql;
\i DDL/APP_USER_ACTIVITY.sql;
\i DDL/APP_USER_PASS_HIST.sql;
\i DDL/APP_USER_ROLE.sql;
\i DDL/RUNTIME_USER_LOGIN.sql;

\i DDL/QUARTZ.sql;

-- DML init script
\i DML/LOOKUP.sql;
\i DML/APP_USER.sql;
\i DML/APP_FUNCTION.sql;
\i DML/APP_ROLE.sql;
\i DML/APP_ROLE_FUNCTION.sql;
\i DML/APP_USER_ROLE.sql;

-- DDL script
\i DDL/SIMPLE_OBJECT.sql;