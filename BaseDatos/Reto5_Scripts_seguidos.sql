drop table if exists historial_stock;
drop table if exists detalle_ventas;
drop table if exists cabecera_ventas;
drop table if exists detalle_pedido;
drop table if exists cabecera_pedido;
drop table if exists estados_pedido;
drop table if exists proveedores;
drop table if exists tipo_documento;
drop table if exists productos;
drop table if exists unidades_medida;
drop table if exists categorias_unidad_medida;
drop table if exists categorias;



create table categorias(
	codigo_cat serial not null,
	nombre varchar(100) not null,
	categoria_padre int,
	constraint categorias_pk primary key (codigo_cat),
	constraint categorias_fk foreign key (categoria_padre)
	references categorias(codigo_cat)
);

insert into categorias(nombre, categoria_padre)
values('Materia Prima', null);
insert into categorias(nombre, categoria_padre)
values('Proteina', 1);
insert into categorias(nombre, categoria_padre)
values('Salsas', 1);
insert into categorias(nombre, categoria_padre)
values('Punto de Venta', null);
insert into categorias(nombre, categoria_padre)
values('Bebidas', 4);
insert into categorias(nombre, categoria_padre)
values('Con alchol', 5);
insert into categorias(nombre, categoria_padre)
values('Sin alchol', 5);

create table categorias_unidad_medida(
codigo_udm char(1) not null,
nombre varchar(100) not null,
constraint categorias_unidad_medida_pk primary key(codigo_udm)
);

insert into categorias_unidad_medida(codigo_udm, nombre)
values('U', 'Unidades');
insert into categorias_unidad_medida(codigo_udm, nombre)
values('V', 'Volumen');
insert into categorias_unidad_medida(codigo_udm, nombre)
values('P', 'Peso');

create table unidades_medida(
codigo_um varchar(3) not null,
descripcion varchar(100) not null,
categoria_udm char(1) not null,
constraint  unidades_medida_pk primary key(codigo_um),
constraint unidades_medida_fk foreign key(categoria_udm)
references categorias_unidad_medida(codigo_udm)
);

insert into unidades_medida(codigo_um, descripcion, categoria_udm)
values('ml', 'mililitro', 'V');
insert into unidades_medida(codigo_um, descripcion, categoria_udm)
values('lt', 'litros', 'V');
insert into unidades_medida(codigo_um, descripcion, categoria_udm)
values('u', 'unidad', 'U');
insert into unidades_medida(codigo_um, descripcion, categoria_udm)
values('d', 'docenas', 'U');
insert into unidades_medida(codigo_um, descripcion, categoria_udm)
values('g', 'gramos', 'P');
insert into unidades_medida(codigo_um, descripcion, categoria_udm)
values('kg', 'kilogramos', 'P');
insert into unidades_medida(codigo_um, descripcion, categoria_udm)
values('lb', 'libras', 'P');

create table productos(
codigo_prod serial not null,
nombre varchar(100) not null,
unidad_medida varchar(3) not null,
precio_venta money not null,
tiene_iva boolean not null,
coste money not null,
categoria_prod int not null,
stock int not null,	
constraint productos_pk primary key(codigo_prod),
constraint pruductos_fk foreign key(categoria_prod)
references categorias(codigo_cat)
);

insert into productos(nombre, unidad_medida, precio_venta, tiene_iva, coste, categoria_prod, stock)
values('Coca Cola pequeña', 'u', 0.5804, true, 0.3729, 7, 105);
insert into productos(nombre, unidad_medida, precio_venta, tiene_iva, coste, categoria_prod, stock)
values('Salsa de tomate', 'kg', 0.95, true, 0.8736, 3, 0);
insert into productos(nombre, unidad_medida, precio_venta, tiene_iva, coste, categoria_prod, stock)
values('Mostaza', 'kg', 0.95, true, 0.8936, 3, 0);
insert into productos(nombre, unidad_medida, precio_venta, tiene_iva, coste, categoria_prod, stock)
values('Fuze Tea', 'u', 0.80, true, 0.70, 7, 49);

create table tipo_documento(
codigo_doc char(1) not null,
descripcion varchar(100) not null,
constraint tipo_documento_pk primary key(codigo_doc)
);

insert into tipo_documento(codigo_doc, descripcion)
values('C', 'Cédula');
insert into tipo_documento(codigo_doc, descripcion)
values('R', 'Ruc');

create table proveedores(
identificador varchar(13) not null,
tipo_documento_prov char(1) not null,
nombre varchar(100) not null,
telefono char(10) not null,
correo varchar(100) not null,
direccion varchar(100) not null,
constraint proveedores_pk primary key(identificador),
constraint proveedores_fk foreign key(tipo_documento_prov)
references tipo_documento(codigo_doc)
);

