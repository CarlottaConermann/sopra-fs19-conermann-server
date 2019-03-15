/**
 * User model
 */
class User {
  constructor(data = {}) {
    this.id = null;
    this.name = null;
    this.username = null;
    this.birthday = null;
    this.status = null;
    this.creationDate = null;
    this.token = null;
    this.games = null;
    this.moves = null;
    Object.assign(this, data);
  }
}
export default User;


