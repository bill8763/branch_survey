package com.allianz.sd.core.service;

import com.allianz.sd.core.jpa.model.TableUpdateVersion;
import com.allianz.sd.core.jpa.repository.TableUpdateVersionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class TableUpdateVersionService {

    private Logger logger = LoggerFactory.getLogger(TableUpdateVersionService.class);

    @Autowired
    private TableUpdateVersionRepository tableUpdateVersionRepository;

    @Autowired
    private DateService dateService;

    public void updateTableVersion(String tableName) {
        updateTableVersion(tableName,dateService.getTodayDate());
    }

    public void updateTableVersion(String tableName , Date dataTime) {
        TableUpdateVersion tableUpdateVersion = new TableUpdateVersion();
        tableUpdateVersion.setTableName(tableName);
        tableUpdateVersion.setDataTime(dataTime);

        this.tableUpdateVersionRepository.save(tableUpdateVersion);
    }

    public boolean haveNewVersion(Date appSyncDate , String tableName) {
        TableUpdateVersion tableUpdateVersion = new TableUpdateVersion();
        tableUpdateVersion.setTableName(tableName);

        Optional<TableUpdateVersion> tableUpdateVersionOptional = this.tableUpdateVersionRepository.findById(tableName);
        tableUpdateVersion = tableUpdateVersionOptional.orElse(null);

        if(tableUpdateVersion == null) return true;
        else return tableUpdateVersion.getDataTime().getTime() > appSyncDate.getTime();
    }
}
