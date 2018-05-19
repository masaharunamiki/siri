package jp.cds.siri.api.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jp.cds.siri.service.GoogleService;

@RestController
@RequestMapping("/api/google")
public class GoogleController {
    @Autowired
    GoogleService googleService;

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    @Transactional(rollbackFor = Throwable.class)
    public String getContents(@RequestParam(name = "word", required = true) String word) throws IOException {

        // Googleサジェスト検索をして単語を返す.
        return googleService.searchWord(word);
    }
}
