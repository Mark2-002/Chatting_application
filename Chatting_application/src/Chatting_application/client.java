package Chatting_application;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.net.*;
import java.util.*;
import java.text.SimpleDateFormat;

public class client  {
    JTextField text;
    JPanel p1;
    static JPanel al;//declared globally

    static JFrame f= new JFrame();
    static Box vertical=Box.createVerticalBox();

    static DataOutputStream dout;
    client (){
        f.setLayout(null);

        //This is for main panel

        f.setSize(400,600);
        f.setLocation(800,50);
        f.getContentPane().setBackground(Color.white);

        //Now for header panel
        p1 = new JPanel();
        p1.setBackground(new Color(7,94,84));
        p1.setBounds(0,0,400,60);
        p1.setLayout(null);
        f.add(p1);

        //Now we will add buttons
        ImageIcon i1 =new ImageIcon(ClassLoader.getSystemResource("icons/3.png"));
        Image i2= i1.getImage().getScaledInstance(20 ,20,Image.SCALE_DEFAULT);

        //We can't simply pass i2 in JLabel  we have to convert image to image icon
        ImageIcon i3= new ImageIcon(i2);
        JLabel back =new JLabel(i3);
        back.setBounds(10,20,20,20);
        p1.add(back);
        //Now we will add functionality of this button
        back.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                System.exit(0);
            }
        }) ;

        //for profile Image
        ImageIcon i4 =new ImageIcon(ClassLoader.getSystemResource("icons/gaitonde.jpeg"));
        Image i5= i4.getImage().getScaledInstance(40 ,40,Image.SCALE_DEFAULT);

        //We can't simply pass i4 in JLabel  we have to convert image to image icon
        ImageIcon i6= new ImageIcon(i5);
        JLabel profile =new JLabel(i6);
        profile.setBounds(35,10,40,40);
        p1.add(profile);

        // for video icon
        ImageIcon i7 =new ImageIcon(ClassLoader.getSystemResource("icons/video.png"));
        Image i8= i7.getImage().getScaledInstance(25 ,20,Image.SCALE_DEFAULT);

        //We can't simply pass i4 in JLabel  we have to convert image to image icon
        ImageIcon i9= new ImageIcon(i8);
        JLabel video =new JLabel(i9);
        video.setBounds(250,15,25,20);
        p1.add(video);

        //for phone
        ImageIcon i13 =new ImageIcon(ClassLoader.getSystemResource("icons/phone.png"));
        Image i14= i13.getImage().getScaledInstance(25 ,20,Image.SCALE_DEFAULT);

        //We can't simply pass i4 in JLabel  we have to convert image to image icon
        ImageIcon i15= new ImageIcon(i14);
        JLabel phone =new JLabel(i15);
        phone.setBounds(305,15,25,20);
        p1.add(phone);


        //for morevert
        ImageIcon i10 =new ImageIcon(ClassLoader.getSystemResource("icons/3icon.png"));
        Image i11= i10.getImage().getScaledInstance(10 ,20,Image.SCALE_DEFAULT);

        //We can't simply pass i4 in JLabel  we have to convert image to image icon
        ImageIcon i12= new ImageIcon(i11);
        JLabel morevert =new JLabel(i12);
        morevert.setBounds(360,15,10,20);
        p1.add(morevert);


        //For textArea

        JLabel name= new JLabel("User 1");
        name.setBounds(100,15,100,14);
        name.setForeground(Color.white);
        name.setFont(new Font("SAN_SERIF",Font.BOLD,14));
        p1.add(name);

        JLabel Status= new JLabel("active now");
        Status.setBounds(100,30,100,12);
        Status.setForeground(Color.white);
        Status.setFont(new Font("SAN_SERIF",Font.BOLD,10));
        p1.add(Status);

        //for text Area
        al=new JPanel();
        al.setBounds(5,65,390,490);
        al.setBackground(new Color(5, 107, 121));
        f.add(al);


        //text field

        text =new JTextField();
        text.setBounds(5, 560,270,40);
        text.setFont( new Font("SAN_SERIF",Font.PLAIN,16));
        f.add(text);

        JButton send = new JButton("Send");
        send.setBounds(275,560,120,40);
        send.setBackground( new Color(6, 69, 63));
        send.setForeground(Color.white);
        send.addActionListener(this::actionPerformed);
        send.setFont(new Font("SAN_SERIF",Font.PLAIN,16));
        f.add(send);







        f.setUndecorated(true);
        f.setVisible(true);
    }

    //function for back button
    public void  actionPerformed(ActionEvent ae){
        String out=text.getText();

        JPanel p2 =formatLabel(out);

        al.setLayout(new BorderLayout());

        JPanel right= new JPanel(new BorderLayout());
        right.add(p2,BorderLayout.LINE_END);

        right.setBackground(new Color(5, 107, 121));
        vertical.add(right);
        vertical.add(Box.createVerticalStrut(15));
        try {
            dout.writeUTF(out);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        al.add(vertical,BorderLayout.PAGE_START);
        f.repaint();
        f.invalidate();
        f.validate();



    }

    public static  JPanel formatLabel(String out){
        JPanel panel= new JPanel();
        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
        JLabel output =new JLabel("<html><p style=\"width:100px\">"+out+"</p><html>");
        output.setFont(new Font("Tahoma",Font.PLAIN,20));
        output.setBackground(new Color(4, 103, 2));
        output.setOpaque(true);
        output.setBorder(new EmptyBorder(15,15,15,50));
        panel.setBackground(new Color(5, 107, 121));
        panel.add(output);



        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf =new SimpleDateFormat("HH:mm");

        JLabel time =new JLabel();
        time.setText(sdf.format(cal.getTime()));
        panel.add(time);



        return panel;
    }

    public static void main(String[] args){
        new client();

        try{
            Socket s=new Socket("127.0.0.1",6001);
            DataInputStream din= new DataInputStream(s.getInputStream());
            dout=new DataOutputStream(s.getOutputStream());

            while(true){
                al.setLayout(new BorderLayout());
                String msg = din.readUTF();
                JPanel panel= formatLabel(msg);

                JPanel left= new JPanel(new BorderLayout());
                left.setBackground(new Color(5, 107, 121));
                left.add(panel,BorderLayout.LINE_START);
                vertical.add(left);

                vertical.add(Box.createVerticalStrut(15));
                al.add(vertical, BorderLayout.PAGE_START);
                f.validate();
            }

        }catch(Exception e){
            e.printStackTrace();
        }

    }
}
