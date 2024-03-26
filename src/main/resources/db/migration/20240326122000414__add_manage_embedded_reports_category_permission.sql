DO $$
    BEGIN
        IF EXISTS
            ( SELECT 1
              FROM   information_schema.tables
              WHERE  table_schema = 'referencedata'
              AND    table_name = 'rights'
            )
        THEN
            BEGIN
                INSERT INTO referencedata.rights (id, description, name, type) VALUES ('f470482e-6ff0-4a91-b37a-00125673d593', NULL, 'EMBEDDED_REPORTS_CATEGORY_MANAGE', 'GENERAL_ADMIN');
            END;
        END IF;
    END
$$;

CREATE TABLE reports.embedded_report_categories (
    id UUID PRIMARY KEY NOT NULL,
    name text UNIQUE NOT NULL
);