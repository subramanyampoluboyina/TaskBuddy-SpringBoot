package com.TaskBuddy.TaskBuddy.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Blob;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "trnDocuments")
public class TrnDocuments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private int Id;

    @Column(name = "DocumentName")
    private String DocumentName;
    @Lob
    @Column(name = "Base64Content", columnDefinition = "LONGTEXT")
    private String Base64Content;

    @Column(name = "DocumentSize")
    private String DocumentSize;

    @Column(name = "DocumentType")
    private String DocumentType;

    @Column(name = "IsActive", columnDefinition = "tinyint(1)")
    private boolean IsActive;

    @ManyToOne
    @JoinColumn(name = "TaskId", nullable = false)
    private TrnTask trnTask;

    public TrnDocuments(String documentName, String base64Content, String documentSize, String documentType, boolean isActive, TrnTask trnTask) {
        DocumentName = documentName;
        Base64Content = base64Content;
        DocumentSize = documentSize;
        DocumentType = documentType;
        IsActive = isActive;
        this.trnTask = trnTask;
    }

    @Override
    public String toString() {
        return "TrnDocuments{" +
                "Id=" + Id +
                ", DocumentName='" + DocumentName + '\'' +
                ", Base64Content=" + Base64Content +
                ", DocumentSize=" + DocumentSize +
                ", DocumentType='" + DocumentType + '\'' +
                ", IsActive=" + IsActive +
                ", trnTask=" + trnTask +
                '}';
    }
}
