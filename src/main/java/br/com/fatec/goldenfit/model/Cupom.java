package br.com.fatec.goldenfit.model;

import br.com.fatec.goldenfit.model.enums.TipoCupom;

import java.util.Calendar;
import java.util.Date;

public class Cupom extends EntidadeDominio{
    private String codigo;
    private String nome;
    private Double valor;
    private Date validade;
    private TipoCupom tipo;
    private boolean aplicado;
    private Integer idCliente;
    private Integer idPedido;
    private boolean status;

    public Cupom() {

    }

    public Cupom(String codigo, String nome, Double valor, Date validade, TipoCupom tipo, Integer idCliente) {
        this.codigo = codigo;
        this.nome = nome;
        this.valor = valor;
        this.validade = validade;
        this.tipo = tipo;
        this.idCliente = idCliente;
    }

    public Cupom(Integer id, String codigo, String nome, Double valor, Date validade, TipoCupom tipo, Integer idCliente, Integer idPedido, boolean status) {
        super();
        if (id != null) {
            this.setId(id);
        }
        this.codigo = codigo;
        this.nome = nome;
        this.valor = valor;
        this.validade = validade;
        this.tipo = tipo;
        if(idCliente != null && idCliente > 0) {
            this.idCliente = idCliente;
        }
        if(idPedido != null && idPedido > 0) {
            this.idPedido = idPedido;
        }

        this.status = status;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public TipoCupom getTipo() {
        return tipo;
    }

    public void setTipo(TipoCupom tipo) {
        this.tipo = tipo;
    }

    public double getValor() {
        if(valor == null) {
            valor = 0d;
        }		return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Date getValidade() {
        return validade;
    }

    public void setValidade(Date validade) {
        this.validade = validade;
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public Integer getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(Integer idPedido) {
        this.idPedido = idPedido;
    }

    public boolean isValido() {
        boolean isValido = false;
        if (this.validade != null) {

            Calendar hoje = Calendar.getInstance();
            hoje.set(Calendar.HOUR_OF_DAY, 0);
            hoje.set(Calendar.MINUTE, 0);
            hoje.set(Calendar.SECOND, 0);
            hoje.set(Calendar.MILLISECOND, 0);

            if (this.validade.after(hoje.getTime()) || this.validade.equals(hoje.getTime())) {
                isValido = true;
            }
        }
        return isValido;
    }

    public boolean isAplicado() {
        return aplicado;
    }

    public void setAplicado(boolean aplicado) {
        this.aplicado = aplicado;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (aplicado ? 1231 : 1237);
        result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
        result = prime * result + ((nome == null) ? 0 : nome.hashCode());
        result = prime * result + ((tipo == null) ? 0 : tipo.hashCode());
        result = prime * result + ((validade == null) ? 0 : validade.hashCode());
        result = prime * result + ((valor == null) ? 0 : valor.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Cupom other = (Cupom) obj;
        if (aplicado != other.aplicado)
            return false;
        if (codigo == null) {
            if (other.codigo != null)
                return false;
        } else if (!codigo.equals(other.codigo))
            return false;
        if (nome == null) {
            if (other.nome != null)
                return false;
        } else if (!nome.equals(other.nome))
            return false;
        if (tipo != other.tipo)
            return false;
        if (validade == null) {
            if (other.validade != null)
                return false;
        } else if (!validade.equals(other.validade))
            return false;
        if (valor == null) {
            if (other.valor != null)
                return false;
        } else if (!valor.equals(other.valor))
            return false;
        return true;
    }
}
