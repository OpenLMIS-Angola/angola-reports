ALTER TABLE reports.embedded_reports DROP CONSTRAINT fk_embedded_reports_category;
ALTER TABLE reports.embedded_reports DROP COLUMN categoryId;
ALTER TABLE reports.embedded_reports ADD COLUMN categoryId UUID;
ALTER TABLE reports.embedded_reports ADD CONSTRAINT fk_embedded_reports_category FOREIGN KEY (categoryid) REFERENCES reports.embedded_report_categories(id);
