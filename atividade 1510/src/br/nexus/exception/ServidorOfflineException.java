package src.br.nexus.exception;

public class ServidorOfflineException extends RuntimeException {
    public ServidorOfflineException(String mensagem) {
        super(mensagem);
    }
}