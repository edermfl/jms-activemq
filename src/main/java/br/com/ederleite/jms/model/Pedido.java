package br.com.ederleite.jms.model;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

//JAX-B

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Pedido implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer codigo;

    private Calendar dataPagamento;

    private Calendar dataPedido;

    @XmlElementWrapper(name = "itens")
    @XmlElement(name = "item")
    private Set<Item> itens = new LinkedHashSet<>();

    private BigDecimal valorTotal;

    public Pedido(Integer codigo, Calendar dataPedido, Calendar dataPagamento, BigDecimal valorTotal) {
	this.codigo = codigo;
	this.dataPedido = dataPedido;
	this.dataPagamento = dataPagamento;
	this.valorTotal = valorTotal;
    }

    Pedido() {
    }

    public void adicionaItem(Item item) {
	this.itens.add(item);
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj) {
	    return true;
	}
	if (obj == null) {
	    return false;
	}
	if (getClass() != obj.getClass()) {
	    return false;
	}
	Pedido other = (Pedido) obj;
	if (codigo == null) {
	    if (other.codigo != null) {
		return false;
	    }
	} else if (!codigo.equals(other.codigo)) {
	    return false;
	}
	return true;
    }

    public Integer getCodigo() {
	return codigo;
    }

    public Calendar getDataPagamento() {
	return dataPagamento;
    }

    public Calendar getDataPedido() {
	return dataPedido;
    }

    public Set<Item> getItens() {
	return Collections.unmodifiableSet(this.itens);
    }

    public BigDecimal getValorTotal() {
	return valorTotal;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
	return result;
    }

    public void setCodigo(final Integer pCodigo) {
	codigo = pCodigo;
    }

    @Override
    public String toString() {
	return "Pedido [codigo=" + codigo + ", dataPedido=" + dataPedido + ", dataPagamento=" + dataPagamento
			+ ", valorTotal=" + valorTotal + ", itens=" + itens + "]";
    }

}
