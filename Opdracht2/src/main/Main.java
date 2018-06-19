package main;

import javax.swing.*;

public class Main
{
    private JFrame mainForm;

    public static void main(String[] args)
    {
        Main main = new Main();
        main.CreateScreen();
    }

    private void CreateScreen()
    {
        mainForm = new JFrame("MainForm");
        mainForm.setContentPane(new MainForm().getMainPanel());
        mainForm.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainForm.pack();
        mainForm.setLocationRelativeTo(null);
        mainForm.setVisible(true);
    }
}
