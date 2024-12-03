package br.com.fiap.soat07.clean.core.exception;

public class CPFInvalidoException extends RuntimeException {

    private static final long serialVersionUID = -1433365271481278186L;
	private final String valor;

    public CPFInvalidoException(String valor) {
    	super(valor);
        this.valor = valor;
        
    }

    public String getValor() {
        return valor;
    }
}
