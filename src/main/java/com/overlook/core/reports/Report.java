package com.overlook.core.reports;

import com.overlook.core.exception.ReportExportException;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRAbstractBeanDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

public interface Report {

    JasperReport getJasperReport();

    Map<String, Object> getParameters();

    JRAbstractBeanDataSource getDataSource();

    default JasperReport loadJasperReport(Resource report) {
        try {

            return Optional.ofNullable(JRLoader.loadObject(report.getInputStream()))
                    .map(JasperReport.class::cast)
                    .orElseThrow(ClassCastException::new);

        } catch (JRException | IOException e) {
            throw new ReportExportException(e.getMessage(), e);
        }
    }

}
