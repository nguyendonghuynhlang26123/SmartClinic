import * as express from "express";
import { UserService } from "./user.service";
const router = express.Router();

const userService: UserService = new UserService();

router.get("/", async (req, res) => {
  const users = await userService.getAllUser();
  res.json(users);
});

router.get("/:user_id", async (req, res) => {
  const user = await userService.getUserById(req.params.user_id);
  res.json(user);
});
