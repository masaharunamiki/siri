package jp.cds.siri.common.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

import lombok.Data;

@Data
@Entity
@Table(name = "session_and_word")
@DynamicUpdate
public class SessionAndWord implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "session_id")
    private String sessionId;

    @Column(name = "own_word_json")
    private String ownWordJson;

    @Column(name = "opponent_word_json")
    private String opponentWordJson;

}
