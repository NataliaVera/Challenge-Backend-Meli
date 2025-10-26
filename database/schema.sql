-- Create database
CREATE DATABASE inventory_db;

-- Connect to the database
\c inventory_db;

-- Enable UUID extension
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- Create Products Table
CREATE TABLE tbl_products (
    product_id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    product_code VARCHAR(50) UNIQUE NOT NULL,
    product_name VARCHAR(255) NOT NULL,
    product_price DECIMAL(10,2) NOT NULL,
    product_category VARCHAR(100) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Create Stores Table
CREATE TABLE tbl_stores (
    store_id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    store_code VARCHAR(50) UNIQUE NOT NULL,
    store_name VARCHAR(255) NOT NULL,
    store_location VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Create Inventories Table
CREATE TABLE tbl_inventories (
    inventory_id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    product_id UUID NOT NULL,
    quantity INTEGER NOT NULL DEFAULT 0,
    store_id UUID NOT NULL,
    status VARCHAR(50) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_product FOREIGN KEY (product_id) REFERENCES tbl_products(product_id),
    CONSTRAINT fk_store FOREIGN KEY (store_id) REFERENCES tbl_stores(store_id)
);

-- Create indexes for better performance
CREATE INDEX idx_product_code ON tbl_products(product_code);
CREATE INDEX idx_product_category ON tbl_products(product_category);
CREATE INDEX idx_store_code ON tbl_stores(store_code);
CREATE INDEX idx_inventory_product ON tbl_inventories(product_id);
CREATE INDEX idx_inventory_store ON tbl_inventories(store_id);

-- Create trigger function to update updated_at timestamp
CREATE OR REPLACE FUNCTION update_updated_at_column()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ language 'plpgsql';

-- Create triggers for each table
CREATE TRIGGER update_products_updated_at
    BEFORE UPDATE ON tbl_products
    FOR EACH ROW
    EXECUTE FUNCTION update_updated_at_column();

CREATE TRIGGER update_stores_updated_at
    BEFORE UPDATE ON tbl_stores
    FOR EACH ROW
    EXECUTE FUNCTION update_updated_at_column();

CREATE TRIGGER update_inventories_updated_at
    BEFORE UPDATE ON tbl_inventories
    FOR EACH ROW
    EXECUTE FUNCTION update_updated_at_column();

-- Insert sample data
-- Products
INSERT INTO tbl_products (product_code, product_name, product_price, product_category) VALUES
('PRO001', 'iPhone 14 Pro Max', 1299.99, 'Electronics'),
('PRO002', 'Samsung Galaxy S23 Ultra', 1199.99, 'Electronics'),
('PRO003', 'Nike Air Max 2025', 199.99, 'Footwear'),
('PRO004', 'Adidas Ultraboost', 189.99, 'Footwear'),
('PRO005', 'MacBook Pro M3', 1999.99, 'Electronics'),
('PRO006', 'PlayStation 6', 599.99, 'Gaming'),
('PRO007', 'Xbox Series X Pro', 599.99, 'Gaming'),
('PRO008', 'LG OLED 65" TV', 2499.99, 'Electronics'),
('PRO009', 'Dyson V15 Absolute', 699.99, 'Home Appliances'),
('PRO010', 'Nintendo Switch 2', 399.99, 'Gaming');

-- Stores
INSERT INTO tbl_stores (store_code, store_name, store_location) VALUES
('STO001', 'MeLi Central NYC', 'New York, NY'),
('STO002', 'MeLi Miami Downtown', 'Miami, FL'),
('STO003', 'MeLi LA Plaza', 'Los Angeles, CA'),
('STO004', 'MeLi Chicago Hub', 'Chicago, IL'),
('STO005', 'MeLi Houston Center', 'Houston, TX'),
('STO006', 'MeLi Phoenix Mall', 'Phoenix, AZ'),
('STO007', 'MeLi Seattle Prime', 'Seattle, WA'),
('STO008', 'MeLi Boston Square', 'Boston, MA'),
('STO009', 'MeLi Atlanta Hub', 'Atlanta, GA'),
('STO010', 'MeLi Denver Center', 'Denver, CO');

-- Get UUIDs for reference (first store and first product for reference)
DO $$ 
DECLARE
    store1_id UUID;
    store2_id UUID;
    store3_id UUID;
    store4_id UUID;
    store5_id UUID;
    prod1_id UUID;
    prod2_id UUID;
    prod3_id UUID;
    prod4_id UUID;
    prod5_id UUID;
BEGIN
    SELECT store_id INTO store1_id FROM tbl_stores WHERE store_code = 'STO001';
    SELECT store_id INTO store2_id FROM tbl_stores WHERE store_code = 'STO002';
    SELECT store_id INTO store3_id FROM tbl_stores WHERE store_code = 'STO003';
    SELECT store_id INTO store4_id FROM tbl_stores WHERE store_code = 'STO004';
    SELECT store_id INTO store5_id FROM tbl_stores WHERE store_code = 'STO005';
    
    SELECT product_id INTO prod1_id FROM tbl_products WHERE product_code = 'PRO001';
    SELECT product_id INTO prod2_id FROM tbl_products WHERE product_code = 'PRO002';
    SELECT product_id INTO prod3_id FROM tbl_products WHERE product_code = 'PRO003';
    SELECT product_id INTO prod4_id FROM tbl_products WHERE product_code = 'PRO004';
    SELECT product_id INTO prod5_id FROM tbl_products WHERE product_code = 'PRO005';

    -- Inventories - 10 registros específicos
    INSERT INTO tbl_inventories (product_id, store_id, quantity, status) VALUES
    (prod1_id, store1_id, 100, 'IN_STOCK'),
    (prod2_id, store1_id, 75, 'IN_STOCK'),
    (prod3_id, store2_id, 15, 'LOW_STOCK'),
    (prod4_id, store2_id, 50, 'IN_STOCK'),
    (prod5_id, store3_id, 0, 'OUT_OF_STOCK'),
    (prod1_id, store3_id, 25, 'IN_STOCK'),
    (prod2_id, store4_id, 10, 'LOW_STOCK'),
    (prod3_id, store4_id, 30, 'IN_STOCK'),
    (prod4_id, store5_id, 5, 'LOW_STOCK'),
    (prod5_id, store5_id, 60, 'IN_STOCK');

    -- Notifications - 10 registros específicos
    INSERT INTO tbl_notifications (store_id, product_id, event_type, message) VALUES
    (store1_id, prod1_id, 'STOCK_UPDATE', 'New stock arrival: iPhone 14 Pro Max'),
    (store2_id, prod2_id, 'LOW_STOCK', 'Low stock alert: Samsung Galaxy S23 Ultra'),
    (store3_id, prod3_id, 'OUT_OF_STOCK', 'Out of stock: Nike Air Max 2025'),
    (store4_id, prod4_id, 'RESTOCK_NEEDED', 'Restock needed: Adidas Ultraboost'),
    (store5_id, prod5_id, 'STOCK_UPDATE', 'Stock updated: MacBook Pro M3'),
    (store1_id, prod2_id, 'LOW_STOCK', 'Low stock warning: Samsung Galaxy S23 Ultra'),
    (store2_id, prod3_id, 'STOCK_UPDATE', 'Inventory updated: Nike Air Max 2025'),
    (store3_id, prod4_id, 'OUT_OF_STOCK', 'No stock available: Adidas Ultraboost'),
    (store4_id, prod5_id, 'RESTOCK_NEEDED', 'Please restock: MacBook Pro M3'),
    (store5_id, prod1_id, 'STOCK_UPDATE', 'Stock level updated: iPhone 14 Pro Max');

    -- Inventory Records - 10 registros específicos
    INSERT INTO tbl_inventory_records (store_id, product_id) VALUES
    (store1_id, prod1_id),
    (store1_id, prod2_id),
    (store2_id, prod2_id),
    (store2_id, prod3_id),
    (store3_id, prod3_id),
    (store3_id, prod4_id),
    (store4_id, prod4_id),
    (store4_id, prod5_id),
    (store5_id, prod5_id),
    (store5_id, prod1_id);
END $$;

-- Create Notifications Table
CREATE TABLE tbl_notifications (
    notification_id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    store_id UUID NOT NULL,
    product_id UUID NOT NULL,
    event_type VARCHAR(50) NOT NULL,
    message TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_notification_store FOREIGN KEY (store_id) REFERENCES tbl_stores(store_id),
    CONSTRAINT fk_notification_product FOREIGN KEY (product_id) REFERENCES tbl_products(product_id)
);

-- Create Inventory Records Table for Sync
CREATE TABLE tbl_inventory_records (
    event_id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    store_id UUID NOT NULL,
    product_id UUID NOT NULL,
    last_updated TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_inventory_store FOREIGN KEY (store_id) REFERENCES tbl_stores(store_id),
    CONSTRAINT fk_inventory_product FOREIGN KEY (product_id) REFERENCES tbl_products(product_id),
    CONSTRAINT uk_store_product UNIQUE (store_id, product_id)
);

-- Create indexes for notifications
CREATE INDEX idx_notification_store ON tbl_notifications(store_id);
CREATE INDEX idx_notification_product ON tbl_notifications(product_id);
CREATE INDEX idx_notification_event_type ON tbl_notifications(event_type);
CREATE INDEX idx_notification_created_at ON tbl_notifications(created_at);

-- Create indexes for inventory records
CREATE INDEX idx_inventory_record_store ON tbl_inventory_records(store_id);
CREATE INDEX idx_inventory_record_product ON tbl_inventory_records(product_id);
CREATE INDEX idx_inventory_record_last_updated ON tbl_inventory_records(last_updated);