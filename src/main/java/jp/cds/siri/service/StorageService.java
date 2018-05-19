package jp.cds.siri.service;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.cds.siri.common.entity.Session;
import jp.cds.siri.common.entity.Word;
import jp.cds.siri.common.repository.SessionRepository;
import jp.cds.siri.common.repository.WordRepository;

@Service
public class StorageService {
    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private WordRepository wordRepository;

    /**
     * セッション情報の保管
     *
     * @param sessionId
     */
    public void registSession(String sessionId) {
        Date now = new Date();
        Session session = new Session();
        session.setSessionId(sessionId);
        session.setInsertTime(now);
        session.setUpdateTime(now);

        sessionRepository.save(session);
    }

    /**
     * Googleから取得した単語情報を蓄積させる
     *
     * @param word
     */
    public void storeWord(String word) {
        Word found = wordRepository.findByWord(word);
        if (found != null) {
            return;
        }

        Word newWord = new Word();
        newWord.setPrefix(word.substring(0, 1));
        newWord.setWord(word);

        wordRepository.save(newWord);
    }

    public Word findByPrefix(String prefix, Collection<String> usedList) {
        List<Word> wordList = wordRepository.findByPrefix(prefix);
        return wordList.stream().filter(w -> !usedList.contains(w.getWord())).findFirst().orElse(null);
    }

    /**
     *
     * @param sessionId
     * @param word
     */
    public void updateUsedWordBySession(String sessionId, String word) {

    }
}
