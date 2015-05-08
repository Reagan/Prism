package prism.re.gan.prism.db.domain;

import java.util.Date;

/**
 * Created by rmbitiru on 4/11/15.
 */
public class CommentModel {

    private int productId ;
    private int commentId ;
    private int authorId;
    private int authorName ;
    private Date publishTimeStamp ;
    private int parentCommentId ;
    private int commentIndex ;
    private String comment ;

    public CommentModel() {}

    public CommentModel(int productId, int commentId, int authorId,
                        int authorName, Date publishTimeStamp, int parentCommentId,
                        int commentIndex, String comment) {
        setProductId(productId);
        setCommentId(commentId);
        setAuthorId(authorId);
        setAuthorName(authorName);
        setPublishTimeStamp(publishTimeStamp);
        setParentCommentId(parentCommentId);
        setCommentIndex(commentIndex);
        setComment(comment);
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public int getAuthorName() {
        return authorName;
    }

    public void setAuthorName(int authorName) {
        this.authorName = authorName;
    }

    public Date getPublishTimeStamp() {
        return publishTimeStamp;
    }

    public void setPublishTimeStamp(Date publishTimeStamp) {
        this.publishTimeStamp = publishTimeStamp;
    }

    public int getParentCommentId() {
        return parentCommentId;
    }

    public void setParentCommentId(int parentCommentId) {
        this.parentCommentId = parentCommentId;
    }

    public int getCommentIndex() {
        return commentIndex;
    }

    public void setCommentIndex(int commentIndex) {
        this.commentIndex = commentIndex;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
