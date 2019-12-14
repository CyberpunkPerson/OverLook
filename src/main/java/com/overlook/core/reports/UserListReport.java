package com.overlook.core.reports;

import com.overlook.core.domain.user.User;
import lombok.AllArgsConstructor;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRAbstractBeanDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.core.io.ClassPathResource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
public class UserListReport implements Report {

    private List<User> users;

    @Override
    public Map<String, Object> getParameters() {
        return new HashMap<>();
    }

    @Override
    public JasperReport getJasperReport() {
        return loadJasperReport(new ClassPathResource("/jasper/user-list.jasper"));
    }

    @Override
    public JRAbstractBeanDataSource getDataSource() {
        return new JRBeanCollectionDataSource(users);
    }
}
