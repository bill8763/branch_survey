package com.allianz.sd.core.console;

import com.allianz.sd.core.service.CISLSyncToSNDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/console")
public class ConsoleController {

    @Autowired
    private CISLSyncToSNDService cislSyncToSNDService;


    @RequestMapping("/index")
    public String testAllTableExist(Model model) {
        return "console-index";
    }


    @RequestMapping("/retryCISLBatch")
    @Async
    public String handler(Model model , @RequestParam("batchID") Integer batchID) {

        cislSyncToSNDService.handlingErrorCISLToSND(batchID);

        return "retryCISLBatch";
    }
}
