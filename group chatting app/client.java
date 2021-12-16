import javax.swing.*;

import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;

public class client  implements ActionListener,Runnable{
    // declaration of reference variables
    JPanel topPanel;
    JTextField msgField;
    JButton sendButton;
    static JPanel textArea;
    static Socket socket;
    static DataInputStream dataInputStream;
    static DataOutputStream dataOutputStream;
    static String mymsg;

    static JFrame frame =new JFrame();

    // this is coming from javax.swing.box for creating vertical box layout 
    static Box vertical = Box.createVerticalBox();


    // Declaring colors
    Color purple = new Color(138,37,196);
    Color blackForBg = new Color(26,32,47);
    Color blackForMsg = new Color(57,71,101);

    client(){

        // setting title
        frame.setTitle("userOne");
        // creating topPanel
        topPanel =new JPanel();
        // setting layout to topPanel
        topPanel.setLayout(null);
        // setting background color
        topPanel.setBackground(purple);
        // setting location and size
        topPanel.setBounds(0, 0, 450, 70);
        

        // for back button
        // to load the resource resourse 
        ImageIcon loadBackIcon = new ImageIcon(ClassLoader.getSystemResource("icons/3.png"));
        // this will set proper size to images
        Image backIconSized = loadBackIcon.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
        // this will cast Image object to ImageIcon
        ImageIcon back = new ImageIcon(backIconSized);
        // placing image using label
        JLabel label1 =new JLabel(back);
        label1.setBounds(5, 17, 30, 30);
        
        
        // dp image
        ImageIcon loadDpIcon = new ImageIcon(ClassLoader.getSystemResource("icons/grp_icon.png"));
        Image dpIconSized = loadDpIcon.getImage().getScaledInstance(60, 60, Image.SCALE_DEFAULT);
        ImageIcon dp = new ImageIcon(dpIconSized);
        JLabel label2 =new JLabel(dp);
        label2.setBounds(40, 5, 60, 60);

        // for first label for displaying group name

        JLabel name = new JLabel("Chat Group");
        name.setFont(new Font("SAN_SERIF",Font.BOLD,18));
        name.setForeground(Color.white);
        name.setBounds(110, 15, 100, 18);

         // for first label for displaying group members name

        JLabel activeStatus = new JLabel("You, Nikhil, Harshdip");
        activeStatus.setFont(new Font("SAN_SERIF",Font.PLAIN,14));
        activeStatus.setForeground(Color.white);
        activeStatus.setBounds(110, 35, 110, 20);
        


        // this is for main back panel
        textArea =new JPanel();
        textArea.setBackground(blackForBg); //grayish black

        // adding scroll pane for scrollable pane
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBounds(0, 70, 431, 545);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        
        // here we will type our message
        msgField =new JTextField();

        //this will create empty border means remove border
        msgField.setBorder(BorderFactory.createEmptyBorder());
        msgField.setBackground(blackForMsg);
        msgField.setForeground(Color.white);
        msgField.setBounds(0, 615, 310, 40);
        msgField.setFont(new Font("SAN_SERIF",Font.PLAIN,16));
        
        // this is for send button
        sendButton =new JButton("send");
        sendButton.setBounds(310, 615, 123, 40);
        sendButton.setForeground(Color.WHITE);
        sendButton.setBackground(purple);
        
        //setting 'sendButton' as default button so msg will be send On pressing Enter
        frame.getRootPane().setDefaultButton(sendButton); 
        sendButton.addActionListener(this);
        
        
        // adding following component to topPanel 
        topPanel.add(label1);
        topPanel.add(label2);
        topPanel.add(name);
        topPanel.add(activeStatus);
        
         // adding following component to frame 
        frame.add(topPanel);
        frame.add(scrollPane);
        frame.add(msgField);
        frame.add(sendButton);
        
        
        frame.getContentPane().setBackground(Color.black);
        // some basic frame settings
        frame.setLayout(null);
        frame.setLocation(10, 50);
        frame.setSize(450, 700);
        frame.setVisible(true);

        // setting default close operation
        frame.setDefaultCloseOperation(3);
    }


