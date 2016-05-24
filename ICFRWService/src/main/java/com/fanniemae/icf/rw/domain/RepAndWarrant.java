package com.fanniemae.icf.rw.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A RepAndWarrant.
 */
@Entity
@Table(name = "rep_and_warrant")
public class RepAndWarrant implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "rep_and_warrant_id", nullable = false)
    private String repAndWarrantId;

    @NotNull
    @Column(name = "seller_id", nullable = false)
    private String sellerId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRepAndWarrantId() {
        return repAndWarrantId;
    }

    public void setRepAndWarrantId(String repAndWarrantId) {
        this.repAndWarrantId = repAndWarrantId;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RepAndWarrant repAndWarrant = (RepAndWarrant) o;
        if(repAndWarrant.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, repAndWarrant.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "RepAndWarrant{" +
            "id=" + id +
            ", repAndWarrantId='" + repAndWarrantId + "'" +
            ", sellerId='" + sellerId + "'" +
            '}';
    }
}
