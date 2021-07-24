/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import category.Category;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import map.UserCategoryMap;
import map.UserCategoryMapDAO;
import post_management.comment.CommentDAO;
import post_management.like.PostLikeDAO;
import post_management.post.Post;
import post_management.post.PostDAO;
import subscription_management.tier.Tier;
import subscription_management.tier.TierDAO;
import user_management.follow.Follow;
import user_management.follow.FollowDAO;
import user_management.user.User;
import user_management.user.UserDAO;

/**
 *
 * @author chiuy
 */
@WebServlet(name = "CreatorInfoServlet", urlPatterns = {"/CreatorInfoServlet"})
public class CreatorInfoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        UserDAO userDAO = new UserDAO();
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("user");
        User creator = userDAO.getUserByUsername(username);
        PostDAO dao = new PostDAO();
        boolean subscribed = false;
        if (currentUser != null)
            subscribed = checkSubscribed(creator, currentUser);
        //get Posts
        int page = 0;
        String strPage = request.getParameter("page");
        if (strPage == null)
            page = 1;
        else
            page = Integer.parseInt(strPage);
        TreeMap<Post, int[]> postMap = getPosts(creator, currentUser, page);
        //pagination
        int count = dao.countPostsByUser(creator);
        int pageSize = 4;
        int endPage = count / pageSize;
        if (count % pageSize != 0)
            endPage++;
//        getPosts(request, currentUser, creator);
//        getTiersByCreator(creator, currentUser, request);

        //check if user is following this creator
        boolean followed = false;
        if (currentUser != null && !currentUser.getUsername().equalsIgnoreCase(creator.getUsername())) {
            FollowDAO followDAO = new FollowDAO();
            Follow follow = followDAO.getFollow(currentUser, creator);
            if (follow != null)
                followed = true;
        }
        //get followers count, subscribers count, categories
        int followerCount = userDAO.countFollowers(creator);
        int subCount = userDAO.countSubscribers(creator);
        getCategories(request, creator);

        //get Tiers
        List<Tier> tiers = new TierDAO().getTiersByUser(creator);
        System.out.println(tiers.size());
        request.setAttribute("count", count);
        request.setAttribute("tiers", tiers);
        request.setAttribute("subscribed", subscribed);
        request.setAttribute("postList", postMap);
        request.setAttribute("followed", followed);
        request.setAttribute("followCount", followerCount);
        request.setAttribute("subCount", subCount);
        request.setAttribute("creator", creator);
        request.setAttribute("end", endPage);
        request.getRequestDispatcher("creator_info.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (request.getAttribute("tiererror") != null)
            doGet(request, response);
    }

    private boolean checkSubscribed(User creator, User current) {
        TierDAO tierDAO = new TierDAO();
        ArrayList<Tier> creatorList = tierDAO.getTiersByUser(creator);
        //if user is logged in
        //check if user has subscribed to any tier of the creator
        ArrayList<Tier> currentList = tierDAO.getTiersBySubscription(current);
        boolean result = false;
        outerLoop:
        for (Tier creatorTier : creatorList)
            for (Tier tier : currentList)
                if (creatorTier.getTierId() == tier.getTierId()) {
                    result = true;
                    break outerLoop;
                }
        return result;
    }

    private TreeMap<Post, int[]> getPosts(User creator, User user, int page) {
        PostDAO dao = new PostDAO();
        List<Post> postList = dao.getPostsByUserPage(creator, page);
        TreeMap<Post, int[]> postMap = new TreeMap<>();
        PostLikeDAO likeDAO = new PostLikeDAO();
        CommentDAO cmtDAO = new CommentDAO();
        postList.forEach(post -> {
            int allowed = checkTier(post, user);
            int likeCount = likeDAO.countPostLikeByPost(post);
            int cmtCount = cmtDAO.countCommentsByPost(post.getPostId());
            int[] value = {likeCount, cmtCount, allowed};
            postMap.put(post, value);
        });
        return postMap;
    }

