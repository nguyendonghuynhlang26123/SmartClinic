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

export const CategoryController = router;