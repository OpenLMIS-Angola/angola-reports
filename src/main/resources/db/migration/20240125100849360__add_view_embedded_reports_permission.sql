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
                INSERT INTO referencedata.rights (id, description, name, type) VALUES ('81103da6-5eeb-4da8-82f4-3bd0931e78d3', NULL, 'EMBEDDED_REPORTS_VIEW', 'REPORTS');
            END;
        END IF;
    END
$$;

CREATE TABLE reports.embedded_reports (
    id UUID PRIMARY KEY NOT NULL,
    name text UNIQUE NOT NULL,
    url text NOT NULL,
    category text NOT NULL
);