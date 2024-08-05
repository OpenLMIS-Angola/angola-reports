-- Adds ?excludeLevel=local to select expressions in reports Balancete Detalhado, Movimentos de Stock (Por Produto)
-- Stock Disponível (Por Instituição), Stock Disponível (Por Lote), Stock Disponível (Por Producto), Stock Disponível (Por
-- Programa), Balancete Diário

UPDATE template_parameters
SET selectexpression = '/api/geographicZones?excludeLevel=local'
WHERE selectexpression = '/api/geographicZones'
  AND templateid in ('0e6d3515-e88e-4f1a-8e71-5b4613974954', 'a565bb23-1d39-4a9a-8717-a7ff46706243',
                     'b7249c89-8ba0-431b-8b75-052f84a77225',
                     'a525bbec-240e-4782-9cb3-1694fb6c45ee', '33f166c5-bd42-4b64-895e-eb84718a4ac1',
                     '6c42a83b-a803-412e-a3ad-0e2ab97f03fd', 'cdf0cc6c-52e7-485e-a375-60fd4a9951af');
