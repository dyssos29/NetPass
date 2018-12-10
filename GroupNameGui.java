import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class GroupNameGui extends JFrame
{
    private JPanel mainPanel;
    private JLabel groupNameLabel;
    private JTextField groupNameText;
    private JButton okButton;
    private JButton cancelButton;
    private JFrame thisFrame;
    private GridBagConstraints gbConstrains;
    private ChatGui chatGuiWindow;

    public GroupNameGui(ChatGui chatGui)
    {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                drawFrame();
                constructMainPanel();
                addListeners();
                chatGuiWindow = chatGui;
            }
        });
    }

    private void drawFrame()
    {
        setTitle("GROUP");
        setSize(300,250);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        mainPanel = new JPanel(new GridBagLayout());
        add(mainPanel);
        thisFrame = this;
    }

    private void addToMainPanel(int inset, int gridx, int gridy, JComponent componentObject)
    {
        gbConstrains = new GridBagConstraints();
        gbConstrains.gridwidth = 1;
        gbConstrains.fill = GridBagConstraints.HORIZONTAL;
        gbConstrains.insets = new Insets(inset,inset,inset,inset);
        gbConstrains.gridx = gridx;
        gbConstrains.gridy = gridy;
        mainPanel.add(componentObject,gbConstrains);
    }

    private void constructMainPanel()
    {
        groupNameLabel = new JLabel("Group name");
        addToMainPanel(10,0,0,groupNameLabel);

        groupNameText = new JTextField();
        addToMainPanel(10,1,0,groupNameText);

        okButton = new JButton("OK");
        addToMainPanel(20,0,1,okButton);

        cancelButton = new JButton("Cancel");
        addToMainPanel(20,1,1,cancelButton);
    }

    private void addListeners()
    {
        okButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(checkIfInputCorrect())
                {
                    chatGuiWindow.setGroupName(groupNameText.getText());
                    dispose();
                }
                else
                    JOptionPane.showMessageDialog(thisFrame, "Please fill the field.", "Input error", JOptionPane.ERROR_MESSAGE);
            }
        });

        cancelButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    private boolean checkIfInputCorrect()
    {
        boolean isCorrect = true;

        if (groupNameText.getText().isEmpty())
            isCorrect = false;

        return isCorrect;
    }
}
