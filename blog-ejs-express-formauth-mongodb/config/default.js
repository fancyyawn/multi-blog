module.exports = {
  blog: {
    title: '多人博客平台',
    description: '一个博客示例，来自Node.js实战'
  },
  port: 3000,
  session: {
    secret: 'myblog',
    key: 'myblog',
    maxAge: 2592000000
  },
  mongodb: process.env.MONGODB_UIL || 'mongodb://localhost:27017/zblog',
  imageBase: '/upload/'

};