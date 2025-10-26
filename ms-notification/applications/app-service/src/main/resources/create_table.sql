CREATE TABLE notification (
notification_id UUID PRIMARY KEY,
store_id UUID NOT NULL,
product_id UUID NOT NULL,
message VARCHAR(1024),
status VARCHAR(50),
created_at TIMESTAMP
);


CREATE TABLE inventory_sync (
store_id UUID NOT NULL,
product_id UUID NOT NULL,
quantity INTEGER,
last_updated TIMESTAMP,
PRIMARY KEY (store_id, product_id)
);