package View;

import java.awt.BorderLayout;
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
import javax.swing.JOptionPane;
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
    
    public DashboardGUI(User user) {
        this.currentUser = user;
        
        frame = new JFrame();
        frame.setSize(900, 625);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(GUIConstants.lightGray);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // TOP PANEL
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(GUIConstants.lightGray);
        
        JLabel welcomeLabel = new JLabel("Welcome, " + user.getFirstName() + " " + user.getLastName() + "!");
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        welcomeLabel.setForeground(GUIConstants.blue);
        topPanel.add(welcomeLabel, BorderLayout.WEST);
        
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightPanel.setBackground(GUIConstants.lightGray);
        
        JButton profileBtn = new JButton("👤 Profile");
        profileBtn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        rightPanel.add(profileBtn);
        
        JButton friendsBtn = new JButton("👥 Friends");
        friendsBtn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        rightPanel.add(friendsBtn);
        
        JButton logoutBtn = new JButton("Logout");
        logoutBtn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        rightPanel.add(logoutBtn);
        
        topPanel.add(rightPanel, BorderLayout.EAST);
        mainPanel.add(topPanel, BorderLayout.NORTH);
        
        // CREATE POST
        JPanel postPanel = new JPanel(new BorderLayout(5, 5));
        postPanel.setBackground(GUIConstants.white);
        postPanel.setBorder(BorderFactory.createTitledBorder("Create Post"));
        
        JTextArea postArea = new JTextArea(3, 30);
        postArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        postArea.setLineWrap(true);
        postArea.setText("What's on your mind?");
        
        JButton postButton = new JButton("Post");
        postButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        postPanel.add(new JScrollPane(postArea), BorderLayout.CENTER);
        postPanel.add(postButton, BorderLayout.EAST);
        mainPanel.add(postPanel, BorderLayout.CENTER);
        
        // FEED
        feedPanel = new JPanel();
        feedPanel.setLayout(new GridLayout(0, 1, 5, 5));
        feedPanel.setBackground(GUIConstants.white);
        
        JScrollPane scrollPane = new JScrollPane(feedPanel);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Feed"));
        mainPanel.add(scrollPane, BorderLayout.SOUTH);
        
        loadFeed();
        
        // Profile Button
        profileBtn.addMouseListener(new MouseListener() {
            @Override public void mouseReleased(MouseEvent e) {}
            @Override public void mousePressed(MouseEvent e) {}
            @Override public void mouseExited(MouseEvent e) {}
            @Override public void mouseEntered(MouseEvent e) {}
            @Override
            public void mouseClicked(MouseEvent e) {
                frame.dispose();
                new ProfileGUI(currentUser);
            }
        });
        
        // Friends Button
        friendsBtn.addMouseListener(new MouseListener() {
            @Override public void mouseReleased(MouseEvent e) {}
            @Override public void mousePressed(MouseEvent e) {}
            @Override public void mouseExited(MouseEvent e) {}
            @Override public void mouseEntered(MouseEvent e) {}
            @Override
            public void mouseClicked(MouseEvent e) {
                new FriendsGUI(currentUser);
            }
        });
        
        // Logout Button
        logoutBtn.addMouseListener(new MouseListener() {
            @Override public void mouseReleased(MouseEvent e) {}
            @Override public void mousePressed(MouseEvent e) {}
            @Override public void mouseExited(MouseEvent e) {}
            @Override public void mouseEntered(MouseEvent e) {}
            @Override
            public void mouseClicked(MouseEvent e) {
                frame.dispose();
                new LoginGUI();
            }
        });
        
        // Post Button
        postButton.addMouseListener(new MouseListener() {
            @Override public void mouseReleased(MouseEvent e) {}
            @Override public void mousePressed(MouseEvent e) {}
            @Override public void mouseExited(MouseEvent e) {}
            @Override public void mouseEntered(MouseEvent e) {}
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
        card.setBackground(GUIConstants.white);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(GUIConstants.lightGray, 1),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        
        int postId = post.getId();
        
        // USER NAME
        JLabel userLabel = new JLabel("👤 " + post.getUser().getFirstName() + " " + post.getUser().getLastName());
        userLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        userLabel.setForeground(GUIConstants.black);
        card.add(userLabel, BorderLayout.NORTH);
        
        // CONTENT
        JLabel contentLabel = new JLabel(post.getContent());
        contentLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        contentLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        card.add(contentLabel, BorderLayout.CENTER);
        
        // BUTTONS PANEL
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.setBackground(GUIConstants.white);
        
        // LIKE BUTTON
        int likes = likePost.getLikesCount(postId);
        boolean liked = likePost.hasLiked(postId, currentUser.getID());
        JButton likeBtn = new JButton((liked ? "Unlike" : "Like") + " (" + likes + ")");
        likeBtn.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        buttonPanel.add(likeBtn);
        
        // COMMENT BUTTON
        int comments = commentPost.getCommentCount(postId);
        JButton commentBtn = new JButton("Comment (" + comments + ")");
        commentBtn.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        buttonPanel.add(commentBtn);
        
        // EDIT & DELETE (Only for post owner)
        if (post.getUser().getID() == currentUser.getID()) {
            JButton editBtn = new JButton("Edit");
            editBtn.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            buttonPanel.add(editBtn);
            
            JButton deleteBtn = new JButton("Delete");
            deleteBtn.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            deleteBtn.setForeground(GUIConstants.red);
            buttonPanel.add(deleteBtn);
            
            editBtn.addMouseListener(new MouseListener() {
                @Override public void mouseReleased(MouseEvent e) {}
                @Override public void mousePressed(MouseEvent e) {}
                @Override public void mouseExited(MouseEvent e) {}
                @Override public void mouseEntered(MouseEvent e) {}
                @Override
                public void mouseClicked(MouseEvent e) {
                    String newContent = JOptionPane.showInputDialog(frame, "Edit post:", post.getContent());
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
            
            deleteBtn.addMouseListener(new MouseListener() {
                @Override public void mouseReleased(MouseEvent e) {}
                @Override public void mousePressed(MouseEvent e) {}
                @Override public void mouseExited(MouseEvent e) {}
                @Override public void mouseEntered(MouseEvent e) {}
                @Override
                public void mouseClicked(MouseEvent e) {
                    int confirm = JOptionPane.showConfirmDialog(frame, "Delete this post?", "Confirm", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
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
        
        // LIKE ACTION
        likeBtn.addMouseListener(new MouseListener() {
            @Override public void mouseReleased(MouseEvent e) {}
            @Override public void mousePressed(MouseEvent e) {}
            @Override public void mouseExited(MouseEvent e) {}
            @Override public void mouseEntered(MouseEvent e) {}
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
        
        // COMMENT SECTION (hidden by default)
        JPanel commentSection = new JPanel(new BorderLayout(5, 5));
        commentSection.setBackground(GUIConstants.white);
        commentSection.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        commentSection.setVisible(false);
        
        JTextArea commentArea = new JTextArea(2, 20);
        commentArea.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        commentArea.setLineWrap(true);
        commentArea.setWrapStyleWord(true);
        commentArea.setText("Write a comment...");
        commentSection.add(new JScrollPane(commentArea), BorderLayout.CENTER);
        
        JButton submitComment = new JButton("Post Comment");
        submitComment.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        commentSection.add(submitComment, BorderLayout.EAST);
        
        // COMMENT BUTTON ACTION
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
        
        // SUBMIT COMMENT ACTION
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
        
        // ✅ FIX: BOTTOM CONTAINER - BOTH BUTTON PANEL AND COMMENT SECTION
        JPanel bottomContainer = new JPanel();
        bottomContainer.setLayout(new GridLayout(2, 1, 0, 5));
        bottomContainer.setBackground(GUIConstants.white);
        
        bottomContainer.add(buttonPanel);
        bottomContainer.add(commentSection);
        
        card.add(bottomContainer, BorderLayout.SOUTH);
        
        return card;
    }
    
    private void loadComments(JPanel parent, int postId, CommentPost commentPost) {
        parent.removeAll();
        
        ArrayList<Comment> comments = commentPost.getCommentsForPost(postId);
        JPanel commentsPanel = new JPanel(new GridLayout(0, 1, 5, 5));
        commentsPanel.setBackground(GUIConstants.white);
        
        if (comments.isEmpty()) {
            JLabel emptyLabel = new JLabel("No comments yet.");
            emptyLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
            emptyLabel.setForeground(GUIConstants.black);
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
        commentCard.setBackground(GUIConstants.lightGray);
        commentCard.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        
        JLabel commentUser = new JLabel("👤 " + comment.getUser().getFirstName() + " " + comment.getUser().getLastName());
        commentUser.setFont(new Font("Segoe UI", Font.BOLD, 11));
        commentUser.setForeground(GUIConstants.blue);
        commentCard.add(commentUser, BorderLayout.NORTH);
        
        JLabel commentContent = new JLabel(comment.getContent());
        commentContent.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        commentCard.add(commentContent, BorderLayout.CENTER);
        
        if (comment.getUser().getID() == currentUser.getID()) {
            JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            actionPanel.setBackground(GUIConstants.lightGray);
            
            JButton editBtn = new JButton("Edit");
            editBtn.setFont(new Font("Segoe UI", Font.PLAIN, 10));
            actionPanel.add(editBtn);
            
            JButton deleteBtn = new JButton("Delete");
            deleteBtn.setFont(new Font("Segoe UI", Font.PLAIN, 10));
            deleteBtn.setForeground(GUIConstants.red);
            actionPanel.add(deleteBtn);
            
            commentCard.add(actionPanel, BorderLayout.EAST);
            
            editBtn.addMouseListener(new MouseListener() {
                @Override public void mouseReleased(MouseEvent e) {}
                @Override public void mousePressed(MouseEvent e) {}
                @Override public void mouseExited(MouseEvent e) {}
                @Override public void mouseEntered(MouseEvent e) {}
                @Override
                public void mouseClicked(MouseEvent e) {
                    String newContent = JOptionPane.showInputDialog(frame, "Edit comment:", comment.getContent());
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
                    int confirm = JOptionPane.showConfirmDialog(frame, "Delete this comment?", "Confirm", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
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