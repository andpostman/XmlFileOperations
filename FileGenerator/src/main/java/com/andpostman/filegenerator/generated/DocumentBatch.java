//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.andpostman.filegenerator.generated;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.adapters.HexBinaryAdapter;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
        name = "",
        propOrder = {"documentRecord"}
)
@XmlRootElement(
        name = "DocumentBatch"
)
public class DocumentBatch {
    @XmlElement(
            name = "DocumentRecord"
    )
    protected List<DocumentRecord> documentRecord;
    @XmlAttribute(
            name = "BatchId"
    )
    @XmlJavaTypeAdapter(HexBinaryAdapter.class)
    @XmlSchemaType(
            name = "hexBinary"
    )
    protected byte[] batchId;
    @XmlAttribute(
            name = "SessionId"
    )
    @XmlJavaTypeAdapter(HexBinaryAdapter.class)
    @XmlSchemaType(
            name = "hexBinary"
    )
    protected byte[] sessionId;
    @XmlAttribute(
            name = "Server_name"
    )
    protected String serverName;
    @XmlAttribute(
            name = "Problem"
    )
    protected String problem;
    @XmlAttribute(
            name = "FilePath"
    )
    protected String filePath;
    @XmlAttribute(
            name = "DbReplicaID"
    )
    protected String dbReplicaID;
    @XmlAttribute(
            name = "DB_name"
    )
    protected String dbName;

    public DocumentBatch() {
    }

    public List<DocumentRecord> getDocumentRecord() {
        if (this.documentRecord == null) {
            this.documentRecord = new ArrayList();
        }

        return this.documentRecord;
    }

    public void setDocumentRecord(List<DocumentRecord> documentRecord) {
        this.documentRecord = documentRecord;
    }

    public byte[] getBatchId() {
        return this.batchId;
    }

    public void setBatchId(byte[] value) {
        this.batchId = value;
    }

    public byte[] getSessionId() {
        return this.sessionId;
    }

    public void setSessionId(byte[] value) {
        this.sessionId = value;
    }

    public String getServerName() {
        return this.serverName;
    }

    public void setServerName(String value) {
        this.serverName = value;
    }

    public String getProblem() {
        return this.problem;
    }

    public void setProblem(String value) {
        this.problem = value;
    }

    public String getFilePath() {
        return this.filePath;
    }

    public void setFilePath(String value) {
        this.filePath = value;
    }

    public String getDbReplicaID() {
        return this.dbReplicaID;
    }

    public void setDbReplicaID(String value) {
        this.dbReplicaID = value;
    }

    public String getDBName() {
        return this.dbName;
    }

    public void setDBName(String value) {
        this.dbName = value;
    }
}
