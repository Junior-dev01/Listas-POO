package src.br.nexus.manager;

import src.br.nexus.model.Cliente;
import src.br.nexus.model.Servidor;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CloudManager {
    private Map<Cliente, Set<Servidor>> bancoDeDados = new HashMap<>();

    public void registrarCliente(Cliente c) {
        bancoDeDados.putIfAbsent(c, new HashSet<>());
    }

    public void provisionarServidor(Cliente c, Servidor s) {
        registrarCliente(c);
        bancoDeDados.get(c).add(s);
    }

    public double calcularFaturaAtiva(Cliente c) {
        Set<Servidor> servidores = bancoDeDados.get(c);
        if (servidores == null) {
            return 0.0;
        }

        return servidores.stream()
                .filter(Servidor::isStatusLigado)
                .mapToDouble(Servidor::getPrecoPorHora)
                .sum();
    }
}