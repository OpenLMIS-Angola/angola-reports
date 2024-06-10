INSERT INTO
    reports.template_parameters (
        id,
        datatype,
        defaultvalue,
        description,
        displayname,
        displayproperty,
        name,
        required,
        selectexpression,
        selectproperty,
        templateid,
        selectmethod,
        selectbody
    )
VALUES
    (
        'f6e83d7e-2641-4081-a128-439aa31c69e1',
        'java.lang.String',
        null,
        null,
        'Zona Geográfica',
        'name',
        'district',
        false,
        '/api/geographicZones',
        'name',
        'a565bb23-1d39-4a9a-8717-a7ff46706243',
        'GET',
        null
    );

UPDATE
    reports.template_parameters
SET
    "datatype" = 'java.lang.String',
    defaultvalue = NULL,
    description = NULL,
    displayname = 'Instituição',
    displayproperty = 'name',
    "name" = 'facility',
    required = false,
    selectexpression = '/api/facilities',
    selectproperty = 'name',
    templateid = 'a565bb23-1d39-4a9a-8717-a7ff46706243',
    selectmethod = 'GET',
    selectbody = NULL
WHERE
    id = '35dd6fbd-e22c-4d7d-ac08-d6193e77aa96';
