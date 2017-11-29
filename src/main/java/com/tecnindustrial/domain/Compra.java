package com.tecnindustrial.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A Compra.
 */
@Entity
@Table(name = "compra")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Compra implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "fecha")
    private LocalDate fecha;

    @Column(name = "numero")
    private Integer numero;

    @Column(name = "total")
    private Long total;

    @OneToMany(mappedBy = "compra")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CompraLinea> lineas = new HashSet<>();

    @ManyToOne
    private Proveedor proveedor;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public Compra fecha(LocalDate fecha) {
        this.fecha = fecha;
        return this;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Integer getNumero() {
        return numero;
    }

    public Compra numero(Integer numero) {
        this.numero = numero;
        return this;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public Long getTotal() {
        return total;
    }

    public Compra total(Long total) {
        this.total = total;
        return this;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Set<CompraLinea> getLineas() {
        return lineas;
    }

    public Compra lineas(Set<CompraLinea> compraLineas) {
        this.lineas = compraLineas;
        return this;
    }

    public Compra addLinea(CompraLinea compraLinea) {
        this.lineas.add(compraLinea);
        compraLinea.setCompra(this);
        return this;
    }

    public Compra removeLinea(CompraLinea compraLinea) {
        this.lineas.remove(compraLinea);
        compraLinea.setCompra(null);
        return this;
    }

    public void setLineas(Set<CompraLinea> compraLineas) {
        this.lineas = compraLineas;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public Compra proveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
        return this;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Compra compra = (Compra) o;
        if (compra.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), compra.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Compra{" +
            "id=" + getId() +
            ", fecha='" + getFecha() + "'" +
            ", numero=" + getNumero() +
            ", total=" + getTotal() +
            "}";
    }
}
