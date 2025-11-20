
import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class Main extends JFrame {
    private Font font;
    private JPanel panelArbol;
    private JTextField texto;
    private RBT<Integer> rbt = new RBT<>();
    
    public Main() {
        super("graficar RBT");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(screenSize);
        setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);
        getContentPane().setBackground(Color.BLUE);
        setLocationRelativeTo(null); // Centrar
        font = new Font("Arial", Font.BOLD, 50);
        setFont(font);

        //Panel para graficar el arbol
        panelArbol = new JPanel();
        panelArbol.setBackground(Color.WHITE);
        this.add(panelArbol,BorderLayout.CENTER);

        //Botones de insertar y area de texto
        JPanel panel = new JPanel(); // por defecto FlowLayout
        panel.setLayout(new GridLayout(1,2));
        this.add(panel,BorderLayout.NORTH);
        JButton boton = new JButton("Insertar");
        boton.setFont(font);
        boton.addActionListener(e -> this.dibujar(Color.RED));        
        texto = new JTextField(40);
        texto.setFont(font);
        texto.setHorizontalAlignment(JTextField.CENTER);
        panel.add(texto);
        panel.add(boton);
        setVisible(true);
    }

    public void calcularPosiciones(Nodo<Integer> nodo, int nivel, Map<Nodo<Integer>, Point> posiciones, int ancho, int alto, int x, int aa) {
        if (nodo == null) return;
        int distancia = ancho / nivel / 2; //espacio entre nodos
        int y =  alto / aa * nivel; // Espaciado vertical
        posiciones.put(nodo, new Point(x, y));
        calcularPosiciones(nodo.left, nivel + 1, posiciones, ancho, alto, x-distancia, aa);
        calcularPosiciones(nodo.right, nivel + 1, posiciones, ancho, alto, x+distancia, aa);
    }


    public void dibujar(Color color) {
        int value = Integer.parseInt(texto.getText());
        rbt.insert(value);        
        int aa = (int) Math.ceil(Math.log(rbt.size) / Math.log(2));
        aa = Math.max(aa, 1) + 2;
        Map<Nodo<Integer>, Point> posiciones = new HashMap<>();
        calcularPosiciones(rbt.root, 1, posiciones, 1000, 1000, 500, aa);
        for (Map.Entry<Nodo<Integer>, Point> iterable_element : posiciones.entrySet()) {
            Nodo<Integer> nodo = (Nodo<Integer>) iterable_element.getKey();
            Point p = (Point) iterable_element.getValue();
            System.out.println("Nodo: " + nodo.elemento + " Posicion: (" + p.x + ", " + p.y + ")");
            
        }
        
        // reemplazar el panel centro por uno que pinte un círculo centrado
        getContentPane().remove(panelArbol);
        JPanel dibujo = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                int diameter = 100; // tamaño del círculo
                for (Map.Entry<Nodo<Integer>, Point> iterable_element : posiciones.entrySet()) {
                    Nodo<Integer> nodo = (Nodo<Integer>) iterable_element.getKey();
                    Point p = (Point) iterable_element.getValue();
                    //System.out.println("Nodo: " + nodo.elemento + " Posicion: (" + p.x + ", " + p.y + ")");
                    int x = p.x;
                    int y = p.y;
                    g2.setColor(color);
                    g2.fillOval(x, y, diameter, diameter);
                    g2.setColor(Color.BLACK);
                    g2.setStroke(new BasicStroke(2));
                    g2.drawOval(x, y, diameter, diameter);
                    g2.setFont(font);
                    String text = "" + nodo.elemento;
                    FontMetrics fm = g2.getFontMetrics();
                    int textX = x + (diameter - fm.stringWidth(text)) / 2;
                    int textY = y + ((diameter - fm.getHeight()) / 2) + fm.getAscent();
                    g2.drawString(text, textX, textY);
                }
                
            }
        };
        dibujo.setBackground(Color.WHITE);
        this.add(dibujo, BorderLayout.CENTER);
        revalidate();
        repaint();
        }
public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Main();
            });
    }
}