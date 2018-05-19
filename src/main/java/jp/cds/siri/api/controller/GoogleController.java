package jp.cds.siri.api.controller;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import jp.cds.siri.response.GoogleResponse.CompleteSuggestion;
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

    @RequestMapping(value = "/suggest", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(readOnly = true)
    @ResponseBody
    public ResponseEntity<List<String>> getSuggest(@RequestParam(name = "word", required = true) String word) throws IOException {

        List<CompleteSuggestion> completeSuggestions = googleService.requestGoogleSuggenst(word.substring(word.length() - 1));
        return ResponseEntity.ok(completeSuggestions.stream().map(cs -> cs.getSuggestion().getData()).collect(Collectors.toList()));
    }
}
