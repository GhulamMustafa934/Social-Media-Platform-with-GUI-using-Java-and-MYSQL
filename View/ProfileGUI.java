package View;

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
import javax.swing.SwingConstants;

import Controller.CommentPost;
import Controller.CreatePost;
import Controller.FriendController;
import Controller.LikePost;
import Model.Post;
import Model.User;

public class ProfileGUI {

    private User currentUser;
    private JFrame frame;

    public ProfileGUI(User user) {
        this.currentUser = user;

        frame = new JFrame();
        frame.setSize(800, 700);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(GUIConstants.darkBg);

        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBackground(GUIConstants.darkBg);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Profile Header
        JPanel headerPanel = new JPanel(new BorderLayout(15, 15));
        headerPanel.setBackground(GUIConstants.darkCard);
        headerPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(GUIConstants.darkBorder, 1),
            BorderFactory.createEmptyBorder(25, 25, 25, 25)
        ));

        // Profile Picture
        JLabel profilePic = new JLabel("👤", SwingConstants.CENTER);
        profilePic.setFont(new Font("Segoe UI", Font.PLAIN, 80));
        headerPanel.add(profilePic, BorderLayout.WEST);

        // User Info
        JPanel infoPanel = new JPanel(new GridLayout(3, 1, 5, 5));
        infoPanel.setBackground(GUIConstants.darkCard);

        JLabel nameLabel = new JLabel(user.getFirstName() + " " + user.getLastName());
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        nameLabel.setForeground(GUIConstants.textWhite);
        infoPanel.add(nameLabel);

        JLabel emailLabel = new JLabel("📧 " + user.getEmail());
        emailLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        emailLabel.setForeground(GUIConstants.textGray);
        infoPanel.add(emailLabel);

        // Stats
        CreatePost createPost = new CreatePost();
        ArrayList<Post> userPosts = new ArrayList<>();
        for (Post p : createPost.getAllPosts()) {
            if (p.getUser().getID() == user.getID()) {
                userPosts.add(p);
            }
        }

        JPanel statsPanel = new JPanel(new GridLayout(1, 3, 15, 15));
        statsPanel.setBackground(GUIConstants.darkCard);

        JLabel postCount = new JLabel("📝 " + userPosts.size() + " Posts");
        postCount.setFont(new Font("Segoe UI", Font.BOLD, 14));
        postCount.setForeground(GUIConstants.textWhite);
        statsPanel.add(postCount);

        int totalLikes = 0;
        for (Post p : userPosts) {
            LikePost lp = new LikePost();
            totalLikes += lp.getLikesCount(p.getId());
        }
        JLabel likeCount = new JLabel("❤️ " + totalLikes + " Likes");
        likeCount.setForeground(GUIConstants.textWhite);
        likeCount.setFont(new Font("Segoe UI", Font.BOLD, 14));
        statsPanel.add(likeCount);

        FriendController fc = new FriendController();
        int friendCount = fc.getFriends(user.getID()).size();
        JLabel friendCountLabel = new JLabel("👥 " + friendCount + " Friends");
        friendCountLabel.setForeground(GUIConstants.textWhite);
        friendCountLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        statsPanel.add(friendCountLabel);

        infoPanel.add(statsPanel);
        headerPanel.add(infoPanel, BorderLayout.CENTER);

        // Edit Profile Button (Only this button)
        JButton editButton = new JButton("✏️ Edit Profile");
        editButton.setBackground(GUIConstants.btnPrimary);
        editButton.setForeground(GUIConstants.textWhite);
        editButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        editButton.setFocusPainted(false);
        editButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        headerPanel.add(editButton, BorderLayout.SOUTH);

        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // User's Posts
        JPanel postsPanel = new JPanel(new GridLayout(0, 1, 10, 10));
        postsPanel.setBackground(GUIConstants.darkBg);
        postsPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(GUIConstants.darkBorder),
            "📄 My Posts",
            SwingConstants.LEFT,
            Font.BOLD,
            new Font("Segoe UI", Font.BOLD, 16),
            GUIConstants.textWhite
        ));

        if (userPosts.isEmpty()) {
            JLabel emptyLabel = new JLabel("No posts yet!");
            emptyLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            emptyLabel.setForeground(GUIConstants.textGray);
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
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Back Button
        JButton backButton = new JButton("⬅ Back to Dashboard");
        backButton.setBackground(GUIConstants.darkCard);
        backButton.setForeground(GUIConstants.textWhite);
        backButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        backButton.setFocusPainted(false);
        backButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
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
        card.setLayout(new BorderLayout(10, 8));
        card.setBackground(GUIConstants.darkCard);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(GUIConstants.darkBorder, 1),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        int postId = post.getId();

        // Content
        JLabel contentLabel = new JLabel(post.getContent());
        contentLabel.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        contentLabel.setForeground(GUIConstants.textWhite);
        contentLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        card.add(contentLabel, BorderLayout.CENTER);

        // Buttons
        JPanel buttonPanel = new JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));
        buttonPanel.setBackground(GUIConstants.darkCard);

        int likes = likePost.getLikesCount(postId);
        JButton likeBtn = new JButton("❤️ " + likes);
        likeBtn.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        likeBtn.setBackground(GUIConstants.darkBg);
        likeBtn.setForeground(GUIConstants.textWhite);
        likeBtn.setFocusPainted(false);
        buttonPanel.add(likeBtn);

        int comments = commentPost.getCommentCount(postId);
        JButton commentBtn = new JButton("💬 " + comments);
        commentBtn.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        commentBtn.setBackground(GUIConstants.darkBg);
        commentBtn.setForeground(GUIConstants.textWhite);
        commentBtn.setFocusPainted(false);
        buttonPanel.add(commentBtn);

        card.add(buttonPanel, BorderLayout.SOUTH);

        return card;
    }
}