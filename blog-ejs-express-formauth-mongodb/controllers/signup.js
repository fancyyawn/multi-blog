const express = require('express');
const router = express.Router();
const sha1 = require('sha1');
const path = require('path');
const fs = require('fs');
const config = require('config-lite');

const UserService = require('../services/users');
const checkNotLogin = require('../middlewares/check').checkNotLogin;


router.get('/', checkNotLogin, function(req, res, next) {
  res.render('signup');
});

router.post('/', checkNotLogin, function(req, res, next) {

  let name = req.fields.name;
  let gender = req.fields.gender;
  let bio = req.fields.bio;
  let avatar = config.imageBase+req.files.avatar.path.split(path.sep).pop();
  let password = req.fields.password;
  let repassword = req.fields.repassword;

  // 校验参数
  try {
    if (!(name.length >= 1 && name.length <= 10)) {
      throw new Error('名字请限制在 1-10 个字符');
    }
    if (['m', 'f', 'x'].indexOf(gender) === -1) {
      throw new Error('性别只能是 m、f 或 x');
    }
    if (!(bio.length >= 1 && bio.length <= 30)) {
      throw new Error('个人简介请限制在 1-30 个字符');
    }
    if (!req.files.avatar.name) {
      throw new Error('缺少头像');
    }
    if (password.length < 6) {
      throw new Error('密码至少 6 个字符');
    }
    if (password !== repassword) {
      throw new Error('两次输入密码不一致');
    }
  } catch (e) {
    // 注册失败，异步删除上传的头像
    fs.unlink(req.files.avatar.path);
    req.flash('error', e.message);
    return res.redirect('/signup');
  }

  let user = {
    name: name,
    password: sha1(password),
    gender: gender,
    bio: bio,
    avatar: avatar
  };

  UserService.create(user).then(result=>{
    user = result.ops[0];
    delete user.password;
    req.session.user = user;
    req.flash('success', '注册成功');
    res.redirect('/posts');
  }).catch(e=>{
    fs.unlink(req.files.avatar.path);

    // 用户名被占用则跳回注册页，而不是错误页
    if (e.message.match('E11000 duplicate key')) {
      req.flash('error', '用户名已被占用');
      return res.redirect('/signup');
    }
    next(e);
  });
});

module.exports = router;