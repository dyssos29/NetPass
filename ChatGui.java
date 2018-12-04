import javax.swing.*;
import java.awt.*;

public class ChatGui extends JFrame
{
//    private JPanel usersPanel;
//    private JPanel centerPanel;
//
//    private JList userListGui;
//    private DefaultListModel<String> userList;
//    private JScrollPane scrollPanelUsers;
//
//    private JList groupListGui;
//    private DefaultListModel<String> groupList;
//    private JScrollPane scrollPanelGroups;
//
//    private JList messageListGui;
//    private DefaultListModel<String> messageList;
//
//    public ChatGui()
//    {
//        setTitle("NetPass");
//        setSize(600,500);
//        setDefaultCloseOperation(EXIT_ON_CLOSE);
//        setResizable(false);
//        setLocationRelativeTo(null);
//
//        usersPanel = new JPanel();
//        usersPanel.setPreferredSize(new Dimension(getWidth()/3,getHeight()));
//        centerPanel = new JPanel();
//
//        centerPanel.setBorder(BorderFactory.createLineBorder(Color.black));
////        Border border = centerPanel.getBorder();
////        Border margin = new EmptyBorder(40,40,40,40);
////        centerPanel.setBorder(new CompoundBorder(border, margin));
//
//        userList = new DefaultListModel<String>();
//        userListGui = new JList(userList);
//        userListGui.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
//        scrollPanelUsers = new JScrollPane(userListGui);
//        scrollPanelUsers.setPreferredSize(new Dimension((int)usersPanel.getPreferredSize().getWidth(),(int)usersPanel.getPreferredSize().getHeight()/3));
//
//        groupList = new DefaultListModel<String>();
//        groupListGui = new JList(groupList);
//        groupListGui.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
//        scrollPanelGroups = new JScrollPane(groupListGui);
//        scrollPanelGroups.setBorder(new EmptyBorder(40,40,40,40));
//        scrollPanelGroups.setPreferredSize(new Dimension((int)usersPanel.getPreferredSize().getWidth(),(int)usersPanel.getPreferredSize().getHeight()/3));
//
//
//        messageList = new DefaultListModel<String>();
//        messageListGui = new JList(messageList);
//        messageListGui.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
//
//        usersPanel.add(scrollPanelUsers,BorderLayout.CENTER);
//        usersPanel.add(scrollPanelGroups,BorderLayout.PAGE_END);
//        centerPanel.add(messageListGui,BorderLayout.PAGE_END);
//        add(centerPanel, BorderLayout.CENTER);
//        add(usersPanel, BorderLayout.LINE_END);
//    }
    private JPanel mainPanel;
    private GridBagConstraints gConstraints;
    private JLabel recipient;
    private JButton createGroupButton;
    private JList messageListGui;
    private DefaultListModel<String> messageList;
    private JScrollPane scrollPanelMessages;

    public ChatGui()
    {
        setTitle("NetPass");
        setSize(600,500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);

        mainPanel = new JPanel(new GridBagLayout());

        gConstraints = new GridBagConstraints();
        gConstraints.gridx = 0;
        gConstraints.gridy = 0;
        recipient = new JLabel("Message to: recipient");
        mainPanel.add(recipient,gConstraints);

        createGroupButton = new JButton("+");
        gConstraints.gridx = 1;
        gConstraints.gridy = 0;
        mainPanel.add(createGroupButton,gConstraints);


        messageList = new DefaultListModel<String>();
        messageListGui = new JList(messageList);
        scrollPanelMessages = new JScrollPane(messageListGui);
        gConstraints.gridx = 0;
        gConstraints.gridy = 1;
        mainPanel.add(scrollPanelMessages,gConstraints);

        add(mainPanel);
    }
}