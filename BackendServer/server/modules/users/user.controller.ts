import * as express from 'express';
import { UserService } from './user.service';
const router = express.Router();

const userService: UserService = new UserService();

router.get('/', async (req, res) => {
  const users = await userService.getAllUser();
  res.json(users);
});

router.get('/:user_id', async (req, res) => {
  const user = await userService.getUserById(req.params.user_id);
  res.json(user);
});

router.post('/', async (req, res) => {
  try {
    const user = await userService.createUser(req.body);
    res.json(user);
  } catch (err) {
    res.status(400).send({ message: err.message });
  }
});

export const UserController = router;
