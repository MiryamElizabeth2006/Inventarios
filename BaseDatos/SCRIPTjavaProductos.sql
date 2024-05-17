select prod.codigo_prod, prod.nombre as nombre_producto, 
udm.codigo_um as nombre_udm, udm.descripcion as descripcion_udm,
cast (prod.precio_venta as decimal(6,2)), prod.tiene_iva, 
cast (prod.coste as decimal(5,4)),
prod.categoria_prod, cat.nombre as nombre_categoria,
stock
from productos prod,unidades_medida udm, categorias cat
where prod.udm = udm.codigo_um
and prod.categoria_prod = cat.codigo_cat
and upper(prod.nombre) like '%M%'

---------------------------------------------------------------------

select * from productos prod,unidades_medida udm, categorias cat
where prod.udm = udm.codigo_um
and prod.categoria_prod = cat.codigo_cat