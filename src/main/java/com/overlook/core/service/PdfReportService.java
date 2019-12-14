package com.overlook.core.service;

import com.overlook.core.exception.ReportExportException;
import com.overlook.core.reports.Report;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperReport;
import org.springframework.stereotype.Service;
import org.springframework.ui.jasperreports.JasperReportsUtils;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.ReflectionUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;

@Service
public class PdfReportService {

    private static final String DEFAULT_REPORT_FOLDER = "";

    //TODO Dubious implementation
    public byte[] exportReportAsByteArray(Report report) {

        JasperReport jasperReport = report.getJasperReport();
        File outputReport = new File(String.format("  %s%s.pdf", DEFAULT_REPORT_FOLDER, jasperReport.getName()));

        try (FileOutputStream fos = new FileOutputStream(outputReport)) {

            JasperReportsUtils.renderAsPdf(jasperReport, report.getParameters(), report.getDataSource(), fos);

            return FileCopyUtils.copyToByteArray(outputReport);

        } catch (JRException | IOException e) {

            throw new ReportExportException(e.getMessage(), e);

        } finally {
            try {
                Files.delete(outputReport.toPath());
            } catch (IOException ioEx) {
                ReflectionUtils.rethrowRuntimeException(ioEx);
            }
        }
    }
}