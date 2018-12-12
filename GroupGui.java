import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class GroupGui extends JFrame
{
    private JPanel mainPanel;
    private JLabel aliasLabel;
    private JTextField aliasText;
    private JButton nextButton;
    private JButton addToGroupButton;
    private JButton cancelButton;
    private GridBagConstraints gbConstrains;
    private JFrame thisFrame;
    private GroupNameGui groupNameGui;
    private ChatGui chatGuiWindow;

    public GroupGui(ChatGui guiObject)
    {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                drawFrame();
                constructMainPanel();
                addListeners();
                chatGuiWindow = guiObject;
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

    private void addToMainPanel(int gridWidth, int fill, int weightx, int gridx, int gridy, int inset, JComponent componentObject)
    {
        gbConstrains = new GridBagConstraints();
        gbConstrains.gridwidth = gridWidth;
        gbConstrains.weightx = weightx;
        gbConstrains.fill = fill;
        gbConstrains.insets = new Insets(inset,inset,inset,inset);
        gbConstrains.gridx = gridx;
        gbConstrains.gridy = gridy;
        mainPanel.add(componentObject,gbConstrains);
    }

    private void constructMainPanel()
    {
        aliasLabel = new JLabel("Alias");
        addToMainPanel(1,2,1,0,0,10,aliasLabel);

        aliasText = new JTextField();
        addToMainPanel(1,2,1,1,0,10,aliasText);

        addToGroupButton = new JButton("Add member");
        addToMainPanel(1,2,1,0,1,20,addToGroupButton);

        nextButton = new JButton("Next");
        addToMainPanel(1,2,1,1,1,20,nextButton);

        cancelButton = new JButton("Cancel");
        addToMainPanel(2,0,0,0,2,20, cancelButton);
    }

    private void addListeners()
    {
        addToGroupButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(checkIfInputCorrect())
                {
                    chatGuiWindow.addUserInGroup(aliasText.getText());
                    aliasText.setText("");
                }
                else
                    JOptionPane.showMessageDialog(thisFrame, "Please fill the field.", "Input error", JOptionPane.ERROR_MESSAGE);
            }
        });

        nextButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                groupNameGui = new GroupNameGui(chatGuiWindow);
                groupNameGui.setVisible(true);
                setVisible(false);
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

        if (aliasText.getText().isEmpty())
            isCorrect = false;

        return isCorrect;
    }
}
