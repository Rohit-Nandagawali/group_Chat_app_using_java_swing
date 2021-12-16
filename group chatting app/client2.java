import javax.swing.*;

import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;

public class client2  implements ActionListener,Runnable{
    JPanel topPanel;
    JTextField msgField;
    JButton sendButton;
    static JPanel textArea;
    static Socket socket;
    static DataInputStream dataInputStream;
    static DataOutputStream dataOutputStream;
    static String mymsg;

    static JFrame frame =new JFrame();

    static Box vertical = Box.createVerticalBox();

    // Declaring colors
    Color yellowColor = new Color(246,181,0);
    Color blackForBg = new Color(26,32,47);
    Color blackForMsg = new Color(57,71,101);



    client2(){

        frame.setTitle("userTwo");
        topPanel =new JPanel();
        topPanel.setLayout(null);
        topPanel.setBackground(yellowColor);
        topPanel.setBounds(0, 0, 450, 70);
        
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

        JLabel name = new JLabel("Chat Group");
        name.setFont(new Font("SAN_SERIF",Font.BOLD,18));
        name.setForeground(Color.white);
        name.setBounds(110, 15, 100, 18);

        JLabel activeStatus = new JLabel("You, Harshdip, Rohit");
        activeStatus.setFont(new Font("SAN_SERIF",Font.PLAIN,14));
        activeStatus.setForeground(Color.white);
        activeStatus.setBounds(110, 35, 127, 20);
        

        textArea =new JPanel();
        // textArea.setBounds(0, 70, 433, 545);
        textArea.setBackground(blackForBg);
        // textArea.setEditable(false);
        // textArea.setLineWrap(true);
        // textArea.setWrapStyleWord(true);

        // adding scroll pane for scrollable pane
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBounds(0, 70, 431, 545);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        
        msgField =new JTextField();
        msgField.setBorder(BorderFactory.createEmptyBorder());
        msgField.setBackground(blackForMsg);
        msgField.setForeground(Color.white);
        msgField.setBounds(0, 615, 310, 40);
        msgField.setFont(new Font("SAN_SERIF",Font.PLAIN,16));
        
        sendButton =new JButton("send");
        sendButton.setBounds(310, 615, 123, 40);
        sendButton.setBackground(yellowColor);
        
        frame.getRootPane().setDefaultButton(sendButton); //setting 'sendButton' as default button so msg will be send On pressing Enter
        sendButton.addActionListener(this);
        
        
        topPanel.add(label1);
        topPanel.add(label2);
        topPanel.add(name);
        topPanel.add(activeStatus);
        
        frame.add(topPanel);
        frame.add(scrollPane);
        frame.add(msgField);
        frame.add(sendButton);
        
        frame.getContentPane().setBackground(Color.black);
        frame.setLayout(null);
        frame.setLocation(470, 50);
        frame.setSize(450, 700);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(3);
    }
    public void actionPerformed(ActionEvent ae) {

        try {
            textArea.setLayout(new BorderLayout());
            JPanel right = new JPanel(new BorderLayout());
            
            String out = "<b style=\"color:white;\"><u>Nikhil:</u></b><br>"+msgField.getText();        
            // textArea.setText(textArea.getText()+"\n\t\t\t\t"+out);

            JPanel p4 =new JPanel();
            p4 = formatLabel(out);
            right.add(p4,BorderLayout.LINE_END);
            p4.setBackground(blackForBg);
            right.setBackground(blackForBg);
           // textArea.add(p4);
           vertical.add(right);
           textArea.add(vertical,BorderLayout.PAGE_START);
           frame.validate();

            msgField.setText("");
            dataOutputStream.writeUTF(out);
            System.out.println("message send Successfully");
            
        } catch (Exception e) {
            System.out.println(e);
        }

    }


        // for sended message
    public static JPanel formatLabel(String out) {
        JPanel panel3 = new JPanel();
        JLabel l1 = new JLabel("<html><p style=\"width:150px;\">"+out+"</p></html>");
        l1.setBackground(new Color(246,181,0));
        l1.setOpaque(true);
        l1.setBorder(new EmptyBorder(15,15,15,50));
        // mymsg = l1.getText();
        mymsg = out;
        panel3.add(l1);
        return panel3;
    }

       // for received message
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
   
    
    // start() internally call run mtf and we have to override run mtd
    public void run() {
        try {
            
            while (true) {
                
                String msgInput = "";
                msgInput = dataInputStream.readUTF();


                // JPanel p2 = formatLabel(msgInput);
                // JPanel left = new JPanel(new BorderLayout());
                // left.add(p2,BorderLayout.LINE_START);
                // vertical.add(left);
                // frame.validate();

                // i changed this
                if (msgInput.equals(mymsg)) {
                    
                    System.out.println("I am comming from If");
                    System.out.println(msgInput+"===>"+mymsg);
    
                    frame.validate();
                    JPanel p2 = formatLabel(msgInput);
                    JPanel left = new JPanel(new BorderLayout());
                    left.add(p2,BorderLayout.LINE_START);
                    // vertical.add(left);
                    vertical.remove(left);
                    frame.validate();
    
                    } 
                    else {
                        System.out.println("I am comming from else");
                        System.out.println(msgInput+"===>"+mymsg);

                        
                            frame.validate();
                            System.out.println("strings are equal");
                            JPanel p2 = formatLabelReceived(msgInput);
                            JPanel left = new JPanel(new BorderLayout());

                            p2.setBackground(blackForBg);//black
                            left.setBackground(blackForBg);//grayinsh black

                            left.add(p2,BorderLayout.LINE_START);
                            vertical.add(left);
                            // vertical.remove(left);
                            frame.validate();
                            System.out.println("You : "+msgInput);
                       

                    }
    
               
    
                // textArea.setText(textArea.getText()+"\n"+msgInput);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void main(String[] args) {
        client2  one =new client2();
        Thread t1 = new Thread(one);
        t1.start();

        try {
            socket = new Socket("127.0.0.1",6001);

            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());//this

            // while (true) {
                
            //     String msgInput = "";
            //     msgInput = dataInputStream.readUTF();

            //     JPanel p2 = formatLabel(msgInput);
            //     JPanel left = new JPanel(new BorderLayout());
            //     left.add(p2,BorderLayout.LINE_START);
            //     vertical.add(left);
            //     frame.validate();
    
            //     // textArea.setText(textArea.getText()+"\n"+msgInput);
            // }


        } catch (Exception e) {
            System.out.println(e);
        }
    }
}