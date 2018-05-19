package jp.cds.siri.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import jp.cds.siri.common.entity.SessionAndWord;

@Repository
public interface SessionAndWordRepository extends JpaRepository<SessionAndWord, String>, JpaSpecificationExecutor<SessionAndWord> {
    SessionAndWord findBySessionId(String sessionId);
}
