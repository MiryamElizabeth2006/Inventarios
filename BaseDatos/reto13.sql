select * from productos
select * from cabecera_pedido
select * from detalle_pedido
select * from proveedores

update cabecera_pedido set estado = 'R' WHERE codigoCP = ?

update detalle_pedido 
set cantidad_recibida = 40, subtotal = 20
where  codigo_det = 5
