package jp.cds.siri.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import jp.cds.siri.common.entity.Session;

@Repository
public interface SessionRepository extends JpaRepository<Session, String>, JpaSpecificationExecutor<Session> {

    @Query(value = "SELECT s FROM Session s JOIN FETCH s.joinSessionAndWord WHERE s.sessionId = :sessionId", nativeQuery = true)
    Session findSessionIdByWithWord(String sessionId);

}
