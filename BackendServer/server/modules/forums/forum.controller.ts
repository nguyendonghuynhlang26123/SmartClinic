import * as express from 'express';
import { ForumService } from './forum.service';
const router = express.Router();

const forumService: ForumService = new ForumService();

router.get('/', async (req, res) => {
  const forums = await forumService.getAllForum(req.query);
  res.json(forums);
});

router.get('/:forum_id', async (req, res) => {
  const forum = await forumService.getForumById(req.params.forum_id);
  res.json(forum);
});

router.post('/', async (req, res) => {
  try {
    const forum = await forumService.createForum(req.body);
    res.json(forum);
  } catch (err) {
    console.log(err);
    res.status(400).json({ message: err.message });
  }
});

router.put('/:forum_id', async (req, res) => {
  try {
    const result = await forumService.updateForumById(
      req.params.forum_id,
      req.body
    );
    res.json(result);
  } catch (err) {
    console.log(err);
    res.status(404).json({ message: err.message });
  }
});

router.put('/answer/:forum_id', async (req, res) => {
  try {
    const result = await forumService.addChatToForum(
      req.params.forum_id,
      req.body
    );
    res.json(result);
  } catch (err) {
    console.log(err);
    res.status(404).json({ message: err.message });
  }
});

router.delete('/:forum_id', async (req, res) => {
  try {
    const result = await forumService.deleteForum(req.params.forum_id);
    res.json(result);
  } catch (err) {
    console.log(err);
    res.status(404).json({ message: err.message });
  }
});

export const ForumController = router;
