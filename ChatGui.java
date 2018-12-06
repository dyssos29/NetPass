import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class ChatGui extends JFrame
{
    private JPanel mainPanel;
    private JPanel leftPanel;
    private GridBagConstraints gridConstrainsMain;
    private GridBagConstraints gridConstrainsLeft;
    private JLabel recipient;
    private JButton createGroupButton;
    private JButton sendMessageButton;
    private JButton closeButton;
    private JButton logoutButton;
    private DefaultListModel<String> messageList;
    private JList messageListGui;
    private JScrollPane scrollPanelMessages;
    private JList userListGui;
    private DefaultListModel<String> userList;
    private JScrollPane scrollPanelUsers;
    private JList groupListGui;
    private DefaultListModel<String> groupList;
    private JScrollPane scrollPanelGroups;
    private JTextField messageText;
    private LoginGui loginGui;


    public ChatGui()
    {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                drawFrame();
                constructLeftPanel();
                constructMainPanel();
                addListeners();
            }
        });
    }

    private void drawFrame()
    {
        setTitle("NetPass");
        setSize(600,500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        mainPanel = new JPanel(new GridBagLayout());
        add(mainPanel);
    }

    private void addToLeftPanel(int gridWidth, int ipady, int gridx, int gridy, JComponent componentObject)
    {
        gridConstrainsLeft = new GridBagConstraints();
        gridConstrainsLeft.gridwidth = gridWidth;
        gridConstrainsLeft.ipady = ipady;
        gridConstrainsLeft.insets = new Insets(10,10,10,10);
        gridConstrainsLeft.gridx = gridx;
        gridConstrainsLeft.gridy = gridy;
        leftPanel.add(componentObject, gridConstrainsLeft);
    }

    private void addToMainPanel(int gridWidth, int gridHeight, int gridx, int gridy, JComponent componentObject)
    {
        gridConstrainsMain = new GridBagConstraints();
        gridConstrainsMain.gridwidth = gridWidth;
        gridConstrainsMain.gridheight = gridHeight;
        gridConstrainsMain.weightx = 0.5;
        gridConstrainsMain.weighty = 0.5;
        gridConstrainsMain.fill = GridBagConstraints.HORIZONTAL;
        gridConstrainsMain.insets = new Insets(10,10,10,10);
        gridConstrainsMain.gridx = gridx;
        gridConstrainsMain.gridy = gridy;
        mainPanel.add(componentObject, gridConstrainsMain);
    }

    private void constructLeftPanel()
    {
        leftPanel = new JPanel(new GridBagLayout());

        recipient = new JLabel("Message to: recipient");
        addToLeftPanel(1,0,0,0,recipient);

        messageList = new DefaultListModel<String>();
        messageListGui = new JList(messageList);
        scrollPanelMessages = new JScrollPane(messageListGui);
        addToLeftPanel(2,190,0,1,scrollPanelMessages);

        createGroupButton = new JButton("+");
        addToLeftPanel(1,0,1,0,createGroupButton);
    }

    private void constructMainPanel()
    {
        addToMainPanel(2,3,0,0,leftPanel);

        userList = new DefaultListModel<String>();
        userListGui = new JList(userList);
        userListGui.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        scrollPanelUsers = new JScrollPane(userListGui);
        addToMainPanel(1,1,2,0,scrollPanelUsers);

        groupList = new DefaultListModel<String>();
        groupListGui = new JList(groupList);
        groupListGui.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        scrollPanelGroups = new JScrollPane(groupListGui);
        addToMainPanel(1,1,2,1,scrollPanelGroups);

        logoutButton = new JButton("Logout");
        addToMainPanel(1,1,2,2,logoutButton);

        messageText = new JTextField();
        addToMainPanel(1,1,0,3,messageText);

        sendMessageButton = new JButton("Send");
        addToMainPanel(1,1,1,3,sendMessageButton);

        closeButton = new JButton("Close");
        addToMainPanel(1,1,2,3,closeButton);
    }

    private void addListeners()
    {
        logoutButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loginGui = new LoginGui();
                loginGui.setVisible(true);
                setVisible(false);
            }
        });

        closeButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }
}