insert into proveedores(identificador, tipo_documento_prov, nombre, telefono, correo, direccion)
values('1245789632145', 'R', 'Alvaro Loaiza', '0987451263', 'alvaro2000@gmail.com', 'Calderón');
insert into proveedores(identificador, tipo_documento_prov, nombre, telefono, correo, direccion)
values('1778961236', 'C', 'Snacks.SA', '0984187545', 'snacksSA@gmail.com', 'Carapungo');

create table estados_pedido(
codigo_ped char(1) not null,
descripcion varchar(100) not null,
constraint estados_pedido_pk primary key(codigo_ped)
);

insert into estados_pedido(codigo_ped, descripcion)
values('S', 'Solicitado');

insert into estados_pedido(codigo_ped, descripcion)
values('R', 'Recibido');

create table cabecera_pedido(
numero serial not null,
proveedor varchar(13) not null, 
fecha TIMESTAMPTZ not null,
estado char(1) not null,
constraint cabecera_pedido_pk primary key(numero),
	
FOREIGN KEY (proveedor) REFERENCES proveedores(identificador),
FOREIGN KEY (estado) REFERENCES estados_pedido(codigo_ped)	
);

insert into cabecera_pedido(proveedor, fecha, estado)
values('1245789632145', '30/11/2023 19:45', 'R');
insert into cabecera_pedido(proveedor, fecha, estado)
values('1245789632145', '30/11/2023 19:45', 'R');

create table detalle_pedido(
codigo_det serial not null,
cabecera_pedido  int not null,
producto int not null,
cantidad_solicitada int not null,
subtotal money not null,
cantidad_recibida int not null,
constraint detalle_pedido_pk primary key(codigo_det),
FOREIGN KEY (cabecera_pedido) REFERENCES cabecera_pedido(numero),
FOREIGN KEY (producto) REFERENCES productos(codigo_prod)
);

insert into detalle_pedido(cabecera_pedido, producto, cantidad_solicitada, subtotal, cantidad_recibida)
values(1, 1, 100, 37.29, 100);
insert into detalle_pedido(cabecera_pedido, producto, cantidad_solicitada, subtotal, cantidad_recibida)
values(1, 4, 50, 11.8, 50);
insert into detalle_pedido(cabecera_pedido, producto, cantidad_solicitada, subtotal, cantidad_recibida)
values(2, 1, 10, 3.73, 10);

create table cabecera_ventas(
codigo_cab_vent serial not null,
fecha_hora TIMESTAMPTZ not null,
total_sin_iva money not null,
iva money not null,
total money not null,
constraint cabecera_ventas_pk primary key(codigo_cab_vent)
);

insert into cabecera_ventas(fecha_hora, total_sin_iva, iva, total)
values('20/11/2023 20:00', 3.26, 0.39, 3.65);

create table detalle_ventas(
codigo_det_ven serial not null,
cabecera_ventas int not null,
producto int not null,
cantidad_vendida int not null,
precio_venta money not null,
subtotal money not null,
subtotal_iva money not null,
constraint detalle_ventas_pk primary key(codigo_det_ven),
FOREIGN KEY (cabecera_ventas) REFERENCES cabecera_ventas(codigo_cab_vent),
FOREIGN KEY (producto) REFERENCES productos(codigo_prod)
);

insert into detalle_ventas(cabecera_ventas, producto, cantidad_vendida, precio_venta, subtotal, subtotal_iva)
values(1, 1, 5,	0.58, 2.9, 3.25);
insert into detalle_ventas(cabecera_ventas, producto, cantidad_vendida, precio_venta, subtotal, subtotal_iva)
values(1, 4, 1, 0.36, 0.36, 0.4);

create table historial_stock(
codigo_his serial not null,
fecha_hora TIMESTAMPTZ not null,
referencia varchar(50) not null,
producto int not null,
cantidad int not null,
constraint historial_stock_pk primary key(codigo_his),
constraint historial_stock_fk foreign key(producto)
references productos(codigo_prod)
);

insert into historial_stock(fecha_hora, referencia, producto, cantidad)
values('20/11/2023 19:59', 'Pedido1', 1, 100);
insert into historial_stock(fecha_hora, referencia, producto, cantidad)
values('20/11/2023 19:59', 'Pedido1', 4, 50);
insert into historial_stock(fecha_hora, referencia, producto, cantidad)
values('20/11/2023 20:00', 'Pedido2', 1, 10);
insert into historial_stock(fecha_hora, referencia, producto, cantidad)
values('20/11/2023 20:00', 'Venta1', 1, -5);
insert into historial_stock(fecha_hora, referencia, producto, cantidad)
values('20/11/2023 20:00', 'Venta1', 4, -1);