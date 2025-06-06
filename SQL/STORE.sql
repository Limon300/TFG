CREATE TABLE usuario (
  ID INTEGER PRIMARY KEY,
  NOMBRE TEXT NOT NULL,
  APELLIDOS TEXT,
  TELEGRAM TEXT,
  DNI TEXT NOT NULL,
  STATUS TEXT NOT NULL constraint u_priv CHECK (STATUS IN ('ADM', 'USR')),
  GRUPO TEXT,
  PASSWORD TEXT NOT NULL
);

CREATE TABLE mapa (
  ID_MAPA INTEGER PRIMARY KEY,
  NOMBRE TEXT NOT NULL,
  ENERGIA_IN INTEGER NOT NULL DEFAULT 100,
  X_INICIO INTEGER NOT NULL,
  Y_INICIO INTEGER NOT NULL,
  DESCRIPCION TEXT,
  X_TAM INTEGER NOT NULL,
  Y_TAM INTEGER NOT NULL
);

CREATE TABLE casilla (
  X INTEGER,
  Y INTEGER,
  ID_MAPA INTEGER,
  Z INTEGER NOT NULL,--MEJOR PONERLO 0 < Z < 255
  COSTE INTEGER NOT NULL DEFAULT 1,
  APARIENCIA TEXT,
  constraint claves_c PRIMARY KEY (X,Y,ID_MAPA),
  constraint clave_cm foreign KEY (ID_MAPA) references mapa(ID_MAPA)
);

CREATE TABLE objetivos (
  ID_OBJ INTEGER PRIMARY KEY,
  nombre TEXT NOT NULL,
  categoria text not null,
  DESCRIPCION TEXT,
  VALOR INTEGER
);

CREATE TABLE poi (
  ID_POI INTEGER PRIMARY KEY,
  nombre TEXT NOT NULL,
  categoria text not null,
  DESCRIPCION TEXT,
  VALOR INTEGER
);

CREATE TABLE cas_obj (
  X INTEGER,
  Y INTEGER,
  ID_MAPA INTEGER,
  ID_OBJ INTEGER,
  constraint claves_cobj PRIMARY KEY (X,Y,ID_MAPA,ID_OBJ),
  constraint clave_cobj1 foreign KEY (ID_OBJ) references objetivos(ID_OBJ),
  constraint clave_cobj2 foreign KEY (X,Y,ID_MAPA) references casilla(X,Y,ID_MAPA)
);

CREATE TABLE cas_poi (
  X INTEGER,
  Y INTEGER,
  ID_MAPA INTEGER,
  ID_POI INTEGER,
  constraint claves_cpoi PRIMARY KEY (X,Y,ID_MAPA,ID_POI),
  constraint clave_cpoi1 foreign KEY (ID_POI) references poi(ID_POI),
  constraint clave_cpoi2 foreign KEY (X,Y,ID_MAPA) references casilla(X,Y,ID_MAPA)
);

CREATE TABLE agente (
  ID_AGENTE INTEGER PRIMARY KEY,
  nombre TEXT NOT NULL,
  DESCRIPCION TEXT,
  ENERGIA_MAX INTEGER NOT NULL DEFAULT 100
);

CREATE TABLE sensor (
  ID_SENSOR INTEGER PRIMARY KEY,
  nombre TEXT NOT NULL,
  DESCRIPCION TEXT,
  categoria TEXT NOT NULL,
  RADIO INTEGER DEFAULT 1
);

CREATE TABLE habilidad (
  ID_HABILIDAD INTEGER PRIMARY KEY,
  nombre TEXT NOT NULL,
  categoria TEXT NOT NULL,
  V_UP INTEGER DEFAULT 1,
  V_DOWN INTEGER DEFAULT 1,
  DESCRIPCION TEXT
);

CREATE TABLE agente_sensor (
  ID_AGENTE INTEGER,
  ID_SENSOR INTEGER,
  constraint CLAVES_AS PRIMARY KEY (ID_AGENTE,ID_SENSOR),
  constraint CLAVE_AS1 foreign KEY (ID_AGENTE) references agente(ID_AGENTE),
  constraint CLAVE_AS2 foreign KEY (ID_SENSOR) references sensor(ID_SENSOR)
);

CREATE TABLE agente_habilidad (
  ID_AGENTE INTEGER,
  ID_HABILIDAD INTEGER,
  constraint CLAVES_AH PRIMARY KEY (ID_AGENTE,ID_HABILIDAD),
  constraint CLAVE_AH1 foreign KEY (ID_AGENTE) references agente(ID_AGENTE),
  constraint CLAVE_AH2 foreign KEY (ID_HABILIDAD) references habilidad(ID_HABILIDAD)
);

INSERT INTO usuarios (id, nombre, dni, status,PASSWORD)
VALUES (1, 'Luis', '11111111A', 'adm', '33HH66');

SELECT * FROM usuario;
SHOW TABLES;
