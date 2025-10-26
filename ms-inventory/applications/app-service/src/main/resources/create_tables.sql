-- Database: inventory_db

-- DROP DATABASE IF EXISTS inventory_db;

CREATE DATABASE inventory_db
    WITH
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'Spanish_Latin America.1252'
    LC_CTYPE = 'Spanish_Latin America.1252'
    LOCALE_PROVIDER = 'libc'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1
    IS_TEMPLATE = False;

-- Table: public.tbl_inventories

-- DROP TABLE IF EXISTS public.tbl_inventories;

CREATE TABLE IF NOT EXISTS public.tbl_inventories
(
    product_id uuid NOT NULL,
    total_stock integer NOT NULL DEFAULT 0,
    stock_by_store jsonb,
    last_updated timestamp with time zone DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT tbl_inventories_pkey PRIMARY KEY (product_id),
    CONSTRAINT tbl_inventories_product_id_fkey FOREIGN KEY (product_id)
        REFERENCES public.tbl_products (product_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

-- Table: public.tbl_products

-- DROP TABLE IF EXISTS public.tbl_products;

CREATE TABLE IF NOT EXISTS public.tbl_products
(
    product_id uuid NOT NULL DEFAULT gen_random_uuid(),
    product_code character varying(50) COLLATE pg_catalog."default" NOT NULL,
    product_category character varying(100) COLLATE pg_catalog."default",
    product_name character varying(100) COLLATE pg_catalog."default",
    description character varying(200) COLLATE pg_catalog."default",
    product_price character varying(100) COLLATE pg_catalog."default",
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT tbl_products_pkey PRIMARY KEY (product_id),
    CONSTRAINT tbl_products_product_code_key UNIQUE (product_code)
)

-- Table: public.tbl_products

-- DROP TABLE IF EXISTS public.tbl_products;

CREATE TABLE IF NOT EXISTS public.tbl_products
(
    product_id uuid NOT NULL DEFAULT gen_random_uuid(),
    product_code character varying(50) COLLATE pg_catalog."default" NOT NULL,
    product_category character varying(100) COLLATE pg_catalog."default",
    product_name character varying(100) COLLATE pg_catalog."default",
    description character varying(200) COLLATE pg_catalog."default",
    product_price character varying(100) COLLATE pg_catalog."default",
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT tbl_products_pkey PRIMARY KEY (product_id),
    CONSTRAINT tbl_products_product_code_key UNIQUE (product_code)
)

CREATE TABLE stock (
    stock_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    store_id UUID NOT NULL,
    product_id UUID NOT NULL,
    quantity INT NOT NULL DEFAULT 0,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_store FOREIGN KEY (store_id) REFERENCES store(store_id),
    CONSTRAINT fk_product FOREIGN KEY (product_id) REFERENCES product(product_id)
);