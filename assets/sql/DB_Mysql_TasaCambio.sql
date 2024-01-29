

/**Ejectuar con usuario  root/desa  */
CREATE DATABASE dbtasas;

SHOW DATABASES;

USE dbtasas;

CREATE TABLE dbtasas.tasa_cambio (
    id INT AUTO_INCREMENT PRIMARY KEY,
    canal VARCHAR(10) DEFAULT 'ATM',
    fecha TIMESTAMP,    
    moneda VARCHAR(3),
    cambioOficial DECIMAL(12, 2),
    cambioCompra DECIMAL(12, 2),
    cambioVenta DECIMAL(12, 2),
    estado VARCHAR(3)     
);


INSERT INTO dbtasas.tasa_cambio
(canal, fecha, moneda,  cambioOficial,
  cambioCompra,  cambioVenta,  estado)
VALUES
( 'ATM', current_timestamp, 'USD', 6.96,
  6.85, 6.97, 'A');


commit;

SELECT id,canal, fecha, moneda, cambioOficial,
       cambioCompra, cambioVenta, estado
FROM dbtasas.tasa_cambio
where date(fecha)= date('2024-01-28')
and moneda = 'USD'



SELECT *
 from dbtasas.tasa_cambio;


   












