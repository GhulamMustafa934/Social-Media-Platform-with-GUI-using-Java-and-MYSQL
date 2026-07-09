package View;

import Model.User;
import Model.Post;
import Controller.CreatePost;
import Controller.LikePost;
import Controller.CommentPost;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

public class ProfileGUI {
    
    private User currentUser;
    private JFrame frame;
    
    public ProfileGUI(User user) {
        this.currentUser = user;
        
        frame = new JFrame();
        frame.setSize(600, 700);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(GUIConstants.lightGray);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Profile Header
        JPanel headerPanel = new JPanel(new BorderLayout(10, 10));
        headerPanel.setBackground(GUIConstants.white);
        headerPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(GUIConstants.lightGray, 1),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        
        // Profile Picture Placeholder
        JLabel profilePic = new JLabel("👤", SwingConstants.CENTER);
        profilePic.setFont(new Font("Segoe UI", Font.PLAIN, 60));
        headerPanel.add(profilePic, BorderLayout.WEST);
        
        // User Info
        JPanel infoPanel = new JPanel(new GridLayout(3, 1, 5, 5));
        infoPanel.setBackground(GUIConstants.white);
        
        JLabel nameLabel = new JLabel(user.getFirstName() + " " + user.getLastName());
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        infoPanel.add(nameLabel);
        
        JLabel emailLabel = new JLabel("📧 " + user.getEmail());
        emailLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        infoPanel.add(emailLabel);
        
        // Count posts
        CreatePost createPost = new CreatePost();
        ArrayList<Post> userPosts = new ArrayList<>();
        for (Post p : createPost.getAllPosts()) {
            if (p.getUser().getID() == user.getID()) {
                userPosts.add(p);
            }
        }
        JLabel postCountLabel = new JLabel("📝 " + userPosts.size() + " posts");
        postCountLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        infoPanel.add(postCountLabel);
        
        headerPanel.add(infoPanel, BorderLayout.CENTER);
        
        // Edit Profile Button
        JButton editButton = new JButton("✏️ Edit Profile");
        editButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        headerPanel.add(editButton, BorderLayout.SOUTH);
        
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        
        // User's Posts
        JPanel postsPanel = new JPanel(new GridLayout(0, 1, 5, 5));
        postsPanel.setBackground(GUIConstants.white);
        postsPanel.setBorder(BorderFactory.createTitledBorder("My Posts"));
        
        if (userPosts.isEmpty()) {
            JLabel emptyLabel = new JLabel("No posts yet!");
            emptyLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            emptyLabel.setHorizontalAlignment(SwingConstants.CENTER);
            postsPanel.add(emptyLabel);
        } else {
            LikePost likePost = new LikePost();
            CommentPost commentPost = new CommentPost();
            for (Post post : userPosts) {
                postsPanel.add(createPostCard(post, likePost, commentPost));
            }
        }
        
        JScrollPane scrollPane = new JScrollPane(postsPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        
        // Back Button
        JButton backButton = new JButton("⬅ Back to Dashboard");
        backButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        backButton.addMouseListener(new MouseListener() {
            @Override public void mouseReleased(MouseEvent e) {}
            @Override public void mousePressed(MouseEvent e) {}
            @Override public void mouseExited(MouseEvent e) {}
            @Override public void mouseEntered(MouseEvent e) {}
            @Override
            public void mouseClicked(MouseEvent e) {
                frame.dispose();
                new DashboardGUI(currentUser);
            }
        });
        mainPanel.add(backButton, BorderLayout.SOUTH);
        
        // Edit Profile Action
        editButton.addMouseListener(new MouseListener() {
            @Override public void mouseReleased(MouseEvent e) {}
            @Override public void mousePressed(MouseEvent e) {}
            @Override public void mouseExited(MouseEvent e) {}
            @Override public void mouseEntered(MouseEvent e) {}
            @Override
            public void mouseClicked(MouseEvent e) {
                frame.dispose();
                new EditProfileGUI(currentUser);
            }
        });
        
        frame.getContentPane().add(mainPanel);
        frame.setVisible(true);
        frame.requestFocus();
    }
    
    private JPanel createPostCard(Post post, LikePost likePost, CommentPost commentPost) {
        JPanel card = new JPanel();
        card.setLayout(new BorderLayout(10, 5));
        card.setBackground(GUIConstants.white);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(GUIConstants.lightGray, 1),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        
        int postId = post.getId();
        
        // Content
        JLabel contentLabel = new JLabel(post.getContent());
        contentLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        contentLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        card.add(contentLabel, BorderLayout.CENTER);
        
        // Buttons
        JPanel buttonPanel = new JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));
        buttonPanel.setBackground(GUIConstants.white);
        
        int likes = likePost.getLikesCount(postId);
        JButton likeBtn = new JButton("❤️ " + likes);
        likeBtn.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        buttonPanel.add(likeBtn);
        
        int comments = commentPost.getCommentCount(postId);
        JButton commentBtn = new JButton("💬 " + comments);
        commentBtn.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        buttonPanel.add(commentBtn);
        
        card.add(buttonPanel, BorderLayout.SOUTH);
        
        return card;
    }
}