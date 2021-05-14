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

router.get('/phone/:phone', async (req, res) => {
  const user = await userService.findUserByPhone(req.params.phone, false);
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

router.put('/:user_id', async (req, res) => {
  try {
    const result = await userService.updateUserById(
      req.params.user_id,
      req.body
    );
    res.json(result);
  } catch (err) {
    console.log(err);
    res.status(404).json({ message: err.message });
  }
});

router.delete('/:user_id', async (req, res) => {
  try {
    const result = await userService.deleteUser(req.params.user_id);
    res.json(result);
  } catch (err) {
    console.log(err);
    res.status(404).json({ message: err.message });
  }
});

export const UserController = router;
