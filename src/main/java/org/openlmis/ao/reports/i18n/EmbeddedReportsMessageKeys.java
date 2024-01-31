package org.openlmis.ao.reports.i18n;

public class EmbeddedReportsMessageKeys extends ReportingMessageKeys {

  private static final String ERROR = join(SERVICE_ERROR, "embeddedReport");
  public static final String ERROR_EMBEDDED_REPORT_NAME_DUPLICATED =
      join(ERROR, "name", "duplicated");
  public static final String ERROR_EMBEDDED_REPORT_INVALID_PARAMS =
      join(ERROR, "invalid", "request", "params");
  public static final String ERROR_EMBEDDED_REPORT_NOT_FOUND = join(ERROR, NOT_FOUND);

}
