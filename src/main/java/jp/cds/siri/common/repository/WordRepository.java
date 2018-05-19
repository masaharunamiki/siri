package jp.cds.siri.common.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import jp.cds.siri.common.entity.Word;

@Repository
public interface WordRepository extends JpaRepository<Word, Integer>, JpaSpecificationExecutor<Word> {
    List<Word> findByPrefix(String prefix);

    Word findByWord(String word);

}
