/* PROFILE */
INSERT INTO profile (id, name, surname, email, password)
VALUES ('CBWD-2675', 'Nancy', 'Jones', 'sextence0@fc2.com', 'XGf92taFPEhg');


/* ROOM */
INSERT INTO room(id, name)
VALUES ('XUZC-2250', 'Hall');

INSERT INTO room(id, name)
VALUES ('TRHE-4606', 'Bathroom');

INSERT INTO room(id, name)
VALUES ('DPVC-4769', 'Bedroom3');

INSERT INTO room(id, name)
VALUES ('UAAT-5485', 'Living');

INSERT INTO room(name)
VALUES ('Bedroom4');

INSERT INTO room(id, name)
VALUES ('QXFG-5409', 'Bathroom2');

/* DEVICE */
INSERT INTO device(id, name, status, room_id)
VALUES ('PCRM-6254', 'Vacuum', 'true', 'DPVC-4769');

INSERT INTO device(id, name, status, room_id)
VALUES ('JGLZ-6650', 'Television', 'true', 'ZUQJ-9281');

INSERT INTO device(id, name, status, room_id)
VALUES ('AMYJ-6823', 'Vacuum', 'true', 'ZUQJ-9281');

INSERT INTO device(id, name, status, room_id)
VALUES ('RMVZ-0994', 'Light bulb', 'true', 'TRHE-4606');

INSERT INTO device(id, name, status, room_id)
VALUES ('YZQS-8865', 'Conditioner', 'false', 'QXFG-5409');

INSERT INTO device(id, name, status, room_id)
VALUES ('SXQT-1887', 'Light bulb', 'false', 'ZUQJ-9281');

INSERT INTO device(id, name, status, room_id)
VALUES ('JPDV-0784', 'Television', 'false', 'XUZC-2250');

INSERT INTO device(id, name, status, room_id)
VALUES ('FIIE-9288', 'Vacuum', 'true', 'TRHE-4606');

INSERT INTO device(id, name, status, room_id)
VALUES ('NWKI-0073', 'Vacuum', 'false', 'UAAT-5485');

INSERT INTO device(id, name, status, room_id)
VALUES ('RSML-7282', 'Conditioner', 'false', 'UAAT-5485');

INSERT INTO device(id, name, status, room_id)
VALUES ('XCYP-9524', 'Alarm clock', 'false', 'QXFG-5409');

INSERT INTO device(id, name, status, room_id)
VALUES ('BZRQ-5070', 'Conditioner', 'true', 'QXFG-5409');

INSERT INTO device(id, name, status, room_id)
VALUES ('JRRT-8956', 'Alarm clock', 'false', 'DPVC-4769');

INSERT INTO device(id, name, status, room_id)
VALUES ('NQHW-1220', 'Alarm clock', 'true', 'UAAT-5485');

INSERT INTO device(id, name, status, room_id)
VALUES ('LVAL-1828', 'Vacuum', 'true', 'QXFG-5409');

INSERT INTO device(id, name, status, room_id)
VALUES ('XYQW-2381', 'Thermostat', 'true', 'QXFG-5409');

INSERT INTO device(id, name, status, room_id)
VALUES ('FVNP-2689', 'Vacuum', 'false', 'ZUQJ-9281');

INSERT INTO device(id, name, status, room_id)
VALUES ('EUOB-7427', 'Conditioner', 'false', 'TRHE-4606');

INSERT INTO device(id, name, status, room_id)
VALUES ('OWOD-0897', 'Conditioner', 'false', 'DPVC-4769');

INSERT INTO device(id, name, status, room_id)
VALUES ('KBNF-3574', 'Thermostat', 'false', 'QXFG-5409');


/* SCENE */
INSERT INTO scene(id, name, status)
VALUES ('XIHU-2692', 'Start', 'true');

INSERT INTO scene(id, name, status)
VALUES ('MMNE-7975', 'Turn off', 'true');

INSERT INTO scene(id, name, status)
VALUES ('IIHE-1415', 'Turn on', 'true');

INSERT INTO scene(id, name, status)
VALUES ('GOKU-2935', 'Set threshold', 'true');

INSERT INTO scene(id, name, status)
VALUES ('TSID-1525', 'Reminder', 'true');

