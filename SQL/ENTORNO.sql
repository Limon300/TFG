create table sesion (
  id_sesion BIGINT primary key,
  id_mapa BIGINT not null,
  t_inicio timestamp(6) not null,
  t_espera BIGINT not null,
  t_ciclo BIGINT not null,--t_ciclo en microsegundos
  t_ultimo timestamp(6) not null
);

create table usuarios (
  id_usuario BIGINT primary key,
  jwt VARCHAR(255),
  jwt_estado VARCHAR(255)
);

create table sesion_usuarios (
  id_sesion BIGINT,
  id_usuario BIGINT,
  constraint claves_su primary key (id_sesion,id_usuario),
  constraint clave_sus foreign key (id_sesion) references sesion(id_sesion),
  constraint clave_suu foreign key (id_usuario) references usuarios(id_usuario)
);

create table logs (
  id_entrada BIGINT,
  id_sesion BIGINT,
  fecha timestamp(6) not null,
  entrada VARCHAR(255),
  evento VARCHAR(255) not null,
  id_sujeto BIGINT not null,--huh estos tres si deberian ser null
  val_1 BIGINT not null,
  val_2 BIGINT noy null,
  constraint claves_l primary key (id_entrada),
  constraint clave_ls foreign key (id_sesion) references sesion(id_sesion)
);

create table poi (
  id_poi BIGINT,
  id_sesion BIGINT,
  x BIGINT not null,
  y BIGINT not null,
  id_poi_store BIGINT not null,
  constraint claves_p primary key (id_poi),
  constraint clave_ps foreign key (id_sesion) references sesion(id_sesion)
);

create table agentes (
  id_agente BIGINT,
  id_sesion BIGINT,
  id_usuario BIGINT,
  id_agente_store BIGINT not null,
  t_ultimo timestamp(6),
  x BIGINT not null,
  y BIGINT not null,
  energia BIGINT not null,
  constraint claves_a primary key (id_agente),
  constraint clave_as foreign key (id_sesion) references sesion(id_sesion),
  constraint clave_au foreign key (id_usuario) references usuarios(id_usuario)
);

create table sensor (
  id_sensor BIGINT,
  id_agente BIGINT,
  id_sensor_store BIGINT not null,
  valor BIGINT,
  constraint claves_s primary key (id_sensor),
  constraint clave_sa foreign key (id_agente) references agentes(id_agente)
);

create table objetivos (
  id_sesion BIGINT,
  id_objetivo BIGINT,
  estatus VARCHAR(255),
  compartido BIGINT,
  id_objetivo_store BIGINT,
  x BIGINT not null,
  y BIGINT not null,
  constraint claves_o primary key (id_objetivo),
  constraint clave_os foreign key (id_sesion) references sesion(id_sesion)
);

create table actual (
  id_sesion BIGINT,
  id_objetivo BIGINT,
  constraint claves_ac primary key (id_sesion),
  constraint clave_aco foreign key (id_objetivo) references objetivos(id_objetivo),
  constraint clave_acs foreign key (id_sesion) references sesion(id_sesion)
);

create table alcanzado (
  id_agente BIGINT,
  id_objetivo BIGINT,
  constraint claves_alc primary key (id_objetivo,id_agente),
  constraint clave_alco foreign key (id_objetivo) references objetivos(id_objetivo),
  constraint clave_alca foreign key (id_agente) references agentes(id_agente)
);

show tables;
