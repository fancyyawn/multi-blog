const express = require('express');
const router = express.Router();
const sha1 = require('sha1');
const UserService = require('../services/users');
const checkNotLogin = require('../middlewares/check').checkNotLogin;

router.get('/', checkNotLogin, function (req, res) {
  res.render('login');
});

router.post('/', checkNotLogin, function (req, res, next) {
  let name = req.fields.name;
  let password = req.fields.password;

  UserService.getUserByName(name).then((user)=>{
    if (!user) {
      req.flash('error', '用户不存在');
      return res.redirect('back');
    }
    if (sha1(password) !== user.password) {
      req.flash('error', '用户名或密码错误');
      return res.redirect('back');
    }
    req.flash('success', '登录成功');
    delete user.password;
    req.session.user = user;
    res.redirect('/posts');

  }).catch(next);
});

module.exports = router;