INSERT INTO scene(id, name, status)
VALUES ('HTSK-5959', 'Start', 'true');

INSERT INTO scene(id, name, status)
VALUES ('KIQZ-9966', 'Turn on', 'true');

INSERT INTO scene(id, name, status)
VALUES ('DSUO-3931', 'Turn on', 'false');

INSERT INTO scene(id, name, status)
VALUES ('VESM-7090', 'Start', 'false');

INSERT INTO scene(id, name, status)
VALUES ('VBDQ-6833', 'Wake Up', 'true');

/* CONDITIONS */
INSERT INTO conditions(device_id, scene_id, activation_date, period, threshold)
VALUES ('PCRM-6254', 'XIHU-2692', NULL, NULL, 74);

INSERT INTO conditions(device_id, scene_id, activation_date, period, threshold)
VALUES ('JGLZ-6650', 'XIHU-2692', NULL, NULL, 23);

INSERT INTO conditions(device_id, scene_id, activation_date, period, threshold)
VALUES ('AMYJ-6823', 'XIHU-2692', '22-Jan-2021', 'Workdays', 54);

INSERT INTO conditions(device_id, scene_id, activation_date, period, threshold)
VALUES ('RMVZ-0994', 'MMNE-7975', '20-Oct-2020', 'Daily', 52);

INSERT INTO conditions(device_id, scene_id, activation_date, period, threshold)
VALUES ('YZQS-8865', 'MMNE-7975', '05-Dec-2020', 'Weekends', 56);

INSERT INTO conditions(device_id, scene_id, activation_date, period, threshold)
VALUES ('SXQT-1887', 'HTSK-5959', NULL, NULL, 73);

INSERT INTO conditions(device_id, scene_id, activation_date, period, threshold)
VALUES ('JPDV-0784', 'KIQZ-9966', NULL, NULL, 20);

INSERT INTO conditions(device_id, scene_id, activation_date, period, threshold)
VALUES ('FIIE-9288', 'DSUO-3931', '31-Jul-2021', 'Weekends', 91);

INSERT INTO conditions(device_id, scene_id, activation_date, period, threshold)
VALUES ('NWKI-0073', 'VESM-7090', '12-Aug-2021', 'Workdays', 11);

INSERT INTO conditions(device_id, scene_id, activation_date, period, threshold)
VALUES ('RSML-7282', 'VBDQ-6833', NULL, NULL, 86);

INSERT INTO conditions(device_id, scene_id, activation_date, period, threshold)
VALUES ('XCYP-9524', 'XIHU-2692', NULL, NULL, 14);

INSERT INTO conditions(device_id, scene_id, activation_date, period, threshold)
VALUES ('BZRQ-5070', 'MMNE-7975', '21-Mar-2021', 'Workdays', 15);

INSERT INTO conditions(device_id, scene_id, activation_date, period, threshold)
VALUES ('JRRT-8956', 'IIHE-1415', NULL, NULL, 56);

INSERT INTO conditions(device_id, scene_id, activation_date, period, threshold)
VALUES ('NQHW-1220', 'GOKU-2935', NULL, NULL, 7);

INSERT INTO conditions(device_id, scene_id, activation_date, period, threshold)
VALUES ('LVAL-1828', 'VESM-7090', NULL, NULL, 23);

INSERT INTO conditions(device_id, scene_id, activation_date, period, threshold)
VALUES ('XYQW-2381', 'HTSK-5959', NULL, NULL, 46);

INSERT INTO conditions(device_id, scene_id, activation_date, period, threshold)
VALUES ('FVNP-2689', 'KIQZ-9966', NULL, NULL, 4);

INSERT INTO conditions(device_id, scene_id, activation_date, period, threshold)
VALUES ('EUOB-7427', 'DSUO-3931', NULL, NULL, 65);

INSERT INTO conditions(device_id, scene_id, activation_date, period, threshold)
VALUES ('OWOD-0897', 'VESM-7090', '21-Apr-2021', 'Daily', 20);

INSERT INTO conditions(device_id, scene_id, activation_date, period, threshold)
VALUES ('KBNF-3574', 'VBDQ-6833', '19-May-2021', 'Weekends', 23);