package View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
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

import Controller.CommentPost;
import Controller.CreatePost;
import Controller.LikePost;
import Model.Comment;
import Model.Post;
import Model.User;

public class DashboardGUI {
    
    private User currentUser;
    private JPanel feedPanel;
    private JFrame frame;
    
    // Dark Theme Colors
    private final Color BG_DARK = new Color(30, 30, 46);
    private final Color BG_CARD = new Color(40, 40, 60);
    private final Color BG_INPUT = new Color(50, 50, 70);
    private final Color BORDER_COLOR = new Color(60, 60, 80);
    private final Color TEXT_WHITE = Color.WHITE;
    private final Color TEXT_GRAY = new Color(180, 180, 200);
    private final Color ACCENT_BLUE = new Color(0, 150, 255);
    private final Color ACCENT_RED = new Color(255, 70, 70);
    
    public DashboardGUI(User user) {
        this.currentUser = user;
        
        frame = new JFrame();
        frame.setSize(900, 625);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(BG_DARK);
        
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(BG_DARK);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Top Panel
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(BG_DARK);
        
        JLabel welcomeLabel = new JLabel("Welcome, " + user.getFirstName() + " " + user.getLastName() + "!");
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        welcomeLabel.setForeground(TEXT_WHITE);
        topPanel.add(welcomeLabel, BorderLayout.WEST);
        
        // RIGHT PANEL: Profile + Friends + Logout
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightPanel.setBackground(BG_DARK);
        
        JButton profileBtn = new JButton("👤 Profile");
        profileBtn.setBackground(BG_CARD);
        profileBtn.setForeground(TEXT_WHITE);
        profileBtn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        profileBtn.setFocusPainted(false);
        profileBtn.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        rightPanel.add(profileBtn);
        
        JButton friendsBtn = new JButton("👥 Friends");
        friendsBtn.setBackground(BG_CARD);
        friendsBtn.setForeground(TEXT_WHITE);
        friendsBtn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        friendsBtn.setFocusPainted(false);
        friendsBtn.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        rightPanel.add(friendsBtn);
        
        JButton logoutButton = new JButton("Logout");
        logoutButton.setBackground(BG_CARD);
        logoutButton.setForeground(TEXT_WHITE);
        logoutButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        logoutButton.setFocusPainted(false);
        logoutButton.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        rightPanel.add(logoutButton);
        
        topPanel.add(rightPanel, BorderLayout.EAST);
        mainPanel.add(topPanel, BorderLayout.NORTH);
        
        // Create Post
        JPanel postPanel = new JPanel(new BorderLayout(5, 5));
        postPanel.setBackground(BG_CARD);
        postPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_COLOR, 1),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        
        JLabel postTitle = new JLabel("Create Post");
        postTitle.setFont(new Font("Segoe UI", Font.BOLD, 14));
        postTitle.setForeground(TEXT_WHITE);
        postPanel.add(postTitle, BorderLayout.NORTH);
        