//    private void getPosts(HttpServletRequest request, User currentUser, User creator) {
//        PostDAO dao = new PostDAO();
//        PostLikeDAO likeDAO = new PostLikeDAO();
//        CommentDAO cmtDAO = new CommentDAO();
//        TierDAO tierDAO = new TierDAO();
//        String pageStr = request.getParameter("page");
//        int pageIndex = 0;
//        if (pageStr == null)
//            pageIndex = 1;
//        else
//            pageIndex = Integer.parseInt(pageStr);
//        ArrayList<Post> postList = dao.getPostsByUserPage(creator, pageIndex);
////        System.out.println(postList.size());
//        TreeMap<Post, int[]> postMap = new TreeMap<>();
//        postList.forEach(post -> {
//            int allowed = 0;
//            List<Tier> postTiers = tierDAO.getTiersByPost(post);
//            if (postTiers.size() <= 0)
//                allowed = 1;
//            else if (currentUser != null && currentUser.getUsername().equals(post.getUploader().getUsername()))
//                allowed = 1;
//            else
//                allowed = checkTier(postTiers, currentUser);
//            int likeCount = likeDAO.countPostLikeByPost(post);
//            int cmtCount = cmtDAO.countCommentsByPost(post.getPostId());
//            int[] value = {likeCount, cmtCount, allowed};
//            postMap.put(post, value);
//        });
//        int count = dao.countPostsByUser(creator);
//        int pageSize = 4;
//        int endPage = count / pageSize;
//        if (count % pageSize != 0)
//            endPage++;
//        request.setAttribute("end", endPage);
//        request.setAttribute("count", count);
//        request.setAttribute("postList", postMap);
//        postMap.forEach((p, v) -> {
//            System.out.println(p.getPostId() + "-" + v[2]);
//        });
//    }
//    private void getOwnPost(HttpServletRequest request, User creator) {
//        PostDAO dao = new PostDAO();
//        PostLikeDAO likeDAO = new PostLikeDAO();
//        String pageStr = request.getParameter("page");
//        CommentDAO cDAO = new CommentDAO();
//        int pageIndex = 0;
//        if (pageStr == null)
//            pageIndex = 1;
//        else
//            pageIndex = Integer.parseInt(pageStr);
//        ArrayList<Post> postList = dao.getPostsByUserPage(creator, pageIndex);
//        TreeMap<Post, int[]> map = new TreeMap<>();
//        for (Post post : postList) {
//            int likeCount = likeDAO.countPostLikeByPost(post);
//            int cmtCount = cDAO.countCommentsByPost(post.getPostId());
//            int[] arr = {
//                likeCount, cmtCount
//            };
//            map.put(post, arr);
//        }
//        int pageSize = 3;
//        int count = dao.countPostsByUser(creator);
//        int endPage = count / pageSize;
//        if (count % pageSize != 0)
//            endPage++;
//        request.setAttribute("CreatorInfoServletFlag", "flag");
//        request.setAttribute("postList", map);
//        request.setAttribute("end", endPage);
//    }
    private void getCategories(HttpServletRequest request, User creator) {
        UserCategoryMapDAO dao = new UserCategoryMapDAO();
        List<Category> lst = dao.getCategoriesByUser(creator);
        request.setAttribute("cateList", lst);
    }

    private int checkTier(Post post, User user) {
        TierDAO tierDAO = new TierDAO();
        List<Tier> postTiers = tierDAO.getTiersByPost(post);
        if (postTiers.size() <= 0)
            return 1;
        else if (user == null)
            return 0;
        else if (user.getUsername().equalsIgnoreCase(post.getUploader().getUsername()))
            return 1;
        List<Tier> userTiers = tierDAO.getTiersBySubscription(user);
        for (Tier userTier : userTiers)
            for (Tier postTier : postTiers)
                if (userTier.getTierId() == postTier.getTierId())
                    return 1;
        return 0;
    }
}
