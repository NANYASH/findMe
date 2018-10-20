CREATE TABLE USER(
  ID         NUMBER PRIMARY KEY,
  FIRST_NAME               VARCHAR(50) NOT NULL,
  LAST_NAME                VARCHAR(50) NOT NULL,
  PHONE                    VARCHAR(50),
  COUNTRY                  VARCHAR(50),
  CITY                     VARCHAR(50),
  AGE                      NUMBER,
  DATE_REGISTERED          DATE NOT NULL,
  DATE_LAST_ACTIVE         DATE NOT NULL,
  RELATIONSHIPS_STATUS     VARCHAR(50),
  RELIGION                 VARCHAR(50),
  SCHOOL                   VARCHAR(50),
  UNIVERSITY               VARCHAR(50)
);