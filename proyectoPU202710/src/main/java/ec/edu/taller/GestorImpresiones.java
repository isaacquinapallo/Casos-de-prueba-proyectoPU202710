package ec.edu.taller;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class GestorImpresiones {
    private final Queue<TrabajoImpresion> colaTrabajos;
    private final int capacidadMaxima;

    public GestorImpresiones(int capacidadMaxima) {
        if (capacidadMaxima < 0) {
            throw new IllegalArgumentException("La capacidad no puede ser negativa.");
        }
        this.capacidadMaxima = capacidadMaxima;
        this.colaTrabajos = new PriorityQueue<>(
                Comparator.comparingInt(TrabajoImpresion::getPrioridad)
        );
    }

    public boolean registrarTrabajo(TrabajoImpresion trabajo) {
        if (trabajo == null) {
            return false;
        }

        String codigo = trabajo.getCodigo();
        if (codigo == null || codigo.trim().isEmpty()) {
            return false;
        }

        String codigoNormalizado = codigo.trim();

        if (colaTrabajos.size() >= capacidadMaxima) {
            return false;
        }

        for (TrabajoImpresion pendiente : colaTrabajos) {
            if (pendiente != null && pendiente.getCodigo() != null
                    && pendiente.getCodigo().trim().equals(codigoNormalizado)) {
                return false;
            }
        }

        trabajo.setCodigo(codigoNormalizado);
        return colaTrabajos.add(trabajo);
    }

    public TrabajoImpresion consultarSiguienteTrabajo() {
        return colaTrabajos.peek();
    }

    public TrabajoImpresion procesarSiguienteTrabajo() {
        TrabajoImpresion trabajo = colaTrabajos.poll();
        if (trabajo != null) {
            trabajo.setEstado("PROCESADO");
        }
        return trabajo;
    }

    public int contarTrabajosPendientes() {
        return colaTrabajos.size();
    }

    public int consultarEspaciosDisponibles() {
        return Math.max(0, capacidadMaxima - colaTrabajos.size());
    }

    public List<TrabajoImpresion> listarTrabajos() {
        List<TrabajoImpresion> ordenada = new ArrayList<>(colaTrabajos);
        ordenada.sort(Comparator.comparingInt(TrabajoImpresion::getPrioridad));
        return Collections.unmodifiableList(ordenada);
    }
}