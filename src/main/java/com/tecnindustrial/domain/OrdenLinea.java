package com.tecnindustrial.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;


/**
 * A OrdenLinea.
 */
@Entity
@Table(name = "orden_linea")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class OrdenLinea implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "cantidad")
    private Long cantidad;

    @ManyToOne
    private OrdenReparacion ordenReparacion;

    @ManyToOne
    private Producto producto;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCantidad() {
        return cantidad;
    }

    public OrdenLinea cantidad(Long cantidad) {
        this.cantidad = cantidad;
        return this;
    }

    public void setCantidad(Long cantidad) {
        this.cantidad = cantidad;
    }

    public OrdenReparacion getOrdenReparacion() {
        return ordenReparacion;
    }

    public OrdenLinea ordenReparacion(OrdenReparacion ordenReparacion) {
        this.ordenReparacion = ordenReparacion;
        return this;
    }

    public void setOrdenReparacion(OrdenReparacion ordenReparacion) {
        this.ordenReparacion = ordenReparacion;
    }

    public Producto getProducto() {
        return producto;
    }

    public OrdenLinea producto(Producto producto) {
        this.producto = producto;
        return this;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
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
        OrdenLinea ordenLinea = (OrdenLinea) o;
        if (ordenLinea.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), ordenLinea.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "OrdenLinea{" +
            "id=" + getId() +
            ", cantidad=" + getCantidad() +
            "}";
    }
}