        JTextArea postArea = new JTextArea(3, 30);
        postArea.setBackground(BG_INPUT);
        postArea.setForeground(TEXT_WHITE);
        postArea.setCaretColor(TEXT_WHITE);
        postArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        postArea.setLineWrap(true);
        postArea.setText("What's on your mind?");
        postArea.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_COLOR, 1),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        postPanel.add(postArea, BorderLayout.CENTER);
        
        JButton postButton = new JButton("Post");
        postButton.setBackground(ACCENT_BLUE);
        postButton.setForeground(TEXT_WHITE);
        postButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        postButton.setFocusPainted(false);
        postButton.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        
        JPanel postBtnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        postBtnPanel.setBackground(BG_CARD);
        postBtnPanel.add(postButton);
        postPanel.add(postBtnPanel, BorderLayout.SOUTH);
        
        mainPanel.add(postPanel, BorderLayout.CENTER);
        
        // Feed
        feedPanel = new JPanel();
        feedPanel.setLayout(new GridLayout(0, 1, 5, 5));
        feedPanel.setBackground(BG_DARK);
        
        JScrollPane scrollPane = new JScrollPane(feedPanel);
        scrollPane.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(BORDER_COLOR),
            "Feed",
            SwingConstants.LEFT,
            Font.BOLD,
            new Font("Segoe UI", Font.BOLD, 14),
            TEXT_WHITE
        ));
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        mainPanel.add(scrollPane, BorderLayout.SOUTH);
        
        loadFeed();
        
        // ========== BUTTON ACTIONS ==========
        
        profileBtn.addMouseListener(new MouseListener() {
            @Override public void mouseReleased(MouseEvent e) {}
            @Override public void mousePressed(MouseEvent e) {}
            @Override public void mouseExited(MouseEvent e) {}
            @Override public void mouseEntered(MouseEvent e) {
                profileBtn.setBackground(new Color(60, 60, 80));
            }
            @Override
            public void mouseClicked(MouseEvent e) {
                frame.dispose();
                new ProfileGUI(currentUser);
            }
        });
        
        friendsBtn.addMouseListener(new MouseListener() {
            @Override public void mouseReleased(MouseEvent e) {}
            @Override public void mousePressed(MouseEvent e) {}
            @Override public void mouseExited(MouseEvent e) {}
            @Override public void mouseEntered(MouseEvent e) {
                friendsBtn.setBackground(new Color(60, 60, 80));
            }
            @Override
            public void mouseClicked(MouseEvent e) {
                new FriendsGUI(currentUser);
            }
        });
        
        logoutButton.addMouseListener(new MouseListener() {
            @Override public void mouseReleased(MouseEvent e) {}
            @Override public void mousePressed(MouseEvent e) {}
            @Override public void mouseExited(MouseEvent e) {}
            @Override public void mouseEntered(MouseEvent e) {
                logoutButton.setBackground(new Color(60, 60, 80));
            }
            @Override
            public void mouseClicked(MouseEvent e) {
                frame.dispose();
                new LoginGUI();
            }
        });
        
        postButton.addMouseListener(new MouseListener() {
            @Override public void mouseReleased(MouseEvent e) {}
            @Override public void mousePressed(MouseEvent e) {}
            @Override public void mouseExited(MouseEvent e) {}
            @Override public void mouseEntered(MouseEvent e) {
                postButton.setBackground(new Color(50, 180, 255));
            }
            @Override
            public void mouseClicked(MouseEvent e) {
                String content = postArea.getText();
                if (content.isEmpty() || content.equals("What's on your mind?")) {
                    new Alert("Please write something!", frame);
                    return;
                }
                
                Post post = new Post(content, currentUser);
                CreatePost createPost = new CreatePost();
                int postId = createPost.savePost(post);
                
                if (postId > 0) {
                    new Alert("Post created!", frame);
                    postArea.setText("What's on your mind?");
                    loadFeed();
                } else {
                    new Alert("Failed to create post!", frame);
                }
            }
        });
        
        frame.getContentPane().add(mainPanel);
        frame.setVisible(true);
        frame.requestFocus();
    }
    
    private void loadFeed() {
        try {
            feedPanel.removeAll();
            CreatePost createPost = new CreatePost();
            LikePost likePost = new LikePost();
            CommentPost commentPost = new CommentPost();
            ArrayList<Post> posts = createPost.getAllPosts();
            
            if (posts.isEmpty()) {
                JLabel emptyLabel = new JLabel("No posts yet!");
                emptyLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
                emptyLabel.setForeground(TEXT_GRAY);
                emptyLabel.setHorizontalAlignment(SwingConstants.CENTER);
                feedPanel.add(emptyLabel);
            } else {
                for (Post post : posts) {
                    feedPanel.add(createPostCard(post, likePost, commentPost, createPost));
                }
            }
            
            feedPanel.revalidate();
            feedPanel.repaint();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private JPanel createPostCard(Post post, LikePost likePost, CommentPost commentPost, CreatePost createPost) {
        JPanel card = new JPanel();
        card.setLayout(new BorderLayout(10, 5));
        card.setBackground(BG_CARD);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_COLOR, 1),
            BorderFactory.createEmptyBorder(12, 12, 12, 12)
        ));
        
        int postId = post.getId();
        
        // User name
        JLabel userLabel = new JLabel("👤 " + post.getUser().getFirstName() + " " + post.getUser().getLastName());
        userLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        userLabel.setForeground(TEXT_WHITE);
        card.add(userLabel, BorderLayout.NORTH);
        
        // Content
        JLabel contentLabel = new JLabel(post.getContent());
        contentLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        contentLabel.setForeground(TEXT_GRAY);
        contentLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        card.add(contentLabel, BorderLayout.CENTER);
        
        // Buttons Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.setBackground(BG_CARD);
        
        // Like button
        int likes = likePost.getLikesCount(postId);
        boolean liked = likePost.hasLiked(postId, currentUser.getID());
        JButton likeBtn = new JButton((liked ? "❤️ Unlike" : "🤍 Like") + " (" + likes + ")");
        likeBtn.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        likeBtn.setBackground(BG_INPUT);
        likeBtn.setForeground(TEXT_WHITE);
        likeBtn.setFocusPainted(false);
        likeBtn.setBorder(BorderFactory.createEmptyBorder(3, 10, 3, 10));
        buttonPanel.add(likeBtn);
        
        // Comment button
        int comments = commentPost.getCommentCount(postId);
        JButton commentBtn = new JButton("💬 Comment (" + comments + ")");
        commentBtn.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        commentBtn.setBackground(BG_INPUT);
        commentBtn.setForeground(TEXT_WHITE);
        commentBtn.setFocusPainted(false);
        commentBtn.setBorder(BorderFactory.createEmptyBorder(3, 10, 3, 10));
        buttonPanel.add(commentBtn);
        
        // Edit & Delete (only for post owner)
        if (post.getUser().getID() == currentUser.getID()) {
            JButton editBtn = new JButton("✏️ Edit");
            editBtn.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            editBtn.setBackground(BG_INPUT);
            editBtn.setForeground(ACCENT_BLUE);
            editBtn.setFocusPainted(false);
            editBtn.setBorder(BorderFactory.createEmptyBorder(3, 10, 3, 10));
            buttonPanel.add(editBtn);
            
            JButton deleteBtn = new JButton("🗑️ Delete");
            deleteBtn.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            deleteBtn.setBackground(BG_INPUT);
            deleteBtn.setForeground(ACCENT_RED);
            deleteBtn.setFocusPainted(false);
            deleteBtn.setBorder(BorderFactory.createEmptyBorder(3, 10, 3, 10));
            buttonPanel.add(deleteBtn);
            
            // Edit action
            editBtn.addMouseListener(new MouseListener() {
                @Override public void mouseReleased(MouseEvent e) {}
                @Override public void mousePressed(MouseEvent e) {}
                @Override public void mouseExited(MouseEvent e) {}
                @Override public void mouseEntered(MouseEvent e) {
                    editBtn.setBackground(new Color(60, 60, 80));
                }
                @Override
                public void mouseClicked(MouseEvent e) {
                    String newContent = javax.swing.JOptionPane.showInputDialog(frame, "Edit post:", post.getContent());
                    if (newContent != null && !newContent.trim().isEmpty()) {
                        boolean success = createPost.updatePost(postId, newContent);
                        if (success) {
                            new Alert("Post updated!", frame);
                            loadFeed();
                        } else {
                            new Alert("Failed to update post!", frame);
                        }
                    }
                }
            });
            
            // Delete action
            deleteBtn.addMouseListener(new MouseListener() {
                @Override public void mouseReleased(MouseEvent e) {}
                @Override public void mousePressed(MouseEvent e) {}
                @Override public void mouseExited(MouseEvent e) {}
                @Override public void mouseEntered(MouseEvent e) {
                    deleteBtn.setBackground(new Color(60, 60, 80));
                }
                @Override
                public void mouseClicked(MouseEvent e) {
                    int confirm = javax.swing.JOptionPane.showConfirmDialog(frame, 
                        "Delete this post?", "Confirm", javax.swing.JOptionPane.YES_NO_OPTION);
                    if (confirm == javax.swing.JOptionPane.YES_OPTION) {
                        boolean success = createPost.deletePost(postId);
                        if (success) {
                            new Alert("Post deleted!", frame);
                            loadFeed();
                        } else {
                            new Alert("Failed to delete post!", frame);
                        }
                    }
                }
            });
        }
        
        card.add(buttonPanel, BorderLayout.SOUTH);
        
        // Like action
        likeBtn.addMouseListener(new MouseListener() {
            @Override public void mouseReleased(MouseEvent e) {}
            @Override public void mousePressed(MouseEvent e) {}
            @Override public void mouseExited(MouseEvent e) {}
            @Override public void mouseEntered(MouseEvent e) {
                likeBtn.setBackground(new Color(60, 60, 80));
            }
            @Override
            public void mouseClicked(MouseEvent e) {
                if (liked) {
                    likePost.removeLike(postId, currentUser.getID());
                } else {
                    likePost.addLike(postId, currentUser.getID());
                }
                loadFeed();
            }
        });
        
        // Comment action
        commentBtn.addMouseListener(new MouseListener() {
            @Override public void mouseReleased(MouseEvent e) {}
            @Override public void mousePressed(MouseEvent e) {}
            @Override public void mouseExited(MouseEvent e) {}
            @Override public void mouseEntered(MouseEvent e) {
                commentBtn.setBackground(new Color(60, 60, 80));
            }
            @Override
            public void mouseClicked(MouseEvent e) {
                String comment = javax.swing.JOptionPane.showInputDialog(frame, "Write a comment:");
                if (comment != null && !comment.trim().isEmpty()) {
                    commentPost.addComment(postId, currentUser.getID(), comment);
                    loadFeed();
                }
            }
        });
        
        // Comment Section (hidden by default)
        JPanel commentSection = new JPanel(new BorderLayout(5, 5));
        commentSection.setBackground(BG_CARD);
        commentSection.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        commentSection.setVisible(false);
        
        JTextArea commentArea = new JTextArea(2, 20);
        commentArea.setBackground(BG_INPUT);
        commentArea.setForeground(TEXT_WHITE);
        commentArea.setCaretColor(TEXT_WHITE);
        commentArea.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        commentArea.setLineWrap(true);
        commentArea.setWrapStyleWord(true);
        commentArea.setText("Write a comment...");
        commentArea.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_COLOR, 1),
            BorderFactory.createEmptyBorder(5, 8, 5, 8)
        ));
        commentSection.add(new JScrollPane(commentArea), BorderLayout.CENTER);
        
        JButton submitComment = new JButton("Post Comment");
        submitComment.setBackground(ACCENT_BLUE);
        submitComment.setForeground(TEXT_WHITE);
        submitComment.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        submitComment.setFocusPainted(false);
        submitComment.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        commentSection.add(submitComment, BorderLayout.EAST);
        
        // Show comments when clicking comment button
        commentBtn.addMouseListener(new MouseListener() {
            @Override public void mouseReleased(MouseEvent e) {}
            @Override public void mousePressed(MouseEvent e) {}
            @Override public void mouseExited(MouseEvent e) {}
            @Override public void mouseEntered(MouseEvent e) {}
            @Override
            public void mouseClicked(MouseEvent e) {
                boolean visible = commentSection.isVisible();
                commentSection.setVisible(!visible);
                if (!visible) {
                    loadComments(commentSection, postId, commentPost);
                }
                card.revalidate();
                card.repaint();
            }
        });
        
        // Submit comment action
        submitComment.addMouseListener(new MouseListener() {
            @Override public void mouseReleased(MouseEvent e) {}
            @Override public void mousePressed(MouseEvent e) {}
            @Override public void mouseExited(MouseEvent e) {}
            @Override public void mouseEntered(MouseEvent e) {}
            @Override
            public void mouseClicked(MouseEvent e) {
                String content = commentArea.getText();
                if (content.isEmpty() || content.equals("Write a comment...")) {
                    new Alert("Please write a comment!", frame);
                    return;
                }
                boolean success = commentPost.addComment(postId, currentUser.getID(), content);
                if (success) {
                    new Alert("Comment posted!", frame);
                    loadFeed();
                } else {
                    new Alert("Failed to post comment!", frame);
                }
            }
        });
        
        // Bottom container for both button panel and comment section
        JPanel bottomContainer = new JPanel();
        bottomContainer.setLayout(new GridLayout(2, 1, 0, 5));
        bottomContainer.setBackground(BG_CARD);
        
        bottomContainer.add(buttonPanel);
        bottomContainer.add(commentSection);
        
        card.add(bottomContainer, BorderLayout.SOUTH);
        
        return card;
    }
    
    private void loadComments(JPanel parent, int postId, CommentPost commentPost) {
        parent.removeAll();
        
        ArrayList<Comment> comments = commentPost.getCommentsForPost(postId);
        JPanel commentsPanel = new JPanel(new GridLayout(0, 1, 5, 5));
        commentsPanel.setBackground(BG_DARK);
        
        if (comments.isEmpty()) {
            JLabel emptyLabel = new JLabel("No comments yet.");
            emptyLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            emptyLabel.setForeground(TEXT_GRAY);
            commentsPanel.add(emptyLabel);
        } else {
            for (Comment comment : comments) {
                commentsPanel.add(createCommentCard(comment, commentPost));
            }
        }
        
        parent.add(new JScrollPane(commentsPanel), BorderLayout.CENTER);
        parent.revalidate();
        parent.repaint();
    }
    
    private JPanel createCommentCard(Comment comment, CommentPost commentPost) {
        JPanel commentCard = new JPanel(new BorderLayout(5, 2));
        commentCard.setBackground(BG_INPUT);
        commentCard.setBorder(BorderFactory.createEmptyBorder(5, 8, 5, 8));
        
        JLabel commentUser = new JLabel("👤 " + comment.getUser().getFirstName() + " " + comment.getUser().getLastName());
        commentUser.setFont(new Font("Segoe UI", Font.BOLD, 11));
        commentUser.setForeground(ACCENT_BLUE);
        commentCard.add(commentUser, BorderLayout.NORTH);
        
        JLabel commentContent = new JLabel(comment.getContent());
        commentContent.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        commentContent.setForeground(TEXT_GRAY);
        commentCard.add(commentContent, BorderLayout.CENTER);
        
        if (comment.getUser().getID() == currentUser.getID()) {
            JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            actionPanel.setBackground(BG_INPUT);
            
            JButton editBtn = new JButton("✏️");
            editBtn.setFont(new Font("Segoe UI", Font.PLAIN, 10));
            editBtn.setBackground(BG_CARD);
            editBtn.setForeground(ACCENT_BLUE);
            editBtn.setFocusPainted(false);
            editBtn.setBorder(BorderFactory.createEmptyBorder(2, 8, 2, 8));
            actionPanel.add(editBtn);
            
            JButton deleteBtn = new JButton("🗑️");
            deleteBtn.setFont(new Font("Segoe UI", Font.PLAIN, 10));
            deleteBtn.setBackground(BG_CARD);
            deleteBtn.setForeground(ACCENT_RED);
            deleteBtn.setFocusPainted(false);
            deleteBtn.setBorder(BorderFactory.createEmptyBorder(2, 8, 2, 8));
            actionPanel.add(deleteBtn);
            
            commentCard.add(actionPanel, BorderLayout.EAST);
            
            editBtn.addMouseListener(new MouseListener() {
                @Override public void mouseReleased(MouseEvent e) {}
                @Override public void mousePressed(MouseEvent e) {}
                @Override public void mouseExited(MouseEvent e) {}
                @Override public void mouseEntered(MouseEvent e) {}
                @Override
                public void mouseClicked(MouseEvent e) {
                    String newContent = javax.swing.JOptionPane.showInputDialog(frame, "Edit comment:", comment.getContent());
                    if (newContent != null && !newContent.trim().isEmpty()) {
                        boolean success = commentPost.updateComment(comment.getId(), newContent);
                        if (success) {
                            new Alert("Comment updated!", frame);
                            loadFeed();
                        } else {
                            new Alert("Failed to update comment!", frame);
                        }
                    }
                }
            });
            
            deleteBtn.addMouseListener(new MouseListener() {
                @Override public void mouseReleased(MouseEvent e) {}
                @Override public void mousePressed(MouseEvent e) {}
                @Override public void mouseExited(MouseEvent e) {}
                @Override public void mouseEntered(MouseEvent e) {}
                @Override
                public void mouseClicked(MouseEvent e) {
                    int confirm = javax.swing.JOptionPane.showConfirmDialog(frame, 
                        "Delete this comment?", "Confirm", javax.swing.JOptionPane.YES_NO_OPTION);
                    if (confirm == javax.swing.JOptionPane.YES_OPTION) {
                        boolean success = commentPost.deleteComment(comment.getId());
                        if (success) {
                            new Alert("Comment deleted!", frame);
                            loadFeed();
                        } else {
                            new Alert("Failed to delete comment!", frame);
                        }
                    }
                }
            });
        }
        
        return commentCard;
    }
}