package View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.time.format.DateTimeFormatter;
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
import Controller.FriendController;
import Controller.LikePost;
import Model.Comment;
import Model.Post;
import Model.User;

public class DashboardGUI {

    private User currentUser;
    private JPanel feedPanel;
    private JFrame frame;
    private JLabel feedTitle;
    private String currentView = "home"; // home | posts | comments | likes
    private JPanel navPosts, navComments, navLikes, navFriends, navHome;

    private static final DateTimeFormatter DATE_FMT = DateTimeFormatter.ofPattern("EEE, dd MMM yyyy");

    public DashboardGUI(User user) {
        this.currentUser = user;

        frame = new JFrame();
        frame.setTitle("Social Media Platform");
        frame.setSize(900, 700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(GUIConstants.darkBg);

        JPanel mainPanel = new JPanel(new BorderLayout(0, 0));
        mainPanel.setBackground(GUIConstants.darkBg);

        // ========== CONTENT PANEL ==========
        JPanel contentPanel = new JPanel(new BorderLayout(15, 0));
        contentPanel.setBackground(GUIConstants.darkBg);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // ========== LEFT: SIDEBAR NAV ==========
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BorderLayout());
        sidebar.setBackground(GUIConstants.darkCard);
        sidebar.setBorder(BorderFactory.createLineBorder(GUIConstants.darkBorder, 1));
        sidebar.setPreferredSize(new Dimension(190, 0));

        JPanel sidebarTop = new JPanel();
        sidebarTop.setLayout(new javax.swing.BoxLayout(sidebarTop, javax.swing.BoxLayout.Y_AXIS));
        sidebarTop.setBackground(GUIConstants.darkCard);
        sidebarTop.setBorder(BorderFactory.createEmptyBorder(20, 15, 10, 15));

        JLabel userName = new JLabel(user.getFirstName());
        userName.setFont(new Font("Segoe UI", Font.BOLD, 16));
        userName.setForeground(GUIConstants.textWhite);
        userName.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
        userName.setCursor(new Cursor(Cursor.HAND_CURSOR));
        userName.setToolTipText("View / edit your profile");
        userName.addMouseListener(new MouseListener() {
            @Override public void mouseReleased(MouseEvent e) {}
            @Override public void mousePressed(MouseEvent e) {}
            @Override public void mouseExited(MouseEvent e) { userName.setForeground(GUIConstants.textWhite); }
            @Override public void mouseEntered(MouseEvent e) { userName.setForeground(GUIConstants.accentBlue); }
            @Override
            public void mouseClicked(MouseEvent e) {
                frame.dispose();
                new ProfileGUI(currentUser);
            }
        });
        sidebarTop.add(userName);
        sidebarTop.add(javax.swing.Box.createRigidArea(new Dimension(0, 20)));

        navHome = createNavItem("Home");
        navPosts = createNavItem("Posts");
        navComments = createNavItem("Comments");
        navLikes = createNavItem("Likes");
        navFriends = createNavItem("Friends");

        sidebarTop.add(navHome);
        sidebarTop.add(navPosts);
        sidebarTop.add(navComments);
        sidebarTop.add(navLikes);
        sidebarTop.add(navFriends);

        sidebar.add(sidebarTop, BorderLayout.NORTH);
        contentPanel.add(sidebar, BorderLayout.WEST);

        // ========== RIGHT: FEED PANEL ==========
        JPanel feedMainPanel = new JPanel(new BorderLayout(0, 15));
        feedMainPanel.setBackground(GUIConstants.darkBg);

        // Top block: title + create-post box, stacked, fixed height
        JPanel topBlock = new JPanel(new BorderLayout(0, 12));
        topBlock.setBackground(GUIConstants.darkBg);

        feedTitle = new JLabel("Home", SwingConstants.LEFT);
        feedTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        feedTitle.setForeground(GUIConstants.textWhite);
        topBlock.add(feedTitle, BorderLayout.NORTH);

        // Create Post
        JPanel createPostPanel = new JPanel(new BorderLayout(5, 8));
        createPostPanel.setBackground(GUIConstants.darkCard);
        createPostPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(GUIConstants.darkBorder, 1),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        JLabel shareLabel = new JLabel("Share your thoughts...");
        shareLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        shareLabel.setForeground(GUIConstants.textGray);
        createPostPanel.add(shareLabel, BorderLayout.NORTH);

        JTextArea postArea = new JTextArea(2, 30);
        postArea.setBackground(GUIConstants.darkBg);
        postArea.setForeground(GUIConstants.textWhite);
        postArea.setCaretColor(GUIConstants.textWhite);
        postArea.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        postArea.setLineWrap(true);
        postArea.setText("What's on your mind?");
        postArea.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(GUIConstants.darkBorder, 1),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        createPostPanel.add(postArea, BorderLayout.CENTER);

        JPanel postBtnPanel = new JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));
        postBtnPanel.setBackground(GUIConstants.darkCard);
        JButton postButton = new JButton("Post");
        postButton.setBackground(GUIConstants.btnPrimary);
        postButton.setForeground(Color.WHITE);
        postButton.setFont(new Font("Segoe UI", Font.BOLD, 13));
        postButton.setFocusPainted(false);
        postButton.setBorder(BorderFactory.createEmptyBorder(6, 20, 6, 20));
        postButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        postBtnPanel.add(postButton);
        createPostPanel.add(postBtnPanel, BorderLayout.SOUTH);

        topBlock.add(createPostPanel, BorderLayout.CENTER);
        feedMainPanel.add(topBlock, BorderLayout.NORTH);

        // Feed
        feedPanel = new JPanel();
        feedPanel.setLayout(new GridLayout(0, 1, 0, 10));
        feedPanel.setBackground(GUIConstants.darkBg);

        JScrollPane scrollPane = new JScrollPane(feedPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(GUIConstants.darkBg);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        feedMainPanel.add(scrollPane, BorderLayout.CENTER);

        contentPanel.add(feedMainPanel, BorderLayout.CENTER);
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        loadFeed();
        highlightNav(navHome);

        // ============================================
        // POST BUTTON ACTION
        // ============================================
        postButton.addMouseListener(new MouseListener() {
            @Override public void mouseReleased(MouseEvent e) {}
            @Override public void mousePressed(MouseEvent e) {}
            @Override public void mouseExited(MouseEvent e) {}
            @Override public void mouseEntered(MouseEvent e) {
                postButton.setBackground(GUIConstants.btnPrimaryHover);
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

        // ============================================
        // NAV ACTIONS
        // ============================================
        navHome.addMouseListener(navClickListener(navHome, "home"));
        navPosts.addMouseListener(navClickListener(navPosts, "posts"));
        navComments.addMouseListener(navClickListener(navComments, "comments"));
        navLikes.addMouseListener(navClickListener(navLikes, "likes"));
        navFriends.addMouseListener(new MouseListener() {
            @Override public void mouseReleased(MouseEvent e) {}
            @Override public void mousePressed(MouseEvent e) {}
            @Override public void mouseExited(MouseEvent e) { navFriends.setBackground(GUIConstants.darkCard); }
            @Override public void mouseEntered(MouseEvent e) { navFriends.setBackground(GUIConstants.darkCardHover); }
            @Override
            public void mouseClicked(MouseEvent e) {
                frame.dispose();
                new FriendsGUI(currentUser);
            }
        });

        frame.getContentPane().add(mainPanel);
        frame.setVisible(true);
        frame.requestFocus();
    }

    // ---------- Sidebar nav item builder ----------
    private JPanel createNavItem(String label) {
        JPanel item = new JPanel(new BorderLayout());
        item.setBackground(GUIConstants.darkCard);
        item.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 8));
        item.setCursor(new Cursor(Cursor.HAND_CURSOR));
        item.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        item.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);

        JLabel textLabel = new JLabel(label);
        textLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        textLabel.setForeground(GUIConstants.textWhite);
        item.add(textLabel, BorderLayout.CENTER);

        return item;
    }

    private void highlightNav(JPanel active) {
        for (JPanel p : new JPanel[]{navHome, navPosts, navComments, navLikes}) {
            p.setBackground(p == active ? GUIConstants.darkCardHover : GUIConstants.darkCard);
        }
    }

    private MouseListener navClickListener(JPanel item, String view) {
        return new MouseListener() {
            @Override public void mouseReleased(MouseEvent e) {}
            @Override public void mousePressed(MouseEvent e) {}
            @Override public void mouseExited(MouseEvent e) {
                if (!view.equals(currentView)) item.setBackground(GUIConstants.darkCard);
            }
            @Override public void mouseEntered(MouseEvent e) {
                item.setBackground(GUIConstants.darkCardHover);
            }
            @Override
            public void mouseClicked(MouseEvent e) {
                currentView = view;
                highlightNav(item);
                loadFeed();
            }
        };
    }

    // ---------- Feed loading with filtering ----------
    private void loadFeed() {
        try {
            feedPanel.removeAll();
            CreatePost createPost = new CreatePost();
            LikePost likePost = new LikePost();
            CommentPost commentPost = new CommentPost();
            ArrayList<Post> allPosts = createPost.getAllPosts();
            ArrayList<Post> posts = new ArrayList<>();

            switch (currentView) {
                case "posts":
                    feedTitle.setText("My Posts");
                    for (Post p : allPosts) {
                        if (p.getUser().getID() == currentUser.getID()) posts.add(p);
                    }
                    break;
                case "comments":
                    feedTitle.setText("Posts I've Commented On");
                    for (Post p : allPosts) {
                        ArrayList<Comment> pc = commentPost.getCommentsForPost(p.getId());
                        if (pc != null) {
                            for (Comment c : pc) {
                                if (c.getUser() != null && c.getUser().getID() == currentUser.getID()) {
                                    posts.add(p);
                                    break;
                                }
                            }
                        }
                    }
                    break;
                case "likes":
                    feedTitle.setText("Posts I've Liked");
                    for (Post p : allPosts) {
                        if (likePost.hasLiked(p.getId(), currentUser.getID())) posts.add(p);
                    }
                    break;
                default:
                    feedTitle.setText("Home");
                    posts = allPosts;
                    break;
            }

            if (posts.isEmpty()) {
                JLabel emptyLabel = new JLabel("No posts yet.");
                emptyLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                emptyLabel.setForeground(GUIConstants.textGray);
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
        JPanel card = new JPanel(new BorderLayout(8, 6));
        card.setBackground(GUIConstants.darkCard);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(GUIConstants.darkBorder, 1),
            BorderFactory.createEmptyBorder(14, 16, 14, 16)
        ));

        int postId = post.getId();

        // Top row: user name (left) + date (right)
        JPanel topRow = new JPanel(new BorderLayout());
        topRow.setBackground(GUIConstants.darkCard);

        JLabel userLabel = new JLabel(post.getUser().getFirstName());
        userLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        userLabel.setForeground(GUIConstants.textWhite);
        topRow.add(userLabel, BorderLayout.WEST);

        if (post.getDateTime() != null) {
            JLabel dateLabel = new JLabel(post.getDateTime().format(DATE_FMT));
            dateLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            dateLabel.setForeground(GUIConstants.textGray);
            topRow.add(dateLabel, BorderLayout.EAST);
        }
        card.add(topRow, BorderLayout.NORTH);

        // Content
        JLabel contentLabel = new JLabel("<html><body style='width:600px'>" + escapeHtml(post.getContent()) + "</body></html>");
        contentLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        contentLabel.setForeground(GUIConstants.textLight);
        contentLabel.setBorder(BorderFactory.createEmptyBorder(6, 0, 6, 0));
        card.add(contentLabel, BorderLayout.CENTER);

        // Bottom row: like heart + comment count (left) | edit/delete (right, owner only)
        JPanel bottomRow = new JPanel(new BorderLayout());
        bottomRow.setBackground(GUIConstants.darkCard);
        bottomRow.setBorder(BorderFactory.createEmptyBorder(6, 0, 0, 0));

        JPanel buttonPanel = new JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 18, 0));
        buttonPanel.setBackground(GUIConstants.darkCard);

        // LIKE (heart icon)
        int likes = likePost.getLikesCount(postId);
        boolean liked = likePost.hasLiked(postId, currentUser.getID());
        String likeWord = likes == 1 ? "Like" : "Likes";
        JLabel likeBtn = new JLabel((liked ? "♥ " : "♡ ") + likes + " " + likeWord);
        likeBtn.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        likeBtn.setForeground(liked ? GUIConstants.accentRed : GUIConstants.textGray);
        likeBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        buttonPanel.add(likeBtn);

        // COMMENT COUNT (click to add a comment)
        int comments = commentPost.getCommentCount(postId);
        String commentWord = comments == 1 ? "Comment" : "Comments";
        JLabel commentBtn = new JLabel(comments + " " + commentWord);
        commentBtn.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        commentBtn.setForeground(GUIConstants.textGray);
        commentBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        buttonPanel.add(commentBtn);

        bottomRow.add(buttonPanel, BorderLayout.WEST);

        // EDIT & DELETE (only for post owner)
        if (post.getUser().getID() == currentUser.getID()) {
            JPanel ownerPanel = new JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 12, 0));
            ownerPanel.setBackground(GUIConstants.darkCard);

            JLabel editBtn = new JLabel("Edit");
            editBtn.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            editBtn.setForeground(GUIConstants.accentBlue);
            editBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            ownerPanel.add(editBtn);

            JLabel deleteBtn = new JLabel("Delete");
            deleteBtn.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            deleteBtn.setForeground(GUIConstants.accentRed);
            deleteBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            ownerPanel.add(deleteBtn);

            bottomRow.add(ownerPanel, BorderLayout.EAST);

            editBtn.addMouseListener(new MouseListener() {
                @Override public void mouseReleased(MouseEvent e) {}
                @Override public void mousePressed(MouseEvent e) {}
                @Override public void mouseExited(MouseEvent e) {}
                @Override public void mouseEntered(MouseEvent e) {}
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

            deleteBtn.addMouseListener(new MouseListener() {
                @Override public void mouseReleased(MouseEvent e) {}
                @Override public void mousePressed(MouseEvent e) {}
                @Override public void mouseExited(MouseEvent e) {}
                @Override public void mouseEntered(MouseEvent e) {}
                @Override
                public void mouseClicked(MouseEvent e) {
                    int confirm = javax.swing.JOptionPane.showConfirmDialog(frame, "Delete this post?", "Confirm", javax.swing.JOptionPane.YES_NO_OPTION);
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

        card.add(bottomRow, BorderLayout.SOUTH);

        // LIKE ACTION
        likeBtn.addMouseListener(new MouseListener() {
            @Override public void mouseReleased(MouseEvent e) {}
            @Override public void mousePressed(MouseEvent e) {}
            @Override public void mouseExited(MouseEvent e) {}
            @Override public void mouseEntered(MouseEvent e) {}
            @Override
            public void mouseClicked(MouseEvent e) {
                boolean success;
                if (liked) {
                    success = likePost.removeLike(postId, currentUser.getID());
                } else {
                    success = likePost.addLike(postId, currentUser.getID());
                }
                if (success) {
                    loadFeed();
                } else {
                    new Alert("Failed to update like!", frame);
                }
            }
        });

        // COMMENT ACTION
        commentBtn.addMouseListener(new MouseListener() {
            @Override public void mouseReleased(MouseEvent e) {}
            @Override public void mousePressed(MouseEvent e) {}
            @Override public void mouseExited(MouseEvent e) {}
            @Override public void mouseEntered(MouseEvent e) {}
            @Override
            public void mouseClicked(MouseEvent e) {
                String comment = javax.swing.JOptionPane.showInputDialog(frame, "Write a comment:");
                if (comment != null && !comment.trim().isEmpty()) {
                    boolean success = commentPost.addComment(postId, currentUser.getID(), comment);
                    if (success) {
                        new Alert("Comment posted!", frame);
                        loadFeed();
                    } else {
                        new Alert("Failed to post comment!", frame);
                    }
                }
            }
        });

        return card;
    }

    private String escapeHtml(String s) {
        if (s == null) return "";
        return s.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
    }
}
