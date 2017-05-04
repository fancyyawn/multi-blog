const express = require('express');
const session = require('express-session');
const MongoStore = require('connect-mongo')(session);
const flash = require('connect-flash');
const config = require('config-lite');
const routes = require('./controllers');
const winston = require('winston');
const expressWinston = require('express-winston');
const formidable = require('express-formidable');
const path = require('path');

const app = express();

app.set('views', path.join(__dirname, 'views'));
app.set('view engine', 'ejs');

app.use(express.static(path.join(__dirname, 'public')));

app.use(session({
  name: config.session.key,
  secret: config.session.secret,
  resave: true,
  saveUninitialized: false,
  cookie: {
    maxAge: config.session.maxAge
  },
  store: new MongoStore({
    url: config.mongodb
  })
}));

app.use(flash());

app.use(formidable({
  uploadDir: path.join(__dirname, 'public/upload'),
  keepExtensions: true
}));


app.locals.blog = {
  title: config.blog.title,
  description: config.blog.description
};
app.use(function (req, res, next) {
  res.locals.user = req.session.user;
  res.locals.success = req.flash('success').toString();
  res.locals.error = req.flash('error').toString();
  next();
});

routes(app);

app.use(expressWinston.errorLogger({
  transports: [
    new winston.transports.Console({
      json: true,
      colorize: true
    }),
    new winston.transports.File({
      filename: 'logs-error.log'
    })
  ]
}));

app.use(function (err, req, res, next) {
  res.render('error', { error: err})
});

if(module.parent){
  module.exports = app;
}else{
  app.listen(config.port, function () {
    console.log(`app listening on port ${config.port}`);
  })
}