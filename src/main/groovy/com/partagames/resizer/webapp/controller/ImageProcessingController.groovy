package com.partagames.resizer.webapp.controller

import groovy.util.logging.Slf4j
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping

@Slf4j
@Controller
class ImageProcessingController {

    @RequestMapping("/imageprocessing")
    void processImages() {
        log.debug("processImages")
    }

}
