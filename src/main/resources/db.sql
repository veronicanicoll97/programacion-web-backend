
CREATE TABLE vencimientos_puntos (
                                     id_vencimiento SERIAL NOT NULL,
                                     fecha_inicio DATE NOT NULL,
                                     fecha_fin DATE NOT NULL,
                                     dias_duracion INTEGER NOT NULL,
                                     CONSTRAINT vencimientos_puntos_pk PRIMARY KEY (id_vencimiento)
);


CREATE TABLE reglas_asignaciones_puntos (
                                            id_asignacion SERIAL NOT NULL,
                                            limite_inferior FLOAT NOT NULL,
                                            limite_superior FLOAT NOT NULL,
                                            monto_equivalencia_punto INTEGER DEFAULT 1 NOT NULL,
                                            CONSTRAINT reglas_asignaciones_puntos_pk PRIMARY KEY (id_asignacion)
);


CREATE TABLE conceptos_usos_puntos (
                                       id_concepto SERIAL NOT NULL,
                                       descripcion VARCHAR NOT NULL,
                                       puntos_requeridos INTEGER NOT NULL,
                                       CONSTRAINT conceptos_usos_puntos_pk PRIMARY KEY (id_concepto)
);


CREATE TABLE contactos (
                           id_contacto SERIAL NOT NULL,
                           tipo_contacto VARCHAR NOT NULL,
                           valor VARCHAR NOT NULL,
                           CONSTRAINT contactos_pk PRIMARY KEY (id_contacto)
);


CREATE TABLE pais (
                      id_pais SERIAL NOT NULL,
                      descripcion VARCHAR NOT NULL,
                      nacionalidad VARCHAR NOT NULL,
                      CONSTRAINT pais_pk PRIMARY KEY (id_pais)
);


CREATE TABLE tipos_documentos (
                                  id_tipo_documento INTEGER NOT NULL,
                                  descripcion VARCHAR NOT NULL,
                                  CONSTRAINT tipos_documentos_pk PRIMARY KEY (id_tipo_documento)
);


CREATE TABLE clientes (
                          id_cliente SERIAL NOT NULL,
                          nombre VARCHAR NOT NULL,
                          apellido VARCHAR NOT NULL,
                          nro_documento VARCHAR NOT NULL,
                          id_tipo_documento INTEGER NOT NULL,
                          id_pais INTEGER NOT NULL,
                          fecha_nacimiento DATE NOT NULL,
                          id_contacto INTEGER NOT NULL,
                          CONSTRAINT clientes_pk PRIMARY KEY (id_cliente)
);


CREATE TABLE cabeceras (
                           id_cabecera SERIAL NOT NULL,
                           id_cliente INTEGER NOT NULL,
                           puntaje_utilizado INTEGER DEFAULT 0 NOT NULL,
                           fecha_uso DATE NOT NULL,
                           id_concepto INTEGER NOT NULL,
                           CONSTRAINT cabeceras_pk PRIMARY KEY (id_cabecera)
);


CREATE TABLE bolsas_puntos (
                               id_bolsa SERIAL NOT NULL,
                               id_cliente INTEGER NOT NULL,
                               fecha_asignacion_punto DATE NOT NULL,
                               fecha_caducidad_punto DATE NOT NULL,
                               puntaje_asignado INTEGER DEFAULT 0 NOT NULL,
                               puntaje_utilizado INTEGER DEFAULT 0 NOT NULL,
                               saldo_puntos INTEGER DEFAULT 0 NOT NULL,
                               monto_operacion FLOAT DEFAULT 0 NOT NULL,
                               CONSTRAINT bolsas_puntos_pk PRIMARY KEY (id_bolsa)
);


CREATE TABLE detalles (
                          id_detalle SERIAL NOT NULL,
                          id_cabecera INTEGER NOT NULL,
                          puntaje_utilizado INTEGER DEFAULT 0 NOT NULL,
                          id_bolsa INTEGER NOT NULL,
                          CONSTRAINT detalles_pk PRIMARY KEY (id_detalle)
);


ALTER TABLE cabeceras ADD CONSTRAINT conceptos_usos_puntos_cabeceras_fk
    FOREIGN KEY (id_concepto)
        REFERENCES conceptos_usos_puntos (id_concepto)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION
    NOT DEFERRABLE;

ALTER TABLE clientes ADD CONSTRAINT contactos_clientes_fk
    FOREIGN KEY (id_contacto)
        REFERENCES contactos (id_contacto)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION
    NOT DEFERRABLE;

ALTER TABLE clientes ADD CONSTRAINT pais_clientes_fk
    FOREIGN KEY (id_pais)
        REFERENCES pais (id_pais)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION
    NOT DEFERRABLE;

ALTER TABLE clientes ADD CONSTRAINT tipos_documentos_clientes_fk
    FOREIGN KEY (id_tipo_documento)
        REFERENCES tipos_documentos (id_tipo_documento)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION
    NOT DEFERRABLE;

ALTER TABLE bolsas_puntos ADD CONSTRAINT clientes_bolsas_puntos_fk
    FOREIGN KEY (id_cliente)
        REFERENCES clientes (id_cliente)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION
    NOT DEFERRABLE;

ALTER TABLE cabeceras ADD CONSTRAINT clientes_cabeceras_fk
    FOREIGN KEY (id_cliente)
        REFERENCES clientes (id_cliente)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION
    NOT DEFERRABLE;

ALTER TABLE detalles ADD CONSTRAINT cabeceras_detalles_fk
    FOREIGN KEY (id_cabecera)
        REFERENCES cabeceras (id_cabecera)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION
    NOT DEFERRABLE;

ALTER TABLE detalles ADD CONSTRAINT bolsas_puntos_detalles_fk
    FOREIGN KEY (id_bolsa)
        REFERENCES bolsas_puntos (id_bolsa)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION
    NOT DEFERRABLE;