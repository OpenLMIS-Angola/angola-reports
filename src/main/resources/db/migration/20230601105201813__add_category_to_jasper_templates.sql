ALTER TABLE jasper_templates
ADD COLUMN category VARCHAR
DEFAULT 'STOCK';

UPDATE reports.jasper_templates
SET category='ORDER'
WHERE id='cdf0cc6c-52e7-485e-a375-60fd4a9951af';

UPDATE reports.jasper_templates
SET category='REQUISITION'
WHERE id='3ae277e4-fe3e-42fa-ac97-d43868c2e9d8'
OR id='e8c66178-81b6-4b43-b8b9-b3206285fdc2'
OR id='93d09638-4dc7-4c94-a9f2-e80b5c62408e'
OR id='3fafb1cb-659b-4182-8c84-6df209a0f8d5'
OR id='3ac08504-08e1-4b31-8929-f4bfb9112f69';

UPDATE reports.jasper_templates
SET category='ADMINISTRATION'
WHERE id='e1a2f89c-fa5e-40a6-bd1a-b43fdd570eb1'
OR id='5308cb58-a5b7-4741-a3d3-13fb24871bac';