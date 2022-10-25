CREATE
USER coffee_shop_server WITH LOGIN PASSWORD 'CSS_12!@';

CREATE
DATABASE coffee_shop;

GRANT ALL PRIVILEGES ON DATABASE
coffee_shop TO coffee_shop_server;


\connect coffee_shop;

GRANT ALL ON schema public TO coffee_shop_server;
