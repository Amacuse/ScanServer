package com.Bean;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.io.Serializable;

@NamedQueries({
        @NamedQuery(
                name = "getAllCreationDate",
                query = "SELECT fc.id, fc.creation FROM FileContent fc WHERE fc.file =:file_id"
        )
})
@Entity
@Table(name = "file_content")
public class FileContent implements Serializable {

    private static final long serialVersionUID = 8132068823531740525L;

    @Id
    @GeneratedValue
    private Long id;

    @Lob
    private byte[] content;

    @Column(insertable = false, updatable = false)
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime creation;

    @ManyToOne(fetch = FetchType.LAZY)
    private File file;

    public FileContent() {
    }

    public FileContent(byte[] content) {
        this(content, null);
    }

    public FileContent(byte[] content, DateTime creation) {
        this.content = content;
        this.creation = creation;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public DateTime getCreation() {
        return creation;
    }

    public void setCreation(DateTime creation) {
        this.creation = creation;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj.getClass() != getClass()) {
            return false;
        }
        FileContent rhs = (FileContent) obj;
        return new EqualsBuilder()
                .append(this.content, rhs.content)
                .append(this.file, rhs.file)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(content)
                .append(file)
                .toHashCode();
    }
}
