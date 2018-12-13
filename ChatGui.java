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
    private String recipientString;
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
    private DefaultMutableTreeNode root;
    private DefaultTreeModel dm;
    private JScrollPane scrollPanelGroups;
    private JTextField messageText;
    private LoginGui loginGui;
    private GroupGui groupGui;
    private ChatGui thisFrame;
    private ArrayList<DefaultMutableTreeNode> usersInGroup;

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

        recipientString = "Message to: ";
        recipientLabel = new JLabel(recipientString);
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
        addToMainPanel(2,3,0,0,leftPanel);

        userList = new DefaultListModel<String>();
        userListGui = new JList(userList);
        userListGui.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        scrollPanelUsers = new JScrollPane(userListGui);
        addToMainPanel(1,1,2,0,scrollPanelUsers);

        root = new DefaultMutableTreeNode();
        root.add(new DefaultMutableTreeNode("Your groups"));
        dm = new DefaultTreeModel(root);
        tree = new JTree(dm);
        tree.setRootVisible(false);
        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);

        setLeafIconOfTree("userIcon.png",tree);
        setParentIconOfTree("groupIcon.png",tree);

        scrollPanelGroups = new JScrollPane(tree);
        scrollPanelGroups.setPreferredSize(scrollPanelMessages.getPreferredSize());
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

        tree.addTreeSelectionListener(new TreeSelectionListener() {
            public void valueChanged(TreeSelectionEvent e) {
                DefaultMutableTreeNode node = (DefaultMutableTreeNode)
                        tree.getLastSelectedPathComponent();

                /* if nothing is selected */
                if (node == null) return;

                /* retrieve the node that was selected */
                Object nodeInfo = node.getUserObject();

                /* React to the node selection. */
                if (!(nodeInfo.equals("Me") || nodeInfo.equals("Your groups")))
                {
                    recipientLabel.setText(recipientString + node.getUserObject());

                    if (node.isLeaf())
                    {
//                        hiddenButton = new JButton("Remove user");
//                        buttonPanel.add(hiddenButton);
                        //if (buttonPanel.getl)
                    }
                    else
                    {
                        //buttonPanel.remove(hiddenButton);
                    }
                }

            }
        });
    }

    public void setGroupName(String name)
    {
        DefaultMutableTreeNode insideNode2 = new DefaultMutableTreeNode(name);
        for(DefaultMutableTreeNode node: usersInGroup)
            insideNode2.add(node);

        dm.insertNodeInto(insideNode2, root, root.getChildCount());
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
}