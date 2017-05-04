const User = require('../repos').User;

module.exports = {

  create(user){
    return User.create(user).exec();
  },

  getUserByName(name){
    return User.findOne({ name: name})
        .addCreatedAt()
        .exec();
  }
};