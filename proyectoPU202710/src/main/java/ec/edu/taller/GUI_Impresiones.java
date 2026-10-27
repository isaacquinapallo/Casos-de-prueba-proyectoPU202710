package ec.edu.taller;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class GUI_Impresiones {
    private static final GestorImpresiones gestor = new GestorImpresiones(10);

    private JPanel panel1;
    private JTabbedPane tabbedPane;
    private JPanel jpanel_RegistrarTrabajo;
    private JTextField txtCodigo;
    private JComboBox<String> cmbSolicitante;
    private JTextField txtDocumento;
    private JSpinner spPrioridad;
    private JButton btnRegistrarTrabajo;
    private JButton btnLimpiarElementos;
    private JTextArea txtAreaEstado;
    private JButton btnProcesar;
    private JButton btnConsultarSiguiente;
    private JButton btnListar;

    public GUI_Impresiones() {
        initComponents();
    }

    private void initComponents() {
        panel1 = new JPanel(new BorderLayout(10, 10));
        panel1.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        tabbedPane = new JTabbedPane();

        jpanel_RegistrarTrabajo = new JPanel(new BorderLayout(10, 10));
        jpanel_RegistrarTrabajo.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel formRegistro = new JPanel(new GridLayout(0, 2, 10, 10));
        JLabel lblCodigo = new JLabel("Codigo");
        txtCodigo = new JTextField();

        JLabel lblSolicitante = new JLabel("Solicitante");
        cmbSolicitante = new JComboBox<>(new String[]{"ESTUDIANTE", "DOCENTE", "ADMINISTRATIVO"});

        JLabel lblDocumento = new JLabel("Documento");
        txtDocumento = new JTextField();

        JLabel lblPrioridad = new JLabel("Prioridad");
        spPrioridad = new JSpinner(new SpinnerNumberModel(1, 1, 5, 1));

        formRegistro.add(lblCodigo);
        formRegistro.add(txtCodigo);
        formRegistro.add(lblSolicitante);
        formRegistro.add(cmbSolicitante);
        formRegistro.add(lblDocumento);
        formRegistro.add(txtDocumento);
        formRegistro.add(lblPrioridad);
        formRegistro.add(spPrioridad);

        JPanel botonesRegistro = new JPanel(new GridLayout(2, 1, 8, 8));
        btnRegistrarTrabajo = new JButton("Registrar Trabajo");
        btnLimpiarElementos = new JButton("Limpiar Elementos");
        botonesRegistro.add(btnRegistrarTrabajo);
        botonesRegistro.add(btnLimpiarElementos);

        jpanel_RegistrarTrabajo.add(formRegistro, BorderLayout.CENTER);
        jpanel_RegistrarTrabajo.add(botonesRegistro, BorderLayout.SOUTH);

        JPanel panelGestion = new JPanel(new BorderLayout(10, 10));
        panelGestion.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel panelDatos = new JPanel(new GridLayout(0, 2, 10, 10));
        panelDatos.setBorder(BorderFactory.createTitledBorder("GESTIONAR TRABAJOS"));

        JLabel lblCodigoGestion = new JLabel("Codigo");
        JLabel valorCodigo = new JLabel(" ");
        JLabel lblSolicitanteGestion = new JLabel("Solicitante");
        JLabel valorSolicitante = new JLabel(" ");
        JLabel lblDocumentoGestion = new JLabel("Documento");
        JLabel valorDocumento = new JLabel(" ");
        JLabel lblPrioridadGestion = new JLabel("Prioridad");
        JLabel valorPrioridad = new JLabel(" ");
        JLabel lblEstadoGestion = new JLabel("Estado");
        JLabel valorEstado = new JLabel(" ");

        panelDatos.add(lblCodigoGestion);
        panelDatos.add(valorCodigo);
        panelDatos.add(lblSolicitanteGestion);
        panelDatos.add(valorSolicitante);
        panelDatos.add(lblDocumentoGestion);
        panelDatos.add(valorDocumento);
        panelDatos.add(lblPrioridadGestion);
        panelDatos.add(valorPrioridad);
        panelDatos.add(lblEstadoGestion);
        panelDatos.add(valorEstado);

        txtAreaEstado = new JTextArea(10, 28);
        txtAreaEstado.setEditable(false);
        txtAreaEstado.setLineWrap(true);
        txtAreaEstado.setWrapStyleWord(true);

        JPanel panelAcciones = new JPanel(new GridLayout(3, 1, 8, 8));
        btnProcesar = new JButton("Procesar");
        btnConsultarSiguiente = new JButton("Consultar Siguiente");
        btnListar = new JButton("Listar");
        panelAcciones.add(btnProcesar);
        panelAcciones.add(btnConsultarSiguiente);
        panelAcciones.add(btnListar);

        JPanel panelCentroGestion = new JPanel(new BorderLayout(10, 10));
        panelCentroGestion.add(panelDatos, BorderLayout.CENTER);
        panelCentroGestion.add(new JScrollPane(txtAreaEstado), BorderLayout.EAST);

        panelGestion.add(panelCentroGestion, BorderLayout.CENTER);
        panelGestion.add(panelAcciones, BorderLayout.SOUTH);

        tabbedPane.addTab("REGISTRAR TRABAJO", jpanel_RegistrarTrabajo);
        tabbedPane.addTab("GESTIONAR TRABAJOS", panelGestion);

        panel1.add(tabbedPane, BorderLayout.CENTER);

        btnRegistrarTrabajo.addActionListener(e -> {
            String codigo = txtCodigo.getText() == null ? "" : txtCodigo.getText().trim();
            String solicitante = (String) cmbSolicitante.getSelectedItem();
            String documento = txtDocumento.getText() == null ? "" : txtDocumento.getText().trim();
            int prioridad = (Integer) spPrioridad.getValue();

            TrabajoImpresion trabajo = new TrabajoImpresion(codigo, solicitante, documento, prioridad);
            boolean ok = gestor.registrarTrabajo(trabajo);

            if (ok) {
                mostrarMensaje("Registrado: " + trabajo + "\nPendientes: " + gestor.contarTrabajosPendientes() + "\nEspacios libres: " + gestor.consultarEspaciosDisponibles());
            } else {
                mostrarMensaje("No se pudo registrar el trabajo. Revise código, duplicados o capacidad.");
            }
        });

        btnLimpiarElementos.addActionListener(e -> {
            txtCodigo.setText("");
            txtDocumento.setText("");
            cmbSolicitante.setSelectedIndex(0);
            spPrioridad.setValue(1);
            mostrarMensaje("Campos limpiados.");
        });

        btnConsultarSiguiente.addActionListener(e -> {
            TrabajoImpresion siguiente = gestor.consultarSiguienteTrabajo();
            if (siguiente == null) {
                mostrarMensaje("No hay trabajos pendientes.");
            } else {
                mostrarMensaje("Siguiente: " + siguiente + "\nPrioridad: " + siguiente.getPrioridad() + "\nEstado: " + siguiente.getEstado());
            }
        });

        btnProcesar.addActionListener(e -> {
            TrabajoImpresion procesado = gestor.procesarSiguienteTrabajo();
            if (procesado == null) {
                mostrarMensaje("No hay trabajos para procesar.");
            } else {
                mostrarMensaje("Procesado: " + procesado.getCodigo() + "\nEstado final: " + procesado.getEstado() + "\nPendientes: " + gestor.contarTrabajosPendientes() + "\nEspacios libres: " + gestor.consultarEspaciosDisponibles());
            }
        });

        btnListar.addActionListener(e -> {
            List<TrabajoImpresion> trabajos = gestor.listarTrabajos();
            if (trabajos.isEmpty()) {
                mostrarMensaje("La cola está vacía.");
                return;
            }

            StringBuilder sb = new StringBuilder("Lista ordenada por prioridad:\n");
            for (TrabajoImpresion t : trabajos) {
                sb.append(t).append("\n");
            }
            mostrarMensaje(sb.toString());
        });
    }

    private void mostrarMensaje(String mensaje) {
        if (txtAreaEstado != null) {
            txtAreaEstado.setText(mensaje);
        }
        JOptionPane.showMessageDialog(panel1, mensaje, "Resultado", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GUI_Impresiones gui = new GUI_Impresiones();
            JFrame frame = new JFrame("Gestor de Impresiones");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setContentPane(gui.panel1);
            frame.setSize(1000, 560);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}