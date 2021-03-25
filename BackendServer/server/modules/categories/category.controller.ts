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

router.put("/:category_id", async (req, res) => {
  try {
    const result = await categoryService.updateCategoryById(
      req.params.category_id,
      req.body
    );
    res.json(result);
  } catch (err) {
    console.log(err);
    res.status(404).json({ message: err.message });
  }
});

router.delete("/:category_id", async (req, res) => {
  try {
    const result = await categoryService.deleteCategory(
      req.params.category_id
    );
    res.json(result);
  } catch (err) {
    console.log(err);
    res.status(404).json({ message: err.message });
  }
});

export const CategoryController = router;
