const express = require('express');
const router = express.Router();

const PostService = require('../services/posts');
const CommentService = require('../services/comments');
const checkLogin = require('../middlewares/check').checkLogin;

router.get('/', function (req, res, next) {
  let author = req.query.author;

  PostService.getPosts(author).then(posts => {
    res.render('posts', {
      posts: posts
    });
  }).catch(next);

});

router.get('/create', checkLogin, function (req, res) {
  res.render('create');
});

router.post('/create', checkLogin, function (req, res, next) {
  let author = req.session.user._id;
  let title = req.fields.title;
  let content = req.fields.content;

  // 校验参数
  try {
    if (!title.length) {
      throw new Error('请填写标题');
    }
    if (!content.length) {
      throw new Error('请填写内容');
    }
  } catch (e) {
    req.flash('error', e.message);
    return res.redirect('back');
  }

  let post = {
    author: author,
    title: title,
    content: content,
    pv: 0
  };

  PostService.create(post).then(result=>{
    post = result.ops[0];
    req.flash('success', '发表成功');
    res.redirect(`/posts/${post._id}`);
  }).catch(next);

});

router.get('/:postId', function (req, res, next) {
  let postId = req.params.postId;

  Promise.all([
    PostService.getPostById(postId),// 获取文章信息
    CommentService.getComments(postId),// 获取该文章所有留言
    PostService.incPv(postId)// pv 加 1
  ]).then(result =>{
    let post = result[0];
    let comments = result[1];
    if (!post) {
      throw new Error('该文章不存在');
    }
    res.render('post', {
      post: post,
      comments: comments
    });
  }).catch(next);

});

router.get('/:postId/edit', checkLogin, function (req, res, next) {
  let postId = req.params.postId;
  let author = req.session.user._id;

  PostService.getRawPostById(postId).then((post)=>{
    if (!post) {
      throw new Error('该文章不存在');
    }
    if (author.toString() !== post.author._id.toString()) {
      throw new Error('权限不足');
    }
    res.render('edit', {
      post: post
    });

  }).catch(next);

});

router.post('/:postId/edit', checkLogin, function (req, res, next) {

  let postId = req.params.postId;
  let author = req.session.user._id;
  let title = req.fields.title;
  let content = req.fields.content;

  PostService.updatePostById(postId, author,
      { title: title, content: content }).then(() => {

    req.flash('success', '编辑文章成功');
    res.redirect(`/posts/${postId}`);

  }).catch(next);

});

router.get('/:postId/remove', checkLogin, function (req, res, next) {
  let postId = req.params.postId;
  let author = req.session.user._id;

  PostService.delPostById(postId, author).then(() => {

    req.flash('success', '删除文章成功');
    res.redirect('/posts');

  }).catch(next);
});

router.post('/:postId/comment', checkLogin, function (req, res, next) {
  let author = req.session.user._id;
  let postId = req.params.postId;
  let content = req.fields.content;
  let comment = {
    author: author,
    postId: postId,
    content: content
  };

  CommentService.create(comment).then(()=> {

    req.flash('success', '留言成功');
    res.redirect('back');

  }).catch(next);
});

router.get('/:postId/comment/:commentId/remove', checkLogin, function (req, res, next) {
  let commentId = req.params.commentId;
  let author = req.session.user._id;

  CommentService.delCommentById(commentId, author).then(() => {

    req.flash('success', '删除留言成功');
    res.redirect('back');

  }).catch(next);

});

module.exports = router;