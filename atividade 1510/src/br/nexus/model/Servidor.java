package src.br.nexus.model;

import src.br.nexus.exception.CapacidadeExcedidaException;
import src.br.nexus.exception.ServidorOfflineException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Servidor {
    private String ip;
    private double ramTotal;
    private double precoPorHora;
    private boolean statusLigado;
    private List<Container> containers = new ArrayList<>();

    public Servidor(String ip, double ramTotal, double precoPorHora) {
        this.ip = ip;
        this.ramTotal = ramTotal;
        this.precoPorHora = precoPorHora;
        this.statusLigado = false;
    }

    public void ligar() {
        this.statusLigado = true;
    }

    public void desligar() {
        this.statusLigado = false;
    }

    public void alocarContainer(Container c) throws CapacidadeExcedidaException, ServidorOfflineException {
        if (!statusLigado) {
            throw new ServidorOfflineException("Erro: Não é possível alocar containers em um servidor desligado.");
        }

        double ramAtualEmUso = calcularRamEmUso();
        if ((ramAtualEmUso + c.getRamNecessaria()) > ramTotal) {
            throw new CapacidadeExcedidaException("Falha Crítica: Memória RAM insuficiente no servidor " + ip + ".");
        }

        containers.add(c);
    }

    public double calcularRamEmUso() {
        return containers.stream()
                .mapToDouble(Container::getRamNecessaria)
                .sum();
    }

    public String getIp() {
        return ip;
    }

    public double getRamTotal() {
        return ramTotal;
    }

    public double getPrecoPorHora() {
        return precoPorHora;
    }

    public boolean isStatusLigado() {
        return statusLigado;
    }

    public List<Container> getContainers() {
        return containers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Servidor servidor = (Servidor) o;
        return Objects.equals(ip, servidor.ip);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ip);
    }
}