    // Method called by send button after clicking it
    public void actionPerformed(ActionEvent ae) {

        try {
            // setting layout for mainPanel
            textArea.setLayout(new BorderLayout());

            // setting layout for right panel->right single message panel,this will take complete area
            JPanel right = new JPanel(new BorderLayout());
            
            String out = "<b style=\"color:white;\"><u>Rohit:</u></b><br>"+msgField.getText();        

            //declaring new frame ,which will be added to' right' panel
            JPanel p4 =new JPanel();
            // sending out message to 'formatLabel' function
            p4 = formatLabel(out);

            // setting color to panels
            p4.setBackground(blackForBg);//purple
            right.setBackground(blackForBg);//grayinsh black

            // adding p4 in right and setting position of p4
            right.add(p4,BorderLayout.LINE_END);

            // adding right panel to vertical
            vertical.add(right);

            // adding verticle to mainPanel
            textArea.add(vertical,BorderLayout.PAGE_START); 
            // refreshing frame after adding above message
            frame.validate();

            // clearing 'msgField' for after sending message
            msgField.setText("");

            // ***** sending message to server *****
            dataOutputStream.writeUTF(out);
            
            System.out.println("message send Successfully");
            
        } catch (Exception e) {
            System.out.println(e);
        }

    }
    //**** formatLabel() for sended message
    public static JPanel formatLabel(String out) {
        // creating local panel3
        JPanel panel3 = new JPanel();
        // creating label and putting sended message in it
        JLabel l1 = new JLabel("<html><p style=\"width:150px;\">"+out+"</p></html>");

        // setting background this is Color code for purple
        l1.setBackground(new Color(138,37,196));
        l1.setForeground(Color.WHITE);
        // setting this label as Opaque to set background color
        l1.setOpaque(true);

        // setting border for l1->this is like margin in HTML
        l1.setBorder(new EmptyBorder(15,15,15,50));

        // saving 'out' to 'mymsg' for following code
        mymsg = out;

        // adding l1 to panel3 and returning panel
        panel3.add(l1);
        return panel3;
    }
   
    // for received message ,same as above 
    public static JPanel formatLabelReceived(String out) {
        JPanel panel3 = new JPanel();
        JLabel l1 = new JLabel("<html><p style=\"width:150px;\">"+out+"</p></html>");
        l1.setBackground( new Color(57,71,101));//old color->37,211,102
        l1.setForeground(Color.WHITE);
        l1.setOpaque(true);
        l1.setBorder(new EmptyBorder(15,15,15,50));
        mymsg = out;
        panel3.add(l1);
        return panel3;
    }
   
    
    // start() internally call run method and we have to override run mtd
    public void run() {
        try {
            
            while (true) {
                // msgInput stores the message received from servers
                String msgInput = "";
                msgInput = dataInputStream.readUTF();
             
                /** this if else for : as we know our sended message go to server and server send that message to all client ,so server wil send our own sended message to us ,so I have to write the following code */


                // if sended message is our own message then message will get removed
                if (msgInput.equals(mymsg)) {
                    
                    frame.validate();
                    JPanel p2 = formatLabel(msgInput);
                    JPanel left = new JPanel(new BorderLayout());

                    left.add(p2,BorderLayout.LINE_START);
                                 
                    vertical.remove(left);
                    frame.validate();
    
                    } 

                    // else - means message is comming from other group members so this message will be added or dispalyed
                    else {
                                              
                            frame.validate();
                            //calling  'formatLabelReceived()' for receivedMethods
                            JPanel p2 = formatLabelReceived(msgInput);
                            JPanel left = new JPanel(new BorderLayout());
                            p2.setBackground(blackForBg);//black
                            left.setBackground(blackForBg);//grayinsh black
                            left.add(p2,BorderLayout.LINE_START);
                            vertical.add(left);
                            frame.validate();
                            System.out.println("Rohit : "+msgInput);
                       
                    } 
                   
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void main(String[] args) {

        // creating object of client
        client  one =new client();

        // setting 'one' as targetted thread
        Thread t1 = new Thread(one);
        // calling start this will internally call run() method
        t1.start();

        try {

            // connecting with specific server/port number
            socket = new Socket("127.0.0.1",6001);

            // this will get inputs from server
            dataInputStream = new DataInputStream(socket.getInputStream());
            // this will send inputs to server
            dataOutputStream = new DataOutputStream(socket.getOutputStream());

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}