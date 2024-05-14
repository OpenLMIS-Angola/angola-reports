package org.openlmis.ao.reports.i18n;

public class EmbeddedReportCategoryMessageKeys extends ReportingMessageKeys {

  private static final String ERROR = join(SERVICE_ERROR, "embeddedReportCategory");

  public static final String ERROR_EMBEDDED_REPORT_CATEGORY_NAME_DUPLICATED =
      join(ERROR, "name", "duplicated");
  public static final String ERROR_EMBEDDED_REPORT_CATEGORY_NOT_FOUND = join(ERROR, NOT_FOUND);

}
