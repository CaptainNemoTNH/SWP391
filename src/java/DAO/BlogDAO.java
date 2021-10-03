package DAO;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import Utils.DBHelpers;
import java.sql.Date;
import java.sql.Statement;

public class BlogDAO implements Serializable {

    /**
     * Create blog
     *
     * @param dto BlogDTO
     * @return Id of created Blog if success, 0 if failed
     * @throws SQLException
     */
    public int createBlog(BlogDTO dto) throws SQLException {
        Connection con = null;
        PreparedStatement stm = null;

        try {
            // 1. Connect DB
            con = DBHelpers.makeConnection();
            // 2. Create SQL String
            if (con != null) {
                String sql = "INSERT INTO Blog (title, content, postDate, categoryID, status, tags, ownerID) "
                        + "VALUES (?, ?, ?, ?, ?, ?, ?)";
                // 3. Create statement object
                stm = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                stm.setString(1, dto.getTitle());
                stm.setString(2, dto.getContent());
                stm.setDate(3, dto.getPostDate());
                stm.setString(4, dto.getCategoryID());
                stm.setString(5, dto.getStatus());
                stm.setString(6, dto.getTags());
                stm.setInt(7, dto.getStudentID());

                int line = stm.executeUpdate();

                if (line > 0) {
                    ResultSet keys = stm.getGeneratedKeys();
                    if (keys.next()) {
                        int key = (int) stm.getGeneratedKeys().getLong(1);
                        System.out.println(key);
                        return key;
                    }
                    return 0;
                }
            } // End connection
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {

        }
        return 0;
    }

    /**
     * Get block using blogID
     *
     * @param blogId ID of the blog
     * @return BlogDTO if found, null if not found
     * @throws SQLException
     */
    public BlogDTO getBlogFromBlogID(int blogId) throws SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            BlogDTO dto = null;

            con = DBHelpers.makeConnection();

            if (con != null) {
                String sql = "SELECT blogID, title, content,postDate, categoryID, status, approvedByID, approvedDate, tags, ownerID "
                        + "FROM Blog "
                        + "WHERE blogID = ?";

                stm = con.prepareStatement(sql);
                stm.setInt(1, blogId);

                rs = stm.executeQuery();

                while (rs.next()) {
                    int blogID = rs.getInt("blogID");
                    String title = rs.getString("title");
                    String content = rs.getString("content");
                    Date postDate = rs.getDate("postDate");
                    String categoryID = rs.getString("categoryID");
                    String status = rs.getString("status");
                    int approvedByID = rs.getInt("approvedByID");
                    Date approvedDate = rs.getDate("approvedDate");
                    String tags = rs.getString("tags");
                    int ownerID = rs.getInt("ownerID");

                    dto = new BlogDTO(blogID, title, content, postDate, categoryID, status, approvedByID, approvedDate, tags, ownerID);
                }

                return dto;
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return null;
    }

    public ArrayList<BlogDTO> getAllBlogFromAccountId(int accountId) throws SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            ArrayList<BlogDTO> blogList = new ArrayList<BlogDTO>();

            con = DBHelpers.makeConnection();

            if (con != null) {
                String sql = "SELECT blogID, title, content, postDate, categoryID, status, approvedByID, approvedDate, tags, ownerID "
                        + "FROM Blog "
                        + "WHERE ownerID = ? "
                        + "ORDER BY postDate DESC";

                stm = con.prepareStatement(sql);
                stm.setInt(1, accountId);
                rs = stm.executeQuery();

                while (rs.next()) {
                    int blogID = rs.getInt("blogID");
                    String title = rs.getString("title");
                    String content = rs.getString("content");
                    Date postDate = rs.getDate("postDate");
                    String categoryID = rs.getString("categoryID");
                    String status = rs.getString("status");
                    int approvedByID = rs.getInt("approvedByID");
                    Date approvedDate = rs.getDate("approvedDate");
                    String tags = rs.getString("tags");
                    int ownerID = rs.getInt("ownerID");

                    BlogDTO dto = new BlogDTO(blogID, title, content, postDate, categoryID, status, approvedByID, approvedDate, tags, ownerID);
                    blogList.add(dto);
                }

                return blogList;
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return null;
    }

    public ArrayList<BlogDTO> getAllBlogFromCategoryId(String categoryId) throws SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            ArrayList<BlogDTO> blogList = new ArrayList<BlogDTO>();

            con = DBHelpers.makeConnection();

            if (con != null) {
                String sql = "SELECT blogID, title, content, postDate, categoryID, status, approvedByID, approvedDate, tags, ownerID "
                        + "FROM Blog "
                        + "WHERE categoryID = ? "
                        + "ORDER BY postDate DESC";

                stm = con.prepareStatement(sql);
                stm.setString(1, categoryId);
                rs = stm.executeQuery();

                while (rs.next()) {
                    int blogID = rs.getInt("blogID");
                    String title = rs.getString("title");
                    String content = rs.getString("content");
                    Date postDate = rs.getDate("postDate");
                    String categoryID = rs.getString("categoryID");
                    String status = rs.getString("status");
                    int approvedByID = rs.getInt("approvedByID");
                    Date approvedDate = rs.getDate("approvedDate");
                    String tags = rs.getString("tags");
                    int ownerID = rs.getInt("ownerID");

                    BlogDTO dto = new BlogDTO(blogID, title, content, postDate, categoryID, status, approvedByID, approvedDate, tags, ownerID);
                    blogList.add(dto);
                }

                return blogList;
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return null;
    }

    public ArrayList<BlogDTO> getAllBlogLikeTitle(String searchTitle) throws SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            ArrayList<BlogDTO> blogList = new ArrayList<BlogDTO>();

            con = DBHelpers.makeConnection();

            if (con != null) {
                String sql = "SELECT blogID, title, content, postDate, categoryID, status, approvedByID, approvedDate, tags, ownerID "
                        + "FROM Blog "
                        + "WHERE title LIKE ? "
                        + "ORDER BY postDate DESC";

                stm = con.prepareStatement(sql);
                stm.setString(1, "%" + searchTitle + "%");
                rs = stm.executeQuery();

                while (rs.next()) {
                    int blogID = rs.getInt("blogID");
                    String title = rs.getString("title");
                    String content = rs.getString("content");
                    Date postDate = rs.getDate("postDate");
                    String categoryID = rs.getString("categoryID");
                    String status = rs.getString("status");
                    int approvedByID = rs.getInt("approvedByID");
                    Date approvedDate = rs.getDate("approvedDate");
                    String tags = rs.getString("tags");
                    int ownerID = rs.getInt("ownerID");

                    BlogDTO dto = new BlogDTO(blogID, title, content, postDate, categoryID, status, approvedByID, approvedDate, tags, ownerID);
                    blogList.add(dto);
                }

                return blogList;
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return null;
    }

    public ArrayList<BlogDTO> getAllBlogLikeTag(String searchTag) throws SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            ArrayList<BlogDTO> blogList = new ArrayList<BlogDTO>();

            con = DBHelpers.makeConnection();

            if (con != null) {
                String sql = "SELECT blogID, title, content, postDate, categoryID, status, approvedByID, approvedDate, tags, ownerID "
                        + "FROM Blog "
                        + "WHERE tags LIKE ? "
                        + "ORDER BY postDate DESC";

                stm = con.prepareStatement(sql);
                stm.setString(1, "%" + searchTag + "%");
                rs = stm.executeQuery();

                while (rs.next()) {
                    int blogID = rs.getInt("blogID");
                    String title = rs.getString("title");
                    String content = rs.getString("content");
                    Date postDate = rs.getDate("postDate");
                    String categoryID = rs.getString("categoryID");
                    String status = rs.getString("status");
                    int approvedByID = rs.getInt("approvedByID");
                    Date approvedDate = rs.getDate("approvedDate");
                    String tags = rs.getString("tags");
                    int ownerID = rs.getInt("ownerID");

                    BlogDTO dto = new BlogDTO(blogID, title, content, postDate, categoryID, status, approvedByID, approvedDate, tags, ownerID);
                    blogList.add(dto);
                }

                return blogList;
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return null;
    }

    public ArrayList<BlogDTO> getAllBlogLikeTitleAndFromCategoryID(String searchTitle, String categoryId) throws SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            ArrayList<BlogDTO> blogList = new ArrayList<BlogDTO>();

            con = DBHelpers.makeConnection();

            if (con != null) {
                String sql = "SELECT blogID, title, content, postDate, categoryID, status, approvedByID, approvedDate, tags, ownerID "
                        + "FROM Blog "
                        + "WHERE title LIKE ? AND categoryID = ? "
                        + "ORDER BY postDate DESC";

                stm = con.prepareStatement(sql);
                stm.setString(1, "%" + searchTitle + "%");
                stm.setString(2, categoryId);
                rs = stm.executeQuery();

                while (rs.next()) {
                    int blogID = rs.getInt("blogID");
                    String title = rs.getString("title");
                    String content = rs.getString("content");
                    Date postDate = rs.getDate("postDate");
                    String categoryID = rs.getString("categoryID");
                    String status = rs.getString("status");
                    int approvedByID = rs.getInt("approvedByID");
                    Date approvedDate = rs.getDate("approvedDate");
                    String tags = rs.getString("tags");
                    int ownerID = rs.getInt("ownerID");

                    BlogDTO dto = new BlogDTO(blogID, title, content, postDate, categoryID, status, approvedByID, approvedDate, tags, ownerID);
                    blogList.add(dto);
                }

                return blogList;
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return null;
    }

    public ArrayList<BlogDTO> getAllBlogWithMostAward(int maxBlog) throws SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            ArrayList<BlogDTO> blogList = new ArrayList<BlogDTO>();

            con = DBHelpers.makeConnection();

            if (con != null) {
                String sql = "SELECT blogID, title, content, postDate, categoryID, status, approvedByID, approvedDate, tags, ownerID "
                        + "FROM getAllBlogWithMostAward(?) "
                        + "WHERE status = ? ";

                stm = con.prepareStatement(sql);
                stm.setInt(1, maxBlog);
                stm.setString(2, "AVAILABLE");
                rs = stm.executeQuery();

                while (rs.next()) {
                    int blogID = rs.getInt("blogID");
                    String title = rs.getString("title");
                    String content = rs.getString("content");
                    Date postDate = rs.getDate("postDate");
                    String categoryID = rs.getString("categoryID");
                    String status = rs.getString("status");
                    int approvedByID = rs.getInt("approvedByID");
                    Date approvedDate = rs.getDate("approvedDate");
                    String tags = rs.getString("tags");
                    int ownerID = rs.getInt("ownerID");

                    BlogDTO dto = new BlogDTO(blogID, title, content, postDate, categoryID, status, approvedByID, approvedDate, tags, ownerID);
                    blogList.add(dto);
                }

                return blogList;
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return null;
    }

    public ArrayList<BlogDTO> getAllBlogWithHighestRating(int maxBlog) throws SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            ArrayList<BlogDTO> blogList = new ArrayList<BlogDTO>();

            con = DBHelpers.makeConnection();

            if (con != null) {
                String sql = "SELECT blogID, title, content, postDate, categoryID, status, approvedByID, approvedDate, tags, ownerID "
                        + "FROM getAllBlogWithHighestRating(?) "
                        + "WHERE status = ? ";

                stm = con.prepareStatement(sql);
                stm.setInt(1, maxBlog);
                stm.setString(2, "AVAILABLE");
                rs = stm.executeQuery();

                while (rs.next()) {
                    int blogID = rs.getInt("blogID");
                    String title = rs.getString("title");
                    String content = rs.getString("content");
                    Date postDate = rs.getDate("postDate");
                    String categoryID = rs.getString("categoryID");
                    String status = rs.getString("status");
                    int approvedByID = rs.getInt("approvedByID");
                    Date approvedDate = rs.getDate("approvedDate");
                    String tags = rs.getString("tags");
                    int ownerID = rs.getInt("ownerID");

                    BlogDTO dto = new BlogDTO(blogID, title, content, postDate, categoryID, status, approvedByID, approvedDate, tags, ownerID);
                    blogList.add(dto);
                }

                return blogList;
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return null;
    }

    public ArrayList<BlogDTO> getAllAvailableBlog() throws SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            ArrayList<BlogDTO> blogList = new ArrayList<BlogDTO>();

            con = DBHelpers.makeConnection();

            if (con != null) {
                String sql = "SELECT blogID, title, content, postDate, categoryID, status, approvedByID, approvedDate, tags, ownerID "
                        + "FROM Blog "
                        + "WHERE status = ? "
                        + "ORDER BY postDate DESC";

                stm = con.prepareStatement(sql);
                stm.setString(1, "AVAILABLE");
                rs = stm.executeQuery();

                while (rs.next()) {
                    int blogID = rs.getInt("blogID");
                    String title = rs.getString("title");
                    String content = rs.getString("content");
                    Date postDate = rs.getDate("postDate");
                    String categoryID = rs.getString("categoryID");
                    String status = rs.getString("status");
                    int approvedByID = rs.getInt("approvedByID");
                    Date approvedDate = rs.getDate("approvedDate");
                    String tags = rs.getString("tags");
                    int ownerID = rs.getInt("ownerID");

                    BlogDTO dto = new BlogDTO(blogID, title, content, postDate, categoryID, status, approvedByID, approvedDate, tags, ownerID);
                    blogList.add(dto);
                }

                return blogList;
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return null;
    }

    public ArrayList<BlogDTO> getAllPendingBlog() throws SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            ArrayList<BlogDTO> blogList = new ArrayList<BlogDTO>();

            con = DBHelpers.makeConnection();

            if (con != null) {
                String sql = "SELECT blogID, title, content, postDate, categoryID, status, approvedByID, approvedDate, tags, ownerID "
                        + "FROM Blog "
                        + "WHERE status = ? "
                        + "ORDER BY postDate DESC";

                stm = con.prepareStatement(sql);
                stm.setString(1, "PENDING");
                rs = stm.executeQuery();

                while (rs.next()) {
                    int blogID = rs.getInt("blogID");
                    String title = rs.getString("title");
                    String content = rs.getString("content");
                    Date postDate = rs.getDate("postDate");
                    String categoryID = rs.getString("categoryID");
                    String status = rs.getString("status");
                    int approvedByID = rs.getInt("approvedByID");
                    Date approvedDate = rs.getDate("approvedDate");
                    String tags = rs.getString("tags");
                    int ownerID = rs.getInt("ownerID");

                    BlogDTO dto = new BlogDTO(blogID, title, content, postDate, categoryID, status, approvedByID, approvedDate, tags, ownerID);
                    blogList.add(dto);
                }

                return blogList;
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return null;
    }

    public ArrayList<BlogDTO> getAllAvailableBlogFromCategoryID(String categoryId) throws SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            ArrayList<BlogDTO> blogList = new ArrayList<BlogDTO>();

            con = DBHelpers.makeConnection();

            if (con != null) {
                String sql = "SELECT blogID, title, content, postDate, categoryID, status, approvedByID, approvedDate, tags, ownerID "
                        + "FROM Blog "
                        + "WHERE status = ? AND categoryID = ? "
                        + "ORDER BY postDate DESC";

                stm = con.prepareStatement(sql);
                stm.setString(1, "APPROVED");
                stm.setString(2, categoryId);
                rs = stm.executeQuery();

                while (rs.next()) {
                    int blogID = rs.getInt("blogID");
                    String title = rs.getString("title");
                    String content = rs.getString("content");
                    Date postDate = rs.getDate("postDate");
                    String categoryID = rs.getString("categoryID");
                    String status = rs.getString("status");
                    int approvedByID = rs.getInt("approvedByID");
                    Date approvedDate = rs.getDate("approvedDate");
                    String tags = rs.getString("tags");
                    int ownerID = rs.getInt("ownerID");

                    BlogDTO dto = new BlogDTO(blogID, title, content, postDate, categoryID, status, approvedByID, approvedDate, tags, ownerID);
                    blogList.add(dto);
                }

                return blogList;
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return null;
    }

    public ArrayList<BlogDTO> getAllPendingBlogFromCategoryID(String categoryId) throws SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            ArrayList<BlogDTO> blogList = new ArrayList<BlogDTO>();

            con = DBHelpers.makeConnection();

            if (con != null) {
                String sql = "SELECT blogID, title, content, postDate, categoryID, status, approvedByID, approvedDate, tags, ownerID "
                        + "FROM Blog "
                        + "WHERE status = ? AND categoryID = ? "
                        + "ORDER BY postDate DESC";

                stm = con.prepareStatement(sql);
                stm.setString(1, "PENDING");
                stm.setString(2, categoryId);
                rs = stm.executeQuery();

                while (rs.next()) {
                    int blogID = rs.getInt("blogID");
                    String title = rs.getString("title");
                    String content = rs.getString("content");
                    Date postDate = rs.getDate("postDate");
                    String categoryID = rs.getString("categoryID");
                    String status = rs.getString("status");
                    int approvedByID = rs.getInt("approvedByID");
                    Date approvedDate = rs.getDate("approvedDate");
                    String tags = rs.getString("tags");
                    int ownerID = rs.getInt("ownerID");

                    BlogDTO dto = new BlogDTO(blogID, title, content, postDate, categoryID, status, approvedByID, approvedDate, tags, ownerID);
                    blogList.add(dto);
                }

                return blogList;
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return null;
    }

    /**
     * Get all blog in the database
     *
     * @return ArrayList<BlogDTO> if found, NULL if not found
     * @throws SQLException
     */
    public ArrayList<BlogDTO> getAllBlogs() throws SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            ArrayList<BlogDTO> blogList = new ArrayList<BlogDTO>();

            con = DBHelpers.makeConnection();

            if (con != null) {
                String sql = "SELECT blogID, title, content, postDate, categoryID, status, approvedByID, approvedDate, tags, ownerID "
                        + "FROM Blog "
                        + "ORDER BY postDate DESC";

                stm = con.prepareStatement(sql);
                rs = stm.executeQuery();

                while (rs.next()) {
                    int blogID = rs.getInt("blogID");
                    String title = rs.getString("title");
                    String content = rs.getString("content");
                    Date postDate = rs.getDate("postDate");
                    String categoryID = rs.getString("categoryID");
                    String status = rs.getString("status");
                    int approvedByID = rs.getInt("approvedByID");
                    Date approvedDate = rs.getDate("approvedDate");
                    String tags = rs.getString("tags");
                    int ownerID = rs.getInt("ownerID");

                    BlogDTO dto = new BlogDTO(blogID, title, content, postDate, categoryID, status, approvedByID, approvedDate, tags, ownerID);
                    blogList.add(dto);
                }

                return blogList;
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return null;
    }

    public boolean updateBlog(int blogId, BlogDTO dto) throws SQLException {
        Connection con = null;
        PreparedStatement stm = null;

        try {
            // 1. Connect DB
            con = DBHelpers.makeConnection();
            // 2. Create SQL String
            if (con != null) {
                String sql = "UPDATE Blog "
                        + "SET title = ?, content = ?, postDate = ?, categoryID = ?, status = ?, approvedByID = ?, approvedDate = ?, tags = ?, ownerID = ? "
                        + "WHERE blogID = ? ";
                // 3. Create statement object
                stm = con.prepareStatement(sql);

                stm.setString(1, dto.getTitle());
                stm.setString(2, dto.getContent());
                stm.setDate(3, dto.getPostDate());
                stm.setString(4, dto.getCategoryID());
                stm.setString(5, dto.getStatus());
                stm.setInt(6, dto.getMentorID());
                stm.setDate(7, dto.getApprovedDate());
                stm.setString(8, dto.getTags());
                stm.setInt(9, dto.getStudentID());

                stm.setInt(10, blogId);
                int line = stm.executeUpdate();

                return line > 0;
            } // End connection
        } finally {

        }
        return false;
    }

    public boolean setBlogStatusFromBlogID(int blogId, String status) throws SQLException {
        Connection con = null;
        PreparedStatement stm = null;

        try {
            // 1. Connect DB
            con = DBHelpers.makeConnection();
            // 2. Create SQL String
            if (con != null) {
                String sql = "UPDATE Blog "
                        + "SET status = ?  "
                        + "WHERE blogID = ? ";
                // 3. Create statement object
                stm = con.prepareStatement(sql);

                stm.setString(1, status);

                stm.setInt(2, blogId);
                int line = stm.executeUpdate();

                return line > 0;
            } // End connection
        } finally {

        }
        return false;
    }

    public boolean activateBlog(int blogId) throws SQLException {
        Connection con = null;
        PreparedStatement stm = null;

        try {
            // 1. Connect DB
            con = DBHelpers.makeConnection();
            // 2. Create SQL String
            if (con != null) {
                String sql = "UPDATE Blog "
                        + "SET status = ? "
                        + "WHERE blogID = ?";
                // 3. Create statement object
                stm = con.prepareStatement(sql);
                stm.setString(1, "AVAILABLE");
                stm.setInt(2, blogId);
                int line = stm.executeUpdate();

                return line > 0;
            } // End connection
        } finally {

        }
        return false;
    }

    public boolean deactivateBlog(int blogId) throws SQLException {
        Connection con = null;
        PreparedStatement stm = null;

        try {
            // 1. Connect DB
            con = DBHelpers.makeConnection();
            // 2. Create SQL String
            if (con != null) {
                String sql = "UPDATE Blog "
                        + "SET status = ? "
                        + "WHERE blogID = ?";
                // 3. Create statement object
                stm = con.prepareStatement(sql);
                stm.setString(1, "UNAVAILABLE");
                stm.setInt(2, blogId);
                int line = stm.executeUpdate();

                return line > 0;
            } // End connection
        } finally {

        }
        return false;
    }

}
