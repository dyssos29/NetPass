import javax.swing.*;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreePath;
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
//    private DefaultListModel<String> groupList;
    private JTree tree;
    private DefaultMutableTreeNode root;
    private JScrollPane scrollPanelGroups;
    private JTextField messageText;
    private LoginGui loginGui;
    private GroupGui groupGui;
    private ChatGui thisFrame;

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

//        groupList = new DefaultListModel<String>();
        String groupStr = "group test";
        DefaultListModel<String> listModel = new DefaultListModel<String>();
        listModel.addElement(groupStr);
        groupListGui = new JList(listModel);
        groupListGui.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Groups");
        root.add(new DefaultMutableTreeNode("Child1"));
        root.add(new DefaultMutableTreeNode("Child1"));
        root.add(new DefaultMutableTreeNode("Child1"));
        root.add(new DefaultMutableTreeNode("Child1"));
        root.add(new DefaultMutableTreeNode("Child1"));
        root.add(new DefaultMutableTreeNode("Child1"));
        root.add(new DefaultMutableTreeNode("Child1"));
        DefaultMutableTreeNode insideNode = new DefaultMutableTreeNode("ChildParent");
        insideNode.add(new DefaultMutableTreeNode("Inside child"));
        root.add(insideNode);
        tree = new JTree(root);

        DefaultTreeCellRenderer renderer = (DefaultTreeCellRenderer) tree.getCellRenderer();
        //Icon closedIcon = new ImageIcon("closed.png");
        //Icon openIcon = new ImageIcon("open.png");
        Icon leafIcon = new ImageIcon("userIcon.png");
        //renderer.setClosedIcon(closedIcon);
        //renderer.setOpenIcon(openIcon);
        renderer.setLeafIcon(leafIcon);

        scrollPanelGroups = new JScrollPane(tree);
//        tree = new JTree(new DefaultMutableTreeNode("tree test2"));
//        scrollPanelGroups.setViewportView(tree);
        scrollPanelGroups.setPreferredSize(scrollPanelMessages.getPreferredSize());
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
        thisFrame = this;

        sendMessageButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!messageText.getText().isEmpty())
                {
                    messageList.addElement("You: " + messageText.getText());
                    messageText.setText("");
                }
            }
        });

        logoutButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loginGui = new LoginGui();
                loginGui.setVisible(true);
                setVisible(false);
            }
        });

        createGroupButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                groupGui = new GroupGui(thisFrame);
                groupGui.setVisible(true);
            }
        });

        closeButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

    public void setGroupName(String name)
    {
        tree = new JTree(new DefaultMutableTreeNode(name));
        groupListGui.add(tree);
    }
}