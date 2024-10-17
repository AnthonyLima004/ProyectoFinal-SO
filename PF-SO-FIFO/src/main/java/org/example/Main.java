package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class Proceso {
    int id;
    int tiempoLlegada;
    int rafagaCPU;
    int tiempoEspera;
    int tiempoRetorno;

    public Proceso(int id, int tiempoLlegada, int rafagaCPU) {
        this.id = id;
        this.tiempoLlegada = tiempoLlegada;
        this.rafagaCPU = rafagaCPU;
        this.tiempoEspera = 0;
        this.tiempoRetorno = 0;
    }
}

public class Main {
    public static void main(String[] args) {
        // Llamar a la ventana de inicio
        mostrarVentanaInicio();
    }

    public static void mostrarVentanaInicio() {
        // Crear la ventana de inicio
        JFrame frameInicio = new JFrame("Algoritmo FIFO, Grupo No.5");
        frameInicio.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameInicio.setSize(400, 300);
        frameInicio.setLayout(new BorderLayout());

        // Título en la parte superior
        JLabel titulo = new JLabel("Algoritmo FIFO, Grupo No.5", SwingConstants.CENTER);
        titulo.setFont(new Font("Serif", Font.BOLD, 24));
        frameInicio.add(titulo, BorderLayout.NORTH);

        // Botón de iniciar
        JButton btnIniciar = new JButton("Iniciar");
        btnIniciar.setFont(new Font("Arial", Font.PLAIN, 18));
        frameInicio.add(btnIniciar, BorderLayout.CENTER);

        // Fondo del programa
        JPanel panelCentral = new JPanel();
        panelCentral.setBackground(new Color(173, 216, 230));  // Azul claro
        frameInicio.add(panelCentral, BorderLayout.CENTER);
        panelCentral.setLayout(new BorderLayout());
        panelCentral.add(btnIniciar, BorderLayout.CENTER);

        // Acción del botón Iniciar
        btnIniciar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Cerrar la ventana de inicio y abrir el simulador
                frameInicio.dispose();
                mostrarSimulador();
            }
        });

        // Mostrar la ventana
        frameInicio.setVisible(true);
    }

    public static void mostrarSimulador() {
        // Crear el marco principal de la interfaz del simulador
        JFrame frame = new JFrame("Simulador FIFO");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);

        // Panel principal con un borde y fondo
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setBackground(new Color(240, 248, 255));  // Color de fondo: azul muy claro
        panelPrincipal.setLayout(new BorderLayout());
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        frame.add(panelPrincipal);

        // Título del simulador
        JLabel tituloSimulador = new JLabel("Simulador FIFO", SwingConstants.CENTER);
        tituloSimulador.setFont(new Font("Serif", Font.BOLD, 26));
        tituloSimulador.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        panelPrincipal.add(tituloSimulador, BorderLayout.NORTH);

        // Panel central con campos de texto y etiquetas
        JPanel panelCentro = new JPanel();
        panelCentro.setLayout(new BoxLayout(panelCentro, BoxLayout.Y_AXIS));
        panelCentro.setBackground(new Color(240, 248, 255));  // Fondo igual para el panel
        panelPrincipal.add(panelCentro, BorderLayout.CENTER);

        // Campo para ingresar el número de procesos
        JPanel panelNumProcesos = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelNumProcesos.setBackground(new Color(240, 248, 255));
        JLabel labelNumProcesos = new JLabel("Número de procesos:");
        labelNumProcesos.setFont(new Font("Arial", Font.PLAIN, 16));
        JTextField textNumProcesos = new JTextField(10);
        panelNumProcesos.add(labelNumProcesos);
        panelNumProcesos.add(textNumProcesos);
        panelCentro.add(panelNumProcesos);

        // Botón para ingresar los detalles de cada proceso
        JButton btnIngresarProcesos = new JButton("Ingresar procesos");
        btnIngresarProcesos.setFont(new Font("Arial", Font.PLAIN, 16));
        panelCentro.add(btnIngresarProcesos);

        // Área para mostrar los resultados
        JTextArea textAreaResultados = new JTextArea(10, 30);
        textAreaResultados.setEditable(false);
        textAreaResultados.setFont(new Font("Arial", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(textAreaResultados);
        panelCentro.add(scrollPane);

        // Panel inferior para el botón y diseño
        JPanel panelBoton = new JPanel();
        panelBoton.setBackground(new Color(240, 248, 255));
        panelBoton.setLayout(new FlowLayout(FlowLayout.RIGHT));
        panelPrincipal.add(panelBoton, BorderLayout.SOUTH);

        // Acción del botón Ingresar Procesos
        btnIngresarProcesos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int numProcesos = Integer.parseInt(textNumProcesos.getText());
                Proceso[] procesos = new Proceso[numProcesos];

                // Crear un nuevo diálogo para ingresar los detalles de cada proceso
                for (int i = 0; i < numProcesos; i++) {
                    JTextField tiempoLlegadaField = new JTextField();
                    JTextField rafagaCPUField = new JTextField();
                    Object[] message = {
                            "Proceso " + (i + 1),
                            "Tiempo de llegada:", tiempoLlegadaField,
                            "Ráfaga de CPU:", rafagaCPUField,
                    };

                    int option = JOptionPane.showConfirmDialog(null, message, "Ingresar datos del proceso",
                            JOptionPane.OK_CANCEL_OPTION);
                    if (option == JOptionPane.OK_OPTION) {
                        int tiempoLlegada = Integer.parseInt(tiempoLlegadaField.getText());
                        int rafagaCPU = Integer.parseInt(rafagaCPUField.getText());
                        procesos[i] = new Proceso(i + 1, tiempoLlegada, rafagaCPU);
                    }
                }

                // Ejecutar el algoritmo FIFO y mostrar los resultados
                int tiempoActual = 0;
                int sumaEspera = 0;
                int sumaRetorno = 0;

                StringBuilder resultados = new StringBuilder();
                resultados.append("Orden de ejecución de los procesos:\n");

                for (int i = 0; i < numProcesos; i++) {
                    Proceso proceso = procesos[i];

                    // Si el CPU está inactivo, avanzar el tiempo hasta la llegada del proceso
                    if (tiempoActual < proceso.tiempoLlegada) {
                        tiempoActual = proceso.tiempoLlegada;
                    }

                    // Calcular el tiempo de espera (tiempo actual - tiempo de llegada)
                    proceso.tiempoEspera = tiempoActual - proceso.tiempoLlegada;
                    sumaEspera += proceso.tiempoEspera;

                    // Calcular el tiempo de retorno (tiempo actual + ráfaga de CPU - tiempo de llegada)
                    proceso.tiempoRetorno = proceso.tiempoEspera + proceso.rafagaCPU;
                    sumaRetorno += proceso.tiempoRetorno;

                    // Actualizar el tiempo actual
                    tiempoActual += proceso.rafagaCPU;

                    // Añadir resultados a la cadena
                    resultados.append("Proceso " + proceso.id + " ejecutado.\n");
                }

                resultados.append("\nTiempos de espera y retorno para cada proceso:\n");
                for (Proceso proceso : procesos) {
                    resultados.append("Proceso " + proceso.id + " -> Tiempo de espera: " + proceso.tiempoEspera
                            + ", Tiempo de retorno: " + proceso.tiempoRetorno + "\n");
                }

                // Calcular tiempos medios
                double tiempoMedioEspera = (double) sumaEspera / numProcesos;
                double tiempoMedioRetorno = (double) sumaRetorno / numProcesos;

                resultados.append("\nTiempo medio de espera: " + tiempoMedioEspera + "\n");
                resultados.append("Tiempo medio de retorno: " + tiempoMedioRetorno + "\n");

                // Mostrar resultados en el área de texto
                textAreaResultados.setText(resultados.toString());
            }
        });

        // Mostrar la ventana
        frame.setVisible(true);
    }
}

