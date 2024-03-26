package org.openlmis.ao.reports.i18n;

public class EmbeddedReportsMessageKeys extends ReportingMessageKeys {

  private static final String ERROR = join(SERVICE_ERROR, "embeddedReport");
  private static final String ID = "id";
  private static final String MISMATCH = "mismatch";
  public static final String ERROR_EMBEDDED_REPORT_NAME_DUPLICATED =
      join(ERROR, "name", "duplicated");
  public static final String ERROR_EMBEDDED_REPORT_NOT_FOUND = join(ERROR, NOT_FOUND);
  public static final String ERROR_EMBEDDED_REPORT_ID_MISMATCH = join(ERROR, ID, MISMATCH);

}
