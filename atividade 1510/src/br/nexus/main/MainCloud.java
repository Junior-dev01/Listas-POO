package src.br.nexus.main;

import src.br.nexus.model.Cliente;
import src.br.nexus.model.Container;
import src.br.nexus.model.Servidor;
import src.br.nexus.manager.CloudManager;
import src.br.nexus.exception.ServidorOfflineException;
import src.br.nexus.exception.CapacidadeExcedidaException;

public class MainCloud {
    public static void main(String[] args) {
        CloudManager manager = new CloudManager();

        Cliente cliente = new Cliente("111.222.333-44", "Wayne Enterprises");

        Servidor servidor = new Servidor("192.168.0.1", 16.0, 5.50);

        manager.registrarCliente(cliente);
        manager.provisionarServidor(cliente, servidor);

        Container c1 = new Container("C-01", "AppA", 10.0);
        Container c2 = new Container("C-02", "AppB", 8.0);

        try {
            servidor.alocarContainer(c1);
        } catch (ServidorOfflineException e) {
            System.err.println(e.getMessage());
        } catch (CapacidadeExcedidaException e) {
            e.printStackTrace();
        }

        servidor.ligar();

        try {
            servidor.alocarContainer(c1);
            System.out.println("Container C-01 alocado com sucesso!");
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            servidor.alocarContainer(c2);
        } catch (CapacidadeExcedidaException e) {
            System.out.println("Capturado com sucesso: " + e.getMessage());
        } catch (ServidorOfflineException e) {
            System.err.println(e.getMessage());
        }

        double fatura = manager.calcularFaturaAtiva(cliente);
        System.out.println("Fatura Ativa: R$ " + fatura);
    }
}