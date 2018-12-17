import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class ChatGui extends JFrame
{
    private JPanel mainPanel;
    private JPanel leftPanel;
    private JPanel buttonPanel;
    private GridBagConstraints gridConstrainsMain;
    private GridBagConstraints gridConstrainsLeft;
    private JLabel recipientLabel;
    private JLabel userLabel;
    private String recipientLabelString;
    private String recipient;
    private JButton createGroupButton;
    private JButton sendMessageButton;
    private JButton closeButton;
    private JButton logoutButton;
    private JButton removeUserButton;
    private JButton deleteGroupButton;
    private JButton joinGroupButton;
    private DefaultListModel<String> messageList;
    private JList messageListGui;
    private JScrollPane scrollPanelMessages;
    private JList userListGui;
    private DefaultListModel<String> userList;
    private JScrollPane scrollPanelUsers;
    private JTree tree;
    private DefaultMutableTreeNode rootGroups;
    private DefaultMutableTreeNode rootUsers;
    private DefaultMutableTreeNode selectionNodeVariable = null;
    private DefaultTreeModel dmGroups;
    private DefaultTreeModel dmUsers;
    private JScrollPane scrollPanelGroups;
    private JTextField messageText;
    private LoginGui loginGui;
    private GroupGui groupGui;
    private ChatGui thisFrame;
    private ArrayList<DefaultMutableTreeNode> usersInGroup;
    private Client client;

    public ChatGui(Client client)
    {
        this.client = client;
        client.setGui(this);

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
        setSize(600,580);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(true);
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

        String username = "username";
        userLabel = new JLabel("User: " + username);
        addToLeftPanel(1,0,0,0,userLabel);

        recipientLabelString = "Message to: ";
        recipientLabel = new JLabel(recipientLabelString);
        addToLeftPanel(1,0,0,1, recipientLabel);

        messageList = new DefaultListModel<String>();
        messageListGui = new JList(messageList);
        scrollPanelMessages = new JScrollPane(messageListGui);
        addToLeftPanel(2,170,0,2,scrollPanelMessages);

        createGroupButton = new JButton("+");
        addToLeftPanel(1,0,1,1,createGroupButton);
    }

    private void constructMainPanel()
    {
        Dimension userPanelDimension = new Dimension(259,131);

        addToMainPanel(2,3,0,0,leftPanel);

        rootUsers = new DefaultMutableTreeNode();
        rootUsers.add(new DefaultMutableTreeNode("Online users"));
        dmUsers = new DefaultTreeModel(rootUsers);
        tree = new JTree(dmUsers);
        tree.setRootVisible(false);
        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);

        setLeafIconOfTree("userIcon.png",tree);
        scrollPanelUsers = new JScrollPane(tree);
        scrollPanelUsers.setPreferredSize(userPanelDimension);
        addToMainPanel(1,1,2,0,scrollPanelUsers);

        rootGroups = new DefaultMutableTreeNode();
        rootGroups.add(new DefaultMutableTreeNode("Your groups"));
        dmGroups = new DefaultTreeModel(rootGroups);
        tree = new JTree(dmGroups);
        tree.setRootVisible(false);
        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);

        setLeafIconOfTree("userIcon.png",tree);
        setParentIconOfTree("groupIcon.png",tree);
        scrollPanelGroups = new JScrollPane(tree);
        scrollPanelGroups.setPreferredSize(userPanelDimension);
        addToMainPanel(1,1,2,1,scrollPanelGroups);

        logoutButton = new JButton("Logout");
        addToMainPanel(1,1,2,3,logoutButton);

        messageText = new JTextField();
        addToMainPanel(1,1,0,3,messageText);

        sendMessageButton = new JButton("Send");
        addToMainPanel(1,1,1,3,sendMessageButton);

        closeButton = new JButton("Close");
        addToMainPanel(1,1,2,4,closeButton);


        buttonPanel = new JPanel();
        buttonPanel.setPreferredSize(new Dimension(100,35));
        addToMainPanel(1,1,2,2,buttonPanel);

        deleteGroupButton = new JButton("Delete group");
        removeUserButton = new JButton("Remove user");
        joinGroupButton = new JButton("Join group");
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
                    //client.sendPrivateMessage();
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

        removeUserButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dmGroups.removeNodeFromParent(selectionNodeVariable);
            }
        });

        tree.addTreeSelectionListener(new TreeSelectionListener() {
            public void valueChanged(TreeSelectionEvent e) {
                DefaultMutableTreeNode selectionNode = (DefaultMutableTreeNode)tree.getLastSelectedPathComponent();

                selectionNodeVariable = selectionNode;
                /* if nothing is selected */
                if (selectionNode == null) return;

                /* retrieve the node that was selected */
                Object nodeInfo = selectionNode.getUserObject();
                buttonPanel.removeAll();
                /* React to the node selection. */
                if (!(nodeInfo.equals("Me") || nodeInfo.equals("Your groups")))
                {
                    recipient = selectionNode.getUserObject().toString();
                    recipientLabel.setText(recipientLabelString + recipient);

                    if (selectionNode.isLeaf())
                        buttonPanel.add(removeUserButton);
                    else
                    {
                        //buttonPanel.remove(hiddenButton);
                    }
                }
                buttonPanel.repaint();
            }
        });
    }

    public void setGroupName(String name)
    {
        DefaultMutableTreeNode insideNode2 = new DefaultMutableTreeNode(name);
        for(DefaultMutableTreeNode node: usersInGroup)
            insideNode2.add(node);

        dmGroups.insertNodeInto(insideNode2, rootGroups, rootGroups.getChildCount());
    }

    public void addUserInGroup(String userName)
    {
        usersInGroup = new ArrayList<DefaultMutableTreeNode>();
        usersInGroup.add(new DefaultMutableTreeNode("Me"));
        usersInGroup.add(new DefaultMutableTreeNode(userName));
    }

    private void setLeafIconOfTree(String path, JTree tree)
    {
        DefaultTreeCellRenderer renderer = (DefaultTreeCellRenderer) tree.getCellRenderer();
        ImageIcon tempIcon = new ImageIcon(path);
        Image img = tempIcon.getImage();
        Image newImg = img.getScaledInstance(renderer.getLeafIcon().getIconWidth(),renderer.getLeafIcon().getIconWidth(), Image.SCALE_SMOOTH);
        renderer.setLeafIcon(new ImageIcon(newImg));
    }

    private void setParentIconOfTree(String path, JTree tree)
    {
        DefaultTreeCellRenderer renderer = (DefaultTreeCellRenderer) tree.getCellRenderer();
        ImageIcon tempIcon = new ImageIcon(path);
        Image img = tempIcon.getImage();
        Image newImg = img.getScaledInstance(renderer.getLeafIcon().getIconWidth(),renderer.getLeafIcon().getIconWidth(), Image.SCALE_SMOOTH);
        renderer.setOpenIcon(new ImageIcon(newImg));
        renderer.setClosedIcon(new ImageIcon(newImg));
    }

    public void updateClients(String[] clients)
    {
        DefaultMutableTreeNode insideNode2;
        for (String client: clients)
        {
            insideNode2 = new DefaultMutableTreeNode(client);
            dmUsers.insertNodeInto(insideNode2, rootUsers, rootUsers.getChildCount());
        }
    }
}