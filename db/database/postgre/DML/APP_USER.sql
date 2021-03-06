INSERT INTO PARTY(NAME, CREATED_BY, CREATION_TIME, MODIFIED_BY, 
            MODIFICATION_TIME, NPWP, CODE, STATUS)
    VALUES ('ADMIN', 'SYSTEM', NOW(), 'SYSTEM', 
            NOW(), null, 'ADMIN', 1);

INSERT INTO APP_USER(FK_PARTY, IS_SUPER_USER, USER_TYPE, USER_NAME, EMAIL, 
            PASSWORD, RANDOM_KEY, STATUS, LOGIN_FAILED, LAST_ACTION, LAST_LOGIN_ACCESS, 
            LAST_LOGIN_DEVICE, LAST_LOGIN_DATE, IS_LOCK, CREATED_BY, CREATION_TIME, 
            MODIFIED_BY, MODIFICATION_TIME, ACTIVATION_CODE, INITIAL_WIZARD_STEP, 
            ACTIVATION_METHOD)
    VALUES ((SELECT PK_PARTY FROM PARTY WHERE CODE = 'ADMIN'), false, 1, 'ADMIN', 'admin@admin.com', 
	'$2a$10$/ozUTpyv/IqQJrYWomZvL.MaLL97ETOqs8S0xWMY6Ojlfp7iLd6Ue', null, 1, 0, null, null, 
	    null, now(), false, 'SYSTEM', now(), 
            'SYSTEM', now(), null, null, 1);
