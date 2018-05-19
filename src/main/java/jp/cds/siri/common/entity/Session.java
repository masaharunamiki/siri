package jp.cds.siri.common.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Entity
@Table(name = "session")
@EqualsAndHashCode(exclude = { "joinSessionAndWord" })
@ToString(exclude = { "joinSessionAndWord" })
public class Session implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "session_id")
    private String sessionId;

    @Column(name = "insert_time")
    private Date insertTime;

    @Column(name = "update_time")
    private Date updateTime;

    @OneToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "session_id", insertable = false, updatable = false)
    private SessionAndWord joinSessionAndWord;
}
