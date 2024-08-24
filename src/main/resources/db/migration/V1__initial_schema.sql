# Flyway - Flyway is a framework/database migration tool that uses migrations to update a database from one version to the next. It simplifies the process of managing database schema changes in your applications (schema versioning). It automates the process of applying and rolling back database changes, ensuring consistency and reducing the risk of errors.
# Flyway Migration - It refers to the process of applying changes to the database schema using Flyway. Essentially, it involves executing migration scripts that modify the database's structure (e.g., creating tables, altering columns) or data (e.g., seeding initial data). It migrates from one version to another version of the database schema.
# Alternatively, you can use Liquibase, another popular database migration tool that provides similar functionality to Flyway. Both tools are widely used in software development to manage database schema changes in a controlled and automated manner. Liquibase uses XML or YAML files to define database changes, while Flyway uses SQL scripts. Liquibase also supports a wider range of databases and has additional features like change logs and rollback support.
# Flyway follows a convention-over-configuration approach, while Liquibase allows more flexibility and customization through its XML, YAML, or JSON formatted change logs. Syntax: Flyway uses SQL-based migration scripts, whereas Liquibase uses XML, YAML, or JSON to define the database changes.
# Flyway - Simple, enough for 90% cases
# Liquibase - Complex, but more powerful, support for advance features.
# FileStructure: V1__(name).sql

CREATE TABLE category
(
    id               BIGINT AUTO_INCREMENT NOT NULL,
    created_at       datetime NULL,
    last_modified_at datetime NULL,
    is_deleted       BIT(1)       NOT NULL,
    category_name             VARCHAR(255) NOT NULL,
    `description`    VARCHAR(255) NULL,
    subcategory_id   BIGINT NULL,
    CONSTRAINT pk_category PRIMARY KEY (id)
);

CREATE TABLE category_featured_products
(
    category_id          BIGINT NOT NULL,
    featured_products_id BIGINT NOT NULL
);

CREATE TABLE product
(
    id               BIGINT AUTO_INCREMENT NOT NULL,
    created_at       datetime NULL,
    last_modified_at datetime NULL,
    is_deleted       BIT(1) NOT NULL,
    title            VARCHAR(255) NULL,
    `description`    VARCHAR(255) NULL,
    price DOUBLE NULL,
    image_url        VARCHAR(255) NULL,
    category_id      BIGINT NULL,
    CONSTRAINT pk_product PRIMARY KEY (id)
);

CREATE TABLE subcategory
(
    id               BIGINT AUTO_INCREMENT NOT NULL,
    created_at       datetime NULL,
    last_modified_at datetime NULL,
    is_deleted       BIT(1) NOT NULL,
    name             VARCHAR(255) NULL,
    CONSTRAINT pk_subcategory PRIMARY KEY (id)
);

ALTER TABLE category_featured_products
    ADD CONSTRAINT uc_category_featured_products_featuredproducts UNIQUE (featured_products_id);

ALTER TABLE category
    ADD CONSTRAINT uc_category_name UNIQUE (category_name);

ALTER TABLE category
    ADD CONSTRAINT FK_CATEGORY_ON_SUBCATEGORY FOREIGN KEY (subcategory_id) REFERENCES subcategory (id);

ALTER TABLE product
    ADD CONSTRAINT FK_PRODUCT_ON_CATEGORY FOREIGN KEY (category_id) REFERENCES category (id);

ALTER TABLE category_featured_products
    ADD CONSTRAINT fk_catfeapro_on_category FOREIGN KEY (category_id) REFERENCES category (id);

ALTER TABLE category_featured_products
    ADD CONSTRAINT fk_catfeapro_on_product FOREIGN KEY (featured_products_id) REFERENCES product (id);