package mvc_01_basic.components;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.PublishSubject;
import io.reactivex.rxjava3.subjects.Subject;
import mvc_01_basic.InputSource;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class InputUISource implements InputSource {

    private final Subject<Event> emitter = PublishSubject.create();
    private final MyFrame frame;

    public InputUISource() {
        frame = new MyFrame();
    }

    @Override
    public Observable<Event> onInput() {
        return emitter;
    }

    public void display() {
        SwingUtilities.invokeLater(() -> {
            frame.setVisible(true);
        });
    }

    private void log(String msg) {
        System.out.println("[InputUI] " + msg);
    }

    class MyFrame extends JFrame implements ActionListener {

        private JTextField state;

        public MyFrame() {
            super("My Input UI");

            setSize(300, 70);
            setResizable(false);

            JButton button = new JButton("Update");
            button.addActionListener(this);

            JPanel panel = new JPanel();
            panel.add(button);

            setLayout(new BorderLayout());
            add(panel, BorderLayout.NORTH);

            addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent ev) {
                    System.exit(-1);
                }
            });
        }

        public void actionPerformed(ActionEvent ev) {
            try {
                log("New input detected.");
                emitter.onNext(Event.CLICK);
            } catch (Exception ex) {
            }
        }
    }

}
