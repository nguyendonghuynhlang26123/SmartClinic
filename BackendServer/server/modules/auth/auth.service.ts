import * as bcrypt from 'bcrypt';
import { UserService } from '../users/user.service';

export class AuthService {
  constructor(private userService: UserService = new UserService()) {}

  async authenticate(phone: string, password: string, fn) {
    try {
      const user = await this.userService.findUserByPhone(phone);
      if (!user) return fn(new Error('Cannot find user'));
      if (!bcrypt.compareSync(password, user.password))
        return fn(new Error('Invalid password'));
      return fn(null, user);
    } catch (err) {
      return fn(err, null);
    }
  }

  async restrictDoctor(req, res, next) {
    if (req.session.auth && req.session.user_type == 'DOCTOR') {
      next();
    } else {
      req.session.error = 'Access denied! Please login.';
      res.redirect('/login');
    }
  }

  async restrict(req, res, next) {
    if (req.session.auth) {
      next();
    } else {
      req.session.error = 'Access denied! Please login.';
      res.redirect('/login');
    }
  }
}
