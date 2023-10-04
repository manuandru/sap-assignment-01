package mvc_01_basic;

import io.reactivex.rxjava3.disposables.Disposable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MyView {

    private final MyFrame frame;
    private final Disposable unsubscriber; // call me to unsubscribe!

    public MyView(ModelInterface model) {
        frame = new MyFrame(model.getState());
        this.unsubscriber = model.onVariation().subscribe(v -> {
            log("model updated => updating the view");
            frame.updateView(v);
        });
    }

    public void display() {
        SwingUtilities.invokeLater(() -> {
            frame.setVisible(true);
        });
    }

    private void log(String msg) {
        System.out.println("[View] " + msg);
    }

    class MyFrame extends JFrame {

        private final JTextField state;

        public MyFrame(int initState) {
            super("My View");

            setSize(300, 70);
            setResizable(false);

            state = new JTextField(10);
            state.setText("" + initState);

            JPanel panel = new JPanel();
            panel.add(state);

            setLayout(new BorderLayout());
            add(panel, BorderLayout.NORTH);

            addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent ev) {
                    System.exit(-1);
                }
            });
        }

        public void updateView(int newState) {
            try {
                SwingUtilities.invokeLater(() -> {
                    state.setText("" + newState);
                });
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

}
