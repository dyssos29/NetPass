import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class LoginGui extends JFrame
{
    private JPanel mainPanel;
    private JLabel aliasLabel;
    private JLabel passwordLabel;
    private JButton loginButton;
    private JButton closeButton;
    private JTextField aliasText;
    private JPasswordField password;
    private GridBagConstraints gbConstrains;
    private ChatGui chatGuiWindow;
    private JFrame thisFrame;

    public LoginGui()
    {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                drawFrame();
                constructMainPanel();
                addListeners();
            }
        });
    }

    private void drawFrame()
    {
        setTitle("LOGIN");
        setSize(300,250);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        mainPanel = new JPanel(new GridBagLayout());
        add(mainPanel);
        thisFrame = this;
    }

    private void addToMainPanel(int weightx, int gridx, int gridy, int inset, JComponent componentObject)
    {
        gbConstrains = new GridBagConstraints();
        gbConstrains.weightx = weightx;
        gbConstrains.fill = GridBagConstraints.HORIZONTAL;
        gbConstrains.insets = new Insets(inset,inset,inset,inset);
        gbConstrains.gridx = gridx;
        gbConstrains.gridy = gridy;
        mainPanel.add(componentObject,gbConstrains);
    }

    private void constructMainPanel()
    {
        aliasLabel = new JLabel("Alias");
        addToMainPanel(1,0,0, 10,aliasLabel);

        aliasText = new JTextField();
        addToMainPanel(1,1,0,10,aliasText);

        passwordLabel = new JLabel("Password");
        addToMainPanel(0,0,1,10,passwordLabel);

        password = new JPasswordField();
        addToMainPanel(0,1,1,10,password);

        loginButton = new JButton("Login");
        addToMainPanel(0,0,2,20,loginButton);

        closeButton = new JButton("Close");
        addToMainPanel(0,1,2,20,closeButton);
    }

    private void addListeners()
    {
        loginButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(checkIfInputsCorrect())
                {
                    chatGuiWindow = new ChatGui();
                    chatGuiWindow.setVisible(true);
                    setVisible(false);
                }
                else
                    JOptionPane.showMessageDialog(thisFrame, "Please fill both of the fields.", "Input error", JOptionPane.ERROR_MESSAGE);
            }
        });

        closeButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

    private boolean checkIfInputsCorrect()
    {
        boolean isCorrect = true;

        if (aliasText.getText().isEmpty() || password.getPassword().length == 0)
            isCorrect = false;

        return isCorrect;
    }
}
