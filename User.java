package SocialMediaPlatform; 

import java.util.ArrayList;

public class User{

        private int ID;
        private String firstName;
        private String lastName;
        private String email;
        private String password;
        private ArrayList<Post> posts;
        private ArrayList<Comment> comments;
        private ArrayList<Post> likes;
        private ArrayList<User> friends;

        public User(){}

        public int getID() {
            return ID;
        }

        public void setID(int ID) {
            this.ID = ID;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getName() {
            return firstName + " " + lastName;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public ArrayList<Post> getPosts() {
            return posts;
        }

        public void setPosts(ArrayList<Post> posts) {
            this.posts = posts;
        }

        public ArrayList<Comment> getComments() {
            return comments;
        }

        public void setComments(ArrayList<Comment> comments) {
            this.comments = comments;
        }

        public ArrayList<Post> getLikes() {
            return likes;
        }

        public void setLikes(ArrayList<Post> likes) {
            this.likes = likes;
        }

        public ArrayList<User> getFriends() {
            return friends;
        }

        public void setFriends(ArrayList<User> friends) {
            this.friends = friends;
        }

        public ArrayList<Integer> getFriendsIDs() {
            ArrayList<Integer> ids = new ArrayList<>();
            for (User friend : friends) {
                ids.add(friend.getID());
            }
            return ids;
        }

}
