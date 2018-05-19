package jp.cds.siri.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import jp.cds.siri.common.converter.XmlParser;
import jp.cds.siri.common.entity.Word;
import jp.cds.siri.common.utils.RequestUtils;
import jp.cds.siri.common.utils.StringUtils;
import jp.cds.siri.response.GoogleResponse.CompleteSuggestion;

@Service
public class GoogleService {
    private static String SEARCH_URI = "https://www.google.com/complete/search?hl=ja&output=toolbar&";
    public static String USER_SESSION = "user";
    public static String WORD_SESSION = "word";

    @Autowired
    private HttpSession session;

    @Autowired
    private StorageService storageService;

    /**
     *
     * @param word
     * @return
     * @throws IOException
     */
    public String searchWord(String word) throws IOException {
        HttpSession session = initSession();

        // 相手の単語を保管する
        @SuppressWarnings("unchecked")
        Set<String> alreadyWord = (Set<String>) session.getAttribute(WORD_SESSION);
        alreadyWord.add(word);

        String prefix = word.substring(0, 1);
        String surfix = word.substring(word.length() - 1);
        if (StringUtils.toHiragana(surfix).equals(StringUtils.NG_WORD) || alreadyWord.contains(word)) {
            return "You Lose.";
        }

        // ストレージから未使用の単語を探す
        Word newWord = storageService.findByPrefix(surfix, alreadyWord);
        if (newWord != null) {
            return newWord.getWord();
        }

        List<CompleteSuggestion> json = requestGoogleSuggenst(prefix);

        for (CompleteSuggestion suggest : json) {
            String data = StringUtils.toHiragana(suggest.getSuggestion().getData());
            // ひらがな以外が含まれる場合は無視する
            if (!StringUtils.checkHiragana(data)) {
                continue;
            }

            if (!alreadyWord.contains(data)) {
                alreadyWord.add(data);
                session.setAttribute(WORD_SESSION, alreadyWord);
                storageService.storeWord(data);
                return data;
            }
        }

        return "You Win.";

    }

    private List<CompleteSuggestion> requestGoogleSuggenst(String word) throws IOException {
        Map<String, String> params = Maps.newHashMap();
        params.put("q", word);
        String response = RequestUtils.get(SEARCH_URI, params, String.class);
        List<CompleteSuggestion> json = XmlParser.toJson(response, CompleteSuggestion.class);
        return json;
    }

    private HttpSession initSession() {
        if (session.getAttribute(USER_SESSION) == null) {
            session.setAttribute(GoogleService.USER_SESSION, StringUtils.createRandomString(10));
            session.setAttribute(GoogleService.WORD_SESSION, Sets.newHashSet());
        }
        return session;
    }

}
