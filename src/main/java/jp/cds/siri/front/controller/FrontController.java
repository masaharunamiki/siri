package jp.cds.siri.front.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller()
public class FrontController {

    @RequestMapping("/siri")
    String index() {
        return "page/siri/index";
    }

}
