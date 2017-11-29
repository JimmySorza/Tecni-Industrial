package com.tecnindustrial.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A OrdenReparacion.
 */
@Entity
@Table(name = "orden_reparacion")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class OrdenReparacion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "fecha")
    private LocalDate fecha;

    @Column(name = "garantia")
    private Boolean garantia;

    @NotNull
    @Column(name = "maquina", nullable = false)
    private String maquina;

    @Column(name = "falla")
    private String falla;

    @Column(name = "fecha_prometido")
    private LocalDate fechaPrometido;

    @Column(name = "accesorios")
    private String accesorios;

    @Column(name = "observaciones")
    private String observaciones;

    @Column(name = "informe_tecnico")
    private String informeTecnico;

    @Column(name = "total")
    private Long total;

    @OneToMany(mappedBy = "ordenReparacion")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<OrdenLinea> lineas = new HashSet<>();

    @ManyToOne
    private Tecnico tecnico;

    @ManyToOne
    private Estado estado;

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

    public OrdenReparacion fecha(LocalDate fecha) {
        this.fecha = fecha;
        return this;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Boolean isGarantia() {
        return garantia;
    }

    public OrdenReparacion garantia(Boolean garantia) {
        this.garantia = garantia;
        return this;
    }

    public void setGarantia(Boolean garantia) {
        this.garantia = garantia;
    }

    public String getMaquina() {
        return maquina;
    }

    public OrdenReparacion maquina(String maquina) {
        this.maquina = maquina;
        return this;
    }

    public void setMaquina(String maquina) {
        this.maquina = maquina;
    }

    public String getFalla() {
        return falla;
    }

    public OrdenReparacion falla(String falla) {
        this.falla = falla;
        return this;
    }

    public void setFalla(String falla) {
        this.falla = falla;
    }

    public LocalDate getFechaPrometido() {
        return fechaPrometido;
    }

    public OrdenReparacion fechaPrometido(LocalDate fechaPrometido) {
        this.fechaPrometido = fechaPrometido;
        return this;
    }

    public void setFechaPrometido(LocalDate fechaPrometido) {
        this.fechaPrometido = fechaPrometido;
    }

    public String getAccesorios() {
        return accesorios;
    }

    public OrdenReparacion accesorios(String accesorios) {
        this.accesorios = accesorios;
        return this;
    }

    public void setAccesorios(String accesorios) {
        this.accesorios = accesorios;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public OrdenReparacion observaciones(String observaciones) {
        this.observaciones = observaciones;
        return this;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getInformeTecnico() {
        return informeTecnico;
    }

    public OrdenReparacion informeTecnico(String informeTecnico) {
        this.informeTecnico = informeTecnico;
        return this;
    }

    public void setInformeTecnico(String informeTecnico) {
        this.informeTecnico = informeTecnico;
    }

    public Long getTotal() {
        return total;
    }

    public OrdenReparacion total(Long total) {
        this.total = total;
        return this;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Set<OrdenLinea> getLineas() {
        return lineas;
    }

    public OrdenReparacion lineas(Set<OrdenLinea> ordenLineas) {
        this.lineas = ordenLineas;
        return this;
    }

    public OrdenReparacion addLinea(OrdenLinea ordenLinea) {
        this.lineas.add(ordenLinea);
        ordenLinea.setOrdenReparacion(this);
        return this;
    }

    public OrdenReparacion removeLinea(OrdenLinea ordenLinea) {
        this.lineas.remove(ordenLinea);
        ordenLinea.setOrdenReparacion(null);
        return this;
    }

    public void setLineas(Set<OrdenLinea> ordenLineas) {
        this.lineas = ordenLineas;
    }

    public Tecnico getTecnico() {
        return tecnico;
    }

    public OrdenReparacion tecnico(Tecnico tecnico) {
        this.tecnico = tecnico;
        return this;
    }

    public void setTecnico(Tecnico tecnico) {
        this.tecnico = tecnico;
    }

    public Estado getEstado() {
        return estado;
    }

    public OrdenReparacion estado(Estado estado) {
        this.estado = estado;
        return this;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public OrdenReparacion cliente(Cliente cliente) {
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
        OrdenReparacion ordenReparacion = (OrdenReparacion) o;
        if (ordenReparacion.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), ordenReparacion.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "OrdenReparacion{" +
            "id=" + getId() +
            ", fecha='" + getFecha() + "'" +
            ", garantia='" + isGarantia() + "'" +
            ", maquina='" + getMaquina() + "'" +
            ", falla='" + getFalla() + "'" +
            ", fechaPrometido='" + getFechaPrometido() + "'" +
            ", accesorios='" + getAccesorios() + "'" +
            ", observaciones='" + getObservaciones() + "'" +
            ", informeTecnico='" + getInformeTecnico() + "'" +
            ", total=" + getTotal() +
            "}";
    }
}
