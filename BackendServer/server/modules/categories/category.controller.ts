import * as express from "express";
import { CategoryService } from "./category.service";
const router = express.Router();

const categoryService: CategoryService = new CategoryService();

router.get("/", async (req, res) => {
  const categories = await categoryService.getAllCategory();
  res.json(categories);
});

router.get("/:category_id", async (req, res) => {
  const category = await categoryService.getCategoryById(
    req.params.category_id
  );
  res.json(category);
});

router.post("/", async (req, res) => {
  try {
    const category = await categoryService.createCategory(req.body);
    res.json(category);
  } catch (err) {
    console.log(err);
    res.status(400).json({ message: err.message });
  }
});

export const CategoryController = router;
