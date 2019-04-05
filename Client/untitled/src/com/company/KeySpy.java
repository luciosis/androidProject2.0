package com.company;

	import java.awt.event.KeyEvent;
    import java.awt.event.KeyListener;

    import javax.swing.JFrame;
   import javax.swing.JLabel;


        public class KeySpy {
            JLabel label=new JLabel("Enter the key");
            public KeySpy() {
                JFrame frame=new JFrame("KeySpy");
                frame.add(label);

                frame.addKeyListener(new KeyListener() {

                    @Override
                    public void keyTyped(KeyEvent e) {
                    }

                    @Override
                    public void keyReleased(KeyEvent e) {
                    }

                    @Override
                    public void keyPressed(KeyEvent e) {
                        label.setText(e.toString());
                        System.out.println(e.toString());
                    }
                });

                frame.setSize(200, 200);
                frame.setVisible(true);
            }

            public static void main(String[] args) {
                new KeySpy();

            }

        }
