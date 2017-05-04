const marked = require('marked');
const Comment = require('../repos').Comment;

Comment.plugin('contentToHtml', {
  afterFind(comments) {
    comments.forEach((comment) => {
      comment.content = marked(comment.content);
    });
    return comments;
  }
});

module.exports = {
  create(comment){
    return Comment.create(comment).exec();
  },

  delCommentById(commentId, author){
    return Comment.remove({author, _id: commentId}).exec();
  },

  delCommentsByPostId(postId){
    return Comment.remove({postId}).exec();
  },

  getComments(postId){
    return Comment
        .find({ postId })
        .populate({path: 'author', model: 'User'})
        .sort({ _id: 1 })
        .addCreatedAt()
        .contentToHtml()
        .exec();
  },

  getCommentsCount(postId){
    return Comment.count({ postId }).exec();
  }
};