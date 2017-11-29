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
 * A Venta.
 */
@Entity
@Table(name = "venta")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Venta implements Serializable {

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

    @OneToMany(mappedBy = "venta")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<VentaLinea> lineas = new HashSet<>();

    @ManyToOne
    private Cliente cliente;

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

    public Venta fecha(LocalDate fecha) {
        this.fecha = fecha;
        return this;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Integer getNumero() {
        return numero;
    }

    public Venta numero(Integer numero) {
        this.numero = numero;
        return this;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public Long getTotal() {
        return total;
    }

    public Venta total(Long total) {
        this.total = total;
        return this;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Set<VentaLinea> getLineas() {
        return lineas;
    }

    public Venta lineas(Set<VentaLinea> ventaLineas) {
        this.lineas = ventaLineas;
        return this;
    }

    public Venta addLinea(VentaLinea ventaLinea) {
        this.lineas.add(ventaLinea);
        ventaLinea.setVenta(this);
        return this;
    }

    public Venta removeLinea(VentaLinea ventaLinea) {
        this.lineas.remove(ventaLinea);
        ventaLinea.setVenta(null);
        return this;
    }

    public void setLineas(Set<VentaLinea> ventaLineas) {
        this.lineas = ventaLineas;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Venta cliente(Cliente cliente) {
        this.cliente = cliente;
        return this;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
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
        Venta venta = (Venta) o;
        if (venta.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), venta.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Venta{" +
            "id=" + getId() +
            ", fecha='" + getFecha() + "'" +
            ", numero=" + getNumero() +
            ", total=" + getTotal() +
            "}";
    }
}
